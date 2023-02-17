package org.apache.logging.log4j.core.appender.rewrite;

import org.apache.logging.log4j.core.LogEvent;

public interface RewritePolicy {
  LogEvent rewrite(LogEvent paramLogEvent);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\rewrite\RewritePolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */