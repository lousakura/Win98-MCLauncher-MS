/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import org.apache.commons.lang3.exception.CloneFailedException;
/*     */ import org.apache.commons.lang3.mutable.MutableInt;
/*     */ import org.apache.commons.lang3.text.StrBuilder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectUtils
/*     */ {
/*  63 */   public static final Null NULL = new Null();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T defaultIfNull(T object, T defaultValue) {
/*  96 */     return (object != null) ? object : defaultValue;
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
/*     */ 
/*     */   
/*     */   public static <T> T firstNonNull(T... values) {
/* 122 */     if (values != null) {
/* 123 */       for (T val : values) {
/* 124 */         if (val != null) {
/* 125 */           return val;
/*     */         }
/*     */       } 
/*     */     }
/* 129 */     return null;
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean equals(Object object1, Object object2) {
/* 157 */     if (object1 == object2) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (object1 == null || object2 == null) {
/* 161 */       return false;
/*     */     }
/* 163 */     return object1.equals(object2);
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
/*     */   public static boolean notEqual(Object object1, Object object2) {
/* 186 */     return !equals(object1, object2);
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
/*     */   @Deprecated
/*     */   public static int hashCode(Object obj) {
/* 207 */     return (obj == null) ? 0 : obj.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 212 */     return super.toString();
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
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int hashCodeMulti(Object... objects) {
/* 239 */     int hash = 1;
/* 240 */     if (objects != null) {
/* 241 */       for (Object object : objects) {
/* 242 */         int tmpHash = hashCode(object);
/* 243 */         hash = hash * 31 + tmpHash;
/*     */       } 
/*     */     }
/* 246 */     return hash;
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
/*     */   public static String identityToString(Object object) {
/* 268 */     if (object == null) {
/* 269 */       return null;
/*     */     }
/* 271 */     StringBuilder builder = new StringBuilder();
/* 272 */     identityToString(builder, object);
/* 273 */     return builder.toString();
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
/*     */   public static void identityToString(Appendable appendable, Object object) throws IOException {
/* 293 */     if (object == null) {
/* 294 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 296 */     appendable.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
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
/*     */   public static void identityToString(StrBuilder builder, Object object) {
/* 317 */     if (object == null) {
/* 318 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 320 */     builder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
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
/*     */   public static void identityToString(StringBuffer buffer, Object object) {
/* 341 */     if (object == null) {
/* 342 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 344 */     buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
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
/*     */   public static void identityToString(StringBuilder builder, Object object) {
/* 365 */     if (object == null) {
/* 366 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 368 */     builder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String toString(Object obj) {
/* 397 */     return (obj == null) ? "" : obj.toString();
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
/*     */   
/*     */   @Deprecated
/*     */   public static String toString(Object obj, String nullStr) {
/* 423 */     return (obj == null) ? nullStr : obj.toString();
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
/*     */   public static <T extends Comparable<? super T>> T min(T... values) {
/* 442 */     T result = null;
/* 443 */     if (values != null) {
/* 444 */       for (T value : values) {
/* 445 */         if (compare(value, result, true) < 0) {
/* 446 */           result = value;
/*     */         }
/*     */       } 
/*     */     }
/* 450 */     return result;
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
/*     */   public static <T extends Comparable<? super T>> T max(T... values) {
/* 467 */     T result = null;
/* 468 */     if (values != null) {
/* 469 */       for (T value : values) {
/* 470 */         if (compare(value, result, false) > 0) {
/* 471 */           result = value;
/*     */         }
/*     */       } 
/*     */     }
/* 475 */     return result;
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
/*     */   public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
/* 489 */     return compare(c1, c2, false);
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
/*     */   public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
/* 506 */     if (c1 == c2)
/* 507 */       return 0; 
/* 508 */     if (c1 == null)
/* 509 */       return nullGreater ? 1 : -1; 
/* 510 */     if (c2 == null) {
/* 511 */       return nullGreater ? -1 : 1;
/*     */     }
/* 513 */     return c1.compareTo(c2);
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
/*     */   public static <T extends Comparable<? super T>> T median(T... items) {
/* 527 */     Validate.notEmpty(items);
/* 528 */     Validate.noNullElements(items);
/* 529 */     TreeSet<T> sort = new TreeSet<T>();
/* 530 */     Collections.addAll(sort, items);
/*     */ 
/*     */     
/* 533 */     return (T)sort.toArray()[(sort.size() - 1) / 2];
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
/*     */   public static <T> T median(Comparator<T> comparator, T... items) {
/* 549 */     Validate.notEmpty(items, "null/empty items", new Object[0]);
/* 550 */     Validate.noNullElements(items);
/* 551 */     Validate.notNull(comparator, "null comparator", new Object[0]);
/* 552 */     TreeSet<T> sort = new TreeSet<T>(comparator);
/* 553 */     Collections.addAll(sort, items);
/*     */ 
/*     */     
/* 556 */     T result = (T)sort.toArray()[(sort.size() - 1) / 2];
/* 557 */     return result;
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
/*     */   public static <T> T mode(T... items) {
/* 571 */     if (ArrayUtils.isNotEmpty(items)) {
/* 572 */       HashMap<T, MutableInt> occurrences = new HashMap<T, MutableInt>(items.length);
/* 573 */       for (T t : items) {
/* 574 */         MutableInt count = occurrences.get(t);
/* 575 */         if (count == null) {
/* 576 */           occurrences.put(t, new MutableInt(1));
/*     */         } else {
/* 578 */           count.increment();
/*     */         } 
/*     */       } 
/* 581 */       T result = null;
/* 582 */       int max = 0;
/* 583 */       for (Map.Entry<T, MutableInt> e : occurrences.entrySet()) {
/* 584 */         int cmp = ((MutableInt)e.getValue()).intValue();
/* 585 */         if (cmp == max) {
/* 586 */           result = null; continue;
/* 587 */         }  if (cmp > max) {
/* 588 */           max = cmp;
/* 589 */           result = e.getKey();
/*     */         } 
/*     */       } 
/* 592 */       return result;
/*     */     } 
/* 594 */     return null;
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
/*     */   public static <T> T clone(T obj) {
/* 609 */     if (obj instanceof Cloneable) {
/*     */       Object result;
/* 611 */       if (obj.getClass().isArray()) {
/* 612 */         Class<?> componentType = obj.getClass().getComponentType();
/* 613 */         if (!componentType.isPrimitive()) {
/* 614 */           result = ((Object[])obj).clone();
/*     */         } else {
/* 616 */           int length = Array.getLength(obj);
/* 617 */           result = Array.newInstance(componentType, length);
/* 618 */           while (length-- > 0) {
/* 619 */             Array.set(result, length, Array.get(obj, length));
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         try {
/* 624 */           Method clone = obj.getClass().getMethod("clone", new Class[0]);
/* 625 */           result = clone.invoke(obj, new Object[0]);
/* 626 */         } catch (NoSuchMethodException e) {
/* 627 */           throw new CloneFailedException("Cloneable type " + obj.getClass().getName() + " has no clone method", e);
/*     */         
/*     */         }
/* 630 */         catch (IllegalAccessException e) {
/* 631 */           throw new CloneFailedException("Cannot clone Cloneable type " + obj.getClass().getName(), e);
/*     */         }
/* 633 */         catch (InvocationTargetException e) {
/* 634 */           throw new CloneFailedException("Exception cloning Cloneable type " + obj.getClass().getName(), e.getCause());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 639 */       T checked = (T)result;
/* 640 */       return checked;
/*     */     } 
/*     */     
/* 643 */     return null;
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
/*     */   public static <T> T cloneIfPossible(T obj) {
/* 663 */     T clone = clone(obj);
/* 664 */     return (clone == null) ? obj : clone;
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
/*     */   public static class Null
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7092611880189329093L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private Object readResolve() {
/* 703 */       return ObjectUtils.NULL;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean CONST(boolean v) {
/* 746 */     return v;
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
/*     */   public static byte CONST(byte v) {
/* 765 */     return v;
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
/*     */   public static byte CONST_BYTE(int v) throws IllegalArgumentException {
/* 788 */     if (v < -128 || v > 127) {
/* 789 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + v + "]");
/*     */     }
/* 791 */     return (byte)v;
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
/*     */   public static char CONST(char v) {
/* 811 */     return v;
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
/*     */   public static short CONST(short v) {
/* 830 */     return v;
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
/*     */   public static short CONST_SHORT(int v) throws IllegalArgumentException {
/* 853 */     if (v < -32768 || v > 32767) {
/* 854 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + v + "]");
/*     */     }
/* 856 */     return (short)v;
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
/*     */   public static int CONST(int v) {
/* 877 */     return v;
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
/*     */   public static long CONST(long v) {
/* 896 */     return v;
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
/*     */   public static float CONST(float v) {
/* 915 */     return v;
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
/*     */   public static double CONST(double v) {
/* 934 */     return v;
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
/*     */   public static <T> T CONST(T v) {
/* 954 */     return v;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\ObjectUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */