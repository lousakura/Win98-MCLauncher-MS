/*    */ package com.mojang.launcher.updater.download;
/*    */ 
/*    */ public class ProgressContainer {
/*    */   private long total;
/*    */   private long current;
/*    */   private DownloadJob job;
/*    */   
/*    */   public DownloadJob getJob() {
/*  9 */     return this.job;
/*    */   }
/*    */   
/*    */   public void setJob(DownloadJob job) {
/* 13 */     this.job = job;
/* 14 */     if (job != null) job.updateProgress(); 
/*    */   }
/*    */   
/*    */   public long getTotal() {
/* 18 */     return this.total;
/*    */   }
/*    */   
/*    */   public void setTotal(long total) {
/* 22 */     this.total = total;
/* 23 */     if (this.job != null) this.job.updateProgress(); 
/*    */   }
/*    */   
/*    */   public long getCurrent() {
/* 27 */     return this.current;
/*    */   }
/*    */   
/*    */   public void setCurrent(long current) {
/* 31 */     this.current = current;
/* 32 */     if (current > this.total) this.total = current; 
/* 33 */     if (this.job != null) this.job.updateProgress(); 
/*    */   }
/*    */   
/*    */   public void addProgress(long amount) {
/* 37 */     setCurrent(getCurrent() + amount);
/*    */   }
/*    */   
/*    */   public float getProgress() {
/* 41 */     if (this.total == 0L) return 0.0F; 
/* 42 */     return (float)this.current / (float)this.total;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 47 */     return "ProgressContainer{current=" + this.current + ", total=" + this.total + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\ProgressContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */