/*    */ package com.mojang.authlib.properties;
/*    */ import com.google.common.collect.Multimap;
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonSerializationContext;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PropertyMap extends ForwardingMultimap<String, Property> {
/* 12 */   private final Multimap<String, Property> properties = (Multimap<String, Property>)LinkedHashMultimap.create();
/*    */ 
/*    */   
/*    */   protected Multimap<String, Property> delegate() {
/* 16 */     return this.properties;
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     implements JsonSerializer<PropertyMap>, JsonDeserializer<PropertyMap> {
/*    */     public PropertyMap deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 22 */       PropertyMap result = new PropertyMap();
/*    */       
/* 24 */       if (json instanceof JsonObject) {
/* 25 */         JsonObject object = (JsonObject)json;
/*    */         
/* 27 */         for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)object.entrySet()) {
/* 28 */           if (entry.getValue() instanceof JsonArray) {
/* 29 */             for (JsonElement element : entry.getValue()) {
/* 30 */               result.put(entry.getKey(), new Property(entry.getKey(), element.getAsString()));
/*    */             }
/*    */           }
/*    */         } 
/* 34 */       } else if (json instanceof JsonArray) {
/* 35 */         for (JsonElement element : json) {
/* 36 */           if (element instanceof JsonObject) {
/* 37 */             JsonObject object = (JsonObject)element;
/* 38 */             String name = object.getAsJsonPrimitive("name").getAsString();
/* 39 */             String value = object.getAsJsonPrimitive("value").getAsString();
/*    */             
/* 41 */             if (object.has("signature")) {
/* 42 */               result.put(name, new Property(name, value, object.getAsJsonPrimitive("signature").getAsString())); continue;
/*    */             } 
/* 44 */             result.put(name, new Property(name, value));
/*    */           } 
/*    */         } 
/*    */       } 
/*    */ 
/*    */       
/* 50 */       return result;
/*    */     }
/*    */ 
/*    */     
/*    */     public JsonElement serialize(PropertyMap src, Type typeOfSrc, JsonSerializationContext context) {
/* 55 */       JsonArray result = new JsonArray();
/*    */       
/* 57 */       for (Property property : src.values()) {
/* 58 */         JsonObject object = new JsonObject();
/*    */         
/* 60 */         object.addProperty("name", property.getName());
/* 61 */         object.addProperty("value", property.getValue());
/*    */         
/* 63 */         if (property.hasSignature()) {
/* 64 */           object.addProperty("signature", property.getSignature());
/*    */         }
/*    */         
/* 67 */         result.add((JsonElement)object);
/*    */       } 
/*    */       
/* 70 */       return (JsonElement)result;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\properties\PropertyMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */