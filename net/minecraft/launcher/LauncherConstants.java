/*     */ package net.minecraft.launcher;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.TypeAdapterFactory;
/*     */ import com.mojang.launcher.updater.LowerCaseEnumTypeAdapterFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.net.URL;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LauncherConstants
/*     */ {
/*     */   public static final int VERSION_FORMAT = 21;
/*     */   public static final int PROFILES_FORMAT = 1;
/*  22 */   public static final URI URL_REGISTER = constantURI("https://account.mojang.com/register");
/*     */   
/*     */   public static final String URL_JAR_FALLBACK = "https://s3.amazonaws.com/Minecraft.Download/";
/*     */   
/*     */   public static final String URL_RESOURCE_BASE = "http://resources.download.minecraft.net/";
/*     */   public static final String URL_LIBRARY_BASE = "https://libraries.minecraft.net/";
/*     */   public static final String URL_BLOG = "http://mcupdate.tumblr.com";
/*     */   public static final String URL_SUPPORT = "http://help.mojang.com/?ref=launcher";
/*     */   public static final String URL_STATUS_CHECKER = "http://status.mojang.com/check";
/*     */   public static final int UNVERSIONED_BOOTSTRAP_VERSION = 0;
/*     */   public static final int MINIMUM_BOOTSTRAP_SUPPORTED = 4;
/*     */   public static final int SUPER_COOL_BOOTSTRAP_VERSION = 100;
/*     */   public static final String URL_BOOTSTRAP_DOWNLOAD = "https://mojang.com/2013/06/minecraft-1-6-pre-release/";
/*  35 */   public static final String[] BOOTSTRAP_OUT_OF_DATE_BUTTONS = new String[] { "Go to URL", "Close" };
/*     */   
/*     */   public static final String LAUNCHER_OUT_OF_DATE_MESSAGE = "It looks like you've used a newer launcher than this one! If you go back to using this one, we will need to reset your configuration.";
/*  38 */   public static final String[] LAUNCHER_OUT_OF_DATE_BUTTONS = new String[] { "Nevermind, close this launcher", "I'm sure. Reset my settings." };
/*     */   
/*     */   public static final String LAUNCHER_NOT_NATIVE_MESSAGE = "This shortcut to the launcher is out of date. Please delete it and remake it to the new launcher, which we will start for you now.";
/*     */   
/*  42 */   public static final String[] CONFIRM_PROFILE_DELETION_OPTIONS = new String[] { "Delete profile", "Cancel" };
/*     */   
/*  44 */   public static final URI URL_FORGOT_USERNAME = constantURI("http://help.mojang.com/customer/portal/articles/1233873?ref=launcher");
/*  45 */   public static final URI URL_FORGOT_PASSWORD_MINECRAFT = constantURI("http://help.mojang.com/customer/portal/articles/329524-change-or-forgot-password?ref=launcher");
/*  46 */   public static final URI URL_FORGOT_MIGRATED_EMAIL = constantURI("http://help.mojang.com/customer/portal/articles/1205055-minecraft-launcher-error---migrated-account?ref=launcher");
/*  47 */   public static final URI URL_DEMO_HELP = constantURI("https://help.mojang.com/customer/portal/articles/1218766-can-only-play-minecraft-demo?ref=launcher");
/*  48 */   public static final URI URL_UPGRADE_WINDOWS = constantURI("https://launcher.mojang.com/download/MinecraftInstaller.msi");
/*  49 */   public static final URI URL_UPGRADE_OSX = constantURI("https://launcher.mojang.com/download/Minecraft.dmg");
/*     */   
/*     */   public static final int MAX_NATIVES_LIFE_IN_SECONDS = 3600;
/*     */   
/*     */   public static final int MAX_SKIN_LIFE_IN_SECONDS = 604800;
/*     */ 
/*     */   
/*     */   public static URI constantURI(String input) {
/*     */     try {
/*  58 */       return new URI(input);
/*  59 */     } catch (URISyntaxException e) {
/*  60 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static URL constantURL(String input) {
/*     */     try {
/*  66 */       return new URL(input);
/*  67 */     } catch (MalformedURLException e) {
/*  68 */       throw new Error(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String getVersionName() {
/*  73 */     return (String)Objects.firstNonNull(LauncherConstants.class.getPackage().getImplementationVersion(), "unknown");
/*     */   }
/*     */   
/*  76 */   public static final LauncherProperties PROPERTIES = getProperties();
/*     */   
/*     */   private static LauncherProperties getProperties() {
/*  79 */     Gson gson = (new GsonBuilder()).registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory()).create();
/*  80 */     InputStream stream = LauncherConstants.class.getResourceAsStream("/launcher_properties.json");
/*  81 */     if (stream != null) {
/*     */       try {
/*  83 */         return (LauncherProperties)gson.fromJson(IOUtils.toString(stream), LauncherProperties.class);
/*  84 */       } catch (IOException e) {
/*  85 */         e.printStackTrace();
/*     */       } finally {
/*  87 */         IOUtils.closeQuietly(stream);
/*     */       } 
/*     */     }
/*  90 */     return new LauncherProperties();
/*     */   }
/*     */   
/*     */   public enum LauncherEnvironment {
/*  94 */     PRODUCTION(""),
/*  95 */     STAGING(" (STAGING VERSION, NOT FINAL)"),
/*  96 */     DEV(" (DEV VERSION, NOT FINAL)");
/*     */     
/*     */     private final String title;
/*     */ 
/*     */     
/*     */     LauncherEnvironment(String title) {
/* 102 */       this.title = title;
/*     */     }
/*     */     
/*     */     public String getTitle() {
/* 106 */       return this.title;
/*     */     } }
/*     */   public static class LauncherProperties { private LauncherConstants.LauncherEnvironment environment;
/*     */     
/*     */     public LauncherProperties() {
/* 111 */       this.environment = LauncherConstants.LauncherEnvironment.PRODUCTION;
/* 112 */       this.versionManifest = LauncherConstants.constantURL("https://launchermeta.mojang.com/mc/game/version_manifest.json");
/*     */     } private URL versionManifest;
/*     */     public LauncherConstants.LauncherEnvironment getEnvironment() {
/* 115 */       return this.environment;
/*     */     }
/*     */     
/*     */     public URL getVersionManifest() {
/* 119 */       return this.versionManifest;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\LauncherConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */