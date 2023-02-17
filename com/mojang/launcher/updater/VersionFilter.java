/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ import com.google.common.collect.Iterables;
/*    */ import com.google.common.collect.Sets;
/*    */ import com.mojang.launcher.versions.ReleaseType;
/*    */ import com.mojang.launcher.versions.ReleaseTypeFactory;
/*    */ import java.util.Collections;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class VersionFilter<T extends ReleaseType>
/*    */ {
/* 12 */   private final Set<T> types = Sets.newHashSet();
/* 13 */   private int maxCount = 5;
/*    */   
/*    */   public VersionFilter(ReleaseTypeFactory<T> factory) {
/* 16 */     Iterables.addAll(this.types, (Iterable)factory);
/*    */   }
/*    */   
/*    */   public Set<T> getTypes() {
/* 20 */     return this.types;
/*    */   }
/*    */   
/*    */   public VersionFilter<T> onlyForTypes(T... types) {
/* 24 */     this.types.clear();
/* 25 */     includeTypes(types);
/* 26 */     return this;
/*    */   }
/*    */   
/*    */   public VersionFilter<T> includeTypes(T... types) {
/* 30 */     if (types != null) Collections.addAll(this.types, types); 
/* 31 */     return this;
/*    */   }
/*    */   
/*    */   public VersionFilter<T> excludeTypes(T... types) {
/* 35 */     if (types != null) {
/* 36 */       for (T type : types) {
/* 37 */         this.types.remove(type);
/*    */       }
/*    */     }
/* 40 */     return this;
/*    */   }
/*    */   
/*    */   public int getMaxCount() {
/* 44 */     return this.maxCount;
/*    */   }
/*    */   
/*    */   public VersionFilter<T> setMaxCount(int maxCount) {
/* 48 */     this.maxCount = maxCount;
/* 49 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\VersionFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */