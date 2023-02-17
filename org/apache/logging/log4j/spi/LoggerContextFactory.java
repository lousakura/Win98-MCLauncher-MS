package org.apache.logging.log4j.spi;

import java.net.URI;

public interface LoggerContextFactory {
  LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean);
  
  LoggerContext getContext(String paramString, ClassLoader paramClassLoader, boolean paramBoolean, URI paramURI);
  
  void removeContext(LoggerContext paramLoggerContext);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\spi\LoggerContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */