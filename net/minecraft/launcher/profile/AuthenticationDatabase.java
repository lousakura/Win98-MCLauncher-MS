/*     */ package net.minecraft.launcher.profile;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.mojang.authlib.AuthenticationService;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ 
/*     */ public class AuthenticationDatabase {
/*     */   public static final String DEMO_UUID_PREFIX = "demo-";
/*     */   
/*     */   public AuthenticationDatabase(AuthenticationService authenticationService) {
/*  20 */     this(new HashMap<String, UserAuthentication>(), authenticationService);
/*     */   }
/*     */   private final Map<String, UserAuthentication> authById; private final AuthenticationService authenticationService;
/*     */   public AuthenticationDatabase(Map<String, UserAuthentication> authById, AuthenticationService authenticationService) {
/*  24 */     this.authById = authById;
/*  25 */     this.authenticationService = authenticationService;
/*     */   }
/*     */   
/*     */   public UserAuthentication getByName(String name) {
/*  29 */     if (name == null) return null;
/*     */     
/*  31 */     for (Map.Entry<String, UserAuthentication> entry : this.authById.entrySet()) {
/*  32 */       GameProfile profile = ((UserAuthentication)entry.getValue()).getSelectedProfile();
/*     */       
/*  34 */       if (profile != null && profile.getName().equals(name))
/*  35 */         return entry.getValue(); 
/*  36 */       if (profile == null && getUserFromDemoUUID(entry.getKey()).equals(name)) {
/*  37 */         return entry.getValue();
/*     */       }
/*     */     } 
/*     */     
/*  41 */     return null;
/*     */   }
/*     */   
/*     */   public UserAuthentication getByUUID(String uuid) {
/*  45 */     return this.authById.get(uuid);
/*     */   }
/*     */   
/*     */   public Collection<String> getKnownNames() {
/*  49 */     List<String> names = new ArrayList<String>();
/*     */     
/*  51 */     for (Map.Entry<String, UserAuthentication> entry : this.authById.entrySet()) {
/*  52 */       GameProfile profile = ((UserAuthentication)entry.getValue()).getSelectedProfile();
/*     */       
/*  54 */       if (profile != null) {
/*  55 */         names.add(profile.getName()); continue;
/*     */       } 
/*  57 */       names.add(getUserFromDemoUUID(entry.getKey()));
/*     */     } 
/*     */ 
/*     */     
/*  61 */     return names;
/*     */   }
/*     */   
/*     */   public void register(String uuid, UserAuthentication authentication) {
/*  65 */     this.authById.put(uuid, authentication);
/*     */   }
/*     */   
/*     */   public Set<String> getknownUUIDs() {
/*  69 */     return this.authById.keySet();
/*     */   }
/*     */   
/*     */   public void removeUUID(String uuid) {
/*  73 */     this.authById.remove(uuid);
/*     */   }
/*     */   
/*     */   public AuthenticationService getAuthenticationService() {
/*  77 */     return this.authenticationService;
/*     */   }
/*     */   
/*     */   public static class Serializer implements JsonDeserializer<AuthenticationDatabase>, JsonSerializer<AuthenticationDatabase> {
/*     */     private final Launcher launcher;
/*     */     
/*     */     public Serializer(Launcher launcher) {
/*  84 */       this.launcher = launcher;
/*     */     }
/*     */ 
/*     */     
/*     */     public AuthenticationDatabase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/*  89 */       Map<String, UserAuthentication> services = new HashMap<String, UserAuthentication>();
/*  90 */       Map<String, Map<String, Object>> credentials = deserializeCredentials((JsonObject)json, context);
/*  91 */       YggdrasilAuthenticationService authService = new YggdrasilAuthenticationService(this.launcher.getLauncher().getProxy(), this.launcher.getClientToken().toString());
/*     */       
/*  93 */       for (Map.Entry<String, Map<String, Object>> entry : credentials.entrySet()) {
/*  94 */         UserAuthentication auth = authService.createUserAuthentication(this.launcher.getLauncher().getAgent());
/*  95 */         auth.loadFromStorage(entry.getValue());
/*  96 */         services.put(entry.getKey(), auth);
/*     */       } 
/*     */       
/*  99 */       return new AuthenticationDatabase(services, (AuthenticationService)authService);
/*     */     }
/*     */     
/*     */     protected Map<String, Map<String, Object>> deserializeCredentials(JsonObject json, JsonDeserializationContext context) {
/* 103 */       Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>();
/*     */       
/* 105 */       for (Map.Entry<String, JsonElement> authEntry : (Iterable<Map.Entry<String, JsonElement>>)json.entrySet()) {
/* 106 */         Map<String, Object> credentials = new LinkedHashMap<String, Object>();
/*     */         
/* 108 */         for (Map.Entry<String, JsonElement> credentialsEntry : (Iterable<Map.Entry<String, JsonElement>>)((JsonObject)authEntry.getValue()).entrySet()) {
/* 109 */           credentials.put(credentialsEntry.getKey(), deserializeCredential(credentialsEntry.getValue()));
/*     */         }
/*     */         
/* 112 */         result.put(authEntry.getKey(), credentials);
/*     */       } 
/*     */       
/* 115 */       return result;
/*     */     }
/*     */     
/*     */     private Object deserializeCredential(JsonElement element) {
/* 119 */       if (element instanceof JsonObject) {
/* 120 */         Map<String, Object> result = new LinkedHashMap<String, Object>();
/* 121 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)((JsonObject)element).entrySet()) {
/* 122 */           result.put(entry.getKey(), deserializeCredential(entry.getValue()));
/*     */         }
/* 124 */         return result;
/* 125 */       }  if (element instanceof com.google.gson.JsonArray) {
/* 126 */         List<Object> result = new ArrayList();
/* 127 */         for (JsonElement entry : element) {
/* 128 */           result.add(deserializeCredential(entry));
/*     */         }
/* 130 */         return result;
/*     */       } 
/* 132 */       return element.getAsString();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public JsonElement serialize(AuthenticationDatabase src, Type typeOfSrc, JsonSerializationContext context) {
/* 138 */       Map<String, UserAuthentication> services = src.authById;
/* 139 */       Map<String, Map<String, Object>> credentials = new HashMap<String, Map<String, Object>>();
/*     */       
/* 141 */       for (Map.Entry<String, UserAuthentication> entry : services.entrySet()) {
/* 142 */         credentials.put(entry.getKey(), ((UserAuthentication)entry.getValue()).saveForStorage());
/*     */       }
/*     */       
/* 145 */       return context.serialize(credentials);
/*     */     }
/*     */   }
/*     */   
/*     */   public static String getUserFromDemoUUID(String uuid) {
/* 150 */     if (uuid.startsWith("demo-") && uuid.length() > "demo-".length()) {
/* 151 */       return "Demo User " + uuid.substring("demo-".length());
/*     */     }
/* 153 */     return "Demo User";
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\profile\AuthenticationDatabase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */