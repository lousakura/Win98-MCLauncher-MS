/*     */ package net.minecraft.launcher.updater;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.Gson;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.events.RefreshedVersionsListener;
/*     */ import com.mojang.launcher.updater.ExceptionalThreadPoolExecutor;
/*     */ import com.mojang.launcher.updater.VersionFilter;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.updater.download.DownloadJob;
/*     */ import com.mojang.launcher.updater.download.Downloadable;
/*     */ import com.mojang.launcher.updater.download.assets.AssetDownloadable;
/*     */ import com.mojang.launcher.updater.download.assets.AssetIndex;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import com.mojang.launcher.versions.ReleaseType;
/*     */ import com.mojang.launcher.versions.Version;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class MinecraftVersionManager implements VersionManager {
/*  38 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final VersionList localVersionList;
/*     */   private final VersionList remoteVersionList;
/*  41 */   private final ThreadPoolExecutor executorService = (ThreadPoolExecutor)new ExceptionalThreadPoolExecutor(4, 8, 30L, TimeUnit.SECONDS);
/*  42 */   private final List<RefreshedVersionsListener> refreshedVersionsListeners = Collections.synchronizedList(new ArrayList<RefreshedVersionsListener>());
/*  43 */   private final Object refreshLock = new Object();
/*     */   private boolean isRefreshing;
/*  45 */   private final Gson gson = new Gson();
/*     */   
/*     */   public MinecraftVersionManager(VersionList localVersionList, VersionList remoteVersionList) {
/*  48 */     this.localVersionList = localVersionList;
/*  49 */     this.remoteVersionList = remoteVersionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshVersions() throws IOException {
/*  54 */     synchronized (this.refreshLock) {
/*  55 */       this.isRefreshing = true;
/*     */     } 
/*     */     
/*     */     try {
/*  59 */       LOGGER.info("Refreshing local version list...");
/*  60 */       this.localVersionList.refreshVersions();
/*  61 */       LOGGER.info("Refreshing remote version list...");
/*  62 */       this.remoteVersionList.refreshVersions();
/*  63 */     } catch (IOException ex) {
/*  64 */       synchronized (this.refreshLock) {
/*  65 */         this.isRefreshing = false;
/*     */       } 
/*  67 */       throw ex;
/*     */     } 
/*     */     
/*  70 */     LOGGER.info("Refresh complete.");
/*     */     
/*  72 */     synchronized (this.refreshLock) {
/*  73 */       this.isRefreshing = false;
/*     */     } 
/*     */     
/*  76 */     for (RefreshedVersionsListener listener : Lists.newArrayList(this.refreshedVersionsListeners)) {
/*  77 */       listener.onVersionsRefreshed(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VersionSyncInfo> getVersions() {
/*  83 */     return getVersions(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VersionSyncInfo> getVersions(VersionFilter<? extends ReleaseType> filter) {
/*  88 */     synchronized (this.refreshLock) {
/*  89 */       if (this.isRefreshing) return new ArrayList<VersionSyncInfo>();
/*     */     
/*     */     } 
/*  92 */     List<VersionSyncInfo> result = new ArrayList<VersionSyncInfo>();
/*  93 */     Map<String, VersionSyncInfo> lookup = new HashMap<String, VersionSyncInfo>();
/*  94 */     Map<MinecraftReleaseType, Integer> counts = Maps.newEnumMap(MinecraftReleaseType.class);
/*     */     
/*  96 */     for (MinecraftReleaseType type : MinecraftReleaseType.values()) {
/*  97 */       counts.put(type, Integer.valueOf(0));
/*     */     }
/*     */     
/* 100 */     for (Version version : Lists.newArrayList(this.localVersionList.getVersions())) {
/* 101 */       if (version.getType() == null || version.getUpdatedTime() == null)
/* 102 */         continue;  MinecraftReleaseType type = (MinecraftReleaseType)version.getType();
/* 103 */       if (filter != null && (!filter.getTypes().contains(type) || ((Integer)counts.get(type)).intValue() >= filter.getMaxCount()))
/*     */         continue; 
/* 105 */       VersionSyncInfo syncInfo = getVersionSyncInfo(version, this.remoteVersionList.getVersion(version.getId()));
/* 106 */       lookup.put(version.getId(), syncInfo);
/* 107 */       result.add(syncInfo);
/*     */     } 
/*     */     
/* 110 */     for (Version version : this.remoteVersionList.getVersions()) {
/* 111 */       if (version.getType() == null || version.getUpdatedTime() == null)
/* 112 */         continue;  MinecraftReleaseType type = (MinecraftReleaseType)version.getType();
/* 113 */       if (lookup.containsKey(version.getId()) || (
/* 114 */         filter != null && (!filter.getTypes().contains(type) || ((Integer)counts.get(type)).intValue() >= filter.getMaxCount())))
/*     */         continue; 
/* 116 */       VersionSyncInfo syncInfo = getVersionSyncInfo(this.localVersionList.getVersion(version.getId()), version);
/* 117 */       lookup.put(version.getId(), syncInfo);
/* 118 */       result.add(syncInfo);
/*     */       
/* 120 */       if (filter != null) counts.put(type, Integer.valueOf(((Integer)counts.get(type)).intValue() + 1));
/*     */     
/*     */     } 
/* 123 */     if (result.isEmpty()) {
/* 124 */       for (Version version : this.localVersionList.getVersions()) {
/* 125 */         if (version.getType() == null || version.getUpdatedTime() == null)
/* 126 */           continue;  VersionSyncInfo syncInfo = getVersionSyncInfo(version, this.remoteVersionList.getVersion(version.getId()));
/* 127 */         lookup.put(version.getId(), syncInfo);
/* 128 */         result.add(syncInfo);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 133 */     Collections.sort(result, new Comparator<VersionSyncInfo>()
/*     */         {
/*     */           public int compare(VersionSyncInfo a, VersionSyncInfo b) {
/* 136 */             Version aVer = a.getLatestVersion();
/* 137 */             Version bVer = b.getLatestVersion();
/*     */             
/* 139 */             if (aVer.getReleaseTime() != null && bVer.getReleaseTime() != null) {
/* 140 */               return bVer.getReleaseTime().compareTo(aVer.getReleaseTime());
/*     */             }
/* 142 */             return bVer.getUpdatedTime().compareTo(aVer.getUpdatedTime());
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 147 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public VersionSyncInfo getVersionSyncInfo(Version version) {
/* 152 */     return getVersionSyncInfo(version.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public VersionSyncInfo getVersionSyncInfo(String name) {
/* 157 */     return getVersionSyncInfo(this.localVersionList.getVersion(name), this.remoteVersionList.getVersion(name));
/*     */   }
/*     */ 
/*     */   
/*     */   public VersionSyncInfo getVersionSyncInfo(Version localVersion, Version remoteVersion) {
/* 162 */     boolean installed = (localVersion != null);
/* 163 */     boolean upToDate = installed;
/* 164 */     CompleteMinecraftVersion resolved = null;
/*     */     
/* 166 */     if (installed && remoteVersion != null) {
/* 167 */       upToDate = !remoteVersion.getUpdatedTime().after(localVersion.getUpdatedTime());
/*     */     }
/* 169 */     if (localVersion instanceof CompleteVersion) {
/*     */       try {
/* 171 */         resolved = ((CompleteMinecraftVersion)localVersion).resolve(this);
/* 172 */       } catch (IOException ex) {
/* 173 */         LOGGER.error("Couldn't resolve version " + localVersion.getId(), ex);
/* 174 */         resolved = (CompleteMinecraftVersion)localVersion;
/*     */       } 
/*     */       
/* 177 */       upToDate &= this.localVersionList.hasAllFiles(resolved, OperatingSystem.getCurrentPlatform());
/*     */     } 
/*     */     
/* 180 */     return new VersionSyncInfo((Version)resolved, remoteVersion, installed, upToDate);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<VersionSyncInfo> getInstalledVersions() {
/* 185 */     List<VersionSyncInfo> result = new ArrayList<VersionSyncInfo>();
/*     */     
/* 187 */     Collection<Version> versions = Lists.newArrayList(this.localVersionList.getVersions());
/* 188 */     for (Version version : versions) {
/* 189 */       if (version.getType() == null || version.getUpdatedTime() == null)
/*     */         continue; 
/* 191 */       VersionSyncInfo syncInfo = getVersionSyncInfo(version, this.remoteVersionList.getVersion(version.getId()));
/* 192 */       result.add(syncInfo);
/*     */     } 
/*     */     
/* 195 */     return result;
/*     */   }
/*     */   
/*     */   public VersionList getRemoteVersionList() {
/* 199 */     return this.remoteVersionList;
/*     */   }
/*     */   
/*     */   public VersionList getLocalVersionList() {
/* 203 */     return this.localVersionList;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompleteMinecraftVersion getLatestCompleteVersion(VersionSyncInfo syncInfo) throws IOException {
/* 208 */     if (syncInfo.getLatestSource() == VersionSyncInfo.VersionSource.REMOTE) {
/* 209 */       CompleteMinecraftVersion result = null;
/* 210 */       IOException exception = null;
/*     */       
/*     */       try {
/* 213 */         result = this.remoteVersionList.getCompleteVersion(syncInfo.getLatestVersion());
/* 214 */       } catch (IOException e) {
/* 215 */         exception = e;
/*     */         try {
/* 217 */           result = this.localVersionList.getCompleteVersion(syncInfo.getLatestVersion());
/* 218 */         } catch (IOException iOException) {}
/*     */       } 
/*     */       
/* 221 */       if (result != null) {
/* 222 */         return result;
/*     */       }
/* 224 */       throw exception;
/*     */     } 
/*     */     
/* 227 */     return this.localVersionList.getCompleteVersion(syncInfo.getLatestVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public DownloadJob downloadVersion(VersionSyncInfo syncInfo, DownloadJob job) throws IOException {
/* 233 */     if (!(this.localVersionList instanceof LocalVersionList)) throw new IllegalArgumentException("Cannot download if local repo isn't a LocalVersionList"); 
/* 234 */     if (!(this.remoteVersionList instanceof RemoteVersionList)) throw new IllegalArgumentException("Cannot download if local repo isn't a RemoteVersionList"); 
/* 235 */     CompleteMinecraftVersion version = getLatestCompleteVersion(syncInfo);
/* 236 */     File baseDirectory = ((LocalVersionList)this.localVersionList).getBaseDirectory();
/* 237 */     Proxy proxy = ((RemoteVersionList)this.remoteVersionList).getProxy();
/*     */     
/* 239 */     job.addDownloadables(version.getRequiredDownloadables(OperatingSystem.getCurrentPlatform(), proxy, baseDirectory, false));
/*     */     
/* 241 */     String jarFile = "versions/" + version.getJar() + "/" + version.getJar() + ".jar";
/* 242 */     AbstractDownloadInfo clientInfo = version.getDownloadURL(DownloadType.CLIENT);
/* 243 */     if (clientInfo == null) {
/* 244 */       job.addDownloadables(new Downloadable[] { (Downloadable)new EtagDownloadable(proxy, new URL("https://s3.amazonaws.com/Minecraft.Download/" + jarFile), new File(baseDirectory, jarFile), false) });
/*     */     } else {
/* 246 */       job.addDownloadables(new Downloadable[] { new PreHashedDownloadable(proxy, clientInfo.getUrl(), new File(baseDirectory, jarFile), false, clientInfo.getSha1()) });
/*     */     } 
/*     */     
/* 249 */     return job;
/*     */   }
/*     */ 
/*     */   
/*     */   public DownloadJob downloadResources(DownloadJob job, CompleteVersion version) throws IOException {
/* 254 */     File baseDirectory = ((LocalVersionList)this.localVersionList).getBaseDirectory();
/*     */     
/* 256 */     job.addDownloadables(getResourceFiles(((RemoteVersionList)this.remoteVersionList).getProxy(), baseDirectory, (CompleteMinecraftVersion)version));
/*     */     
/* 258 */     return job;
/*     */   }
/*     */   
/*     */   private Set<Downloadable> getResourceFiles(Proxy proxy, File baseDirectory, CompleteMinecraftVersion version) {
/* 262 */     Set<Downloadable> result = new HashSet<Downloadable>();
/* 263 */     InputStream inputStream = null;
/* 264 */     File assets = new File(baseDirectory, "assets");
/* 265 */     File objectsFolder = new File(assets, "objects");
/* 266 */     File indexesFolder = new File(assets, "indexes");
/* 267 */     long start = System.nanoTime();
/*     */     
/* 269 */     AssetIndexInfo indexInfo = version.getAssetIndex();
/* 270 */     File indexFile = new File(indexesFolder, indexInfo.getId() + ".json");
/*     */     
/*     */     try {
/* 273 */       URL indexUrl = indexInfo.getUrl();
/* 274 */       inputStream = indexUrl.openConnection(proxy).getInputStream();
/* 275 */       String json = IOUtils.toString(inputStream);
/* 276 */       FileUtils.writeStringToFile(indexFile, json);
/* 277 */       AssetIndex index = (AssetIndex)this.gson.fromJson(json, AssetIndex.class);
/*     */       
/* 279 */       for (Map.Entry<AssetIndex.AssetObject, String> entry : (Iterable<Map.Entry<AssetIndex.AssetObject, String>>)index.getUniqueObjects().entrySet()) {
/* 280 */         AssetIndex.AssetObject object = entry.getKey();
/* 281 */         String filename = object.getHash().substring(0, 2) + "/" + object.getHash();
/* 282 */         File file = new File(objectsFolder, filename);
/* 283 */         if (!file.isFile() || FileUtils.sizeOf(file) != object.getSize()) {
/* 284 */           AssetDownloadable assetDownloadable = new AssetDownloadable(proxy, entry.getValue(), object, "http://resources.download.minecraft.net/", objectsFolder);
/* 285 */           assetDownloadable.setExpectedSize(object.getSize());
/* 286 */           result.add(assetDownloadable);
/*     */         } 
/*     */       } 
/*     */       
/* 290 */       long end = System.nanoTime();
/* 291 */       long delta = end - start;
/* 292 */       LOGGER.debug("Delta time to compare resources: " + (delta / 1000000L) + " ms ");
/* 293 */     } catch (Exception ex) {
/* 294 */       LOGGER.error("Couldn't download resources", ex);
/*     */     } finally {
/* 296 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
/*     */     
/* 299 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public ThreadPoolExecutor getExecutorService() {
/* 304 */     return this.executorService;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addRefreshedVersionsListener(RefreshedVersionsListener listener) {
/* 309 */     this.refreshedVersionsListeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeRefreshedVersionsListener(RefreshedVersionsListener listener) {
/* 314 */     this.refreshedVersionsListeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public VersionSyncInfo syncVersion(VersionSyncInfo syncInfo) throws IOException {
/* 319 */     CompleteVersion remoteVersion = getRemoteVersionList().getCompleteVersion(syncInfo.getRemoteVersion());
/* 320 */     getLocalVersionList().removeVersion(syncInfo.getLocalVersion());
/* 321 */     getLocalVersionList().addVersion(remoteVersion);
/* 322 */     ((LocalVersionList)getLocalVersionList()).saveVersion(((CompleteMinecraftVersion)remoteVersion).getSavableVersion());
/* 323 */     return getVersionSyncInfo((Version)remoteVersion);
/*     */   }
/*     */ 
/*     */   
/*     */   public void installVersion(CompleteVersion version) throws IOException {
/* 328 */     if (version instanceof CompleteMinecraftVersion) {
/* 329 */       version = ((CompleteMinecraftVersion)version).getSavableVersion();
/*     */     }
/*     */     
/* 332 */     VersionList localVersionList = getLocalVersionList();
/*     */     
/* 334 */     if (localVersionList.getVersion(version.getId()) != null) {
/* 335 */       localVersionList.removeVersion(version.getId());
/*     */     }
/* 337 */     localVersionList.addVersion(version);
/*     */     
/* 339 */     if (localVersionList instanceof LocalVersionList) {
/* 340 */       ((LocalVersionList)localVersionList).saveVersion(version);
/*     */     }
/*     */     
/* 343 */     LOGGER.info("Installed " + version);
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallVersion(CompleteVersion version) throws IOException {
/* 348 */     VersionList localVersionList = getLocalVersionList();
/* 349 */     if (localVersionList instanceof LocalVersionList) {
/* 350 */       localVersionList.uninstallVersion((Version)version);
/* 351 */       LOGGER.info("Uninstalled " + version);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\MinecraftVersionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */