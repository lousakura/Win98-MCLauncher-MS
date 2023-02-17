/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ import com.mojang.launcher.versions.Version;
/*    */ 
/*    */ public class VersionSyncInfo {
/*    */   private final Version localVersion;
/*    */   private final Version remoteVersion;
/*    */   private final boolean isInstalled;
/*    */   private final boolean isUpToDate;
/*    */   
/*    */   public VersionSyncInfo(Version localVersion, Version remoteVersion, boolean installed, boolean upToDate) {
/* 12 */     this.localVersion = localVersion;
/* 13 */     this.remoteVersion = remoteVersion;
/* 14 */     this.isInstalled = installed;
/* 15 */     this.isUpToDate = upToDate;
/*    */   }
/*    */   
/*    */   public Version getLocalVersion() {
/* 19 */     return this.localVersion;
/*    */   }
/*    */   
/*    */   public Version getRemoteVersion() {
/* 23 */     return this.remoteVersion;
/*    */   }
/*    */   
/*    */   public Version getLatestVersion() {
/* 27 */     if (getLatestSource() == VersionSource.REMOTE) {
/* 28 */       return this.remoteVersion;
/*    */     }
/* 30 */     return this.localVersion;
/*    */   }
/*    */ 
/*    */   
/*    */   public VersionSource getLatestSource() {
/* 35 */     if (getLocalVersion() == null) return VersionSource.REMOTE; 
/* 36 */     if (getRemoteVersion() == null) return VersionSource.LOCAL; 
/* 37 */     if (getRemoteVersion().getUpdatedTime().after(getLocalVersion().getUpdatedTime())) return VersionSource.REMOTE; 
/* 38 */     return VersionSource.LOCAL;
/*    */   }
/*    */   
/*    */   public boolean isInstalled() {
/* 42 */     return this.isInstalled;
/*    */   }
/*    */   
/*    */   public boolean isOnRemote() {
/* 46 */     return (this.remoteVersion != null);
/*    */   }
/*    */   
/*    */   public boolean isUpToDate() {
/* 50 */     return this.isUpToDate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 55 */     return "VersionSyncInfo{localVersion=" + this.localVersion + ", remoteVersion=" + this.remoteVersion + ", isInstalled=" + this.isInstalled + ", isUpToDate=" + this.isUpToDate + '}';
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum VersionSource
/*    */   {
/* 64 */     REMOTE, LOCAL;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\VersionSyncInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */