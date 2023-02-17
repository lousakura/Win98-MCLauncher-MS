/*     */ package net.minecraft.launcher.ui.bottombar;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.game.GameInstanceStatus;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ import net.minecraft.launcher.SwingUserInterface;
/*     */ import net.minecraft.launcher.game.GameLaunchDispatcher;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.profile.UserChangedListener;
/*     */ 
/*     */ public class PlayButtonPanel extends JPanel implements RefreshedVersionsListener, RefreshedProfilesListener, UserChangedListener {
/*  24 */   private final JButton playButton = new JButton("Play"); private final Launcher minecraftLauncher;
/*  25 */   private final JLabel demoHelpLink = new JLabel("(Why can I only play demo?)");
/*     */   
/*     */   public PlayButtonPanel(Launcher minecraftLauncher) {
/*  28 */     this.minecraftLauncher = minecraftLauncher;
/*     */     
/*  30 */     minecraftLauncher.getProfileManager().addRefreshedProfilesListener(this);
/*  31 */     minecraftLauncher.getProfileManager().addUserChangedListener(this);
/*  32 */     checkState();
/*  33 */     createInterface();
/*     */     
/*  35 */     this.playButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  38 */             GameLaunchDispatcher dispatcher = PlayButtonPanel.this.getMinecraftLauncher().getLaunchDispatcher();
/*  39 */             if (dispatcher.isRunningInSameFolder()) {
/*  40 */               int result = JOptionPane.showConfirmDialog(((SwingUserInterface)PlayButtonPanel.this.getMinecraftLauncher().getUserInterface()).getFrame(), "You already have an instance of Minecraft running. If you launch another one in the same folder, they may clash and corrupt your saves.\nThis could cause many issues, in singleplayer or otherwise. We will not be responsible for anything that goes wrong.\nDo you want to start another instance of Minecraft, despite this?\nYou may solve this issue by launching the game in a different folder (see the \"Edit Profile\" button)", "Duplicate instance warning", 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*  49 */               if (result == 0) {
/*  50 */                 dispatcher.play();
/*     */               }
/*     */             } else {
/*  53 */               dispatcher.play();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  60 */     setLayout(new GridBagLayout());
/*  61 */     GridBagConstraints constraints = new GridBagConstraints();
/*  62 */     constraints.fill = 1;
/*  63 */     constraints.weightx = 1.0D;
/*  64 */     constraints.weighty = 1.0D;
/*     */     
/*  66 */     constraints.gridy = 0;
/*  67 */     constraints.gridx = 0;
/*  68 */     add(this.playButton, constraints);
/*     */     
/*  70 */     constraints.gridy++;
/*  71 */     constraints.weighty = 0.0D;
/*  72 */     constraints.anchor = 10;
/*  73 */     Font smalltextFont = this.demoHelpLink.getFont().deriveFont(this.demoHelpLink.getFont().getSize() - 2.0F);
/*  74 */     this.demoHelpLink.setCursor(new Cursor(12));
/*  75 */     this.demoHelpLink.setFont(smalltextFont);
/*  76 */     this.demoHelpLink.setHorizontalAlignment(0);
/*  77 */     this.demoHelpLink.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e) {
/*  80 */             OperatingSystem.openLink(LauncherConstants.URL_DEMO_HELP);
/*     */           }
/*     */         });
/*  83 */     add(this.demoHelpLink, constraints);
/*     */     
/*  85 */     this.playButton.setFont(this.playButton.getFont().deriveFont(1, (this.playButton.getFont().getSize() + 2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfilesRefreshed(ProfileManager manager) {
/*  90 */     checkState();
/*     */   }
/*     */   
/*     */   public void checkState() {
/*  94 */     GameLaunchDispatcher.PlayStatus status = this.minecraftLauncher.getLaunchDispatcher().getStatus();
/*  95 */     this.playButton.setText(status.getName());
/*  96 */     this.playButton.setEnabled(status.canPlay());
/*  97 */     this.demoHelpLink.setVisible((status == GameLaunchDispatcher.PlayStatus.CAN_PLAY_DEMO));
/*     */     
/*  99 */     if (status == GameLaunchDispatcher.PlayStatus.DOWNLOADING) {
/* 100 */       GameInstanceStatus instanceStatus = this.minecraftLauncher.getLaunchDispatcher().getInstanceStatus();
/* 101 */       if (instanceStatus != GameInstanceStatus.IDLE) {
/* 102 */         this.playButton.setText(instanceStatus.getName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onVersionsRefreshed(VersionManager manager) {
/* 109 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 112 */             PlayButtonPanel.this.checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 118 */     return this.minecraftLauncher;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUserChanged(ProfileManager manager) {
/* 123 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 126 */             PlayButtonPanel.this.checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\bottombar\PlayButtonPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */