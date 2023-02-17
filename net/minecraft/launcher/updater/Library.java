/*     */ package net.minecraft.launcher.updater;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.updater.download.ChecksummedDownloadable;
/*     */ import com.mojang.launcher.updater.download.Downloadable;
/*     */ import com.mojang.launcher.versions.ExtractRules;
/*     */ import java.io.File;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.launcher.CompatibilityRule;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.text.StrSubstitutor;
/*     */ 
/*     */ public class Library {
/*  19 */   private static final StrSubstitutor SUBSTITUTOR = new StrSubstitutor(new HashMap<String, String>()
/*     */       {
/*     */       
/*     */       });
/*     */   private String name;
/*     */   private List<CompatibilityRule> rules;
/*     */   private Map<OperatingSystem, String> natives;
/*     */   private ExtractRules extract;
/*     */   private String url;
/*     */   private LibraryDownloadInfo downloads;
/*     */   
/*     */   public Library() {}
/*     */   
/*     */   public Library(String name) {
/*  33 */     if (name == null || name.length() == 0) throw new IllegalArgumentException("Library name cannot be null or empty"); 
/*  34 */     this.name = name;
/*     */   }
/*     */   
/*     */   public Library(Library library) {
/*  38 */     this.name = library.name;
/*  39 */     this.url = library.url;
/*     */     
/*  41 */     if (library.extract != null) {
/*  42 */       this.extract = new ExtractRules(library.extract);
/*     */     }
/*     */     
/*  45 */     if (library.rules != null) {
/*  46 */       this.rules = new ArrayList<CompatibilityRule>();
/*  47 */       for (CompatibilityRule compatibilityRule : library.rules) {
/*  48 */         this.rules.add(new CompatibilityRule(compatibilityRule));
/*     */       }
/*     */     } 
/*     */     
/*  52 */     if (library.natives != null) {
/*  53 */       this.natives = new LinkedHashMap<OperatingSystem, String>();
/*  54 */       for (Map.Entry<OperatingSystem, String> entry : library.getNatives().entrySet()) {
/*  55 */         this.natives.put(entry.getKey(), entry.getValue());
/*     */       }
/*     */     } 
/*     */     
/*  59 */     if (library.downloads != null) {
/*  60 */       this.downloads = new LibraryDownloadInfo(library.downloads);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getName() {
/*  65 */     return this.name;
/*     */   }
/*     */   
/*     */   public Library addNative(OperatingSystem operatingSystem, String name) {
/*  69 */     if (operatingSystem == null || !operatingSystem.isSupported()) throw new IllegalArgumentException("Cannot add native for unsupported OS"); 
/*  70 */     if (name == null || name.length() == 0) throw new IllegalArgumentException("Cannot add native for null or empty name"); 
/*  71 */     if (this.natives == null) this.natives = new EnumMap<OperatingSystem, String>(OperatingSystem.class); 
/*  72 */     this.natives.put(operatingSystem, name);
/*  73 */     return this;
/*     */   }
/*     */   
/*     */   public List<CompatibilityRule> getCompatibilityRules() {
/*  77 */     return this.rules;
/*     */   }
/*     */   
/*     */   public boolean appliesToCurrentEnvironment(CompatibilityRule.FeatureMatcher featureMatcher) {
/*  81 */     if (this.rules == null) return true; 
/*  82 */     CompatibilityRule.Action lastAction = CompatibilityRule.Action.DISALLOW;
/*     */     
/*  84 */     for (CompatibilityRule compatibilityRule : this.rules) {
/*  85 */       CompatibilityRule.Action action = compatibilityRule.getAppliedAction(featureMatcher);
/*  86 */       if (action != null) lastAction = action;
/*     */     
/*     */     } 
/*  89 */     return (lastAction == CompatibilityRule.Action.ALLOW);
/*     */   }
/*     */   
/*     */   public Map<OperatingSystem, String> getNatives() {
/*  93 */     return this.natives;
/*     */   }
/*     */   
/*     */   public ExtractRules getExtractRules() {
/*  97 */     return this.extract;
/*     */   }
/*     */   
/*     */   public Library setExtractRules(ExtractRules rules) {
/* 101 */     this.extract = rules;
/* 102 */     return this;
/*     */   }
/*     */   
/*     */   public String getArtifactBaseDir() {
/* 106 */     if (this.name == null) throw new IllegalStateException("Cannot get artifact dir of empty/blank artifact"); 
/* 107 */     String[] parts = this.name.split(":", 3);
/* 108 */     return String.format("%s/%s/%s", new Object[] { parts[0].replaceAll("\\.", "/"), parts[1], parts[2] });
/*     */   }
/*     */   
/*     */   public String getArtifactPath() {
/* 112 */     return getArtifactPath(null);
/*     */   }
/*     */   
/*     */   public String getArtifactPath(String classifier) {
/* 116 */     if (this.name == null) throw new IllegalStateException("Cannot get artifact path of empty/blank artifact"); 
/* 117 */     return String.format("%s/%s", new Object[] { getArtifactBaseDir(), getArtifactFilename(classifier) });
/*     */   }
/*     */   
/*     */   public String getArtifactFilename(String classifier) {
/* 121 */     if (this.name == null) throw new IllegalStateException("Cannot get artifact filename of empty/blank artifact");
/*     */     
/* 123 */     String[] parts = this.name.split(":", 3);
/* 124 */     String result = String.format("%s-%s%s.jar", new Object[] { parts[1], parts[2], StringUtils.isEmpty(classifier) ? "" : ("-" + classifier) });
/*     */     
/* 126 */     return SUBSTITUTOR.replace(result);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 131 */     return "Library{name='" + this.name + '\'' + ", rules=" + this.rules + ", natives=" + this.natives + ", extract=" + this.extract + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Downloadable createDownload(Proxy proxy, String path, File local, boolean ignoreLocalFiles, String classifier) throws MalformedURLException {
/* 140 */     if (this.url != null) {
/* 141 */       URL url = new URL(this.url + path);
/*     */       
/* 143 */       return (Downloadable)new ChecksummedDownloadable(proxy, url, local, ignoreLocalFiles);
/* 144 */     }  if (this.downloads == null) {
/* 145 */       URL url = new URL("https://libraries.minecraft.net/" + path);
/* 146 */       return (Downloadable)new ChecksummedDownloadable(proxy, url, local, ignoreLocalFiles);
/*     */     } 
/* 148 */     AbstractDownloadInfo info = this.downloads.getDownloadInfo(SUBSTITUTOR.replace(classifier));
/* 149 */     if (info != null) {
/* 150 */       URL url = info.getUrl();
/* 151 */       if (url != null) {
/* 152 */         return new PreHashedDownloadable(proxy, url, local, ignoreLocalFiles, info.getSha1());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 157 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\Library.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */