/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import java.net.URL;
/*    */ 
/*    */ public class DownloadInfo
/*    */   extends AbstractDownloadInfo {
/*    */   protected URL url;
/*    */   protected String sha1;
/*    */   protected int size;
/*    */   
/*    */   public DownloadInfo() {}
/*    */   
/*    */   public DownloadInfo(DownloadInfo other) {
/* 14 */     this.url = other.url;
/* 15 */     this.sha1 = other.sha1;
/* 16 */     this.size = other.size;
/*    */   }
/*    */   
/*    */   public URL getUrl() {
/* 20 */     return this.url;
/*    */   }
/*    */   
/*    */   public String getSha1() {
/* 24 */     return this.sha1;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 28 */     return this.size;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\DownloadInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */