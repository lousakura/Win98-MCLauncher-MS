package org.apache.logging.log4j.core.appender.rolling;

import org.apache.logging.log4j.core.appender.rolling.helper.Action;

public interface RolloverDescription {
  String getActiveFileName();
  
  boolean getAppend();
  
  Action getSynchronous();
  
  Action getAsynchronous();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\rolling\RolloverDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */