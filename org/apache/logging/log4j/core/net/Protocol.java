/*    */ package org.apache.logging.log4j.core.net;
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
/*    */ public enum Protocol
/*    */ {
/* 24 */   TCP,
/*    */   
/* 26 */   UDP;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEqual(String name) {
/* 34 */     return name().equalsIgnoreCase(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\net\Protocol.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */