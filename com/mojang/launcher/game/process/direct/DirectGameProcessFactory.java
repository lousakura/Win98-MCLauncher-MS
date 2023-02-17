/*    */ package com.mojang.launcher.game.process.direct;
/*    */ 
/*    */ import com.mojang.launcher.game.process.GameProcess;
/*    */ import com.mojang.launcher.game.process.GameProcessBuilder;
/*    */ import com.mojang.launcher.game.process.GameProcessFactory;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DirectGameProcessFactory
/*    */   implements GameProcessFactory
/*    */ {
/*    */   public GameProcess startGame(GameProcessBuilder builder) throws IOException {
/* 13 */     List<String> full = builder.getFullCommands();
/* 14 */     return (GameProcess)new DirectGameProcess(full, (new ProcessBuilder(full)).directory(builder.getDirectory()).redirectErrorStream(true).start(), builder.getSysOutFilter(), builder.getLogProcessor());
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\direct\DirectGameProcessFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */