package org.apache.logging.log4j.core.appender.rolling;

public interface RolloverStrategy {
  RolloverDescription rollover(RollingFileManager paramRollingFileManager) throws SecurityException;
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverStrategy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */