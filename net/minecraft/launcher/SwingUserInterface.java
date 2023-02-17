/*     */ package net.minecraft.launcher;
/*     */ import com.google.common.util.concurrent.SettableFuture;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.launcher.events.GameOutputLogProcessor;
/*     */ import com.mojang.launcher.updater.DownloadProgress;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import java.awt.Component;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.Timer;
/*     */ import java.util.TimerTask;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.UIManager;
/*     */ import net.minecraft.launcher.game.MinecraftGameRunner;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.ui.LauncherPanel;
/*     */ import net.minecraft.launcher.ui.popups.login.LogInPopup;
/*     */ import net.minecraft.launcher.ui.tabs.CrashReportTab;
/*     */ import net.minecraft.launcher.ui.tabs.GameOutputTab;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SwingUserInterface implements MinecraftUserInterface {
/*  34 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private static final long MAX_SHUTDOWN_TIME = 10000L;
/*     */   private final Launcher minecraftLauncher;
/*     */   private LauncherPanel launcherPanel;
/*     */   private final JFrame frame;
/*     */   
/*     */   public SwingUserInterface(Launcher minecraftLauncher, JFrame frame) {
/*  42 */     this.minecraftLauncher = minecraftLauncher;
/*  43 */     this.frame = frame;
/*     */     
/*  45 */     setLookAndFeel();
/*     */   }
/*     */   
/*     */   private static void setLookAndFeel() {
/*  49 */     JFrame frame = new JFrame();
/*     */     try {
/*  51 */       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
/*  52 */     } catch (Throwable ignored) {
/*     */       try {
/*  54 */         LOGGER.error("Your java failed to provide normal look and feel, trying the old fallback now");
/*  55 */         UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/*  56 */       } catch (Throwable t) {
/*  57 */         LOGGER.error("Unexpected exception setting look and feel", t);
/*     */       } 
/*     */     } 
/*  60 */     JPanel panel = new JPanel();
/*  61 */     panel.setBorder(BorderFactory.createTitledBorder("test"));
/*  62 */     frame.add(panel);
/*     */     
/*     */     try {
/*  65 */       frame.pack();
/*  66 */     } catch (Throwable ignored) {
/*  67 */       LOGGER.error("Custom (broken) theme detected, falling back onto x-platform theme");
/*     */       try {
/*  69 */         UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
/*  70 */       } catch (Throwable ex) {
/*  71 */         LOGGER.error("Unexpected exception setting look and feel", ex);
/*     */       } 
/*     */     } 
/*     */     
/*  75 */     frame.dispose();
/*     */   }
/*     */   
/*     */   public void showLoginPrompt(final Launcher minecraftLauncher, final LogInPopup.Callback callback) {
/*  79 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*  82 */             LogInPopup popup = new LogInPopup(minecraftLauncher, callback);
/*  83 */             SwingUserInterface.this.launcherPanel.setCard("login", (JPanel)popup);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public void initializeFrame() {
/*  89 */     this.frame.getContentPane().removeAll();
/*  90 */     this.frame.setTitle("Minecraft Launcher " + LauncherConstants.getVersionName() + LauncherConstants.PROPERTIES.getEnvironment().getTitle());
/*  91 */     this.frame.setPreferredSize(new Dimension(900, 580));
/*  92 */     this.frame.setDefaultCloseOperation(2);
/*     */     
/*  94 */     this.frame.addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e) {
/*  97 */             SwingUserInterface.LOGGER.info("Window closed, shutting down.");
/*  98 */             SwingUserInterface.this.frame.setVisible(false);
/*  99 */             SwingUserInterface.this.frame.dispose();
/* 100 */             SwingUserInterface.LOGGER.info("Halting executors");
/* 101 */             SwingUserInterface.this.minecraftLauncher.getLauncher().getVersionManager().getExecutorService().shutdown();
/* 102 */             SwingUserInterface.LOGGER.info("Awaiting termination.");
/*     */             try {
/* 104 */               SwingUserInterface.this.minecraftLauncher.getLauncher().getVersionManager().getExecutorService().awaitTermination(10L, TimeUnit.SECONDS);
/* 105 */             } catch (InterruptedException e1) {
/* 106 */               SwingUserInterface.LOGGER.info("Termination took too long.");
/*     */             } 
/* 108 */             SwingUserInterface.LOGGER.info("Goodbye.");
/* 109 */             SwingUserInterface.this.forcefullyShutdown();
/*     */           }
/*     */         });
/*     */     
/*     */     try {
/* 114 */       InputStream in = Launcher.class.getResourceAsStream("/favicon.png");
/* 115 */       if (in != null) {
/* 116 */         this.frame.setIconImage(ImageIO.read(in));
/*     */       }
/* 118 */     } catch (IOException iOException) {}
/*     */ 
/*     */     
/* 121 */     this.launcherPanel = new LauncherPanel(this.minecraftLauncher);
/*     */     
/* 123 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 126 */             SwingUserInterface.this.frame.add((Component)SwingUserInterface.this.launcherPanel);
/* 127 */             SwingUserInterface.this.frame.pack();
/* 128 */             SwingUserInterface.this.frame.setVisible(true);
/* 129 */             SwingUserInterface.this.frame.setAlwaysOnTop(true);
/* 130 */             SwingUserInterface.this.frame.setAlwaysOnTop(false);
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void forcefullyShutdown() {
/*     */     try {
/* 137 */       Timer timer = new Timer();
/* 138 */       timer.schedule(new TimerTask()
/*     */           {
/*     */             public void run() {
/* 141 */               Runtime.getRuntime().halt(0);
/*     */             }
/*     */           },  10000L);
/*     */       
/* 145 */       System.exit(0);
/* 146 */     } catch (Throwable ignored) {
/* 147 */       Runtime.getRuntime().halt(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void showOutdatedNotice() {
/* 153 */     String error = "Sorry, but your launcher is outdated! Please redownload it at https://mojang.com/2013/06/minecraft-1-6-pre-release/";
/*     */     
/* 155 */     this.frame.getContentPane().removeAll();
/*     */     
/* 157 */     int result = JOptionPane.showOptionDialog(this.frame, error, "Outdated launcher", 0, 0, null, (Object[])LauncherConstants.BOOTSTRAP_OUT_OF_DATE_BUTTONS, LauncherConstants.BOOTSTRAP_OUT_OF_DATE_BUTTONS[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     if (result == 0) {
/*     */       try {
/* 168 */         OperatingSystem.openLink(new URI("https://mojang.com/2013/06/minecraft-1-6-pre-release/"));
/* 169 */       } catch (URISyntaxException e) {
/* 170 */         LOGGER.error("Couldn't open bootstrap download link. Please visit https://mojang.com/2013/06/minecraft-1-6-pre-release/ manually.", e);
/*     */       } 
/*     */     }
/* 173 */     this.minecraftLauncher.getLauncher().shutdownLauncher();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void showLoginPrompt() {
/* 179 */     final ProfileManager profileManager = this.minecraftLauncher.getProfileManager();
/*     */     
/*     */     try {
/* 182 */       profileManager.saveProfiles();
/* 183 */     } catch (IOException e) {
/* 184 */       LOGGER.error("Couldn't save profiles before logging in!", e);
/*     */     } 
/*     */     
/* 187 */     final Profile selectedProfile = profileManager.getSelectedProfile();
/* 188 */     showLoginPrompt(this.minecraftLauncher, new LogInPopup.Callback()
/*     */         {
/*     */           public void onLogIn(String uuid) {
/* 191 */             UserAuthentication auth = profileManager.getAuthDatabase().getByUUID(uuid);
/* 192 */             profileManager.setSelectedUser(uuid);
/*     */             
/* 194 */             if (selectedProfile.getName().equals("(Default)") && auth.getSelectedProfile() != null) {
/* 195 */               String playerName = auth.getSelectedProfile().getName();
/* 196 */               String profileName = auth.getSelectedProfile().getName();
/* 197 */               int count = 1;
/*     */               
/* 199 */               while (profileManager.getProfiles().containsKey(profileName)) {
/* 200 */                 profileName = playerName + " " + ++count;
/*     */               }
/*     */               
/* 203 */               Profile newProfile = new Profile(selectedProfile);
/* 204 */               newProfile.setName(profileName);
/* 205 */               profileManager.getProfiles().put(profileName, newProfile);
/* 206 */               profileManager.getProfiles().remove("(Default)");
/* 207 */               profileManager.setSelectedProfile(profileName);
/*     */             } 
/*     */             
/*     */             try {
/* 211 */               profileManager.saveProfiles();
/* 212 */             } catch (IOException e) {
/* 213 */               SwingUserInterface.LOGGER.error("Couldn't save profiles after logging in!", e);
/*     */             } 
/*     */             
/* 216 */             if (uuid == null) {
/* 217 */               SwingUserInterface.this.minecraftLauncher.getLauncher().shutdownLauncher();
/*     */             } else {
/* 219 */               profileManager.fireRefreshEvent();
/*     */             } 
/*     */             
/* 222 */             SwingUserInterface.this.launcherPanel.setCard("launcher", null);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(final boolean visible) {
/* 229 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 232 */             SwingUserInterface.this.frame.setVisible(visible);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdownLauncher() {
/* 239 */     if (SwingUtilities.isEventDispatchThread()) {
/* 240 */       LOGGER.info("Requesting window close");
/* 241 */       this.frame.dispatchEvent(new WindowEvent(this.frame, 201));
/*     */     } else {
/* 243 */       SwingUtilities.invokeLater(new Runnable()
/*     */           {
/*     */             public void run() {
/* 246 */               SwingUserInterface.this.shutdownLauncher();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDownloadProgress(final DownloadProgress downloadProgress) {
/* 254 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 257 */             SwingUserInterface.this.launcherPanel.getProgressBar().setVisible(true);
/* 258 */             SwingUserInterface.this.launcherPanel.getProgressBar().setValue((int)(downloadProgress.getPercent() * 100.0F));
/* 259 */             SwingUserInterface.this.launcherPanel.getProgressBar().setString(downloadProgress.getStatus());
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void hideDownloadProgress() {
/* 266 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 269 */             SwingUserInterface.this.launcherPanel.getProgressBar().setVisible(false);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void showCrashReport(final CompleteVersion version, final File crashReportFile, final String crashReport) {
/* 276 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 279 */             SwingUserInterface.this.launcherPanel.getTabPanel().setCrashReport(new CrashReportTab(SwingUserInterface.this.minecraftLauncher, version, crashReportFile, crashReport));
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void gameLaunchFailure(final String reason) {
/* 286 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 289 */             JOptionPane.showMessageDialog(SwingUserInterface.this.frame, reason, "Cannot play game", 0);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePlayState() {
/* 296 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 299 */             SwingUserInterface.this.launcherPanel.getBottomBar().getPlayButtonPanel().checkState();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public GameOutputLogProcessor showGameOutputTab(final MinecraftGameRunner gameRunner) {
/* 306 */     final SettableFuture<GameOutputLogProcessor> future = SettableFuture.create();
/*     */     
/* 308 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 311 */             GameOutputTab tab = new GameOutputTab(SwingUserInterface.this.minecraftLauncher);
/* 312 */             future.set(tab);
/* 313 */             UserAuthentication auth = gameRunner.getAuth();
/* 314 */             String name = (auth.getSelectedProfile() == null) ? "Demo" : auth.getSelectedProfile().getName();
/* 315 */             SwingUserInterface.this.launcherPanel.getTabPanel().removeTab("Game Output (" + name + ")");
/* 316 */             SwingUserInterface.this.launcherPanel.getTabPanel().addTab("Game Output (" + name + ")", (Component)tab);
/* 317 */             SwingUserInterface.this.launcherPanel.getTabPanel().setSelectedComponent((Component)tab);
/*     */           }
/*     */         });
/*     */     
/* 321 */     return (GameOutputLogProcessor)Futures.getUnchecked((Future)future);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDowngradeProfiles() {
/* 326 */     int result = JOptionPane.showOptionDialog(this.frame, "It looks like you've used a newer launcher than this one! If you go back to using this one, we will need to reset your configuration.", "Outdated launcher", 0, 0, null, (Object[])LauncherConstants.LAUNCHER_OUT_OF_DATE_BUTTONS, LauncherConstants.LAUNCHER_OUT_OF_DATE_BUTTONS[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     return (result == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTitle() {
/* 341 */     return "Minecraft Launcher " + LauncherConstants.getVersionName();
/*     */   }
/*     */   
/*     */   public JFrame getFrame() {
/* 345 */     return this.frame;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\SwingUserInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */