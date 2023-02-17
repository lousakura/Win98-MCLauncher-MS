/*    */ package com.mojang.launcher.game;
/*    */ 
/*    */ public enum GameInstanceStatus {
/*  4 */   PREPARING("Preparing..."),
/*  5 */   DOWNLOADING("Downloading..."),
/*  6 */   INSTALLING("Installing..."),
/*  7 */   LAUNCHING("Launching..."),
/*  8 */   PLAYING("Playing..."),
/*  9 */   IDLE("Idle");
/*    */   
/*    */   private final String name;
/*    */ 
/*    */   
/*    */   GameInstanceStatus(String name) {
/* 15 */     this.name = name;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 19 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 24 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\GameInstanceStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */