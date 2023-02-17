/*     */ package com.mojang.launcher.updater.download.assets;
/*     */ import com.mojang.launcher.updater.download.MonitoringInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AssetDownloadable extends Downloadable {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final String name;
/*     */   private final AssetIndex.AssetObject asset;
/*     */   private final String urlBase;
/*     */   private final File destination;
/*  24 */   private Status status = Status.DOWNLOADING;
/*     */   
/*     */   public AssetDownloadable(Proxy proxy, String name, AssetIndex.AssetObject asset, String urlBase, File destination) throws MalformedURLException {
/*  27 */     super(proxy, new URL(urlBase + createPathFromHash(asset.getHash())), new File(destination, createPathFromHash(asset.getHash())), false);
/*  28 */     this.name = name;
/*  29 */     this.asset = asset;
/*  30 */     this.urlBase = urlBase;
/*  31 */     this.destination = destination;
/*     */   }
/*     */   
/*     */   protected static String createPathFromHash(String hash) {
/*  35 */     return hash.substring(0, 2) + "/" + hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String download() throws IOException {
/*  40 */     this.status = Status.DOWNLOADING;
/*     */     
/*  42 */     this.numAttempts++;
/*  43 */     File localAsset = getTarget();
/*  44 */     File localCompressed = this.asset.hasCompressedAlternative() ? new File(this.destination, createPathFromHash(this.asset.getCompressedHash())) : null;
/*  45 */     URL remoteAsset = getUrl();
/*  46 */     URL remoteCompressed = this.asset.hasCompressedAlternative() ? new URL(this.urlBase + createPathFromHash(this.asset.getCompressedHash())) : null;
/*     */     
/*  48 */     ensureFileWritable(localAsset);
/*  49 */     if (localCompressed != null) {
/*  50 */       ensureFileWritable(localCompressed);
/*     */     }
/*     */     
/*  53 */     if (localAsset.isFile()) {
/*  54 */       if (FileUtils.sizeOf(localAsset) == this.asset.getSize()) {
/*  55 */         return "Have local file and it's the same size; assuming it's okay!";
/*     */       }
/*  57 */       LOGGER.warn("Had local file but it was the wrong size... had {} but expected {}", new Object[] { Long.valueOf(FileUtils.sizeOf(localAsset)), Long.valueOf(this.asset.getSize()) });
/*  58 */       FileUtils.deleteQuietly(localAsset);
/*  59 */       this.status = Status.DOWNLOADING;
/*     */     } 
/*     */ 
/*     */     
/*  63 */     if (localCompressed != null && localCompressed.isFile()) {
/*  64 */       String localCompressedHash = getDigest(localCompressed, "SHA", 40);
/*     */       
/*  66 */       if (localCompressedHash.equalsIgnoreCase(this.asset.getCompressedHash())) {
/*  67 */         return decompressAsset(localAsset, localCompressed);
/*     */       }
/*  69 */       LOGGER.warn("Had local compressed but it was the wrong hash... expected {} but had {}", new Object[] { this.asset.getCompressedHash(), localCompressedHash });
/*  70 */       FileUtils.deleteQuietly(localCompressed);
/*     */     } 
/*     */ 
/*     */     
/*  74 */     if (remoteCompressed != null && localCompressed != null) {
/*  75 */       HttpURLConnection httpURLConnection = makeConnection(remoteCompressed);
/*  76 */       int i = httpURLConnection.getResponseCode();
/*     */       
/*  78 */       if (i / 100 == 2) {
/*  79 */         updateExpectedSize(httpURLConnection);
/*     */         
/*  81 */         MonitoringInputStream monitoringInputStream = new MonitoringInputStream(httpURLConnection.getInputStream(), getMonitor());
/*  82 */         FileOutputStream outputStream = new FileOutputStream(localCompressed);
/*  83 */         String hash = copyAndDigest((InputStream)monitoringInputStream, outputStream, "SHA", 40);
/*     */         
/*  85 */         if (hash.equalsIgnoreCase(this.asset.getCompressedHash())) {
/*  86 */           return decompressAsset(localAsset, localCompressed);
/*     */         }
/*  88 */         FileUtils.deleteQuietly(localCompressed);
/*  89 */         throw new RuntimeException(String.format("Hash did not match downloaded compressed asset (Expected %s, downloaded %s)", new Object[] { this.asset.getCompressedHash(), hash }));
/*     */       } 
/*     */       
/*  92 */       throw new RuntimeException("Server responded with " + i);
/*     */     } 
/*     */ 
/*     */     
/*  96 */     HttpURLConnection connection = makeConnection(remoteAsset);
/*  97 */     int status = connection.getResponseCode();
/*     */     
/*  99 */     if (status / 100 == 2) {
/* 100 */       updateExpectedSize(connection);
/*     */       
/* 102 */       MonitoringInputStream monitoringInputStream = new MonitoringInputStream(connection.getInputStream(), getMonitor());
/* 103 */       FileOutputStream outputStream = new FileOutputStream(localAsset);
/* 104 */       String hash = copyAndDigest((InputStream)monitoringInputStream, outputStream, "SHA", 40);
/*     */       
/* 106 */       if (hash.equalsIgnoreCase(this.asset.getHash())) {
/* 107 */         return "Downloaded asset and hash matched successfully";
/*     */       }
/* 109 */       FileUtils.deleteQuietly(localAsset);
/* 110 */       throw new RuntimeException(String.format("Hash did not match downloaded asset (Expected %s, downloaded %s)", new Object[] { this.asset.getHash(), hash }));
/*     */     } 
/*     */     
/* 113 */     throw new RuntimeException("Server responded with " + status);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStatus() {
/* 119 */     return this.status.name + " " + this.name;
/*     */   }
/*     */   protected String decompressAsset(File localAsset, File localCompressed) throws IOException {
/*     */     String hash;
/* 123 */     this.status = Status.EXTRACTING;
/* 124 */     OutputStream outputStream = FileUtils.openOutputStream(localAsset);
/* 125 */     InputStream inputStream = new GZIPInputStream(FileUtils.openInputStream(localCompressed));
/*     */ 
/*     */     
/*     */     try {
/* 129 */       hash = copyAndDigest(inputStream, outputStream, "SHA", 40);
/*     */     } finally {
/* 131 */       IOUtils.closeQuietly(outputStream);
/* 132 */       IOUtils.closeQuietly(inputStream);
/*     */     } 
/*     */     
/* 135 */     this.status = Status.DOWNLOADING;
/*     */     
/* 137 */     if (hash.equalsIgnoreCase(this.asset.getHash())) {
/* 138 */       return "Had local compressed asset, unpacked successfully and hash matched";
/*     */     }
/* 140 */     FileUtils.deleteQuietly(localAsset);
/* 141 */     throw new RuntimeException("Had local compressed asset but unpacked hash did not match (expected " + this.asset.getHash() + " but had " + hash + ")");
/*     */   }
/*     */   
/*     */   private enum Status
/*     */   {
/* 146 */     DOWNLOADING("Downloading"),
/* 147 */     EXTRACTING("Extracting");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     Status(String name) {
/* 153 */       this.name = name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\assets\AssetDownloadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */