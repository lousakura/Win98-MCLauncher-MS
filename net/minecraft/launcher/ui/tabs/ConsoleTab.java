/*    */ package net.minecraft.launcher.ui.tabs;
/*    */ import com.mojang.util.QueueLogAppender;
/*    */ import java.awt.Font;
/*    */ import java.awt.Toolkit;
/*    */ import java.awt.datatransfer.StringSelection;
/*    */ import java.awt.event.ActionListener;
/*    */ import javax.swing.JMenuItem;
/*    */ import javax.swing.JScrollBar;
/*    */ import javax.swing.JTextArea;
/*    */ import javax.swing.SwingUtilities;
/*    */ import javax.swing.text.Document;
/*    */ import net.minecraft.launcher.Launcher;
/*    */ 
/*    */ public class ConsoleTab extends JScrollPane {
/* 15 */   private static final Font MONOSPACED = new Font("Monospaced", 0, 12);
/*    */   
/* 17 */   private final JTextArea console = new JTextArea();
/* 18 */   private final JPopupMenu popupMenu = new JPopupMenu();
/* 19 */   private final JMenuItem copyTextButton = new JMenuItem("Copy All Text");
/*    */   
/*    */   private final Launcher minecraftLauncher;
/*    */   
/*    */   public ConsoleTab(Launcher minecraftLauncher) {
/* 24 */     this.minecraftLauncher = minecraftLauncher;
/*    */     
/* 26 */     this.popupMenu.add(this.copyTextButton);
/* 27 */     this.console.setComponentPopupMenu(this.popupMenu);
/*    */     
/* 29 */     this.copyTextButton.addActionListener(new ActionListener()
/*    */         {
/*    */           public void actionPerformed(ActionEvent e) {
/*    */             try {
/* 33 */               StringSelection ss = new StringSelection(ConsoleTab.this.console.getText());
/* 34 */               Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
/* 35 */             } catch (Exception exception) {}
/*    */           }
/*    */         });
/*    */     
/* 39 */     this.console.setFont(MONOSPACED);
/* 40 */     this.console.setEditable(false);
/* 41 */     this.console.setMargin((Insets)null);
/*    */     
/* 43 */     setViewportView(this.console);
/*    */     
/* 45 */     Thread thread = new Thread(new Runnable()
/*    */         {
/*    */           public void run() {
/*    */             String line;
/* 49 */             while ((line = QueueLogAppender.getNextLogEvent("DevelopmentConsole")) != null) {
/* 50 */               ConsoleTab.this.print(line);
/*    */             }
/*    */           }
/*    */         });
/* 54 */     thread.setDaemon(true);
/* 55 */     thread.start();
/*    */   }
/*    */   
/*    */   public Launcher getMinecraftLauncher() {
/* 59 */     return this.minecraftLauncher;
/*    */   }
/*    */   
/*    */   public void print(final String line) {
/* 63 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 64 */       SwingUtilities.invokeLater(new Runnable()
/*    */           {
/*    */             public void run() {
/* 67 */               ConsoleTab.this.print(line);
/*    */             }
/*    */           });
/*    */       
/*    */       return;
/*    */     } 
/* 73 */     Document document = this.console.getDocument();
/* 74 */     JScrollBar scrollBar = getVerticalScrollBar();
/* 75 */     boolean shouldScroll = false;
/*    */     
/* 77 */     if (getViewport().getView() == this.console) {
/* 78 */       shouldScroll = (scrollBar.getValue() + scrollBar.getSize().getHeight() + (MONOSPACED.getSize() * 4) > scrollBar.getMaximum());
/*    */     }
/*    */     
/*    */     try {
/* 82 */       document.insertString(document.getLength(), line, null);
/* 83 */     } catch (BadLocationException badLocationException) {}
/*    */     
/* 85 */     if (shouldScroll)
/* 86 */       scrollBar.setValue(2147483647); 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\ConsoleTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */