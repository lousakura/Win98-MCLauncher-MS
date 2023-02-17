package com.mojang.launcher.game.runner;

import com.mojang.launcher.game.GameInstanceStatus;
import com.mojang.launcher.updater.VersionSyncInfo;
import com.mojang.launcher.updater.download.DownloadJob;

public interface GameRunner {
  GameInstanceStatus getStatus();
  
  void playGame(VersionSyncInfo paramVersionSyncInfo);
  
  boolean hasRemainingJobs();
  
  void addJob(DownloadJob paramDownloadJob);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\runner\GameRunner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */