/*     */ package net.minecraft.launcher;
/*     */ 
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.util.Map;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class CompatibilityRule
/*     */ {
/*     */   public enum Action
/*     */   {
/*  12 */     ALLOW,
/*  13 */     DISALLOW;
/*     */   }
/*     */   
/*     */   public static interface FeatureMatcher {
/*     */     boolean hasFeature(String param1String, Object param1Object);
/*     */   }
/*     */   
/*     */   public class OSRestriction {
/*     */     private OperatingSystem name;
/*     */     private String version;
/*     */     
/*     */     public OperatingSystem getName() {
/*  25 */       return this.name;
/*     */     } private String arch;
/*     */     public OSRestriction() {}
/*     */     public String getVersion() {
/*  29 */       return this.version;
/*     */     }
/*     */     
/*     */     public String getArch() {
/*  33 */       return this.arch;
/*     */     }
/*     */     
/*     */     public OSRestriction(OSRestriction osRestriction) {
/*  37 */       this.name = osRestriction.name;
/*  38 */       this.version = osRestriction.version;
/*  39 */       this.arch = osRestriction.arch;
/*     */     }
/*     */     
/*     */     public boolean isCurrentOperatingSystem() {
/*  43 */       if (this.name != null && this.name != OperatingSystem.getCurrentPlatform()) return false;
/*     */       
/*  45 */       if (this.version != null) {
/*     */         try {
/*  47 */           Pattern pattern = Pattern.compile(this.version);
/*  48 */           Matcher matcher = pattern.matcher(System.getProperty("os.version"));
/*  49 */           if (!matcher.matches()) return false; 
/*  50 */         } catch (Throwable throwable) {}
/*     */       }
/*     */ 
/*     */       
/*  54 */       if (this.arch != null) {
/*     */         try {
/*  56 */           Pattern pattern = Pattern.compile(this.arch);
/*  57 */           Matcher matcher = pattern.matcher(System.getProperty("os.arch"));
/*  58 */           if (!matcher.matches()) return false; 
/*  59 */         } catch (Throwable throwable) {}
/*     */       }
/*     */ 
/*     */       
/*  63 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*  68 */       return "OSRestriction{name=" + this.name + ", version='" + this.version + '\'' + ", arch='" + this.arch + '\'' + '}';
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  76 */   private Action action = Action.ALLOW;
/*     */   
/*     */   private OSRestriction os;
/*     */   private Map<String, Object> features;
/*     */   
/*     */   public CompatibilityRule() {}
/*     */   
/*     */   public CompatibilityRule(CompatibilityRule compatibilityRule) {
/*  84 */     this.action = compatibilityRule.action;
/*     */     
/*  86 */     if (compatibilityRule.os != null) {
/*  87 */       this.os = new OSRestriction(compatibilityRule.os);
/*     */     }
/*     */     
/*  90 */     if (compatibilityRule.features != null) {
/*  91 */       this.features = compatibilityRule.features;
/*     */     }
/*     */   }
/*     */   
/*     */   public Action getAppliedAction(FeatureMatcher featureMatcher) {
/*  96 */     if (this.os != null && !this.os.isCurrentOperatingSystem()) return null; 
/*  97 */     if (this.features != null) {
/*  98 */       if (featureMatcher == null) return null; 
/*  99 */       for (Map.Entry<String, Object> feature : this.features.entrySet()) {
/* 100 */         if (!featureMatcher.hasFeature(feature.getKey(), feature.getValue())) {
/* 101 */           return null;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 106 */     return this.action;
/*     */   }
/*     */   
/*     */   public Action getAction() {
/* 110 */     return this.action;
/*     */   }
/*     */   
/*     */   public OSRestriction getOs() {
/* 114 */     return this.os;
/*     */   }
/*     */   
/*     */   public Map<String, Object> getFeatures() {
/* 118 */     return this.features;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return "Rule{action=" + this.action + ", os=" + this.os + ", features=" + this.features + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\CompatibilityRule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */