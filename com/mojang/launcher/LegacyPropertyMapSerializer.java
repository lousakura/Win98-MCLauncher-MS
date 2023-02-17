/*    */ package com.mojang.launcher;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import com.mojang.authlib.properties.Property;
/*    */ import com.mojang.authlib.properties.PropertyMap;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ public class LegacyPropertyMapSerializer implements JsonSerializer<PropertyMap> {
/*    */   public JsonElement serialize(PropertyMap src, Type typeOfSrc, JsonSerializationContext context) {
/* 12 */     JsonObject result = new JsonObject();
/*    */     
/* 14 */     for (String key : src.keySet()) {
/* 15 */       JsonArray values = new JsonArray();
/*    */       
/* 17 */       for (Property property : src.get(key)) {
/* 18 */         values.add((JsonElement)new JsonPrimitive(property.getValue()));
/*    */       }
/*    */       
/* 21 */       result.add(key, (JsonElement)values);
/*    */     } 
/*    */     
/* 24 */     return (JsonElement)result;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\LegacyPropertyMapSerializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */