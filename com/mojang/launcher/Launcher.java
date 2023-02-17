/*    */ package com.mojang.launcher;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.launcher.updater.ExceptionalThreadPoolExecutor;
/*    */ import com.mojang.launcher.updater.VersionManager;
/*    */ import com.mojang.launcher.versions.ReleaseTypeFactory;
/*    */ import java.io.File;
/*    */ import java.net.PasswordAuthentication;
/*    */ import java.net.Proxy;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class Launcher
/*    */ {
/*    */   private final int launcherFormatVersion;
/*    */   private final ReleaseTypeFactory releaseTypeFactory;
/*    */   private final Agent agent;
/*    */   private final PasswordAuthentication proxyAuth;
/*    */   private final Proxy proxy;
/*    */   private final UserInterface ui;
/* 23 */   private final ThreadPoolExecutor downloaderExecutorService = (ThreadPoolExecutor)new ExceptionalThreadPoolExecutor(16, 16, 30L, TimeUnit.SECONDS);
/*    */   
/*    */   private final File workingDirectory;
/*    */   private final VersionManager versionManager;
/*    */   
/*    */   static {
/* 29 */     Thread.currentThread().setContextClassLoader(Launcher.class.getClassLoader());
/* 30 */   } private static final Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */   
/*    */   public Launcher(UserInterface ui, File workingDirectory, Proxy proxy, PasswordAuthentication proxyAuth, VersionManager versionManager, Agent agent, ReleaseTypeFactory releaseTypeFactory, int launcherFormatVersion) {
/* 34 */     this.ui = ui;
/* 35 */     this.proxy = proxy;
/* 36 */     this.proxyAuth = proxyAuth;
/* 37 */     this.workingDirectory = workingDirectory;
/* 38 */     this.agent = agent;
/* 39 */     this.versionManager = versionManager;
/* 40 */     this.releaseTypeFactory = releaseTypeFactory;
/* 41 */     this.launcherFormatVersion = launcherFormatVersion;
/*    */     
/* 43 */     this.downloaderExecutorService.allowCoreThreadTimeOut(true);
/*    */   }
/*    */   
/*    */   public ReleaseTypeFactory getReleaseTypeFactory() {
/* 47 */     return this.releaseTypeFactory;
/*    */   }
/*    */   
/*    */   public VersionManager getVersionManager() {
/* 51 */     return this.versionManager;
/*    */   }
/*    */   
/*    */   public File getWorkingDirectory() {
/* 55 */     return this.workingDirectory;
/*    */   }
/*    */   
/*    */   public UserInterface getUserInterface() {
/* 59 */     return this.ui;
/*    */   }
/*    */   
/*    */   public Proxy getProxy() {
/* 63 */     return this.proxy;
/*    */   }
/*    */   
/*    */   public PasswordAuthentication getProxyAuth() {
/* 67 */     return this.proxyAuth;
/*    */   }
/*    */   
/*    */   public ThreadPoolExecutor getDownloaderExecutorService() {
/* 71 */     return this.downloaderExecutorService;
/*    */   }
/*    */   
/*    */   public void shutdownLauncher() {
/* 75 */     getUserInterface().shutdownLauncher();
/*    */   }
/*    */   
/*    */   public Agent getAgent() {
/* 79 */     return this.agent;
/*    */   }
/*    */   
/*    */   public int getLauncherFormatVersion() {
/* 83 */     return this.launcherFormatVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\Launcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */