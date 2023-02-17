/*     */ package com.mojang.authlib.yggdrasil;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.Agent;
/*     */ import com.mojang.authlib.AuthenticationService;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.HttpAuthenticationService;
/*     */ import com.mojang.authlib.UserType;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.yggdrasil.request.AuthenticationRequest;
/*     */ import com.mojang.authlib.yggdrasil.request.RefreshRequest;
/*     */ import com.mojang.authlib.yggdrasil.request.ValidateRequest;
/*     */ import com.mojang.authlib.yggdrasil.response.AuthenticationResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.RefreshResponse;
/*     */ import com.mojang.authlib.yggdrasil.response.User;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class YggdrasilUserAuthentication extends HttpUserAuthentication {
/*  23 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private static final String BASE_URL = "https://authserver.mojang.com/";
/*  25 */   private static final URL ROUTE_AUTHENTICATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/authenticate");
/*  26 */   private static final URL ROUTE_REFRESH = HttpAuthenticationService.constantURL("https://authserver.mojang.com/refresh");
/*  27 */   private static final URL ROUTE_VALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/validate");
/*  28 */   private static final URL ROUTE_INVALIDATE = HttpAuthenticationService.constantURL("https://authserver.mojang.com/invalidate");
/*  29 */   private static final URL ROUTE_SIGNOUT = HttpAuthenticationService.constantURL("https://authserver.mojang.com/signout");
/*     */   
/*     */   private static final String STORAGE_KEY_ACCESS_TOKEN = "accessToken";
/*     */   
/*     */   private final Agent agent;
/*     */   private GameProfile[] profiles;
/*     */   private String accessToken;
/*     */   private boolean isOnline;
/*     */   
/*     */   public YggdrasilUserAuthentication(YggdrasilAuthenticationService authenticationService, Agent agent) {
/*  39 */     super(authenticationService);
/*  40 */     this.agent = agent;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canLogIn() {
/*  45 */     return (!canPlayOnline() && StringUtils.isNotBlank(getUsername()) && (StringUtils.isNotBlank(getPassword()) || StringUtils.isNotBlank(getAuthenticatedToken())));
/*     */   }
/*     */ 
/*     */   
/*     */   public void logIn() throws AuthenticationException {
/*  50 */     if (StringUtils.isBlank(getUsername())) {
/*  51 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*     */     
/*  54 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/*  55 */       logInWithToken();
/*  56 */     } else if (StringUtils.isNotBlank(getPassword())) {
/*  57 */       logInWithPassword();
/*     */     } else {
/*  59 */       throw new InvalidCredentialsException("Invalid password");
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void logInWithPassword() throws AuthenticationException {
/*  64 */     if (StringUtils.isBlank(getUsername())) {
/*  65 */       throw new InvalidCredentialsException("Invalid username");
/*     */     }
/*  67 */     if (StringUtils.isBlank(getPassword())) {
/*  68 */       throw new InvalidCredentialsException("Invalid password");
/*     */     }
/*     */     
/*  71 */     LOGGER.info("Logging in with username & password");
/*     */     
/*  73 */     AuthenticationRequest request = new AuthenticationRequest(this, getUsername(), getPassword());
/*  74 */     AuthenticationResponse response = getAuthenticationService().<AuthenticationResponse>makeRequest(ROUTE_AUTHENTICATE, request, AuthenticationResponse.class);
/*     */     
/*  76 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/*  77 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/*  80 */     if (response.getSelectedProfile() != null) {
/*  81 */       setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*  82 */     } else if (ArrayUtils.isNotEmpty((Object[])response.getAvailableProfiles())) {
/*  83 */       setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     } 
/*     */     
/*  86 */     User user = response.getUser();
/*     */     
/*  88 */     if (user != null && user.getId() != null) {
/*  89 */       setUserid(user.getId());
/*     */     } else {
/*  91 */       setUserid(getUsername());
/*     */     } 
/*     */     
/*  94 */     this.isOnline = true;
/*  95 */     this.accessToken = response.getAccessToken();
/*  96 */     this.profiles = response.getAvailableProfiles();
/*  97 */     setSelectedProfile(response.getSelectedProfile());
/*  98 */     getModifiableUserProperties().clear();
/*     */     
/* 100 */     updateUserProperties(user);
/*     */   }
/*     */   
/*     */   protected void updateUserProperties(User user) {
/* 104 */     if (user == null)
/*     */       return; 
/* 106 */     if (user.getProperties() != null) {
/* 107 */       getModifiableUserProperties().putAll((Multimap)user.getProperties());
/*     */     }
/*     */   }
/*     */   
/*     */   protected void logInWithToken() throws AuthenticationException {
/* 112 */     if (StringUtils.isBlank(getUserID())) {
/* 113 */       if (StringUtils.isBlank(getUsername())) {
/* 114 */         setUserid(getUsername());
/*     */       } else {
/* 116 */         throw new InvalidCredentialsException("Invalid uuid & username");
/*     */       } 
/*     */     }
/* 119 */     if (StringUtils.isBlank(getAuthenticatedToken())) {
/* 120 */       throw new InvalidCredentialsException("Invalid access token");
/*     */     }
/*     */     
/* 123 */     LOGGER.info("Logging in with access token");
/*     */     
/* 125 */     if (checkTokenValidity()) {
/* 126 */       LOGGER.debug("Skipping refresh call as we're safely logged in.");
/* 127 */       this.isOnline = true;
/*     */       
/*     */       return;
/*     */     } 
/* 131 */     RefreshRequest request = new RefreshRequest(this);
/* 132 */     RefreshResponse response = getAuthenticationService().<RefreshResponse>makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
/*     */     
/* 134 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 135 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 138 */     if (response.getSelectedProfile() != null) {
/* 139 */       setUserType(response.getSelectedProfile().isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/* 140 */     } else if (ArrayUtils.isNotEmpty((Object[])response.getAvailableProfiles())) {
/* 141 */       setUserType(response.getAvailableProfiles()[0].isLegacy() ? UserType.LEGACY : UserType.MOJANG);
/*     */     } 
/*     */     
/* 144 */     if (response.getUser() != null && response.getUser().getId() != null) {
/* 145 */       setUserid(response.getUser().getId());
/*     */     } else {
/* 147 */       setUserid(getUsername());
/*     */     } 
/*     */     
/* 150 */     this.isOnline = true;
/* 151 */     this.accessToken = response.getAccessToken();
/* 152 */     this.profiles = response.getAvailableProfiles();
/* 153 */     setSelectedProfile(response.getSelectedProfile());
/* 154 */     getModifiableUserProperties().clear();
/*     */     
/* 156 */     updateUserProperties(response.getUser());
/*     */   }
/*     */   
/*     */   protected boolean checkTokenValidity() throws AuthenticationException {
/* 160 */     ValidateRequest request = new ValidateRequest(this);
/*     */     try {
/* 162 */       getAuthenticationService().makeRequest(ROUTE_VALIDATE, request, Response.class);
/* 163 */       return true;
/* 164 */     } catch (AuthenticationException ex) {
/* 165 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void logOut() {
/* 171 */     super.logOut();
/*     */     
/* 173 */     this.accessToken = null;
/* 174 */     this.profiles = null;
/* 175 */     this.isOnline = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameProfile[] getAvailableProfiles() {
/* 180 */     return this.profiles;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLoggedIn() {
/* 185 */     return StringUtils.isNotBlank(this.accessToken);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlayOnline() {
/* 190 */     return (isLoggedIn() && getSelectedProfile() != null && this.isOnline);
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectGameProfile(GameProfile profile) throws AuthenticationException {
/* 195 */     if (!isLoggedIn()) {
/* 196 */       throw new AuthenticationException("Cannot change game profile whilst not logged in");
/*     */     }
/* 198 */     if (getSelectedProfile() != null) {
/* 199 */       throw new AuthenticationException("Cannot change game profile. You must log out and back in.");
/*     */     }
/* 201 */     if (profile == null || !ArrayUtils.contains((Object[])this.profiles, profile)) {
/* 202 */       throw new IllegalArgumentException("Invalid profile '" + profile + "'");
/*     */     }
/*     */     
/* 205 */     RefreshRequest request = new RefreshRequest(this, profile);
/* 206 */     RefreshResponse response = getAuthenticationService().<RefreshResponse>makeRequest(ROUTE_REFRESH, request, RefreshResponse.class);
/*     */     
/* 208 */     if (!response.getClientToken().equals(getAuthenticationService().getClientToken())) {
/* 209 */       throw new AuthenticationException("Server requested we change our client token. Don't know how to handle this!");
/*     */     }
/*     */     
/* 212 */     this.isOnline = true;
/* 213 */     this.accessToken = response.getAccessToken();
/* 214 */     setSelectedProfile(response.getSelectedProfile());
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadFromStorage(Map<String, Object> credentials) {
/* 219 */     super.loadFromStorage(credentials);
/*     */     
/* 221 */     this.accessToken = String.valueOf(credentials.get("accessToken"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, Object> saveForStorage() {
/* 226 */     Map<String, Object> result = super.saveForStorage();
/*     */     
/* 228 */     if (StringUtils.isNotBlank(getAuthenticatedToken())) {
/* 229 */       result.put("accessToken", getAuthenticatedToken());
/*     */     }
/*     */     
/* 232 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getSessionToken() {
/* 240 */     if (isLoggedIn() && getSelectedProfile() != null && canPlayOnline()) {
/* 241 */       return String.format("token:%s:%s", new Object[] { getAuthenticatedToken(), getSelectedProfile().getId() });
/*     */     }
/* 243 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAuthenticatedToken() {
/* 249 */     return this.accessToken;
/*     */   }
/*     */   
/*     */   public Agent getAgent() {
/* 253 */     return this.agent;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 258 */     return "YggdrasilAuthenticationService{agent=" + this.agent + ", profiles=" + Arrays.toString((Object[])this.profiles) + ", selectedProfile=" + getSelectedProfile() + ", username='" + getUsername() + '\'' + ", isLoggedIn=" + isLoggedIn() + ", userType=" + getUserType() + ", canPlayOnline=" + canPlayOnline() + ", accessToken='" + this.accessToken + '\'' + ", clientToken='" + getAuthenticationService().getClientToken() + '\'' + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public YggdrasilAuthenticationService getAuthenticationService() {
/* 273 */     return (YggdrasilAuthenticationService)super.getAuthenticationService();
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\yggdrasil\YggdrasilUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */