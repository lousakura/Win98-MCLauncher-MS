/*     */ package net.minecraft.launcher.ui.popups.profile;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.io.File;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ import net.minecraft.launcher.profile.LauncherVisibilityRule;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ 
/*     */ public class ProfileInfoPanel extends JPanel {
/*  16 */   private final JCheckBox gameDirCustom = new JCheckBox("Game Directory:"); private final ProfileEditorPopup editor;
/*  17 */   private final JTextField profileName = new JTextField();
/*  18 */   private final JTextField gameDirField = new JTextField();
/*  19 */   private final JCheckBox resolutionCustom = new JCheckBox("Resolution:");
/*  20 */   private final JTextField resolutionWidth = new JTextField();
/*  21 */   private final JTextField resolutionHeight = new JTextField();
/*  22 */   private final JCheckBox useHopper = new JCheckBox("Automatically ask Mojang for assistance with fixing crashes");
/*  23 */   private final JCheckBox launcherVisibilityCustom = new JCheckBox("Launcher Visibility:");
/*  24 */   private final JComboBox launcherVisibilityOption = new JComboBox();
/*     */   
/*     */   public ProfileInfoPanel(ProfileEditorPopup editor) {
/*  27 */     this.editor = editor;
/*     */     
/*  29 */     setLayout(new GridBagLayout());
/*  30 */     setBorder(BorderFactory.createTitledBorder("Profile Info"));
/*     */     
/*  32 */     createInterface();
/*  33 */     fillDefaultValues();
/*  34 */     addEventHandlers();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createInterface() {
/*  39 */     GridBagConstraints constraints = new GridBagConstraints();
/*  40 */     constraints.insets = new Insets(2, 2, 2, 2);
/*  41 */     constraints.anchor = 17;
/*     */     
/*  43 */     constraints.gridy = 0;
/*     */     
/*  45 */     add(new JLabel("Profile Name:"), constraints);
/*  46 */     constraints.fill = 2;
/*  47 */     constraints.weightx = 1.0D;
/*  48 */     add(this.profileName, constraints);
/*  49 */     constraints.weightx = 0.0D;
/*  50 */     constraints.fill = 0;
/*     */     
/*  52 */     constraints.gridy++;
/*     */     
/*  54 */     add(this.gameDirCustom, constraints);
/*  55 */     constraints.fill = 2;
/*  56 */     constraints.weightx = 1.0D;
/*  57 */     add(this.gameDirField, constraints);
/*  58 */     constraints.weightx = 0.0D;
/*  59 */     constraints.fill = 0;
/*     */     
/*  61 */     constraints.gridy++;
/*     */     
/*  63 */     JPanel resolutionPanel = new JPanel();
/*  64 */     resolutionPanel.setLayout(new BoxLayout(resolutionPanel, 0));
/*  65 */     resolutionPanel.add(this.resolutionWidth);
/*  66 */     resolutionPanel.add(Box.createHorizontalStrut(5));
/*  67 */     resolutionPanel.add(new JLabel("x"));
/*  68 */     resolutionPanel.add(Box.createHorizontalStrut(5));
/*  69 */     resolutionPanel.add(this.resolutionHeight);
/*     */     
/*  71 */     add(this.resolutionCustom, constraints);
/*  72 */     constraints.fill = 2;
/*  73 */     constraints.weightx = 1.0D;
/*  74 */     add(resolutionPanel, constraints);
/*  75 */     constraints.weightx = 0.0D;
/*  76 */     constraints.fill = 0;
/*     */     
/*  78 */     constraints.gridy++;
/*     */     
/*  80 */     constraints.fill = 2;
/*  81 */     constraints.weightx = 1.0D;
/*  82 */     constraints.gridwidth = 0;
/*  83 */     add(this.useHopper, constraints);
/*  84 */     constraints.gridwidth = 1;
/*  85 */     constraints.weightx = 0.0D;
/*  86 */     constraints.fill = 0;
/*     */     
/*  88 */     constraints.gridy++;
/*     */     
/*  90 */     add(this.launcherVisibilityCustom, constraints);
/*  91 */     constraints.fill = 2;
/*  92 */     constraints.weightx = 1.0D;
/*  93 */     add(this.launcherVisibilityOption, constraints);
/*  94 */     constraints.weightx = 0.0D;
/*  95 */     constraints.fill = 0;
/*     */     
/*  97 */     constraints.gridy++;
/*     */     
/*  99 */     for (LauncherVisibilityRule value : LauncherVisibilityRule.values()) {
/* 100 */       this.launcherVisibilityOption.addItem(value);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void fillDefaultValues() {
/* 105 */     this.profileName.setText(this.editor.getProfile().getName());
/*     */     
/* 107 */     File gameDir = this.editor.getProfile().getGameDir();
/* 108 */     if (gameDir != null) {
/* 109 */       this.gameDirCustom.setSelected(true);
/* 110 */       this.gameDirField.setText(gameDir.getAbsolutePath());
/*     */     } else {
/* 112 */       this.gameDirCustom.setSelected(false);
/* 113 */       this.gameDirField.setText(this.editor.getMinecraftLauncher().getLauncher().getWorkingDirectory().getAbsolutePath());
/*     */     } 
/* 115 */     updateGameDirState();
/*     */     
/* 117 */     Profile.Resolution resolution = this.editor.getProfile().getResolution();
/* 118 */     this.resolutionCustom.setSelected((resolution != null));
/* 119 */     if (resolution == null) resolution = Profile.DEFAULT_RESOLUTION; 
/* 120 */     this.resolutionWidth.setText(String.valueOf(resolution.getWidth()));
/* 121 */     this.resolutionHeight.setText(String.valueOf(resolution.getHeight()));
/* 122 */     updateResolutionState();
/*     */     
/* 124 */     this.useHopper.setSelected(this.editor.getProfile().getUseHopperCrashService());
/*     */     
/* 126 */     LauncherVisibilityRule visibility = this.editor.getProfile().getLauncherVisibilityOnGameClose();
/*     */     
/* 128 */     if (visibility != null) {
/* 129 */       this.launcherVisibilityCustom.setSelected(true);
/* 130 */       this.launcherVisibilityOption.setSelectedItem(visibility);
/*     */     } else {
/* 132 */       this.launcherVisibilityCustom.setSelected(false);
/* 133 */       this.launcherVisibilityOption.setSelectedItem(Profile.DEFAULT_LAUNCHER_VISIBILITY);
/*     */     } 
/* 135 */     updateLauncherVisibilityState();
/*     */   }
/*     */   
/*     */   protected void addEventHandlers() {
/* 139 */     this.profileName.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void insertUpdate(DocumentEvent e) {
/* 142 */             ProfileInfoPanel.this.updateProfileName();
/*     */           }
/*     */ 
/*     */           
/*     */           public void removeUpdate(DocumentEvent e) {
/* 147 */             ProfileInfoPanel.this.updateProfileName();
/*     */           }
/*     */ 
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {
/* 152 */             ProfileInfoPanel.this.updateProfileName();
/*     */           }
/*     */         });
/*     */     
/* 156 */     this.gameDirCustom.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 159 */             ProfileInfoPanel.this.updateGameDirState();
/*     */           }
/*     */         });
/*     */     
/* 163 */     this.gameDirField.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void insertUpdate(DocumentEvent e) {
/* 166 */             ProfileInfoPanel.this.updateGameDir();
/*     */           }
/*     */ 
/*     */           
/*     */           public void removeUpdate(DocumentEvent e) {
/* 171 */             ProfileInfoPanel.this.updateGameDir();
/*     */           }
/*     */ 
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {
/* 176 */             ProfileInfoPanel.this.updateGameDir();
/*     */           }
/*     */         });
/*     */     
/* 180 */     this.resolutionCustom.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 183 */             ProfileInfoPanel.this.updateResolutionState();
/*     */           }
/*     */         });
/*     */     
/* 187 */     DocumentListener resolutionListener = new DocumentListener()
/*     */       {
/*     */         public void insertUpdate(DocumentEvent e) {
/* 190 */           ProfileInfoPanel.this.updateResolution();
/*     */         }
/*     */ 
/*     */         
/*     */         public void removeUpdate(DocumentEvent e) {
/* 195 */           ProfileInfoPanel.this.updateResolution();
/*     */         }
/*     */ 
/*     */         
/*     */         public void changedUpdate(DocumentEvent e) {
/* 200 */           ProfileInfoPanel.this.updateResolution();
/*     */         }
/*     */       };
/* 203 */     this.resolutionWidth.getDocument().addDocumentListener(resolutionListener);
/* 204 */     this.resolutionHeight.getDocument().addDocumentListener(resolutionListener);
/*     */     
/* 206 */     this.useHopper.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 209 */             ProfileInfoPanel.this.updateHopper();
/*     */           }
/*     */         });
/*     */     
/* 213 */     this.launcherVisibilityCustom.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 216 */             ProfileInfoPanel.this.updateLauncherVisibilityState();
/*     */           }
/*     */         });
/*     */     
/* 220 */     this.launcherVisibilityOption.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 223 */             ProfileInfoPanel.this.updateLauncherVisibilitySelection();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void updateLauncherVisibilityState() {
/* 229 */     Profile profile = this.editor.getProfile();
/*     */     
/* 231 */     if (this.launcherVisibilityCustom.isSelected() && this.launcherVisibilityOption.getSelectedItem() instanceof LauncherVisibilityRule) {
/* 232 */       profile.setLauncherVisibilityOnGameClose((LauncherVisibilityRule)this.launcherVisibilityOption.getSelectedItem());
/* 233 */       this.launcherVisibilityOption.setEnabled(true);
/*     */     } else {
/* 235 */       profile.setLauncherVisibilityOnGameClose(null);
/* 236 */       this.launcherVisibilityOption.setEnabled(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateLauncherVisibilitySelection() {
/* 241 */     Profile profile = this.editor.getProfile();
/*     */     
/* 243 */     if (this.launcherVisibilityOption.getSelectedItem() instanceof LauncherVisibilityRule) {
/* 244 */       profile.setLauncherVisibilityOnGameClose((LauncherVisibilityRule)this.launcherVisibilityOption.getSelectedItem());
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateHopper() {
/* 249 */     Profile profile = this.editor.getProfile();
/*     */     
/* 251 */     if (this.useHopper.isSelected()) {
/* 252 */       profile.setUseHopperCrashService(true);
/*     */     } else {
/* 254 */       profile.setUseHopperCrashService(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateProfileName() {
/* 259 */     if (this.profileName.getText().length() > 0) {
/* 260 */       this.editor.getProfile().setName(this.profileName.getText());
/*     */     }
/*     */   }
/*     */   
/*     */   private void updateGameDirState() {
/* 265 */     if (this.gameDirCustom.isSelected()) {
/* 266 */       this.gameDirField.setEnabled(true);
/* 267 */       this.editor.getProfile().setGameDir(new File(this.gameDirField.getText()));
/*     */     } else {
/* 269 */       this.gameDirField.setEnabled(false);
/* 270 */       this.editor.getProfile().setGameDir(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateResolutionState() {
/* 275 */     if (this.resolutionCustom.isSelected()) {
/* 276 */       this.resolutionWidth.setEnabled(true);
/* 277 */       this.resolutionHeight.setEnabled(true);
/* 278 */       updateResolution();
/*     */     } else {
/* 280 */       this.resolutionWidth.setEnabled(false);
/* 281 */       this.resolutionHeight.setEnabled(false);
/* 282 */       this.editor.getProfile().setResolution(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateResolution() {
/*     */     try {
/* 288 */       int width = Integer.parseInt(this.resolutionWidth.getText());
/* 289 */       int height = Integer.parseInt(this.resolutionHeight.getText());
/*     */       
/* 291 */       this.editor.getProfile().setResolution(new Profile.Resolution(width, height));
/* 292 */     } catch (NumberFormatException ignored) {
/* 293 */       this.editor.getProfile().setResolution(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateGameDir() {
/* 298 */     File file = new File(this.gameDirField.getText());
/* 299 */     this.editor.getProfile().setGameDir(file);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\profile\ProfileInfoPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */