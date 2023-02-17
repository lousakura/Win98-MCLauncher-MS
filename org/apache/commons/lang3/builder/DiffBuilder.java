/*     */ package org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiffBuilder
/*     */   implements Builder<DiffResult>
/*     */ {
/*     */   private final List<Diff<?>> diffs;
/*     */   private final boolean objectsTriviallyEqual;
/*     */   private final Object left;
/*     */   private final Object right;
/*     */   private final ToStringStyle style;
/*     */   
/*     */   public DiffBuilder(Object lhs, Object rhs, ToStringStyle style) {
/*  98 */     if (lhs == null) {
/*  99 */       throw new IllegalArgumentException("lhs cannot be null");
/*     */     }
/* 101 */     if (rhs == null) {
/* 102 */       throw new IllegalArgumentException("rhs cannot be null");
/*     */     }
/*     */     
/* 105 */     this.diffs = new ArrayList<Diff<?>>();
/* 106 */     this.left = lhs;
/* 107 */     this.right = rhs;
/* 108 */     this.style = style;
/*     */ 
/*     */     
/* 111 */     this.objectsTriviallyEqual = (lhs == rhs || lhs.equals(rhs));
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
/*     */   public DiffBuilder append(String fieldName, final boolean lhs, final boolean rhs) {
/* 131 */     if (fieldName == null) {
/* 132 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 135 */     if (this.objectsTriviallyEqual) {
/* 136 */       return this;
/*     */     }
/* 138 */     if (lhs != rhs) {
/* 139 */       this.diffs.add(new Diff<Boolean>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Boolean getLeft() {
/* 144 */               return Boolean.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Boolean getRight() {
/* 149 */               return Boolean.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 153 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final boolean[] lhs, final boolean[] rhs) {
/* 173 */     if (fieldName == null) {
/* 174 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/* 176 */     if (this.objectsTriviallyEqual) {
/* 177 */       return this;
/*     */     }
/* 179 */     if (!Arrays.equals(lhs, rhs)) {
/* 180 */       this.diffs.add(new Diff<Boolean[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Boolean[] getLeft() {
/* 185 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Boolean[] getRight() {
/* 190 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 194 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final byte lhs, final byte rhs) {
/* 214 */     if (fieldName == null) {
/* 215 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/* 217 */     if (this.objectsTriviallyEqual) {
/* 218 */       return this;
/*     */     }
/* 220 */     if (lhs != rhs) {
/* 221 */       this.diffs.add(new Diff<Byte>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Byte getLeft() {
/* 226 */               return Byte.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Byte getRight() {
/* 231 */               return Byte.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 235 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final byte[] lhs, final byte[] rhs) {
/* 255 */     if (fieldName == null) {
/* 256 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 259 */     if (this.objectsTriviallyEqual) {
/* 260 */       return this;
/*     */     }
/* 262 */     if (!Arrays.equals(lhs, rhs)) {
/* 263 */       this.diffs.add(new Diff<Byte[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Byte[] getLeft() {
/* 268 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Byte[] getRight() {
/* 273 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 277 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final char lhs, final char rhs) {
/* 297 */     if (fieldName == null) {
/* 298 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 301 */     if (this.objectsTriviallyEqual) {
/* 302 */       return this;
/*     */     }
/* 304 */     if (lhs != rhs) {
/* 305 */       this.diffs.add(new Diff<Character>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Character getLeft() {
/* 310 */               return Character.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Character getRight() {
/* 315 */               return Character.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 319 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final char[] lhs, final char[] rhs) {
/* 339 */     if (fieldName == null) {
/* 340 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 343 */     if (this.objectsTriviallyEqual) {
/* 344 */       return this;
/*     */     }
/* 346 */     if (!Arrays.equals(lhs, rhs)) {
/* 347 */       this.diffs.add(new Diff<Character[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Character[] getLeft() {
/* 352 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Character[] getRight() {
/* 357 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 361 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final double lhs, final double rhs) {
/* 381 */     if (fieldName == null) {
/* 382 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 385 */     if (this.objectsTriviallyEqual) {
/* 386 */       return this;
/*     */     }
/* 388 */     if (Double.doubleToLongBits(lhs) != Double.doubleToLongBits(rhs)) {
/* 389 */       this.diffs.add(new Diff<Double>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Double getLeft() {
/* 394 */               return Double.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Double getRight() {
/* 399 */               return Double.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 403 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final double[] lhs, final double[] rhs) {
/* 423 */     if (fieldName == null) {
/* 424 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 427 */     if (this.objectsTriviallyEqual) {
/* 428 */       return this;
/*     */     }
/* 430 */     if (!Arrays.equals(lhs, rhs)) {
/* 431 */       this.diffs.add(new Diff<Double[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Double[] getLeft() {
/* 436 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Double[] getRight() {
/* 441 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 445 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final float lhs, final float rhs) {
/* 465 */     if (fieldName == null) {
/* 466 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 469 */     if (this.objectsTriviallyEqual) {
/* 470 */       return this;
/*     */     }
/* 472 */     if (Float.floatToIntBits(lhs) != Float.floatToIntBits(rhs)) {
/* 473 */       this.diffs.add(new Diff<Float>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Float getLeft() {
/* 478 */               return Float.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Float getRight() {
/* 483 */               return Float.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 487 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final float[] lhs, final float[] rhs) {
/* 507 */     if (fieldName == null) {
/* 508 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 511 */     if (this.objectsTriviallyEqual) {
/* 512 */       return this;
/*     */     }
/* 514 */     if (!Arrays.equals(lhs, rhs)) {
/* 515 */       this.diffs.add(new Diff<Float[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Float[] getLeft() {
/* 520 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Float[] getRight() {
/* 525 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 529 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final int lhs, final int rhs) {
/* 549 */     if (fieldName == null) {
/* 550 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 553 */     if (this.objectsTriviallyEqual) {
/* 554 */       return this;
/*     */     }
/* 556 */     if (lhs != rhs) {
/* 557 */       this.diffs.add(new Diff<Integer>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Integer getLeft() {
/* 562 */               return Integer.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Integer getRight() {
/* 567 */               return Integer.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 571 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final int[] lhs, final int[] rhs) {
/* 591 */     if (fieldName == null) {
/* 592 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 595 */     if (this.objectsTriviallyEqual) {
/* 596 */       return this;
/*     */     }
/* 598 */     if (!Arrays.equals(lhs, rhs)) {
/* 599 */       this.diffs.add(new Diff<Integer[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Integer[] getLeft() {
/* 604 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Integer[] getRight() {
/* 609 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 613 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final long lhs, final long rhs) {
/* 633 */     if (fieldName == null) {
/* 634 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 637 */     if (this.objectsTriviallyEqual) {
/* 638 */       return this;
/*     */     }
/* 640 */     if (lhs != rhs) {
/* 641 */       this.diffs.add(new Diff<Long>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Long getLeft() {
/* 646 */               return Long.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Long getRight() {
/* 651 */               return Long.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 655 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final long[] lhs, final long[] rhs) {
/* 675 */     if (fieldName == null) {
/* 676 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 679 */     if (this.objectsTriviallyEqual) {
/* 680 */       return this;
/*     */     }
/* 682 */     if (!Arrays.equals(lhs, rhs)) {
/* 683 */       this.diffs.add(new Diff<Long[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Long[] getLeft() {
/* 688 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Long[] getRight() {
/* 693 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 697 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final short lhs, final short rhs) {
/* 717 */     if (fieldName == null) {
/* 718 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 721 */     if (this.objectsTriviallyEqual) {
/* 722 */       return this;
/*     */     }
/* 724 */     if (lhs != rhs) {
/* 725 */       this.diffs.add(new Diff<Short>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Short getLeft() {
/* 730 */               return Short.valueOf(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Short getRight() {
/* 735 */               return Short.valueOf(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 739 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final short[] lhs, final short[] rhs) {
/* 759 */     if (fieldName == null) {
/* 760 */       throw new IllegalArgumentException("Field name cannot be null");
/*     */     }
/*     */     
/* 763 */     if (this.objectsTriviallyEqual) {
/* 764 */       return this;
/*     */     }
/* 766 */     if (!Arrays.equals(lhs, rhs)) {
/* 767 */       this.diffs.add(new Diff<Short[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Short[] getLeft() {
/* 772 */               return ArrayUtils.toObject(lhs);
/*     */             }
/*     */ 
/*     */             
/*     */             public Short[] getRight() {
/* 777 */               return ArrayUtils.toObject(rhs);
/*     */             }
/*     */           });
/*     */     }
/* 781 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final Object lhs, final Object rhs) {
/*     */     Object objectToTest;
/* 800 */     if (this.objectsTriviallyEqual) {
/* 801 */       return this;
/*     */     }
/* 803 */     if (lhs == rhs) {
/* 804 */       return this;
/*     */     }
/*     */ 
/*     */     
/* 808 */     if (lhs != null) {
/* 809 */       objectToTest = lhs;
/*     */     } else {
/*     */       
/* 812 */       objectToTest = rhs;
/*     */     } 
/*     */     
/* 815 */     if (objectToTest.getClass().isArray()) {
/* 816 */       if (objectToTest instanceof boolean[]) {
/* 817 */         return append(fieldName, (boolean[])lhs, (boolean[])rhs);
/*     */       }
/* 819 */       if (objectToTest instanceof byte[]) {
/* 820 */         return append(fieldName, (byte[])lhs, (byte[])rhs);
/*     */       }
/* 822 */       if (objectToTest instanceof char[]) {
/* 823 */         return append(fieldName, (char[])lhs, (char[])rhs);
/*     */       }
/* 825 */       if (objectToTest instanceof double[]) {
/* 826 */         return append(fieldName, (double[])lhs, (double[])rhs);
/*     */       }
/* 828 */       if (objectToTest instanceof float[]) {
/* 829 */         return append(fieldName, (float[])lhs, (float[])rhs);
/*     */       }
/* 831 */       if (objectToTest instanceof int[]) {
/* 832 */         return append(fieldName, (int[])lhs, (int[])rhs);
/*     */       }
/* 834 */       if (objectToTest instanceof long[]) {
/* 835 */         return append(fieldName, (long[])lhs, (long[])rhs);
/*     */       }
/* 837 */       if (objectToTest instanceof short[]) {
/* 838 */         return append(fieldName, (short[])lhs, (short[])rhs);
/*     */       }
/*     */       
/* 841 */       return append(fieldName, (Object[])lhs, (Object[])rhs);
/*     */     } 
/*     */ 
/*     */     
/* 845 */     this.diffs.add(new Diff(fieldName)
/*     */         {
/*     */           private static final long serialVersionUID = 1L;
/*     */           
/*     */           public Object getLeft() {
/* 850 */             return lhs;
/*     */           }
/*     */ 
/*     */           
/*     */           public Object getRight() {
/* 855 */             return rhs;
/*     */           }
/*     */         });
/*     */     
/* 859 */     return this;
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
/*     */   public DiffBuilder append(String fieldName, final Object[] lhs, final Object[] rhs) {
/* 877 */     if (this.objectsTriviallyEqual) {
/* 878 */       return this;
/*     */     }
/*     */     
/* 881 */     if (!Arrays.equals(lhs, rhs)) {
/* 882 */       this.diffs.add(new Diff<Object[]>(fieldName)
/*     */           {
/*     */             private static final long serialVersionUID = 1L;
/*     */             
/*     */             public Object[] getLeft() {
/* 887 */               return lhs;
/*     */             }
/*     */ 
/*     */             
/*     */             public Object[] getRight() {
/* 892 */               return rhs;
/*     */             }
/*     */           });
/*     */     }
/*     */     
/* 897 */     return this;
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
/*     */   public DiffResult build() {
/* 911 */     return new DiffResult(this.left, this.right, this.diffs, this.style);
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\builder\DiffBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */