/*    */ package com.mojang.launcher.updater;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.CancellationException;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class ExceptionalThreadPoolExecutor extends ThreadPoolExecutor {
/* 10 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   public ExceptionalThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit) {
/* 13 */     super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new LinkedBlockingQueue<Runnable>(), (new ThreadFactoryBuilder()).setDaemon(true).build());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void afterExecute(Runnable r, Throwable t) {
/* 18 */     super.afterExecute(r, t);
/*    */     
/* 20 */     if (t == null && r instanceof Future) {
/*    */       try {
/* 22 */         Future<?> future = (Future)r;
/* 23 */         if (future.isDone()) {
/* 24 */           future.get();
/*    */         }
/* 26 */       } catch (CancellationException ce) {
/* 27 */         t = ce;
/* 28 */       } catch (ExecutionException ee) {
/* 29 */         t = ee.getCause();
/* 30 */       } catch (InterruptedException ie) {
/* 31 */         Thread.currentThread().interrupt();
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> RunnableFuture<T> newTaskFor(Runnable runnable, T value) {
/* 38 */     return new ExceptionalFutureTask<T>(runnable, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
/* 43 */     return new ExceptionalFutureTask<T>(callable);
/*    */   }
/*    */   
/*    */   public class ExceptionalFutureTask<T> extends FutureTask<T> {
/*    */     public ExceptionalFutureTask(Callable<T> callable) {
/* 48 */       super(callable);
/*    */     }
/*    */     
/*    */     public ExceptionalFutureTask(Runnable runnable, T result) {
/* 52 */       super(runnable, result);
/*    */     }
/*    */ 
/*    */     
/*    */     protected void done() {
/*    */       try {
/* 58 */         get();
/* 59 */       } catch (Throwable t) {
/* 60 */         ExceptionalThreadPoolExecutor.LOGGER.error("Unhandled exception in executor " + this, t);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\ExceptionalThreadPoolExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */