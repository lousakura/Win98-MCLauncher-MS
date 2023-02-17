/*     */ package net.minecraft.launcher.ui.popups.profile;
/*     */ import java.awt.Window;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.BoxLayout;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ProfileEditorPopup extends JPanel implements ActionListener {
/*  21 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final Launcher minecraftLauncher;
/*     */   private final Profile originalProfile;
/*     */   private final Profile profile;
/*  25 */   private final JButton saveButton = new JButton("Save Profile");
/*  26 */   private final JButton cancelButton = new JButton("Cancel");
/*  27 */   private final JButton browseButton = new JButton("Open Game Dir");
/*     */   private final ProfileInfoPanel profileInfoPanel;
/*     */   private final ProfileVersionPanel profileVersionPanel;
/*     */   private final ProfileJavaPanel javaInfoPanel;
/*     */   
/*     */   public ProfileEditorPopup(Launcher minecraftLauncher, Profile profile) {
/*  33 */     super(true);
/*     */     
/*  35 */     this.minecraftLauncher = minecraftLauncher;
/*  36 */     this.originalProfile = profile;
/*  37 */     this.profile = new Profile(profile);
/*  38 */     this.profileInfoPanel = new ProfileInfoPanel(this);
/*  39 */     this.profileVersionPanel = new ProfileVersionPanel(this);
/*  40 */     this.javaInfoPanel = new ProfileJavaPanel(this);
/*     */     
/*  42 */     this.saveButton.addActionListener(this);
/*  43 */     this.cancelButton.addActionListener(this);
/*  44 */     this.browseButton.addActionListener(this);
/*     */     
/*  46 */     setBorder(new EmptyBorder(5, 5, 5, 5));
/*  47 */     setLayout(new BorderLayout(0, 5));
/*  48 */     createInterface();
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  52 */     JPanel standardPanels = new JPanel(true);
/*  53 */     standardPanels.setLayout(new BoxLayout(standardPanels, 1));
/*  54 */     standardPanels.add(this.profileInfoPanel);
/*  55 */     standardPanels.add(this.profileVersionPanel);
/*  56 */     standardPanels.add(this.javaInfoPanel);
/*     */     
/*  58 */     add(standardPanels, "Center");
/*     */     
/*  60 */     JPanel buttonPannel = new JPanel();
/*  61 */     buttonPannel.setLayout(new BoxLayout(buttonPannel, 0));
/*  62 */     buttonPannel.add(this.cancelButton);
/*  63 */     buttonPannel.add(Box.createGlue());
/*  64 */     buttonPannel.add(this.browseButton);
/*  65 */     buttonPannel.add(Box.createHorizontalStrut(5));
/*  66 */     buttonPannel.add(this.saveButton);
/*  67 */     add(buttonPannel, "South");
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*  72 */     if (e.getSource() == this.saveButton) {
/*     */       try {
/*  74 */         ProfileManager manager = this.minecraftLauncher.getProfileManager();
/*  75 */         Map<String, Profile> profiles = manager.getProfiles();
/*  76 */         String selected = manager.getSelectedProfile().getName();
/*     */         
/*  78 */         if (!this.originalProfile.getName().equals(this.profile.getName())) {
/*  79 */           profiles.remove(this.originalProfile.getName());
/*     */           
/*  81 */           while (profiles.containsKey(this.profile.getName())) {
/*  82 */             this.profile.setName(this.profile.getName() + "_");
/*     */           }
/*     */         } 
/*     */         
/*  86 */         profiles.put(this.profile.getName(), this.profile);
/*     */         
/*  88 */         if (selected.equals(this.originalProfile.getName())) {
/*  89 */           manager.setSelectedProfile(this.profile.getName());
/*     */         }
/*     */         
/*  92 */         manager.saveProfiles();
/*  93 */         manager.fireRefreshEvent();
/*  94 */       } catch (IOException ex) {
/*  95 */         LOGGER.error("Couldn't save profiles whilst editing " + this.profile.getName(), ex);
/*     */       } 
/*  97 */       closeWindow();
/*  98 */     } else if (e.getSource() == this.browseButton) {
/*  99 */       OperatingSystem.openFolder((this.profile.getGameDir() == null) ? this.minecraftLauncher.getLauncher().getWorkingDirectory() : this.profile.getGameDir());
/*     */     } else {
/* 101 */       closeWindow();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeWindow() {
/* 106 */     if (SwingUtilities.isEventDispatchThread()) {
/* 107 */       Window window = (Window)getTopLevelAncestor();
/* 108 */       window.dispatchEvent(new WindowEvent(window, 201));
/*     */     } else {
/* 110 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 113 */               ProfileEditorPopup.this.closeWindow();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 120 */     return this.minecraftLauncher;
/*     */   }
/*     */   
/*     */   public Profile getProfile() {
/* 124 */     return this.profile;
/*     */   }
/*     */   
/*     */   public static void showEditProfileDialog(Launcher minecraftLauncher, Profile profile) {
/* 128 */     JFrame frame = ((SwingUserInterface)minecraftLauncher.getUserInterface()).getFrame();
/* 129 */     JDialog dialog = new JDialog(frame, "Profile Editor", true);
/* 130 */     ProfileEditorPopup editor = new ProfileEditorPopup(minecraftLauncher, profile);
/* 131 */     dialog.add(editor);
/* 132 */     dialog.pack();
/* 133 */     dialog.setLocationRelativeTo(frame);
/* 134 */     dialog.setVisible(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\profile\ProfileEditorPopup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */