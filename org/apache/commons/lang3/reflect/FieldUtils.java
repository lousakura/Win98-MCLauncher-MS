/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FieldUtils
/*     */ {
/*     */   public static Field getField(Class<?> cls, String fieldName) {
/*  62 */     Field field = getField(cls, fieldName, false);
/*  63 */     MemberUtils.setAccessibleWorkaround(field);
/*  64 */     return field;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getField(Class<?> cls, String fieldName, boolean forceAccess) {
/*  85 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/*  86 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 101 */     for (Class<?> acls = cls; acls != null; acls = acls.getSuperclass()) {
/*     */       try {
/* 103 */         Field field = acls.getDeclaredField(fieldName);
/*     */ 
/*     */         
/* 106 */         if (!Modifier.isPublic(field.getModifiers()))
/* 107 */         { if (forceAccess)
/* 108 */           { field.setAccessible(true);
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 113 */             return field; }  } else { return field; } 
/* 114 */       } catch (NoSuchFieldException ex) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 121 */     Field match = null;
/* 122 */     for (Class<?> class1 : (Iterable<Class<?>>)ClassUtils.getAllInterfaces(cls)) {
/*     */       try {
/* 124 */         Field test = class1.getField(fieldName);
/* 125 */         Validate.isTrue((match == null), "Reference to field %s is ambiguous relative to %s; a matching field exists on two or more implemented interfaces.", new Object[] { fieldName, cls });
/*     */         
/* 127 */         match = test;
/* 128 */       } catch (NoSuchFieldException ex) {}
/*     */     } 
/*     */ 
/*     */     
/* 132 */     return match;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName) {
/* 147 */     return getDeclaredField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field getDeclaredField(Class<?> cls, String fieldName, boolean forceAccess) {
/* 167 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/* 168 */     Validate.isTrue(StringUtils.isNotBlank(fieldName), "The field name must not be blank/empty", new Object[0]);
/*     */     
/*     */     try {
/* 171 */       Field field = cls.getDeclaredField(fieldName);
/* 172 */       if (!MemberUtils.isAccessible(field)) {
/* 173 */         if (forceAccess) {
/* 174 */           field.setAccessible(true);
/*     */         } else {
/* 176 */           return null;
/*     */         } 
/*     */       }
/* 179 */       return field;
/* 180 */     } catch (NoSuchFieldException e) {
/*     */ 
/*     */       
/* 183 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getAllFields(Class<?> cls) {
/* 197 */     List<Field> allFieldsList = getAllFieldsList(cls);
/* 198 */     return allFieldsList.<Field>toArray(new Field[allFieldsList.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Field> getAllFieldsList(Class<?> cls) {
/* 212 */     Validate.isTrue((cls != null), "The class must not be null", new Object[0]);
/* 213 */     List<Field> allFields = new ArrayList<Field>();
/* 214 */     Class<?> currentClass = cls;
/* 215 */     while (currentClass != null) {
/* 216 */       Field[] declaredFields = currentClass.getDeclaredFields();
/* 217 */       for (Field field : declaredFields) {
/* 218 */         allFields.add(field);
/*     */       }
/* 220 */       currentClass = currentClass.getSuperclass();
/*     */     } 
/* 222 */     return allFields;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Field field) throws IllegalAccessException {
/* 237 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Field field, boolean forceAccess) throws IllegalAccessException {
/* 255 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 256 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field '%s' is not static", new Object[] { field.getName() });
/* 257 */     return readField(field, (Object)null, forceAccess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 275 */     return readStaticField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 297 */     Field field = getField(cls, fieldName, forceAccess);
/* 298 */     Validate.isTrue((field != null), "Cannot locate field '%s' on %s", new Object[] { fieldName, cls });
/*     */     
/* 300 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName) throws IllegalAccessException {
/* 319 */     return readDeclaredStaticField(cls, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredStaticField(Class<?> cls, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 341 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 342 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 344 */     return readStaticField(field, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Field field, Object target) throws IllegalAccessException {
/* 361 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Field field, Object target, boolean forceAccess) throws IllegalAccessException {
/* 381 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 382 */     if (forceAccess && !field.isAccessible()) {
/* 383 */       field.setAccessible(true);
/*     */     } else {
/* 385 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 387 */     return field.get(target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Object target, String fieldName) throws IllegalAccessException {
/* 404 */     return readField(target, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 425 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 426 */     Class<?> cls = target.getClass();
/* 427 */     Field field = getField(cls, fieldName, forceAccess);
/* 428 */     Validate.isTrue((field != null), "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 430 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredField(Object target, String fieldName) throws IllegalAccessException {
/* 447 */     return readDeclaredField(target, fieldName, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object readDeclaredField(Object target, String fieldName, boolean forceAccess) throws IllegalAccessException {
/* 468 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 469 */     Class<?> cls = target.getClass();
/* 470 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 471 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls, fieldName });
/*     */     
/* 473 */     return readField(field, target, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Field field, Object value) throws IllegalAccessException {
/* 489 */     writeStaticField(field, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Field field, Object value, boolean forceAccess) throws IllegalAccessException {
/* 509 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 510 */     Validate.isTrue(Modifier.isStatic(field.getModifiers()), "The field %s.%s is not static", new Object[] { field.getDeclaringClass().getName(), field.getName() });
/*     */     
/* 512 */     writeField(field, (Object)null, value, forceAccess);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 531 */     writeStaticField(cls, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 555 */     Field field = getField(cls, fieldName, forceAccess);
/* 556 */     Validate.isTrue((field != null), "Cannot locate field %s on %s", new Object[] { fieldName, cls });
/*     */     
/* 558 */     writeStaticField(field, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value) throws IllegalAccessException {
/* 577 */     writeDeclaredStaticField(cls, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredStaticField(Class<?> cls, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 600 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 601 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 603 */     writeField(field, (Object)null, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Field field, Object target, Object value) throws IllegalAccessException {
/* 620 */     writeField(field, target, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Field field, Object target, Object value, boolean forceAccess) throws IllegalAccessException {
/* 643 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/* 644 */     if (forceAccess && !field.isAccessible()) {
/* 645 */       field.setAccessible(true);
/*     */     } else {
/* 647 */       MemberUtils.setAccessibleWorkaround(field);
/*     */     } 
/* 649 */     field.set(target, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeFinalModifier(Field field) {
/* 662 */     removeFinalModifier(field, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeFinalModifier(Field field, boolean forceAccess) {
/* 679 */     Validate.isTrue((field != null), "The field must not be null", new Object[0]);
/*     */     
/*     */     try {
/* 682 */       if (Modifier.isFinal(field.getModifiers())) {
/*     */         
/* 684 */         Field modifiersField = Field.class.getDeclaredField("modifiers");
/* 685 */         boolean doForceAccess = (forceAccess && !modifiersField.isAccessible());
/* 686 */         if (doForceAccess) {
/* 687 */           modifiersField.setAccessible(true);
/*     */         }
/*     */         try {
/* 690 */           modifiersField.setInt(field, field.getModifiers() & 0xFFFFFFEF);
/*     */         } finally {
/* 692 */           if (doForceAccess) {
/* 693 */             modifiersField.setAccessible(false);
/*     */           }
/*     */         } 
/*     */       } 
/* 697 */     } catch (NoSuchFieldException ignored) {
/*     */     
/* 699 */     } catch (IllegalAccessException ignored) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 720 */     writeField(target, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 744 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 745 */     Class<?> cls = target.getClass();
/* 746 */     Field field = getField(cls, fieldName, forceAccess);
/* 747 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 749 */     writeField(field, target, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value) throws IllegalAccessException {
/* 768 */     writeDeclaredField(target, fieldName, value, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void writeDeclaredField(Object target, String fieldName, Object value, boolean forceAccess) throws IllegalAccessException {
/* 792 */     Validate.isTrue((target != null), "target object must not be null", new Object[0]);
/* 793 */     Class<?> cls = target.getClass();
/* 794 */     Field field = getDeclaredField(cls, fieldName, forceAccess);
/* 795 */     Validate.isTrue((field != null), "Cannot locate declared field %s.%s", new Object[] { cls.getName(), fieldName });
/*     */     
/* 797 */     writeField(field, target, value, false);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\reflect\FieldUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */