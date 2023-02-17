/*    */ package org.apache.logging.log4j.core.impl;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LoggerContext;
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
/*    */ public final class ContextAnchor
/*    */ {
/* 29 */   public static final ThreadLocal<LoggerContext> THREAD_CONTEXT = new ThreadLocal<LoggerContext>();
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\impl\ContextAnchor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */