/*     */ package net.minecraft.launcher.ui;
/*     */ 
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.CardLayout;
/*     */ import java.awt.Color;
/*     */ import java.awt.Component;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.net.URI;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ import net.minecraft.launcher.ui.tabs.LauncherTabPanel;
/*     */ import org.apache.commons.lang3.SystemUtils;
/*     */ 
/*     */ 
/*     */ public class LauncherPanel
/*     */   extends JPanel
/*     */ {
/*     */   public static final String CARD_DIRT_BACKGROUND = "loading";
/*     */   public static final String CARD_LOGIN = "login";
/*     */   public static final String CARD_LAUNCHER = "launcher";
/*     */   private final CardLayout cardLayout;
/*     */   private final LauncherTabPanel tabPanel;
/*     */   
/*     */   public LauncherPanel(Launcher minecraftLauncher) {
/*  31 */     this.minecraftLauncher = minecraftLauncher;
/*  32 */     this.cardLayout = new CardLayout();
/*  33 */     setLayout(this.cardLayout);
/*     */     
/*  35 */     this.progressBar = new JProgressBar();
/*  36 */     this.bottomBar = new BottomBarPanel(minecraftLauncher);
/*  37 */     this.tabPanel = new LauncherTabPanel(minecraftLauncher);
/*  38 */     this.loginPanel = new TexturedPanel("/dirt.png");
/*  39 */     createInterface();
/*     */   }
/*     */   private final BottomBarPanel bottomBar; private final JProgressBar progressBar; private final Launcher minecraftLauncher; private final JPanel loginPanel; private JLabel warningLabel;
/*     */   protected void createInterface() {
/*  43 */     add(createLauncherInterface(), "launcher");
/*  44 */     add(createDirtInterface(), "loading");
/*  45 */     add(createLoginInterface(), "login");
/*     */   }
/*     */   
/*     */   protected JPanel createLauncherInterface() {
/*  49 */     JPanel result = new JPanel(new BorderLayout());
/*     */     
/*  51 */     this.tabPanel.getBlog().setPage("http://mcupdate.tumblr.com");
/*     */     
/*  53 */     boolean javaBootstrap = (getMinecraftLauncher().getBootstrapVersion().intValue() < 100);
/*  54 */     boolean upgradableOS = (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS);
/*  55 */     if (OperatingSystem.getCurrentPlatform() == OperatingSystem.OSX) {
/*  56 */       String ver = SystemUtils.OS_VERSION;
/*  57 */       if (ver != null && !ver.isEmpty()) {
/*  58 */         String[] split = ver.split("\\.", 3);
/*  59 */         if (split.length >= 2) {
/*     */           try {
/*  61 */             int major = Integer.parseInt(split[0]);
/*  62 */             int minor = Integer.parseInt(split[1]);
/*     */             
/*  64 */             if (major == 10) {
/*  65 */               upgradableOS = (minor >= 8);
/*  66 */             } else if (major > 10) {
/*  67 */               upgradableOS = true;
/*     */             } 
/*  69 */           } catch (NumberFormatException numberFormatException) {}
/*     */         }
/*     */       } 
/*     */     } 
/*  73 */     if (javaBootstrap && upgradableOS) {
/*  74 */       final URI url; this.warningLabel = new JLabel();
/*  75 */       this.warningLabel.setForeground(Color.RED);
/*  76 */       this.warningLabel.setHorizontalAlignment(0);
/*     */ 
/*     */       
/*  79 */       if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
/*  80 */         url = LauncherConstants.URL_UPGRADE_WINDOWS;
/*     */       } else {
/*  82 */         url = LauncherConstants.URL_UPGRADE_OSX;
/*     */       } 
/*     */       
/*  85 */       if (SystemUtils.IS_JAVA_1_8) {
/*  86 */         if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
/*  87 */           this.warningLabel.setText("<html><p style='font-size: 1.1em'>You are running an old version of the launcher. Please consider <a href='" + url + "'>using the new launcher</a> which will improve the performance of both launcher and game.</p></html>");
/*     */         } else {
/*  89 */           this.warningLabel.setText("<html><p style='font-size: 1em'>You are running an old version of the launcher. Please consider <a href='" + url + "'>using the new launcher</a> which will improve the performance of both launcher and game.</p></html>");
/*     */         }
/*     */       
/*  92 */       } else if (OperatingSystem.getCurrentPlatform() == OperatingSystem.WINDOWS) {
/*  93 */         this.warningLabel.setText("<html><p style='font-size: 1.1em'>You are running on an old version of Java. Please consider <a href='" + url + "'>using the new launcher</a> which doesn't require Java, as it will make your game faster.</p></html>");
/*     */       } else {
/*  95 */         this.warningLabel.setText("<html><p style='font-size: 1em'>You are running on an old version of Java. Please consider <a href='" + url + "'>using the new launcher</a> which doesn't require Java, as it will make your game faster.</p></html>");
/*     */       } 
/*     */ 
/*     */       
/*  99 */       result.add(this.warningLabel, "North");
/* 100 */       result.addMouseListener(new MouseAdapter()
/*     */           {
/*     */             public void mouseClicked(MouseEvent e) {
/* 103 */               OperatingSystem.openLink(url);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 108 */     JPanel center = new JPanel();
/* 109 */     center.setLayout(new BorderLayout());
/* 110 */     center.add((Component)this.tabPanel, "Center");
/* 111 */     center.add(this.progressBar, "South");
/*     */     
/* 113 */     this.progressBar.setVisible(false);
/* 114 */     this.progressBar.setMinimum(0);
/* 115 */     this.progressBar.setMaximum(100);
/* 116 */     this.progressBar.setStringPainted(true);
/*     */     
/* 118 */     result.add(center, "Center");
/* 119 */     result.add(this.bottomBar, "South");
/*     */     
/* 121 */     return result;
/*     */   }
/*     */   
/*     */   protected JPanel createDirtInterface() {
/* 125 */     return new TexturedPanel("/dirt.png");
/*     */   }
/*     */   
/*     */   protected JPanel createLoginInterface() {
/* 129 */     this.loginPanel.setLayout(new GridBagLayout());
/* 130 */     return this.loginPanel;
/*     */   }
/*     */   
/*     */   public LauncherTabPanel getTabPanel() {
/* 134 */     return this.tabPanel;
/*     */   }
/*     */   
/*     */   public BottomBarPanel getBottomBar() {
/* 138 */     return this.bottomBar;
/*     */   }
/*     */   
/*     */   public JProgressBar getProgressBar() {
/* 142 */     return this.progressBar;
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 146 */     return this.minecraftLauncher;
/*     */   }
/*     */   
/*     */   public void setCard(String card, JPanel additional) {
/* 150 */     if (card.equals("login")) {
/* 151 */       this.loginPanel.removeAll();
/* 152 */       this.loginPanel.add(additional);
/*     */     } 
/* 154 */     this.cardLayout.show(this, card);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\LauncherPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */