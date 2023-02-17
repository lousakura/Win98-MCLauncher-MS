/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ public class DownloadProgress {
/*    */   private final long current;
/*    */   private final long total;
/*    */   private final float percent;
/*    */   private final String status;
/*    */   
/*    */   public DownloadProgress(long current, long total, String status) {
/* 10 */     this.current = current;
/* 11 */     this.total = total;
/* 12 */     this.percent = (float)current / (float)total;
/* 13 */     this.status = status;
/*    */   }
/*    */   
/*    */   public long getCurrent() {
/* 17 */     return this.current;
/*    */   }
/*    */   
/*    */   public long getTotal() {
/* 21 */     return this.total;
/*    */   }
/*    */   
/*    */   public float getPercent() {
/* 25 */     return this.percent;
/*    */   }
/*    */   
/*    */   public String getStatus() {
/* 29 */     return this.status;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\DownloadProgress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */