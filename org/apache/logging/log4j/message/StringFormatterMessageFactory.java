/*    */ package org.apache.logging.log4j.message;
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
/*    */ public final class StringFormatterMessageFactory
/*    */   extends AbstractMessageFactory
/*    */ {
/* 35 */   public static final StringFormatterMessageFactory INSTANCE = new StringFormatterMessageFactory();
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
/*    */   public Message newMessage(String message, Object... params) {
/* 48 */     return new StringFormattedMessage(message, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\StringFormatterMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */