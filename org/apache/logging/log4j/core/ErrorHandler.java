package org.apache.logging.log4j.core;

public interface ErrorHandler {
  void error(String paramString);
  
  void error(String paramString, Throwable paramThrowable);
  
  void error(String paramString, LogEvent paramLogEvent, Throwable paramThrowable);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\ErrorHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */