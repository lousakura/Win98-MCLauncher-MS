/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.filter.ThresholdFilter;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.layout.HTMLLayout;
/*     */ import org.apache.logging.log4j.core.net.SMTPManager;
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
/*     */ @Plugin(name = "SMTP", category = "Core", elementType = "appender", printObject = true)
/*     */ public final class SMTPAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   private static final int DEFAULT_BUFFER_SIZE = 512;
/*     */   protected final SMTPManager manager;
/*     */   
/*     */   private SMTPAppender(String name, Filter filter, Layout<? extends Serializable> layout, SMTPManager manager, boolean ignoreExceptions) {
/*  62 */     super(name, filter, layout, ignoreExceptions);
/*  63 */     this.manager = manager;
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
/*     */   @PluginFactory
/*     */   public static SMTPAppender createAppender(@PluginAttribute("name") String name, @PluginAttribute("to") String to, @PluginAttribute("cc") String cc, @PluginAttribute("bcc") String bcc, @PluginAttribute("from") String from, @PluginAttribute("replyTo") String replyTo, @PluginAttribute("subject") String subject, @PluginAttribute("smtpProtocol") String smtpProtocol, @PluginAttribute("smtpHost") String smtpHost, @PluginAttribute("smtpPort") String smtpPortStr, @PluginAttribute("smtpUsername") String smtpUsername, @PluginAttribute("smtpPassword") String smtpPassword, @PluginAttribute("smtpDebug") String smtpDebug, @PluginAttribute("bufferSize") String bufferSizeStr, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") Filter filter, @PluginAttribute("ignoreExceptions") String ignore) {
/*     */     HTMLLayout hTMLLayout;
/*     */     ThresholdFilter thresholdFilter;
/* 124 */     if (name == null) {
/* 125 */       LOGGER.error("No name provided for SMTPAppender");
/* 126 */       return null;
/*     */     } 
/*     */     
/* 129 */     boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
/* 130 */     int smtpPort = AbstractAppender.parseInt(smtpPortStr, 0);
/* 131 */     boolean isSmtpDebug = Boolean.parseBoolean(smtpDebug);
/* 132 */     int bufferSize = (bufferSizeStr == null) ? 512 : Integer.parseInt(bufferSizeStr);
/*     */     
/* 134 */     if (layout == null) {
/* 135 */       hTMLLayout = HTMLLayout.createLayout(null, null, null, null, null, null);
/*     */     }
/* 137 */     if (filter == null) {
/* 138 */       thresholdFilter = ThresholdFilter.createFilter(null, null, null);
/*     */     }
/*     */     
/* 141 */     SMTPManager manager = SMTPManager.getSMTPManager(to, cc, bcc, from, replyTo, subject, smtpProtocol, smtpHost, smtpPort, smtpUsername, smtpPassword, isSmtpDebug, thresholdFilter.toString(), bufferSize);
/*     */     
/* 143 */     if (manager == null) {
/* 144 */       return null;
/*     */     }
/*     */     
/* 147 */     return new SMTPAppender(name, (Filter)thresholdFilter, (Layout<? extends Serializable>)hTMLLayout, manager, ignoreExceptions);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFiltered(LogEvent event) {
/* 157 */     boolean filtered = super.isFiltered(event);
/* 158 */     if (filtered) {
/* 159 */       this.manager.add(event);
/*     */     }
/* 161 */     return filtered;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(LogEvent event) {
/* 172 */     this.manager.sendEvents(getLayout(), event);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\appender\SMTPAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */