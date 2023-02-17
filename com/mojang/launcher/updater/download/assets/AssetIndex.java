/*    */ package com.mojang.launcher.updater.download.assets;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class AssetIndex {
/*    */   public static final String DEFAULT_ASSET_NAME = "legacy";
/*  9 */   private Map<String, AssetObject> objects = new LinkedHashMap<String, AssetObject>();
/*    */   private boolean virtual;
/*    */   
/*    */   public Map<String, AssetObject> getFileMap() {
/* 13 */     return this.objects;
/*    */   }
/*    */   
/*    */   public Map<AssetObject, String> getUniqueObjects() {
/* 17 */     Map<AssetObject, String> result = Maps.newHashMap();
/*    */     
/* 19 */     for (Map.Entry<String, AssetObject> objectEntry : this.objects.entrySet()) {
/* 20 */       result.put(objectEntry.getValue(), objectEntry.getKey());
/*    */     }
/*    */     
/* 23 */     return result;
/*    */   }
/*    */   
/*    */   public boolean isVirtual() {
/* 27 */     return this.virtual;
/*    */   }
/*    */   
/*    */   public class AssetObject {
/*    */     private String hash;
/*    */     private long size;
/*    */     private boolean reconstruct;
/*    */     private String compressedHash;
/*    */     private long compressedSize;
/*    */     
/*    */     public String getHash() {
/* 38 */       return this.hash;
/*    */     }
/*    */     
/*    */     public long getSize() {
/* 42 */       return this.size;
/*    */     }
/*    */     
/*    */     public boolean shouldReconstruct() {
/* 46 */       return this.reconstruct;
/*    */     }
/*    */     
/*    */     public boolean hasCompressedAlternative() {
/* 50 */       return (this.compressedHash != null);
/*    */     }
/*    */     
/*    */     public String getCompressedHash() {
/* 54 */       return this.compressedHash;
/*    */     }
/*    */     
/*    */     public long getCompressedSize() {
/* 58 */       return this.compressedSize;
/*    */     }
/*    */ 
/*    */     
/*    */     public boolean equals(Object o) {
/* 63 */       if (this == o) return true; 
/* 64 */       if (o == null || getClass() != o.getClass()) return false;
/*    */       
/* 66 */       AssetObject that = (AssetObject)o;
/*    */       
/* 68 */       if (this.compressedSize != that.compressedSize) return false; 
/* 69 */       if (this.reconstruct != that.reconstruct) return false; 
/* 70 */       if (this.size != that.size) return false; 
/* 71 */       if ((this.compressedHash != null) ? !this.compressedHash.equals(that.compressedHash) : (that.compressedHash != null)) return false; 
/* 72 */       if ((this.hash != null) ? !this.hash.equals(that.hash) : (that.hash != null)) return false;
/*    */       
/* 74 */       return true;
/*    */     }
/*    */ 
/*    */     
/*    */     public int hashCode() {
/* 79 */       int result = (this.hash != null) ? this.hash.hashCode() : 0;
/* 80 */       result = 31 * result + (int)(this.size ^ this.size >>> 32L);
/* 81 */       result = 31 * result + (this.reconstruct ? 1 : 0);
/* 82 */       result = 31 * result + ((this.compressedHash != null) ? this.compressedHash.hashCode() : 0);
/* 83 */       result = 31 * result + (int)(this.compressedSize ^ this.compressedSize >>> 32L);
/* 84 */       return result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\assets\AssetIndex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */