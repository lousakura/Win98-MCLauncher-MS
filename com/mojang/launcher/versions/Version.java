package com.mojang.launcher.versions;

import java.util.Date;

public interface Version {
  String getId();
  
  ReleaseType getType();
  
  Date getUpdatedTime();
  
  Date getReleaseTime();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\Version.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */