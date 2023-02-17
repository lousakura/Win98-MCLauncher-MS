package org.apache.logging.log4j.core.appender.rolling.helper;

import java.io.IOException;

public interface Action extends Runnable {
  boolean execute() throws IOException;
  
  void close();
  
  boolean isComplete();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\rolling\helper\Action.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */