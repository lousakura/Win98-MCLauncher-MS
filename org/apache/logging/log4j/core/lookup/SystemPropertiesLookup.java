/*    */ package org.apache.logging.log4j.core.lookup;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name = "sys", category = "Lookup")
/*    */ public class SystemPropertiesLookup
/*    */   implements StrLookup
/*    */ {
/*    */   public String lookup(String key) {
/*    */     try {
/* 36 */       return System.getProperty(key);
/* 37 */     } catch (Exception ex) {
/* 38 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String lookup(LogEvent event, String key) {
/*    */     try {
/* 51 */       return System.getProperty(key);
/* 52 */     } catch (Exception ex) {
/* 53 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\lookup\SystemPropertiesLookup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */