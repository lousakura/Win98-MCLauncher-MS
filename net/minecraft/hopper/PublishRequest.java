/*   */ package net.minecraft.hopper;
/*   */ 
/*   */ public class PublishRequest {
/*   */   private String token;
/*   */   private int report_id;
/*   */   
/*   */   public PublishRequest(Report report) {
/* 8 */     this.report_id = report.getId();
/* 9 */     this.token = report.getToken();
/*   */   }
/*   */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\hopper\PublishRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */