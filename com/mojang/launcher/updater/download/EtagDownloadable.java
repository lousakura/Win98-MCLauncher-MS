/*    */ package com.mojang.launcher.updater.download;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class EtagDownloadable extends Downloadable {
/*    */   public EtagDownloadable(Proxy proxy, URL remoteFile, File localFile, boolean forceDownload) {
/* 10 */     super(proxy, remoteFile, localFile, forceDownload);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String download() throws IOException {
/* 16 */     this.numAttempts++;
/* 17 */     ensureFileWritable(getTarget());
/*    */     
/*    */     try {
/* 20 */       HttpURLConnection connection = makeConnection(getUrl());
/* 21 */       int status = connection.getResponseCode();
/*    */       
/* 23 */       if (status == 304)
/* 24 */         return "Used own copy as it matched etag"; 
/* 25 */       if (status / 100 == 2) {
/* 26 */         updateExpectedSize(connection);
/*    */         
/* 28 */         InputStream inputStream = new MonitoringInputStream(connection.getInputStream(), getMonitor());
/* 29 */         FileOutputStream outputStream = new FileOutputStream(getTarget());
/* 30 */         String md5 = copyAndDigest(inputStream, outputStream, "MD5", 32);
/* 31 */         String etag = getEtag(connection.getHeaderField("ETag"));
/*    */         
/* 33 */         if (etag.contains("-"))
/* 34 */           return "Didn't have etag so assuming our copy is good"; 
/* 35 */         if (etag.equalsIgnoreCase(md5)) {
/* 36 */           return "Downloaded successfully and etag matched";
/*    */         }
/* 38 */         throw new RuntimeException(String.format("E-tag did not match downloaded MD5 (ETag was %s, downloaded %s)", new Object[] { etag, md5 }));
/*    */       } 
/* 40 */       if (getTarget().isFile()) {
/* 41 */         return "Couldn't connect to server (responded with " + status + ") but have local file, assuming it's good";
/*    */       }
/* 43 */       throw new RuntimeException("Server responded with " + status);
/*    */     }
/* 45 */     catch (IOException e) {
/* 46 */       if (getTarget().isFile()) {
/* 47 */         return "Couldn't connect to server (" + e.getClass().getSimpleName() + ": '" + e.getMessage() + "') but have local file, assuming it's good";
/*    */       }
/* 49 */       throw e;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected HttpURLConnection makeConnection(URL url) throws IOException {
/* 56 */     HttpURLConnection connection = super.makeConnection(url);
/*    */     
/* 58 */     if (!shouldIgnoreLocal() && getTarget().isFile()) {
/* 59 */       connection.setRequestProperty("If-None-Match", getDigest(getTarget(), "MD5", 32));
/*    */     }
/*    */     
/* 62 */     return connection;
/*    */   }
/*    */   
/*    */   public static String getEtag(String etag) {
/* 66 */     if (etag == null) {
/* 67 */       etag = "-";
/* 68 */     } else if (etag.startsWith("\"") && etag.endsWith("\"")) {
/*    */       
/* 70 */       etag = etag.substring(1, etag.length() - 1);
/*    */     } 
/*    */     
/* 73 */     return etag;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\EtagDownloadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */