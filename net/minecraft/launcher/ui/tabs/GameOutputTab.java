/*     */ package net.minecraft.launcher.ui.tabs;
/*     */ import java.awt.Font;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.datatransfer.StringSelection;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JScrollBar;
/*     */ import javax.swing.JTextArea;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.text.BadLocationException;
/*     */ import javax.swing.text.Document;
/*     */ import javax.swing.text.Element;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ 
/*     */ public class GameOutputTab extends JScrollPane implements GameOutputLogProcessor {
/*  19 */   private static final Font MONOSPACED = new Font("Monospaced", 0, 12);
/*     */   
/*     */   private static final int MAX_LINE_COUNT = 1000;
/*  22 */   private final JTextArea console = new JTextArea();
/*  23 */   private final JPopupMenu popupMenu = new JPopupMenu();
/*  24 */   private final JMenuItem copyTextButton = new JMenuItem("Copy All Text");
/*     */   
/*     */   private final Launcher minecraftLauncher;
/*     */   private boolean alreadyCensored = false;
/*     */   
/*     */   public GameOutputTab(Launcher minecraftLauncher) {
/*  30 */     this.minecraftLauncher = minecraftLauncher;
/*     */     
/*  32 */     this.popupMenu.add(this.copyTextButton);
/*  33 */     this.console.setComponentPopupMenu(this.popupMenu);
/*     */     
/*  35 */     this.copyTextButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*     */             try {
/*  39 */               StringSelection ss = new StringSelection(GameOutputTab.this.console.getText());
/*  40 */               Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
/*  41 */             } catch (Exception exception) {}
/*     */           }
/*     */         });
/*     */     
/*  45 */     this.console.setFont(MONOSPACED);
/*  46 */     this.console.setEditable(false);
/*  47 */     this.console.setMargin((Insets)null);
/*     */     
/*  49 */     setViewportView(this.console);
/*     */     
/*  51 */     this.console.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void insertUpdate(DocumentEvent e) {
/*  54 */             SwingUtilities.invokeLater(new Runnable()
/*     */                 {
/*     */                   public void run() {
/*  57 */                     Document document = GameOutputTab.this.console.getDocument();
/*  58 */                     Element root = document.getDefaultRootElement();
/*  59 */                     while (root.getElementCount() > 1001) {
/*     */                       try {
/*  61 */                         document.remove(0, root.getElement(0).getEndOffset());
/*  62 */                       } catch (BadLocationException badLocationException) {}
/*     */                     } 
/*     */                   }
/*     */                 });
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void removeUpdate(DocumentEvent e) {}
/*     */ 
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/*  79 */     return this.minecraftLauncher;
/*     */   }
/*     */   
/*     */   public void print(final String line) {
/*  83 */     if (!SwingUtilities.isEventDispatchThread()) {
/*  84 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/*  87 */               GameOutputTab.this.print(line);
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/*  93 */     Document document = this.console.getDocument();
/*  94 */     JScrollBar scrollBar = getVerticalScrollBar();
/*  95 */     boolean shouldScroll = false;
/*     */     
/*  97 */     if (getViewport().getView() == this.console) {
/*  98 */       shouldScroll = (scrollBar.getValue() + scrollBar.getSize().getHeight() + (MONOSPACED.getSize() * 4) > scrollBar.getMaximum());
/*     */     }
/*     */     
/*     */     try {
/* 102 */       document.insertString(document.getLength(), line, null);
/* 103 */     } catch (BadLocationException badLocationException) {}
/*     */     
/* 105 */     if (shouldScroll) {
/* 106 */       scrollBar.setValue(2147483647);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGameOutput(GameProcess process, String logLine) {
/* 112 */     if (!this.alreadyCensored) {
/* 113 */       int index = logLine.indexOf("(Session ID is");
/* 114 */       if (index > 0) {
/* 115 */         this.alreadyCensored = true;
/* 116 */         logLine = logLine.substring(0, index) + "(Session ID is <censored>)";
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     print(logLine + "\n");
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\GameOutputTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */