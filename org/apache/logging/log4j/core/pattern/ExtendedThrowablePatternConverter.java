/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*    */ import org.apache.logging.log4j.core.helpers.Constants;
/*    */ import org.apache.logging.log4j.core.impl.Log4jLogEvent;
/*    */ import org.apache.logging.log4j.core.impl.ThrowableProxy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Plugin(name = "ExtendedThrowablePatternConverter", category = "Converter")
/*    */ @ConverterKeys({"xEx", "xThrowable", "xException"})
/*    */ public final class ExtendedThrowablePatternConverter
/*    */   extends ThrowablePatternConverter
/*    */ {
/*    */   private ExtendedThrowablePatternConverter(String[] options) {
/* 43 */     super("ExtendedThrowable", "throwable", options);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ExtendedThrowablePatternConverter newInstance(String[] options) {
/* 54 */     return new ExtendedThrowablePatternConverter(options);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 62 */     ThrowableProxy proxy = null;
/* 63 */     if (event instanceof Log4jLogEvent) {
/* 64 */       proxy = ((Log4jLogEvent)event).getThrownProxy();
/*    */     }
/* 66 */     Throwable throwable = event.getThrown();
/* 67 */     if (throwable != null && this.options.anyLines()) {
/* 68 */       if (proxy == null) {
/* 69 */         super.format(event, toAppendTo);
/*    */         return;
/*    */       } 
/* 72 */       String trace = proxy.getExtendedStackTrace(this.options.getPackages());
/* 73 */       int len = toAppendTo.length();
/* 74 */       if (len > 0 && !Character.isWhitespace(toAppendTo.charAt(len - 1))) {
/* 75 */         toAppendTo.append(" ");
/*    */       }
/* 77 */       if (!this.options.allLines() || !Constants.LINE_SEP.equals(this.options.getSeparator())) {
/* 78 */         StringBuilder sb = new StringBuilder();
/* 79 */         String[] array = trace.split(Constants.LINE_SEP);
/* 80 */         int limit = this.options.minLines(array.length) - 1;
/* 81 */         for (int i = 0; i <= limit; i++) {
/* 82 */           sb.append(array[i]);
/* 83 */           if (i < limit) {
/* 84 */             sb.append(this.options.getSeparator());
/*    */           }
/*    */         } 
/* 87 */         toAppendTo.append(sb.toString());
/*    */       } else {
/*    */         
/* 90 */         toAppendTo.append(trace);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\pattern\ExtendedThrowablePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */