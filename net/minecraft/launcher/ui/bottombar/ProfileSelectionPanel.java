/*     */ package net.minecraft.launcher.ui.bottombar;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.GridLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JList;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.plaf.basic.BasicComboBoxRenderer;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.ui.popups.profile.ProfileEditorPopup;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ 
/*     */ public class ProfileSelectionPanel extends JPanel implements ActionListener, ItemListener, RefreshedProfilesListener {
/*  24 */   private static final Logger LOGGER = LogManager.getLogger();
/*  25 */   private final JComboBox profileList = new JComboBox();
/*  26 */   private final JButton newProfileButton = new JButton("New Profile");
/*  27 */   private final JButton editProfileButton = new JButton("Edit Profile");
/*     */   
/*     */   private final Launcher minecraftLauncher;
/*     */   private boolean skipSelectionUpdate;
/*     */   
/*     */   public ProfileSelectionPanel(Launcher minecraftLauncher) {
/*  33 */     this.minecraftLauncher = minecraftLauncher;
/*     */     
/*  35 */     this.profileList.setRenderer(new ProfileListRenderer());
/*  36 */     this.profileList.addItemListener(this);
/*  37 */     this.profileList.addItem("Loading profiles...");
/*     */     
/*  39 */     this.newProfileButton.addActionListener(this);
/*  40 */     this.editProfileButton.addActionListener(this);
/*     */     
/*  42 */     createInterface();
/*     */     
/*  44 */     minecraftLauncher.getProfileManager().addRefreshedProfilesListener(this);
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  48 */     setLayout(new GridBagLayout());
/*  49 */     GridBagConstraints constraints = new GridBagConstraints();
/*  50 */     constraints.fill = 2;
/*  51 */     constraints.weightx = 0.0D;
/*     */     
/*  53 */     constraints.gridy = 0;
/*     */     
/*  55 */     add(new JLabel("Profile: "), constraints);
/*  56 */     constraints.gridx = 1;
/*  57 */     add(this.profileList, constraints);
/*  58 */     constraints.gridx = 0;
/*     */     
/*  60 */     constraints.gridy++;
/*     */     
/*  62 */     JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
/*  63 */     buttonPanel.setBorder(new EmptyBorder(2, 0, 0, 0));
/*  64 */     buttonPanel.add(this.newProfileButton);
/*  65 */     buttonPanel.add(this.editProfileButton);
/*     */     
/*  67 */     constraints.gridwidth = 2;
/*  68 */     add(buttonPanel, constraints);
/*  69 */     constraints.gridwidth = 1;
/*     */     
/*  71 */     constraints.gridy++;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfilesRefreshed(ProfileManager manager) {
/*  76 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*  79 */             ProfileSelectionPanel.this.populateProfiles();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void populateProfiles() {
/*  85 */     String previous = this.minecraftLauncher.getProfileManager().getSelectedProfile().getName();
/*  86 */     Profile selected = null;
/*  87 */     List<Profile> profiles = Lists.newArrayList(this.minecraftLauncher.getProfileManager().getProfiles().values());
/*  88 */     this.profileList.removeAllItems();
/*     */     
/*  90 */     Collections.sort(profiles);
/*     */     
/*  92 */     this.skipSelectionUpdate = true;
/*     */     
/*  94 */     for (Profile profile : profiles) {
/*  95 */       if (previous.equals(profile.getName())) {
/*  96 */         selected = profile;
/*     */       }
/*     */       
/*  99 */       this.profileList.addItem(profile);
/*     */     } 
/*     */     
/* 102 */     if (selected == null) {
/* 103 */       if (profiles.isEmpty()) {
/* 104 */         selected = this.minecraftLauncher.getProfileManager().getSelectedProfile();
/* 105 */         this.profileList.addItem(selected);
/*     */       } 
/*     */       
/* 108 */       selected = profiles.iterator().next();
/*     */     } 
/*     */     
/* 111 */     this.profileList.setSelectedItem(selected);
/* 112 */     this.skipSelectionUpdate = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemStateChanged(ItemEvent e) {
/* 117 */     if (e.getStateChange() != 1)
/*     */       return; 
/* 119 */     if (!this.skipSelectionUpdate && e.getItem() instanceof Profile) {
/* 120 */       Profile profile = (Profile)e.getItem();
/* 121 */       this.minecraftLauncher.getProfileManager().setSelectedProfile(profile.getName());
/*     */       try {
/* 123 */         this.minecraftLauncher.getProfileManager().saveProfiles();
/* 124 */       } catch (IOException e1) {
/* 125 */         LOGGER.error("Couldn't save new selected profile", e1);
/*     */       } 
/* 127 */       this.minecraftLauncher.ensureLoggedIn();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 133 */     if (e.getSource() == this.newProfileButton) {
/* 134 */       Profile profile = new Profile(this.minecraftLauncher.getProfileManager().getSelectedProfile());
/* 135 */       profile.setName("Copy of " + profile.getName());
/*     */       
/* 137 */       while (this.minecraftLauncher.getProfileManager().getProfiles().containsKey(profile.getName())) {
/* 138 */         profile.setName(profile.getName() + "_");
/*     */       }
/*     */       
/* 141 */       ProfileEditorPopup.showEditProfileDialog(getMinecraftLauncher(), profile);
/* 142 */       this.minecraftLauncher.getProfileManager().setSelectedProfile(profile.getName());
/* 143 */     } else if (e.getSource() == this.editProfileButton) {
/* 144 */       Profile profile = this.minecraftLauncher.getProfileManager().getSelectedProfile();
/* 145 */       ProfileEditorPopup.showEditProfileDialog(getMinecraftLauncher(), profile);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class ProfileListRenderer
/*     */     extends BasicComboBoxRenderer
/*     */   {
/*     */     private ProfileListRenderer() {}
/*     */     
/*     */     public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
/* 156 */       if (value instanceof Profile) {
/* 157 */         value = ((Profile)value).getName();
/*     */       }
/*     */       
/* 160 */       super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
/* 161 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 166 */     return this.minecraftLauncher;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\bottombar\ProfileSelectionPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */