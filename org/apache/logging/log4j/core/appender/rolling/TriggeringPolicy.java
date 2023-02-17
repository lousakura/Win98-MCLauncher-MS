package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.LogEvent;

public interface TriggeringPolicy {
  void initialize(RollingFileManager paramRollingFileManager);
  
  boolean isTriggeringEvent(LogEvent paramLogEvent);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\rolling\TriggeringPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */