/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.UUID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Conversion
/*      */ {
/*      */   public static int hexDigitToInt(char hexDigit) {
/*   81 */     int digit = Character.digit(hexDigit, 16);
/*   82 */     if (digit < 0) {
/*   83 */       throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */     }
/*      */ 
/*      */     
/*   87 */     return digit;
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
/*      */   public static int hexDigitMsb0ToInt(char hexDigit) {
/*  103 */     switch (hexDigit) {
/*      */       case '0':
/*  105 */         return 0;
/*      */       case '1':
/*  107 */         return 8;
/*      */       case '2':
/*  109 */         return 4;
/*      */       case '3':
/*  111 */         return 12;
/*      */       case '4':
/*  113 */         return 2;
/*      */       case '5':
/*  115 */         return 10;
/*      */       case '6':
/*  117 */         return 6;
/*      */       case '7':
/*  119 */         return 14;
/*      */       case '8':
/*  121 */         return 1;
/*      */       case '9':
/*  123 */         return 9;
/*      */       case 'A':
/*      */       case 'a':
/*  126 */         return 5;
/*      */       case 'B':
/*      */       case 'b':
/*  129 */         return 13;
/*      */       case 'C':
/*      */       case 'c':
/*  132 */         return 3;
/*      */       case 'D':
/*      */       case 'd':
/*  135 */         return 11;
/*      */       case 'E':
/*      */       case 'e':
/*  138 */         return 7;
/*      */       case 'F':
/*      */       case 'f':
/*  141 */         return 15;
/*      */     } 
/*  143 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
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
/*      */   public static boolean[] hexDigitToBinary(char hexDigit) {
/*  163 */     switch (hexDigit) {
/*      */       case '0':
/*  165 */         return new boolean[] { false, false, false, false };
/*      */       case '1':
/*  167 */         return new boolean[] { true, false, false, false };
/*      */       case '2':
/*  169 */         return new boolean[] { false, true, false, false };
/*      */       case '3':
/*  171 */         return new boolean[] { true, true, false, false };
/*      */       case '4':
/*  173 */         return new boolean[] { false, false, true, false };
/*      */       case '5':
/*  175 */         return new boolean[] { true, false, true, false };
/*      */       case '6':
/*  177 */         return new boolean[] { false, true, true, false };
/*      */       case '7':
/*  179 */         return new boolean[] { true, true, true, false };
/*      */       case '8':
/*  181 */         return new boolean[] { false, false, false, true };
/*      */       case '9':
/*  183 */         return new boolean[] { true, false, false, true };
/*      */       case 'A':
/*      */       case 'a':
/*  186 */         return new boolean[] { false, true, false, true };
/*      */       case 'B':
/*      */       case 'b':
/*  189 */         return new boolean[] { true, true, false, true };
/*      */       case 'C':
/*      */       case 'c':
/*  192 */         return new boolean[] { false, false, true, true };
/*      */       case 'D':
/*      */       case 'd':
/*  195 */         return new boolean[] { true, false, true, true };
/*      */       case 'E':
/*      */       case 'e':
/*  198 */         return new boolean[] { false, true, true, true };
/*      */       case 'F':
/*      */       case 'f':
/*  201 */         return new boolean[] { true, true, true, true };
/*      */     } 
/*  203 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
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
/*      */   public static boolean[] hexDigitMsb0ToBinary(char hexDigit) {
/*  223 */     switch (hexDigit) {
/*      */       case '0':
/*  225 */         return new boolean[] { false, false, false, false };
/*      */       case '1':
/*  227 */         return new boolean[] { false, false, false, true };
/*      */       case '2':
/*  229 */         return new boolean[] { false, false, true, false };
/*      */       case '3':
/*  231 */         return new boolean[] { false, false, true, true };
/*      */       case '4':
/*  233 */         return new boolean[] { false, true, false, false };
/*      */       case '5':
/*  235 */         return new boolean[] { false, true, false, true };
/*      */       case '6':
/*  237 */         return new boolean[] { false, true, true, false };
/*      */       case '7':
/*  239 */         return new boolean[] { false, true, true, true };
/*      */       case '8':
/*  241 */         return new boolean[] { true, false, false, false };
/*      */       case '9':
/*  243 */         return new boolean[] { true, false, false, true };
/*      */       case 'A':
/*      */       case 'a':
/*  246 */         return new boolean[] { true, false, true, false };
/*      */       case 'B':
/*      */       case 'b':
/*  249 */         return new boolean[] { true, false, true, true };
/*      */       case 'C':
/*      */       case 'c':
/*  252 */         return new boolean[] { true, true, false, false };
/*      */       case 'D':
/*      */       case 'd':
/*  255 */         return new boolean[] { true, true, false, true };
/*      */       case 'E':
/*      */       case 'e':
/*  258 */         return new boolean[] { true, true, true, false };
/*      */       case 'F':
/*      */       case 'f':
/*  261 */         return new boolean[] { true, true, true, true };
/*      */     } 
/*  263 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
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
/*      */   public static char binaryToHexDigit(boolean[] src) {
/*  284 */     return binaryToHexDigit(src, 0);
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
/*      */   public static char binaryToHexDigit(boolean[] src, int srcPos) {
/*  303 */     if (src.length == 0) {
/*  304 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  306 */     if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  307 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  308 */         if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  309 */           if (src[srcPos]) {
/*  310 */             return 'f';
/*      */           }
/*  312 */           return 'e';
/*      */         } 
/*      */         
/*  315 */         if (src[srcPos]) {
/*  316 */           return 'd';
/*      */         }
/*  318 */         return 'c';
/*      */       } 
/*      */ 
/*      */       
/*  322 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  323 */         if (src[srcPos]) {
/*  324 */           return 'b';
/*      */         }
/*  326 */         return 'a';
/*      */       } 
/*      */       
/*  329 */       if (src[srcPos]) {
/*  330 */         return '9';
/*      */       }
/*  332 */       return '8';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  337 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  338 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  339 */         if (src[srcPos]) {
/*  340 */           return '7';
/*      */         }
/*  342 */         return '6';
/*      */       } 
/*      */       
/*  345 */       if (src[srcPos]) {
/*  346 */         return '5';
/*      */       }
/*  348 */       return '4';
/*      */     } 
/*      */ 
/*      */     
/*  352 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  353 */       if (src[srcPos]) {
/*  354 */         return '3';
/*      */       }
/*  356 */       return '2';
/*      */     } 
/*      */     
/*  359 */     if (src[srcPos]) {
/*  360 */       return '1';
/*      */     }
/*  362 */     return '0';
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
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src) {
/*  385 */     return binaryToHexDigitMsb0_4bits(src, 0);
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
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src, int srcPos) {
/*  406 */     if (src.length > 8) {
/*  407 */       throw new IllegalArgumentException("src.length>8: src.length=" + src.length);
/*      */     }
/*  409 */     if (src.length - srcPos < 4) {
/*  410 */       throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + src.length + ", srcPos=" + srcPos);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  415 */     if (src[srcPos + 3]) {
/*  416 */       if (src[srcPos + 2]) {
/*  417 */         if (src[srcPos + 1]) {
/*  418 */           if (src[srcPos]) {
/*  419 */             return 'f';
/*      */           }
/*  421 */           return '7';
/*      */         } 
/*      */         
/*  424 */         if (src[srcPos]) {
/*  425 */           return 'b';
/*      */         }
/*  427 */         return '3';
/*      */       } 
/*      */ 
/*      */       
/*  431 */       if (src[srcPos + 1]) {
/*  432 */         if (src[srcPos]) {
/*  433 */           return 'd';
/*      */         }
/*  435 */         return '5';
/*      */       } 
/*      */       
/*  438 */       if (src[srcPos]) {
/*  439 */         return '9';
/*      */       }
/*  441 */       return '1';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  446 */     if (src[srcPos + 2]) {
/*  447 */       if (src[srcPos + 1]) {
/*  448 */         if (src[srcPos]) {
/*  449 */           return 'e';
/*      */         }
/*  451 */         return '6';
/*      */       } 
/*      */       
/*  454 */       if (src[srcPos]) {
/*  455 */         return 'a';
/*      */       }
/*  457 */       return '2';
/*      */     } 
/*      */ 
/*      */     
/*  461 */     if (src[srcPos + 1]) {
/*  462 */       if (src[srcPos]) {
/*  463 */         return 'c';
/*      */       }
/*  465 */       return '4';
/*      */     } 
/*      */     
/*  468 */     if (src[srcPos]) {
/*  469 */       return '8';
/*      */     }
/*  471 */     return '0';
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
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src) {
/*  494 */     return binaryBeMsb0ToHexDigit(src, 0);
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
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src, int srcPos) {
/*  514 */     if (src.length == 0) {
/*  515 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  517 */     int beSrcPos = src.length - 1 - srcPos;
/*  518 */     int srcLen = Math.min(4, beSrcPos + 1);
/*  519 */     boolean[] paddedSrc = new boolean[4];
/*  520 */     System.arraycopy(src, beSrcPos + 1 - srcLen, paddedSrc, 4 - srcLen, srcLen);
/*  521 */     src = paddedSrc;
/*  522 */     srcPos = 0;
/*  523 */     if (src[srcPos]) {
/*  524 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  525 */         if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  526 */           if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  527 */             return 'f';
/*      */           }
/*  529 */           return 'e';
/*      */         } 
/*      */         
/*  532 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  533 */           return 'd';
/*      */         }
/*  535 */         return 'c';
/*      */       } 
/*      */ 
/*      */       
/*  539 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  540 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  541 */           return 'b';
/*      */         }
/*  543 */         return 'a';
/*      */       } 
/*      */       
/*  546 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  547 */         return '9';
/*      */       }
/*  549 */       return '8';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  554 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  555 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  556 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  557 */           return '7';
/*      */         }
/*  559 */         return '6';
/*      */       } 
/*      */       
/*  562 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  563 */         return '5';
/*      */       }
/*  565 */       return '4';
/*      */     } 
/*      */ 
/*      */     
/*  569 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  570 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  571 */         return '3';
/*      */       }
/*  573 */       return '2';
/*      */     } 
/*      */     
/*  576 */     if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  577 */       return '1';
/*      */     }
/*  579 */     return '0';
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
/*      */   public static char intToHexDigit(int nibble) {
/*  605 */     char c = Character.forDigit(nibble, 16);
/*  606 */     if (c == '\000') {
/*  607 */       throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
/*      */     }
/*  609 */     return c;
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
/*      */   public static char intToHexDigitMsb0(int nibble) {
/*  631 */     switch (nibble) {
/*      */       case 0:
/*  633 */         return '0';
/*      */       case 1:
/*  635 */         return '8';
/*      */       case 2:
/*  637 */         return '4';
/*      */       case 3:
/*  639 */         return 'c';
/*      */       case 4:
/*  641 */         return '2';
/*      */       case 5:
/*  643 */         return 'a';
/*      */       case 6:
/*  645 */         return '6';
/*      */       case 7:
/*  647 */         return 'e';
/*      */       case 8:
/*  649 */         return '1';
/*      */       case 9:
/*  651 */         return '9';
/*      */       case 10:
/*  653 */         return '5';
/*      */       case 11:
/*  655 */         return 'd';
/*      */       case 12:
/*  657 */         return '3';
/*      */       case 13:
/*  659 */         return 'b';
/*      */       case 14:
/*  661 */         return '7';
/*      */       case 15:
/*  663 */         return 'f';
/*      */     } 
/*  665 */     throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
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
/*      */   public static long intArrayToLong(int[] src, int srcPos, long dstInit, int dstPos, int nInts) {
/*  687 */     if ((src.length == 0 && srcPos == 0) || 0 == nInts) {
/*  688 */       return dstInit;
/*      */     }
/*  690 */     if ((nInts - 1) * 32 + dstPos >= 64) {
/*  691 */       throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  694 */     long out = dstInit;
/*  695 */     int shift = 0;
/*  696 */     for (int i = 0; i < nInts; i++) {
/*  697 */       shift = i * 32 + dstPos;
/*  698 */       long bits = (0xFFFFFFFFL & src[i + srcPos]) << shift;
/*  699 */       long mask = 4294967295L << shift;
/*  700 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  702 */     return out;
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
/*      */   public static long shortArrayToLong(short[] src, int srcPos, long dstInit, int dstPos, int nShorts) {
/*  724 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  725 */       return dstInit;
/*      */     }
/*  727 */     if ((nShorts - 1) * 16 + dstPos >= 64) {
/*  728 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  731 */     long out = dstInit;
/*  732 */     int shift = 0;
/*  733 */     for (int i = 0; i < nShorts; i++) {
/*  734 */       shift = i * 16 + dstPos;
/*  735 */       long bits = (0xFFFFL & src[i + srcPos]) << shift;
/*  736 */       long mask = 65535L << shift;
/*  737 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  739 */     return out;
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
/*      */   public static int shortArrayToInt(short[] src, int srcPos, int dstInit, int dstPos, int nShorts) {
/*  761 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  762 */       return dstInit;
/*      */     }
/*  764 */     if ((nShorts - 1) * 16 + dstPos >= 32) {
/*  765 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  768 */     int out = dstInit;
/*  769 */     int shift = 0;
/*  770 */     for (int i = 0; i < nShorts; i++) {
/*  771 */       shift = i * 16 + dstPos;
/*  772 */       int bits = (0xFFFF & src[i + srcPos]) << shift;
/*  773 */       int mask = 65535 << shift;
/*  774 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  776 */     return out;
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
/*      */   public static long byteArrayToLong(byte[] src, int srcPos, long dstInit, int dstPos, int nBytes) {
/*  798 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  799 */       return dstInit;
/*      */     }
/*  801 */     if ((nBytes - 1) * 8 + dstPos >= 64) {
/*  802 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  805 */     long out = dstInit;
/*  806 */     int shift = 0;
/*  807 */     for (int i = 0; i < nBytes; i++) {
/*  808 */       shift = i * 8 + dstPos;
/*  809 */       long bits = (0xFFL & src[i + srcPos]) << shift;
/*  810 */       long mask = 255L << shift;
/*  811 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  813 */     return out;
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
/*      */   public static int byteArrayToInt(byte[] src, int srcPos, int dstInit, int dstPos, int nBytes) {
/*  834 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  835 */       return dstInit;
/*      */     }
/*  837 */     if ((nBytes - 1) * 8 + dstPos >= 32) {
/*  838 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  841 */     int out = dstInit;
/*  842 */     int shift = 0;
/*  843 */     for (int i = 0; i < nBytes; i++) {
/*  844 */       shift = i * 8 + dstPos;
/*  845 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  846 */       int mask = 255 << shift;
/*  847 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  849 */     return out;
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
/*      */   public static short byteArrayToShort(byte[] src, int srcPos, short dstInit, int dstPos, int nBytes) {
/*  871 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  872 */       return dstInit;
/*      */     }
/*  874 */     if ((nBytes - 1) * 8 + dstPos >= 16) {
/*  875 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  878 */     short out = dstInit;
/*  879 */     int shift = 0;
/*  880 */     for (int i = 0; i < nBytes; i++) {
/*  881 */       shift = i * 8 + dstPos;
/*  882 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  883 */       int mask = 255 << shift;
/*  884 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  886 */     return out;
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
/*      */   public static long hexToLong(String src, int srcPos, long dstInit, int dstPos, int nHex) {
/*  905 */     if (0 == nHex) {
/*  906 */       return dstInit;
/*      */     }
/*  908 */     if ((nHex - 1) * 4 + dstPos >= 64) {
/*  909 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  912 */     long out = dstInit;
/*  913 */     int shift = 0;
/*  914 */     for (int i = 0; i < nHex; i++) {
/*  915 */       shift = i * 4 + dstPos;
/*  916 */       long bits = (0xFL & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  917 */       long mask = 15L << shift;
/*  918 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  920 */     return out;
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
/*      */   public static int hexToInt(String src, int srcPos, int dstInit, int dstPos, int nHex) {
/*  939 */     if (0 == nHex) {
/*  940 */       return dstInit;
/*      */     }
/*  942 */     if ((nHex - 1) * 4 + dstPos >= 32) {
/*  943 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  946 */     int out = dstInit;
/*  947 */     int shift = 0;
/*  948 */     for (int i = 0; i < nHex; i++) {
/*  949 */       shift = i * 4 + dstPos;
/*  950 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  951 */       int mask = 15 << shift;
/*  952 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  954 */     return out;
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
/*      */   public static short hexToShort(String src, int srcPos, short dstInit, int dstPos, int nHex) {
/*  973 */     if (0 == nHex) {
/*  974 */       return dstInit;
/*      */     }
/*  976 */     if ((nHex - 1) * 4 + dstPos >= 16) {
/*  977 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  980 */     short out = dstInit;
/*  981 */     int shift = 0;
/*  982 */     for (int i = 0; i < nHex; i++) {
/*  983 */       shift = i * 4 + dstPos;
/*  984 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  985 */       int mask = 15 << shift;
/*  986 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  988 */     return out;
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
/*      */   public static byte hexToByte(String src, int srcPos, byte dstInit, int dstPos, int nHex) {
/* 1007 */     if (0 == nHex) {
/* 1008 */       return dstInit;
/*      */     }
/* 1010 */     if ((nHex - 1) * 4 + dstPos >= 8) {
/* 1011 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1014 */     byte out = dstInit;
/* 1015 */     int shift = 0;
/* 1016 */     for (int i = 0; i < nHex; i++) {
/* 1017 */       shift = i * 4 + dstPos;
/* 1018 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/* 1019 */       int mask = 15 << shift;
/* 1020 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1022 */     return out;
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
/*      */   public static long binaryToLong(boolean[] src, int srcPos, long dstInit, int dstPos, int nBools) {
/* 1044 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1045 */       return dstInit;
/*      */     }
/* 1047 */     if (nBools - 1 + dstPos >= 64) {
/* 1048 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1051 */     long out = dstInit;
/* 1052 */     int shift = 0;
/* 1053 */     for (int i = 0; i < nBools; i++) {
/* 1054 */       shift = i * 1 + dstPos;
/* 1055 */       long bits = (src[i + srcPos] ? 1L : 0L) << shift;
/* 1056 */       long mask = 1L << shift;
/* 1057 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/* 1059 */     return out;
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
/*      */   public static int binaryToInt(boolean[] src, int srcPos, int dstInit, int dstPos, int nBools) {
/* 1080 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1081 */       return dstInit;
/*      */     }
/* 1083 */     if (nBools - 1 + dstPos >= 32) {
/* 1084 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1087 */     int out = dstInit;
/* 1088 */     int shift = 0;
/* 1089 */     for (int i = 0; i < nBools; i++) {
/* 1090 */       shift = i * 1 + dstPos;
/* 1091 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1092 */       int mask = 1 << shift;
/* 1093 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/* 1095 */     return out;
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
/*      */   public static short binaryToShort(boolean[] src, int srcPos, short dstInit, int dstPos, int nBools) {
/* 1117 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1118 */       return dstInit;
/*      */     }
/* 1120 */     if (nBools - 1 + dstPos >= 16) {
/* 1121 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1124 */     short out = dstInit;
/* 1125 */     int shift = 0;
/* 1126 */     for (int i = 0; i < nBools; i++) {
/* 1127 */       shift = i * 1 + dstPos;
/* 1128 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1129 */       int mask = 1 << shift;
/* 1130 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1132 */     return out;
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
/*      */   public static byte binaryToByte(boolean[] src, int srcPos, byte dstInit, int dstPos, int nBools) {
/* 1154 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1155 */       return dstInit;
/*      */     }
/* 1157 */     if (nBools - 1 + dstPos >= 8) {
/* 1158 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 8");
/*      */     }
/* 1160 */     byte out = dstInit;
/* 1161 */     int shift = 0;
/* 1162 */     for (int i = 0; i < nBools; i++) {
/* 1163 */       shift = i * 1 + dstPos;
/* 1164 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1165 */       int mask = 1 << shift;
/* 1166 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1168 */     return out;
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
/*      */   public static int[] longToIntArray(long src, int srcPos, int[] dst, int dstPos, int nInts) {
/* 1189 */     if (0 == nInts) {
/* 1190 */       return dst;
/*      */     }
/* 1192 */     if ((nInts - 1) * 32 + srcPos >= 64) {
/* 1193 */       throw new IllegalArgumentException("(nInts-1)*32+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1196 */     int shift = 0;
/* 1197 */     for (int i = 0; i < nInts; i++) {
/* 1198 */       shift = i * 32 + srcPos;
/* 1199 */       dst[dstPos + i] = (int)(0xFFFFFFFFFFFFFFFFL & src >> shift);
/*      */     } 
/* 1201 */     return dst;
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
/*      */   public static short[] longToShortArray(long src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1223 */     if (0 == nShorts) {
/* 1224 */       return dst;
/*      */     }
/* 1226 */     if ((nShorts - 1) * 16 + srcPos >= 64) {
/* 1227 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1230 */     int shift = 0;
/* 1231 */     for (int i = 0; i < nShorts; i++) {
/* 1232 */       shift = i * 16 + srcPos;
/* 1233 */       dst[dstPos + i] = (short)(int)(0xFFFFL & src >> shift);
/*      */     } 
/* 1235 */     return dst;
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
/*      */   public static short[] intToShortArray(int src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1257 */     if (0 == nShorts) {
/* 1258 */       return dst;
/*      */     }
/* 1260 */     if ((nShorts - 1) * 16 + srcPos >= 32) {
/* 1261 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1264 */     int shift = 0;
/* 1265 */     for (int i = 0; i < nShorts; i++) {
/* 1266 */       shift = i * 16 + srcPos;
/* 1267 */       dst[dstPos + i] = (short)(0xFFFF & src >> shift);
/*      */     } 
/* 1269 */     return dst;
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
/*      */   public static byte[] longToByteArray(long src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1291 */     if (0 == nBytes) {
/* 1292 */       return dst;
/*      */     }
/* 1294 */     if ((nBytes - 1) * 8 + srcPos >= 64) {
/* 1295 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1298 */     int shift = 0;
/* 1299 */     for (int i = 0; i < nBytes; i++) {
/* 1300 */       shift = i * 8 + srcPos;
/* 1301 */       dst[dstPos + i] = (byte)(int)(0xFFL & src >> shift);
/*      */     } 
/* 1303 */     return dst;
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
/*      */   public static byte[] intToByteArray(int src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1324 */     if (0 == nBytes) {
/* 1325 */       return dst;
/*      */     }
/* 1327 */     if ((nBytes - 1) * 8 + srcPos >= 32) {
/* 1328 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1331 */     int shift = 0;
/* 1332 */     for (int i = 0; i < nBytes; i++) {
/* 1333 */       shift = i * 8 + srcPos;
/* 1334 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1336 */     return dst;
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
/*      */   public static byte[] shortToByteArray(short src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1358 */     if (0 == nBytes) {
/* 1359 */       return dst;
/*      */     }
/* 1361 */     if ((nBytes - 1) * 8 + srcPos >= 16) {
/* 1362 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1365 */     int shift = 0;
/* 1366 */     for (int i = 0; i < nBytes; i++) {
/* 1367 */       shift = i * 8 + srcPos;
/* 1368 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1370 */     return dst;
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
/*      */   public static String longToHex(long src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1390 */     if (0 == nHexs) {
/* 1391 */       return dstInit;
/*      */     }
/* 1393 */     if ((nHexs - 1) * 4 + srcPos >= 64) {
/* 1394 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1397 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1398 */     int shift = 0;
/* 1399 */     int append = sb.length();
/* 1400 */     for (int i = 0; i < nHexs; i++) {
/* 1401 */       shift = i * 4 + srcPos;
/* 1402 */       int bits = (int)(0xFL & src >> shift);
/* 1403 */       if (dstPos + i == append) {
/* 1404 */         append++;
/* 1405 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1407 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1410 */     return sb.toString();
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
/*      */   public static String intToHex(int src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1430 */     if (0 == nHexs) {
/* 1431 */       return dstInit;
/*      */     }
/* 1433 */     if ((nHexs - 1) * 4 + srcPos >= 32) {
/* 1434 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1437 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1438 */     int shift = 0;
/* 1439 */     int append = sb.length();
/* 1440 */     for (int i = 0; i < nHexs; i++) {
/* 1441 */       shift = i * 4 + srcPos;
/* 1442 */       int bits = 0xF & src >> shift;
/* 1443 */       if (dstPos + i == append) {
/* 1444 */         append++;
/* 1445 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1447 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1450 */     return sb.toString();
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
/*      */   public static String shortToHex(short src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1470 */     if (0 == nHexs) {
/* 1471 */       return dstInit;
/*      */     }
/* 1473 */     if ((nHexs - 1) * 4 + srcPos >= 16) {
/* 1474 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1477 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1478 */     int shift = 0;
/* 1479 */     int append = sb.length();
/* 1480 */     for (int i = 0; i < nHexs; i++) {
/* 1481 */       shift = i * 4 + srcPos;
/* 1482 */       int bits = 0xF & src >> shift;
/* 1483 */       if (dstPos + i == append) {
/* 1484 */         append++;
/* 1485 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1487 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1490 */     return sb.toString();
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
/*      */   public static String byteToHex(byte src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1510 */     if (0 == nHexs) {
/* 1511 */       return dstInit;
/*      */     }
/* 1513 */     if ((nHexs - 1) * 4 + srcPos >= 8) {
/* 1514 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1517 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1518 */     int shift = 0;
/* 1519 */     int append = sb.length();
/* 1520 */     for (int i = 0; i < nHexs; i++) {
/* 1521 */       shift = i * 4 + srcPos;
/* 1522 */       int bits = 0xF & src >> shift;
/* 1523 */       if (dstPos + i == append) {
/* 1524 */         append++;
/* 1525 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1527 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1530 */     return sb.toString();
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
/*      */   public static boolean[] longToBinary(long src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1552 */     if (0 == nBools) {
/* 1553 */       return dst;
/*      */     }
/* 1555 */     if (nBools - 1 + srcPos >= 64) {
/* 1556 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1559 */     int shift = 0;
/* 1560 */     for (int i = 0; i < nBools; i++) {
/* 1561 */       shift = i * 1 + srcPos;
/* 1562 */       dst[dstPos + i] = ((0x1L & src >> shift) != 0L);
/*      */     } 
/* 1564 */     return dst;
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
/*      */   public static boolean[] intToBinary(int src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1586 */     if (0 == nBools) {
/* 1587 */       return dst;
/*      */     }
/* 1589 */     if (nBools - 1 + srcPos >= 32) {
/* 1590 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1593 */     int shift = 0;
/* 1594 */     for (int i = 0; i < nBools; i++) {
/* 1595 */       shift = i * 1 + srcPos;
/* 1596 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1598 */     return dst;
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
/*      */   public static boolean[] shortToBinary(short src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1620 */     if (0 == nBools) {
/* 1621 */       return dst;
/*      */     }
/* 1623 */     if (nBools - 1 + srcPos >= 16) {
/* 1624 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1627 */     int shift = 0;
/* 1628 */     assert (nBools - 1) * 1 < 16 - srcPos;
/* 1629 */     for (int i = 0; i < nBools; i++) {
/* 1630 */       shift = i * 1 + srcPos;
/* 1631 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1633 */     return dst;
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
/*      */   public static boolean[] byteToBinary(byte src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1655 */     if (0 == nBools) {
/* 1656 */       return dst;
/*      */     }
/* 1658 */     if (nBools - 1 + srcPos >= 8) {
/* 1659 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 8");
/*      */     }
/* 1661 */     int shift = 0;
/* 1662 */     for (int i = 0; i < nBools; i++) {
/* 1663 */       shift = i * 1 + srcPos;
/* 1664 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1666 */     return dst;
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
/*      */   public static byte[] uuidToByteArray(UUID src, byte[] dst, int dstPos, int nBytes) {
/* 1686 */     if (0 == nBytes) {
/* 1687 */       return dst;
/*      */     }
/* 1689 */     if (nBytes > 16) {
/* 1690 */       throw new IllegalArgumentException("nBytes is greather than 16");
/*      */     }
/* 1692 */     longToByteArray(src.getMostSignificantBits(), 0, dst, dstPos, (nBytes > 8) ? 8 : nBytes);
/* 1693 */     if (nBytes >= 8) {
/* 1694 */       longToByteArray(src.getLeastSignificantBits(), 0, dst, dstPos + 8, nBytes - 8);
/*      */     }
/* 1696 */     return dst;
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
/*      */   public static UUID byteArrayToUuid(byte[] src, int srcPos) {
/* 1713 */     if (src.length - srcPos < 16) {
/* 1714 */       throw new IllegalArgumentException("Need at least 16 bytes for UUID");
/*      */     }
/* 1716 */     return new UUID(byteArrayToLong(src, srcPos, 0L, 0, 8), byteArrayToLong(src, srcPos + 8, 0L, 0, 8));
/*      */   }
/*      */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\Conversion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */