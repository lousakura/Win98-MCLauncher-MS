/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import com.mojang.launcher.versions.ReleaseType;
/*    */ import com.mojang.launcher.versions.Version;
/*    */ import java.net.URL;
/*    */ import java.util.Date;
/*    */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*    */ 
/*    */ public class PartialVersion
/*    */   implements Version {
/*    */   private String id;
/*    */   private Date time;
/*    */   private Date releaseTime;
/*    */   private MinecraftReleaseType type;
/*    */   private URL url;
/*    */   
/*    */   public PartialVersion() {}
/*    */   
/*    */   public PartialVersion(String id, Date releaseTime, Date updateTime, MinecraftReleaseType type, URL url) {
/* 20 */     if (id == null || id.length() == 0) throw new IllegalArgumentException("ID cannot be null or empty"); 
/* 21 */     if (releaseTime == null) throw new IllegalArgumentException("Release time cannot be null"); 
/* 22 */     if (updateTime == null) throw new IllegalArgumentException("Update time cannot be null"); 
/* 23 */     if (type == null) throw new IllegalArgumentException("Release type cannot be null"); 
/* 24 */     this.id = id;
/* 25 */     this.releaseTime = releaseTime;
/* 26 */     this.time = updateTime;
/* 27 */     this.type = type;
/* 28 */     this.url = url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 33 */     return this.id;
/*    */   }
/*    */ 
/*    */   
/*    */   public MinecraftReleaseType getType() {
/* 38 */     return this.type;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date getUpdatedTime() {
/* 43 */     return this.time;
/*    */   }
/*    */   
/*    */   public void setUpdatedTime(Date time) {
/* 47 */     if (time == null) throw new IllegalArgumentException("Time cannot be null"); 
/* 48 */     this.time = time;
/*    */   }
/*    */ 
/*    */   
/*    */   public Date getReleaseTime() {
/* 53 */     return this.releaseTime;
/*    */   }
/*    */   
/*    */   public void setReleaseTime(Date time) {
/* 57 */     if (time == null) throw new IllegalArgumentException("Time cannot be null"); 
/* 58 */     this.releaseTime = time;
/*    */   }
/*    */   
/*    */   public void setType(MinecraftReleaseType type) {
/* 62 */     if (type == null) throw new IllegalArgumentException("Release type cannot be null"); 
/* 63 */     this.type = type;
/*    */   }
/*    */   
/*    */   public URL getUrl() {
/* 67 */     return this.url;
/*    */   }
/*    */   
/*    */   public void setUrl(URL url) {
/* 71 */     this.url = url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 76 */     return "PartialVersion{id='" + this.id + '\'' + ", updateTime=" + this.time + ", releaseTime=" + this.releaseTime + ", type=" + this.type + ", url=" + this.url + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\PartialVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */