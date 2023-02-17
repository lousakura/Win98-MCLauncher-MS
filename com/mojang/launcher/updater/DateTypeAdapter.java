/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.google.gson.JsonPrimitive;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.google.gson.JsonSerializer;
/*    */ import java.lang.reflect.Type;
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
/* 17 */   private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
/* 18 */   private final DateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
/*    */ 
/*    */ 
/*    */   
/*    */   public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 23 */     if (!(json instanceof JsonPrimitive)) {
/* 24 */       throw new JsonParseException("The date should be a string value");
/*    */     }
/*    */     
/* 27 */     Date date = deserializeToDate(json.getAsString());
/*    */     
/* 29 */     if (typeOfT == Date.class) {
/* 30 */       return date;
/*    */     }
/* 32 */     throw new IllegalArgumentException(getClass() + " cannot deserialize to " + typeOfT);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
/* 38 */     synchronized (this.enUsFormat) {
/* 39 */       return (JsonElement)new JsonPrimitive(serializeToString(src));
/*    */     } 
/*    */   }
/*    */   
/*    */   public Date deserializeToDate(String string) {
/* 44 */     synchronized (this.enUsFormat) {
/*    */       
/* 46 */       return this.enUsFormat.parse(string);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String serializeToString(Date date) {
/* 64 */     synchronized (this.enUsFormat) {
/* 65 */       String result = this.iso8601Format.format(date);
/* 66 */       return result.substring(0, 22) + ":" + result.substring(22);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\DateTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */