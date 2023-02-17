/*    */ package net.minecraft.hopper;
/*    */ 
/*    */ import java.io.DataOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.MalformedURLException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.nio.charset.Charset;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class Util
/*    */ {
/*    */   public static String performPost(URL url, String parameters, Proxy proxy, String contentType, boolean returnErrorPage) throws IOException {
/* 16 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection(proxy);
/* 17 */     byte[] paramAsBytes = parameters.getBytes(Charset.forName("UTF-8"));
/*    */     
/* 19 */     connection.setConnectTimeout(15000);
/* 20 */     connection.setReadTimeout(15000);
/* 21 */     connection.setRequestMethod("POST");
/* 22 */     connection.setRequestProperty("Content-Type", contentType + "; charset=utf-8");
/*    */     
/* 24 */     connection.setRequestProperty("Content-Length", "" + paramAsBytes.length);
/* 25 */     connection.setRequestProperty("Content-Language", "en-US");
/*    */     
/* 27 */     connection.setUseCaches(false);
/* 28 */     connection.setDoInput(true);
/* 29 */     connection.setDoOutput(true);
/*    */ 
/*    */     
/* 32 */     DataOutputStream writer = new DataOutputStream(connection.getOutputStream());
/* 33 */     writer.write(paramAsBytes);
/* 34 */     writer.flush();
/* 35 */     writer.close();
/*    */ 
/*    */     
/* 38 */     InputStream stream = null;
/*    */     try {
/* 40 */       stream = connection.getInputStream();
/* 41 */     } catch (IOException e) {
/*    */       
/* 43 */       if (returnErrorPage) {
/* 44 */         stream = connection.getErrorStream();
/*    */         
/* 46 */         if (stream == null) {
/* 47 */           throw e;
/*    */         }
/*    */       } else {
/* 50 */         throw e;
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return IOUtils.toString(stream);
/*    */   }
/*    */   
/*    */   public static URL constantURL(String input) {
/*    */     try {
/* 59 */       return new URL(input);
/* 60 */     } catch (MalformedURLException e) {
/* 61 */       throw new Error(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\hopper\Util.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */