/*    */ package com.mojang.launcher.versions;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ExtractRules {
/*  8 */   private List<String> exclude = new ArrayList<String>();
/*    */ 
/*    */   
/*    */   public ExtractRules() {}
/*    */   
/*    */   public ExtractRules(String... exclude) {
/* 14 */     if (exclude != null) Collections.addAll(this.exclude, exclude); 
/*    */   }
/*    */   
/*    */   public ExtractRules(ExtractRules rules) {
/* 18 */     for (String exclude : rules.exclude) {
/* 19 */       this.exclude.add(exclude);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<String> getExcludes() {
/* 24 */     return this.exclude;
/*    */   }
/*    */   
/*    */   public boolean shouldExtract(String path) {
/* 28 */     if (this.exclude != null) {
/* 29 */       for (String rule : this.exclude) {
/* 30 */         if (path.startsWith(rule)) return false;
/*    */       
/*    */       } 
/*    */     }
/* 34 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\ExtractRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */