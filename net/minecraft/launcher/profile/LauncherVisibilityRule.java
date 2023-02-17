/*    */ package net.minecraft.launcher.profile;
/*    */ 
/*    */ public enum LauncherVisibilityRule {
/*  4 */   HIDE_LAUNCHER("Hide launcher and re-open when game closes"),
/*  5 */   CLOSE_LAUNCHER("Close launcher when game starts"),
/*  6 */   DO_NOTHING("Keep the launcher open");
/*    */   
/*    */   private final String name;
/*    */   
/*    */   LauncherVisibilityRule(String name) {
/* 11 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 15 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\profile\LauncherVisibilityRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */