package org.apache.logging.log4j.spi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.MessageFactory;

public interface LoggerContext {
  Object getExternalContext();
  
  Logger getLogger(String paramString);
  
  Logger getLogger(String paramString, MessageFactory paramMessageFactory);
  
  boolean hasLogger(String paramString);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\spi\LoggerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */