/*    */ package com.mojang.launcher.updater.download;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class MonitoringInputStream extends FilterInputStream {
/*    */   private final ProgressContainer monitor;
/*    */   
/*    */   public MonitoringInputStream(InputStream in, ProgressContainer monitor) {
/* 11 */     super(in);
/* 12 */     this.monitor = monitor;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read() throws IOException {
/* 17 */     int result = this.in.read();
/*    */     
/* 19 */     if (result >= 0) {
/* 20 */       this.monitor.addProgress(1L);
/*    */     }
/*    */     
/* 23 */     return result;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] buffer) throws IOException {
/* 28 */     int size = this.in.read(buffer);
/*    */     
/* 30 */     if (size >= 0) {
/* 31 */       this.monitor.addProgress(size);
/*    */     }
/*    */     
/* 34 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public int read(byte[] buffer, int off, int len) throws IOException {
/* 39 */     int size = this.in.read(buffer, off, len);
/*    */     
/* 41 */     if (size > 0) {
/* 42 */       this.monitor.addProgress(size);
/*    */     }
/*    */     
/* 45 */     return size;
/*    */   }
/*    */ 
/*    */   
/*    */   public long skip(long size) throws IOException {
/* 50 */     long skipped = super.skip(size);
/*    */     
/* 52 */     if (skipped > 0L) {
/* 53 */       this.monitor.addProgress(skipped);
/*    */     }
/*    */     
/* 56 */     return skipped;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\MonitoringInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */