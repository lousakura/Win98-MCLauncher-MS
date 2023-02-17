/*    */ package com.mojang.authlib.minecraft;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.io.FilenameUtils;
/*    */ import org.apache.commons.lang3.builder.ToStringBuilder;
/*    */ 
/*    */ public class MinecraftProfileTexture {
/*    */   private final String url;
/*    */   private final Map<String, String> metadata;
/*    */   
/*    */   public enum Type {
/* 12 */     SKIN,
/* 13 */     CAPE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftProfileTexture(String url, Map<String, String> metadata) {
/* 21 */     this.url = url;
/* 22 */     this.metadata = metadata;
/*    */   }
/*    */   
/*    */   public String getUrl() {
/* 26 */     return this.url;
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   public String getMetadata(String key) {
/* 31 */     if (this.metadata == null) {
/* 32 */       return null;
/*    */     }
/* 34 */     return this.metadata.get(key);
/*    */   }
/*    */   
/*    */   public String getHash() {
/* 38 */     return FilenameUtils.getBaseName(this.url);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 43 */     return (new ToStringBuilder(this)).append("url", this.url).append("hash", getHash()).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\minecraft\MinecraftProfileTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */