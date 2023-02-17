/*    */ package net.minecraft.launcher.updater;
/*    */ 
/*    */ import com.google.gson.JsonArray;
/*    */ import com.google.gson.JsonDeserializationContext;
/*    */ import com.google.gson.JsonDeserializer;
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParseException;
/*    */ import com.mojang.launcher.game.process.GameProcessBuilder;
/*    */ import java.lang.reflect.Type;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.launcher.CompatibilityRule;
/*    */ import org.apache.commons.lang3.text.StrSubstitutor;
/*    */ 
/*    */ public class Argument
/*    */ {
/*    */   private final String[] value;
/*    */   private final List<CompatibilityRule> compatibilityRules;
/*    */   
/*    */   public Argument(String[] values, List<CompatibilityRule> compatibilityRules) {
/* 22 */     this.value = values;
/* 23 */     this.compatibilityRules = compatibilityRules;
/*    */   }
/*    */   
/*    */   public void apply(GameProcessBuilder output, CompatibilityRule.FeatureMatcher featureMatcher, StrSubstitutor substitutor) {
/* 27 */     if (appliesToCurrentEnvironment(featureMatcher)) {
/* 28 */       for (int i = 0; i < this.value.length; i++) {
/* 29 */         output.withArguments(new String[] { substitutor.replace(this.value[i]) });
/*    */       } 
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean appliesToCurrentEnvironment(CompatibilityRule.FeatureMatcher featureMatcher) {
/* 35 */     if (this.compatibilityRules == null) return true; 
/* 36 */     CompatibilityRule.Action lastAction = CompatibilityRule.Action.DISALLOW;
/*    */     
/* 38 */     for (CompatibilityRule compatibilityRule : this.compatibilityRules) {
/* 39 */       CompatibilityRule.Action action = compatibilityRule.getAppliedAction(featureMatcher);
/* 40 */       if (action != null) lastAction = action;
/*    */     
/*    */     } 
/* 43 */     return (lastAction == CompatibilityRule.Action.ALLOW);
/*    */   }
/*    */   
/*    */   public static class Serializer
/*    */     implements JsonDeserializer<Argument> {
/*    */     public Argument deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
/* 49 */       if (json.isJsonPrimitive())
/* 50 */         return new Argument(new String[] { json.getAsString() }, null); 
/* 51 */       if (json.isJsonObject()) {
/* 52 */         String[] values; JsonObject obj = json.getAsJsonObject();
/* 53 */         JsonElement value = obj.get("value");
/*    */ 
/*    */         
/* 56 */         if (value.isJsonPrimitive()) {
/* 57 */           values = new String[] { value.getAsString() };
/*    */         } else {
/* 59 */           JsonArray array = value.getAsJsonArray();
/* 60 */           values = new String[array.size()];
/* 61 */           for (int i = 0; i < array.size(); i++) {
/* 62 */             values[i] = array.get(i).getAsString();
/*    */           }
/*    */         } 
/*    */         
/* 66 */         List<CompatibilityRule> rules = new ArrayList<CompatibilityRule>();
/* 67 */         if (obj.has("rules")) {
/* 68 */           JsonArray array = obj.getAsJsonArray("rules");
/* 69 */           for (JsonElement element : array) {
/* 70 */             rules.add((CompatibilityRule)context.deserialize(element, CompatibilityRule.class));
/*    */           }
/*    */         } 
/*    */         
/* 74 */         return new Argument(values, rules);
/*    */       } 
/* 76 */       throw new JsonParseException("Invalid argument, must be object or string");
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\updater\Argument.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */