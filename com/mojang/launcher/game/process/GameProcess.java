package com.mojang.launcher.game.process;

import com.google.common.base.Predicate;
import java.util.Collection;
import java.util.List;

public interface GameProcess {
  List<String> getStartupArguments();
  
  Collection<String> getSysOutLines();
  
  Predicate<String> getSysOutFilter();
  
  boolean isRunning();
  
  void setExitRunnable(GameProcessRunnable paramGameProcessRunnable);
  
  GameProcessRunnable getExitRunnable();
  
  int getExitCode();
  
  void stop();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\GameProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */