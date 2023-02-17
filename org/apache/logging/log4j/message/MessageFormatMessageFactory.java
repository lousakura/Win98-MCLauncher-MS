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
/*    */ 
/*    */ public class MessageFormatMessageFactory
/*    */   extends AbstractMessageFactory
/*    */ {
/*    */   public Message newMessage(String message, Object... params) {
/* 37 */     return new MessageFormatMessage(message, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\MessageFormatMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */