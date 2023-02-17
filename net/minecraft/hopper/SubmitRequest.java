/*    */ package net.minecraft.hopper;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class SubmitRequest {
/*    */   private String report;
/*    */   private String version;
/*    */   private String product;
/*    */   private Map<String, String> environment;
/*    */   
/*    */   public SubmitRequest(String report, String product, String version, Map<String, String> environment) {
/* 12 */     this.report = report;
/* 13 */     this.version = version;
/* 14 */     this.product = product;
/* 15 */     this.environment = environment;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\hopper\SubmitRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */