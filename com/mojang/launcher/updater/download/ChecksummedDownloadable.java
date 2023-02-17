/*    */ package com.mojang.launcher.updater.download;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import org.apache.commons.io.Charsets;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class ChecksummedDownloadable
/*    */   extends Downloadable
/*    */ {
/*    */   private String localHash;
/*    */   private String expectedHash;
/*    */   
/*    */   public ChecksummedDownloadable(Proxy proxy, URL remoteFile, File localFile, boolean forceDownload) {
/* 20 */     super(proxy, remoteFile, localFile, forceDownload);
/*    */   }
/*    */ 
/*    */   
/*    */   public String download() throws IOException {
/* 25 */     this.numAttempts++;
/* 26 */     ensureFileWritable(getTarget());
/*    */     
/* 28 */     File target = getTarget();
/*    */     
/* 30 */     if (this.localHash == null && target.isFile()) {
/* 31 */       this.localHash = getDigest(target, "SHA-1", 40);
/*    */     }
/*    */     
/* 34 */     if (this.expectedHash == null) {
/*    */       try {
/* 36 */         HttpURLConnection connection = makeConnection(new URL(getUrl().toString() + ".sha1"));
/* 37 */         int status = connection.getResponseCode();
/*    */         
/* 39 */         if (status / 100 == 2) {
/* 40 */           InputStream inputStream = connection.getInputStream();
/*    */           
/*    */           try {
/* 43 */             this.expectedHash = IOUtils.toString(inputStream, Charsets.UTF_8).trim();
/* 44 */           } catch (IOException e) {
/* 45 */             this.expectedHash = "";
/*    */           } finally {
/* 47 */             IOUtils.closeQuietly(inputStream);
/*    */           } 
/*    */         } else {
/* 50 */           this.expectedHash = "";
/*    */         } 
/* 52 */       } catch (IOException e) {
/* 53 */         this.expectedHash = "";
/*    */       } 
/*    */     }
/*    */     
/* 57 */     if (this.expectedHash.length() == 0 && target.isFile())
/* 58 */       return "Couldn't find a checksum so assuming our copy is good"; 
/* 59 */     if (this.expectedHash.equalsIgnoreCase(this.localHash)) {
/* 60 */       return "Remote checksum matches local file";
/*    */     }
/*    */     
/*    */     try {
/* 64 */       HttpURLConnection connection = makeConnection(getUrl());
/* 65 */       int status = connection.getResponseCode();
/*    */       
/* 67 */       if (status / 100 == 2) {
/* 68 */         updateExpectedSize(connection);
/*    */         
/* 70 */         InputStream inputStream = new MonitoringInputStream(connection.getInputStream(), getMonitor());
/* 71 */         FileOutputStream outputStream = new FileOutputStream(getTarget());
/* 72 */         String digest = copyAndDigest(inputStream, outputStream, "SHA", 40);
/*    */         
/* 74 */         if (this.expectedHash.length() == 0)
/* 75 */           return "Didn't have checksum so assuming the downloaded file is good"; 
/* 76 */         if (this.expectedHash.equalsIgnoreCase(digest)) {
/* 77 */           return "Downloaded successfully and checksum matched";
/*    */         }
/* 79 */         throw new RuntimeException(String.format("Checksum did not match downloaded file (Checksum was %s, downloaded %s)", new Object[] { this.expectedHash, digest }));
/*    */       } 
/* 81 */       if (getTarget().isFile()) {
/* 82 */         return "Couldn't connect to server (responded with " + status + ") but have local file, assuming it's good";
/*    */       }
/* 84 */       throw new RuntimeException("Server responded with " + status);
/*    */     }
/* 86 */     catch (IOException e) {
/* 87 */       if (getTarget().isFile() && (this.expectedHash == null || this.expectedHash.length() == 0)) {
/* 88 */         return "Couldn't connect to server (" + e.getClass().getSimpleName() + ": '" + e.getMessage() + "') but have local file, assuming it's good";
/*    */       }
/* 90 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\ChecksummedDownloadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */