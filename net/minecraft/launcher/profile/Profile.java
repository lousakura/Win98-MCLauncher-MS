/*     */ package net.minecraft.launcher.profile;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.mojang.launcher.updater.VersionFilter;
/*     */ import com.mojang.launcher.versions.ReleaseType;
/*     */ import com.mojang.launcher.versions.ReleaseTypeFactory;
/*     */ import java.io.File;
/*     */ import java.util.Set;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseTypeFactory;
/*     */ 
/*     */ public class Profile implements Comparable<Profile> {
/*     */   public static final String DEFAULT_JRE_ARGUMENTS_64BIT = "-Xmx1G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
/*  15 */   public static final Resolution DEFAULT_RESOLUTION = new Resolution(854, 480); public static final String DEFAULT_JRE_ARGUMENTS_32BIT = "-Xmx512M -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M";
/*  16 */   public static final LauncherVisibilityRule DEFAULT_LAUNCHER_VISIBILITY = LauncherVisibilityRule.CLOSE_LAUNCHER;
/*  17 */   public static final Set<MinecraftReleaseType> DEFAULT_RELEASE_TYPES = Sets.newHashSet((Object[])new MinecraftReleaseType[] { MinecraftReleaseType.RELEASE });
/*     */   
/*     */   private String name;
/*     */   
/*     */   private File gameDir;
/*     */   private String lastVersionId;
/*     */   private String javaDir;
/*     */   private String javaArgs;
/*     */   private Resolution resolution;
/*     */   private Set<MinecraftReleaseType> allowedReleaseTypes;
/*     */   private String playerUUID;
/*     */   private Boolean useHopperCrashService;
/*     */   private LauncherVisibilityRule launcherVisibilityOnGameClose;
/*     */   
/*     */   public Profile() {}
/*     */   
/*     */   public Profile(Profile copy) {
/*  34 */     this.name = copy.name;
/*  35 */     this.gameDir = copy.gameDir;
/*  36 */     this.playerUUID = copy.playerUUID;
/*  37 */     this.lastVersionId = copy.lastVersionId;
/*  38 */     this.javaDir = copy.javaDir;
/*  39 */     this.javaArgs = copy.javaArgs;
/*  40 */     this.resolution = (copy.resolution == null) ? null : new Resolution(copy.resolution);
/*  41 */     this.allowedReleaseTypes = (copy.allowedReleaseTypes == null) ? null : Sets.newHashSet(copy.allowedReleaseTypes);
/*  42 */     this.useHopperCrashService = copy.useHopperCrashService;
/*  43 */     this.launcherVisibilityOnGameClose = copy.launcherVisibilityOnGameClose;
/*     */   }
/*     */   
/*     */   public Profile(String name) {
/*  47 */     this.name = name;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  51 */     return (String)Objects.firstNonNull(this.name, "");
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  55 */     this.name = name;
/*     */   }
/*     */   
/*     */   public File getGameDir() {
/*  59 */     return this.gameDir;
/*     */   }
/*     */   
/*     */   public void setGameDir(File gameDir) {
/*  63 */     this.gameDir = gameDir;
/*     */   }
/*     */   
/*     */   public void setLastVersionId(String lastVersionId) {
/*  67 */     this.lastVersionId = lastVersionId;
/*     */   }
/*     */   
/*     */   public void setJavaDir(String javaDir) {
/*  71 */     this.javaDir = javaDir;
/*     */   }
/*     */   
/*     */   public void setJavaArgs(String javaArgs) {
/*  75 */     this.javaArgs = javaArgs;
/*     */   }
/*     */   
/*     */   public String getLastVersionId() {
/*  79 */     return this.lastVersionId;
/*     */   }
/*     */   
/*     */   public String getJavaArgs() {
/*  83 */     return this.javaArgs;
/*     */   }
/*     */   
/*     */   public String getJavaPath() {
/*  87 */     return this.javaDir;
/*     */   }
/*     */   
/*     */   public Resolution getResolution() {
/*  91 */     return this.resolution;
/*     */   }
/*     */   
/*     */   public void setResolution(Resolution resolution) {
/*  95 */     this.resolution = resolution;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public String getPlayerUUID() {
/* 100 */     return this.playerUUID;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setPlayerUUID(String playerUUID) {
/* 105 */     this.playerUUID = playerUUID;
/*     */   }
/*     */   
/*     */   public Set<MinecraftReleaseType> getAllowedReleaseTypes() {
/* 109 */     return this.allowedReleaseTypes;
/*     */   }
/*     */   
/*     */   public void setAllowedReleaseTypes(Set<MinecraftReleaseType> allowedReleaseTypes) {
/* 113 */     this.allowedReleaseTypes = allowedReleaseTypes;
/*     */   }
/*     */   
/*     */   public boolean getUseHopperCrashService() {
/* 117 */     return (this.useHopperCrashService == null);
/*     */   }
/*     */   
/*     */   public void setUseHopperCrashService(boolean useHopperCrashService) {
/* 121 */     this.useHopperCrashService = useHopperCrashService ? null : Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   public VersionFilter<MinecraftReleaseType> getVersionFilter() {
/* 125 */     VersionFilter<MinecraftReleaseType> filter = (new VersionFilter((ReleaseTypeFactory)MinecraftReleaseTypeFactory.instance())).setMaxCount(2147483647);
/*     */     
/* 127 */     if (this.allowedReleaseTypes == null) {
/* 128 */       filter.onlyForTypes((ReleaseType[])DEFAULT_RELEASE_TYPES.toArray((Object[])new MinecraftReleaseType[DEFAULT_RELEASE_TYPES.size()]));
/*     */     } else {
/* 130 */       filter.onlyForTypes((ReleaseType[])this.allowedReleaseTypes.toArray((Object[])new MinecraftReleaseType[this.allowedReleaseTypes.size()]));
/*     */     } 
/*     */     
/* 133 */     return filter;
/*     */   }
/*     */   
/*     */   public LauncherVisibilityRule getLauncherVisibilityOnGameClose() {
/* 137 */     return this.launcherVisibilityOnGameClose;
/*     */   }
/*     */   
/*     */   public void setLauncherVisibilityOnGameClose(LauncherVisibilityRule launcherVisibilityOnGameClose) {
/* 141 */     this.launcherVisibilityOnGameClose = launcherVisibilityOnGameClose;
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Profile o) {
/* 146 */     if (o == null) {
/* 147 */       return -1;
/*     */     }
/* 149 */     return getName().compareTo(o.getName());
/*     */   }
/*     */   
/*     */   public static class Resolution
/*     */   {
/*     */     private int width;
/*     */     private int height;
/*     */     
/*     */     public Resolution() {}
/*     */     
/*     */     public Resolution(Resolution resolution) {
/* 160 */       this(resolution.getWidth(), resolution.getHeight());
/*     */     }
/*     */     
/*     */     public Resolution(int width, int height) {
/* 164 */       this.width = width;
/* 165 */       this.height = height;
/*     */     }
/*     */     
/*     */     public int getWidth() {
/* 169 */       return this.width;
/*     */     }
/*     */     
/*     */     public int getHeight() {
/* 173 */       return this.height;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\profile\Profile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */