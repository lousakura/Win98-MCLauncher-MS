/*    */ package org.apache.logging.log4j.core;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
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
/*    */ public class AbstractServer
/*    */ {
/* 29 */   private final LoggerContext context = (LoggerContext)LogManager.getContext(false);
/*    */ 
/*    */   
/*    */   protected void log(LogEvent event) {
/* 33 */     Logger logger = this.context.getLogger(event.getLoggerName());
/* 34 */     if (logger.config.filter(event.getLevel(), event.getMarker(), event.getMessage(), event.getThrown()))
/* 35 */       logger.config.logEvent(event); 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\AbstractServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */