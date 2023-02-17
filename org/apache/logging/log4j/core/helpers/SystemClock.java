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
/*    */ public class SystemClock
/*    */   implements Clock
/*    */ {
/*    */   public long currentTimeMillis() {
/* 30 */     return System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\helpers\SystemClock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */