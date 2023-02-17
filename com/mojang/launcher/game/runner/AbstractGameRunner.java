/*     */ package com.mojang.launcher.game.runner;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.mojang.launcher.Launcher;
/*     */ import com.mojang.launcher.game.GameInstanceStatus;
/*     */ import com.mojang.launcher.updater.DownloadProgress;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.updater.download.DownloadJob;
/*     */ import com.mojang.launcher.updater.download.DownloadListener;
/*     */ import com.mojang.launcher.updater.download.Downloadable;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class AbstractGameRunner
/*     */   implements GameRunner, DownloadListener
/*     */ {
/*  21 */   protected static final Logger LOGGER = LogManager.getLogger();
/*  22 */   protected final Object lock = new Object();
/*  23 */   private final List<DownloadJob> jobs = new ArrayList<DownloadJob>();
/*     */   protected CompleteVersion version;
/*  25 */   private GameInstanceStatus status = GameInstanceStatus.IDLE;
/*  26 */   private final List<GameRunnerListener> listeners = Lists.newArrayList();
/*     */   
/*     */   protected void setStatus(GameInstanceStatus status) {
/*  29 */     synchronized (this.lock) {
/*  30 */       this.status = status;
/*  31 */       for (GameRunnerListener listener : Lists.newArrayList(this.listeners)) {
/*  32 */         listener.onGameInstanceChangedState(this, status);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract Launcher getLauncher();
/*     */   
/*     */   public GameInstanceStatus getStatus() {
/*  41 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playGame(VersionSyncInfo syncInfo) {
/*  46 */     synchronized (this.lock) {
/*  47 */       if (getStatus() != GameInstanceStatus.IDLE) {
/*  48 */         LOGGER.warn("Tried to play game but game is already starting!");
/*     */         
/*     */         return;
/*     */       } 
/*  52 */       setStatus(GameInstanceStatus.PREPARING);
/*     */     } 
/*     */     
/*  55 */     LOGGER.info("Getting syncinfo for selected version");
/*     */     
/*  57 */     if (syncInfo == null) {
/*  58 */       LOGGER.warn("Tried to launch a version without a version being selected...");
/*  59 */       setStatus(GameInstanceStatus.IDLE);
/*     */       
/*     */       return;
/*     */     } 
/*  63 */     synchronized (this.lock) {
/*  64 */       LOGGER.info("Queueing library & version downloads");
/*     */       
/*     */       try {
/*  67 */         this.version = getLauncher().getVersionManager().getLatestCompleteVersion(syncInfo);
/*  68 */       } catch (IOException e) {
/*  69 */         LOGGER.error("Couldn't get complete version info for " + syncInfo.getLatestVersion(), e);
/*  70 */         setStatus(GameInstanceStatus.IDLE);
/*     */         
/*     */         return;
/*     */       } 
/*  74 */       if (syncInfo.getRemoteVersion() != null && syncInfo.getLatestSource() != VersionSyncInfo.VersionSource.REMOTE && !this.version.isSynced()) {
/*     */         try {
/*  76 */           syncInfo = getLauncher().getVersionManager().syncVersion(syncInfo);
/*  77 */           this.version = getLauncher().getVersionManager().getLatestCompleteVersion(syncInfo);
/*  78 */         } catch (IOException e) {
/*  79 */           LOGGER.error("Couldn't sync local and remote versions", e);
/*     */         } 
/*  81 */         this.version.setSynced(true);
/*     */       } 
/*     */       
/*  84 */       if (!this.version.appliesToCurrentEnvironment()) {
/*  85 */         String reason = this.version.getIncompatibilityReason();
/*  86 */         if (reason == null) reason = "This version is incompatible with your computer. Please try another one by going into Edit Profile and selecting one through the dropdown. Sorry!"; 
/*  87 */         LOGGER.error("Version " + this.version.getId() + " is incompatible with current environment: " + reason);
/*  88 */         getLauncher().getUserInterface().gameLaunchFailure(reason);
/*  89 */         setStatus(GameInstanceStatus.IDLE);
/*     */         
/*     */         return;
/*     */       } 
/*  93 */       if (this.version.getMinimumLauncherVersion() > getLauncher().getLauncherFormatVersion()) {
/*  94 */         LOGGER.error("An update to your launcher is available and is required to play " + this.version.getId() + ". Please restart your launcher.");
/*  95 */         setStatus(GameInstanceStatus.IDLE);
/*     */         
/*     */         return;
/*     */       } 
/*  99 */       if (!syncInfo.isUpToDate()) {
/*     */         try {
/* 101 */           getLauncher().getVersionManager().installVersion(this.version);
/* 102 */         } catch (IOException e) {
/* 103 */           LOGGER.error("Couldn't save version info to install " + syncInfo.getLatestVersion(), e);
/* 104 */           setStatus(GameInstanceStatus.IDLE);
/*     */           
/*     */           return;
/*     */         } 
/*     */       }
/* 109 */       setStatus(GameInstanceStatus.DOWNLOADING);
/* 110 */       downloadRequiredFiles(syncInfo);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void downloadRequiredFiles(VersionSyncInfo syncInfo) {
/*     */     try {
/* 116 */       DownloadJob librariesJob = new DownloadJob("Version & Libraries", false, this);
/* 117 */       addJob(librariesJob);
/* 118 */       getLauncher().getVersionManager().downloadVersion(syncInfo, librariesJob);
/* 119 */       librariesJob.startDownloading(getLauncher().getDownloaderExecutorService());
/*     */       
/* 121 */       DownloadJob resourceJob = new DownloadJob("Resources", true, this);
/* 122 */       addJob(resourceJob);
/* 123 */       getLauncher().getVersionManager().downloadResources(resourceJob, this.version);
/* 124 */       resourceJob.startDownloading(getLauncher().getDownloaderExecutorService());
/* 125 */     } catch (IOException e) {
/* 126 */       LOGGER.error("Couldn't get version info for " + syncInfo.getLatestVersion(), e);
/* 127 */       setStatus(GameInstanceStatus.IDLE);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateProgressBar() {
/* 132 */     synchronized (this.lock) {
/* 133 */       if (hasRemainingJobs()) {
/* 134 */         long total = 0L;
/* 135 */         long current = 0L;
/* 136 */         Downloadable longestRunning = null;
/*     */         
/* 138 */         for (DownloadJob job : this.jobs) {
/* 139 */           for (Downloadable file : job.getAllFiles()) {
/* 140 */             total += file.getMonitor().getTotal();
/* 141 */             current += file.getMonitor().getCurrent();
/*     */             
/* 143 */             if (longestRunning == null || longestRunning.getEndTime() > 0L || (file.getStartTime() < longestRunning.getStartTime() && file.getEndTime() == 0L)) {
/* 144 */               longestRunning = file;
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 149 */         getLauncher().getUserInterface().setDownloadProgress(new DownloadProgress(current, total, (longestRunning == null) ? null : longestRunning.getStatus()));
/*     */       } else {
/* 151 */         this.jobs.clear();
/* 152 */         getLauncher().getUserInterface().hideDownloadProgress();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean hasRemainingJobs() {
/* 158 */     synchronized (this.lock) {
/* 159 */       for (DownloadJob job : this.jobs) {
/* 160 */         if (!job.isComplete()) return true;
/*     */       
/*     */       } 
/*     */     } 
/* 164 */     return false;
/*     */   }
/*     */   
/*     */   public void addJob(DownloadJob job) {
/* 168 */     synchronized (this.lock) {
/* 169 */       this.jobs.add(job);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDownloadJobFinished(DownloadJob job) {
/* 175 */     updateProgressBar();
/* 176 */     synchronized (this.lock) {
/* 177 */       if (job.getFailures() > 0) {
/* 178 */         LOGGER.error("Job '" + job.getName() + "' finished with " + job.getFailures() + " failure(s)! (took " + job.getStopWatch().toString() + ")");
/* 179 */         setStatus(GameInstanceStatus.IDLE);
/*     */       } else {
/* 181 */         LOGGER.info("Job '" + job.getName() + "' finished successfully (took " + job.getStopWatch().toString() + ")");
/*     */         
/* 183 */         if (getStatus() != GameInstanceStatus.IDLE && !hasRemainingJobs()) {
/*     */           try {
/* 185 */             setStatus(GameInstanceStatus.LAUNCHING);
/* 186 */             launchGame();
/* 187 */           } catch (Throwable ex) {
/* 188 */             LOGGER.fatal("Fatal error launching game. Report this to http://bugs.mojang.com please!", ex);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract void launchGame() throws IOException;
/*     */   
/*     */   public void onDownloadJobProgressChanged(DownloadJob job) {
/* 199 */     updateProgressBar();
/*     */   }
/*     */   
/*     */   public void addListener(GameRunnerListener listener) {
/* 203 */     synchronized (this.lock) {
/* 204 */       this.listeners.add(listener);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\runner\AbstractGameRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */