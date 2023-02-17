/*     */ package net.minecraft.launcher.game;
/*     */ 
/*     */ import com.google.common.base.Objects;
/*     */ import com.google.common.collect.BiMap;
/*     */ import com.google.common.collect.HashBiMap;
/*     */ import com.mojang.authlib.UserAuthentication;
/*     */ import com.mojang.launcher.game.GameInstanceStatus;
/*     */ import com.mojang.launcher.game.runner.GameRunner;
/*     */ import com.mojang.launcher.game.runner.GameRunnerListener;
/*     */ import com.mojang.launcher.updater.VersionSyncInfo;
/*     */ import java.io.File;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.profile.LauncherVisibilityRule;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ 
/*     */ public class GameLaunchDispatcher implements GameRunnerListener {
/*     */   private final Launcher launcher;
/*     */   private final String[] additionalLaunchArgs;
/*  21 */   private final ReentrantLock lock = new ReentrantLock();
/*  22 */   private final BiMap<UserAuthentication, MinecraftGameRunner> instances = (BiMap<UserAuthentication, MinecraftGameRunner>)HashBiMap.create();
/*     */   private boolean downloadInProgress = false;
/*     */   
/*     */   public GameLaunchDispatcher(Launcher launcher, String[] additionalLaunchArgs) {
/*  26 */     this.launcher = launcher;
/*  27 */     this.additionalLaunchArgs = additionalLaunchArgs;
/*     */   }
/*     */   
/*     */   public PlayStatus getStatus() {
/*  31 */     ProfileManager profileManager = this.launcher.getProfileManager();
/*  32 */     Profile profile = profileManager.getProfiles().isEmpty() ? null : profileManager.getSelectedProfile();
/*  33 */     UserAuthentication user = (profileManager.getSelectedUser() == null) ? null : profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/*     */     
/*  35 */     if (user == null || !user.isLoggedIn() || profile == null || this.launcher.getLauncher().getVersionManager().getVersions(profile.getVersionFilter()).isEmpty()) {
/*  36 */       return PlayStatus.LOADING;
/*     */     }
/*     */     
/*  39 */     this.lock.lock();
/*     */     try {
/*  41 */       if (this.downloadInProgress) {
/*  42 */         return PlayStatus.DOWNLOADING;
/*     */       }
/*  44 */       if (this.instances.containsKey(user)) {
/*  45 */         return PlayStatus.ALREADY_PLAYING;
/*     */       }
/*     */     } finally {
/*  48 */       this.lock.unlock();
/*     */     } 
/*     */     
/*  51 */     if (user.getSelectedProfile() == null)
/*  52 */       return PlayStatus.CAN_PLAY_DEMO; 
/*  53 */     if (user.canPlayOnline()) {
/*  54 */       return PlayStatus.CAN_PLAY_ONLINE;
/*     */     }
/*  56 */     return PlayStatus.CAN_PLAY_OFFLINE;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameInstanceStatus getInstanceStatus() {
/*  61 */     ProfileManager profileManager = this.launcher.getProfileManager();
/*  62 */     UserAuthentication user = (profileManager.getSelectedUser() == null) ? null : profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/*     */     
/*  64 */     this.lock.lock();
/*     */     try {
/*  66 */       GameRunner gameRunner = (GameRunner)this.instances.get(user);
/*  67 */       if (gameRunner != null) {
/*  68 */         return gameRunner.getStatus();
/*     */       }
/*     */     } finally {
/*  71 */       this.lock.unlock();
/*     */     } 
/*     */     
/*  74 */     return GameInstanceStatus.IDLE;
/*     */   }
/*     */   
/*     */   public void play() {
/*  78 */     ProfileManager profileManager = this.launcher.getProfileManager();
/*  79 */     final Profile profile = profileManager.getSelectedProfile();
/*  80 */     UserAuthentication user = (profileManager.getSelectedUser() == null) ? null : profileManager.getAuthDatabase().getByUUID(profileManager.getSelectedUser());
/*  81 */     final String lastVersionId = profile.getLastVersionId();
/*  82 */     final MinecraftGameRunner gameRunner = new MinecraftGameRunner(this.launcher, this.additionalLaunchArgs);
/*  83 */     gameRunner.setStatus(GameInstanceStatus.PREPARING);
/*     */     
/*  85 */     this.lock.lock();
/*     */     try {
/*  87 */       if (this.instances.containsKey(user) || this.downloadInProgress) {
/*     */         return;
/*     */       }
/*  90 */       this.instances.put(user, gameRunner);
/*  91 */       this.downloadInProgress = true;
/*     */     } finally {
/*  93 */       this.lock.unlock();
/*     */     } 
/*     */     
/*  96 */     this.launcher.getLauncher().getVersionManager().getExecutorService().execute(new Runnable()
/*     */         {
/*     */           public void run() {
/*  99 */             gameRunner.setVisibility((LauncherVisibilityRule)Objects.firstNonNull(profile.getLauncherVisibilityOnGameClose(), Profile.DEFAULT_LAUNCHER_VISIBILITY));
/*     */             
/* 101 */             VersionSyncInfo syncInfo = null;
/* 102 */             if (lastVersionId != null) {
/* 103 */               syncInfo = GameLaunchDispatcher.this.launcher.getLauncher().getVersionManager().getVersionSyncInfo(lastVersionId);
/*     */             }
/* 105 */             if (syncInfo == null || syncInfo.getLatestVersion() == null) {
/* 106 */               syncInfo = GameLaunchDispatcher.this.launcher.getLauncher().getVersionManager().getVersions(profile.getVersionFilter()).get(0);
/*     */             }
/*     */             
/* 109 */             gameRunner.setStatus(GameInstanceStatus.IDLE);
/* 110 */             gameRunner.addListener(GameLaunchDispatcher.this);
/* 111 */             gameRunner.playGame(syncInfo);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onGameInstanceChangedState(GameRunner runner, GameInstanceStatus status) {
/* 118 */     this.lock.lock();
/*     */     try {
/* 120 */       if (status == GameInstanceStatus.IDLE) {
/* 121 */         this.instances.inverse().remove(runner);
/*     */       }
/*     */       
/* 124 */       this.downloadInProgress = false;
/* 125 */       for (GameRunner instance : this.instances.values()) {
/* 126 */         if (instance.getStatus() != GameInstanceStatus.PLAYING) {
/* 127 */           this.downloadInProgress = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 132 */       this.launcher.getUserInterface().updatePlayState();
/*     */     } finally {
/* 134 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isRunningInSameFolder() {
/* 139 */     this.lock.lock();
/*     */     try {
/* 141 */       File currentGameDir = (File)Objects.firstNonNull(this.launcher.getProfileManager().getSelectedProfile().getGameDir(), this.launcher.getLauncher().getWorkingDirectory());
/* 142 */       for (MinecraftGameRunner runner : this.instances.values()) {
/* 143 */         Profile profile = runner.getSelectedProfile();
/* 144 */         if (profile != null) {
/* 145 */           File otherGameDir = (File)Objects.firstNonNull(profile.getGameDir(), this.launcher.getLauncher().getWorkingDirectory());
/* 146 */           if (currentGameDir.equals(otherGameDir)) {
/* 147 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 152 */       this.lock.unlock();
/*     */     } 
/*     */     
/* 155 */     return false;
/*     */   }
/*     */   
/*     */   public enum PlayStatus {
/* 159 */     LOADING("Loading...", false),
/* 160 */     CAN_PLAY_DEMO("Play Demo", true),
/* 161 */     CAN_PLAY_ONLINE("Play", true),
/* 162 */     CAN_PLAY_OFFLINE("Play Offline", true),
/* 163 */     ALREADY_PLAYING("Already Playing...", false),
/* 164 */     DOWNLOADING("Installing...", false);
/*     */     
/*     */     private final String name;
/*     */     
/*     */     private final boolean canPlay;
/*     */     
/*     */     PlayStatus(String name, boolean canPlay) {
/* 171 */       this.name = name;
/* 172 */       this.canPlay = canPlay;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 176 */       return this.name;
/*     */     }
/*     */     
/*     */     public boolean canPlay() {
/* 180 */       return this.canPlay;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\game\GameLaunchDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */