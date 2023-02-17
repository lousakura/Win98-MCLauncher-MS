/*    */ package joptsimple;
/*    */ 
/*    */ import java.util.Collections;
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
/*    */ class IllegalOptionSpecificationException
/*    */   extends OptionException
/*    */ {
/*    */   private static final long serialVersionUID = -1L;
/*    */   
/*    */   IllegalOptionSpecificationException(String option) {
/* 39 */     super(Collections.singletonList(option));
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 44 */     return singleOptionMessage() + " is not a legal option character";
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\joptsimple\IllegalOptionSpecificationException.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */