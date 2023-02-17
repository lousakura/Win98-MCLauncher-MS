/*    */ package com.mojang.launcher.game.process.direct;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.collect.EvictingQueue;
/*    */ import com.mojang.launcher.events.GameOutputLogProcessor;
/*    */ import com.mojang.launcher.game.process.AbstractGameProcess;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DirectGameProcess
/*    */   extends AbstractGameProcess
/*    */ {
/*    */   private static final int MAX_SYSOUT_LINES = 5;
/*    */   private final Process process;
/*    */   protected final DirectProcessInputMonitor monitor;
/* 17 */   private final Collection<String> sysOutLines = (Collection<String>)EvictingQueue.create(5);
/*    */   
/*    */   public DirectGameProcess(List<String> commands, Process process, Predicate<String> sysOutFilter, GameOutputLogProcessor logProcessor) {
/* 20 */     super(commands, sysOutFilter);
/* 21 */     this.process = process;
/* 22 */     this.monitor = new DirectProcessInputMonitor(this, logProcessor);
/*    */     
/* 24 */     this.monitor.start();
/*    */   }
/*    */   
/*    */   public Process getRawProcess() {
/* 28 */     return this.process;
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<String> getSysOutLines() {
/* 33 */     return this.sysOutLines;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRunning() {
/*    */     try {
/* 39 */       this.process.exitValue();
/* 40 */     } catch (IllegalThreadStateException ex) {
/* 41 */       return true;
/*    */     } 
/*    */     
/* 44 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getExitCode() {
/*    */     try {
/* 50 */       return this.process.exitValue();
/* 51 */     } catch (IllegalThreadStateException ex) {
/* 52 */       ex.fillInStackTrace();
/* 53 */       throw ex;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 62 */     return Objects.toStringHelper(this).add("process", this.process).add("monitor", this.monitor).toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public void stop() {
/* 67 */     this.process.destroy();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\direct\DirectGameProcess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */