/*    */ package org.apache.logging.log4j.util;
/*    */ 
/*    */ import java.util.Locale;
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
/*    */ public final class EnglishEnums
/*    */ {
/*    */   public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
/* 46 */     return valueOf(enumType, name, null);
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
/*    */   public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name, T defaultValue) {
/* 63 */     return (name == null) ? defaultValue : Enum.<T>valueOf(enumType, name.toUpperCase(Locale.ENGLISH));
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\logging\log4\\util\EnglishEnums.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */