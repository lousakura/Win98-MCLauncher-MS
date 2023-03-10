/*     */ package org.apache.logging.log4j.core.net;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.net.ConnectException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.CountDownLatch;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.OutputStreamManager;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
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
/*     */ public class TCPSocketManager
/*     */   extends AbstractSocketManager
/*     */ {
/*     */   public static final int DEFAULT_RECONNECTION_DELAY = 30000;
/*     */   private static final int DEFAULT_PORT = 4560;
/*  50 */   private static final TCPSocketManagerFactory FACTORY = new TCPSocketManagerFactory();
/*     */   
/*     */   private final int reconnectionDelay;
/*     */   
/*  54 */   private Reconnector connector = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Socket socket;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean retry;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean immediateFail;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TCPSocketManager(String name, OutputStream os, Socket sock, InetAddress addr, String host, int port, int delay, boolean immediateFail, Layout<? extends Serializable> layout) {
/*  77 */     super(name, os, addr, host, port, layout);
/*  78 */     this.reconnectionDelay = delay;
/*  79 */     this.socket = sock;
/*  80 */     this.immediateFail = immediateFail;
/*  81 */     this.retry = (delay > 0);
/*  82 */     if (sock == null) {
/*  83 */       this.connector = new Reconnector(this);
/*  84 */       this.connector.setDaemon(true);
/*  85 */       this.connector.setPriority(1);
/*  86 */       this.connector.start();
/*     */     } 
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
/*     */   public static TCPSocketManager getSocketManager(String host, int port, int delay, boolean immediateFail, Layout<? extends Serializable> layout) {
/*  99 */     if (Strings.isEmpty(host)) {
/* 100 */       throw new IllegalArgumentException("A host name is required");
/*     */     }
/* 102 */     if (port <= 0) {
/* 103 */       port = 4560;
/*     */     }
/* 105 */     if (delay == 0) {
/* 106 */       delay = 30000;
/*     */     }
/* 108 */     return (TCPSocketManager)getManager("TCP:" + host + ":" + port, new FactoryData(host, port, delay, immediateFail, layout), FACTORY);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void write(byte[] bytes, int offset, int length) {
/* 114 */     if (this.socket == null) {
/* 115 */       if (this.connector != null && !this.immediateFail) {
/* 116 */         this.connector.latch();
/*     */       }
/* 118 */       if (this.socket == null) {
/* 119 */         String msg = "Error writing to " + getName() + " socket not available";
/* 120 */         throw new AppenderLoggingException(msg);
/*     */       } 
/*     */     } 
/* 123 */     synchronized (this) {
/*     */       try {
/* 125 */         getOutputStream().write(bytes, offset, length);
/* 126 */       } catch (IOException ex) {
/* 127 */         if (this.retry && this.connector == null) {
/* 128 */           this.connector = new Reconnector(this);
/* 129 */           this.connector.setDaemon(true);
/* 130 */           this.connector.setPriority(1);
/* 131 */           this.connector.start();
/*     */         } 
/* 133 */         String msg = "Error writing to " + getName();
/* 134 */         throw new AppenderLoggingException(msg, ex);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void close() {
/* 141 */     super.close();
/* 142 */     if (this.connector != null) {
/* 143 */       this.connector.shutdown();
/* 144 */       this.connector.interrupt();
/* 145 */       this.connector = null;
/*     */     } 
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
/*     */   public Map<String, String> getContentFormat() {
/* 158 */     Map<String, String> result = new HashMap<String, String>(super.getContentFormat());
/* 159 */     result.put("protocol", "tcp");
/* 160 */     result.put("direction", "out");
/* 161 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class Reconnector
/*     */     extends Thread
/*     */   {
/* 169 */     private final CountDownLatch latch = new CountDownLatch(1);
/*     */     
/*     */     private boolean shutdown = false;
/*     */     
/*     */     private final Object owner;
/*     */     
/*     */     public Reconnector(OutputStreamManager owner) {
/* 176 */       this.owner = owner;
/*     */     }
/*     */     
/*     */     public void latch() {
/*     */       try {
/* 181 */         this.latch.await();
/* 182 */       } catch (InterruptedException ex) {}
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void shutdown() {
/* 188 */       this.shutdown = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 193 */       while (!this.shutdown) {
/*     */         try {
/* 195 */           sleep(TCPSocketManager.this.reconnectionDelay);
/* 196 */           Socket sock = TCPSocketManager.this.createSocket(TCPSocketManager.this.address, TCPSocketManager.this.port);
/* 197 */           OutputStream newOS = sock.getOutputStream();
/* 198 */           synchronized (this.owner) {
/*     */             try {
/* 200 */               TCPSocketManager.this.getOutputStream().close();
/* 201 */             } catch (IOException ioe) {}
/*     */ 
/*     */ 
/*     */             
/* 205 */             TCPSocketManager.this.setOutputStream(newOS);
/* 206 */             TCPSocketManager.this.socket = sock;
/* 207 */             TCPSocketManager.this.connector = null;
/* 208 */             this.shutdown = true;
/*     */           } 
/* 210 */           TCPSocketManager.LOGGER.debug("Connection to " + TCPSocketManager.this.host + ":" + TCPSocketManager.this.port + " reestablished.");
/* 211 */         } catch (InterruptedException ie) {
/* 212 */           TCPSocketManager.LOGGER.debug("Reconnection interrupted.");
/* 213 */         } catch (ConnectException ex) {
/* 214 */           TCPSocketManager.LOGGER.debug(TCPSocketManager.this.host + ":" + TCPSocketManager.this.port + " refused connection");
/* 215 */         } catch (IOException ioe) {
/* 216 */           TCPSocketManager.LOGGER.debug("Unable to reconnect to " + TCPSocketManager.this.host + ":" + TCPSocketManager.this.port);
/*     */         } finally {
/* 218 */           this.latch.countDown();
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected Socket createSocket(InetAddress host, int port) throws IOException {
/* 225 */     return createSocket(host.getHostName(), port);
/*     */   }
/*     */   
/*     */   protected Socket createSocket(String host, int port) throws IOException {
/* 229 */     return new Socket(host, port);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String host;
/*     */     
/*     */     private final int port;
/*     */     
/*     */     private final int delay;
/*     */     private final boolean immediateFail;
/*     */     private final Layout<? extends Serializable> layout;
/*     */     
/*     */     public FactoryData(String host, int port, int delay, boolean immediateFail, Layout<? extends Serializable> layout) {
/* 244 */       this.host = host;
/* 245 */       this.port = port;
/* 246 */       this.delay = delay;
/* 247 */       this.immediateFail = immediateFail;
/* 248 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class TCPSocketManagerFactory
/*     */     implements ManagerFactory<TCPSocketManager, FactoryData>
/*     */   {
/*     */     public TCPSocketManager createManager(String name, TCPSocketManager.FactoryData data) {
/*     */       InetAddress address;
/*     */       try {
/* 262 */         address = InetAddress.getByName(data.host);
/* 263 */       } catch (UnknownHostException ex) {
/* 264 */         TCPSocketManager.LOGGER.error("Could not find address of " + data.host, ex);
/* 265 */         return null;
/*     */       } 
/*     */       try {
/* 268 */         Socket socket = new Socket(data.host, data.port);
/* 269 */         OutputStream os = socket.getOutputStream();
/* 270 */         return new TCPSocketManager(name, os, socket, address, data.host, data.port, data.delay, data.immediateFail, data.layout);
/*     */       }
/* 272 */       catch (IOException ex) {
/* 273 */         TCPSocketManager.LOGGER.error("TCPSocketManager (" + name + ") " + ex);
/* 274 */         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/*     */         
/* 276 */         if (data.delay == 0) {
/* 277 */           return null;
/*     */         }
/* 279 */         return new TCPSocketManager(name, byteArrayOutputStream, null, address, data.host, data.port, data.delay, data.immediateFail, data.layout);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4j\core\net\TCPSocketManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */