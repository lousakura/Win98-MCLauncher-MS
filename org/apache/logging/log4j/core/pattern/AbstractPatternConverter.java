/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public abstract class AbstractPatternConverter
/*    */   implements PatternConverter
/*    */ {
/* 35 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String name;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final String style;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractPatternConverter(String name, String style) {
/* 54 */     this.name = name;
/* 55 */     this.style = style;
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
/*    */   public final String getName() {
/* 67 */     return this.name;
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
/*    */   
/*    */   public String getStyleClass(Object e) {
/* 81 */     return this.style;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\pattern\AbstractPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */