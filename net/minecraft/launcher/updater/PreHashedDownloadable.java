/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import com.mojang.launcher.updater.download.Downloadable;
/*    */ import com.mojang.launcher.updater.download.MonitoringInputStream;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import org.apache.commons.io.FileUtils;
/*    */ 
/*    */ public class PreHashedDownloadable
/*    */   extends Downloadable {
/*    */   private final String expectedHash;
/*    */   
/*    */   public PreHashedDownloadable(Proxy proxy, URL remoteFile, File localFile, boolean forceDownload, String expectedHash) {
/* 19 */     super(proxy, remoteFile, localFile, forceDownload);
/* 20 */     this.expectedHash = expectedHash;
/*    */   }
/*    */ 
/*    */   
/*    */   public String download() throws IOException {
/* 25 */     this.numAttempts++;
/* 26 */     ensureFileWritable(getTarget());
/* 27 */     File target = getTarget();
/* 28 */     String localHash = null;
/*    */     
/* 30 */     if (target.isFile()) {
/* 31 */       localHash = getDigest(target, "SHA-1", 40);
/*    */       
/* 33 */       if (this.expectedHash.equalsIgnoreCase(localHash)) {
/* 34 */         return "Local file matches hash, using that";
/*    */       }
/* 36 */       FileUtils.deleteQuietly(target);
/*    */     } 
/*    */ 
/*    */     
/*    */     try {
/* 41 */       HttpURLConnection connection = makeConnection(getUrl());
/* 42 */       int status = connection.getResponseCode();
/*    */       
/* 44 */       if (status / 100 == 2) {
/* 45 */         updateExpectedSize(connection);
/*    */         
/* 47 */         MonitoringInputStream monitoringInputStream = new MonitoringInputStream(connection.getInputStream(), getMonitor());
/* 48 */         FileOutputStream outputStream = new FileOutputStream(getTarget());
/* 49 */         String digest = copyAndDigest((InputStream)monitoringInputStream, outputStream, "SHA", 40);
/*    */         
/* 51 */         if (this.expectedHash.equalsIgnoreCase(digest)) {
/* 52 */           return "Downloaded successfully and hash matched";
/*    */         }
/* 54 */         throw new RuntimeException(String.format("Hash did not match downloaded file (Expected %s, downloaded %s)", new Object[] { this.expectedHash, digest }));
/*    */       } 
/* 56 */       if (getTarget().isFile()) {
/* 57 */         return "Couldn't connect to server (responded with " + status + ") but have local file, assuming it's good";
/*    */       }
/* 59 */       throw new RuntimeException("Server responded with " + status);
/*    */     }
/* 61 */     catch (IOException e) {
/* 62 */       if (getTarget().isFile()) {
/* 63 */         return "Couldn't connect to server (" + e.getClass().getSimpleName() + ": '" + e.getMessage() + "') but have local file, assuming it's good";
/*    */       }
/* 65 */       throw e;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\PreHashedDownloadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */