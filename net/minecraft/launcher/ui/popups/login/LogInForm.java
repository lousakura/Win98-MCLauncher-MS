/*     */ package net.minecraft.launcher.ui.popups.login;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.exceptions.AuthenticationException;
/*     */ import com.mojang.authlib.exceptions.InvalidCredentialsException;
/*     */ import com.mojang.authlib.exceptions.UserMigratedException;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.util.UUIDTypeAdapter;
/*     */ import java.awt.Font;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import javax.swing.Box;
/*     */ import javax.swing.JComboBox;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ import net.minecraft.launcher.profile.AuthenticationDatabase;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ public class LogInForm extends JPanel implements ActionListener {
/*  26 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final LogInPopup popup;
/*  28 */   private final JTextField usernameField = new JTextField();
/*  29 */   private final JPasswordField passwordField = new JPasswordField();
/*  30 */   private final JComboBox userDropdown = new JComboBox();
/*  31 */   private final JPanel userDropdownPanel = new JPanel();
/*     */   
/*     */   private final UserAuthentication authentication;
/*     */   
/*     */   public LogInForm(LogInPopup popup) {
/*  36 */     this.popup = popup;
/*  37 */     this.authentication = popup.getMinecraftLauncher().getProfileManager().getAuthDatabase().getAuthenticationService().createUserAuthentication(Agent.MINECRAFT);
/*     */     
/*  39 */     this.usernameField.addActionListener(this);
/*  40 */     this.passwordField.addActionListener(this);
/*     */     
/*  42 */     createInterface();
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  46 */     setLayout(new GridBagLayout());
/*  47 */     GridBagConstraints constraints = new GridBagConstraints();
/*  48 */     constraints.fill = 2;
/*  49 */     constraints.gridx = 0;
/*  50 */     constraints.gridy = -1;
/*  51 */     constraints.weightx = 1.0D;
/*     */     
/*  53 */     add(Box.createGlue());
/*     */     
/*  55 */     JLabel usernameLabel = new JLabel("Email Address or Username:");
/*  56 */     Font labelFont = usernameLabel.getFont().deriveFont(1);
/*  57 */     Font smalltextFont = usernameLabel.getFont().deriveFont(labelFont.getSize() - 2.0F);
/*     */     
/*  59 */     usernameLabel.setFont(labelFont);
/*  60 */     add(usernameLabel, constraints);
/*  61 */     add(this.usernameField, constraints);
/*     */     
/*  63 */     JLabel forgotUsernameLabel = new JLabel("(Which do I use?)");
/*  64 */     forgotUsernameLabel.setCursor(new Cursor(12));
/*  65 */     forgotUsernameLabel.setFont(smalltextFont);
/*  66 */     forgotUsernameLabel.setHorizontalAlignment(4);
/*  67 */     forgotUsernameLabel.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e) {
/*  70 */             OperatingSystem.openLink(LauncherConstants.URL_FORGOT_USERNAME);
/*     */           }
/*     */         });
/*  73 */     add(forgotUsernameLabel, constraints);
/*     */     
/*  75 */     add(Box.createVerticalStrut(10), constraints);
/*     */     
/*  77 */     JLabel passwordLabel = new JLabel("Password:");
/*  78 */     passwordLabel.setFont(labelFont);
/*  79 */     add(passwordLabel, constraints);
/*  80 */     add(this.passwordField, constraints);
/*     */     
/*  82 */     JLabel forgotPasswordLabel = new JLabel("(Forgot Password?)");
/*  83 */     forgotPasswordLabel.setCursor(new Cursor(12));
/*  84 */     forgotPasswordLabel.setFont(smalltextFont);
/*  85 */     forgotPasswordLabel.setHorizontalAlignment(4);
/*  86 */     forgotPasswordLabel.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e) {
/*  89 */             OperatingSystem.openLink(LauncherConstants.URL_FORGOT_PASSWORD_MINECRAFT);
/*     */           }
/*     */         });
/*  92 */     add(forgotPasswordLabel, constraints);
/*     */     
/*  94 */     createUserDropdownPanel(labelFont);
/*  95 */     add(this.userDropdownPanel, constraints);
/*     */     
/*  97 */     add(Box.createVerticalStrut(10), constraints);
/*     */   }
/*     */   
/*     */   protected void createUserDropdownPanel(Font labelFont) {
/* 101 */     this.userDropdownPanel.setLayout(new GridBagLayout());
/* 102 */     GridBagConstraints constraints = new GridBagConstraints();
/* 103 */     constraints.fill = 2;
/* 104 */     constraints.gridx = 0;
/* 105 */     constraints.gridy = -1;
/* 106 */     constraints.weightx = 1.0D;
/*     */     
/* 108 */     this.userDropdownPanel.add(Box.createVerticalStrut(8), constraints);
/*     */     
/* 110 */     JLabel userDropdownLabel = new JLabel("Character Name:");
/* 111 */     userDropdownLabel.setFont(labelFont);
/* 112 */     this.userDropdownPanel.add(userDropdownLabel, constraints);
/* 113 */     this.userDropdownPanel.add(this.userDropdown, constraints);
/*     */     
/* 115 */     this.userDropdownPanel.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(ActionEvent e) {
/* 120 */     if (e.getSource() == this.usernameField || e.getSource() == this.passwordField) {
/* 121 */       tryLogIn();
/*     */     }
/*     */   }
/*     */   
/*     */   public void tryLogIn() {
/* 126 */     if (this.authentication.isLoggedIn() && this.authentication.getSelectedProfile() == null && ArrayUtils.isNotEmpty((Object[])this.authentication.getAvailableProfiles())) {
/* 127 */       this.popup.setCanLogIn(false);
/*     */       
/* 129 */       GameProfile selectedProfile = null;
/* 130 */       for (GameProfile profile : this.authentication.getAvailableProfiles()) {
/* 131 */         if (profile.getName().equals(this.userDropdown.getSelectedItem())) {
/* 132 */           selectedProfile = profile;
/*     */           break;
/*     */         } 
/*     */       } 
/* 136 */       if (selectedProfile == null) selectedProfile = this.authentication.getAvailableProfiles()[0];
/*     */       
/* 138 */       final GameProfile finalSelectedProfile = selectedProfile;
/* 139 */       this.popup.getMinecraftLauncher().getLauncher().getVersionManager().getExecutorService().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/* 143 */                 LogInForm.this.authentication.selectGameProfile(finalSelectedProfile);
/* 144 */                 LogInForm.this.popup.getMinecraftLauncher().getProfileManager().getAuthDatabase().register(UUIDTypeAdapter.fromUUID(LogInForm.this.authentication.getSelectedProfile().getId()), LogInForm.this.authentication);
/* 145 */                 LogInForm.this.popup.setLoggedIn(UUIDTypeAdapter.fromUUID(LogInForm.this.authentication.getSelectedProfile().getId()));
/* 146 */               } catch (InvalidCredentialsException ex) {
/* 147 */                 LogInForm.LOGGER.error("Couldn't log in", (Throwable)ex);
/* 148 */                 LogInForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "Sorry, but we couldn't log you in right now.", "Please try again later." });
/* 149 */                 LogInForm.this.popup.setCanLogIn(true);
/* 150 */               } catch (AuthenticationException ex) {
/* 151 */                 LogInForm.LOGGER.error("Couldn't log in", (Throwable)ex);
/* 152 */                 LogInForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "Sorry, but we couldn't connect to our servers.", "Please make sure that you are online and that Minecraft is not blocked." });
/* 153 */                 LogInForm.this.popup.setCanLogIn(true);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } else {
/* 158 */       this.popup.setCanLogIn(false);
/* 159 */       this.authentication.logOut();
/* 160 */       this.authentication.setUsername(this.usernameField.getText());
/* 161 */       this.authentication.setPassword(String.valueOf(this.passwordField.getPassword()));
/* 162 */       final int passwordLength = (this.passwordField.getPassword()).length;
/*     */       
/* 164 */       this.passwordField.setText("");
/*     */       
/* 166 */       this.popup.getMinecraftLauncher().getLauncher().getVersionManager().getExecutorService().execute(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try {
/* 171 */                 LogInForm.this.authentication.logIn();
/* 172 */                 AuthenticationDatabase authDatabase = LogInForm.this.popup.getMinecraftLauncher().getProfileManager().getAuthDatabase();
/*     */                 
/* 174 */                 if (LogInForm.this.authentication.getSelectedProfile() == null) {
/* 175 */                   if (ArrayUtils.isNotEmpty((Object[])LogInForm.this.authentication.getAvailableProfiles())) {
/* 176 */                     for (GameProfile profile : LogInForm.this.authentication.getAvailableProfiles()) {
/* 177 */                       LogInForm.this.userDropdown.addItem(profile.getName());
/*     */                     }
/*     */                     
/* 180 */                     SwingUtilities.invokeLater(new Runnable()
/*     */                         {
/*     */                           public void run() {
/* 183 */                             LogInForm.this.usernameField.setEditable(false);
/* 184 */                             LogInForm.this.passwordField.setEditable(false);
/* 185 */                             LogInForm.this.userDropdownPanel.setVisible(true);
/* 186 */                             LogInForm.this.popup.repack();
/* 187 */                             LogInForm.this.popup.setCanLogIn(true);
/* 188 */                             LogInForm.this.passwordField.setText(StringUtils.repeat('*', passwordLength));
/*     */                           }
/*     */                         });
/*     */                   } else {
/* 192 */                     String uuid = "demo-" + LogInForm.this.authentication.getUserID();
/* 193 */                     authDatabase.register(uuid, LogInForm.this.authentication);
/* 194 */                     LogInForm.this.popup.setLoggedIn(uuid);
/*     */                   } 
/*     */                 } else {
/* 197 */                   authDatabase.register(UUIDTypeAdapter.fromUUID(LogInForm.this.authentication.getSelectedProfile().getId()), LogInForm.this.authentication);
/* 198 */                   LogInForm.this.popup.setLoggedIn(UUIDTypeAdapter.fromUUID(LogInForm.this.authentication.getSelectedProfile().getId()));
/*     */                 } 
/* 200 */               } catch (UserMigratedException ex) {
/* 201 */                 LogInForm.LOGGER.error("Couldn't log in", (Throwable)ex);
/* 202 */                 LogInForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "Sorry, but we can't log you in with your username.", "You have migrated your account, please use your email address." });
/* 203 */                 LogInForm.this.popup.setCanLogIn(true);
/* 204 */               } catch (InvalidCredentialsException ex) {
/* 205 */                 LogInForm.LOGGER.error("Couldn't log in", (Throwable)ex);
/* 206 */                 LogInForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "Sorry, but your username or password is incorrect!", "Please try again. If you need help, try the 'Forgot Password' link." });
/* 207 */                 LogInForm.this.popup.setCanLogIn(true);
/* 208 */               } catch (AuthenticationException ex) {
/* 209 */                 LogInForm.LOGGER.error("Couldn't log in", (Throwable)ex);
/* 210 */                 LogInForm.this.popup.getErrorForm().displayError((Throwable)ex, new String[] { "Sorry, but we couldn't connect to our servers.", "Please make sure that you are online and that Minecraft is not blocked." });
/* 211 */                 LogInForm.this.popup.setCanLogIn(true);
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\login\LogInForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */