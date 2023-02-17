package org.apache.logging.log4j.message;

public interface MultiformatMessage extends Message {
  String getFormattedMessage(String[] paramArrayOfString);
  
  String[] getFormats();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\MultiformatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */