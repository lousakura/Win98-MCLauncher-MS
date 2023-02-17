/*    */ package net.minecraft.hopper;
/*    */ 
/*    */ public class Report {
/*    */   private int id;
/*    */   private boolean published;
/*    */   private String token;
/*    */   
/*    */   public int getId() {
/*  9 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean isPublished() {
/* 13 */     return this.published;
/*    */   }
/*    */   
/*    */   public String getToken() {
/* 17 */     return this.token;
/*    */   }
/*    */   
/*    */   public boolean canBePublished() {
/* 21 */     return (getToken() != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\hopper\Report.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */