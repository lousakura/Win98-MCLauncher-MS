/*     */ package org.apache.commons.lang3.concurrent;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.concurrent.ExecutionException;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentUtils
/*     */ {
/*     */   public static ConcurrentException extractCause(ExecutionException ex) {
/*  61 */     if (ex == null || ex.getCause() == null) {
/*  62 */       return null;
/*     */     }
/*     */     
/*  65 */     throwCause(ex);
/*  66 */     return new ConcurrentException(ex.getMessage(), ex.getCause());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ConcurrentRuntimeException extractCauseUnchecked(ExecutionException ex) {
/*  83 */     if (ex == null || ex.getCause() == null) {
/*  84 */       return null;
/*     */     }
/*     */     
/*  87 */     throwCause(ex);
/*  88 */     return new ConcurrentRuntimeException(ex.getMessage(), ex.getCause());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleCause(ExecutionException ex) throws ConcurrentException {
/* 106 */     ConcurrentException cex = extractCause(ex);
/*     */     
/* 108 */     if (cex != null) {
/* 109 */       throw cex;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void handleCauseUnchecked(ExecutionException ex) {
/* 127 */     ConcurrentRuntimeException crex = extractCauseUnchecked(ex);
/*     */     
/* 129 */     if (crex != null) {
/* 130 */       throw crex;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Throwable checkedException(Throwable ex) {
/* 144 */     if (ex != null && !(ex instanceof RuntimeException) && !(ex instanceof Error))
/*     */     {
/* 146 */       return ex;
/*     */     }
/* 148 */     throw new IllegalArgumentException("Not a checked exception: " + ex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void throwCause(ExecutionException ex) {
/* 159 */     if (ex.getCause() instanceof RuntimeException) {
/* 160 */       throw (RuntimeException)ex.getCause();
/*     */     }
/*     */     
/* 163 */     if (ex.getCause() instanceof Error) {
/* 164 */       throw (Error)ex.getCause();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T initialize(ConcurrentInitializer<T> initializer) throws ConcurrentException {
/* 184 */     return (initializer != null) ? initializer.get() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T initializeUnchecked(ConcurrentInitializer<T> initializer) {
/*     */     try {
/* 202 */       return initialize(initializer);
/* 203 */     } catch (ConcurrentException cex) {
/* 204 */       throw new ConcurrentRuntimeException(cex.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> V putIfAbsent(ConcurrentMap<K, V> map, K key, V value) {
/* 244 */     if (map == null) {
/* 245 */       return null;
/*     */     }
/*     */     
/* 248 */     V result = map.putIfAbsent(key, value);
/* 249 */     return (result != null) ? result : value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> V createIfAbsent(ConcurrentMap<K, V> map, K key, ConcurrentInitializer<V> init) throws ConcurrentException {
/* 274 */     if (map == null || init == null) {
/* 275 */       return null;
/*     */     }
/*     */     
/* 278 */     V value = map.get(key);
/* 279 */     if (value == null) {
/* 280 */       return putIfAbsent(map, key, init.get());
/*     */     }
/* 282 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <K, V> V createIfAbsentUnchecked(ConcurrentMap<K, V> map, K key, ConcurrentInitializer<V> init) {
/*     */     try {
/* 303 */       return createIfAbsent(map, key, init);
/* 304 */     } catch (ConcurrentException cex) {
/* 305 */       throw new ConcurrentRuntimeException(cex.getCause());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> Future<T> constantFuture(T value) {
/* 326 */     return new ConstantFuture<T>(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static final class ConstantFuture<T>
/*     */     implements Future<T>
/*     */   {
/*     */     private final T value;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     ConstantFuture(T value) {
/* 344 */       this.value = value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isDone() {
/* 354 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get() {
/* 362 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public T get(long timeout, TimeUnit unit) {
/* 371 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isCancelled() {
/* 380 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean cancel(boolean mayInterruptIfRunning) {
/* 389 */       return false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\concurrent\ConcurrentUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */