/*     */ package net.minecraft.launcher.ui.bottombar;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.launcher.updater.VersionManager;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.profile.RefreshedProfilesListener;
/*     */ import net.minecraft.launcher.profile.UserChangedListener;
/*     */ 
/*     */ public class PlayerInfoPanel extends JPanel implements RefreshedVersionsListener, RefreshedProfilesListener, UserChangedListener {
/*  21 */   private final JLabel welcomeText = new JLabel("", 0); private final Launcher minecraftLauncher;
/*  22 */   private final JLabel versionText = new JLabel("", 0);
/*  23 */   private final JButton switchUserButton = new JButton("Switch User");
/*     */   
/*     */   public PlayerInfoPanel(final Launcher minecraftLauncher) {
/*  26 */     this.minecraftLauncher = minecraftLauncher;
/*     */     
/*  28 */     minecraftLauncher.getProfileManager().addRefreshedProfilesListener(this);
/*  29 */     minecraftLauncher.getProfileManager().addUserChangedListener(this);
/*  30 */     checkState();
/*  31 */     createInterface();
/*     */     
/*  33 */     this.switchUserButton.setEnabled(false);
/*  34 */     this.switchUserButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  37 */             minecraftLauncher.getUserInterface().showLoginPrompt();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  43 */     setLayout(new GridBagLayout());
/*  44 */     GridBagConstraints constraints = new GridBagConstraints();
/*  45 */     constraints.fill = 2;
/*     */     
/*  47 */     constraints.gridy = 0;
/*     */     
/*  49 */     constraints.weightx = 1.0D;
/*  50 */     constraints.gridwidth = 2;
/*  51 */     add(this.welcomeText, constraints);
/*  52 */     constraints.gridwidth = 1;
/*  53 */     constraints.weightx = 0.0D;
/*     */     
/*  55 */     constraints.gridy++;
/*     */     
/*  57 */     constraints.weightx = 1.0D;
/*  58 */     constraints.gridwidth = 2;
/*  59 */     add(this.versionText, constraints);
/*  60 */     constraints.gridwidth = 1;
/*  61 */     constraints.weightx = 0.0D;
/*     */     
/*  63 */     constraints.gridy++;
/*     */     
/*  65 */     constraints.weightx = 0.5D;
/*  66 */     constraints.fill = 0;
/*  67 */     add(this.switchUserButton, constraints);
/*  68 */     constraints.weightx = 0.0D;
/*     */     
/*  70 */     constraints.gridy++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfilesRefreshed(ProfileManager manager) {
/*  75 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*  78 */             PlayerInfoPanel.this.checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void checkState() {
/*  84 */     ProfileManager profileManager = this.minecraftLauncher.getProfileManager();
/*  85 */     UserAuthentication auth = (profileManager.getSelectedUser() == null) ? null : profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/*     */     
/*  87 */     if (auth == null || !auth.isLoggedIn()) {
/*  88 */       this.welcomeText.setText("Welcome, guest! Please log in.");
/*  89 */     } else if (auth.getSelectedProfile() == null) {
/*  90 */       this.welcomeText.setText("<html>Welcome, player!</html>");
/*     */     } else {
/*  92 */       this.welcomeText.setText("<html>Welcome, <b>" + auth.getSelectedProfile().getName() + "</b></html>");
/*     */     } 
/*     */     
/*  95 */     Profile profile = profileManager.getProfiles().isEmpty() ? null : profileManager.getSelectedProfile();
/*  96 */     List<VersionSyncInfo> versions = (profile == null) ? null : this.minecraftLauncher.getLauncher().getVersionManager().getVersions(profile.getVersionFilter());
/*  97 */     VersionSyncInfo version = (profile == null || versions.isEmpty()) ? null : versions.get(0);
/*     */     
/*  99 */     if (profile != null && profile.getLastVersionId() != null) {
/* 100 */       VersionSyncInfo requestedVersion = this.minecraftLauncher.getLauncher().getVersionManager().getVersionSyncInfo(profile.getLastVersionId());
/* 101 */       if (requestedVersion != null && requestedVersion.getLatestVersion() != null) version = requestedVersion;
/*     */     
/*     */     } 
/* 104 */     if (version == null) {
/* 105 */       this.versionText.setText("Loading versions...");
/* 106 */     } else if (version.isUpToDate()) {
/* 107 */       this.versionText.setText("Ready to play Minecraft " + version.getLatestVersion().getId());
/* 108 */     } else if (version.isInstalled()) {
/* 109 */       this.versionText.setText("Ready to update & play Minecraft " + version.getLatestVersion().getId());
/* 110 */     } else if (version.isOnRemote()) {
/* 111 */       this.versionText.setText("Ready to download & play Minecraft " + version.getLatestVersion().getId());
/*     */     } 
/*     */     
/* 114 */     this.switchUserButton.setEnabled(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onVersionsRefreshed(VersionManager manager) {
/* 119 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 122 */             PlayerInfoPanel.this.checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 128 */     return this.minecraftLauncher;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUserChanged(ProfileManager manager) {
/* 133 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 136 */             PlayerInfoPanel.this.checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\bottombar\PlayerInfoPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */