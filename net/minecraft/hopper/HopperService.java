/*    */ package net.minecraft.hopper;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import java.io.IOException;
/*    */ import java.net.Proxy;
/*    */ import java.net.URL;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public final class HopperService
/*    */ {
/*    */   private static final String BASE_URL = "http://hopper.minecraft.net/crashes/";
/* 13 */   private static final URL ROUTE_SUBMIT = Util.constantURL("http://hopper.minecraft.net/crashes/submit_report/");
/* 14 */   private static final URL ROUTE_PUBLISH = Util.constantURL("http://hopper.minecraft.net/crashes/publish_report/");
/* 15 */   private static final String[] INTERESTING_SYSTEM_PROPERTY_KEYS = new String[] { "os.version", "os.name", "os.arch", "java.version", "java.vendor", "sun.arch.data.model" };
/*    */   
/* 17 */   private static final Gson GSON = new Gson();
/*    */   
/*    */   public static SubmitResponse submitReport(Proxy proxy, String report, String product, String version) throws IOException {
/* 20 */     return submitReport(proxy, report, product, version, null);
/*    */   }
/*    */   
/*    */   public static SubmitResponse submitReport(Proxy proxy, String report, String product, String version, Map<String, String> env) throws IOException {
/* 24 */     Map<String, String> environment = new HashMap<String, String>();
/* 25 */     if (env != null) {
/* 26 */       environment.putAll(env);
/*    */     }
/*    */     
/* 29 */     for (String key : INTERESTING_SYSTEM_PROPERTY_KEYS) {
/* 30 */       String value = System.getProperty(key);
/*    */       
/* 32 */       if (value != null) {
/* 33 */         environment.put(key, value);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     SubmitRequest request = new SubmitRequest(report, product, version, environment);
/*    */     
/* 39 */     return makeRequest(proxy, ROUTE_SUBMIT, request, SubmitResponse.class);
/*    */   }
/*    */   
/*    */   public static PublishResponse publishReport(Proxy proxy, Report report) throws IOException {
/* 43 */     PublishRequest request = new PublishRequest(report);
/*    */     
/* 45 */     return makeRequest(proxy, ROUTE_PUBLISH, request, PublishResponse.class);
/*    */   }
/*    */   
/*    */   private static <T extends Response> T makeRequest(Proxy proxy, URL url, Object input, Class<T> classOfT) throws IOException {
/* 49 */     String jsonResult = Util.performPost(url, GSON.toJson(input), proxy, "application/json", true);
/* 50 */     Response response = (Response)GSON.fromJson(jsonResult, classOfT);
/*    */     
/* 52 */     if (response == null) {
/* 53 */       return null;
/*    */     }
/*    */     
/* 56 */     if (response.getError() != null) {
/* 57 */       throw new IOException(response.getError());
/*    */     }
/*    */     
/* 60 */     return (T)response;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\hopper\HopperService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */