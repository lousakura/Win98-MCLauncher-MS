/*    */ package net.minecraft.launcher.ui;
/*    */ import java.awt.Color;
/*    */ import java.awt.GradientPaint;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Graphics2D;
/*    */ import java.awt.Image;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.io.IOException;
/*    */ import javax.swing.JPanel;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class TexturedPanel extends JPanel {
/* 13 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static final long serialVersionUID = 1L;
/*    */   private Image image;
/*    */   private Image bgImage;
/*    */   
/*    */   public TexturedPanel(String filename) {
/* 20 */     setOpaque(true);
/*    */     
/*    */     try {
/* 23 */       this.bgImage = ImageIO.read(TexturedPanel.class.getResource(filename)).getScaledInstance(32, 32, 16);
/* 24 */     } catch (IOException e) {
/* 25 */       LOGGER.error("Unexpected exception initializing textured panel", e);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(Graphics g) {
/* 31 */     paint(g);
/*    */   }
/*    */ 
/*    */   
/*    */   public void paintComponent(Graphics graphics) {
/* 36 */     int width = getWidth() / 2 + 1;
/* 37 */     int height = getHeight() / 2 + 1;
/*    */     
/* 39 */     if (this.image == null || this.image.getWidth(null) != width || this.image.getHeight(null) != height) {
/* 40 */       this.image = createImage(width, height);
/* 41 */       copyImage(width, height);
/*    */     } 
/*    */     
/* 44 */     graphics.drawImage(this.image, 0, 0, width * 2, height * 2, null);
/*    */   }
/*    */   
/*    */   protected void copyImage(int width, int height) {
/* 48 */     Graphics imageGraphics = this.image.getGraphics();
/*    */     
/* 50 */     for (int x = 0; x <= width / 32; x++) {
/* 51 */       for (int y = 0; y <= height / 32; y++) {
/* 52 */         imageGraphics.drawImage(this.bgImage, x * 32, y * 32, null);
/*    */       }
/*    */     } 
/*    */     
/* 56 */     if (imageGraphics instanceof Graphics2D) {
/* 57 */       overlayGradient(width, height, (Graphics2D)imageGraphics);
/*    */     }
/*    */     
/* 60 */     imageGraphics.dispose();
/*    */   }
/*    */   
/*    */   protected void overlayGradient(int width, int height, Graphics2D graphics) {
/* 64 */     int gh = 1;
/* 65 */     graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(553648127, true), new Point2D.Float(0.0F, gh), new Color(0, true)));
/* 66 */     graphics.fillRect(0, 0, width, gh);
/*    */     
/* 68 */     gh = height;
/* 69 */     graphics.setPaint(new GradientPaint(new Point2D.Float(0.0F, 0.0F), new Color(0, true), new Point2D.Float(0.0F, gh), new Color(1610612736, true)));
/* 70 */     graphics.fillRect(0, 0, width, gh);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\TexturedPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */