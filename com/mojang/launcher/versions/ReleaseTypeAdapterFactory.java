/*    */ package com.mojang.launcher.versions;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class ReleaseTypeAdapterFactory<T extends ReleaseType>
/*    */   extends TypeAdapter<T> {
/*    */   private final ReleaseTypeFactory<T> factory;
/*    */   
/*    */   public ReleaseTypeAdapterFactory(ReleaseTypeFactory<T> factory) {
/* 13 */     this.factory = factory;
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(JsonWriter out, T value) throws IOException {
/* 18 */     out.value(value.getName());
/*    */   }
/*    */ 
/*    */   
/*    */   public T read(JsonReader in) throws IOException {
/* 23 */     return this.factory.getTypeByName(in.nextString());
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launcher\versions\ReleaseTypeAdapterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */