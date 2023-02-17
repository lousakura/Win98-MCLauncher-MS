/*     */ package net.minecraft.launcher.updater;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import com.mojang.launcher.versions.Version;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintWriter;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class LocalVersionList extends FileBasedVersionList {
/*  15 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final File baseDirectory;
/*     */   private final File baseVersionsDir;
/*     */   
/*     */   public LocalVersionList(File baseDirectory) {
/*  20 */     if (baseDirectory == null || !baseDirectory.isDirectory()) throw new IllegalArgumentException("Base directory is not a folder!");
/*     */     
/*  22 */     this.baseDirectory = baseDirectory;
/*  23 */     this.baseVersionsDir = new File(this.baseDirectory, "versions");
/*  24 */     if (!this.baseVersionsDir.isDirectory()) this.baseVersionsDir.mkdirs();
/*     */   
/*     */   }
/*     */   
/*     */   protected InputStream getFileInputStream(String path) throws FileNotFoundException {
/*  29 */     return new FileInputStream(new File(this.baseDirectory, path));
/*     */   }
/*     */ 
/*     */   
/*     */   public void refreshVersions() throws IOException {
/*  34 */     clearCache();
/*     */     
/*  36 */     File[] files = this.baseVersionsDir.listFiles();
/*  37 */     if (files == null)
/*     */       return; 
/*  39 */     for (File directory : files) {
/*  40 */       String id = directory.getName();
/*  41 */       File jsonFile = new File(directory, id + ".json");
/*     */       
/*  43 */       if (directory.isDirectory() && jsonFile.exists()) {
/*     */         try {
/*  45 */           String path = "versions/" + id + "/" + id + ".json";
/*  46 */           CompleteVersion version = (CompleteVersion)this.gson.fromJson(getContent(path), CompleteMinecraftVersion.class);
/*     */           
/*  48 */           if (version.getType() == null) {
/*  49 */             LOGGER.warn("Ignoring: " + path + "; it has an invalid version specified");
/*     */             
/*     */             return;
/*     */           } 
/*  53 */           if (version.getId().equals(id)) {
/*  54 */             addVersion(version);
/*     */           } else {
/*  56 */             LOGGER.warn("Ignoring: " + path + "; it contains id: '" + version.getId() + "' expected '" + id + "'");
/*     */           } 
/*  58 */         } catch (RuntimeException ex) {
/*  59 */           LOGGER.error("Couldn't load local version " + jsonFile.getAbsolutePath(), ex);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  64 */     for (Version version : getVersions()) {
/*  65 */       MinecraftReleaseType type = (MinecraftReleaseType)version.getType();
/*     */       
/*  67 */       if (getLatestVersion(type) == null || getLatestVersion(type).getUpdatedTime().before(version.getUpdatedTime())) {
/*  68 */         setLatestVersion(version);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void saveVersion(CompleteVersion version) throws IOException {
/*  74 */     String text = serializeVersion(version);
/*  75 */     File target = new File(this.baseVersionsDir, version.getId() + "/" + version.getId() + ".json");
/*  76 */     if (target.getParentFile() != null) target.getParentFile().mkdirs(); 
/*  77 */     PrintWriter writer = new PrintWriter(target);
/*  78 */     writer.print(text);
/*  79 */     writer.close();
/*     */   }
/*     */   
/*     */   public File getBaseDirectory() {
/*  83 */     return this.baseDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAllFiles(CompleteMinecraftVersion version, OperatingSystem os) {
/*  88 */     Set<String> files = version.getRequiredFiles(os);
/*     */     
/*  90 */     for (String file : files) {
/*  91 */       if (!(new File(this.baseDirectory, file)).isFile()) {
/*  92 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void uninstallVersion(Version version) {
/* 101 */     super.uninstallVersion(version);
/*     */     
/* 103 */     File dir = new File(this.baseVersionsDir, version.getId());
/* 104 */     if (dir.isDirectory())
/* 105 */       FileUtils.deleteQuietly(dir); 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\LocalVersionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */