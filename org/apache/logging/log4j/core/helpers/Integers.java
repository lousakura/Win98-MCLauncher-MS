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
/*    */ 
/*    */ 
/*    */ public class Integers
/*    */ {
/*    */   public static int parseInt(String s, int defaultValue) {
/* 36 */     return Strings.isEmpty(s) ? defaultValue : Integer.parseInt(s);
/*    */   }
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
/*    */   public static int parseInt(String s) {
/* 49 */     return parseInt(s, 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\helpers\Integers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */