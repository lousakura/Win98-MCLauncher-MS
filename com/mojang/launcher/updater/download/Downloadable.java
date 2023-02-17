/*     */ package com.mojang.launcher.updater.download;
/*     */ import java.io.Closeable;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigInteger;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.security.DigestInputStream;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ 
/*     */ public abstract class Downloadable {
/*  16 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final URL url;
/*     */   private final File target;
/*     */   private final boolean forceDownload;
/*     */   private final Proxy proxy;
/*     */   private final ProgressContainer monitor;
/*     */   private long startTime;
/*     */   protected int numAttempts;
/*     */   private long expectedSize;
/*     */   private long endTime;
/*     */   
/*     */   public Downloadable(Proxy proxy, URL remoteFile, File localFile, boolean forceDownload) {
/*  29 */     this.proxy = proxy;
/*  30 */     this.url = remoteFile;
/*  31 */     this.target = localFile;
/*  32 */     this.forceDownload = forceDownload;
/*  33 */     this.monitor = new ProgressContainer();
/*     */   }
/*     */   
/*     */   public ProgressContainer getMonitor() {
/*  37 */     return this.monitor;
/*     */   }
/*     */   
/*     */   public long getExpectedSize() {
/*  41 */     return this.expectedSize;
/*     */   }
/*     */   
/*     */   public void setExpectedSize(long expectedSize) {
/*  45 */     this.expectedSize = expectedSize;
/*     */   }
/*     */   
/*     */   public static String getDigest(File file, String algorithm, int hashLength) {
/*  49 */     DigestInputStream stream = null;
/*     */     try {
/*     */       int read;
/*  52 */       stream = new DigestInputStream(new FileInputStream(file), MessageDigest.getInstance(algorithm));
/*  53 */       byte[] buffer = new byte[65536];
/*     */       
/*     */       do {
/*  56 */         read = stream.read(buffer);
/*  57 */       } while (read > 0);
/*  58 */     } catch (Exception ignored) {
/*  59 */       return null;
/*     */     } finally {
/*  61 */       closeSilently(stream);
/*     */     } 
/*     */     
/*  64 */     return String.format("%1$0" + hashLength + "x", new Object[] { new BigInteger(1, stream.getMessageDigest().digest()) });
/*     */   }
/*     */   
/*     */   public abstract String download() throws IOException;
/*     */   
/*     */   protected void updateExpectedSize(HttpURLConnection connection) {
/*  70 */     if (this.expectedSize == 0L) {
/*  71 */       this.monitor.setTotal(connection.getContentLength());
/*  72 */       setExpectedSize(connection.getContentLength());
/*     */     } else {
/*  74 */       this.monitor.setTotal(this.expectedSize);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected HttpURLConnection makeConnection(URL url) throws IOException {
/*  79 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection(this.proxy);
/*     */ 
/*     */     
/*  82 */     connection.setUseCaches(false);
/*  83 */     connection.setDefaultUseCaches(false);
/*  84 */     connection.setRequestProperty("Cache-Control", "no-store,max-age=0,no-cache");
/*  85 */     connection.setRequestProperty("Expires", "0");
/*  86 */     connection.setRequestProperty("Pragma", "no-cache");
/*  87 */     connection.setConnectTimeout(5000);
/*  88 */     connection.setReadTimeout(30000);
/*     */     
/*  90 */     return connection;
/*     */   }
/*     */   
/*     */   public URL getUrl() {
/*  94 */     return this.url;
/*     */   }
/*     */   
/*     */   public File getTarget() {
/*  98 */     return this.target;
/*     */   }
/*     */   
/*     */   public boolean shouldIgnoreLocal() {
/* 102 */     return this.forceDownload;
/*     */   }
/*     */   
/*     */   public int getNumAttempts() {
/* 106 */     return this.numAttempts;
/*     */   }
/*     */   
/*     */   public Proxy getProxy() {
/* 110 */     return this.proxy;
/*     */   }
/*     */   
/*     */   public static void closeSilently(Closeable closeable) {
/* 114 */     if (closeable != null) {
/*     */       try {
/* 116 */         closeable.close();
/* 117 */       } catch (IOException iOException) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String copyAndDigest(InputStream inputStream, OutputStream outputStream, String algorithm, int hashLength) throws IOException {
/*     */     MessageDigest digest;
/*     */     try {
/* 126 */       digest = MessageDigest.getInstance(algorithm);
/* 127 */     } catch (NoSuchAlgorithmException e) {
/* 128 */       closeSilently(inputStream);
/* 129 */       closeSilently(outputStream);
/* 130 */       throw new RuntimeException("Missing Digest." + algorithm, e);
/*     */     } 
/*     */     
/* 133 */     byte[] buffer = new byte[65536];
/*     */     
/*     */     try {
/* 136 */       int read = inputStream.read(buffer);
/* 137 */       while (read >= 1) {
/* 138 */         digest.update(buffer, 0, read);
/* 139 */         outputStream.write(buffer, 0, read);
/* 140 */         read = inputStream.read(buffer);
/*     */       } 
/*     */     } finally {
/* 143 */       closeSilently(inputStream);
/* 144 */       closeSilently(outputStream);
/*     */     } 
/*     */     
/* 147 */     return String.format("%1$0" + hashLength + "x", new Object[] { new BigInteger(1, digest.digest()) });
/*     */   }
/*     */   
/*     */   protected void ensureFileWritable(File target) {
/* 151 */     if (target.getParentFile() != null && !target.getParentFile().isDirectory()) {
/* 152 */       LOGGER.info("Making directory " + target.getParentFile());
/*     */       
/* 154 */       if (!target.getParentFile().mkdirs() && 
/* 155 */         !target.getParentFile().isDirectory())
/*     */       {
/* 157 */         throw new RuntimeException("Could not create directory " + target.getParentFile());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 162 */     if (target.isFile() && !target.canWrite()) {
/* 163 */       throw new RuntimeException("Do not have write permissions for " + target + " - aborting!");
/*     */     }
/*     */   }
/*     */   
/*     */   public long getStartTime() {
/* 168 */     return this.startTime;
/*     */   }
/*     */   
/*     */   public void setStartTime(long startTime) {
/* 172 */     this.startTime = startTime;
/*     */   }
/*     */   
/*     */   public String getStatus() {
/* 176 */     return "Downloading " + getTarget().getName();
/*     */   }
/*     */   
/*     */   public long getEndTime() {
/* 180 */     return this.endTime;
/*     */   }
/*     */   
/*     */   public void setEndTime(long endTime) {
/* 184 */     this.endTime = endTime;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\download\Downloadable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */