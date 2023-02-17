package com.mojang.launcher.versions;

import java.util.Date;

public interface CompleteVersion extends Version {
  String getId();
  
  ReleaseType getType();
  
  Date getUpdatedTime();
  
  Date getReleaseTime();
  
  int getMinimumLauncherVersion();
  
  boolean appliesToCurrentEnvironment();
  
  String getIncompatibilityReason();
  
  boolean isSynced();
  
  void setSynced(boolean paramBoolean);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\CompleteVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */