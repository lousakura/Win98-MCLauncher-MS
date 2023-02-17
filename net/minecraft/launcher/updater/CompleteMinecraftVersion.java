/*     */ package net.minecraft.launcher.updater;
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.game.process.GameProcessBuilder;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.updater.download.Downloadable;
/*     */ import com.mojang.launcher.versions.ReleaseType;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launcher.CompatibilityRule;
/*     */ import net.minecraft.launcher.CurrentLaunchFeatureMatcher;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import org.apache.commons.lang3.text.StrSubstitutor;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class CompleteMinecraftVersion implements CompleteVersion {
/*  30 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private String inheritsFrom;
/*     */   private String id;
/*     */   private Date time;
/*     */   private Date releaseTime;
/*     */   private ReleaseType type;
/*     */   private String minecraftArguments;
/*     */   private List<Library> libraries;
/*     */   private String mainClass;
/*     */   private int minimumLauncherVersion;
/*     */   private String incompatibilityReason;
/*     */   private String assets;
/*     */   private List<CompatibilityRule> compatibilityRules;
/*     */   private String jar;
/*     */   private CompleteMinecraftVersion savableVersion;
/*     */   private transient boolean synced = false;
/*  47 */   private Map<DownloadType, DownloadInfo> downloads = Maps.newEnumMap(DownloadType.class);
/*     */   
/*     */   private AssetIndexInfo assetIndex;
/*     */   private Map<ArgumentType, List<Argument>> arguments;
/*     */   
/*     */   public CompleteMinecraftVersion() {}
/*     */   
/*     */   public CompleteMinecraftVersion(CompleteMinecraftVersion version) {
/*  55 */     this.inheritsFrom = version.inheritsFrom;
/*  56 */     this.id = version.id;
/*  57 */     this.time = version.time;
/*  58 */     this.releaseTime = version.releaseTime;
/*  59 */     this.type = version.type;
/*  60 */     this.minecraftArguments = version.minecraftArguments;
/*  61 */     this.mainClass = version.mainClass;
/*  62 */     this.minimumLauncherVersion = version.minimumLauncherVersion;
/*  63 */     this.incompatibilityReason = version.incompatibilityReason;
/*  64 */     this.assets = version.assets;
/*  65 */     this.jar = version.jar;
/*  66 */     this.downloads = version.downloads;
/*     */     
/*  68 */     if (version.libraries != null) {
/*  69 */       this.libraries = Lists.newArrayList();
/*  70 */       for (Library library : version.getLibraries()) {
/*  71 */         this.libraries.add(new Library(library));
/*     */       }
/*     */     } 
/*     */     
/*  75 */     if (version.arguments != null) {
/*  76 */       this.arguments = Maps.newEnumMap(ArgumentType.class);
/*  77 */       for (Map.Entry<ArgumentType, List<Argument>> entry : version.arguments.entrySet()) {
/*  78 */         this.arguments.put(entry.getKey(), new ArrayList<Argument>(entry.getValue()));
/*     */       }
/*     */     } 
/*     */     
/*  82 */     if (version.compatibilityRules != null) {
/*  83 */       this.compatibilityRules = Lists.newArrayList();
/*  84 */       for (CompatibilityRule compatibilityRule : version.compatibilityRules) {
/*  85 */         this.compatibilityRules.add(new CompatibilityRule(compatibilityRule));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  92 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public ReleaseType getType() {
/*  97 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getUpdatedTime() {
/* 102 */     return this.time;
/*     */   }
/*     */ 
/*     */   
/*     */   public Date getReleaseTime() {
/* 107 */     return this.releaseTime;
/*     */   }
/*     */   
/*     */   public List<Library> getLibraries() {
/* 111 */     return this.libraries;
/*     */   }
/*     */   
/*     */   public String getMainClass() {
/* 115 */     return this.mainClass;
/*     */   }
/*     */   
/*     */   public String getJar() {
/* 119 */     return (this.jar == null) ? this.id : this.jar;
/*     */   }
/*     */   
/*     */   public void setType(ReleaseType type) {
/* 123 */     if (type == null) throw new IllegalArgumentException("Release type cannot be null"); 
/* 124 */     this.type = type;
/*     */   }
/*     */   
/*     */   public Collection<Library> getRelevantLibraries(CompatibilityRule.FeatureMatcher featureMatcher) {
/* 128 */     List<Library> result = new ArrayList<Library>();
/*     */     
/* 130 */     for (Library library : this.libraries) {
/* 131 */       if (library.appliesToCurrentEnvironment(featureMatcher)) {
/* 132 */         result.add(library);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     return result;
/*     */   }
/*     */   
/*     */   public Collection<File> getClassPath(OperatingSystem os, File base, CompatibilityRule.FeatureMatcher featureMatcher) {
/* 140 */     Collection<Library> libraries = getRelevantLibraries(featureMatcher);
/* 141 */     Collection<File> result = new ArrayList<File>();
/*     */     
/* 143 */     for (Library library : libraries) {
/* 144 */       if (library.getNatives() == null) {
/* 145 */         result.add(new File(base, "libraries/" + library.getArtifactPath()));
/*     */       }
/*     */     } 
/*     */     
/* 149 */     result.add(new File(base, "versions/" + getJar() + "/" + getJar() + ".jar"));
/*     */     
/* 151 */     return result;
/*     */   }
/*     */   
/*     */   public Set<String> getRequiredFiles(OperatingSystem os) {
/* 155 */     Set<String> neededFiles = new HashSet<String>();
/*     */     
/* 157 */     for (Library library : getRelevantLibraries(createFeatureMatcher())) {
/* 158 */       if (library.getNatives() != null) {
/* 159 */         String natives = library.getNatives().get(os);
/* 160 */         if (natives != null) neededFiles.add("libraries/" + library.getArtifactPath(natives));  continue;
/*     */       } 
/* 162 */       neededFiles.add("libraries/" + library.getArtifactPath());
/*     */     } 
/*     */ 
/*     */     
/* 166 */     return neededFiles;
/*     */   }
/*     */   
/*     */   public Set<Downloadable> getRequiredDownloadables(OperatingSystem os, Proxy proxy, File targetDirectory, boolean ignoreLocalFiles) throws MalformedURLException {
/* 170 */     Set<Downloadable> neededFiles = new HashSet<Downloadable>();
/*     */     
/* 172 */     for (Library library : getRelevantLibraries(createFeatureMatcher())) {
/* 173 */       String file = null;
/* 174 */       String classifier = null;
/*     */       
/* 176 */       if (library.getNatives() != null) {
/* 177 */         classifier = library.getNatives().get(os);
/* 178 */         if (classifier != null) {
/* 179 */           file = library.getArtifactPath(classifier);
/*     */         }
/*     */       } else {
/* 182 */         file = library.getArtifactPath();
/*     */       } 
/*     */       
/* 185 */       if (file != null) {
/* 186 */         File local = new File(targetDirectory, "libraries/" + file);
/* 187 */         Downloadable download = library.createDownload(proxy, file, local, ignoreLocalFiles, classifier);
/* 188 */         if (download != null) {
/* 189 */           neededFiles.add(download);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 194 */     return neededFiles;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 199 */     return "CompleteVersion{id='" + this.id + '\'' + ", updatedTime=" + this.time + ", releasedTime=" + this.time + ", type=" + this.type + ", libraries=" + this.libraries + ", mainClass='" + this.mainClass + '\'' + ", jar='" + this.jar + '\'' + ", minimumLauncherVersion=" + this.minimumLauncherVersion + '}';
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
/*     */   public String getMinecraftArguments() {
/* 212 */     return this.minecraftArguments;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMinimumLauncherVersion() {
/* 217 */     return this.minimumLauncherVersion;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean appliesToCurrentEnvironment() {
/* 222 */     if (this.compatibilityRules == null) return true; 
/* 223 */     CompatibilityRule.Action lastAction = CompatibilityRule.Action.DISALLOW;
/*     */     
/* 225 */     for (CompatibilityRule compatibilityRule : this.compatibilityRules) {
/* 226 */       ProfileManager profileManager = Launcher.getCurrentInstance().getProfileManager();
/* 227 */       UserAuthentication auth = profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/* 228 */       CompatibilityRule.Action action = compatibilityRule.getAppliedAction((CompatibilityRule.FeatureMatcher)new CurrentLaunchFeatureMatcher(profileManager.getSelectedProfile(), this, auth));
/* 229 */       if (action != null) lastAction = action;
/*     */     
/*     */     } 
/* 232 */     return (lastAction == CompatibilityRule.Action.ALLOW);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIncompatibilityReason() {
/* 237 */     return this.incompatibilityReason;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSynced() {
/* 242 */     return this.synced;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSynced(boolean synced) {
/* 247 */     this.synced = synced;
/*     */   }
/*     */   
/*     */   public String getInheritsFrom() {
/* 251 */     return this.inheritsFrom;
/*     */   }
/*     */   
/*     */   public CompleteMinecraftVersion resolve(MinecraftVersionManager versionManager) throws IOException {
/* 255 */     return resolve(versionManager, Sets.newHashSet());
/*     */   }
/*     */   
/*     */   protected CompleteMinecraftVersion resolve(MinecraftVersionManager versionManager, Set<String> resolvedSoFar) throws IOException {
/* 259 */     if (this.inheritsFrom == null) {
/* 260 */       return this;
/*     */     }
/*     */     
/* 263 */     if (!resolvedSoFar.add(this.id)) {
/* 264 */       throw new IllegalStateException("Circular dependency detected");
/*     */     }
/*     */     
/* 267 */     VersionSyncInfo parentSync = versionManager.getVersionSyncInfo(this.inheritsFrom);
/* 268 */     CompleteMinecraftVersion parent = versionManager.getLatestCompleteVersion(parentSync).resolve(versionManager, resolvedSoFar);
/* 269 */     CompleteMinecraftVersion result = new CompleteMinecraftVersion(parent);
/*     */     
/* 271 */     if (!parentSync.isInstalled() || !parentSync.isUpToDate() || parentSync.getLatestSource() != VersionSyncInfo.VersionSource.LOCAL) {
/* 272 */       versionManager.installVersion(parent);
/*     */     }
/*     */     
/* 275 */     result.savableVersion = this;
/* 276 */     result.inheritsFrom = null;
/* 277 */     result.id = this.id;
/* 278 */     result.time = this.time;
/* 279 */     result.releaseTime = this.releaseTime;
/* 280 */     result.type = this.type;
/* 281 */     if (this.minecraftArguments != null) result.minecraftArguments = this.minecraftArguments; 
/* 282 */     if (this.mainClass != null) result.mainClass = this.mainClass; 
/* 283 */     if (this.incompatibilityReason != null) result.incompatibilityReason = this.incompatibilityReason; 
/* 284 */     if (this.assets != null) result.assets = this.assets; 
/* 285 */     if (this.jar != null) result.jar = this.jar;
/*     */     
/* 287 */     if (this.libraries != null) {
/* 288 */       List<Library> newLibraries = Lists.newArrayList();
/* 289 */       for (Library library : this.libraries) {
/* 290 */         newLibraries.add(new Library(library));
/*     */       }
/* 292 */       for (Library library : result.libraries) {
/* 293 */         newLibraries.add(library);
/*     */       }
/* 295 */       result.libraries = newLibraries;
/*     */     } 
/*     */     
/* 298 */     if (this.arguments != null) {
/* 299 */       if (result.arguments == null) result.arguments = new EnumMap<ArgumentType, List<Argument>>(ArgumentType.class); 
/* 300 */       for (Map.Entry<ArgumentType, List<Argument>> entry : this.arguments.entrySet()) {
/* 301 */         List<Argument> arguments = result.arguments.get(entry.getKey());
/* 302 */         if (arguments == null) {
/* 303 */           arguments = new ArrayList<Argument>();
/* 304 */           result.arguments.put(entry.getKey(), arguments);
/*     */         } 
/* 306 */         arguments.addAll(entry.getValue());
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 311 */     if (this.compatibilityRules != null) {
/* 312 */       for (CompatibilityRule compatibilityRule : this.compatibilityRules) {
/* 313 */         result.compatibilityRules.add(new CompatibilityRule(compatibilityRule));
/*     */       }
/*     */     }
/*     */     
/* 317 */     return result;
/*     */   }
/*     */   
/*     */   public CompleteMinecraftVersion getSavableVersion() {
/* 321 */     return (CompleteMinecraftVersion)Objects.firstNonNull(this.savableVersion, this);
/*     */   }
/*     */   
/*     */   public AbstractDownloadInfo getDownloadURL(DownloadType type) {
/* 325 */     return this.downloads.get(type);
/*     */   }
/*     */   
/*     */   public AssetIndexInfo getAssetIndex() {
/* 329 */     if (this.assetIndex == null) {
/* 330 */       this.assetIndex = new AssetIndexInfo((String)Objects.firstNonNull(this.assets, "legacy"));
/*     */     }
/* 332 */     return this.assetIndex;
/*     */   }
/*     */   
/*     */   public CompatibilityRule.FeatureMatcher createFeatureMatcher() {
/* 336 */     ProfileManager profileManager = Launcher.getCurrentInstance().getProfileManager();
/* 337 */     UserAuthentication auth = profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/* 338 */     return (CompatibilityRule.FeatureMatcher)new CurrentLaunchFeatureMatcher(profileManager.getSelectedProfile(), this, auth);
/*     */   }
/*     */   
/*     */   public void addArguments(ArgumentType type, CompatibilityRule.FeatureMatcher featureMatcher, GameProcessBuilder builder, StrSubstitutor substitutor) {
/* 342 */     if (this.arguments != null) {
/* 343 */       List<Argument> args = this.arguments.get(type);
/* 344 */       if (args != null) {
/* 345 */         for (Argument argument : args) {
/* 346 */           argument.apply(builder, featureMatcher, substitutor);
/*     */         }
/*     */       }
/* 349 */     } else if (this.minecraftArguments != null) {
/* 350 */       if (type == ArgumentType.GAME) {
/* 351 */         for (String arg : this.minecraftArguments.split(" ")) {
/* 352 */           builder.withArguments(new String[] { substitutor.replace(arg) });
/*     */         } 
/* 354 */         if (featureMatcher.hasFeature("is_demo_user", Boolean.valueOf(true))) {
/* 355 */           builder.withArguments(new String[] { "--demo" });
/*     */         }
/* 357 */         if (featureMatcher.hasFeature("has_custom_resolution", Boolean.valueOf(true))) {
/* 358 */           builder.withArguments(new String[] { "--width", substitutor.replace("${resolution_width}"), "--height", substitutor.replace("${resolution_height}") });
/*     */         }
/* 360 */       } else if (type == ArgumentType.JVM) {
/* 361 */         if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
/* 362 */           builder.withArguments(new String[] { "-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw.exe_minecraft.exe.heapdump" });
/* 363 */           if (Launcher.getCurrentInstance().usesWinTenHack()) {
/* 364 */             builder.withArguments(new String[] { "-Dos.name=Windows 10", "-Dos.version=10.0" });
/*     */           }
/* 366 */         } else if (OperatingSystem.getCurrentPlatform() == OperatingSystem.OSX) {
/* 367 */           builder.withArguments(new String[] { substitutor.replace("-Xdock:icon=${asset=icons/minecraft.icns}"), "-Xdock:name=Minecraft" });
/*     */         } 
/* 369 */         builder.withArguments(new String[] { substitutor.replace("-Djava.library.path=${natives_directory}") });
/* 370 */         builder.withArguments(new String[] { substitutor.replace("-Dminecraft.launcher.brand=${launcher_name}") });
/* 371 */         builder.withArguments(new String[] { substitutor.replace("-Dminecraft.launcher.version=${launcher_version}") });
/* 372 */         builder.withArguments(new String[] { substitutor.replace("-Dminecraft.client.jar=${primary_jar}") });
/* 373 */         builder.withArguments(new String[] { "-cp", substitutor.replace("${classpath}") });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\CompleteMinecraftVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */