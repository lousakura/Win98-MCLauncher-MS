/*     */ package net.minecraft.launcher.ui.popups.profile;
/*     */ import com.mojang.launcher.updater.VersionManager;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import com.mojang.launcher.versions.Version;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JOptionPane;
/*     */ import net.minecraft.launcher.SwingUserInterface;
/*     */ import net.minecraft.launcher.game.MinecraftReleaseType;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ 
/*     */ public class ProfileVersionPanel extends JPanel implements RefreshedVersionsListener {
/*  23 */   private final JComboBox versionList = new JComboBox(); private final ProfileEditorPopup editor;
/*  24 */   private final List<ReleaseTypeCheckBox> customVersionTypes = new ArrayList<ReleaseTypeCheckBox>();
/*     */   
/*     */   public ProfileVersionPanel(ProfileEditorPopup editor) {
/*  27 */     this.editor = editor;
/*     */     
/*  29 */     setLayout(new GridBagLayout());
/*  30 */     setBorder(BorderFactory.createTitledBorder("Version Selection"));
/*     */     
/*  32 */     createInterface();
/*  33 */     addEventHandlers();
/*     */     
/*  35 */     List<VersionSyncInfo> versions = editor.getMinecraftLauncher().getLauncher().getVersionManager().getVersions(editor.getProfile().getVersionFilter());
/*     */     
/*  37 */     if (versions.isEmpty()) {
/*  38 */       editor.getMinecraftLauncher().getLauncher().getVersionManager().addRefreshedVersionsListener(this);
/*     */     } else {
/*  40 */       populateVersions(versions);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createInterface() {
/*  46 */     GridBagConstraints constraints = new GridBagConstraints();
/*  47 */     constraints.insets = new Insets(2, 2, 2, 2);
/*  48 */     constraints.anchor = 17;
/*     */     
/*  50 */     constraints.gridy = 0;
/*     */     
/*  52 */     for (MinecraftReleaseType type : MinecraftReleaseType.values()) {
/*  53 */       if (type.getDescription() != null) {
/*  54 */         ReleaseTypeCheckBox checkbox = new ReleaseTypeCheckBox(type);
/*  55 */         checkbox.setSelected(this.editor.getProfile().getVersionFilter().getTypes().contains(type));
/*  56 */         this.customVersionTypes.add(checkbox);
/*     */         
/*  58 */         constraints.fill = 2;
/*  59 */         constraints.weightx = 1.0D;
/*  60 */         constraints.gridwidth = 0;
/*  61 */         add(checkbox, constraints);
/*  62 */         constraints.gridwidth = 1;
/*  63 */         constraints.weightx = 0.0D;
/*  64 */         constraints.fill = 0;
/*     */         
/*  66 */         constraints.gridy++;
/*     */       } 
/*     */     } 
/*  69 */     add(new JLabel("Use version:"), constraints);
/*  70 */     constraints.fill = 2;
/*  71 */     constraints.weightx = 1.0D;
/*  72 */     add(this.versionList, constraints);
/*  73 */     constraints.weightx = 0.0D;
/*  74 */     constraints.fill = 0;
/*     */     
/*  76 */     constraints.gridy++;
/*     */     
/*  78 */     this.versionList.setRenderer(new VersionListRenderer());
/*     */   }
/*     */   
/*     */   protected void addEventHandlers() {
/*  82 */     this.versionList.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/*  85 */             ProfileVersionPanel.this.updateVersionSelection();
/*     */           }
/*     */         });
/*     */     
/*  89 */     for (ReleaseTypeCheckBox type : this.customVersionTypes) {
/*  90 */       type.addItemListener(new ItemListener()
/*     */           {
/*     */             private boolean isUpdating = false;
/*     */             
/*     */             public void itemStateChanged(ItemEvent e) {
/*  95 */               if (this.isUpdating)
/*  96 */                 return;  if (e.getStateChange() == 1 && type.getType().getPopupWarning() != null) {
/*  97 */                 int result = JOptionPane.showConfirmDialog(((SwingUserInterface)ProfileVersionPanel.this.editor.getMinecraftLauncher().getUserInterface()).getFrame(), type.getType().getPopupWarning() + "\n\nAre you sure you want to continue?");
/*     */                 
/*  99 */                 this.isUpdating = true;
/* 100 */                 if (result == 0) {
/* 101 */                   type.setSelected(true);
/* 102 */                   ProfileVersionPanel.this.updateCustomVersionFilter();
/*     */                 } else {
/* 104 */                   type.setSelected(false);
/*     */                 } 
/* 106 */                 this.isUpdating = false;
/*     */               } else {
/* 108 */                 ProfileVersionPanel.this.updateCustomVersionFilter();
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCustomVersionFilter() {
/* 116 */     Profile profile = this.editor.getProfile();
/* 117 */     Set<MinecraftReleaseType> newTypes = Sets.newHashSet(Profile.DEFAULT_RELEASE_TYPES);
/*     */     
/* 119 */     for (ReleaseTypeCheckBox type : this.customVersionTypes) {
/* 120 */       if (type.isSelected()) {
/* 121 */         newTypes.add(type.getType()); continue;
/*     */       } 
/* 123 */       newTypes.remove(type.getType());
/*     */     } 
/*     */ 
/*     */     
/* 127 */     if (newTypes.equals(Profile.DEFAULT_RELEASE_TYPES)) {
/* 128 */       profile.setAllowedReleaseTypes(null);
/*     */     } else {
/* 130 */       profile.setAllowedReleaseTypes(newTypes);
/*     */     } 
/*     */     
/* 133 */     populateVersions(this.editor.getMinecraftLauncher().getLauncher().getVersionManager().getVersions(this.editor.getProfile().getVersionFilter()));
/* 134 */     this.editor.getMinecraftLauncher().getLauncher().getVersionManager().removeRefreshedVersionsListener(this);
/*     */   }
/*     */   
/*     */   private void updateVersionSelection() {
/* 138 */     Object selection = this.versionList.getSelectedItem();
/*     */     
/* 140 */     if (selection instanceof VersionSyncInfo) {
/* 141 */       Version version = ((VersionSyncInfo)selection).getLatestVersion();
/* 142 */       this.editor.getProfile().setLastVersionId(version.getId());
/*     */     } else {
/* 144 */       this.editor.getProfile().setLastVersionId(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void populateVersions(List<VersionSyncInfo> versions) {
/* 149 */     String previous = this.editor.getProfile().getLastVersionId();
/* 150 */     VersionSyncInfo selected = null;
/*     */     
/* 152 */     this.versionList.removeAllItems();
/* 153 */     this.versionList.addItem("Use Latest Version");
/*     */     
/* 155 */     for (VersionSyncInfo version : versions) {
/* 156 */       if (version.getLatestVersion().getId().equals(previous)) {
/* 157 */         selected = version;
/*     */       }
/*     */       
/* 160 */       this.versionList.addItem(version);
/*     */     } 
/*     */     
/* 163 */     if (selected == null && !versions.isEmpty()) {
/* 164 */       this.versionList.setSelectedIndex(0);
/*     */     } else {
/* 166 */       this.versionList.setSelectedItem(selected);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void onVersionsRefreshed(final VersionManager manager) {
/* 172 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 175 */             List<VersionSyncInfo> versions = manager.getVersions(ProfileVersionPanel.this.editor.getProfile().getVersionFilter());
/* 176 */             ProfileVersionPanel.this.populateVersions(versions);
/* 177 */             ProfileVersionPanel.this.editor.getMinecraftLauncher().getLauncher().getVersionManager().removeRefreshedVersionsListener(ProfileVersionPanel.this);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private static class ReleaseTypeCheckBox extends JCheckBox {
/*     */     private final MinecraftReleaseType type;
/*     */     
/*     */     private ReleaseTypeCheckBox(MinecraftReleaseType type) {
/* 186 */       super(type.getDescription());
/* 187 */       this.type = type;
/*     */     }
/*     */     
/*     */     public MinecraftReleaseType getType() {
/* 191 */       return this.type;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class VersionListRenderer
/*     */     extends BasicComboBoxRenderer
/*     */   {
/*     */     private VersionListRenderer() {}
/*     */     
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 202 */       if (value instanceof VersionSyncInfo) {
/* 203 */         VersionSyncInfo syncInfo = (VersionSyncInfo)value;
/* 204 */         Version version = syncInfo.getLatestVersion();
/*     */         
/* 206 */         value = String.format("%s %s", new Object[] { version.getType().getName(), version.getId() });
/*     */       } 
/*     */       
/* 209 */       super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 210 */       return this;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\profile\ProfileVersionPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */