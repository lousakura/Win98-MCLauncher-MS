/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import com.mojang.launcher.versions.Version;
/*    */ import java.io.FileNotFoundException;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public abstract class FileBasedVersionList
/*    */   extends VersionList
/*    */ {
/*    */   public String getContent(String path) throws IOException {
/* 14 */     return IOUtils.toString(getFileInputStream(path)).replaceAll("\\r\\n", "\r").replaceAll("\\r", "\n");
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract InputStream getFileInputStream(String paramString) throws FileNotFoundException;
/*    */   
/*    */   public CompleteMinecraftVersion getCompleteVersion(Version version) throws IOException {
/* 21 */     if (version instanceof com.mojang.launcher.versions.CompleteVersion) return (CompleteMinecraftVersion)version; 
/* 22 */     if (!(version instanceof PartialVersion)) throw new IllegalArgumentException("Version must be a partial"); 
/* 23 */     PartialVersion partial = (PartialVersion)version;
/*    */     
/* 25 */     CompleteMinecraftVersion complete = (CompleteMinecraftVersion)this.gson.fromJson(getContent("versions/" + version.getId() + "/" + version.getId() + ".json"), CompleteMinecraftVersion.class);
/* 26 */     MinecraftReleaseType type = (MinecraftReleaseType)version.getType();
/*    */     
/* 28 */     replacePartialWithFull(partial, complete);
/*    */     
/* 30 */     return complete;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\FileBasedVersionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */