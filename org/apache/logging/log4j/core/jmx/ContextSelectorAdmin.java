/*    */ package org.apache.logging.log4j.core.jmx;
/*    */ 
/*    */ import javax.management.ObjectName;
/*    */ import org.apache.logging.log4j.core.helpers.Assert;
/*    */ import org.apache.logging.log4j.core.selector.ContextSelector;
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
/*    */ public class ContextSelectorAdmin
/*    */   implements ContextSelectorAdminMBean
/*    */ {
/*    */   private final ObjectName objectName;
/*    */   private final ContextSelector selector;
/*    */   
/*    */   public ContextSelectorAdmin(ContextSelector selector) {
/* 39 */     this.selector = (ContextSelector)Assert.isNotNull(selector, "ContextSelector");
/*    */     try {
/* 41 */       this.objectName = new ObjectName("org.apache.logging.log4j2:type=ContextSelector");
/* 42 */     } catch (Exception e) {
/* 43 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ObjectName getObjectName() {
/* 54 */     return this.objectName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getImplementationClassName() {
/* 59 */     return this.selector.getClass().getName();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\jmx\ContextSelectorAdmin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */