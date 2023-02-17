package org.apache.logging.log4j.core.lookup;

import org.apache.logging.log4j.core.LogEvent;

public interface StrLookup {
  String lookup(String paramString);
  
  String lookup(LogEvent paramLogEvent, String paramString);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\lookup\StrLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */