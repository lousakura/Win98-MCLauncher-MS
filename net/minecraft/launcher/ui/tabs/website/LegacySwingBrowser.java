/*    */ package net.minecraft.launcher.ui.tabs.website;
/*    */ import com.mojang.launcher.OperatingSystem;
/*    */ import java.awt.Color;
/*    */ import java.awt.Dimension;
/*    */ import java.net.URL;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextPane;
/*    */ import javax.swing.event.HyperlinkEvent;
/*    */ import javax.swing.event.HyperlinkListener;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LegacySwingBrowser implements Browser {
/* 14 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/* 16 */   private final JScrollPane scrollPane = new JScrollPane();
/* 17 */   private final JTextPane browser = new JTextPane();
/*    */   
/*    */   public LegacySwingBrowser() {
/* 20 */     this.browser.setEditable(false);
/* 21 */     this.browser.setMargin((Insets)null);
/* 22 */     this.browser.setBackground(Color.DARK_GRAY);
/* 23 */     this.browser.setContentType("text/html");
/* 24 */     this.browser.setText("<html><body><font color=\"#808080\"><br><br><br><br><br><br><br><center><h1>Loading page..</h1></center></font></body></html>");
/* 25 */     this.browser.addHyperlinkListener(new HyperlinkListener()
/*    */         {
/*    */           public void hyperlinkUpdate(HyperlinkEvent he) {
/* 28 */             if (he.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
/*    */               try {
/* 30 */                 OperatingSystem.openLink(he.getURL().toURI());
/* 31 */               } catch (Exception e) {
/* 32 */                 LegacySwingBrowser.LOGGER.error("Unexpected exception opening link " + he.getURL(), e);
/*    */               } 
/*    */             }
/*    */           }
/*    */         });
/*    */     
/* 38 */     this.scrollPane.setViewportView(this.browser);
/*    */   }
/*    */ 
/*    */   
/*    */   public void loadUrl(final String url) {
/* 43 */     Thread thread = new Thread("Update website tab")
/*    */       {
/*    */         public void run() {
/*    */           try {
/* 47 */             LegacySwingBrowser.this.browser.setPage(new URL(url));
/* 48 */           } catch (Exception e) {
/* 49 */             LegacySwingBrowser.LOGGER.error("Unexpected exception loading " + url, e);
/* 50 */             LegacySwingBrowser.this.browser.setText("<html><body><font color=\"#808080\"><br><br><br><br><br><br><br><center><h1>Failed to get page</h1><br>" + e.toString() + "</center></font></body></html>");
/*    */           } 
/*    */         }
/*    */       };
/*    */     
/* 55 */     thread.setDaemon(true);
/* 56 */     thread.start();
/*    */   }
/*    */ 
/*    */   
/*    */   public Component getComponent() {
/* 61 */     return this.scrollPane;
/*    */   }
/*    */   
/*    */   public void resize(Dimension size) {}
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\website\LegacySwingBrowser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */