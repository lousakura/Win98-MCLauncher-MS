/*     */ package net.minecraft.launcher.game;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.authlib.UserType;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import com.mojang.launcher.Launcher;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.game.GameInstanceStatus;
/*     */ import com.mojang.launcher.game.process.GameProcess;
/*     */ import com.mojang.launcher.game.process.GameProcessBuilder;
/*     */ import com.mojang.launcher.game.process.GameProcessFactory;
/*     */ import com.mojang.launcher.game.process.GameProcessRunnable;
/*     */ import com.mojang.launcher.game.runner.AbstractGameRunner;
/*     */ import com.mojang.launcher.updater.DateTypeAdapter;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.updater.download.Downloadable;
/*     */ import com.mojang.launcher.updater.download.assets.AssetIndex;
/*     */ import com.mojang.launcher.versions.ExtractRules;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.PasswordAuthentication;
/*     */ import java.net.Proxy;
/*     */ import java.util.Collection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Map;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import net.minecraft.launcher.CompatibilityRule;
/*     */ import net.minecraft.launcher.CurrentLaunchFeatureMatcher;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.LauncherVisibilityRule;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.updater.ArgumentType;
/*     */ import net.minecraft.launcher.updater.CompleteMinecraftVersion;
/*     */ import net.minecraft.launcher.updater.Library;
/*     */ import org.apache.commons.io.Charsets;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.filefilter.FileFilterUtils;
/*     */ import org.apache.commons.io.filefilter.IOFileFilter;
/*     */ import org.apache.commons.io.filefilter.TrueFileFilter;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.text.StrSubstitutor;
/*     */ 
/*     */ public class MinecraftGameRunner extends AbstractGameRunner implements GameProcessRunnable {
/*  54 */   private final Gson gson = new Gson(); private static final String CRASH_IDENTIFIER_MAGIC = "#@!@#";
/*  55 */   private final DateTypeAdapter dateAdapter = new DateTypeAdapter();
/*     */   private final Launcher minecraftLauncher;
/*     */   private final String[] additionalLaunchArgs;
/*  58 */   private final GameProcessFactory processFactory = (GameProcessFactory)new DirectGameProcessFactory();
/*     */   private File nativeDir;
/*  60 */   private LauncherVisibilityRule visibilityRule = LauncherVisibilityRule.CLOSE_LAUNCHER;
/*     */   private UserAuthentication auth;
/*     */   private Profile selectedProfile;
/*     */   
/*     */   public MinecraftGameRunner(Launcher minecraftLauncher, String[] additionalLaunchArgs) {
/*  65 */     this.minecraftLauncher = minecraftLauncher;
/*  66 */     this.additionalLaunchArgs = additionalLaunchArgs;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setStatus(GameInstanceStatus status) {
/*  71 */     synchronized (this.lock) {
/*  72 */       if (this.nativeDir != null && status == GameInstanceStatus.IDLE) {
/*  73 */         LOGGER.info("Deleting " + this.nativeDir);
/*  74 */         if (!this.nativeDir.isDirectory() || FileUtils.deleteQuietly(this.nativeDir)) {
/*  75 */           this.nativeDir = null;
/*     */         } else {
/*  77 */           LOGGER.warn("Couldn't delete " + this.nativeDir + " - scheduling for deletion upon exit");
/*     */           try {
/*  79 */             FileUtils.forceDeleteOnExit(this.nativeDir);
/*  80 */           } catch (Throwable throwable) {}
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       super.setStatus(status);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Launcher getLauncher() {
/*  90 */     return this.minecraftLauncher.getLauncher();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void downloadRequiredFiles(VersionSyncInfo syncInfo) {
/*  95 */     migrateOldAssets();
/*  96 */     super.downloadRequiredFiles(syncInfo);
/*     */   }
/*     */   
/*     */   protected void launchGame() throws IOException {
/*     */     File assetsDir;
/* 101 */     LOGGER.info("Launching game");
/* 102 */     this.selectedProfile = this.minecraftLauncher.getProfileManager().getSelectedProfile();
/* 103 */     this.auth = this.minecraftLauncher.getProfileManager().getAuthDatabase().getByUUID(this.minecraftLauncher.getProfileManager().getSelectedUser());
/*     */     
/* 105 */     if (getVersion() == null) {
/* 106 */       LOGGER.error("Aborting launch; version is null?");
/*     */       
/*     */       return;
/*     */     } 
/* 110 */     this.nativeDir = new File(getLauncher().getWorkingDirectory(), "versions/" + getVersion().getId() + "/" + getVersion().getId() + "-natives-" + System.nanoTime());
/* 111 */     if (!this.nativeDir.isDirectory()) this.nativeDir.mkdirs(); 
/* 112 */     LOGGER.info("Unpacking natives to " + this.nativeDir);
/*     */     
/*     */     try {
/* 115 */       unpackNatives(this.nativeDir);
/* 116 */     } catch (IOException e) {
/* 117 */       LOGGER.error("Couldn't unpack natives!", e);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*     */     try {
/* 123 */       assetsDir = reconstructAssets();
/* 124 */     } catch (IOException e) {
/* 125 */       LOGGER.error("Couldn't unpack natives!", e);
/*     */       
/*     */       return;
/*     */     } 
/* 129 */     File gameDirectory = (this.selectedProfile.getGameDir() == null) ? getLauncher().getWorkingDirectory() : this.selectedProfile.getGameDir();
/* 130 */     LOGGER.info("Launching in " + gameDirectory);
/*     */     
/* 132 */     if (!gameDirectory.exists()) {
/* 133 */       if (!gameDirectory.mkdirs()) {
/* 134 */         LOGGER.error("Aborting launch; couldn't create game directory");
/*     */         return;
/*     */       } 
/* 137 */     } else if (!gameDirectory.isDirectory()) {
/* 138 */       LOGGER.error("Aborting launch; game directory is not actually a directory");
/*     */       
/*     */       return;
/*     */     } 
/* 142 */     File serverResourcePacksDir = new File(gameDirectory, "server-resource-packs");
/* 143 */     if (!serverResourcePacksDir.exists()) {
/* 144 */       serverResourcePacksDir.mkdirs();
/*     */     }
/*     */     
/* 147 */     GameProcessBuilder processBuilder = new GameProcessBuilder((String)Objects.firstNonNull(this.selectedProfile.getJavaPath(), OperatingSystem.getCurrentPlatform().getJavaDir()));
/* 148 */     processBuilder.withSysOutFilter(new Predicate<String>()
/*     */         {
/*     */           public boolean apply(String input) {
/* 151 */             return input.contains("#@!@#");
/*     */           }
/*     */         });
/* 154 */     processBuilder.directory(gameDirectory);
/* 155 */     processBuilder.withLogProcessor(this.minecraftLauncher.getUserInterface().showGameOutputTab(this));
/*     */ 
/*     */     
/* 158 */     String profileArgs = this.selectedProfile.getJavaArgs();
/*     */     
/* 160 */     if (profileArgs != null) {
/* 161 */       processBuilder.withArguments(profileArgs.split(" "));
/*     */     } else {
/*     */       
/* 164 */       boolean is32Bit = "32".equals(System.getProperty("sun.arch.data.model"));
/* 165 */       String defaultArgument = is32Bit ? "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M" : "-Xmx1G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
/* 166 */       processBuilder.withArguments(defaultArgument.split(" "));
/*     */     } 
/*     */     
/* 169 */     CompatibilityRule.FeatureMatcher featureMatcher = createFeatureMatcher();
/* 170 */     StrSubstitutor argumentsSubstitutor = createArgumentsSubstitutor(getVersion(), this.selectedProfile, gameDirectory, assetsDir, this.auth);
/*     */     
/* 172 */     getVersion().addArguments(ArgumentType.JVM, featureMatcher, processBuilder, argumentsSubstitutor);
/* 173 */     processBuilder.withArguments(new String[] { getVersion().getMainClass() });
/*     */     
/* 175 */     LOGGER.info("Half command: " + StringUtils.join(processBuilder.getFullCommands(), " "));
/*     */     
/* 177 */     getVersion().addArguments(ArgumentType.GAME, featureMatcher, processBuilder, argumentsSubstitutor);
/*     */     
/* 179 */     Proxy proxy = getLauncher().getProxy();
/* 180 */     PasswordAuthentication proxyAuth = getLauncher().getProxyAuth();
/* 181 */     if (!proxy.equals(Proxy.NO_PROXY)) {
/* 182 */       InetSocketAddress address = (InetSocketAddress)proxy.address();
/* 183 */       processBuilder.withArguments(new String[] { "--proxyHost", address.getHostName() });
/* 184 */       processBuilder.withArguments(new String[] { "--proxyPort", Integer.toString(address.getPort()) });
/* 185 */       if (proxyAuth != null) {
/* 186 */         processBuilder.withArguments(new String[] { "--proxyUser", proxyAuth.getUserName() });
/* 187 */         processBuilder.withArguments(new String[] { "--proxyPass", new String(proxyAuth.getPassword()) });
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 192 */     processBuilder.withArguments(this.additionalLaunchArgs);
/*     */     
/*     */     try {
/* 195 */       LOGGER.debug("Running " + StringUtils.join(processBuilder.getFullCommands(), " "));
/* 196 */       GameProcess process = this.processFactory.startGame(processBuilder);
/* 197 */       process.setExitRunnable(this);
/*     */       
/* 199 */       setStatus(GameInstanceStatus.PLAYING);
/* 200 */       if (this.visibilityRule != LauncherVisibilityRule.DO_NOTHING) {
/* 201 */         this.minecraftLauncher.getUserInterface().setVisible(false);
/*     */       }
/* 203 */     } catch (IOException e) {
/* 204 */       LOGGER.error("Couldn't launch game", e);
/* 205 */       setStatus(GameInstanceStatus.IDLE);
/*     */       
/*     */       return;
/*     */     } 
/* 209 */     this.minecraftLauncher.performCleanups();
/*     */   }
/*     */   
/*     */   protected CompleteMinecraftVersion getVersion() {
/* 213 */     return (CompleteMinecraftVersion)this.version;
/*     */   }
/*     */   
/*     */   private AssetIndex getAssetIndex() throws IOException {
/* 217 */     String assetVersion = getVersion().getAssetIndex().getId();
/* 218 */     File indexFile = new File(new File(getAssetsDir(), "indexes"), assetVersion + ".json");
/* 219 */     return (AssetIndex)this.gson.fromJson(FileUtils.readFileToString(indexFile, Charsets.UTF_8), AssetIndex.class);
/*     */   }
/*     */   
/*     */   private File getAssetsDir() {
/* 223 */     return new File(getLauncher().getWorkingDirectory(), "assets");
/*     */   }
/*     */   
/*     */   private File reconstructAssets() throws IOException {
/* 227 */     File assetsDir = getAssetsDir();
/* 228 */     File indexDir = new File(assetsDir, "indexes");
/* 229 */     File objectDir = new File(assetsDir, "objects");
/* 230 */     String assetVersion = getVersion().getAssetIndex().getId();
/* 231 */     File indexFile = new File(indexDir, assetVersion + ".json");
/* 232 */     File virtualRoot = new File(new File(assetsDir, "virtual"), assetVersion);
/*     */     
/* 234 */     if (!indexFile.isFile()) {
/* 235 */       LOGGER.warn("No assets index file " + virtualRoot + "; can't reconstruct assets");
/* 236 */       return virtualRoot;
/*     */     } 
/*     */     
/* 239 */     AssetIndex index = (AssetIndex)this.gson.fromJson(FileUtils.readFileToString(indexFile, Charsets.UTF_8), AssetIndex.class);
/*     */     
/* 241 */     if (index.isVirtual()) {
/* 242 */       LOGGER.info("Reconstructing virtual assets folder at " + virtualRoot);
/* 243 */       for (Map.Entry<String, AssetIndex.AssetObject> entry : (Iterable<Map.Entry<String, AssetIndex.AssetObject>>)index.getFileMap().entrySet()) {
/* 244 */         File target = new File(virtualRoot, entry.getKey());
/* 245 */         File original = new File(new File(objectDir, ((AssetIndex.AssetObject)entry.getValue()).getHash().substring(0, 2)), ((AssetIndex.AssetObject)entry.getValue()).getHash());
/*     */         
/* 247 */         if (!target.isFile()) {
/* 248 */           FileUtils.copyFile(original, target, false);
/*     */         }
/*     */       } 
/*     */       
/* 252 */       FileUtils.writeStringToFile(new File(virtualRoot, ".lastused"), this.dateAdapter.serializeToString(new Date()));
/*     */     } 
/*     */     
/* 255 */     return virtualRoot;
/*     */   }
/*     */   
/*     */   public StrSubstitutor createArgumentsSubstitutor(CompleteMinecraftVersion version, Profile selectedProfile, File gameDirectory, File assetsDirectory, UserAuthentication authentication) {
/* 259 */     Map<String, String> map = new HashMap<String, String>();
/*     */     
/* 261 */     map.put("auth_access_token", authentication.getAuthenticatedToken());
/* 262 */     map.put("user_properties", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new LegacyPropertyMapSerializer()).create().toJson(authentication.getUserProperties()));
/* 263 */     map.put("user_property_map", (new GsonBuilder()).registerTypeAdapter(PropertyMap.class, new PropertyMap.Serializer()).create().toJson(authentication.getUserProperties()));
/*     */     
/* 265 */     if (authentication.isLoggedIn() && authentication.canPlayOnline()) {
/* 266 */       if (authentication instanceof com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication) {
/* 267 */         map.put("auth_session", String.format("token:%s:%s", new Object[] { authentication.getAuthenticatedToken(), UUIDTypeAdapter.fromUUID(authentication.getSelectedProfile().getId()) }));
/*     */       } else {
/* 269 */         map.put("auth_session", authentication.getAuthenticatedToken());
/*     */       } 
/*     */     } else {
/*     */       
/* 273 */       map.put("auth_session", "-");
/*     */     } 
/*     */     
/* 276 */     if (authentication.getSelectedProfile() != null) {
/* 277 */       map.put("auth_player_name", authentication.getSelectedProfile().getName());
/* 278 */       map.put("auth_uuid", UUIDTypeAdapter.fromUUID(authentication.getSelectedProfile().getId()));
/* 279 */       map.put("user_type", authentication.getUserType().getName());
/*     */     } else {
/* 281 */       map.put("auth_player_name", "Player");
/* 282 */       map.put("auth_uuid", (new UUID(0L, 0L)).toString());
/* 283 */       map.put("user_type", UserType.LEGACY.getName());
/*     */     } 
/*     */     
/* 286 */     map.put("profile_name", selectedProfile.getName());
/* 287 */     map.put("version_name", version.getId());
/*     */     
/* 289 */     map.put("game_directory", gameDirectory.getAbsolutePath());
/* 290 */     map.put("game_assets", assetsDirectory.getAbsolutePath());
/*     */     
/* 292 */     map.put("assets_root", getAssetsDir().getAbsolutePath());
/* 293 */     map.put("assets_index_name", getVersion().getAssetIndex().getId());
/*     */     
/* 295 */     map.put("version_type", getVersion().getType().getName());
/*     */     
/* 297 */     if (selectedProfile.getResolution() != null) {
/* 298 */       map.put("resolution_width", String.valueOf(selectedProfile.getResolution().getWidth()));
/* 299 */       map.put("resolution_height", String.valueOf(selectedProfile.getResolution().getHeight()));
/*     */     } else {
/* 301 */       map.put("resolution_width", "");
/* 302 */       map.put("resolution_height", "");
/*     */     } 
/*     */     
/* 305 */     map.put("language", "en-us");
/*     */     
/*     */     try {
/* 308 */       AssetIndex assetIndex = getAssetIndex();
/* 309 */       for (Map.Entry<String, AssetIndex.AssetObject> entry : (Iterable<Map.Entry<String, AssetIndex.AssetObject>>)assetIndex.getFileMap().entrySet()) {
/* 310 */         String hash = ((AssetIndex.AssetObject)entry.getValue()).getHash();
/* 311 */         String path = (new File(new File(getAssetsDir(), "objects"), hash.substring(0, 2) + "/" + hash)).getAbsolutePath();
/* 312 */         map.put("asset=" + (String)entry.getKey(), path);
/*     */       } 
/* 314 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 317 */     map.put("launcher_name", "java-minecraft-launcher");
/* 318 */     map.put("launcher_version", LauncherConstants.getVersionName());
/* 319 */     map.put("natives_directory", this.nativeDir.getAbsolutePath());
/* 320 */     map.put("classpath", constructClassPath(getVersion()));
/* 321 */     map.put("classpath_separator", System.getProperty("path.separator"));
/* 322 */     map.put("primary_jar", (new File(getLauncher().getWorkingDirectory(), "versions/" + getVersion().getJar() + "/" + getVersion().getJar() + ".jar")).getAbsolutePath());
/*     */     
/* 324 */     return new StrSubstitutor(map);
/*     */   }
/*     */   
/*     */   private void migrateOldAssets() {
/* 328 */     File sourceDir = getAssetsDir();
/* 329 */     File objectsDir = new File(sourceDir, "objects");
/* 330 */     if (!sourceDir.isDirectory())
/*     */       return; 
/* 332 */     IOFileFilter migratableFilter = FileFilterUtils.notFileFilter(FileFilterUtils.or(new IOFileFilter[] { FileFilterUtils.nameFileFilter("indexes"), FileFilterUtils.nameFileFilter("objects"), FileFilterUtils.nameFileFilter("virtual"), FileFilterUtils.nameFileFilter("skins") }));
/* 333 */     for (File file : new TreeSet(FileUtils.listFiles(sourceDir, TrueFileFilter.TRUE, migratableFilter))) {
/* 334 */       String hash = Downloadable.getDigest(file, "SHA-1", 40);
/* 335 */       File destinationFile = new File(objectsDir, hash.substring(0, 2) + "/" + hash);
/*     */       
/* 337 */       if (!destinationFile.exists()) {
/* 338 */         LOGGER.info("Migrated old asset {} into {}", new Object[] { file, destinationFile });
/*     */         try {
/* 340 */           FileUtils.copyFile(file, destinationFile);
/* 341 */         } catch (IOException e) {
/* 342 */           LOGGER.error("Couldn't migrate old asset", e);
/*     */         } 
/*     */       } 
/*     */       
/* 346 */       FileUtils.deleteQuietly(file);
/*     */     } 
/*     */     
/* 349 */     File[] assets = sourceDir.listFiles();
/* 350 */     if (assets != null) {
/* 351 */       for (File file : assets) {
/* 352 */         if (!file.getName().equals("indexes") && !file.getName().equals("objects") && !file.getName().equals("virtual") && !file.getName().equals("skins")) {
/* 353 */           LOGGER.info("Cleaning up old assets directory {} after migration", new Object[] { file });
/* 354 */           FileUtils.deleteQuietly(file);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void unpackNatives(File targetDir) throws IOException {
/* 361 */     OperatingSystem os = OperatingSystem.getCurrentPlatform();
/* 362 */     Collection<Library> libraries = getVersion().getRelevantLibraries(createFeatureMatcher());
/*     */     
/* 364 */     for (Library library : libraries) {
/* 365 */       Map<OperatingSystem, String> nativesPerOs = library.getNatives();
/*     */       
/* 367 */       if (nativesPerOs != null && nativesPerOs.get(os) != null) {
/* 368 */         File file = new File(getLauncher().getWorkingDirectory(), "libraries/" + library.getArtifactPath(nativesPerOs.get(os)));
/* 369 */         ZipFile zip = new ZipFile(file);
/* 370 */         ExtractRules extractRules = library.getExtractRules();
/*     */         
/*     */         try {
/* 373 */           Enumeration<? extends ZipEntry> entries = zip.entries();
/*     */           
/* 375 */           while (entries.hasMoreElements()) {
/* 376 */             ZipEntry entry = entries.nextElement();
/*     */             
/* 378 */             if (extractRules != null && !extractRules.shouldExtract(entry.getName())) {
/*     */               continue;
/*     */             }
/*     */             
/* 382 */             File targetFile = new File(targetDir, entry.getName());
/* 383 */             if (targetFile.getParentFile() != null) targetFile.getParentFile().mkdirs();
/*     */             
/* 385 */             if (!entry.isDirectory()) {
/* 386 */               BufferedInputStream inputStream = new BufferedInputStream(zip.getInputStream(entry));
/*     */               
/* 388 */               byte[] buffer = new byte[2048];
/* 389 */               FileOutputStream outputStream = new FileOutputStream(targetFile);
/* 390 */               BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
/*     */               try {
/*     */                 int length;
/* 393 */                 while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
/* 394 */                   bufferedOutputStream.write(buffer, 0, length);
/*     */                 }
/*     */               } finally {
/* 397 */                 Downloadable.closeSilently(bufferedOutputStream);
/* 398 */                 Downloadable.closeSilently(outputStream);
/* 399 */                 Downloadable.closeSilently(inputStream);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } finally {
/* 404 */           zip.close();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private CompatibilityRule.FeatureMatcher createFeatureMatcher() {
/* 411 */     return (CompatibilityRule.FeatureMatcher)new CurrentLaunchFeatureMatcher(this.selectedProfile, getVersion(), this.minecraftLauncher.getProfileManager().getAuthDatabase().getByUUID(this.minecraftLauncher.getProfileManager().getSelectedUser()));
/*     */   }
/*     */   
/*     */   private String constructClassPath(CompleteMinecraftVersion version) {
/* 415 */     StringBuilder result = new StringBuilder();
/* 416 */     Collection<File> classPath = version.getClassPath(OperatingSystem.getCurrentPlatform(), getLauncher().getWorkingDirectory(), createFeatureMatcher());
/* 417 */     String separator = System.getProperty("path.separator");
/*     */     
/* 419 */     for (File file : classPath) {
/* 420 */       if (!file.isFile()) throw new RuntimeException("Classpath file not found: " + file); 
/* 421 */       if (result.length() > 0) result.append(separator); 
/* 422 */       result.append(file.getAbsolutePath());
/*     */     } 
/*     */     
/* 425 */     return result.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGameProcessEnded(GameProcess process) {
/* 430 */     int exitCode = process.getExitCode();
/*     */     
/* 432 */     if (exitCode == 0) {
/* 433 */       LOGGER.info("Game ended with no troubles detected (exit code " + exitCode + ")");
/*     */       
/* 435 */       if (this.visibilityRule == LauncherVisibilityRule.CLOSE_LAUNCHER) {
/* 436 */         LOGGER.info("Following visibility rule and exiting launcher as the game has ended");
/* 437 */         getLauncher().shutdownLauncher();
/* 438 */       } else if (this.visibilityRule == LauncherVisibilityRule.HIDE_LAUNCHER) {
/* 439 */         LOGGER.info("Following visibility rule and showing launcher as the game has ended");
/* 440 */         this.minecraftLauncher.getUserInterface().setVisible(true);
/*     */       } 
/*     */     } else {
/* 443 */       LOGGER.error("Game ended with bad state (exit code " + exitCode + ")");
/* 444 */       LOGGER.info("Ignoring visibility rule and showing launcher due to a game crash");
/* 445 */       this.minecraftLauncher.getUserInterface().setVisible(true);
/*     */       
/* 447 */       String errorText = null;
/* 448 */       Collection<String> sysOutLines = process.getSysOutLines();
/* 449 */       String[] sysOut = sysOutLines.<String>toArray(new String[sysOutLines.size()]);
/*     */       
/* 451 */       for (int i = sysOut.length - 1; i >= 0; i--) {
/* 452 */         String line = sysOut[i];
/* 453 */         int pos = line.lastIndexOf("#@!@#");
/*     */         
/* 455 */         if (pos >= 0 && pos < line.length() - "#@!@#".length() - 1) {
/* 456 */           errorText = line.substring(pos + "#@!@#".length()).trim();
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 461 */       if (errorText != null) {
/* 462 */         File file = new File(errorText);
/*     */         
/* 464 */         if (file.isFile()) {
/* 465 */           LOGGER.info("Crash report detected, opening: " + errorText);
/* 466 */           InputStream inputStream = null;
/*     */           
/*     */           try {
/* 469 */             inputStream = new FileInputStream(file);
/* 470 */             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
/* 471 */             StringBuilder result = new StringBuilder();
/*     */             
/*     */             String line;
/* 474 */             while ((line = reader.readLine()) != null) {
/* 475 */               if (result.length() > 0) result.append("\n"); 
/* 476 */               result.append(line);
/*     */             } 
/*     */             
/* 479 */             reader.close();
/*     */             
/* 481 */             this.minecraftLauncher.getUserInterface().showCrashReport((CompleteVersion)getVersion(), file, result.toString());
/* 482 */           } catch (IOException e) {
/* 483 */             LOGGER.error("Couldn't open crash report", e);
/*     */           } finally {
/* 485 */             Downloadable.closeSilently(inputStream);
/*     */           } 
/*     */         } else {
/* 488 */           LOGGER.error("Crash report detected, but unknown format: " + errorText);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     setStatus(GameInstanceStatus.IDLE);
/*     */   }
/*     */   
/*     */   public void setVisibility(LauncherVisibilityRule visibility) {
/* 497 */     this.visibilityRule = visibility;
/*     */   }
/*     */   
/*     */   public UserAuthentication getAuth() {
/* 501 */     return this.auth;
/*     */   }
/*     */   
/*     */   public Profile getSelectedProfile() {
/* 505 */     return this.selectedProfile;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\game\MinecraftGameRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */