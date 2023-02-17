/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.lang.reflect.Type;
/*    */ 
/*    */ public class ProfileSearchResultsResponse extends Response {
/*    */   public GameProfile[] getProfiles() {
/* 12 */     return this.profiles;
/*    */   }
/*    */   
/*    */   private GameProfile[] profiles;
/*    */   
/*    */   public static class Serializer implements JsonDeserializer<ProfileSearchResultsResponse> { public ProfileSearchResultsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 18 */       ProfileSearchResultsResponse result = new ProfileSearchResultsResponse();
/*    */       
/* 20 */       if (json instanceof JsonObject) {
/* 21 */         JsonObject object = (JsonObject)json;
/* 22 */         if (object.has("error")) {
/* 23 */           result.setError(object.getAsJsonPrimitive("error").getAsString());
/*    */         }
/* 25 */         if (object.has("errorMessage")) {
/* 26 */           result.setError(object.getAsJsonPrimitive("errorMessage").getAsString());
/*    */         }
/* 28 */         if (object.has("cause")) {
/* 29 */           result.setError(object.getAsJsonPrimitive("cause").getAsString());
/*    */         }
/*    */       } else {
/* 32 */         result.profiles = (GameProfile[])context.deserialize(json, GameProfile[].class);
/*    */       } 
/*    */       
/* 35 */       return result;
/*    */     } }
/*    */ 
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\yggdrasil\response\ProfileSearchResultsResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */