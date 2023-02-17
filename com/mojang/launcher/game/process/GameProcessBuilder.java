/*    */ package com.mojang.launcher.game.process;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.mojang.launcher.OperatingSystem;
/*    */ import com.mojang.launcher.events.GameOutputLogProcessor;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ public class GameProcessBuilder
/*    */ {
/*    */   private final String processPath;
/* 17 */   private final List<String> arguments = Lists.newArrayList();
/* 18 */   private Predicate<String> sysOutFilter = Predicates.alwaysTrue();
/* 19 */   private GameOutputLogProcessor logProcessor = new GameOutputLogProcessor() {
/*    */       public void onGameOutput(GameProcess process, String logLine) {}
/*    */     };
/*    */   
/*    */   private File directory;
/*    */   
/*    */   public GameProcessBuilder(String processPath) {
/* 26 */     if (processPath == null) processPath = OperatingSystem.getCurrentPlatform().getJavaDir(); 
/* 27 */     this.processPath = processPath;
/*    */   }
/*    */   
/*    */   public List<String> getFullCommands() {
/* 31 */     List<String> result = new ArrayList<String>(this.arguments);
/* 32 */     result.add(0, getProcessPath());
/* 33 */     return result;
/*    */   }
/*    */   
/*    */   public GameProcessBuilder withArguments(String... commands) {
/* 37 */     this.arguments.addAll(Arrays.asList(commands));
/* 38 */     return this;
/*    */   }
/*    */   
/*    */   public List<String> getArguments() {
/* 42 */     return this.arguments;
/*    */   }
/*    */   
/*    */   public GameProcessBuilder directory(File directory) {
/* 46 */     this.directory = directory;
/* 47 */     return this;
/*    */   }
/*    */   
/*    */   public File getDirectory() {
/* 51 */     return this.directory;
/*    */   }
/*    */   
/*    */   public GameProcessBuilder withSysOutFilter(Predicate<String> predicate) {
/* 55 */     this.sysOutFilter = predicate;
/* 56 */     return this;
/*    */   }
/*    */   
/*    */   public GameProcessBuilder withLogProcessor(GameOutputLogProcessor logProcessor) {
/* 60 */     this.logProcessor = logProcessor;
/* 61 */     return this;
/*    */   }
/*    */   
/*    */   public Predicate<String> getSysOutFilter() {
/* 65 */     return this.sysOutFilter;
/*    */   }
/*    */   
/*    */   protected String getProcessPath() {
/* 69 */     return this.processPath;
/*    */   }
/*    */   
/*    */   public GameOutputLogProcessor getLogProcessor() {
/* 73 */     return this.logProcessor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 84 */     return Objects.toStringHelper(this).add("processPath", this.processPath).add("arguments", this.arguments).add("sysOutFilter", this.sysOutFilter).add("directory", this.directory).add("logProcessor", this.logProcessor).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\game\process\GameProcessBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */