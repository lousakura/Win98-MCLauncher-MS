/*    */ package org.apache.logging.log4j.core.config;
/*    */ 
/*    */ import org.apache.logging.log4j.Level;
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
/*    */ public class NullConfiguration
/*    */   extends BaseConfiguration
/*    */ {
/*    */   public static final String NULL_NAME = "Null";
/*    */   
/*    */   public NullConfiguration() {
/* 30 */     setName("Null");
/* 31 */     LoggerConfig root = getRootLogger();
/* 32 */     root.setLevel(Level.OFF);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\config\NullConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */