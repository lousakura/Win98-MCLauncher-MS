/*    */ package org.apache.logging.log4j.core.pattern;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import java.util.TreeSet;
/*    */ import org.apache.logging.log4j.core.LogEvent;
/*    */ import org.apache.logging.log4j.core.config.plugins.Plugin;
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
/*    */ 
/*    */ 
/*    */ @Plugin(name = "MDCPatternConverter", category = "Converter")
/*    */ @ConverterKeys({"X", "mdc", "MDC"})
/*    */ public final class MDCPatternConverter
/*    */   extends LogEventPatternConverter
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   private MDCPatternConverter(String[] options) {
/* 47 */     super((options != null && options.length > 0) ? ("MDC{" + options[0] + "}") : "MDC", "mdc");
/* 48 */     this.key = (options != null && options.length > 0) ? options[0] : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static MDCPatternConverter newInstance(String[] options) {
/* 58 */     return new MDCPatternConverter(options);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void format(LogEvent event, StringBuilder toAppendTo) {
/* 66 */     Map<String, String> contextMap = event.getContextMap();
/*    */ 
/*    */     
/* 69 */     if (this.key == null) {
/*    */ 
/*    */       
/* 72 */       if (contextMap == null || contextMap.size() == 0) {
/* 73 */         toAppendTo.append("{}");
/*    */         return;
/*    */       } 
/* 76 */       StringBuilder sb = new StringBuilder("{");
/* 77 */       Set<String> keys = new TreeSet<String>(contextMap.keySet());
/* 78 */       for (String key : keys) {
/* 79 */         if (sb.length() > 1) {
/* 80 */           sb.append(", ");
/*    */         }
/* 82 */         sb.append(key).append("=").append(contextMap.get(key));
/*    */       } 
/*    */       
/* 85 */       sb.append("}");
/* 86 */       toAppendTo.append(sb);
/* 87 */     } else if (contextMap != null) {
/*    */       
/* 89 */       Object val = contextMap.get(this.key);
/*    */       
/* 91 */       if (val != null)
/* 92 */         toAppendTo.append(val); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\pattern\MDCPatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */