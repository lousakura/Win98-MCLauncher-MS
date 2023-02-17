/*    */ package com.mojang.launcher.game.process.direct;
/*    */ 
/*    */ import com.mojang.launcher.events.GameOutputLogProcessor;
/*    */ import com.mojang.launcher.game.process.GameProcess;
/*    */ import com.mojang.launcher.game.process.GameProcessRunnable;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStreamReader;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class DirectProcessInputMonitor extends Thread {
/* 14 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   private final DirectGameProcess process;
/*    */   private final GameOutputLogProcessor logProcessor;
/*    */   
/*    */   public DirectProcessInputMonitor(DirectGameProcess process, GameOutputLogProcessor logProcessor) {
/* 19 */     this.process = process;
/* 20 */     this.logProcessor = logProcessor;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 25 */     InputStreamReader reader = new InputStreamReader(this.process.getRawProcess().getInputStream());
/* 26 */     BufferedReader buf = new BufferedReader(reader);
/* 27 */     String line = null;
/*    */     
/* 29 */     while (this.process.isRunning()) {
/*    */       try {
/* 31 */         while ((line = buf.readLine()) != null) {
/* 32 */           this.logProcessor.onGameOutput((GameProcess)this.process, line);
/* 33 */           if (this.process.getSysOutFilter().apply(line) == Boolean.TRUE.booleanValue()) {
/* 34 */             this.process.getSysOutLines().add(line);
/*    */           }
/*    */         } 
/* 37 */       } catch (IOException ex) {
/* 38 */         LOGGER.error(ex);
/*    */       } finally {
/* 40 */         IOUtils.closeQuietly(reader);
/*    */       } 
/*    */     } 
/*    */     
/* 44 */     GameProcessRunnable onExit = this.process.getExitRunnable();
/*    */     
/* 46 */     if (onExit != null)
/* 47 */       onExit.onGameProcessEnded((GameProcess)this.process); 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\direct\DirectProcessInputMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */