/*     */ package net.minecraft.launcher;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.Gson;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.updater.DateTypeAdapter;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.updater.download.assets.AssetIndex;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import com.mojang.launcher.versions.Version;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.Proxy;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import javax.swing.JFrame;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.NonOptionArgumentSpec;
/*     */ import joptsimple.OptionException;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import net.minecraft.launcher.game.GameLaunchDispatcher;
/*     */ import net.minecraft.launcher.profile.AuthenticationDatabase;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.updater.CompleteMinecraftVersion;
/*     */ import net.minecraft.launcher.updater.Library;
/*     */ import net.minecraft.launcher.updater.VersionList;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.filefilter.AgeFileFilter;
/*     */ import org.apache.commons.io.filefilter.DirectoryFileFilter;
/*     */ import org.apache.commons.io.filefilter.FileFileFilter;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.commons.io.filefilter.PrefixFileFilter;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class Launcher {
/*  51 */   private final Gson gson = new Gson();
/*     */   
/*     */   private boolean winTenHack = false;
/*  54 */   private UUID clientToken = UUID.randomUUID();
/*     */ 
/*     */   
/*     */   static {
/*  58 */     Thread.currentThread().setContextClassLoader(Launcher.class.getClassLoader());
/*  59 */   } private static final Logger LOGGER = LogManager.getLogger(); private static Launcher INSTANCE; private final com.mojang.launcher.Launcher launcher; private final Integer bootstrapVersion; private final MinecraftUserInterface userInterface; private final ProfileManager profileManager; private final GameLaunchDispatcher launchDispatcher;
/*     */   private String requestedUser;
/*     */   
/*     */   public static Launcher getCurrentInstance() {
/*  63 */     return INSTANCE;
/*     */   }
/*     */   
/*     */   public Launcher(JFrame frame, File workingDirectory, Proxy proxy, PasswordAuthentication proxyAuth, String[] args) {
/*  67 */     this(frame, workingDirectory, proxy, proxyAuth, args, Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public Launcher(JFrame frame, File workingDirectory, Proxy proxy, PasswordAuthentication proxyAuth, String[] args, Integer bootstrapVersion) {
/*  71 */     INSTANCE = this;
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
/*     */ 
/*     */ 
/*     */     
/*  87 */     setupErrorHandling();
/*  88 */     this.bootstrapVersion = bootstrapVersion;
/*  89 */     this.userInterface = selectUserInterface(frame);
/*     */     
/*  91 */     if (bootstrapVersion.intValue() < 4) {
/*  92 */       this.userInterface.showOutdatedNotice();
/*  93 */       System.exit(0);
/*  94 */       throw new Error("Outdated bootstrap");
/*     */     } 
/*     */     
/*  97 */     LOGGER.info(this.userInterface.getTitle() + " (through bootstrap " + bootstrapVersion + ") started on " + OperatingSystem.getCurrentPlatform().getName() + "...");
/*  98 */     LOGGER.info("Current time is " + DateFormat.getDateTimeInstance(2, 2, Locale.US).format(new Date()));
/*     */     
/* 100 */     if (!OperatingSystem.getCurrentPlatform().isSupported()) {
/* 101 */       LOGGER.fatal("This operating system is unknown or unsupported, we cannot guarantee that the game will launch successfully.");
/*     */     }
/* 103 */     LOGGER.info("System.getProperty('os.name') == '" + System.getProperty("os.name") + "'");
/* 104 */     LOGGER.info("System.getProperty('os.version') == '" + System.getProperty("os.version") + "'");
/* 105 */     LOGGER.info("System.getProperty('os.arch') == '" + System.getProperty("os.arch") + "'");
/* 106 */     LOGGER.info("System.getProperty('java.version') == '" + System.getProperty("java.version") + "'");
/* 107 */     LOGGER.info("System.getProperty('java.vendor') == '" + System.getProperty("java.vendor") + "'");
/* 108 */     LOGGER.info("System.getProperty('sun.arch.data.model') == '" + System.getProperty("sun.arch.data.model") + "'");
/* 109 */     LOGGER.info("proxy == " + proxy);
/*     */     
/* 111 */     this.launchDispatcher = new GameLaunchDispatcher(this, processArgs(args));
/* 112 */     this.launcher = new com.mojang.launcher.Launcher(this.userInterface, workingDirectory, proxy, proxyAuth, (VersionManager)new MinecraftVersionManager((VersionList)new LocalVersionList(workingDirectory), (VersionList)new RemoteVersionList(LauncherConstants.PROPERTIES.getVersionManifest(), proxy)), Agent.MINECRAFT, (ReleaseTypeFactory)MinecraftReleaseTypeFactory.instance(), 21);
/* 113 */     this.profileManager = new ProfileManager(this);
/* 114 */     ((SwingUserInterface)this.userInterface).initializeFrame();
/*     */     
/* 116 */     refreshVersionsAndProfiles();
/*     */   }
/*     */   
/*     */   public File findNativeLauncher() {
/* 120 */     String programData = System.getenv("ProgramData");
/* 121 */     if (programData == null) programData = System.getenv("ALLUSERSPROFILE"); 
/* 122 */     if (programData != null) {
/* 123 */       File shortcut = new File(programData, "Microsoft\\Windows\\Start Menu\\Programs\\Minecraft\\Minecraft.lnk");
/* 124 */       if (shortcut.isFile()) {
/* 125 */         return shortcut;
/*     */       }
/*     */     } 
/* 128 */     return null;
/*     */   }
/*     */   
/*     */   public void runNativeLauncher(File executable, String[] args) {
/* 132 */     ProcessBuilder pb = new ProcessBuilder(new String[] { "cmd", "/c", executable.getAbsolutePath() });
/*     */     try {
/* 134 */       pb.start();
/* 135 */     } catch (IOException e) {
/* 136 */       e.printStackTrace();
/*     */     } 
/* 138 */     System.exit(0);
/*     */   }
/*     */   
/*     */   private void setupErrorHandling() {
/* 142 */     Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler()
/*     */         {
/*     */           public void uncaughtException(Thread t, Throwable e) {
/* 145 */             Launcher.LOGGER.fatal("Unhandled exception in thread " + t, e);
/*     */           }
/*     */         });
/*     */   }
/*     */   private String[] processArgs(String[] args) {
/*     */     OptionSet optionSet;
/* 151 */     OptionParser optionParser = new OptionParser();
/* 152 */     optionParser.allowsUnrecognizedOptions();
/*     */     
/* 154 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec = optionParser.accepts("user").withRequiredArg().ofType(String.class);
/* 155 */     NonOptionArgumentSpec nonOptionArgumentSpec = optionParser.nonOptions();
/*     */ 
/*     */     
/*     */     try {
/* 159 */       optionSet = optionParser.parse(args);
/* 160 */     } catch (OptionException e) {
/* 161 */       return args;
/*     */     } 
/*     */     
/* 164 */     if (optionSet.has((OptionSpec)argumentAcceptingOptionSpec)) {
/* 165 */       this.requestedUser = (String)optionSet.valueOf((OptionSpec)argumentAcceptingOptionSpec);
/*     */     }
/*     */     
/* 168 */     List<String> remainingOptions = optionSet.valuesOf((OptionSpec)nonOptionArgumentSpec);
/* 169 */     return remainingOptions.<String>toArray(new String[remainingOptions.size()]);
/*     */   }
/*     */   
/*     */   public void refreshVersionsAndProfiles() {
/* 173 */     getLauncher().getVersionManager().getExecutorService().submit(new Runnable()
/*     */         {
/*     */           public void run() {
/*     */             try {
/* 177 */               Launcher.this.getLauncher().getVersionManager().refreshVersions();
/* 178 */             } catch (Throwable e) {
/* 179 */               Launcher.LOGGER.error("Unexpected exception refreshing version list", e);
/*     */             } 
/*     */             
/*     */             try {
/* 183 */               Launcher.this.profileManager.loadProfiles();
/* 184 */               Launcher.LOGGER.info("Loaded " + Launcher.this.profileManager.getProfiles().size() + " profile(s); selected '" + Launcher.this.profileManager.getSelectedProfile().getName() + "'");
/* 185 */             } catch (Throwable e) {
/* 186 */               Launcher.LOGGER.error("Unexpected exception refreshing profile list", e);
/*     */             } 
/*     */             
/* 189 */             if (Launcher.this.requestedUser != null) {
/* 190 */               AuthenticationDatabase authDatabase = Launcher.this.profileManager.getAuthDatabase();
/* 191 */               boolean loggedIn = false;
/*     */               
/*     */               try {
/* 194 */                 String uuid = UUIDTypeAdapter.fromUUID(UUIDTypeAdapter.fromString(Launcher.this.requestedUser));
/* 195 */                 UserAuthentication auth = authDatabase.getByUUID(uuid);
/*     */                 
/* 197 */                 if (auth != null) {
/* 198 */                   Launcher.this.profileManager.setSelectedUser(uuid);
/* 199 */                   loggedIn = true;
/*     */                 } 
/* 201 */               } catch (RuntimeException runtimeException) {}
/*     */               
/* 203 */               if (!loggedIn && authDatabase.getByName(Launcher.this.requestedUser) != null) {
/* 204 */                 UserAuthentication auth = authDatabase.getByName(Launcher.this.requestedUser);
/* 205 */                 if (auth.getSelectedProfile() != null) {
/* 206 */                   Launcher.this.profileManager.setSelectedUser(UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId()));
/*     */                 } else {
/* 208 */                   Launcher.this.profileManager.setSelectedUser("demo-" + auth.getUserID());
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 213 */             Launcher.this.ensureLoggedIn();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private MinecraftUserInterface selectUserInterface(JFrame frame) {
/* 219 */     return new SwingUserInterface(this, frame);
/*     */   }
/*     */   
/*     */   public com.mojang.launcher.Launcher getLauncher() {
/* 223 */     return this.launcher;
/*     */   }
/*     */   
/*     */   public MinecraftUserInterface getUserInterface() {
/* 227 */     return this.userInterface;
/*     */   }
/*     */   
/*     */   public Integer getBootstrapVersion() {
/* 231 */     return this.bootstrapVersion;
/*     */   }
/*     */   
/*     */   public void ensureLoggedIn() {
/* 235 */     UserAuthentication auth = this.profileManager.getAuthDatabase().getByUUID(this.profileManager.getSelectedUser());
/*     */     
/* 237 */     if (auth == null) {
/* 238 */       getUserInterface().showLoginPrompt();
/* 239 */     } else if (!auth.isLoggedIn()) {
/* 240 */       if (auth.canLogIn()) {
/*     */         try {
/* 242 */           auth.logIn();
/*     */           try {
/* 244 */             this.profileManager.saveProfiles();
/* 245 */           } catch (IOException e) {
/* 246 */             LOGGER.error("Couldn't save profiles after refreshing auth!", e);
/*     */           } 
/* 248 */           this.profileManager.fireRefreshEvent();
/* 249 */         } catch (AuthenticationException e) {
/* 250 */           LOGGER.error("Exception whilst logging into profile", (Throwable)e);
/* 251 */           getUserInterface().showLoginPrompt();
/*     */         } 
/*     */       } else {
/* 254 */         getUserInterface().showLoginPrompt();
/*     */       } 
/* 256 */     } else if (!auth.canPlayOnline()) {
/*     */       try {
/* 258 */         LOGGER.info("Refreshing auth...");
/* 259 */         auth.logIn();
/*     */         try {
/* 261 */           this.profileManager.saveProfiles();
/* 262 */         } catch (IOException e) {
/* 263 */           LOGGER.error("Couldn't save profiles after refreshing auth!", e);
/*     */         } 
/* 265 */         this.profileManager.fireRefreshEvent();
/* 266 */       } catch (InvalidCredentialsException e) {
/* 267 */         LOGGER.error("Exception whilst logging into profile", (Throwable)e);
/* 268 */         getUserInterface().showLoginPrompt();
/* 269 */       } catch (AuthenticationException e) {
/* 270 */         LOGGER.error("Exception whilst logging into profile", (Throwable)e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public UUID getClientToken() {
/* 276 */     return this.clientToken;
/*     */   }
/*     */   
/*     */   public void setClientToken(UUID clientToken) {
/* 280 */     this.clientToken = clientToken;
/*     */   }
/*     */   
/*     */   public void cleanupOrphanedAssets() throws IOException {
/* 284 */     File assetsDir = new File(getLauncher().getWorkingDirectory(), "assets");
/* 285 */     File indexDir = new File(assetsDir, "indexes");
/* 286 */     File objectsDir = new File(assetsDir, "objects");
/* 287 */     Set<String> referencedObjects = Sets.newHashSet();
/*     */     
/* 289 */     if (!objectsDir.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 293 */     for (VersionSyncInfo syncInfo : getLauncher().getVersionManager().getInstalledVersions()) {
/* 294 */       if (syncInfo.getLocalVersion() instanceof CompleteMinecraftVersion) {
/* 295 */         CompleteMinecraftVersion version = (CompleteMinecraftVersion)syncInfo.getLocalVersion();
/* 296 */         String assetVersion = version.getAssetIndex().getId();
/* 297 */         File indexFile = new File(indexDir, assetVersion + ".json");
/* 298 */         AssetIndex index = (AssetIndex)this.gson.fromJson(FileUtils.readFileToString(indexFile, Charsets.UTF_8), AssetIndex.class);
/* 299 */         for (AssetIndex.AssetObject object : index.getUniqueObjects().keySet()) {
/* 300 */           referencedObjects.add(object.getHash().toLowerCase());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 305 */     File[] directories = objectsDir.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
/* 306 */     if (directories != null) {
/* 307 */       for (File directory : directories) {
/* 308 */         File[] files = directory.listFiles((FileFilter)FileFileFilter.FILE);
/* 309 */         if (files != null) {
/* 310 */           for (File file : files) {
/* 311 */             if (!referencedObjects.contains(file.getName().toLowerCase())) {
/* 312 */               LOGGER.info("Cleaning up orphaned object {}", new Object[] { file.getName() });
/* 313 */               FileUtils.deleteQuietly(file);
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 320 */     deleteEmptyDirectories(objectsDir);
/*     */   }
/*     */   
/*     */   public void cleanupOrphanedLibraries() throws IOException {
/* 324 */     File librariesDir = new File(getLauncher().getWorkingDirectory(), "libraries");
/* 325 */     Set<File> referencedLibraries = Sets.newHashSet();
/*     */     
/* 327 */     if (!librariesDir.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 331 */     for (VersionSyncInfo syncInfo : getLauncher().getVersionManager().getInstalledVersions()) {
/* 332 */       if (syncInfo.getLocalVersion() instanceof CompleteMinecraftVersion) {
/* 333 */         CompleteMinecraftVersion version = (CompleteMinecraftVersion)syncInfo.getLocalVersion();
/* 334 */         for (Library library : version.getRelevantLibraries(version.createFeatureMatcher())) {
/* 335 */           String file = null;
/*     */           
/* 337 */           if (library.getNatives() != null) {
/* 338 */             String natives = (String)library.getNatives().get(OperatingSystem.getCurrentPlatform());
/* 339 */             if (natives != null) {
/* 340 */               file = library.getArtifactPath(natives);
/*     */             }
/*     */           } else {
/* 343 */             file = library.getArtifactPath();
/*     */           } 
/*     */           
/* 346 */           if (file != null) {
/* 347 */             referencedLibraries.add(new File(librariesDir, file));
/* 348 */             referencedLibraries.add(new File(librariesDir, file + ".sha"));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     Collection<File> libraries = FileUtils.listFiles(librariesDir, TrueFileFilter.TRUE, TrueFileFilter.TRUE);
/* 355 */     if (libraries != null) {
/* 356 */       for (File file : libraries) {
/* 357 */         if (!referencedLibraries.contains(file)) {
/* 358 */           LOGGER.info("Cleaning up orphaned library {}", new Object[] { file });
/* 359 */           FileUtils.deleteQuietly(file);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 364 */     deleteEmptyDirectories(librariesDir);
/*     */   }
/*     */   
/*     */   public void cleanupOldSkins() {
/* 368 */     File assetsDir = new File(getLauncher().getWorkingDirectory(), "assets");
/* 369 */     File skinsDir = new File(assetsDir, "skins");
/*     */     
/* 371 */     if (!skinsDir.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 375 */     Collection<File> files = FileUtils.listFiles(skinsDir, (IOFileFilter)new AgeFileFilter(System.currentTimeMillis() - 604800000L), TrueFileFilter.TRUE);
/* 376 */     if (files != null) {
/* 377 */       for (File file : files) {
/* 378 */         LOGGER.info("Cleaning up old skin {}", new Object[] { file.getName() });
/* 379 */         FileUtils.deleteQuietly(file);
/*     */       } 
/*     */     }
/*     */     
/* 383 */     deleteEmptyDirectories(skinsDir);
/*     */   }
/*     */   
/*     */   public void cleanupOldVirtuals() throws IOException {
/* 387 */     File assetsDir = new File(getLauncher().getWorkingDirectory(), "assets");
/* 388 */     File virtualsDir = new File(assetsDir, "virtual");
/* 389 */     DateTypeAdapter dateAdapter = new DateTypeAdapter();
/* 390 */     Calendar calendar = Calendar.getInstance();
/* 391 */     calendar.add(5, -5);
/* 392 */     Date cutoff = calendar.getTime();
/*     */     
/* 394 */     if (!virtualsDir.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 398 */     File[] directories = virtualsDir.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
/* 399 */     if (directories != null) {
/* 400 */       for (File directory : directories) {
/* 401 */         File lastUsedFile = new File(directory, ".lastused");
/*     */         
/* 403 */         if (lastUsedFile.isFile()) {
/* 404 */           Date lastUsed = dateAdapter.deserializeToDate(FileUtils.readFileToString(lastUsedFile));
/* 405 */           if (cutoff.after(lastUsed)) {
/* 406 */             LOGGER.info("Cleaning up old virtual directory {}", new Object[] { directory });
/* 407 */             FileUtils.deleteQuietly(directory);
/*     */           } 
/*     */         } else {
/* 410 */           LOGGER.info("Cleaning up strange virtual directory {}", new Object[] { directory });
/* 411 */           FileUtils.deleteQuietly(directory);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 416 */     deleteEmptyDirectories(virtualsDir);
/*     */   }
/*     */   
/*     */   public void cleanupOldNatives() {
/* 420 */     File root = new File(this.launcher.getWorkingDirectory(), "versions/");
/* 421 */     LOGGER.info("Looking for old natives & assets to clean up...");
/* 422 */     AgeFileFilter ageFileFilter = new AgeFileFilter(System.currentTimeMillis() - 3600000L);
/*     */     
/* 424 */     if (!root.isDirectory()) {
/*     */       return;
/*     */     }
/*     */     
/* 428 */     File[] versions = root.listFiles((FileFilter)DirectoryFileFilter.DIRECTORY);
/* 429 */     if (versions != null) {
/* 430 */       for (File version : versions) {
/* 431 */         File[] files = version.listFiles((FileFilter)FileFilterUtils.and(new IOFileFilter[] { (IOFileFilter)new PrefixFileFilter(version.getName() + "-natives-"), (IOFileFilter)ageFileFilter }));
/* 432 */         if (files != null) {
/* 433 */           for (File folder : files) {
/* 434 */             LOGGER.debug("Deleting " + folder);
/*     */             
/* 436 */             FileUtils.deleteQuietly(folder);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void cleanupOrphanedVersions() {
/* 444 */     LOGGER.info("Looking for orphaned versions to clean up...");
/* 445 */     Set<String> referencedVersions = Sets.newHashSet();
/*     */     
/* 447 */     for (Profile profile : getProfileManager().getProfiles().values()) {
/* 448 */       String lastVersionId = profile.getLastVersionId();
/* 449 */       VersionSyncInfo syncInfo = null;
/*     */       
/* 451 */       if (lastVersionId != null) {
/* 452 */         syncInfo = getLauncher().getVersionManager().getVersionSyncInfo(lastVersionId);
/*     */       }
/*     */       
/* 455 */       if (syncInfo == null || syncInfo.getLatestVersion() == null) {
/* 456 */         syncInfo = getLauncher().getVersionManager().getVersions(profile.getVersionFilter()).get(0);
/*     */       }
/*     */       
/* 459 */       if (syncInfo != null) {
/* 460 */         Version version = syncInfo.getLatestVersion();
/* 461 */         referencedVersions.add(version.getId());
/*     */         
/* 463 */         if (version instanceof CompleteMinecraftVersion) {
/* 464 */           CompleteMinecraftVersion completeMinecraftVersion = (CompleteMinecraftVersion)version;
/* 465 */           referencedVersions.add(completeMinecraftVersion.getInheritsFrom());
/* 466 */           referencedVersions.add(completeMinecraftVersion.getJar());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 471 */     Calendar calendar = Calendar.getInstance();
/* 472 */     calendar.add(5, -7);
/* 473 */     Date cutoff = calendar.getTime();
/*     */     
/* 475 */     for (VersionSyncInfo versionSyncInfo : getLauncher().getVersionManager().getInstalledVersions()) {
/* 476 */       if (versionSyncInfo.getLocalVersion() instanceof CompleteMinecraftVersion) {
/* 477 */         CompleteVersion version = (CompleteVersion)versionSyncInfo.getLocalVersion();
/*     */         
/* 479 */         if (!referencedVersions.contains(version.getId()) && version.getType() == MinecraftReleaseType.SNAPSHOT) {
/* 480 */           if (versionSyncInfo.isOnRemote()) {
/* 481 */             LOGGER.info("Deleting orphaned version {} because it's a snapshot available on remote", new Object[] { version.getId() });
/*     */             try {
/* 483 */               getLauncher().getVersionManager().uninstallVersion(version);
/* 484 */             } catch (IOException e) {
/* 485 */               LOGGER.warn("Couldn't uninstall version " + version.getId(), e);
/*     */             }  continue;
/* 487 */           }  if (version.getUpdatedTime().before(cutoff)) {
/* 488 */             LOGGER.info("Deleting orphaned version {} because it's an unsupported old snapshot", new Object[] { version.getId() });
/*     */             try {
/* 490 */               getLauncher().getVersionManager().uninstallVersion(version);
/* 491 */             } catch (IOException e) {
/* 492 */               LOGGER.warn("Couldn't uninstall version " + version.getId(), e);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Collection<File> listEmptyDirectories(File directory) {
/* 501 */     List<File> result = Lists.newArrayList();
/* 502 */     File[] files = directory.listFiles();
/*     */     
/* 504 */     if (files != null) {
/* 505 */       for (File file : files) {
/* 506 */         if (file.isDirectory()) {
/* 507 */           File[] subFiles = file.listFiles();
/*     */           
/* 509 */           if (subFiles == null || subFiles.length == 0) {
/* 510 */             result.add(file);
/*     */           } else {
/* 512 */             result.addAll(listEmptyDirectories(file));
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 518 */     return result;
/*     */   }
/*     */   
/*     */   private static void deleteEmptyDirectories(File directory) {
/*     */     while (true) {
/* 523 */       Collection<File> files = listEmptyDirectories(directory);
/* 524 */       if (files.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 528 */       for (File file : files) {
/* 529 */         if (FileUtils.deleteQuietly(file)) {
/* 530 */           LOGGER.info("Deleted empty directory {}", new Object[] { file });
/*     */           continue;
/*     */         } 
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void performCleanups() throws IOException {
/* 539 */     cleanupOrphanedVersions();
/* 540 */     cleanupOrphanedAssets();
/* 541 */     cleanupOldSkins();
/* 542 */     cleanupOldNatives();
/* 543 */     cleanupOldVirtuals();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfileManager getProfileManager() {
/* 550 */     return this.profileManager;
/*     */   }
/*     */   
/*     */   public GameLaunchDispatcher getLaunchDispatcher() {
/* 554 */     return this.launchDispatcher;
/*     */   }
/*     */   
/*     */   public boolean usesWinTenHack() {
/* 558 */     return this.winTenHack;
/*     */   }
/*     */   
/*     */   public void setWinTenHack() {
/* 562 */     this.winTenHack = true;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\Launcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */