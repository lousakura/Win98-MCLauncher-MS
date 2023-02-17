/*    */ package org.apache.logging.log4j.core.layout;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.core.Layout;
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
/*    */ public abstract class AbstractLayout<T extends Serializable>
/*    */   implements Layout<T>
/*    */ {
/* 33 */   protected static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected byte[] header;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected byte[] footer;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getHeader() {
/* 49 */     return this.header;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHeader(byte[] header) {
/* 57 */     this.header = header;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte[] getFooter() {
/* 66 */     return this.footer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setFooter(byte[] footer) {
/* 74 */     this.footer = footer;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\layout\AbstractLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */