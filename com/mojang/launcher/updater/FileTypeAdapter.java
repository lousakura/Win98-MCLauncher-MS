/*    */ package com.mojang.launcher.updater;
/*    */ 
/*    */ import com.google.gson.TypeAdapter;
/*    */ import com.google.gson.stream.JsonReader;
/*    */ import com.google.gson.stream.JsonWriter;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class FileTypeAdapter
/*    */   extends TypeAdapter<File>
/*    */ {
/*    */   public void write(JsonWriter out, File value) throws IOException {
/* 13 */     if (value == null) {
/* 14 */       out.nullValue();
/*    */     } else {
/* 16 */       out.value(value.getAbsolutePath());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public File read(JsonReader in) throws IOException {
/* 22 */     if (in.hasNext()) {
/* 23 */       String name = in.nextString();
/* 24 */       return (name != null) ? new File(name) : null;
/*    */     } 
/*    */     
/* 27 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\launche\\updater\FileTypeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */