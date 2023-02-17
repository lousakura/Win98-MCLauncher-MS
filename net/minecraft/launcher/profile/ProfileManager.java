/*     */ package net.minecraft.launcher.profile;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.reflect.TypeToken;
/*     */ import com.mojang.authlib.AuthenticationService;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ 
/*     */ public class ProfileManager {
/*     */   public static final String DEFAULT_PROFILE_NAME = "(Default)";
/*     */   private final Launcher launcher;
/*  25 */   private final JsonParser parser = new JsonParser();
/*     */   private final Gson gson;
/*  27 */   private final Map<String, Profile> profiles = new HashMap<String, Profile>();
/*     */   private final File profileFile;
/*  29 */   private final List<RefreshedProfilesListener> refreshedProfilesListeners = Collections.synchronizedList(new ArrayList<RefreshedProfilesListener>());
/*  30 */   private final List<UserChangedListener> userChangedListeners = Collections.synchronizedList(new ArrayList<UserChangedListener>());
/*     */   private String selectedProfile;
/*     */   private String selectedUser;
/*     */   private AuthenticationDatabase authDatabase;
/*     */   
/*     */   public ProfileManager(Launcher launcher) {
/*  36 */     this.launcher = launcher;
/*  37 */     this.profileFile = new File(launcher.getLauncher().getWorkingDirectory(), "launcher_profiles.json");
/*     */     
/*  39 */     GsonBuilder builder = new GsonBuilder();
/*  40 */     builder.registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory());
/*  41 */     builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
/*  42 */     builder.registerTypeAdapter(File.class, new FileTypeAdapter());
/*  43 */     builder.registerTypeAdapter(AuthenticationDatabase.class, new AuthenticationDatabase.Serializer(launcher));
/*  44 */     builder.registerTypeAdapter(RawProfileList.class, new RawProfileList.Serializer(launcher));
/*  45 */     builder.setPrettyPrinting();
/*  46 */     this.gson = builder.create();
/*  47 */     this.authDatabase = new AuthenticationDatabase((AuthenticationService)new YggdrasilAuthenticationService(launcher.getLauncher().getProxy(), launcher.getClientToken().toString()));
/*     */   }
/*     */   
/*     */   public void saveProfiles() throws IOException {
/*  51 */     RawProfileList rawProfileList = new RawProfileList(this.profiles, getSelectedProfile().getName(), this.selectedUser, this.launcher.getClientToken(), this.authDatabase);
/*     */     
/*  53 */     FileUtils.writeStringToFile(this.profileFile, this.gson.toJson(rawProfileList));
/*     */   }
/*     */   
/*     */   public boolean loadProfiles() throws IOException {
/*  57 */     this.profiles.clear();
/*  58 */     this.selectedProfile = null;
/*  59 */     this.selectedUser = null;
/*     */     
/*  61 */     if (this.profileFile.isFile()) {
/*  62 */       JsonObject object = this.parser.parse(FileUtils.readFileToString(this.profileFile)).getAsJsonObject();
/*  63 */       if (object.has("launcherVersion")) {
/*  64 */         JsonObject version = object.getAsJsonObject("launcherVersion");
/*  65 */         if (version.has("profilesFormat") && version.getAsJsonPrimitive("profilesFormat").getAsInt() != 1) {
/*  66 */           if (this.launcher.getUserInterface().shouldDowngradeProfiles()) {
/*  67 */             File target = new File(this.profileFile.getParentFile(), "launcher_profiles.old.json");
/*  68 */             if (target.exists()) target.delete(); 
/*  69 */             this.profileFile.renameTo(target);
/*  70 */             fireRefreshEvent();
/*  71 */             fireUserChangedEvent();
/*  72 */             return false;
/*     */           } 
/*  74 */           this.launcher.getLauncher().shutdownLauncher();
/*  75 */           System.exit(0);
/*  76 */           return false;
/*     */         } 
/*     */       } 
/*     */       
/*  80 */       if (object.has("clientToken")) this.launcher.setClientToken((UUID)this.gson.fromJson(object.get("clientToken"), UUID.class)); 
/*  81 */       RawProfileList rawProfileList = (RawProfileList)this.gson.fromJson((JsonElement)object, RawProfileList.class);
/*     */       
/*  83 */       this.profiles.putAll(rawProfileList.profiles);
/*  84 */       this.selectedProfile = rawProfileList.selectedProfile;
/*  85 */       this.selectedUser = rawProfileList.selectedUser;
/*  86 */       this.authDatabase = rawProfileList.authenticationDatabase;
/*     */       
/*  88 */       fireRefreshEvent();
/*  89 */       fireUserChangedEvent();
/*  90 */       return true;
/*     */     } 
/*  92 */     fireRefreshEvent();
/*  93 */     fireUserChangedEvent();
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void fireRefreshEvent() {
/*  99 */     for (RefreshedProfilesListener listener : Lists.newArrayList(this.refreshedProfilesListeners)) {
/* 100 */       listener.onProfilesRefreshed(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireUserChangedEvent() {
/* 105 */     for (UserChangedListener listener : Lists.newArrayList(this.userChangedListeners)) {
/* 106 */       listener.onUserChanged(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public Profile getSelectedProfile() {
/* 111 */     if (this.selectedProfile == null || !this.profiles.containsKey(this.selectedProfile)) {
/* 112 */       if (this.profiles.get("(Default)") != null) {
/* 113 */         this.selectedProfile = "(Default)";
/* 114 */       } else if (this.profiles.size() > 0) {
/* 115 */         this.selectedProfile = ((Profile)this.profiles.values().iterator().next()).getName();
/*     */       } else {
/* 117 */         this.selectedProfile = "(Default)";
/* 118 */         this.profiles.put("(Default)", new Profile(this.selectedProfile));
/*     */       } 
/*     */     }
/*     */     
/* 122 */     return this.profiles.get(this.selectedProfile);
/*     */   }
/*     */   
/*     */   public Map<String, Profile> getProfiles() {
/* 126 */     return this.profiles;
/*     */   }
/*     */   
/*     */   public void addRefreshedProfilesListener(RefreshedProfilesListener listener) {
/* 130 */     this.refreshedProfilesListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void addUserChangedListener(UserChangedListener listener) {
/* 134 */     this.userChangedListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void setSelectedProfile(String selectedProfile) {
/* 138 */     boolean update = !this.selectedProfile.equals(selectedProfile);
/* 139 */     this.selectedProfile = selectedProfile;
/*     */     
/* 141 */     if (update) {
/* 142 */       fireRefreshEvent();
/*     */     }
/*     */   }
/*     */   
/*     */   public String getSelectedUser() {
/* 147 */     return this.selectedUser;
/*     */   }
/*     */   
/*     */   public void setSelectedUser(String selectedUser) {
/* 151 */     boolean update = !Objects.equal(this.selectedUser, selectedUser);
/*     */     
/* 153 */     if (update) {
/* 154 */       this.selectedUser = selectedUser;
/* 155 */       fireUserChangedEvent();
/*     */     } 
/*     */   }
/*     */   
/*     */   public AuthenticationDatabase getAuthDatabase() {
/* 160 */     return this.authDatabase;
/*     */   }
/*     */   
/*     */   private static class RawProfileList {
/* 164 */     public Map<String, Profile> profiles = new HashMap<String, Profile>();
/*     */     public String selectedProfile;
/*     */     public String selectedUser;
/* 167 */     public UUID clientToken = UUID.randomUUID();
/*     */     public AuthenticationDatabase authenticationDatabase;
/*     */     
/*     */     private RawProfileList(Map<String, Profile> profiles, String selectedProfile, String selectedUser, UUID clientToken, AuthenticationDatabase authenticationDatabase) {
/* 171 */       this.profiles = profiles;
/* 172 */       this.selectedProfile = selectedProfile;
/* 173 */       this.selectedUser = selectedUser;
/* 174 */       this.clientToken = clientToken;
/* 175 */       this.authenticationDatabase = authenticationDatabase;
/*     */     }
/*     */     
/*     */     public static class Serializer implements JsonDeserializer<RawProfileList>, JsonSerializer<RawProfileList> {
/*     */       private final Launcher launcher;
/*     */       
/*     */       public Serializer(Launcher launcher) {
/* 182 */         this.launcher = launcher;
/*     */       }
/*     */ 
/*     */       
/*     */       public ProfileManager.RawProfileList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 187 */         JsonObject object = (JsonObject)json;
/* 188 */         Map<String, Profile> profiles = Maps.newHashMap();
/* 189 */         if (object.has("profiles")) profiles = (Map<String, Profile>)context.deserialize(object.get("profiles"), (new TypeToken<Map<String, Profile>>() {  }).getType()); 
/* 190 */         String selectedProfile = null;
/* 191 */         if (object.has("selectedProfile")) selectedProfile = object.getAsJsonPrimitive("selectedProfile").getAsString(); 
/* 192 */         UUID clientToken = UUID.randomUUID();
/* 193 */         if (object.has("clientToken")) clientToken = (UUID)context.deserialize(object.get("clientToken"), UUID.class); 
/* 194 */         AuthenticationDatabase database = new AuthenticationDatabase((AuthenticationService)new YggdrasilAuthenticationService(this.launcher.getLauncher().getProxy(), this.launcher.getClientToken().toString()));
/* 195 */         if (object.has("authenticationDatabase")) database = (AuthenticationDatabase)context.deserialize(object.get("authenticationDatabase"), AuthenticationDatabase.class); 
/* 196 */         String selectedUser = null;
/* 197 */         if (object.has("selectedUser")) {
/* 198 */           selectedUser = object.getAsJsonPrimitive("selectedUser").getAsString();
/* 199 */         } else if (selectedProfile != null && profiles.containsKey(selectedProfile) && ((Profile)profiles.get(selectedProfile)).getPlayerUUID() != null) {
/* 200 */           selectedUser = ((Profile)profiles.get(selectedProfile)).getPlayerUUID();
/* 201 */         } else if (!database.getknownUUIDs().isEmpty()) {
/* 202 */           selectedUser = database.getknownUUIDs().iterator().next();
/*     */         } 
/* 204 */         for (Profile profile : profiles.values()) {
/* 205 */           profile.setPlayerUUID(null);
/*     */         }
/* 207 */         return new ProfileManager.RawProfileList(profiles, selectedProfile, selectedUser, clientToken, database);
/*     */       }
/*     */       
/*     */       public JsonElement serialize(ProfileManager.RawProfileList src, Type typeOfSrc, JsonSerializationContext context)
/*     */       {
/* 212 */         JsonObject version = new JsonObject();
/* 213 */         version.addProperty("name", LauncherConstants.getVersionName());
/* 214 */         version.addProperty("format", Integer.valueOf(21));
/* 215 */         version.addProperty("profilesFormat", Integer.valueOf(1));
/*     */         
/* 217 */         JsonObject object = new JsonObject();
/* 218 */         object.add("profiles", context.serialize(src.profiles));
/* 219 */         object.add("selectedProfile", context.serialize(src.selectedProfile));
/* 220 */         object.add("clientToken", context.serialize(src.clientToken));
/* 221 */         object.add("authenticationDatabase", context.serialize(src.authenticationDatabase));
/* 222 */         object.add("selectedUser", context.serialize(src.selectedUser));
/* 223 */         object.add("launcherVersion", (JsonElement)version);
/*     */         
/* 225 */         return (JsonElement)object; } } } public static class Serializer implements JsonDeserializer<RawProfileList>, JsonSerializer<RawProfileList> { public JsonElement serialize(ProfileManager.RawProfileList src, Type typeOfSrc, JsonSerializationContext context) { JsonObject version = new JsonObject(); version.addProperty("name", LauncherConstants.getVersionName()); version.addProperty("format", Integer.valueOf(21)); version.addProperty("profilesFormat", Integer.valueOf(1)); JsonObject object = new JsonObject(); object.add("profiles", context.serialize(src.profiles)); object.add("selectedProfile", context.serialize(src.selectedProfile)); object.add("clientToken", context.serialize(src.clientToken)); object.add("authenticationDatabase", context.serialize(src.authenticationDatabase)); object.add("selectedUser", context.serialize(src.selectedUser)); object.add("launcherVersion", (JsonElement)version); return (JsonElement)object; }
/*     */ 
/*     */     
/*     */     private final Launcher launcher;
/*     */     
/*     */     public Serializer(Launcher launcher) {
/*     */       this.launcher = launcher;
/*     */     }
/*     */     
/*     */     public ProfileManager.RawProfileList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/*     */       JsonObject object = (JsonObject)json;
/*     */       Map<String, Profile> profiles = Maps.newHashMap();
/*     */       if (object.has("profiles"))
/*     */         profiles = (Map<String, Profile>)context.deserialize(object.get("profiles"), (new TypeToken<Map<String, Profile>>() {
/*     */             
/*     */             }).getType()); 
/*     */       String selectedProfile = null;
/*     */       if (object.has("selectedProfile"))
/*     */         selectedProfile = object.getAsJsonPrimitive("selectedProfile").getAsString(); 
/*     */       UUID clientToken = UUID.randomUUID();
/*     */       if (object.has("clientToken"))
/*     */         clientToken = (UUID)context.deserialize(object.get("clientToken"), UUID.class); 
/*     */       AuthenticationDatabase database = new AuthenticationDatabase((AuthenticationService)new YggdrasilAuthenticationService(this.launcher.getLauncher().getProxy(), this.launcher.getClientToken().toString()));
/*     */       if (object.has("authenticationDatabase"))
/*     */         database = (AuthenticationDatabase)context.deserialize(object.get("authenticationDatabase"), AuthenticationDatabase.class); 
/*     */       String selectedUser = null;
/*     */       if (object.has("selectedUser")) {
/*     */         selectedUser = object.getAsJsonPrimitive("selectedUser").getAsString();
/*     */       } else if (selectedProfile != null && profiles.containsKey(selectedProfile) && ((Profile)profiles.get(selectedProfile)).getPlayerUUID() != null) {
/*     */         selectedUser = ((Profile)profiles.get(selectedProfile)).getPlayerUUID();
/*     */       } else if (!database.getknownUUIDs().isEmpty()) {
/*     */         selectedUser = database.getknownUUIDs().iterator().next();
/*     */       } 
/*     */       for (Profile profile : profiles.values())
/*     */         profile.setPlayerUUID(null); 
/*     */       return new ProfileManager.RawProfileList(profiles, selectedProfile, selectedUser, clientToken, database);
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\profile\ProfileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */