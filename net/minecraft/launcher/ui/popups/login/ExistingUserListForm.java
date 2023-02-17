/*     */ package net.minecraft.launcher.ui.popups.login;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.Insets;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.io.IOException;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ExistingUserListForm extends JPanel implements ActionListener {
/*  20 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final LogInPopup popup;
/*  22 */   private final JComboBox userDropdown = new JComboBox();
/*     */   private final AuthenticationDatabase authDatabase;
/*  24 */   private final JButton playButton = new JButton("Play");
/*  25 */   private final JButton logOutButton = new JButton("Log Out");
/*     */   
/*     */   private final ProfileManager profileManager;
/*     */   
/*     */   public ExistingUserListForm(LogInPopup popup) {
/*  30 */     this.popup = popup;
/*  31 */     this.profileManager = popup.getMinecraftLauncher().getProfileManager();
/*  32 */     this.authDatabase = popup.getMinecraftLauncher().getProfileManager().getAuthDatabase();
/*     */     
/*  34 */     fillUsers();
/*  35 */     createInterface();
/*     */     
/*  37 */     this.playButton.addActionListener(this);
/*  38 */     this.logOutButton.addActionListener(this);
/*     */   }
/*     */   
/*     */   private void fillUsers() {
/*  42 */     for (String user : this.authDatabase.getKnownNames()) {
/*  43 */       this.userDropdown.addItem(user);
/*  44 */       if (this.profileManager.getSelectedUser() != null && Objects.equal(this.authDatabase.getByUUID(this.profileManager.getSelectedUser()), this.authDatabase.getByName(user))) {
/*  45 */         this.userDropdown.setSelectedItem(user);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  51 */     setLayout(new GridBagLayout());
/*  52 */     GridBagConstraints constraints = new GridBagConstraints();
/*  53 */     constraints.fill = 2;
/*  54 */     constraints.gridx = 0;
/*  55 */     constraints.gridy = -1;
/*  56 */     constraints.gridwidth = 2;
/*  57 */     constraints.weightx = 1.0D;
/*     */     
/*  59 */     add(Box.createGlue());
/*     */     
/*  61 */     String currentUser = (this.authDatabase.getKnownNames().size() == 1) ? this.authDatabase.getKnownNames().iterator().next() : (this.authDatabase.getKnownNames().size() + " different users");
/*  62 */     String thisOrThese = (this.authDatabase.getKnownNames().size() == 1) ? "this account" : "one of these accounts";
/*  63 */     add(new JLabel("You're already logged in as " + currentUser + "."), constraints);
/*  64 */     add(new JLabel("You may use " + thisOrThese + " and skip authentication."), constraints);
/*     */     
/*  66 */     add(Box.createVerticalStrut(5), constraints);
/*     */     
/*  68 */     JLabel usernameLabel = new JLabel("Existing User:");
/*  69 */     Font labelFont = usernameLabel.getFont().deriveFont(1);
/*     */     
/*  71 */     usernameLabel.setFont(labelFont);
/*  72 */     add(usernameLabel, constraints);
/*     */     
/*  74 */     constraints.gridwidth = 1;
/*  75 */     add(this.userDropdown, constraints);
/*     */     
/*  77 */     constraints.gridx = 1;
/*  78 */     constraints.gridy = 5;
/*  79 */     constraints.weightx = 0.0D;
/*  80 */     constraints.insets = new Insets(0, 5, 0, 0);
/*  81 */     add(this.playButton, constraints);
/*  82 */     constraints.gridx = 2;
/*  83 */     add(this.logOutButton, constraints);
/*  84 */     constraints.insets = new Insets(0, 0, 0, 0);
/*  85 */     constraints.weightx = 1.0D;
/*  86 */     constraints.gridx = 0;
/*  87 */     constraints.gridy = -1;
/*     */     
/*  89 */     constraints.gridwidth = 2;
/*     */     
/*  91 */     add(Box.createVerticalStrut(5), constraints);
/*  92 */     add(new JLabel("Alternatively, log in with a new account below:"), constraints);
/*  93 */     add(new JPopupMenu.Separator(), constraints);
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/*     */     final UserAuthentication auth;
/*     */     final String uuid;
/* 100 */     final Object selected = this.userDropdown.getSelectedItem();
/*     */     
/* 102 */     if (selected != null && selected instanceof String) {
/* 103 */       auth = this.authDatabase.getByName((String)selected);
/*     */       
/* 105 */       if (auth.getSelectedProfile() == null) {
/* 106 */         uuid = "demo-" + auth.getUserID();
/*     */       } else {
/* 108 */         uuid = UUIDTypeAdapter.fromUUID(auth.getSelectedProfile().getId());
/*     */       } 
/*     */     } else {
/* 111 */       auth = null;
/* 112 */       uuid = null;
/*     */     } 
/*     */     
/* 115 */     if (e.getSource() == this.playButton) {
/* 116 */       this.popup.setCanLogIn(false);
/*     */       
/* 118 */       this.popup.getMinecraftLauncher().getLauncher().getVersionManager().getExecutorService().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 121 */               if (auth != null && uuid != null) {
/*     */                 try {
/* 123 */                   if (!auth.canPlayOnline()) {
/* 124 */                     auth.logIn();
/*     */                   }
/* 126 */                   ExistingUserListForm.this.popup.setLoggedIn(uuid);
/* 127 */                 } catch (AuthenticationException ex) {
/* 128 */                   ExistingUserListForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "We couldn't log you back in as " + this.val$selected + ".", "Please try to log in again." });
/*     */                   
/* 130 */                   ExistingUserListForm.this.removeUser((String)selected, uuid);
/*     */                   
/* 132 */                   ExistingUserListForm.this.popup.setCanLogIn(true);
/*     */                 } 
/*     */               } else {
/* 135 */                 ExistingUserListForm.this.popup.setCanLogIn(true);
/*     */               } 
/*     */             }
/*     */           });
/* 139 */     } else if (e.getSource() == this.logOutButton) {
/* 140 */       removeUser((String)selected, uuid);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void removeUser(final String name, final String uuid) {
/* 145 */     if (!SwingUtilities.isEventDispatchThread()) {
/* 146 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 149 */               ExistingUserListForm.this.removeUser(name, uuid);
/*     */             }
/*     */           });
/*     */     } else {
/* 153 */       this.userDropdown.removeItem(name);
/* 154 */       this.authDatabase.removeUUID(uuid);
/*     */       
/*     */       try {
/* 157 */         this.profileManager.saveProfiles();
/* 158 */       } catch (IOException e) {
/* 159 */         LOGGER.error("Couldn't save profiles whilst removing " + name + " / " + uuid + " from database", e);
/*     */       } 
/*     */       
/* 162 */       if (this.userDropdown.getItemCount() == 0)
/* 163 */         this.popup.remove(this); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\login\ExistingUserListForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */