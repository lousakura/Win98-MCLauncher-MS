/*     */ package com.mojang.launcher.versions;
/*     */ 
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class CompatibilityRule
/*     */ {
/*     */   public enum Action {
/*  10 */     ALLOW,
/*  11 */     DISALLOW;
/*     */   }
/*     */   
/*     */   public class OSRestriction
/*     */   {
/*     */     private OperatingSystem name;
/*     */     private String version;
/*     */     private String arch;
/*     */     
/*     */     public OSRestriction() {}
/*     */     
/*     */     public OperatingSystem getName() {
/*  23 */       return this.name;
/*     */     }
/*     */     
/*     */     public String getVersion() {
/*  27 */       return this.version;
/*     */     }
/*     */     
/*     */     public String getArch() {
/*  31 */       return this.arch;
/*     */     }
/*     */     
/*     */     public OSRestriction(OSRestriction osRestriction) {
/*  35 */       this.name = osRestriction.name;
/*  36 */       this.version = osRestriction.version;
/*  37 */       this.arch = osRestriction.arch;
/*     */     }
/*     */     
/*     */     public boolean isCurrentOperatingSystem() {
/*  41 */       if (this.name != null && this.name != OperatingSystem.getCurrentPlatform()) return false;
/*     */       
/*  43 */       if (this.version != null) {
/*     */         try {
/*  45 */           Pattern pattern = Pattern.compile(this.version);
/*  46 */           Matcher matcher = pattern.matcher(System.getProperty("os.version"));
/*  47 */           if (!matcher.matches()) return false; 
/*  48 */         } catch (Throwable throwable) {}
/*     */       }
/*     */ 
/*     */       
/*  52 */       if (this.arch != null) {
/*     */         try {
/*  54 */           Pattern pattern = Pattern.compile(this.arch);
/*  55 */           Matcher matcher = pattern.matcher(System.getProperty("os.arch"));
/*  56 */           if (!matcher.matches()) return false; 
/*  57 */         } catch (Throwable throwable) {}
/*     */       }
/*     */ 
/*     */       
/*  61 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  66 */       return "OSRestriction{name=" + this.name + ", version='" + this.version + '\'' + ", arch='" + this.arch + '\'' + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private Action action = Action.ALLOW;
/*     */   
/*     */   private OSRestriction os;
/*     */   
/*     */   public CompatibilityRule() {}
/*     */   
/*     */   public CompatibilityRule(CompatibilityRule compatibilityRule) {
/*  81 */     this.action = compatibilityRule.action;
/*     */     
/*  83 */     if (compatibilityRule.os != null) {
/*  84 */       this.os = new OSRestriction(compatibilityRule.os);
/*     */     }
/*     */   }
/*     */   
/*     */   public Action getAppliedAction() {
/*  89 */     if (this.os != null && !this.os.isCurrentOperatingSystem()) return null;
/*     */     
/*  91 */     return this.action;
/*     */   }
/*     */   
/*     */   public Action getAction() {
/*  95 */     return this.action;
/*     */   }
/*     */   
/*     */   public OSRestriction getOs() {
/*  99 */     return this.os;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 104 */     return "Rule{action=" + this.action + ", os=" + this.os + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\CompatibilityRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */