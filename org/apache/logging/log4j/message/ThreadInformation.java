package org.apache.logging.log4j.message;

interface ThreadInformation {
  void printThreadInfo(StringBuilder paramStringBuilder);
  
  void printStack(StringBuilder paramStringBuilder, StackTraceElement[] paramArrayOfStackTraceElement);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\ThreadInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */