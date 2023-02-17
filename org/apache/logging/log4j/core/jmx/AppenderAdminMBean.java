package org.apache.logging.log4j.core.jmx;

public interface AppenderAdminMBean {
  public static final String PATTERN = "org.apache.logging.log4j2:type=LoggerContext,ctx=%s,sub=Appender,name=%s";
  
  String getName();
  
  String getLayout();
  
  boolean isExceptionSuppressed();
  
  String getErrorHandler();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\jmx\AppenderAdminMBean.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */