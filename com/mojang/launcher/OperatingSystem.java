/*     */ package com.mojang.launcher;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public enum OperatingSystem
/*     */ {
/*  11 */   LINUX("linux", new String[] { "linux", "unix" }),
/*  12 */   WINDOWS("windows", new String[] { "win" }),
/*  13 */   OSX("osx", new String[] { "mac" }),
/*  14 */   UNKNOWN("unknown", new String[0]); private static final Logger LOGGER;
/*     */   static {
/*  16 */     LOGGER = LogManager.getLogger();
/*     */   }
/*     */   private final String name; private final String[] aliases;
/*     */   
/*     */   OperatingSystem(String name, String... aliases) {
/*  21 */     this.name = name;
/*  22 */     this.aliases = (aliases == null) ? new String[0] : aliases;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  26 */     return this.name;
/*     */   }
/*     */   
/*     */   public String[] getAliases() {
/*  30 */     return this.aliases;
/*     */   }
/*     */   
/*     */   public boolean isSupported() {
/*  34 */     return (this != UNKNOWN);
/*     */   }
/*     */   
/*     */   public String getJavaDir() {
/*  38 */     String separator = System.getProperty("file.separator");
/*  39 */     String path = System.getProperty("java.home") + separator + "bin" + separator;
/*     */     
/*  41 */     if (getCurrentPlatform() == WINDOWS && (
/*  42 */       new File(path + "javaw.exe")).isFile()) {
/*  43 */       return path + "javaw.exe";
/*     */     }
/*     */ 
/*     */     
/*  47 */     return path + "java";
/*     */   }
/*     */   
/*     */   public static OperatingSystem getCurrentPlatform() {
/*  51 */     String osName = System.getProperty("os.name").toLowerCase();
/*     */     
/*  53 */     for (OperatingSystem os : values()) {
/*  54 */       for (String alias : os.getAliases()) {
/*  55 */         if (osName.contains(alias)) return os;
/*     */       
/*     */       } 
/*     */     } 
/*  59 */     return UNKNOWN;
/*     */   }
/*     */   
/*     */   public static void openLink(URI link) {
/*     */     try {
/*  64 */       Class<?> desktopClass = Class.forName("java.awt.Desktop");
/*  65 */       Object o = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/*  66 */       desktopClass.getMethod("browse", new Class[] { URI.class }).invoke(o, new Object[] { link });
/*  67 */     } catch (Throwable e) {
/*  68 */       if (getCurrentPlatform() == OSX) {
/*     */         try {
/*  70 */           Runtime.getRuntime().exec(new String[] { "/usr/bin/open", link
/*  71 */                 .toString() });
/*     */         }
/*  73 */         catch (IOException e1) {
/*  74 */           LOGGER.error("Failed to open link " + link.toString(), e1);
/*     */         } 
/*     */       } else {
/*  77 */         LOGGER.error("Failed to open link " + link.toString(), e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void openFolder(File path) {
/*  83 */     String absolutePath = path.getAbsolutePath();
/*  84 */     OperatingSystem os = getCurrentPlatform();
/*     */     
/*  86 */     if (os == OSX) {
/*     */       try {
/*  88 */         Runtime.getRuntime().exec(new String[] { "/usr/bin/open", absolutePath });
/*     */ 
/*     */         
/*     */         return;
/*  92 */       } catch (IOException e) {
/*  93 */         LOGGER.error("Couldn't open " + path + " through /usr/bin/open", e);
/*     */       } 
/*  95 */     } else if (os == WINDOWS) {
/*     */ 
/*     */       
/*  98 */       String cmd = String.format("cmd.exe /C start \"Open file\" \"%s\"", new Object[] { absolutePath });
/*     */       try {
/* 100 */         Runtime.getRuntime().exec(cmd);
/*     */         return;
/* 102 */       } catch (IOException e) {
/* 103 */         LOGGER.error("Couldn't open " + path + " through cmd.exe", e);
/*     */       } 
/*     */     } 
/*     */     
/*     */     try {
/* 108 */       Class<?> desktopClass = Class.forName("java.awt.Desktop");
/* 109 */       Object desktop = desktopClass.getMethod("getDesktop", new Class[0]).invoke(null, new Object[0]);
/* 110 */       desktopClass.getMethod("browse", new Class[] { URI.class }).invoke(desktop, new Object[] { path.toURI() });
/* 111 */     } catch (Throwable e) {
/* 112 */       LOGGER.error("Couldn't open " + path + " through Desktop.browse()", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\OperatingSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */