/*    */ package org.apache.logging.log4j.core.helpers;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Booleans
/*    */ {
/*    */   public static boolean parseBoolean(String s, boolean defaultValue) {
/* 34 */     return ("true".equalsIgnoreCase(s) || (defaultValue && !"false".equalsIgnoreCase(s)));
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\helpers\Booleans.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */