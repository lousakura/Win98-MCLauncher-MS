/*    */ package com.mojang.launcher.game.process;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import java.util.List;
/*    */ 
/*    */ public abstract class AbstractGameProcess
/*    */   implements GameProcess {
/*    */   protected final List<String> arguments;
/*    */   protected final Predicate<String> sysOutFilter;
/*    */   private GameProcessRunnable onExit;
/*    */   
/*    */   public AbstractGameProcess(List<String> arguments, Predicate<String> sysOutFilter) {
/* 13 */     this.arguments = arguments;
/* 14 */     this.sysOutFilter = sysOutFilter;
/*    */   }
/*    */ 
/*    */   
/*    */   public Predicate<String> getSysOutFilter() {
/* 19 */     return this.sysOutFilter;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getStartupArguments() {
/* 24 */     return this.arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setExitRunnable(GameProcessRunnable runnable) {
/* 29 */     this.onExit = runnable;
/*    */     
/* 31 */     if (!isRunning() && runnable != null) {
/* 32 */       runnable.onGameProcessEnded(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProcessRunnable getExitRunnable() {
/* 38 */     return this.onExit;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\AbstractGameProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */