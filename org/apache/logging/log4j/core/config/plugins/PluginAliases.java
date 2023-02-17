package org.apache.logging.log4j.core.config.plugins;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE})
public @interface PluginAliases {
  String[] value();
}


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\config\plugins\PluginAliases.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */