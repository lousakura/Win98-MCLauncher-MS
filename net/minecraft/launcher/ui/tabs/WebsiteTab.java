/*    */ package net.minecraft.launcher.ui.tabs;
/*    */ 
/*    */ import java.awt.BorderLayout;
/*    */ import java.awt.event.ComponentAdapter;
/*    */ import java.awt.event.ComponentEvent;
/*    */ import java.beans.IntrospectionException;
/*    */ import java.io.File;
/*    */ import java.lang.reflect.Method;
/*    */ import java.net.URL;
/*    */ import java.net.URLClassLoader;
/*    */ import javax.swing.JPanel;
/*    */ import net.minecraft.launcher.Launcher;
/*    */ import net.minecraft.launcher.ui.tabs.website.Browser;
/*    */ import net.minecraft.launcher.ui.tabs.website.JFXBrowser;
/*    */ import net.minecraft.launcher.ui.tabs.website.LegacySwingBrowser;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class WebsiteTab
/*    */   extends JPanel {
/* 21 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/* 23 */   private final Browser browser = selectBrowser();
/*    */   
/*    */   private final Launcher minecraftLauncher;
/*    */   
/*    */   public WebsiteTab(Launcher minecraftLauncher) {
/* 28 */     this.minecraftLauncher = minecraftLauncher;
/*    */     
/* 30 */     setLayout(new BorderLayout());
/* 31 */     add(this.browser.getComponent(), "Center");
/* 32 */     this.browser.resize(getSize());
/*    */     
/* 34 */     addComponentListener(new ComponentAdapter()
/*    */         {
/*    */           public void componentResized(ComponentEvent e) {
/* 37 */             WebsiteTab.this.browser.resize(e.getComponent().getSize());
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   private Browser selectBrowser() {
/* 43 */     if (hasJFX()) {
/* 44 */       LOGGER.info("JFX is already initialized");
/* 45 */       return (Browser)new JFXBrowser();
/*    */     } 
/* 47 */     File jfxrt = new File(System.getProperty("java.home"), "lib/jfxrt.jar");
/*    */     
/* 49 */     if (jfxrt.isFile()) {
/* 50 */       LOGGER.debug("Attempting to load {}...", new Object[] { jfxrt });
/*    */       
/*    */       try {
/* 53 */         addToSystemClassLoader(jfxrt);
/*    */         
/* 55 */         LOGGER.info("JFX has been detected & successfully loaded");
/* 56 */         return (Browser)new JFXBrowser();
/* 57 */       } catch (Throwable e) {
/* 58 */         LOGGER.debug("JFX has been detected but unsuccessfully loaded", e);
/* 59 */         return (Browser)new LegacySwingBrowser();
/*    */       } 
/*    */     } 
/* 62 */     LOGGER.debug("JFX was not found at {}", new Object[] { jfxrt });
/* 63 */     return (Browser)new LegacySwingBrowser();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPage(String url) {
/* 69 */     this.browser.loadUrl(url);
/*    */   }
/*    */   
/*    */   public Launcher getMinecraftLauncher() {
/* 73 */     return this.minecraftLauncher;
/*    */   }
/*    */   
/*    */   public static void addToSystemClassLoader(File file) throws IntrospectionException {
/* 77 */     if (ClassLoader.getSystemClassLoader() instanceof URLClassLoader) {
/* 78 */       URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
/*    */       
/*    */       try {
/* 81 */         Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
/* 82 */         method.setAccessible(true);
/* 83 */         method.invoke(classLoader, new Object[] { file.toURI().toURL() });
/* 84 */       } catch (Throwable t) {
/* 85 */         LOGGER.warn("Couldn't add " + file + " to system classloader", t);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean hasJFX() {
/*    */     try {
/* 92 */       getClass().getClassLoader().loadClass("javafx.embed.swing.JFXPanel");
/* 93 */       return true;
/* 94 */     } catch (ClassNotFoundException e) {
/* 95 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\WebsiteTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */