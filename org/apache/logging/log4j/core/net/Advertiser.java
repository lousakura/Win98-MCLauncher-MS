package org.apache.logging.log4j.core.net;

import java.util.Map;

public interface Advertiser {
  Object advertise(Map<String, String> paramMap);
  
  void unadvertise(Object paramObject);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\net\Advertiser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */