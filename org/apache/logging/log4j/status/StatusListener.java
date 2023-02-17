package org.apache.logging.log4j.status;

import org.apache.logging.log4j.Level;

public interface StatusListener {
  void log(StatusData paramStatusData);
  
  Level getStatusLevel();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\status\StatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */