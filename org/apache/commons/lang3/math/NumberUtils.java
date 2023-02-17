/*      */ package org.apache.commons.lang3.math;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NumberUtils
/*      */ {
/*   34 */   public static final Long LONG_ZERO = Long.valueOf(0L);
/*      */   
/*   36 */   public static final Long LONG_ONE = Long.valueOf(1L);
/*      */   
/*   38 */   public static final Long LONG_MINUS_ONE = Long.valueOf(-1L);
/*      */   
/*   40 */   public static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*      */   
/*   42 */   public static final Integer INTEGER_ONE = Integer.valueOf(1);
/*      */   
/*   44 */   public static final Integer INTEGER_MINUS_ONE = Integer.valueOf(-1);
/*      */   
/*   46 */   public static final Short SHORT_ZERO = Short.valueOf((short)0);
/*      */   
/*   48 */   public static final Short SHORT_ONE = Short.valueOf((short)1);
/*      */   
/*   50 */   public static final Short SHORT_MINUS_ONE = Short.valueOf((short)-1);
/*      */   
/*   52 */   public static final Byte BYTE_ZERO = Byte.valueOf((byte)0);
/*      */   
/*   54 */   public static final Byte BYTE_ONE = Byte.valueOf((byte)1);
/*      */   
/*   56 */   public static final Byte BYTE_MINUS_ONE = Byte.valueOf((byte)-1);
/*      */   
/*   58 */   public static final Double DOUBLE_ZERO = Double.valueOf(0.0D);
/*      */   
/*   60 */   public static final Double DOUBLE_ONE = Double.valueOf(1.0D);
/*      */   
/*   62 */   public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0D);
/*      */   
/*   64 */   public static final Float FLOAT_ZERO = Float.valueOf(0.0F);
/*      */   
/*   66 */   public static final Float FLOAT_ONE = Float.valueOf(1.0F);
/*      */   
/*   68 */   public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str) {
/*  100 */     return toInt(str, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str, int defaultValue) {
/*  121 */     if (str == null) {
/*  122 */       return defaultValue;
/*      */     }
/*      */     try {
/*  125 */       return Integer.parseInt(str);
/*  126 */     } catch (NumberFormatException nfe) {
/*  127 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toLong(String str) {
/*  149 */     return toLong(str, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toLong(String str, long defaultValue) {
/*  170 */     if (str == null) {
/*  171 */       return defaultValue;
/*      */     }
/*      */     try {
/*  174 */       return Long.parseLong(str);
/*  175 */     } catch (NumberFormatException nfe) {
/*  176 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float toFloat(String str) {
/*  199 */     return toFloat(str, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float toFloat(String str, float defaultValue) {
/*  222 */     if (str == null) {
/*  223 */       return defaultValue;
/*      */     }
/*      */     try {
/*  226 */       return Float.parseFloat(str);
/*  227 */     } catch (NumberFormatException nfe) {
/*  228 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(String str) {
/*  251 */     return toDouble(str, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(String str, double defaultValue) {
/*  274 */     if (str == null) {
/*  275 */       return defaultValue;
/*      */     }
/*      */     try {
/*  278 */       return Double.parseDouble(str);
/*  279 */     } catch (NumberFormatException nfe) {
/*  280 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte toByte(String str) {
/*  303 */     return toByte(str, (byte)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte toByte(String str, byte defaultValue) {
/*  324 */     if (str == null) {
/*  325 */       return defaultValue;
/*      */     }
/*      */     try {
/*  328 */       return Byte.parseByte(str);
/*  329 */     } catch (NumberFormatException nfe) {
/*  330 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short toShort(String str) {
/*  352 */     return toShort(str, (short)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short toShort(String str, short defaultValue) {
/*  373 */     if (str == null) {
/*  374 */       return defaultValue;
/*      */     }
/*      */     try {
/*  377 */       return Short.parseShort(str);
/*  378 */     } catch (NumberFormatException nfe) {
/*  379 */       return defaultValue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Number createNumber(String str) throws NumberFormatException {
/*      */     String mant, dec, exp;
/*  451 */     if (str == null) {
/*  452 */       return null;
/*      */     }
/*  454 */     if (StringUtils.isBlank(str)) {
/*  455 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*      */     
/*  458 */     String[] hex_prefixes = { "0x", "0X", "-0x", "-0X", "#", "-#" };
/*  459 */     int pfxLen = 0;
/*  460 */     for (String pfx : hex_prefixes) {
/*  461 */       if (str.startsWith(pfx)) {
/*  462 */         pfxLen += pfx.length();
/*      */         break;
/*      */       } 
/*      */     } 
/*  466 */     if (pfxLen > 0) {
/*  467 */       char firstSigDigit = Character.MIN_VALUE;
/*  468 */       for (int i = pfxLen; i < str.length(); ) {
/*  469 */         firstSigDigit = str.charAt(i);
/*  470 */         if (firstSigDigit == '0') {
/*  471 */           pfxLen++;
/*      */           
/*      */           i++;
/*      */         } 
/*      */       } 
/*  476 */       int hexDigits = str.length() - pfxLen;
/*  477 */       if (hexDigits > 16 || (hexDigits == 16 && firstSigDigit > '7')) {
/*  478 */         return createBigInteger(str);
/*      */       }
/*  480 */       if (hexDigits > 8 || (hexDigits == 8 && firstSigDigit > '7')) {
/*  481 */         return createLong(str);
/*      */       }
/*  483 */       return createInteger(str);
/*      */     } 
/*  485 */     char lastChar = str.charAt(str.length() - 1);
/*      */ 
/*      */ 
/*      */     
/*  489 */     int decPos = str.indexOf('.');
/*  490 */     int expPos = str.indexOf('e') + str.indexOf('E') + 1;
/*      */ 
/*      */ 
/*      */     
/*  494 */     int numDecimals = 0;
/*  495 */     if (decPos > -1) {
/*      */       
/*  497 */       if (expPos > -1) {
/*  498 */         if (expPos < decPos || expPos > str.length()) {
/*  499 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  501 */         dec = str.substring(decPos + 1, expPos);
/*      */       } else {
/*  503 */         dec = str.substring(decPos + 1);
/*      */       } 
/*  505 */       mant = str.substring(0, decPos);
/*  506 */       numDecimals = dec.length();
/*      */     } else {
/*  508 */       if (expPos > -1) {
/*  509 */         if (expPos > str.length()) {
/*  510 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  512 */         mant = str.substring(0, expPos);
/*      */       } else {
/*  514 */         mant = str;
/*      */       } 
/*  516 */       dec = null;
/*      */     } 
/*  518 */     if (!Character.isDigit(lastChar) && lastChar != '.') {
/*  519 */       if (expPos > -1 && expPos < str.length() - 1) {
/*  520 */         exp = str.substring(expPos + 1, str.length() - 1);
/*      */       } else {
/*  522 */         exp = null;
/*      */       } 
/*      */       
/*  525 */       String numeric = str.substring(0, str.length() - 1);
/*  526 */       boolean bool = (isAllZeros(mant) && isAllZeros(exp));
/*  527 */       switch (lastChar) {
/*      */         case 'L':
/*      */         case 'l':
/*  530 */           if (dec == null && exp == null && ((numeric.charAt(0) == '-' && isDigits(numeric.substring(1))) || isDigits(numeric))) {
/*      */             
/*      */             try {
/*      */               
/*  534 */               return createLong(numeric);
/*  535 */             } catch (NumberFormatException nfe) {
/*      */ 
/*      */               
/*  538 */               return createBigInteger(numeric);
/*      */             } 
/*      */           }
/*  541 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         case 'F':
/*      */         case 'f':
/*      */           try {
/*  545 */             Float f = createFloat(numeric);
/*  546 */             if (!f.isInfinite() && (f.floatValue() != 0.0F || bool))
/*      */             {
/*      */               
/*  549 */               return f;
/*      */             }
/*      */           }
/*  552 */           catch (NumberFormatException nfe) {}
/*      */ 
/*      */ 
/*      */         
/*      */         case 'D':
/*      */         case 'd':
/*      */           try {
/*  559 */             Double d = createDouble(numeric);
/*  560 */             if (!d.isInfinite() && (d.floatValue() != 0.0D || bool)) {
/*  561 */               return d;
/*      */             }
/*  563 */           } catch (NumberFormatException nfe) {}
/*      */ 
/*      */           
/*      */           try {
/*  567 */             return createBigDecimal(numeric);
/*  568 */           } catch (NumberFormatException e) {
/*      */             break;
/*      */           } 
/*      */       } 
/*      */       
/*  573 */       throw new NumberFormatException(str + " is not a valid number.");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     if (expPos > -1 && expPos < str.length() - 1) {
/*  580 */       exp = str.substring(expPos + 1, str.length());
/*      */     } else {
/*  582 */       exp = null;
/*      */     } 
/*  584 */     if (dec == null && exp == null) {
/*      */       
/*      */       try {
/*  587 */         return createInteger(str);
/*  588 */       } catch (NumberFormatException nfe) {
/*      */ 
/*      */         
/*      */         try {
/*  592 */           return createLong(str);
/*  593 */         } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */           
/*  596 */           return createBigInteger(str);
/*      */         } 
/*      */       } 
/*      */     }
/*  600 */     boolean allZeros = (isAllZeros(mant) && isAllZeros(exp));
/*      */     try {
/*  602 */       if (numDecimals <= 7) {
/*  603 */         Float f = createFloat(str);
/*  604 */         if (!f.isInfinite() && (f.floatValue() != 0.0F || allZeros)) {
/*  605 */           return f;
/*      */         }
/*      */       } 
/*  608 */     } catch (NumberFormatException nfe) {}
/*      */ 
/*      */     
/*      */     try {
/*  612 */       if (numDecimals <= 16) {
/*  613 */         Double d = createDouble(str);
/*  614 */         if (!d.isInfinite() && (d.doubleValue() != 0.0D || allZeros)) {
/*  615 */           return d;
/*      */         }
/*      */       } 
/*  618 */     } catch (NumberFormatException nfe) {}
/*      */ 
/*      */ 
/*      */     
/*  622 */     return createBigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAllZeros(String str) {
/*  634 */     if (str == null) {
/*  635 */       return true;
/*      */     }
/*  637 */     for (int i = str.length() - 1; i >= 0; i--) {
/*  638 */       if (str.charAt(i) != '0') {
/*  639 */         return false;
/*      */       }
/*      */     } 
/*  642 */     return (str.length() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Float createFloat(String str) {
/*  656 */     if (str == null) {
/*  657 */       return null;
/*      */     }
/*  659 */     return Float.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Double createDouble(String str) {
/*  672 */     if (str == null) {
/*  673 */       return null;
/*      */     }
/*  675 */     return Double.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer createInteger(String str) {
/*  690 */     if (str == null) {
/*  691 */       return null;
/*      */     }
/*      */     
/*  694 */     return Integer.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Long createLong(String str) {
/*  709 */     if (str == null) {
/*  710 */       return null;
/*      */     }
/*  712 */     return Long.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger createBigInteger(String str) {
/*  726 */     if (str == null) {
/*  727 */       return null;
/*      */     }
/*  729 */     int pos = 0;
/*  730 */     int radix = 10;
/*  731 */     boolean negate = false;
/*  732 */     if (str.startsWith("-")) {
/*  733 */       negate = true;
/*  734 */       pos = 1;
/*      */     } 
/*  736 */     if (str.startsWith("0x", pos) || str.startsWith("0x", pos)) {
/*  737 */       radix = 16;
/*  738 */       pos += 2;
/*  739 */     } else if (str.startsWith("#", pos)) {
/*  740 */       radix = 16;
/*  741 */       pos++;
/*  742 */     } else if (str.startsWith("0", pos) && str.length() > pos + 1) {
/*  743 */       radix = 8;
/*  744 */       pos++;
/*      */     } 
/*      */     
/*  747 */     BigInteger value = new BigInteger(str.substring(pos), radix);
/*  748 */     return negate ? value.negate() : value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal createBigDecimal(String str) {
/*  761 */     if (str == null) {
/*  762 */       return null;
/*      */     }
/*      */     
/*  765 */     if (StringUtils.isBlank(str)) {
/*  766 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*  768 */     if (str.trim().startsWith("--"))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  773 */       throw new NumberFormatException(str + " is not a valid number.");
/*      */     }
/*  775 */     return new BigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long[] array) {
/*  790 */     validateArray(array);
/*      */ 
/*      */     
/*  793 */     long min = array[0];
/*  794 */     for (int i = 1; i < array.length; i++) {
/*  795 */       if (array[i] < min) {
/*  796 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  800 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int[] array) {
/*  813 */     validateArray(array);
/*      */ 
/*      */     
/*  816 */     int min = array[0];
/*  817 */     for (int j = 1; j < array.length; j++) {
/*  818 */       if (array[j] < min) {
/*  819 */         min = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  823 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short[] array) {
/*  836 */     validateArray(array);
/*      */ 
/*      */     
/*  839 */     short min = array[0];
/*  840 */     for (int i = 1; i < array.length; i++) {
/*  841 */       if (array[i] < min) {
/*  842 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  846 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte[] array) {
/*  859 */     validateArray(array);
/*      */ 
/*      */     
/*  862 */     byte min = array[0];
/*  863 */     for (int i = 1; i < array.length; i++) {
/*  864 */       if (array[i] < min) {
/*  865 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  869 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double[] array) {
/*  883 */     validateArray(array);
/*      */ 
/*      */     
/*  886 */     double min = array[0];
/*  887 */     for (int i = 1; i < array.length; i++) {
/*  888 */       if (Double.isNaN(array[i])) {
/*  889 */         return Double.NaN;
/*      */       }
/*  891 */       if (array[i] < min) {
/*  892 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  896 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float[] array) {
/*  910 */     validateArray(array);
/*      */ 
/*      */     
/*  913 */     float min = array[0];
/*  914 */     for (int i = 1; i < array.length; i++) {
/*  915 */       if (Float.isNaN(array[i])) {
/*  916 */         return Float.NaN;
/*      */       }
/*  918 */       if (array[i] < min) {
/*  919 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  923 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long[] array) {
/*  938 */     validateArray(array);
/*      */ 
/*      */     
/*  941 */     long max = array[0];
/*  942 */     for (int j = 1; j < array.length; j++) {
/*  943 */       if (array[j] > max) {
/*  944 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  948 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int[] array) {
/*  961 */     validateArray(array);
/*      */ 
/*      */     
/*  964 */     int max = array[0];
/*  965 */     for (int j = 1; j < array.length; j++) {
/*  966 */       if (array[j] > max) {
/*  967 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  971 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short[] array) {
/*  984 */     validateArray(array);
/*      */ 
/*      */     
/*  987 */     short max = array[0];
/*  988 */     for (int i = 1; i < array.length; i++) {
/*  989 */       if (array[i] > max) {
/*  990 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  994 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte[] array) {
/* 1007 */     validateArray(array);
/*      */ 
/*      */     
/* 1010 */     byte max = array[0];
/* 1011 */     for (int i = 1; i < array.length; i++) {
/* 1012 */       if (array[i] > max) {
/* 1013 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1017 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double[] array) {
/* 1031 */     validateArray(array);
/*      */ 
/*      */     
/* 1034 */     double max = array[0];
/* 1035 */     for (int j = 1; j < array.length; j++) {
/* 1036 */       if (Double.isNaN(array[j])) {
/* 1037 */         return Double.NaN;
/*      */       }
/* 1039 */       if (array[j] > max) {
/* 1040 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1044 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float[] array) {
/* 1058 */     validateArray(array);
/*      */ 
/*      */     
/* 1061 */     float max = array[0];
/* 1062 */     for (int j = 1; j < array.length; j++) {
/* 1063 */       if (Float.isNaN(array[j])) {
/* 1064 */         return Float.NaN;
/*      */       }
/* 1066 */       if (array[j] > max) {
/* 1067 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1071 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateArray(Object array) {
/* 1081 */     if (array == null)
/* 1082 */       throw new IllegalArgumentException("The Array must not be null"); 
/* 1083 */     if (Array.getLength(array) == 0) {
/* 1084 */       throw new IllegalArgumentException("Array cannot be empty.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long a, long b, long c) {
/* 1099 */     if (b < a) {
/* 1100 */       a = b;
/*      */     }
/* 1102 */     if (c < a) {
/* 1103 */       a = c;
/*      */     }
/* 1105 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b, int c) {
/* 1117 */     if (b < a) {
/* 1118 */       a = b;
/*      */     }
/* 1120 */     if (c < a) {
/* 1121 */       a = c;
/*      */     }
/* 1123 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short a, short b, short c) {
/* 1135 */     if (b < a) {
/* 1136 */       a = b;
/*      */     }
/* 1138 */     if (c < a) {
/* 1139 */       a = c;
/*      */     }
/* 1141 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte a, byte b, byte c) {
/* 1153 */     if (b < a) {
/* 1154 */       a = b;
/*      */     }
/* 1156 */     if (c < a) {
/* 1157 */       a = c;
/*      */     }
/* 1159 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b, double c) {
/* 1175 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b, float c) {
/* 1191 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long a, long b, long c) {
/* 1205 */     if (b > a) {
/* 1206 */       a = b;
/*      */     }
/* 1208 */     if (c > a) {
/* 1209 */       a = c;
/*      */     }
/* 1211 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b, int c) {
/* 1223 */     if (b > a) {
/* 1224 */       a = b;
/*      */     }
/* 1226 */     if (c > a) {
/* 1227 */       a = c;
/*      */     }
/* 1229 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short a, short b, short c) {
/* 1241 */     if (b > a) {
/* 1242 */       a = b;
/*      */     }
/* 1244 */     if (c > a) {
/* 1245 */       a = c;
/*      */     }
/* 1247 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte a, byte b, byte c) {
/* 1259 */     if (b > a) {
/* 1260 */       a = b;
/*      */     }
/* 1262 */     if (c > a) {
/* 1263 */       a = c;
/*      */     }
/* 1265 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b, double c) {
/* 1281 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b, float c) {
/* 1297 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDigits(String str) {
/* 1312 */     if (StringUtils.isEmpty(str)) {
/* 1313 */       return false;
/*      */     }
/* 1315 */     for (int i = 0; i < str.length(); i++) {
/* 1316 */       if (!Character.isDigit(str.charAt(i))) {
/* 1317 */         return false;
/*      */       }
/*      */     } 
/* 1320 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNumber(String str) {
/* 1338 */     if (StringUtils.isEmpty(str)) {
/* 1339 */       return false;
/*      */     }
/* 1341 */     char[] chars = str.toCharArray();
/* 1342 */     int sz = chars.length;
/* 1343 */     boolean hasExp = false;
/* 1344 */     boolean hasDecPoint = false;
/* 1345 */     boolean allowSigns = false;
/* 1346 */     boolean foundDigit = false;
/*      */     
/* 1348 */     int start = (chars[0] == '-') ? 1 : 0;
/* 1349 */     if (sz > start + 1 && chars[start] == '0') {
/* 1350 */       if (chars[start + 1] == 'x' || chars[start + 1] == 'X') {
/*      */ 
/*      */ 
/*      */         
/* 1354 */         int j = start + 2;
/* 1355 */         if (j == sz) {
/* 1356 */           return false;
/*      */         }
/*      */         
/* 1359 */         for (; j < chars.length; j++) {
/* 1360 */           if ((chars[j] < '0' || chars[j] > '9') && (chars[j] < 'a' || chars[j] > 'f') && (chars[j] < 'A' || chars[j] > 'F'))
/*      */           {
/*      */             
/* 1363 */             return false;
/*      */           }
/*      */         } 
/* 1366 */         return true;
/* 1367 */       }  if (Character.isDigit(chars[start + 1])) {
/*      */         
/* 1369 */         int j = start + 1;
/* 1370 */         for (; j < chars.length; j++) {
/* 1371 */           if (chars[j] < '0' || chars[j] > '7') {
/* 1372 */             return false;
/*      */           }
/*      */         } 
/* 1375 */         return true;
/*      */       } 
/*      */     } 
/* 1378 */     sz--;
/*      */     
/* 1380 */     int i = start;
/*      */ 
/*      */     
/* 1383 */     while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
/* 1384 */       if (chars[i] >= '0' && chars[i] <= '9') {
/* 1385 */         foundDigit = true;
/* 1386 */         allowSigns = false;
/*      */       }
/* 1388 */       else if (chars[i] == '.') {
/* 1389 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1391 */           return false;
/*      */         }
/* 1393 */         hasDecPoint = true;
/* 1394 */       } else if (chars[i] == 'e' || chars[i] == 'E') {
/*      */         
/* 1396 */         if (hasExp)
/*      */         {
/* 1398 */           return false;
/*      */         }
/* 1400 */         if (!foundDigit) {
/* 1401 */           return false;
/*      */         }
/* 1403 */         hasExp = true;
/* 1404 */         allowSigns = true;
/* 1405 */       } else if (chars[i] == '+' || chars[i] == '-') {
/* 1406 */         if (!allowSigns) {
/* 1407 */           return false;
/*      */         }
/* 1409 */         allowSigns = false;
/* 1410 */         foundDigit = false;
/*      */       } else {
/* 1412 */         return false;
/*      */       } 
/* 1414 */       i++;
/*      */     } 
/* 1416 */     if (i < chars.length) {
/* 1417 */       if (chars[i] >= '0' && chars[i] <= '9')
/*      */       {
/* 1419 */         return true;
/*      */       }
/* 1421 */       if (chars[i] == 'e' || chars[i] == 'E')
/*      */       {
/* 1423 */         return false;
/*      */       }
/* 1425 */       if (chars[i] == '.') {
/* 1426 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1428 */           return false;
/*      */         }
/*      */         
/* 1431 */         return foundDigit;
/*      */       } 
/* 1433 */       if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F'))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1438 */         return foundDigit;
/*      */       }
/* 1440 */       if (chars[i] == 'l' || chars[i] == 'L')
/*      */       {
/*      */         
/* 1443 */         return (foundDigit && !hasExp && !hasDecPoint);
/*      */       }
/*      */       
/* 1446 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1450 */     return (!allowSigns && foundDigit);
/*      */   }
/*      */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\math\NumberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */