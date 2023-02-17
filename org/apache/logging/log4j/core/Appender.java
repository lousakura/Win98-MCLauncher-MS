package org.apache.logging.log4j.core;

import java.io.Serializable;

public interface Appender extends LifeCycle {
  void append(LogEvent paramLogEvent);
  
  String getName();
  
  Layout<? extends Serializable> getLayout();
  
  boolean ignoreExceptions();
  
  ErrorHandler getHandler();
  
  void setHandler(ErrorHandler paramErrorHandler);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\Appender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */