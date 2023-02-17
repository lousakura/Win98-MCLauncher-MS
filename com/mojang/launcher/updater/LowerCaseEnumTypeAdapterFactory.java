/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonToken;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LowerCaseEnumTypeAdapterFactory
/*    */   implements TypeAdapterFactory
/*    */ {
/*    */   public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
/* 22 */     Class<T> rawType = type.getRawType();
/* 23 */     if (!rawType.isEnum()) {
/* 24 */       return null;
/*    */     }
/*    */     
/* 27 */     final Map<String, T> lowercaseToConstant = new HashMap<String, T>();
/* 28 */     for (T constant : rawType.getEnumConstants()) {
/* 29 */       lowercaseToConstant.put(toLowercase(constant), constant);
/*    */     }
/*    */     
/* 32 */     return new TypeAdapter<T>() {
/*    */         public void write(JsonWriter out, T value) throws IOException {
/* 34 */           if (value == null) {
/* 35 */             out.nullValue();
/*    */           } else {
/* 37 */             out.value(LowerCaseEnumTypeAdapterFactory.this.toLowercase(value));
/*    */           } 
/*    */         }
/*    */         
/*    */         public T read(JsonReader reader) throws IOException {
/* 42 */           if (reader.peek() == JsonToken.NULL) {
/* 43 */             reader.nextNull();
/* 44 */             return null;
/*    */           } 
/* 46 */           return (T)lowercaseToConstant.get(reader.nextString());
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */   
/*    */   private String toLowercase(Object o) {
/* 53 */     return o.toString().toLowerCase(Locale.US);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\LowerCaseEnumTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */