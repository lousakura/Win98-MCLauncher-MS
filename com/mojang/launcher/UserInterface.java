package com.mojang.launcher;

import com.mojang.launcher.updater.DownloadProgress;
import com.mojang.launcher.versions.CompleteVersion;
import java.io.File;

public interface UserInterface {
  void showLoginPrompt();
  
  void setVisible(boolean paramBoolean);
  
  void shutdownLauncher();
  
  void hideDownloadProgress();
  
  void setDownloadProgress(DownloadProgress paramDownloadProgress);
  
  void showCrashReport(CompleteVersion paramCompleteVersion, File paramFile, String paramString);
  
  void gameLaunchFailure(String paramString);
  
  void updatePlayState();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\UserInterface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */