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
/*    */ 
/*    */ public class FormattedMessageFactory
/*    */   extends AbstractMessageFactory
/*    */ {
/*    */   public Message newMessage(String message, Object... params) {
/* 38 */     return new FormattedMessage(message, params);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\message\FormattedMessageFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */