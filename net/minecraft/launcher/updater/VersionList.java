/*     */ package net.minecraft.launcher.updater;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.updater.DateTypeAdapter;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import com.mojang.launcher.versions.ReleaseType;
/*     */ import com.mojang.launcher.versions.ReleaseTypeAdapterFactory;
/*     */ import com.mojang.launcher.versions.ReleaseTypeFactory;
/*     */ import com.mojang.launcher.versions.Version;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*     */ 
/*     */ public abstract class VersionList {
/*  21 */   protected final Map<String, Version> versionsByName = new HashMap<String, Version>(); protected final Gson gson;
/*  22 */   protected final List<Version> versions = new ArrayList<Version>();
/*  23 */   protected final Map<MinecraftReleaseType, Version> latestVersions = Maps.newEnumMap(MinecraftReleaseType.class);
/*     */   
/*     */   public VersionList() {
/*  26 */     GsonBuilder builder = new GsonBuilder();
/*  27 */     builder.registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory());
/*  28 */     builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
/*  29 */     builder.registerTypeAdapter(ReleaseType.class, new ReleaseTypeAdapterFactory((ReleaseTypeFactory)MinecraftReleaseTypeFactory.instance()));
/*  30 */     builder.registerTypeAdapter(Argument.class, new Argument.Serializer());
/*  31 */     builder.enableComplexMapKeySerialization();
/*  32 */     builder.setPrettyPrinting();
/*     */     
/*  34 */     this.gson = builder.create();
/*     */   }
/*     */   
/*     */   public Collection<Version> getVersions() {
/*  38 */     return this.versions;
/*     */   }
/*     */   
/*     */   public Version getLatestVersion(MinecraftReleaseType type) {
/*  42 */     if (type == null) throw new IllegalArgumentException("Type cannot be null"); 
/*  43 */     return this.latestVersions.get(type);
/*     */   }
/*     */   
/*     */   public Version getVersion(String name) {
/*  47 */     if (name == null || name.length() == 0) throw new IllegalArgumentException("Name cannot be null or empty"); 
/*  48 */     return this.versionsByName.get(name);
/*     */   }
/*     */   
/*     */   public abstract CompleteMinecraftVersion getCompleteVersion(Version paramVersion) throws IOException;
/*     */   
/*     */   protected void replacePartialWithFull(PartialVersion version, CompleteVersion complete) {
/*  54 */     Collections.replaceAll(this.versions, version, complete);
/*  55 */     this.versionsByName.put(version.getId(), complete);
/*     */     
/*  57 */     if (this.latestVersions.get(version.getType()) == version) {
/*  58 */       this.latestVersions.put(version.getType(), complete);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void clearCache() {
/*  63 */     this.versionsByName.clear();
/*  64 */     this.versions.clear();
/*  65 */     this.latestVersions.clear();
/*     */   }
/*     */   
/*     */   public abstract void refreshVersions() throws IOException;
/*     */   
/*     */   public CompleteVersion addVersion(CompleteVersion version) {
/*  71 */     if (version.getId() == null) throw new IllegalArgumentException("Cannot add blank version"); 
/*  72 */     if (getVersion(version.getId()) != null) throw new IllegalArgumentException("Version '" + version.getId() + "' is already tracked");
/*     */     
/*  74 */     this.versions.add(version);
/*  75 */     this.versionsByName.put(version.getId(), version);
/*     */     
/*  77 */     return version;
/*     */   }
/*     */   
/*     */   public void removeVersion(String name) {
/*  81 */     if (name == null || name.length() == 0) throw new IllegalArgumentException("Name cannot be null or empty"); 
/*  82 */     Version version = getVersion(name);
/*  83 */     if (version == null) throw new IllegalArgumentException("Unknown version - cannot remove null"); 
/*  84 */     removeVersion(version);
/*     */   }
/*     */   
/*     */   public void removeVersion(Version version) {
/*  88 */     if (version == null) throw new IllegalArgumentException("Cannot remove null version"); 
/*  89 */     this.versions.remove(version);
/*  90 */     this.versionsByName.remove(version.getId());
/*     */     
/*  92 */     for (MinecraftReleaseType type : MinecraftReleaseType.values()) {
/*  93 */       if (getLatestVersion(type) == version) {
/*  94 */         this.latestVersions.remove(type);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setLatestVersion(Version version) {
/* 100 */     if (version == null) throw new IllegalArgumentException("Cannot set latest version to null"); 
/* 101 */     this.latestVersions.put((MinecraftReleaseType)version.getType(), version);
/*     */   }
/*     */   
/*     */   public void setLatestVersion(String name) {
/* 105 */     if (name == null || name.length() == 0) throw new IllegalArgumentException("Name cannot be null or empty"); 
/* 106 */     Version version = getVersion(name);
/* 107 */     if (version == null) throw new IllegalArgumentException("Unknown version - cannot set latest version to null"); 
/* 108 */     setLatestVersion(version);
/*     */   }
/*     */   
/*     */   public String serializeVersion(CompleteVersion version) {
/* 112 */     if (version == null) throw new IllegalArgumentException("Cannot serialize null!"); 
/* 113 */     return this.gson.toJson(version);
/*     */   }
/*     */   
/*     */   public abstract boolean hasAllFiles(CompleteMinecraftVersion paramCompleteMinecraftVersion, OperatingSystem paramOperatingSystem);
/*     */   
/*     */   public void uninstallVersion(Version version) {
/* 119 */     removeVersion(version);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\VersionList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */