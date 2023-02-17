/*     */ package net.minecraft.launcher.ui.popups.login;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JProgressBar;
/*     */ import javax.swing.SwingUtilities;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ 
/*     */ public class LogInPopup extends JPanel implements ActionListener {
/*     */   private final Launcher minecraftLauncher;
/*     */   private final Callback callback;
/*  23 */   private final JButton loginButton = new JButton("Log In"); private final AuthErrorForm errorForm; private final ExistingUserListForm existingUserListForm; private final LogInForm logInForm;
/*  24 */   private final JButton registerButton = new JButton("Register");
/*  25 */   private final JProgressBar progressBar = new JProgressBar();
/*     */   
/*     */   public LogInPopup(Launcher minecraftLauncher, Callback callback) {
/*  28 */     super(true);
/*  29 */     this.minecraftLauncher = minecraftLauncher;
/*  30 */     this.callback = callback;
/*  31 */     this.errorForm = new AuthErrorForm(this);
/*  32 */     this.existingUserListForm = new ExistingUserListForm(this);
/*  33 */     this.logInForm = new LogInForm(this);
/*     */     
/*  35 */     createInterface();
/*     */     
/*  37 */     this.loginButton.addActionListener(this);
/*  38 */     this.registerButton.addActionListener(this);
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  42 */     setLayout(new BoxLayout(this, 1));
/*  43 */     setBorder(new EmptyBorder(5, 15, 5, 15));
/*     */     
/*     */     try {
/*  46 */       InputStream stream = LogInPopup.class.getResourceAsStream("/minecraft_logo.png");
/*  47 */       if (stream != null) {
/*  48 */         BufferedImage image = ImageIO.read(stream);
/*  49 */         JLabel label = new JLabel(new ImageIcon(image));
/*  50 */         JPanel imagePanel = new JPanel();
/*  51 */         imagePanel.add(label);
/*  52 */         add(imagePanel);
/*  53 */         add(Box.createVerticalStrut(10));
/*     */       } 
/*  55 */     } catch (IOException e) {
/*  56 */       e.printStackTrace();
/*     */     } 
/*     */     
/*  59 */     if (!this.minecraftLauncher.getProfileManager().getAuthDatabase().getKnownNames().isEmpty()) add(this.existingUserListForm); 
/*  60 */     add(this.errorForm);
/*  61 */     add(this.logInForm);
/*     */     
/*  63 */     add(Box.createVerticalStrut(15));
/*     */     
/*  65 */     JPanel buttonPanel = new JPanel();
/*  66 */     buttonPanel.setLayout(new GridLayout(1, 2, 10, 0));
/*  67 */     buttonPanel.add(this.registerButton);
/*  68 */     buttonPanel.add(this.loginButton);
/*     */     
/*  70 */     add(buttonPanel);
/*     */     
/*  72 */     this.progressBar.setIndeterminate(true);
/*  73 */     this.progressBar.setVisible(false);
/*  74 */     add(this.progressBar);
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*  79 */     if (e.getSource() == this.loginButton) {
/*  80 */       this.logInForm.tryLogIn();
/*  81 */     } else if (e.getSource() == this.registerButton) {
/*  82 */       OperatingSystem.openLink(LauncherConstants.URL_REGISTER);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/*  87 */     return this.minecraftLauncher;
/*     */   }
/*     */   
/*     */   public void setCanLogIn(final boolean enabled) {
/*  91 */     if (SwingUtilities.isEventDispatchThread()) {
/*  92 */       this.loginButton.setEnabled(enabled);
/*  93 */       this.progressBar.setIndeterminate(false);
/*  94 */       this.progressBar.setIndeterminate(true);
/*  95 */       this.progressBar.setVisible(!enabled);
/*  96 */       repack();
/*     */     } else {
/*  98 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 101 */               LogInPopup.this.setCanLogIn(enabled);
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public LogInForm getLogInForm() {
/* 108 */     return this.logInForm;
/*     */   }
/*     */   
/*     */   public AuthErrorForm getErrorForm() {
/* 112 */     return this.errorForm;
/*     */   }
/*     */   
/*     */   public ExistingUserListForm getExistingUserListForm() {
/* 116 */     return this.existingUserListForm;
/*     */   }
/*     */   
/*     */   public void setLoggedIn(String uuid) {
/* 120 */     this.callback.onLogIn(uuid);
/*     */   }
/*     */   
/*     */   public void repack() {
/* 124 */     Window window = SwingUtilities.windowForComponent(this);
/* 125 */     if (window != null) window.pack(); 
/*     */   }
/*     */   
/*     */   public static interface Callback {
/*     */     void onLogIn(String param1String);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\login\LogInPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */