/*     */ package com.mojang.authlib.yggdrasil;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.mojang.authlib.AuthenticationService;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.HttpAuthenticationService;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import com.mojang.authlib.minecraft.HttpMinecraftSessionService;
/*     */ import com.mojang.authlib.minecraft.InsecureTextureException;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.yggdrasil.request.JoinMinecraftServerRequest;
/*     */ import com.mojang.authlib.yggdrasil.response.HasJoinedMinecraftServerResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.MinecraftProfilePropertiesResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.MinecraftTexturesPayload;
/*     */ import com.mojang.authlib.yggdrasil.response.Response;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.net.URL;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.commons.codec.Charsets;
/*     */ import org.apache.commons.codec.binary.Base64;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilMinecraftSessionService extends HttpMinecraftSessionService {
/*  40 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final String BASE_URL = "https://sessionserver.mojang.com/session/minecraft/";
/*  42 */   private static final URL JOIN_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/join");
/*  43 */   private static final URL CHECK_URL = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/hasJoined");
/*     */   
/*     */   private final PublicKey publicKey;
/*  46 */   private final Gson gson = (new GsonBuilder()).registerTypeAdapter(UUID.class, new UUIDTypeAdapter()).create();
/*  47 */   private final LoadingCache<GameProfile, GameProfile> insecureProfiles = CacheBuilder.newBuilder().expireAfterWrite(6L, TimeUnit.HOURS).build(new CacheLoader<GameProfile, GameProfile>()
/*     */       {
/*     */ 
/*     */         
/*     */         public GameProfile load(GameProfile key) throws Exception
/*     */         {
/*  53 */           return YggdrasilMinecraftSessionService.this.fillGameProfile(key, false);
/*     */         }
/*     */       });
/*     */   
/*     */   protected YggdrasilMinecraftSessionService(YggdrasilAuthenticationService authenticationService) {
/*  58 */     super(authenticationService);
/*     */     
/*     */     try {
/*  61 */       X509EncodedKeySpec spec = new X509EncodedKeySpec(IOUtils.toByteArray(YggdrasilMinecraftSessionService.class.getResourceAsStream("/yggdrasil_session_pubkey.der")));
/*  62 */       KeyFactory keyFactory = KeyFactory.getInstance("RSA");
/*  63 */       this.publicKey = keyFactory.generatePublic(spec);
/*  64 */     } catch (Exception e) {
/*  65 */       throw new Error("Missing/invalid yggdrasil public key!");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void joinServer(GameProfile profile, String authenticationToken, String serverId) throws AuthenticationException {
/*  71 */     JoinMinecraftServerRequest request = new JoinMinecraftServerRequest();
/*  72 */     request.accessToken = authenticationToken;
/*  73 */     request.selectedProfile = profile.getId();
/*  74 */     request.serverId = serverId;
/*     */     
/*  76 */     getAuthenticationService().makeRequest(JOIN_URL, request, Response.class);
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile hasJoinedServer(GameProfile user, String serverId) throws AuthenticationUnavailableException {
/*  81 */     Map<String, Object> arguments = new HashMap<String, Object>();
/*     */     
/*  83 */     arguments.put("username", user.getName());
/*  84 */     arguments.put("serverId", serverId);
/*     */     
/*  86 */     URL url = HttpAuthenticationService.concatenateURL(CHECK_URL, HttpAuthenticationService.buildQuery(arguments));
/*     */     
/*     */     try {
/*  89 */       HasJoinedMinecraftServerResponse response = getAuthenticationService().<HasJoinedMinecraftServerResponse>makeRequest(url, null, HasJoinedMinecraftServerResponse.class);
/*     */       
/*  91 */       if (response != null && response.getId() != null) {
/*  92 */         GameProfile result = new GameProfile(response.getId(), user.getName());
/*     */         
/*  94 */         if (response.getProperties() != null) {
/*  95 */           result.getProperties().putAll((Multimap)response.getProperties());
/*     */         }
/*     */         
/*  98 */         return result;
/*     */       } 
/* 100 */       return null;
/*     */     }
/* 102 */     catch (AuthenticationUnavailableException e) {
/* 103 */       throw e;
/* 104 */     } catch (AuthenticationException e) {
/* 105 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> getTextures(GameProfile profile, boolean requireSecure) {
/*     */     MinecraftTexturesPayload result;
/* 111 */     Property textureProperty = (Property)Iterables.getFirst(profile.getProperties().get("textures"), null);
/*     */     
/* 113 */     if (textureProperty == null) {
/* 114 */       return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
/*     */     }
/*     */     
/* 117 */     if (requireSecure) {
/* 118 */       if (!textureProperty.hasSignature()) {
/* 119 */         LOGGER.error("Signature is missing from textures payload");
/* 120 */         throw new InsecureTextureException("Signature is missing from textures payload");
/*     */       } 
/*     */       
/* 123 */       if (!textureProperty.isSignatureValid(this.publicKey)) {
/* 124 */         LOGGER.error("Textures payload has been tampered with (signature invalid)");
/* 125 */         throw new InsecureTextureException("Textures payload has been tampered with (signature invalid)");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 131 */       String json = new String(Base64.decodeBase64(textureProperty.getValue()), Charsets.UTF_8);
/* 132 */       result = (MinecraftTexturesPayload)this.gson.fromJson(json, MinecraftTexturesPayload.class);
/* 133 */     } catch (JsonParseException e) {
/* 134 */       LOGGER.error("Could not decode textures payload", (Throwable)e);
/* 135 */       return new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>();
/*     */     } 
/*     */     
/* 138 */     return (result.getTextures() == null) ? new HashMap<MinecraftProfileTexture.Type, MinecraftProfileTexture>() : result.getTextures();
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile fillProfileProperties(GameProfile profile, boolean requireSecure) {
/* 143 */     if (profile.getId() == null) {
/* 144 */       return profile;
/*     */     }
/*     */     
/* 147 */     if (!requireSecure) {
/* 148 */       return (GameProfile)this.insecureProfiles.getUnchecked(profile);
/*     */     }
/*     */     
/* 151 */     return fillGameProfile(profile, true);
/*     */   }
/*     */   
/*     */   protected GameProfile fillGameProfile(GameProfile profile, boolean requireSecure) {
/*     */     try {
/* 156 */       URL url = HttpAuthenticationService.constantURL("https://sessionserver.mojang.com/session/minecraft/profile/" + UUIDTypeAdapter.fromUUID(profile.getId()));
/* 157 */       url = HttpAuthenticationService.concatenateURL(url, "unsigned=" + (!requireSecure ? 1 : 0));
/* 158 */       MinecraftProfilePropertiesResponse response = getAuthenticationService().<MinecraftProfilePropertiesResponse>makeRequest(url, null, MinecraftProfilePropertiesResponse.class);
/*     */       
/* 160 */       if (response == null) {
/* 161 */         LOGGER.debug("Couldn't fetch profile properties for " + profile + " as the profile does not exist");
/* 162 */         return profile;
/*     */       } 
/* 164 */       GameProfile result = new GameProfile(response.getId(), response.getName());
/* 165 */       result.getProperties().putAll((Multimap)response.getProperties());
/* 166 */       profile.getProperties().putAll((Multimap)response.getProperties());
/* 167 */       LOGGER.debug("Successfully fetched profile properties for " + profile);
/* 168 */       return result;
/*     */     }
/* 170 */     catch (AuthenticationException e) {
/* 171 */       LOGGER.warn("Couldn't look up profile properties for " + profile, (Throwable)e);
/* 172 */       return profile;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public YggdrasilAuthenticationService getAuthenticationService() {
/* 178 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\yggdrasil\YggdrasilMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */