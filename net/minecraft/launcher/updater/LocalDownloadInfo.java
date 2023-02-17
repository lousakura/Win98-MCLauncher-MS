/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ public class LocalDownloadInfo
/*    */   extends AbstractDownloadInfo
/*    */ {
/*    */   protected String url;
/*    */   protected String sha1;
/*    */   protected int size;
/*    */   
/*    */   public LocalDownloadInfo() {}
/*    */   
/*    */   public LocalDownloadInfo(LocalDownloadInfo other) {
/* 17 */     this.url = other.url;
/* 18 */     this.sha1 = other.sha1;
/* 19 */     this.size = other.size;
/*    */   }
/*    */   
/*    */   public URL getUrl() {
/*    */     try {
/* 24 */       return new URL(this.url);
/* 25 */     } catch (MalformedURLException e) {
/* 26 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getSha1() {
/* 31 */     return this.sha1;
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 35 */     return this.size;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\LocalDownloadInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */