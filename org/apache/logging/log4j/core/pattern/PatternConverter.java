package org.apache.logging.log4j.core.pattern;

public interface PatternConverter {
  void format(Object paramObject, StringBuilder paramStringBuilder);
  
  String getName();
  
  String getStyleClass(Object paramObject);
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\pattern\PatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */