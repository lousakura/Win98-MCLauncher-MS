package org.apache.logging.log4j.core.config.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Plugin {
  public static final String EMPTY = "";
  
  String name();
  
  String category();
  
  String elementType() default "";
  
  boolean printObject() default false;
  
  boolean deferChildren() default false;
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\config\plugins\Plugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */