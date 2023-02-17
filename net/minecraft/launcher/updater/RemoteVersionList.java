/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.launcher.Http;
/*    */ import com.mojang.launcher.OperatingSystem;
/*    */ import com.mojang.launcher.versions.Version;
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*    */ 
/*    */ public class RemoteVersionList
/*    */   extends VersionList
/*    */ {
/*    */   private final URL manifestUrl;
/*    */   private final Proxy proxy;
/*    */   
/*    */   public RemoteVersionList(URL manifestUrl, Proxy proxy) {
/* 22 */     this.manifestUrl = manifestUrl;
/* 23 */     this.proxy = proxy;
/*    */   }
/*    */ 
/*    */   
/*    */   public CompleteMinecraftVersion getCompleteVersion(Version version) throws IOException {
/* 28 */     if (version instanceof com.mojang.launcher.versions.CompleteVersion) return (CompleteMinecraftVersion)version; 
/* 29 */     if (!(version instanceof PartialVersion)) throw new IllegalArgumentException("Version must be a partial"); 
/* 30 */     PartialVersion partial = (PartialVersion)version;
/*    */     
/* 32 */     CompleteMinecraftVersion complete = (CompleteMinecraftVersion)this.gson.fromJson(Http.performGet(partial.getUrl(), this.proxy), CompleteMinecraftVersion.class);
/* 33 */     replacePartialWithFull(partial, complete);
/*    */     
/* 35 */     return complete;
/*    */   }
/*    */ 
/*    */   
/*    */   public void refreshVersions() throws IOException {
/* 40 */     clearCache();
/*    */     
/* 42 */     RawVersionList versionList = (RawVersionList)this.gson.fromJson(getContent(this.manifestUrl), RawVersionList.class);
/*    */     
/* 44 */     for (Version version : versionList.getVersions()) {
/* 45 */       this.versions.add(version);
/* 46 */       this.versionsByName.put(version.getId(), version);
/*    */     } 
/*    */     
/* 49 */     for (MinecraftReleaseType type : MinecraftReleaseType.values()) {
/* 50 */       this.latestVersions.put(type, this.versionsByName.get(versionList.getLatestVersions().get(type)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasAllFiles(CompleteMinecraftVersion version, OperatingSystem os) {
/* 56 */     return true;
/*    */   }
/*    */   
/*    */   public String getContent(URL url) throws IOException {
/* 60 */     return Http.performGet(url, this.proxy);
/*    */   }
/*    */   
/*    */   public Proxy getProxy() {
/* 64 */     return this.proxy;
/*    */   }
/*    */   
/*    */   private static class RawVersionList {
/* 68 */     private List<PartialVersion> versions = new ArrayList<PartialVersion>();
/* 69 */     private Map<MinecraftReleaseType, String> latest = Maps.newEnumMap(MinecraftReleaseType.class);
/*    */     
/*    */     public List<PartialVersion> getVersions() {
/* 72 */       return this.versions;
/*    */     }
/*    */     
/*    */     public Map<MinecraftReleaseType, String> getLatestVersions() {
/* 76 */       return this.latest;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\RemoteVersionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */