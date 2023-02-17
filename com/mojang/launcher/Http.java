/*    */ package com.mojang.launcher;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.UnsupportedEncodingException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.net.URLEncoder;
/*    */ import java.util.Map;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class Http
/*    */ {
/* 17 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */ 
/*    */ 
/*    */   
/*    */   public static String buildQuery(Map<String, Object> query) {
/* 22 */     StringBuilder builder = new StringBuilder();
/*    */     
/* 24 */     for (Map.Entry<String, Object> entry : query.entrySet()) {
/* 25 */       if (builder.length() > 0) {
/* 26 */         builder.append('&');
/*    */       }
/*    */       
/*    */       try {
/* 30 */         builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/* 31 */       } catch (UnsupportedEncodingException e) {
/* 32 */         LOGGER.error("Unexpected exception building query", e);
/*    */       } 
/*    */       
/* 35 */       if (entry.getValue() != null) {
/* 36 */         builder.append('=');
/*    */         try {
/* 38 */           builder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/* 39 */         } catch (UnsupportedEncodingException e) {
/* 40 */           LOGGER.error("Unexpected exception building query", e);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     return builder.toString();
/*    */   }
/*    */   
/*    */   public static String performGet(URL url, Proxy proxy) throws IOException {
/* 49 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
/* 50 */     connection.setConnectTimeout(15000);
/* 51 */     connection.setReadTimeout(60000);
/* 52 */     connection.setRequestMethod("GET");
/*    */     
/* 54 */     InputStream inputStream = connection.getInputStream();
/*    */     try {
/* 56 */       return IOUtils.toString(inputStream);
/*    */     } finally {
/* 58 */       IOUtils.closeQuietly(inputStream);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\Http.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */