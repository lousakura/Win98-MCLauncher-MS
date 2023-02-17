/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name = "XMLConfigurationFactory", category = "ConfigurationFactory")
/*    */ @Order(5)
/*    */ public class XMLConfigurationFactory
/*    */   extends ConfigurationFactory
/*    */ {
/* 31 */   public static final String[] SUFFIXES = new String[] { ".xml", "*" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Configuration getConfiguration(ConfigurationFactory.ConfigurationSource source) {
/* 40 */     return new XMLConfiguration(source);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String[] getSupportedTypes() {
/* 49 */     return SUFFIXES;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\config\XMLConfigurationFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */