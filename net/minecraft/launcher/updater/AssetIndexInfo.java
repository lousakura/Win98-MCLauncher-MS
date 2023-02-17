/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import net.minecraft.launcher.LauncherConstants;
/*    */ 
/*    */ public class AssetIndexInfo
/*    */   extends DownloadInfo {
/*    */   protected long totalSize;
/*    */   protected String id;
/*    */   protected boolean known = true;
/*    */   
/*    */   public AssetIndexInfo() {}
/*    */   
/*    */   public AssetIndexInfo(String id) {
/* 14 */     this.id = id;
/* 15 */     this.url = LauncherConstants.constantURL("https://s3.amazonaws.com/Minecraft.Download/indexes/" + id + ".json");
/* 16 */     this.known = false;
/*    */   }
/*    */   
/*    */   public long getTotalSize() {
/* 20 */     return this.totalSize;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 24 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean sizeAndHashKnown() {
/* 28 */     return this.known;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\AssetIndexInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */