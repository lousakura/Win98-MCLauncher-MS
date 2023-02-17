package org.apache.logging.log4j;

import java.io.Serializable;

public interface Marker extends Serializable {
  String getName();
  
  Marker getParent();
  
  boolean isInstanceOf(Marker paramMarker);
  
  boolean isInstanceOf(String paramString);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\Marker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */