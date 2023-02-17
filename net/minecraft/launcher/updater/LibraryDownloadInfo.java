/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LibraryDownloadInfo
/*    */ {
/*    */   private LocalDownloadInfo artifact;
/*    */   private Map<String, DownloadInfo> classifiers;
/*    */   
/*    */   public LibraryDownloadInfo() {}
/*    */   
/*    */   public LibraryDownloadInfo(LibraryDownloadInfo other) {
/* 14 */     this.artifact = other.artifact;
/*    */     
/* 16 */     if (other.classifiers != null) {
/* 17 */       this.classifiers = new LinkedHashMap<String, DownloadInfo>();
/* 18 */       for (Map.Entry<String, DownloadInfo> entry : other.classifiers.entrySet()) {
/* 19 */         this.classifiers.put(entry.getKey(), new DownloadInfo(entry.getValue()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public AbstractDownloadInfo getDownloadInfo(String classifier) {
/* 25 */     if (classifier == null) {
/* 26 */       return this.artifact;
/*    */     }
/* 28 */     return this.classifiers.get(classifier);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\LibraryDownloadInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */