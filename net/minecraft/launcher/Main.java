/*     */ package net.minecraft.launcher;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.net.Proxy;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.JFrame;
/*     */ import joptsimple.ArgumentAcceptingOptionSpec;
/*     */ import joptsimple.NonOptionArgumentSpec;
/*     */ import joptsimple.OptionParser;
/*     */ import joptsimple.OptionSet;
/*     */ import joptsimple.OptionSpec;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class Main {
/*  20 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   public static void main(String[] args) {
/*  23 */     LOGGER.debug("main() called!");
/*  24 */     startLauncher(args);
/*     */   }
/*     */   
/*     */   private static void startLauncher(String[] args) {
/*  28 */     OptionParser parser = new OptionParser();
/*  29 */     parser.allowsUnrecognizedOptions();
/*  30 */     parser.accepts("winTen");
/*  31 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec1 = parser.accepts("proxyHost").withRequiredArg();
/*  32 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec2 = parser.accepts("proxyPort").withRequiredArg().defaultsTo("8080", (Object[])new String[0]).ofType(Integer.class);
/*  33 */     ArgumentAcceptingOptionSpec argumentAcceptingOptionSpec3 = parser.accepts("workDir").withRequiredArg().ofType(File.class).defaultsTo(getWorkingDirectory(), (Object[])new File[0]);
/*  34 */     NonOptionArgumentSpec nonOptionArgumentSpec = parser.nonOptions();
/*  35 */     OptionSet optionSet = parser.parse(args);
/*  36 */     List<String> leftoverArgs = optionSet.valuesOf((OptionSpec)nonOptionArgumentSpec);
/*     */ 
/*     */     
/*  39 */     String hostName = (String)optionSet.valueOf((OptionSpec)argumentAcceptingOptionSpec1);
/*  40 */     Proxy proxy = Proxy.NO_PROXY;
/*  41 */     if (hostName != null) {
/*     */       try {
/*  43 */         proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(hostName, ((Integer)optionSet.valueOf((OptionSpec)argumentAcceptingOptionSpec2)).intValue()));
/*  44 */       } catch (Exception exception) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  49 */     File workingDirectory = (File)optionSet.valueOf((OptionSpec)argumentAcceptingOptionSpec3);
/*  50 */     workingDirectory.mkdirs();
/*     */     
/*  52 */     LOGGER.debug("About to create JFrame.");
/*     */ 
/*     */     
/*  55 */     Proxy finalProxy = proxy;
/*  56 */     JFrame frame = new JFrame();
/*  57 */     frame.setTitle("Minecraft Launcher " + LauncherConstants.getVersionName() + LauncherConstants.PROPERTIES.getEnvironment().getTitle());
/*  58 */     frame.setPreferredSize(new Dimension(900, 580));
/*     */     try {
/*  60 */       InputStream in = Launcher.class.getResourceAsStream("/favicon.png");
/*  61 */       if (in != null) {
/*  62 */         frame.setIconImage(ImageIO.read(in));
/*     */       }
/*  64 */     } catch (IOException iOException) {}
/*     */     
/*  66 */     frame.pack();
/*  67 */     frame.setLocationRelativeTo((Component)null);
/*  68 */     frame.setVisible(true);
/*     */     
/*  70 */     if (optionSet.has("winTen")) {
/*  71 */       System.setProperty("os.name", "Windows 10");
/*  72 */       System.setProperty("os.version", "10.0");
/*     */     } 
/*  74 */     LOGGER.debug("Starting up launcher.");
/*  75 */     Launcher launcher = new Launcher(frame, workingDirectory, finalProxy, null, leftoverArgs.<String>toArray(new String[leftoverArgs.size()]), Integer.valueOf(100));
/*     */     
/*  77 */     if (optionSet.has("winTen")) {
/*  78 */       launcher.setWinTenHack();
/*     */     }
/*  80 */     frame.setLocationRelativeTo((Component)null);
/*     */     
/*  82 */     LOGGER.debug("End of main.");
/*     */   }
/*     */   
/*     */   public static File getWorkingDirectory() {
/*  86 */     String applicationData, folder, userHome = System.getProperty("user.home", ".");
/*     */ 
/*     */     
/*  89 */     switch (OperatingSystem.getCurrentPlatform())
/*     */     { case LINUX:
/*  91 */         workingDirectory = new File(userHome, ".minecraft/");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 106 */         return workingDirectory;case WINDOWS: applicationData = System.getenv("APPDATA"); folder = (applicationData != null) ? applicationData : userHome; workingDirectory = new File(folder, ".minecraft/"); return workingDirectory;case OSX: workingDirectory = new File(userHome, "Library/Application Support/minecraft"); return workingDirectory; }  File workingDirectory = new File(userHome, "minecraft/"); return workingDirectory;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */