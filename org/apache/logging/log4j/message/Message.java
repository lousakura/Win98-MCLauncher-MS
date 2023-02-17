package org.apache.logging.log4j.message;

import java.io.Serializable;

public interface Message extends Serializable {
  String getFormattedMessage();
  
  String getFormat();
  
  Object[] getParameters();
  
  Throwable getThrowable();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\Message.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */