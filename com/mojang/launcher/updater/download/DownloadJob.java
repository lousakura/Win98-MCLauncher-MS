/*     */ package com.mojang.launcher.updater.download;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.apache.commons.lang3.time.StopWatch;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class DownloadJob {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private static final int MAX_ATTEMPTS_PER_FILE = 5;
/*     */   private static final int ASSUMED_AVERAGE_FILE_SIZE = 5242880;
/*  17 */   private final Queue<Downloadable> remainingFiles = new ConcurrentLinkedQueue<Downloadable>();
/*  18 */   private final List<Downloadable> allFiles = Collections.synchronizedList(new ArrayList<Downloadable>());
/*  19 */   private final List<Downloadable> failures = Collections.synchronizedList(new ArrayList<Downloadable>());
/*  20 */   private final List<Downloadable> successful = Collections.synchronizedList(new ArrayList<Downloadable>());
/*     */   private final DownloadListener listener;
/*     */   private final String name;
/*     */   private final boolean ignoreFailures;
/*  24 */   private final AtomicInteger remainingThreads = new AtomicInteger();
/*  25 */   private final StopWatch stopWatch = new StopWatch();
/*     */   private boolean started;
/*     */   
/*     */   public DownloadJob(String name, boolean ignoreFailures, DownloadListener listener, Collection<Downloadable> files) {
/*  29 */     this.name = name;
/*  30 */     this.ignoreFailures = ignoreFailures;
/*  31 */     this.listener = listener;
/*  32 */     if (files != null) addDownloadables(files); 
/*     */   }
/*     */   
/*     */   public DownloadJob(String name, boolean ignoreFailures, DownloadListener listener) {
/*  36 */     this(name, ignoreFailures, listener, null);
/*     */   }
/*     */   
/*     */   public void addDownloadables(Collection<Downloadable> downloadables) {
/*  40 */     if (this.started) throw new IllegalStateException("Cannot add to download job that has already started");
/*     */     
/*  42 */     this.allFiles.addAll(downloadables);
/*  43 */     this.remainingFiles.addAll(downloadables);
/*     */     
/*  45 */     for (Downloadable downloadable : downloadables) {
/*  46 */       if (downloadable.getExpectedSize() == 0L) {
/*  47 */         downloadable.getMonitor().setTotal(5242880L);
/*     */       } else {
/*  49 */         downloadable.getMonitor().setTotal(downloadable.getExpectedSize());
/*     */       } 
/*  51 */       downloadable.getMonitor().setJob(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDownloadables(Downloadable... downloadables) {
/*  56 */     if (this.started) throw new IllegalStateException("Cannot add to download job that has already started");
/*     */     
/*  58 */     for (Downloadable downloadable : downloadables) {
/*  59 */       this.allFiles.add(downloadable);
/*  60 */       this.remainingFiles.add(downloadable);
/*  61 */       if (downloadable.getExpectedSize() == 0L) {
/*  62 */         downloadable.getMonitor().setTotal(5242880L);
/*     */       } else {
/*  64 */         downloadable.getMonitor().setTotal(downloadable.getExpectedSize());
/*     */       } 
/*  66 */       downloadable.getMonitor().setJob(this);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void startDownloading(ThreadPoolExecutor executorService) {
/*  71 */     if (this.started) throw new IllegalStateException("Cannot start download job that has already started"); 
/*  72 */     this.started = true;
/*  73 */     this.stopWatch.start();
/*     */     
/*  75 */     if (this.allFiles.isEmpty()) {
/*  76 */       LOGGER.info("Download job '" + this.name + "' skipped as there are no files to download");
/*  77 */       this.listener.onDownloadJobFinished(this);
/*     */     } else {
/*  79 */       int threads = executorService.getMaximumPoolSize();
/*  80 */       this.remainingThreads.set(threads);
/*  81 */       LOGGER.info("Download job '" + this.name + "' started (" + threads + " threads, " + this.allFiles.size() + " files)");
/*  82 */       for (int i = 0; i < threads; i++) {
/*  83 */         executorService.submit(new Runnable()
/*     */             {
/*     */               public void run() {
/*  86 */                 DownloadJob.this.popAndDownload();
/*     */               }
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void popAndDownload() {
/*     */     Downloadable downloadable;
/*  96 */     while ((downloadable = this.remainingFiles.poll()) != null) {
/*  97 */       if (downloadable.getStartTime() == 0L) {
/*  98 */         downloadable.setStartTime(System.currentTimeMillis());
/*     */       }
/*     */       
/* 101 */       if (downloadable.getNumAttempts() > 5) {
/* 102 */         if (!this.ignoreFailures) this.failures.add(downloadable); 
/* 103 */         LOGGER.error("Gave up trying to download " + downloadable.getUrl() + " for job '" + this.name + "'");
/*     */         
/*     */         continue;
/*     */       } 
/*     */       try {
/* 108 */         LOGGER.info("Attempting to download " + downloadable.getTarget() + " for job '" + this.name + "'... (try " + downloadable.getNumAttempts() + ")");
/* 109 */         String result = downloadable.download();
/* 110 */         this.successful.add(downloadable);
/* 111 */         downloadable.setEndTime(System.currentTimeMillis());
/* 112 */         downloadable.getMonitor().setCurrent(downloadable.getMonitor().getTotal());
/* 113 */         LOGGER.info("Finished downloading " + downloadable.getTarget() + " for job '" + this.name + "'" + ": " + result);
/* 114 */       } catch (Throwable t) {
/* 115 */         LOGGER.warn("Couldn't download " + downloadable.getUrl() + " for job '" + this.name + "'", t);
/* 116 */         downloadable.getMonitor().setCurrent(downloadable.getMonitor().getTotal());
/* 117 */         this.remainingFiles.add(downloadable);
/*     */       } 
/*     */     } 
/*     */     
/* 121 */     if (this.remainingThreads.decrementAndGet() <= 0) {
/* 122 */       this.listener.onDownloadJobFinished(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean shouldIgnoreFailures() {
/* 127 */     return this.ignoreFailures;
/*     */   }
/*     */   
/*     */   public boolean isStarted() {
/* 131 */     return this.started;
/*     */   }
/*     */   
/*     */   public boolean isComplete() {
/* 135 */     return (this.started && this.remainingFiles.isEmpty() && this.remainingThreads.get() == 0);
/*     */   }
/*     */   
/*     */   public int getFailures() {
/* 139 */     return this.failures.size();
/*     */   }
/*     */   
/*     */   public int getSuccessful() {
/* 143 */     return this.successful.size();
/*     */   }
/*     */   
/*     */   public String getName() {
/* 147 */     return this.name;
/*     */   }
/*     */   
/*     */   public void updateProgress() {
/* 151 */     this.listener.onDownloadJobProgressChanged(this);
/*     */   }
/*     */   
/*     */   public List<Downloadable> getAllFiles() {
/* 155 */     return this.allFiles;
/*     */   }
/*     */   
/*     */   public StopWatch getStopWatch() {
/* 159 */     return this.stopWatch;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\DownloadJob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */