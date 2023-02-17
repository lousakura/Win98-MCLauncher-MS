/*    */ package org.apache.logging.log4j.simple;
/*    */ 
/*    */ import java.net.URI;
/*    */ import org.apache.logging.log4j.spi.LoggerContext;
/*    */ import org.apache.logging.log4j.spi.LoggerContextFactory;
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
/*    */ public class SimpleLoggerContextFactory
/*    */   implements LoggerContextFactory
/*    */ {
/* 29 */   private static LoggerContext context = new SimpleLoggerContext();
/*    */ 
/*    */   
/*    */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext) {
/* 33 */     return context;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public LoggerContext getContext(String fqcn, ClassLoader loader, boolean currentContext, URI configLocation) {
/* 39 */     return context;
/*    */   }
/*    */   
/*    */   public void removeContext(LoggerContext context) {}
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\simple\SimpleLoggerContextFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */