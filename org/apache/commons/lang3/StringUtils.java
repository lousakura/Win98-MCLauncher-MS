/*      */ package org.apache.commons.lang3;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.nio.charset.Charset;
/*      */ import java.text.Normalizer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.regex.Pattern;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StringUtils
/*      */ {
/*      */   public static final String SPACE = " ";
/*      */   public static final String EMPTY = "";
/*      */   public static final String LF = "\n";
/*      */   public static final String CR = "\r";
/*      */   public static final int INDEX_NOT_FOUND = -1;
/*      */   private static final int PAD_LIMIT = 8192;
/*  183 */   private static final Pattern WHITESPACE_PATTERN = Pattern.compile("(?: |\\u00A0|\\s|[\\s&&[^ ]])\\s*");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(CharSequence cs) {
/*  219 */     return (cs == null || cs.length() == 0);
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
/*      */   public static boolean isNotEmpty(CharSequence cs) {
/*  238 */     return !isEmpty(cs);
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
/*      */   public static boolean isAnyEmpty(CharSequence... css) {
/*  259 */     if (ArrayUtils.isEmpty((Object[])css)) {
/*  260 */       return true;
/*      */     }
/*  262 */     for (CharSequence cs : css) {
/*  263 */       if (isEmpty(cs)) {
/*  264 */         return true;
/*      */       }
/*      */     } 
/*  267 */     return false;
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
/*      */   public static boolean isNoneEmpty(CharSequence... css) {
/*  288 */     return !isAnyEmpty(css);
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
/*      */   public static boolean isBlank(CharSequence cs) {
/*      */     int strLen;
/*  308 */     if (cs == null || (strLen = cs.length()) == 0) {
/*  309 */       return true;
/*      */     }
/*  311 */     for (int i = 0; i < strLen; i++) {
/*  312 */       if (!Character.isWhitespace(cs.charAt(i))) {
/*  313 */         return false;
/*      */       }
/*      */     } 
/*  316 */     return true;
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
/*      */   public static boolean isNotBlank(CharSequence cs) {
/*  337 */     return !isBlank(cs);
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
/*      */   public static boolean isAnyBlank(CharSequence... css) {
/*  359 */     if (ArrayUtils.isEmpty((Object[])css)) {
/*  360 */       return true;
/*      */     }
/*  362 */     for (CharSequence cs : css) {
/*  363 */       if (isBlank(cs)) {
/*  364 */         return true;
/*      */       }
/*      */     } 
/*  367 */     return false;
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
/*      */   public static boolean isNoneBlank(CharSequence... css) {
/*  389 */     return !isAnyBlank(css);
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
/*      */   public static String trim(String str) {
/*  418 */     return (str == null) ? null : str.trim();
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
/*      */   public static String trimToNull(String str) {
/*  444 */     String ts = trim(str);
/*  445 */     return isEmpty(ts) ? null : ts;
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
/*      */   public static String trimToEmpty(String str) {
/*  470 */     return (str == null) ? "" : str.trim();
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
/*      */   public static String strip(String str) {
/*  498 */     return strip(str, null);
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
/*      */   public static String stripToNull(String str) {
/*  525 */     if (str == null) {
/*  526 */       return null;
/*      */     }
/*  528 */     str = strip(str, null);
/*  529 */     return str.isEmpty() ? null : str;
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
/*      */   public static String stripToEmpty(String str) {
/*  555 */     return (str == null) ? "" : strip(str, null);
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
/*      */   public static String strip(String str, String stripChars) {
/*  585 */     if (isEmpty(str)) {
/*  586 */       return str;
/*      */     }
/*  588 */     str = stripStart(str, stripChars);
/*  589 */     return stripEnd(str, stripChars);
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
/*      */   public static String stripStart(String str, String stripChars) {
/*      */     int strLen;
/*  618 */     if (str == null || (strLen = str.length()) == 0) {
/*  619 */       return str;
/*      */     }
/*  621 */     int start = 0;
/*  622 */     if (stripChars == null) {
/*  623 */       while (start != strLen && Character.isWhitespace(str.charAt(start)))
/*  624 */         start++; 
/*      */     } else {
/*  626 */       if (stripChars.isEmpty()) {
/*  627 */         return str;
/*      */       }
/*  629 */       while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
/*  630 */         start++;
/*      */       }
/*      */     } 
/*  633 */     return str.substring(start);
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
/*      */   public static String stripEnd(String str, String stripChars) {
/*      */     int end;
/*  663 */     if (str == null || (end = str.length()) == 0) {
/*  664 */       return str;
/*      */     }
/*      */     
/*  667 */     if (stripChars == null) {
/*  668 */       while (end != 0 && Character.isWhitespace(str.charAt(end - 1)))
/*  669 */         end--; 
/*      */     } else {
/*  671 */       if (stripChars.isEmpty()) {
/*  672 */         return str;
/*      */       }
/*  674 */       while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
/*  675 */         end--;
/*      */       }
/*      */     } 
/*  678 */     return str.substring(0, end);
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
/*      */   public static String[] stripAll(String... strs) {
/*  703 */     return stripAll(strs, null);
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
/*      */   public static String[] stripAll(String[] strs, String stripChars) {
/*      */     int strsLen;
/*  733 */     if (strs == null || (strsLen = strs.length) == 0) {
/*  734 */       return strs;
/*      */     }
/*  736 */     String[] newArr = new String[strsLen];
/*  737 */     for (int i = 0; i < strsLen; i++) {
/*  738 */       newArr[i] = strip(strs[i], stripChars);
/*      */     }
/*  740 */     return newArr;
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
/*      */   public static String stripAccents(String input) {
/*  762 */     if (input == null) {
/*  763 */       return null;
/*      */     }
/*  765 */     Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
/*  766 */     String decomposed = Normalizer.normalize(input, Normalizer.Form.NFD);
/*      */     
/*  768 */     return pattern.matcher(decomposed).replaceAll("");
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
/*      */   public static boolean equals(CharSequence cs1, CharSequence cs2) {
/*  795 */     if (cs1 == cs2) {
/*  796 */       return true;
/*      */     }
/*  798 */     if (cs1 == null || cs2 == null) {
/*  799 */       return false;
/*      */     }
/*  801 */     if (cs1 instanceof String && cs2 instanceof String) {
/*  802 */       return cs1.equals(cs2);
/*      */     }
/*  804 */     return CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
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
/*      */   public static boolean equalsIgnoreCase(CharSequence str1, CharSequence str2) {
/*  829 */     if (str1 == null || str2 == null)
/*  830 */       return (str1 == str2); 
/*  831 */     if (str1 == str2)
/*  832 */       return true; 
/*  833 */     if (str1.length() != str2.length()) {
/*  834 */       return false;
/*      */     }
/*  836 */     return CharSequenceUtils.regionMatches(str1, true, 0, str2, 0, str1.length());
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
/*      */   public static int indexOf(CharSequence seq, int searchChar) {
/*  863 */     if (isEmpty(seq)) {
/*  864 */       return -1;
/*      */     }
/*  866 */     return CharSequenceUtils.indexOf(seq, searchChar, 0);
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
/*      */   public static int indexOf(CharSequence seq, int searchChar, int startPos) {
/*  896 */     if (isEmpty(seq)) {
/*  897 */       return -1;
/*      */     }
/*  899 */     return CharSequenceUtils.indexOf(seq, searchChar, startPos);
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq) {
/*  927 */     if (seq == null || searchSeq == null) {
/*  928 */       return -1;
/*      */     }
/*  930 */     return CharSequenceUtils.indexOf(seq, searchSeq, 0);
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
/*      */   public static int indexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/*  967 */     if (seq == null || searchSeq == null) {
/*  968 */       return -1;
/*      */     }
/*  970 */     return CharSequenceUtils.indexOf(seq, searchSeq, startPos);
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
/*      */   public static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 1008 */     return ordinalIndexOf(str, searchStr, ordinal, false);
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
/*      */   private static int ordinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal, boolean lastIndex) {
/* 1026 */     if (str == null || searchStr == null || ordinal <= 0) {
/* 1027 */       return -1;
/*      */     }
/* 1029 */     if (searchStr.length() == 0) {
/* 1030 */       return lastIndex ? str.length() : 0;
/*      */     }
/* 1032 */     int found = 0;
/* 1033 */     int index = lastIndex ? str.length() : -1;
/*      */     while (true) {
/* 1035 */       if (lastIndex) {
/* 1036 */         index = CharSequenceUtils.lastIndexOf(str, searchStr, index - 1);
/*      */       } else {
/* 1038 */         index = CharSequenceUtils.indexOf(str, searchStr, index + 1);
/*      */       } 
/* 1040 */       if (index < 0) {
/* 1041 */         return index;
/*      */       }
/* 1043 */       found++;
/* 1044 */       if (found >= ordinal) {
/* 1045 */         return index;
/*      */       }
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1074 */     return indexOfIgnoreCase(str, searchStr, 0);
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
/*      */   public static int indexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 1110 */     if (str == null || searchStr == null) {
/* 1111 */       return -1;
/*      */     }
/* 1113 */     if (startPos < 0) {
/* 1114 */       startPos = 0;
/*      */     }
/* 1116 */     int endLimit = str.length() - searchStr.length() + 1;
/* 1117 */     if (startPos > endLimit) {
/* 1118 */       return -1;
/*      */     }
/* 1120 */     if (searchStr.length() == 0) {
/* 1121 */       return startPos;
/*      */     }
/* 1123 */     for (int i = startPos; i < endLimit; i++) {
/* 1124 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 1125 */         return i;
/*      */       }
/*      */     } 
/* 1128 */     return -1;
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
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar) {
/* 1154 */     if (isEmpty(seq)) {
/* 1155 */       return -1;
/*      */     }
/* 1157 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, seq.length());
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
/*      */   public static int lastIndexOf(CharSequence seq, int searchChar, int startPos) {
/* 1192 */     if (isEmpty(seq)) {
/* 1193 */       return -1;
/*      */     }
/* 1195 */     return CharSequenceUtils.lastIndexOf(seq, searchChar, startPos);
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq) {
/* 1222 */     if (seq == null || searchSeq == null) {
/* 1223 */       return -1;
/*      */     }
/* 1225 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, seq.length());
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
/*      */   public static int lastOrdinalIndexOf(CharSequence str, CharSequence searchStr, int ordinal) {
/* 1263 */     return ordinalIndexOf(str, searchStr, ordinal, true);
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
/*      */   public static int lastIndexOf(CharSequence seq, CharSequence searchSeq, int startPos) {
/* 1303 */     if (seq == null || searchSeq == null) {
/* 1304 */       return -1;
/*      */     }
/* 1306 */     return CharSequenceUtils.lastIndexOf(seq, searchSeq, startPos);
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1333 */     if (str == null || searchStr == null) {
/* 1334 */       return -1;
/*      */     }
/* 1336 */     return lastIndexOfIgnoreCase(str, searchStr, str.length());
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
/*      */   public static int lastIndexOfIgnoreCase(CharSequence str, CharSequence searchStr, int startPos) {
/* 1372 */     if (str == null || searchStr == null) {
/* 1373 */       return -1;
/*      */     }
/* 1375 */     if (startPos > str.length() - searchStr.length()) {
/* 1376 */       startPos = str.length() - searchStr.length();
/*      */     }
/* 1378 */     if (startPos < 0) {
/* 1379 */       return -1;
/*      */     }
/* 1381 */     if (searchStr.length() == 0) {
/* 1382 */       return startPos;
/*      */     }
/*      */     
/* 1385 */     for (int i = startPos; i >= 0; i--) {
/* 1386 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, searchStr.length())) {
/* 1387 */         return i;
/*      */       }
/*      */     } 
/* 1390 */     return -1;
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
/*      */   public static boolean contains(CharSequence seq, int searchChar) {
/* 1416 */     if (isEmpty(seq)) {
/* 1417 */       return false;
/*      */     }
/* 1419 */     return (CharSequenceUtils.indexOf(seq, searchChar, 0) >= 0);
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
/*      */   public static boolean contains(CharSequence seq, CharSequence searchSeq) {
/* 1445 */     if (seq == null || searchSeq == null) {
/* 1446 */       return false;
/*      */     }
/* 1448 */     return (CharSequenceUtils.indexOf(seq, searchSeq, 0) >= 0);
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
/*      */   public static boolean containsIgnoreCase(CharSequence str, CharSequence searchStr) {
/* 1476 */     if (str == null || searchStr == null) {
/* 1477 */       return false;
/*      */     }
/* 1479 */     int len = searchStr.length();
/* 1480 */     int max = str.length() - len;
/* 1481 */     for (int i = 0; i <= max; i++) {
/* 1482 */       if (CharSequenceUtils.regionMatches(str, true, i, searchStr, 0, len)) {
/* 1483 */         return true;
/*      */       }
/*      */     } 
/* 1486 */     return false;
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
/*      */   public static boolean containsWhitespace(CharSequence seq) {
/* 1499 */     if (isEmpty(seq)) {
/* 1500 */       return false;
/*      */     }
/* 1502 */     int strLen = seq.length();
/* 1503 */     for (int i = 0; i < strLen; i++) {
/* 1504 */       if (Character.isWhitespace(seq.charAt(i))) {
/* 1505 */         return true;
/*      */       }
/*      */     } 
/* 1508 */     return false;
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
/*      */   public static int indexOfAny(CharSequence cs, char... searchChars) {
/* 1537 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1538 */       return -1;
/*      */     }
/* 1540 */     int csLen = cs.length();
/* 1541 */     int csLast = csLen - 1;
/* 1542 */     int searchLen = searchChars.length;
/* 1543 */     int searchLast = searchLen - 1;
/* 1544 */     for (int i = 0; i < csLen; i++) {
/* 1545 */       char ch = cs.charAt(i);
/* 1546 */       for (int j = 0; j < searchLen; j++) {
/* 1547 */         if (searchChars[j] == ch) {
/* 1548 */           if (i < csLast && j < searchLast && Character.isHighSurrogate(ch)) {
/*      */             
/* 1550 */             if (searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1551 */               return i;
/*      */             }
/*      */           } else {
/* 1554 */             return i;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1559 */     return -1;
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
/*      */   public static int indexOfAny(CharSequence cs, String searchChars) {
/* 1586 */     if (isEmpty(cs) || isEmpty(searchChars)) {
/* 1587 */       return -1;
/*      */     }
/* 1589 */     return indexOfAny(cs, searchChars.toCharArray());
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
/*      */   public static boolean containsAny(CharSequence cs, char... searchChars) {
/* 1619 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1620 */       return false;
/*      */     }
/* 1622 */     int csLength = cs.length();
/* 1623 */     int searchLength = searchChars.length;
/* 1624 */     int csLast = csLength - 1;
/* 1625 */     int searchLast = searchLength - 1;
/* 1626 */     for (int i = 0; i < csLength; i++) {
/* 1627 */       char ch = cs.charAt(i);
/* 1628 */       for (int j = 0; j < searchLength; j++) {
/* 1629 */         if (searchChars[j] == ch) {
/* 1630 */           if (Character.isHighSurrogate(ch)) {
/* 1631 */             if (j == searchLast)
/*      */             {
/* 1633 */               return true;
/*      */             }
/* 1635 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1636 */               return true;
/*      */             }
/*      */           } else {
/*      */             
/* 1640 */             return true;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1645 */     return false;
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
/*      */   public static boolean containsAny(CharSequence cs, CharSequence searchChars) {
/* 1677 */     if (searchChars == null) {
/* 1678 */       return false;
/*      */     }
/* 1680 */     return containsAny(cs, CharSequenceUtils.toCharArray(searchChars));
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
/*      */   public static int indexOfAnyBut(CharSequence cs, char... searchChars) {
/* 1710 */     if (isEmpty(cs) || ArrayUtils.isEmpty(searchChars)) {
/* 1711 */       return -1;
/*      */     }
/* 1713 */     int csLen = cs.length();
/* 1714 */     int csLast = csLen - 1;
/* 1715 */     int searchLen = searchChars.length;
/* 1716 */     int searchLast = searchLen - 1;
/*      */     
/* 1718 */     for (int i = 0; i < csLen; i++) {
/* 1719 */       char ch = cs.charAt(i);
/* 1720 */       int j = 0; while (true) { if (j < searchLen) {
/* 1721 */           if (searchChars[j] == ch && (
/* 1722 */             i >= csLast || j >= searchLast || !Character.isHighSurrogate(ch) || 
/* 1723 */             searchChars[j + 1] == cs.charAt(i + 1))) {
/*      */             break;
/*      */           }
/*      */           
/*      */           j++;
/*      */           
/*      */           continue;
/*      */         } 
/* 1731 */         return i; }
/*      */     
/* 1733 */     }  return -1;
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
/*      */   public static int indexOfAnyBut(CharSequence seq, CharSequence searchChars) {
/* 1760 */     if (isEmpty(seq) || isEmpty(searchChars)) {
/* 1761 */       return -1;
/*      */     }
/* 1763 */     int strLen = seq.length();
/* 1764 */     for (int i = 0; i < strLen; i++) {
/* 1765 */       char ch = seq.charAt(i);
/* 1766 */       boolean chFound = (CharSequenceUtils.indexOf(searchChars, ch, 0) >= 0);
/* 1767 */       if (i + 1 < strLen && Character.isHighSurrogate(ch)) {
/* 1768 */         char ch2 = seq.charAt(i + 1);
/* 1769 */         if (chFound && CharSequenceUtils.indexOf(searchChars, ch2, 0) < 0) {
/* 1770 */           return i;
/*      */         }
/*      */       }
/* 1773 */       else if (!chFound) {
/* 1774 */         return i;
/*      */       } 
/*      */     } 
/*      */     
/* 1778 */     return -1;
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
/*      */   public static boolean containsOnly(CharSequence cs, char... valid) {
/* 1807 */     if (valid == null || cs == null) {
/* 1808 */       return false;
/*      */     }
/* 1810 */     if (cs.length() == 0) {
/* 1811 */       return true;
/*      */     }
/* 1813 */     if (valid.length == 0) {
/* 1814 */       return false;
/*      */     }
/* 1816 */     return (indexOfAnyBut(cs, valid) == -1);
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
/*      */   public static boolean containsOnly(CharSequence cs, String validChars) {
/* 1843 */     if (cs == null || validChars == null) {
/* 1844 */       return false;
/*      */     }
/* 1846 */     return containsOnly(cs, validChars.toCharArray());
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
/*      */   public static boolean containsNone(CharSequence cs, char... searchChars) {
/* 1875 */     if (cs == null || searchChars == null) {
/* 1876 */       return true;
/*      */     }
/* 1878 */     int csLen = cs.length();
/* 1879 */     int csLast = csLen - 1;
/* 1880 */     int searchLen = searchChars.length;
/* 1881 */     int searchLast = searchLen - 1;
/* 1882 */     for (int i = 0; i < csLen; i++) {
/* 1883 */       char ch = cs.charAt(i);
/* 1884 */       for (int j = 0; j < searchLen; j++) {
/* 1885 */         if (searchChars[j] == ch) {
/* 1886 */           if (Character.isHighSurrogate(ch)) {
/* 1887 */             if (j == searchLast)
/*      */             {
/* 1889 */               return false;
/*      */             }
/* 1891 */             if (i < csLast && searchChars[j + 1] == cs.charAt(i + 1)) {
/* 1892 */               return false;
/*      */             }
/*      */           } else {
/*      */             
/* 1896 */             return false;
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/* 1901 */     return true;
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
/*      */   public static boolean containsNone(CharSequence cs, String invalidChars) {
/* 1928 */     if (cs == null || invalidChars == null) {
/* 1929 */       return true;
/*      */     }
/* 1931 */     return containsNone(cs, invalidChars.toCharArray());
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
/*      */   public static int indexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 1964 */     if (str == null || searchStrs == null) {
/* 1965 */       return -1;
/*      */     }
/* 1967 */     int sz = searchStrs.length;
/*      */ 
/*      */     
/* 1970 */     int ret = Integer.MAX_VALUE;
/*      */     
/* 1972 */     int tmp = 0;
/* 1973 */     for (int i = 0; i < sz; i++) {
/* 1974 */       CharSequence search = searchStrs[i];
/* 1975 */       if (search != null) {
/*      */ 
/*      */         
/* 1978 */         tmp = CharSequenceUtils.indexOf(str, search, 0);
/* 1979 */         if (tmp != -1)
/*      */         {
/*      */ 
/*      */           
/* 1983 */           if (tmp < ret)
/* 1984 */             ret = tmp; 
/*      */         }
/*      */       } 
/*      */     } 
/* 1988 */     return (ret == Integer.MAX_VALUE) ? -1 : ret;
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
/*      */   public static int lastIndexOfAny(CharSequence str, CharSequence... searchStrs) {
/* 2018 */     if (str == null || searchStrs == null) {
/* 2019 */       return -1;
/*      */     }
/* 2021 */     int sz = searchStrs.length;
/* 2022 */     int ret = -1;
/* 2023 */     int tmp = 0;
/* 2024 */     for (int i = 0; i < sz; i++) {
/* 2025 */       CharSequence search = searchStrs[i];
/* 2026 */       if (search != null) {
/*      */ 
/*      */         
/* 2029 */         tmp = CharSequenceUtils.lastIndexOf(str, search, str.length());
/* 2030 */         if (tmp > ret)
/* 2031 */           ret = tmp; 
/*      */       } 
/*      */     } 
/* 2034 */     return ret;
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
/*      */   public static String substring(String str, int start) {
/* 2064 */     if (str == null) {
/* 2065 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2069 */     if (start < 0) {
/* 2070 */       start = str.length() + start;
/*      */     }
/*      */     
/* 2073 */     if (start < 0) {
/* 2074 */       start = 0;
/*      */     }
/* 2076 */     if (start > str.length()) {
/* 2077 */       return "";
/*      */     }
/*      */     
/* 2080 */     return str.substring(start);
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
/*      */   public static String substring(String str, int start, int end) {
/* 2119 */     if (str == null) {
/* 2120 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2124 */     if (end < 0) {
/* 2125 */       end = str.length() + end;
/*      */     }
/* 2127 */     if (start < 0) {
/* 2128 */       start = str.length() + start;
/*      */     }
/*      */ 
/*      */     
/* 2132 */     if (end > str.length()) {
/* 2133 */       end = str.length();
/*      */     }
/*      */ 
/*      */     
/* 2137 */     if (start > end) {
/* 2138 */       return "";
/*      */     }
/*      */     
/* 2141 */     if (start < 0) {
/* 2142 */       start = 0;
/*      */     }
/* 2144 */     if (end < 0) {
/* 2145 */       end = 0;
/*      */     }
/*      */     
/* 2148 */     return str.substring(start, end);
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
/*      */   public static String left(String str, int len) {
/* 2174 */     if (str == null) {
/* 2175 */       return null;
/*      */     }
/* 2177 */     if (len < 0) {
/* 2178 */       return "";
/*      */     }
/* 2180 */     if (str.length() <= len) {
/* 2181 */       return str;
/*      */     }
/* 2183 */     return str.substring(0, len);
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
/*      */   public static String right(String str, int len) {
/* 2207 */     if (str == null) {
/* 2208 */       return null;
/*      */     }
/* 2210 */     if (len < 0) {
/* 2211 */       return "";
/*      */     }
/* 2213 */     if (str.length() <= len) {
/* 2214 */       return str;
/*      */     }
/* 2216 */     return str.substring(str.length() - len);
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
/*      */   public static String mid(String str, int pos, int len) {
/* 2245 */     if (str == null) {
/* 2246 */       return null;
/*      */     }
/* 2248 */     if (len < 0 || pos > str.length()) {
/* 2249 */       return "";
/*      */     }
/* 2251 */     if (pos < 0) {
/* 2252 */       pos = 0;
/*      */     }
/* 2254 */     if (str.length() <= pos + len) {
/* 2255 */       return str.substring(pos);
/*      */     }
/* 2257 */     return str.substring(pos, pos + len);
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
/*      */   public static String substringBefore(String str, String separator) {
/* 2290 */     if (isEmpty(str) || separator == null) {
/* 2291 */       return str;
/*      */     }
/* 2293 */     if (separator.isEmpty()) {
/* 2294 */       return "";
/*      */     }
/* 2296 */     int pos = str.indexOf(separator);
/* 2297 */     if (pos == -1) {
/* 2298 */       return str;
/*      */     }
/* 2300 */     return str.substring(0, pos);
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
/*      */   public static String substringAfter(String str, String separator) {
/* 2332 */     if (isEmpty(str)) {
/* 2333 */       return str;
/*      */     }
/* 2335 */     if (separator == null) {
/* 2336 */       return "";
/*      */     }
/* 2338 */     int pos = str.indexOf(separator);
/* 2339 */     if (pos == -1) {
/* 2340 */       return "";
/*      */     }
/* 2342 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringBeforeLast(String str, String separator) {
/* 2373 */     if (isEmpty(str) || isEmpty(separator)) {
/* 2374 */       return str;
/*      */     }
/* 2376 */     int pos = str.lastIndexOf(separator);
/* 2377 */     if (pos == -1) {
/* 2378 */       return str;
/*      */     }
/* 2380 */     return str.substring(0, pos);
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
/*      */   public static String substringAfterLast(String str, String separator) {
/* 2413 */     if (isEmpty(str)) {
/* 2414 */       return str;
/*      */     }
/* 2416 */     if (isEmpty(separator)) {
/* 2417 */       return "";
/*      */     }
/* 2419 */     int pos = str.lastIndexOf(separator);
/* 2420 */     if (pos == -1 || pos == str.length() - separator.length()) {
/* 2421 */       return "";
/*      */     }
/* 2423 */     return str.substring(pos + separator.length());
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
/*      */   public static String substringBetween(String str, String tag) {
/* 2450 */     return substringBetween(str, tag, tag);
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
/*      */   public static String substringBetween(String str, String open, String close) {
/* 2481 */     if (str == null || open == null || close == null) {
/* 2482 */       return null;
/*      */     }
/* 2484 */     int start = str.indexOf(open);
/* 2485 */     if (start != -1) {
/* 2486 */       int end = str.indexOf(close, start + open.length());
/* 2487 */       if (end != -1) {
/* 2488 */         return str.substring(start + open.length(), end);
/*      */       }
/*      */     } 
/* 2491 */     return null;
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
/*      */   public static String[] substringsBetween(String str, String open, String close) {
/* 2517 */     if (str == null || isEmpty(open) || isEmpty(close)) {
/* 2518 */       return null;
/*      */     }
/* 2520 */     int strLen = str.length();
/* 2521 */     if (strLen == 0) {
/* 2522 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2524 */     int closeLen = close.length();
/* 2525 */     int openLen = open.length();
/* 2526 */     List<String> list = new ArrayList<String>();
/* 2527 */     int pos = 0;
/* 2528 */     while (pos < strLen - closeLen) {
/* 2529 */       int start = str.indexOf(open, pos);
/* 2530 */       if (start < 0) {
/*      */         break;
/*      */       }
/* 2533 */       start += openLen;
/* 2534 */       int end = str.indexOf(close, start);
/* 2535 */       if (end < 0) {
/*      */         break;
/*      */       }
/* 2538 */       list.add(str.substring(start, end));
/* 2539 */       pos = end + closeLen;
/*      */     } 
/* 2541 */     if (list.isEmpty()) {
/* 2542 */       return null;
/*      */     }
/* 2544 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] split(String str) {
/* 2575 */     return split(str, null, -1);
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
/*      */   public static String[] split(String str, char separatorChar) {
/* 2603 */     return splitWorker(str, separatorChar, false);
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
/*      */   public static String[] split(String str, String separatorChars) {
/* 2632 */     return splitWorker(str, separatorChars, -1, false);
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
/*      */   public static String[] split(String str, String separatorChars, int max) {
/* 2666 */     return splitWorker(str, separatorChars, max, false);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator) {
/* 2693 */     return splitByWholeSeparatorWorker(str, separator, -1, false);
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
/*      */   public static String[] splitByWholeSeparator(String str, String separator, int max) {
/* 2724 */     return splitByWholeSeparatorWorker(str, separator, max, false);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator) {
/* 2753 */     return splitByWholeSeparatorWorker(str, separator, -1, true);
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
/*      */   public static String[] splitByWholeSeparatorPreserveAllTokens(String str, String separator, int max) {
/* 2786 */     return splitByWholeSeparatorWorker(str, separator, max, true);
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
/*      */   private static String[] splitByWholeSeparatorWorker(String str, String separator, int max, boolean preserveAllTokens) {
/* 2805 */     if (str == null) {
/* 2806 */       return null;
/*      */     }
/*      */     
/* 2809 */     int len = str.length();
/*      */     
/* 2811 */     if (len == 0) {
/* 2812 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/*      */     
/* 2815 */     if (separator == null || "".equals(separator))
/*      */     {
/* 2817 */       return splitWorker(str, null, max, preserveAllTokens);
/*      */     }
/*      */     
/* 2820 */     int separatorLength = separator.length();
/*      */     
/* 2822 */     ArrayList<String> substrings = new ArrayList<String>();
/* 2823 */     int numberOfSubstrings = 0;
/* 2824 */     int beg = 0;
/* 2825 */     int end = 0;
/* 2826 */     while (end < len) {
/* 2827 */       end = str.indexOf(separator, beg);
/*      */       
/* 2829 */       if (end > -1) {
/* 2830 */         if (end > beg) {
/* 2831 */           numberOfSubstrings++;
/*      */           
/* 2833 */           if (numberOfSubstrings == max) {
/* 2834 */             end = len;
/* 2835 */             substrings.add(str.substring(beg));
/*      */             
/*      */             continue;
/*      */           } 
/* 2839 */           substrings.add(str.substring(beg, end));
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 2844 */           beg = end + separatorLength;
/*      */           
/*      */           continue;
/*      */         } 
/* 2848 */         if (preserveAllTokens) {
/* 2849 */           numberOfSubstrings++;
/* 2850 */           if (numberOfSubstrings == max) {
/* 2851 */             end = len;
/* 2852 */             substrings.add(str.substring(beg));
/*      */           } else {
/* 2854 */             substrings.add("");
/*      */           } 
/*      */         } 
/* 2857 */         beg = end + separatorLength;
/*      */         
/*      */         continue;
/*      */       } 
/* 2861 */       substrings.add(str.substring(beg));
/* 2862 */       end = len;
/*      */     } 
/*      */ 
/*      */     
/* 2866 */     return substrings.<String>toArray(new String[substrings.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String str) {
/* 2895 */     return splitWorker(str, null, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, char separatorChar) {
/* 2931 */     return splitWorker(str, separatorChar, true);
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
/*      */   private static String[] splitWorker(String str, char separatorChar, boolean preserveAllTokens) {
/* 2949 */     if (str == null) {
/* 2950 */       return null;
/*      */     }
/* 2952 */     int len = str.length();
/* 2953 */     if (len == 0) {
/* 2954 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 2956 */     List<String> list = new ArrayList<String>();
/* 2957 */     int i = 0, start = 0;
/* 2958 */     boolean match = false;
/* 2959 */     boolean lastMatch = false;
/* 2960 */     while (i < len) {
/* 2961 */       if (str.charAt(i) == separatorChar) {
/* 2962 */         if (match || preserveAllTokens) {
/* 2963 */           list.add(str.substring(start, i));
/* 2964 */           match = false;
/* 2965 */           lastMatch = true;
/*      */         } 
/* 2967 */         start = ++i;
/*      */         continue;
/*      */       } 
/* 2970 */       lastMatch = false;
/* 2971 */       match = true;
/* 2972 */       i++;
/*      */     } 
/* 2974 */     if (match || (preserveAllTokens && lastMatch)) {
/* 2975 */       list.add(str.substring(start, i));
/*      */     }
/* 2977 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars) {
/* 3014 */     return splitWorker(str, separatorChars, -1, true);
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
/*      */   public static String[] splitPreserveAllTokens(String str, String separatorChars, int max) {
/* 3054 */     return splitWorker(str, separatorChars, max, true);
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
/*      */   private static String[] splitWorker(String str, String separatorChars, int max, boolean preserveAllTokens) {
/* 3076 */     if (str == null) {
/* 3077 */       return null;
/*      */     }
/* 3079 */     int len = str.length();
/* 3080 */     if (len == 0) {
/* 3081 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3083 */     List<String> list = new ArrayList<String>();
/* 3084 */     int sizePlus1 = 1;
/* 3085 */     int i = 0, start = 0;
/* 3086 */     boolean match = false;
/* 3087 */     boolean lastMatch = false;
/* 3088 */     if (separatorChars == null) {
/*      */       
/* 3090 */       while (i < len) {
/* 3091 */         if (Character.isWhitespace(str.charAt(i))) {
/* 3092 */           if (match || preserveAllTokens) {
/* 3093 */             lastMatch = true;
/* 3094 */             if (sizePlus1++ == max) {
/* 3095 */               i = len;
/* 3096 */               lastMatch = false;
/*      */             } 
/* 3098 */             list.add(str.substring(start, i));
/* 3099 */             match = false;
/*      */           } 
/* 3101 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3104 */         lastMatch = false;
/* 3105 */         match = true;
/* 3106 */         i++;
/*      */       } 
/* 3108 */     } else if (separatorChars.length() == 1) {
/*      */       
/* 3110 */       char sep = separatorChars.charAt(0);
/* 3111 */       while (i < len) {
/* 3112 */         if (str.charAt(i) == sep) {
/* 3113 */           if (match || preserveAllTokens) {
/* 3114 */             lastMatch = true;
/* 3115 */             if (sizePlus1++ == max) {
/* 3116 */               i = len;
/* 3117 */               lastMatch = false;
/*      */             } 
/* 3119 */             list.add(str.substring(start, i));
/* 3120 */             match = false;
/*      */           } 
/* 3122 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3125 */         lastMatch = false;
/* 3126 */         match = true;
/* 3127 */         i++;
/*      */       } 
/*      */     } else {
/*      */       
/* 3131 */       while (i < len) {
/* 3132 */         if (separatorChars.indexOf(str.charAt(i)) >= 0) {
/* 3133 */           if (match || preserveAllTokens) {
/* 3134 */             lastMatch = true;
/* 3135 */             if (sizePlus1++ == max) {
/* 3136 */               i = len;
/* 3137 */               lastMatch = false;
/*      */             } 
/* 3139 */             list.add(str.substring(start, i));
/* 3140 */             match = false;
/*      */           } 
/* 3142 */           start = ++i;
/*      */           continue;
/*      */         } 
/* 3145 */         lastMatch = false;
/* 3146 */         match = true;
/* 3147 */         i++;
/*      */       } 
/*      */     } 
/* 3150 */     if (match || (preserveAllTokens && lastMatch)) {
/* 3151 */       list.add(str.substring(start, i));
/*      */     }
/* 3153 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static String[] splitByCharacterType(String str) {
/* 3176 */     return splitByCharacterType(str, false);
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
/*      */   public static String[] splitByCharacterTypeCamelCase(String str) {
/* 3204 */     return splitByCharacterType(str, true);
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
/*      */   private static String[] splitByCharacterType(String str, boolean camelCase) {
/* 3222 */     if (str == null) {
/* 3223 */       return null;
/*      */     }
/* 3225 */     if (str.isEmpty()) {
/* 3226 */       return ArrayUtils.EMPTY_STRING_ARRAY;
/*      */     }
/* 3228 */     char[] c = str.toCharArray();
/* 3229 */     List<String> list = new ArrayList<String>();
/* 3230 */     int tokenStart = 0;
/* 3231 */     int currentType = Character.getType(c[tokenStart]);
/* 3232 */     for (int pos = tokenStart + 1; pos < c.length; pos++) {
/* 3233 */       int type = Character.getType(c[pos]);
/* 3234 */       if (type != currentType) {
/*      */ 
/*      */         
/* 3237 */         if (camelCase && type == 2 && currentType == 1) {
/* 3238 */           int newTokenStart = pos - 1;
/* 3239 */           if (newTokenStart != tokenStart) {
/* 3240 */             list.add(new String(c, tokenStart, newTokenStart - tokenStart));
/* 3241 */             tokenStart = newTokenStart;
/*      */           } 
/*      */         } else {
/* 3244 */           list.add(new String(c, tokenStart, pos - tokenStart));
/* 3245 */           tokenStart = pos;
/*      */         } 
/* 3247 */         currentType = type;
/*      */       } 
/* 3249 */     }  list.add(new String(c, tokenStart, c.length - tokenStart));
/* 3250 */     return list.<String>toArray(new String[list.size()]);
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
/*      */   public static <T> String join(T... elements) {
/* 3278 */     return join((Object[])elements, (String)null);
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
/*      */   public static String join(Object[] array, char separator) {
/* 3304 */     if (array == null) {
/* 3305 */       return null;
/*      */     }
/* 3307 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(long[] array, char separator) {
/* 3336 */     if (array == null) {
/* 3337 */       return null;
/*      */     }
/* 3339 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(int[] array, char separator) {
/* 3368 */     if (array == null) {
/* 3369 */       return null;
/*      */     }
/* 3371 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(short[] array, char separator) {
/* 3400 */     if (array == null) {
/* 3401 */       return null;
/*      */     }
/* 3403 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(byte[] array, char separator) {
/* 3432 */     if (array == null) {
/* 3433 */       return null;
/*      */     }
/* 3435 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(char[] array, char separator) {
/* 3464 */     if (array == null) {
/* 3465 */       return null;
/*      */     }
/* 3467 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(float[] array, char separator) {
/* 3496 */     if (array == null) {
/* 3497 */       return null;
/*      */     }
/* 3499 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(double[] array, char separator) {
/* 3528 */     if (array == null) {
/* 3529 */       return null;
/*      */     }
/* 3531 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, char separator, int startIndex, int endIndex) {
/* 3562 */     if (array == null) {
/* 3563 */       return null;
/*      */     }
/* 3565 */     int noOfItems = endIndex - startIndex;
/* 3566 */     if (noOfItems <= 0) {
/* 3567 */       return "";
/*      */     }
/* 3569 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3570 */     for (int i = startIndex; i < endIndex; i++) {
/* 3571 */       if (i > startIndex) {
/* 3572 */         buf.append(separator);
/*      */       }
/* 3574 */       if (array[i] != null) {
/* 3575 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 3578 */     return buf.toString();
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
/*      */   public static String join(long[] array, char separator, int startIndex, int endIndex) {
/* 3613 */     if (array == null) {
/* 3614 */       return null;
/*      */     }
/* 3616 */     int noOfItems = endIndex - startIndex;
/* 3617 */     if (noOfItems <= 0) {
/* 3618 */       return "";
/*      */     }
/* 3620 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3621 */     for (int i = startIndex; i < endIndex; i++) {
/* 3622 */       if (i > startIndex) {
/* 3623 */         buf.append(separator);
/*      */       }
/* 3625 */       buf.append(array[i]);
/*      */     } 
/* 3627 */     return buf.toString();
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
/*      */   public static String join(int[] array, char separator, int startIndex, int endIndex) {
/* 3662 */     if (array == null) {
/* 3663 */       return null;
/*      */     }
/* 3665 */     int noOfItems = endIndex - startIndex;
/* 3666 */     if (noOfItems <= 0) {
/* 3667 */       return "";
/*      */     }
/* 3669 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3670 */     for (int i = startIndex; i < endIndex; i++) {
/* 3671 */       if (i > startIndex) {
/* 3672 */         buf.append(separator);
/*      */       }
/* 3674 */       buf.append(array[i]);
/*      */     } 
/* 3676 */     return buf.toString();
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
/*      */   public static String join(byte[] array, char separator, int startIndex, int endIndex) {
/* 3711 */     if (array == null) {
/* 3712 */       return null;
/*      */     }
/* 3714 */     int noOfItems = endIndex - startIndex;
/* 3715 */     if (noOfItems <= 0) {
/* 3716 */       return "";
/*      */     }
/* 3718 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3719 */     for (int i = startIndex; i < endIndex; i++) {
/* 3720 */       if (i > startIndex) {
/* 3721 */         buf.append(separator);
/*      */       }
/* 3723 */       buf.append(array[i]);
/*      */     } 
/* 3725 */     return buf.toString();
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
/*      */   public static String join(short[] array, char separator, int startIndex, int endIndex) {
/* 3760 */     if (array == null) {
/* 3761 */       return null;
/*      */     }
/* 3763 */     int noOfItems = endIndex - startIndex;
/* 3764 */     if (noOfItems <= 0) {
/* 3765 */       return "";
/*      */     }
/* 3767 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3768 */     for (int i = startIndex; i < endIndex; i++) {
/* 3769 */       if (i > startIndex) {
/* 3770 */         buf.append(separator);
/*      */       }
/* 3772 */       buf.append(array[i]);
/*      */     } 
/* 3774 */     return buf.toString();
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
/*      */   public static String join(char[] array, char separator, int startIndex, int endIndex) {
/* 3809 */     if (array == null) {
/* 3810 */       return null;
/*      */     }
/* 3812 */     int noOfItems = endIndex - startIndex;
/* 3813 */     if (noOfItems <= 0) {
/* 3814 */       return "";
/*      */     }
/* 3816 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3817 */     for (int i = startIndex; i < endIndex; i++) {
/* 3818 */       if (i > startIndex) {
/* 3819 */         buf.append(separator);
/*      */       }
/* 3821 */       buf.append(array[i]);
/*      */     } 
/* 3823 */     return buf.toString();
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
/*      */   public static String join(double[] array, char separator, int startIndex, int endIndex) {
/* 3858 */     if (array == null) {
/* 3859 */       return null;
/*      */     }
/* 3861 */     int noOfItems = endIndex - startIndex;
/* 3862 */     if (noOfItems <= 0) {
/* 3863 */       return "";
/*      */     }
/* 3865 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3866 */     for (int i = startIndex; i < endIndex; i++) {
/* 3867 */       if (i > startIndex) {
/* 3868 */         buf.append(separator);
/*      */       }
/* 3870 */       buf.append(array[i]);
/*      */     } 
/* 3872 */     return buf.toString();
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
/*      */   public static String join(float[] array, char separator, int startIndex, int endIndex) {
/* 3907 */     if (array == null) {
/* 3908 */       return null;
/*      */     }
/* 3910 */     int noOfItems = endIndex - startIndex;
/* 3911 */     if (noOfItems <= 0) {
/* 3912 */       return "";
/*      */     }
/* 3914 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/* 3915 */     for (int i = startIndex; i < endIndex; i++) {
/* 3916 */       if (i > startIndex) {
/* 3917 */         buf.append(separator);
/*      */       }
/* 3919 */       buf.append(array[i]);
/*      */     } 
/* 3921 */     return buf.toString();
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
/*      */   public static String join(Object[] array, String separator) {
/* 3949 */     if (array == null) {
/* 3950 */       return null;
/*      */     }
/* 3952 */     return join(array, separator, 0, array.length);
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
/*      */   public static String join(Object[] array, String separator, int startIndex, int endIndex) {
/* 3991 */     if (array == null) {
/* 3992 */       return null;
/*      */     }
/* 3994 */     if (separator == null) {
/* 3995 */       separator = "";
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 4000 */     int noOfItems = endIndex - startIndex;
/* 4001 */     if (noOfItems <= 0) {
/* 4002 */       return "";
/*      */     }
/*      */     
/* 4005 */     StringBuilder buf = new StringBuilder(noOfItems * 16);
/*      */     
/* 4007 */     for (int i = startIndex; i < endIndex; i++) {
/* 4008 */       if (i > startIndex) {
/* 4009 */         buf.append(separator);
/*      */       }
/* 4011 */       if (array[i] != null) {
/* 4012 */         buf.append(array[i]);
/*      */       }
/*      */     } 
/* 4015 */     return buf.toString();
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
/*      */   public static String join(Iterator<?> iterator, char separator) {
/* 4035 */     if (iterator == null) {
/* 4036 */       return null;
/*      */     }
/* 4038 */     if (!iterator.hasNext()) {
/* 4039 */       return "";
/*      */     }
/* 4041 */     Object first = iterator.next();
/* 4042 */     if (!iterator.hasNext()) {
/*      */       
/* 4044 */       String result = ObjectUtils.toString(first);
/* 4045 */       return result;
/*      */     } 
/*      */ 
/*      */     
/* 4049 */     StringBuilder buf = new StringBuilder(256);
/* 4050 */     if (first != null) {
/* 4051 */       buf.append(first);
/*      */     }
/*      */     
/* 4054 */     while (iterator.hasNext()) {
/* 4055 */       buf.append(separator);
/* 4056 */       Object obj = iterator.next();
/* 4057 */       if (obj != null) {
/* 4058 */         buf.append(obj);
/*      */       }
/*      */     } 
/*      */     
/* 4062 */     return buf.toString();
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
/*      */   public static String join(Iterator<?> iterator, String separator) {
/* 4081 */     if (iterator == null) {
/* 4082 */       return null;
/*      */     }
/* 4084 */     if (!iterator.hasNext()) {
/* 4085 */       return "";
/*      */     }
/* 4087 */     Object first = iterator.next();
/* 4088 */     if (!iterator.hasNext()) {
/*      */       
/* 4090 */       String result = ObjectUtils.toString(first);
/* 4091 */       return result;
/*      */     } 
/*      */ 
/*      */     
/* 4095 */     StringBuilder buf = new StringBuilder(256);
/* 4096 */     if (first != null) {
/* 4097 */       buf.append(first);
/*      */     }
/*      */     
/* 4100 */     while (iterator.hasNext()) {
/* 4101 */       if (separator != null) {
/* 4102 */         buf.append(separator);
/*      */       }
/* 4104 */       Object obj = iterator.next();
/* 4105 */       if (obj != null) {
/* 4106 */         buf.append(obj);
/*      */       }
/*      */     } 
/* 4109 */     return buf.toString();
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
/*      */   public static String join(Iterable<?> iterable, char separator) {
/* 4127 */     if (iterable == null) {
/* 4128 */       return null;
/*      */     }
/* 4130 */     return join(iterable.iterator(), separator);
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
/*      */   public static String join(Iterable<?> iterable, String separator) {
/* 4148 */     if (iterable == null) {
/* 4149 */       return null;
/*      */     }
/* 4151 */     return join(iterable.iterator(), separator);
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
/*      */   public static String deleteWhitespace(String str) {
/* 4171 */     if (isEmpty(str)) {
/* 4172 */       return str;
/*      */     }
/* 4174 */     int sz = str.length();
/* 4175 */     char[] chs = new char[sz];
/* 4176 */     int count = 0;
/* 4177 */     for (int i = 0; i < sz; i++) {
/* 4178 */       if (!Character.isWhitespace(str.charAt(i))) {
/* 4179 */         chs[count++] = str.charAt(i);
/*      */       }
/*      */     } 
/* 4182 */     if (count == sz) {
/* 4183 */       return str;
/*      */     }
/* 4185 */     return new String(chs, 0, count);
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
/*      */   public static String removeStart(String str, String remove) {
/* 4215 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4216 */       return str;
/*      */     }
/* 4218 */     if (str.startsWith(remove)) {
/* 4219 */       return str.substring(remove.length());
/*      */     }
/* 4221 */     return str;
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
/*      */   public static String removeStartIgnoreCase(String str, String remove) {
/* 4250 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4251 */       return str;
/*      */     }
/* 4253 */     if (startsWithIgnoreCase(str, remove)) {
/* 4254 */       return str.substring(remove.length());
/*      */     }
/* 4256 */     return str;
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
/*      */   public static String removeEnd(String str, String remove) {
/* 4284 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4285 */       return str;
/*      */     }
/* 4287 */     if (str.endsWith(remove)) {
/* 4288 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 4290 */     return str;
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
/*      */   public static String removeEndIgnoreCase(String str, String remove) {
/* 4320 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4321 */       return str;
/*      */     }
/* 4323 */     if (endsWithIgnoreCase(str, remove)) {
/* 4324 */       return str.substring(0, str.length() - remove.length());
/*      */     }
/* 4326 */     return str;
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
/*      */   public static String remove(String str, String remove) {
/* 4353 */     if (isEmpty(str) || isEmpty(remove)) {
/* 4354 */       return str;
/*      */     }
/* 4356 */     return replace(str, remove, "", -1);
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
/*      */   public static String remove(String str, char remove) {
/* 4379 */     if (isEmpty(str) || str.indexOf(remove) == -1) {
/* 4380 */       return str;
/*      */     }
/* 4382 */     char[] chars = str.toCharArray();
/* 4383 */     int pos = 0;
/* 4384 */     for (int i = 0; i < chars.length; i++) {
/* 4385 */       if (chars[i] != remove) {
/* 4386 */         chars[pos++] = chars[i];
/*      */       }
/*      */     } 
/* 4389 */     return new String(chars, 0, pos);
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
/*      */   public static String replaceOnce(String text, String searchString, String replacement) {
/* 4418 */     return replace(text, searchString, replacement, 1);
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
/*      */   public static String replacePattern(String source, String regex, String replacement) {
/* 4442 */     return Pattern.compile(regex, 32).matcher(source).replaceAll(replacement);
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
/*      */   public static String removePattern(String source, String regex) {
/* 4458 */     return replacePattern(source, regex, "");
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
/*      */   public static String replace(String text, String searchString, String replacement) {
/* 4485 */     return replace(text, searchString, replacement, -1);
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
/*      */   public static String replace(String text, String searchString, String replacement, int max) {
/* 4517 */     if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0) {
/* 4518 */       return text;
/*      */     }
/* 4520 */     int start = 0;
/* 4521 */     int end = text.indexOf(searchString, start);
/* 4522 */     if (end == -1) {
/* 4523 */       return text;
/*      */     }
/* 4525 */     int replLength = searchString.length();
/* 4526 */     int increase = replacement.length() - replLength;
/* 4527 */     increase = (increase < 0) ? 0 : increase;
/* 4528 */     increase *= (max < 0) ? 16 : ((max > 64) ? 64 : max);
/* 4529 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/* 4530 */     while (end != -1) {
/* 4531 */       buf.append(text.substring(start, end)).append(replacement);
/* 4532 */       start = end + replLength;
/* 4533 */       if (--max == 0) {
/*      */         break;
/*      */       }
/* 4536 */       end = text.indexOf(searchString, start);
/*      */     } 
/* 4538 */     buf.append(text.substring(start));
/* 4539 */     return buf.toString();
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
/*      */   public static String replaceEach(String text, String[] searchList, String[] replacementList) {
/* 4582 */     return replaceEach(text, searchList, replacementList, false, 0);
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
/*      */   public static String replaceEachRepeatedly(String text, String[] searchList, String[] replacementList) {
/* 4632 */     int timeToLive = (searchList == null) ? 0 : searchList.length;
/* 4633 */     return replaceEach(text, searchList, replacementList, true, timeToLive);
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
/*      */   private static String replaceEach(String text, String[] searchList, String[] replacementList, boolean repeat, int timeToLive) {
/* 4690 */     if (text == null || text.isEmpty() || searchList == null || searchList.length == 0 || replacementList == null || replacementList.length == 0)
/*      */     {
/* 4692 */       return text;
/*      */     }
/*      */ 
/*      */     
/* 4696 */     if (timeToLive < 0) {
/* 4697 */       throw new IllegalStateException("Aborting to protect against StackOverflowError - output of one loop is the input of another");
/*      */     }
/*      */ 
/*      */     
/* 4701 */     int searchLength = searchList.length;
/* 4702 */     int replacementLength = replacementList.length;
/*      */ 
/*      */     
/* 4705 */     if (searchLength != replacementLength) {
/* 4706 */       throw new IllegalArgumentException("Search and Replace array lengths don't match: " + searchLength + " vs " + replacementLength);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 4713 */     boolean[] noMoreMatchesForReplIndex = new boolean[searchLength];
/*      */ 
/*      */     
/* 4716 */     int textIndex = -1;
/* 4717 */     int replaceIndex = -1;
/* 4718 */     int tempIndex = -1;
/*      */ 
/*      */ 
/*      */     
/* 4722 */     for (int i = 0; i < searchLength; i++) {
/* 4723 */       if (!noMoreMatchesForReplIndex[i] && searchList[i] != null && !searchList[i].isEmpty() && replacementList[i] != null) {
/*      */ 
/*      */ 
/*      */         
/* 4727 */         tempIndex = text.indexOf(searchList[i]);
/*      */ 
/*      */         
/* 4730 */         if (tempIndex == -1) {
/* 4731 */           noMoreMatchesForReplIndex[i] = true;
/*      */         }
/* 4733 */         else if (textIndex == -1 || tempIndex < textIndex) {
/* 4734 */           textIndex = tempIndex;
/* 4735 */           replaceIndex = i;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 4742 */     if (textIndex == -1) {
/* 4743 */       return text;
/*      */     }
/*      */     
/* 4746 */     int start = 0;
/*      */ 
/*      */     
/* 4749 */     int increase = 0;
/*      */ 
/*      */     
/* 4752 */     for (int j = 0; j < searchList.length; j++) {
/* 4753 */       if (searchList[j] != null && replacementList[j] != null) {
/*      */ 
/*      */         
/* 4756 */         int greater = replacementList[j].length() - searchList[j].length();
/* 4757 */         if (greater > 0) {
/* 4758 */           increase += 3 * greater;
/*      */         }
/*      */       } 
/*      */     } 
/* 4762 */     increase = Math.min(increase, text.length() / 5);
/*      */     
/* 4764 */     StringBuilder buf = new StringBuilder(text.length() + increase);
/*      */     
/* 4766 */     while (textIndex != -1) {
/*      */       int m;
/* 4768 */       for (m = start; m < textIndex; m++) {
/* 4769 */         buf.append(text.charAt(m));
/*      */       }
/* 4771 */       buf.append(replacementList[replaceIndex]);
/*      */       
/* 4773 */       start = textIndex + searchList[replaceIndex].length();
/*      */       
/* 4775 */       textIndex = -1;
/* 4776 */       replaceIndex = -1;
/* 4777 */       tempIndex = -1;
/*      */ 
/*      */       
/* 4780 */       for (m = 0; m < searchLength; m++) {
/* 4781 */         if (!noMoreMatchesForReplIndex[m] && searchList[m] != null && !searchList[m].isEmpty() && replacementList[m] != null) {
/*      */ 
/*      */ 
/*      */           
/* 4785 */           tempIndex = text.indexOf(searchList[m], start);
/*      */ 
/*      */           
/* 4788 */           if (tempIndex == -1) {
/* 4789 */             noMoreMatchesForReplIndex[m] = true;
/*      */           }
/* 4791 */           else if (textIndex == -1 || tempIndex < textIndex) {
/* 4792 */             textIndex = tempIndex;
/* 4793 */             replaceIndex = m;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 4800 */     int textLength = text.length();
/* 4801 */     for (int k = start; k < textLength; k++) {
/* 4802 */       buf.append(text.charAt(k));
/*      */     }
/* 4804 */     String result = buf.toString();
/* 4805 */     if (!repeat) {
/* 4806 */       return result;
/*      */     }
/*      */     
/* 4809 */     return replaceEach(result, searchList, replacementList, repeat, timeToLive - 1);
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
/*      */   public static String replaceChars(String str, char searchChar, char replaceChar) {
/* 4835 */     if (str == null) {
/* 4836 */       return null;
/*      */     }
/* 4838 */     return str.replace(searchChar, replaceChar);
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
/*      */   public static String replaceChars(String str, String searchChars, String replaceChars) {
/* 4878 */     if (isEmpty(str) || isEmpty(searchChars)) {
/* 4879 */       return str;
/*      */     }
/* 4881 */     if (replaceChars == null) {
/* 4882 */       replaceChars = "";
/*      */     }
/* 4884 */     boolean modified = false;
/* 4885 */     int replaceCharsLength = replaceChars.length();
/* 4886 */     int strLength = str.length();
/* 4887 */     StringBuilder buf = new StringBuilder(strLength);
/* 4888 */     for (int i = 0; i < strLength; i++) {
/* 4889 */       char ch = str.charAt(i);
/* 4890 */       int index = searchChars.indexOf(ch);
/* 4891 */       if (index >= 0) {
/* 4892 */         modified = true;
/* 4893 */         if (index < replaceCharsLength) {
/* 4894 */           buf.append(replaceChars.charAt(index));
/*      */         }
/*      */       } else {
/* 4897 */         buf.append(ch);
/*      */       } 
/*      */     } 
/* 4900 */     if (modified) {
/* 4901 */       return buf.toString();
/*      */     }
/* 4903 */     return str;
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
/*      */   public static String overlay(String str, String overlay, int start, int end) {
/* 4938 */     if (str == null) {
/* 4939 */       return null;
/*      */     }
/* 4941 */     if (overlay == null) {
/* 4942 */       overlay = "";
/*      */     }
/* 4944 */     int len = str.length();
/* 4945 */     if (start < 0) {
/* 4946 */       start = 0;
/*      */     }
/* 4948 */     if (start > len) {
/* 4949 */       start = len;
/*      */     }
/* 4951 */     if (end < 0) {
/* 4952 */       end = 0;
/*      */     }
/* 4954 */     if (end > len) {
/* 4955 */       end = len;
/*      */     }
/* 4957 */     if (start > end) {
/* 4958 */       int temp = start;
/* 4959 */       start = end;
/* 4960 */       end = temp;
/*      */     } 
/* 4962 */     return (new StringBuilder(len + start - end + overlay.length() + 1)).append(str.substring(0, start)).append(overlay).append(str.substring(end)).toString();
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
/*      */   public static String chomp(String str) {
/* 4997 */     if (isEmpty(str)) {
/* 4998 */       return str;
/*      */     }
/*      */     
/* 5001 */     if (str.length() == 1) {
/* 5002 */       char ch = str.charAt(0);
/* 5003 */       if (ch == '\r' || ch == '\n') {
/* 5004 */         return "";
/*      */       }
/* 5006 */       return str;
/*      */     } 
/*      */     
/* 5009 */     int lastIdx = str.length() - 1;
/* 5010 */     char last = str.charAt(lastIdx);
/*      */     
/* 5012 */     if (last == '\n') {
/* 5013 */       if (str.charAt(lastIdx - 1) == '\r') {
/* 5014 */         lastIdx--;
/*      */       }
/* 5016 */     } else if (last != '\r') {
/* 5017 */       lastIdx++;
/*      */     } 
/* 5019 */     return str.substring(0, lastIdx);
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
/*      */   @Deprecated
/*      */   public static String chomp(String str, String separator) {
/* 5051 */     return removeEnd(str, separator);
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
/*      */   public static String chop(String str) {
/* 5080 */     if (str == null) {
/* 5081 */       return null;
/*      */     }
/* 5083 */     int strLen = str.length();
/* 5084 */     if (strLen < 2) {
/* 5085 */       return "";
/*      */     }
/* 5087 */     int lastIdx = strLen - 1;
/* 5088 */     String ret = str.substring(0, lastIdx);
/* 5089 */     char last = str.charAt(lastIdx);
/* 5090 */     if (last == '\n' && ret.charAt(lastIdx - 1) == '\r') {
/* 5091 */       return ret.substring(0, lastIdx - 1);
/*      */     }
/* 5093 */     return ret;
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
/*      */   public static String repeat(String str, int repeat) {
/*      */     char ch0, ch1, output2[];
/*      */     int i;
/* 5122 */     if (str == null) {
/* 5123 */       return null;
/*      */     }
/* 5125 */     if (repeat <= 0) {
/* 5126 */       return "";
/*      */     }
/* 5128 */     int inputLength = str.length();
/* 5129 */     if (repeat == 1 || inputLength == 0) {
/* 5130 */       return str;
/*      */     }
/* 5132 */     if (inputLength == 1 && repeat <= 8192) {
/* 5133 */       return repeat(str.charAt(0), repeat);
/*      */     }
/*      */     
/* 5136 */     int outputLength = inputLength * repeat;
/* 5137 */     switch (inputLength) {
/*      */       case 1:
/* 5139 */         return repeat(str.charAt(0), repeat);
/*      */       case 2:
/* 5141 */         ch0 = str.charAt(0);
/* 5142 */         ch1 = str.charAt(1);
/* 5143 */         output2 = new char[outputLength];
/* 5144 */         for (i = repeat * 2 - 2; i >= 0; i--, i--) {
/* 5145 */           output2[i] = ch0;
/* 5146 */           output2[i + 1] = ch1;
/*      */         } 
/* 5148 */         return new String(output2);
/*      */     } 
/* 5150 */     StringBuilder buf = new StringBuilder(outputLength);
/* 5151 */     for (int j = 0; j < repeat; j++) {
/* 5152 */       buf.append(str);
/*      */     }
/* 5154 */     return buf.toString();
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
/*      */   public static String repeat(String str, String separator, int repeat) {
/* 5179 */     if (str == null || separator == null) {
/* 5180 */       return repeat(str, repeat);
/*      */     }
/*      */     
/* 5183 */     String result = repeat(str + separator, repeat);
/* 5184 */     return removeEnd(result, separator);
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
/*      */   public static String repeat(char ch, int repeat) {
/* 5210 */     char[] buf = new char[repeat];
/* 5211 */     for (int i = repeat - 1; i >= 0; i--) {
/* 5212 */       buf[i] = ch;
/*      */     }
/* 5214 */     return new String(buf);
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
/*      */   public static String rightPad(String str, int size) {
/* 5237 */     return rightPad(str, size, ' ');
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
/*      */   public static String rightPad(String str, int size, char padChar) {
/* 5262 */     if (str == null) {
/* 5263 */       return null;
/*      */     }
/* 5265 */     int pads = size - str.length();
/* 5266 */     if (pads <= 0) {
/* 5267 */       return str;
/*      */     }
/* 5269 */     if (pads > 8192) {
/* 5270 */       return rightPad(str, size, String.valueOf(padChar));
/*      */     }
/* 5272 */     return str.concat(repeat(padChar, pads));
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
/*      */   public static String rightPad(String str, int size, String padStr) {
/* 5299 */     if (str == null) {
/* 5300 */       return null;
/*      */     }
/* 5302 */     if (isEmpty(padStr)) {
/* 5303 */       padStr = " ";
/*      */     }
/* 5305 */     int padLen = padStr.length();
/* 5306 */     int strLen = str.length();
/* 5307 */     int pads = size - strLen;
/* 5308 */     if (pads <= 0) {
/* 5309 */       return str;
/*      */     }
/* 5311 */     if (padLen == 1 && pads <= 8192) {
/* 5312 */       return rightPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 5315 */     if (pads == padLen)
/* 5316 */       return str.concat(padStr); 
/* 5317 */     if (pads < padLen) {
/* 5318 */       return str.concat(padStr.substring(0, pads));
/*      */     }
/* 5320 */     char[] padding = new char[pads];
/* 5321 */     char[] padChars = padStr.toCharArray();
/* 5322 */     for (int i = 0; i < pads; i++) {
/* 5323 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 5325 */     return str.concat(new String(padding));
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
/*      */   public static String leftPad(String str, int size) {
/* 5349 */     return leftPad(str, size, ' ');
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
/*      */   public static String leftPad(String str, int size, char padChar) {
/* 5374 */     if (str == null) {
/* 5375 */       return null;
/*      */     }
/* 5377 */     int pads = size - str.length();
/* 5378 */     if (pads <= 0) {
/* 5379 */       return str;
/*      */     }
/* 5381 */     if (pads > 8192) {
/* 5382 */       return leftPad(str, size, String.valueOf(padChar));
/*      */     }
/* 5384 */     return repeat(padChar, pads).concat(str);
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
/*      */   public static String leftPad(String str, int size, String padStr) {
/* 5411 */     if (str == null) {
/* 5412 */       return null;
/*      */     }
/* 5414 */     if (isEmpty(padStr)) {
/* 5415 */       padStr = " ";
/*      */     }
/* 5417 */     int padLen = padStr.length();
/* 5418 */     int strLen = str.length();
/* 5419 */     int pads = size - strLen;
/* 5420 */     if (pads <= 0) {
/* 5421 */       return str;
/*      */     }
/* 5423 */     if (padLen == 1 && pads <= 8192) {
/* 5424 */       return leftPad(str, size, padStr.charAt(0));
/*      */     }
/*      */     
/* 5427 */     if (pads == padLen)
/* 5428 */       return padStr.concat(str); 
/* 5429 */     if (pads < padLen) {
/* 5430 */       return padStr.substring(0, pads).concat(str);
/*      */     }
/* 5432 */     char[] padding = new char[pads];
/* 5433 */     char[] padChars = padStr.toCharArray();
/* 5434 */     for (int i = 0; i < pads; i++) {
/* 5435 */       padding[i] = padChars[i % padLen];
/*      */     }
/* 5437 */     return (new String(padding)).concat(str);
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
/*      */   public static int length(CharSequence cs) {
/* 5453 */     return (cs == null) ? 0 : cs.length();
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
/*      */   public static String center(String str, int size) {
/* 5482 */     return center(str, size, ' ');
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
/*      */   public static String center(String str, int size, char padChar) {
/* 5510 */     if (str == null || size <= 0) {
/* 5511 */       return str;
/*      */     }
/* 5513 */     int strLen = str.length();
/* 5514 */     int pads = size - strLen;
/* 5515 */     if (pads <= 0) {
/* 5516 */       return str;
/*      */     }
/* 5518 */     str = leftPad(str, strLen + pads / 2, padChar);
/* 5519 */     str = rightPad(str, size, padChar);
/* 5520 */     return str;
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
/*      */   public static String center(String str, int size, String padStr) {
/* 5550 */     if (str == null || size <= 0) {
/* 5551 */       return str;
/*      */     }
/* 5553 */     if (isEmpty(padStr)) {
/* 5554 */       padStr = " ";
/*      */     }
/* 5556 */     int strLen = str.length();
/* 5557 */     int pads = size - strLen;
/* 5558 */     if (pads <= 0) {
/* 5559 */       return str;
/*      */     }
/* 5561 */     str = leftPad(str, strLen + pads / 2, padStr);
/* 5562 */     str = rightPad(str, size, padStr);
/* 5563 */     return str;
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
/*      */   public static String upperCase(String str) {
/* 5588 */     if (str == null) {
/* 5589 */       return null;
/*      */     }
/* 5591 */     return str.toUpperCase();
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
/*      */   public static String upperCase(String str, Locale locale) {
/* 5611 */     if (str == null) {
/* 5612 */       return null;
/*      */     }
/* 5614 */     return str.toUpperCase(locale);
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
/*      */   public static String lowerCase(String str) {
/* 5637 */     if (str == null) {
/* 5638 */       return null;
/*      */     }
/* 5640 */     return str.toLowerCase();
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
/*      */   public static String lowerCase(String str, Locale locale) {
/* 5660 */     if (str == null) {
/* 5661 */       return null;
/*      */     }
/* 5663 */     return str.toLowerCase(locale);
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
/*      */   public static String capitalize(String str) {
/*      */     int strLen;
/* 5688 */     if (str == null || (strLen = str.length()) == 0) {
/* 5689 */       return str;
/*      */     }
/*      */     
/* 5692 */     char firstChar = str.charAt(0);
/* 5693 */     if (Character.isTitleCase(firstChar))
/*      */     {
/* 5695 */       return str;
/*      */     }
/*      */     
/* 5698 */     return (new StringBuilder(strLen)).append(Character.toTitleCase(firstChar)).append(str.substring(1)).toString();
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
/*      */   public static String uncapitalize(String str) {
/*      */     int strLen;
/* 5726 */     if (str == null || (strLen = str.length()) == 0) {
/* 5727 */       return str;
/*      */     }
/*      */     
/* 5730 */     char firstChar = str.charAt(0);
/* 5731 */     if (Character.isLowerCase(firstChar))
/*      */     {
/* 5733 */       return str;
/*      */     }
/*      */     
/* 5736 */     return (new StringBuilder(strLen)).append(Character.toLowerCase(firstChar)).append(str.substring(1)).toString();
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
/*      */   public static String swapCase(String str) {
/* 5770 */     if (isEmpty(str)) {
/* 5771 */       return str;
/*      */     }
/*      */     
/* 5774 */     char[] buffer = str.toCharArray();
/*      */     
/* 5776 */     for (int i = 0; i < buffer.length; i++) {
/* 5777 */       char ch = buffer[i];
/* 5778 */       if (Character.isUpperCase(ch)) {
/* 5779 */         buffer[i] = Character.toLowerCase(ch);
/* 5780 */       } else if (Character.isTitleCase(ch)) {
/* 5781 */         buffer[i] = Character.toLowerCase(ch);
/* 5782 */       } else if (Character.isLowerCase(ch)) {
/* 5783 */         buffer[i] = Character.toUpperCase(ch);
/*      */       } 
/*      */     } 
/* 5786 */     return new String(buffer);
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
/*      */   public static int countMatches(CharSequence str, CharSequence sub) {
/* 5812 */     if (isEmpty(str) || isEmpty(sub)) {
/* 5813 */       return 0;
/*      */     }
/* 5815 */     int count = 0;
/* 5816 */     int idx = 0;
/* 5817 */     while ((idx = CharSequenceUtils.indexOf(str, sub, idx)) != -1) {
/* 5818 */       count++;
/* 5819 */       idx += sub.length();
/*      */     } 
/* 5821 */     return count;
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
/*      */   public static boolean isAlpha(CharSequence cs) {
/* 5847 */     if (isEmpty(cs)) {
/* 5848 */       return false;
/*      */     }
/* 5850 */     int sz = cs.length();
/* 5851 */     for (int i = 0; i < sz; i++) {
/* 5852 */       if (!Character.isLetter(cs.charAt(i))) {
/* 5853 */         return false;
/*      */       }
/*      */     } 
/* 5856 */     return true;
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
/*      */   public static boolean isAlphaSpace(CharSequence cs) {
/* 5882 */     if (cs == null) {
/* 5883 */       return false;
/*      */     }
/* 5885 */     int sz = cs.length();
/* 5886 */     for (int i = 0; i < sz; i++) {
/* 5887 */       if (!Character.isLetter(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 5888 */         return false;
/*      */       }
/*      */     } 
/* 5891 */     return true;
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
/*      */   public static boolean isAlphanumeric(CharSequence cs) {
/* 5917 */     if (isEmpty(cs)) {
/* 5918 */       return false;
/*      */     }
/* 5920 */     int sz = cs.length();
/* 5921 */     for (int i = 0; i < sz; i++) {
/* 5922 */       if (!Character.isLetterOrDigit(cs.charAt(i))) {
/* 5923 */         return false;
/*      */       }
/*      */     } 
/* 5926 */     return true;
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
/*      */   public static boolean isAlphanumericSpace(CharSequence cs) {
/* 5952 */     if (cs == null) {
/* 5953 */       return false;
/*      */     }
/* 5955 */     int sz = cs.length();
/* 5956 */     for (int i = 0; i < sz; i++) {
/* 5957 */       if (!Character.isLetterOrDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 5958 */         return false;
/*      */       }
/*      */     } 
/* 5961 */     return true;
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
/*      */   public static boolean isAsciiPrintable(CharSequence cs) {
/* 5991 */     if (cs == null) {
/* 5992 */       return false;
/*      */     }
/* 5994 */     int sz = cs.length();
/* 5995 */     for (int i = 0; i < sz; i++) {
/* 5996 */       if (!CharUtils.isAsciiPrintable(cs.charAt(i))) {
/* 5997 */         return false;
/*      */       }
/*      */     } 
/* 6000 */     return true;
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
/*      */   public static boolean isNumeric(CharSequence cs) {
/* 6034 */     if (isEmpty(cs)) {
/* 6035 */       return false;
/*      */     }
/* 6037 */     int sz = cs.length();
/* 6038 */     for (int i = 0; i < sz; i++) {
/* 6039 */       if (!Character.isDigit(cs.charAt(i))) {
/* 6040 */         return false;
/*      */       }
/*      */     } 
/* 6043 */     return true;
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
/*      */   public static boolean isNumericSpace(CharSequence cs) {
/* 6071 */     if (cs == null) {
/* 6072 */       return false;
/*      */     }
/* 6074 */     int sz = cs.length();
/* 6075 */     for (int i = 0; i < sz; i++) {
/* 6076 */       if (!Character.isDigit(cs.charAt(i)) && cs.charAt(i) != ' ') {
/* 6077 */         return false;
/*      */       }
/*      */     } 
/* 6080 */     return true;
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
/*      */   public static boolean isWhitespace(CharSequence cs) {
/* 6104 */     if (cs == null) {
/* 6105 */       return false;
/*      */     }
/* 6107 */     int sz = cs.length();
/* 6108 */     for (int i = 0; i < sz; i++) {
/* 6109 */       if (!Character.isWhitespace(cs.charAt(i))) {
/* 6110 */         return false;
/*      */       }
/*      */     } 
/* 6113 */     return true;
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
/*      */   public static boolean isAllLowerCase(CharSequence cs) {
/* 6136 */     if (cs == null || isEmpty(cs)) {
/* 6137 */       return false;
/*      */     }
/* 6139 */     int sz = cs.length();
/* 6140 */     for (int i = 0; i < sz; i++) {
/* 6141 */       if (!Character.isLowerCase(cs.charAt(i))) {
/* 6142 */         return false;
/*      */       }
/*      */     } 
/* 6145 */     return true;
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
/*      */   public static boolean isAllUpperCase(CharSequence cs) {
/* 6168 */     if (cs == null || isEmpty(cs)) {
/* 6169 */       return false;
/*      */     }
/* 6171 */     int sz = cs.length();
/* 6172 */     for (int i = 0; i < sz; i++) {
/* 6173 */       if (!Character.isUpperCase(cs.charAt(i))) {
/* 6174 */         return false;
/*      */       }
/*      */     } 
/* 6177 */     return true;
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
/*      */   public static String defaultString(String str) {
/* 6199 */     return (str == null) ? "" : str;
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
/*      */   public static String defaultString(String str, String defaultStr) {
/* 6220 */     return (str == null) ? defaultStr : str;
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
/*      */   public static <T extends CharSequence> T defaultIfBlank(T str, T defaultStr) {
/* 6242 */     return isBlank((CharSequence)str) ? defaultStr : str;
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
/*      */   public static <T extends CharSequence> T defaultIfEmpty(T str, T defaultStr) {
/* 6264 */     return isEmpty((CharSequence)str) ? defaultStr : str;
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
/*      */   public static String reverse(String str) {
/* 6284 */     if (str == null) {
/* 6285 */       return null;
/*      */     }
/* 6287 */     return (new StringBuilder(str)).reverse().toString();
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
/*      */   public static String reverseDelimited(String str, char separatorChar) {
/* 6310 */     if (str == null) {
/* 6311 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 6315 */     String[] strs = split(str, separatorChar);
/* 6316 */     ArrayUtils.reverse((Object[])strs);
/* 6317 */     return join((Object[])strs, separatorChar);
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
/*      */   public static String abbreviate(String str, int maxWidth) {
/* 6354 */     return abbreviate(str, 0, maxWidth);
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
/*      */   public static String abbreviate(String str, int offset, int maxWidth) {
/* 6393 */     if (str == null) {
/* 6394 */       return null;
/*      */     }
/* 6396 */     if (maxWidth < 4) {
/* 6397 */       throw new IllegalArgumentException("Minimum abbreviation width is 4");
/*      */     }
/* 6399 */     if (str.length() <= maxWidth) {
/* 6400 */       return str;
/*      */     }
/* 6402 */     if (offset > str.length()) {
/* 6403 */       offset = str.length();
/*      */     }
/* 6405 */     if (str.length() - offset < maxWidth - 3) {
/* 6406 */       offset = str.length() - maxWidth - 3;
/*      */     }
/* 6408 */     String abrevMarker = "...";
/* 6409 */     if (offset <= 4) {
/* 6410 */       return str.substring(0, maxWidth - 3) + "...";
/*      */     }
/* 6412 */     if (maxWidth < 7) {
/* 6413 */       throw new IllegalArgumentException("Minimum abbreviation width with offset is 7");
/*      */     }
/* 6415 */     if (offset + maxWidth - 3 < str.length()) {
/* 6416 */       return "..." + abbreviate(str.substring(offset), maxWidth - 3);
/*      */     }
/* 6418 */     return "..." + str.substring(str.length() - maxWidth - 3);
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
/*      */   public static String abbreviateMiddle(String str, String middle, int length) {
/* 6451 */     if (isEmpty(str) || isEmpty(middle)) {
/* 6452 */       return str;
/*      */     }
/*      */     
/* 6455 */     if (length >= str.length() || length < middle.length() + 2) {
/* 6456 */       return str;
/*      */     }
/*      */     
/* 6459 */     int targetSting = length - middle.length();
/* 6460 */     int startOffset = targetSting / 2 + targetSting % 2;
/* 6461 */     int endOffset = str.length() - targetSting / 2;
/*      */     
/* 6463 */     StringBuilder builder = new StringBuilder(length);
/* 6464 */     builder.append(str.substring(0, startOffset));
/* 6465 */     builder.append(middle);
/* 6466 */     builder.append(str.substring(endOffset));
/*      */     
/* 6468 */     return builder.toString();
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
/*      */   public static String difference(String str1, String str2) {
/* 6502 */     if (str1 == null) {
/* 6503 */       return str2;
/*      */     }
/* 6505 */     if (str2 == null) {
/* 6506 */       return str1;
/*      */     }
/* 6508 */     int at = indexOfDifference(str1, str2);
/* 6509 */     if (at == -1) {
/* 6510 */       return "";
/*      */     }
/* 6512 */     return str2.substring(at);
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
/*      */   public static int indexOfDifference(CharSequence cs1, CharSequence cs2) {
/* 6541 */     if (cs1 == cs2) {
/* 6542 */       return -1;
/*      */     }
/* 6544 */     if (cs1 == null || cs2 == null) {
/* 6545 */       return 0;
/*      */     }
/*      */     int i;
/* 6548 */     for (i = 0; i < cs1.length() && i < cs2.length() && 
/* 6549 */       cs1.charAt(i) == cs2.charAt(i); i++);
/*      */ 
/*      */ 
/*      */     
/* 6553 */     if (i < cs2.length() || i < cs1.length()) {
/* 6554 */       return i;
/*      */     }
/* 6556 */     return -1;
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
/*      */   public static int indexOfDifference(CharSequence... css) {
/* 6592 */     if (css == null || css.length <= 1) {
/* 6593 */       return -1;
/*      */     }
/* 6595 */     boolean anyStringNull = false;
/* 6596 */     boolean allStringsNull = true;
/* 6597 */     int arrayLen = css.length;
/* 6598 */     int shortestStrLen = Integer.MAX_VALUE;
/* 6599 */     int longestStrLen = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6604 */     for (int i = 0; i < arrayLen; i++) {
/* 6605 */       if (css[i] == null) {
/* 6606 */         anyStringNull = true;
/* 6607 */         shortestStrLen = 0;
/*      */       } else {
/* 6609 */         allStringsNull = false;
/* 6610 */         shortestStrLen = Math.min(css[i].length(), shortestStrLen);
/* 6611 */         longestStrLen = Math.max(css[i].length(), longestStrLen);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 6616 */     if (allStringsNull || (longestStrLen == 0 && !anyStringNull)) {
/* 6617 */       return -1;
/*      */     }
/*      */ 
/*      */     
/* 6621 */     if (shortestStrLen == 0) {
/* 6622 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 6626 */     int firstDiff = -1;
/* 6627 */     for (int stringPos = 0; stringPos < shortestStrLen; stringPos++) {
/* 6628 */       char comparisonChar = css[0].charAt(stringPos);
/* 6629 */       for (int arrayPos = 1; arrayPos < arrayLen; arrayPos++) {
/* 6630 */         if (css[arrayPos].charAt(stringPos) != comparisonChar) {
/* 6631 */           firstDiff = stringPos;
/*      */           break;
/*      */         } 
/*      */       } 
/* 6635 */       if (firstDiff != -1) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */     
/* 6640 */     if (firstDiff == -1 && shortestStrLen != longestStrLen)
/*      */     {
/*      */ 
/*      */       
/* 6644 */       return shortestStrLen;
/*      */     }
/* 6646 */     return firstDiff;
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
/*      */   public static String getCommonPrefix(String... strs) {
/* 6683 */     if (strs == null || strs.length == 0) {
/* 6684 */       return "";
/*      */     }
/* 6686 */     int smallestIndexOfDiff = indexOfDifference((CharSequence[])strs);
/* 6687 */     if (smallestIndexOfDiff == -1) {
/*      */       
/* 6689 */       if (strs[0] == null) {
/* 6690 */         return "";
/*      */       }
/* 6692 */       return strs[0];
/* 6693 */     }  if (smallestIndexOfDiff == 0)
/*      */     {
/* 6695 */       return "";
/*      */     }
/*      */     
/* 6698 */     return strs[0].substring(0, smallestIndexOfDiff);
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
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
/* 6741 */     if (s == null || t == null) {
/* 6742 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6762 */     int n = s.length();
/* 6763 */     int m = t.length();
/*      */     
/* 6765 */     if (n == 0)
/* 6766 */       return m; 
/* 6767 */     if (m == 0) {
/* 6768 */       return n;
/*      */     }
/*      */     
/* 6771 */     if (n > m) {
/*      */       
/* 6773 */       CharSequence tmp = s;
/* 6774 */       s = t;
/* 6775 */       t = tmp;
/* 6776 */       n = m;
/* 6777 */       m = t.length();
/*      */     } 
/*      */     
/* 6780 */     int[] p = new int[n + 1];
/* 6781 */     int[] d = new int[n + 1];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int i;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6792 */     for (i = 0; i <= n; i++) {
/* 6793 */       p[i] = i;
/*      */     }
/*      */     
/* 6796 */     for (int j = 1; j <= m; j++) {
/* 6797 */       char t_j = t.charAt(j - 1);
/* 6798 */       d[0] = j;
/*      */       
/* 6800 */       for (i = 1; i <= n; i++) {
/* 6801 */         int cost = (s.charAt(i - 1) == t_j) ? 0 : 1;
/*      */         
/* 6803 */         d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
/*      */       } 
/*      */ 
/*      */       
/* 6807 */       int[] _d = p;
/* 6808 */       p = d;
/* 6809 */       d = _d;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6814 */     return p[n];
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
/*      */   public static int getLevenshteinDistance(CharSequence s, CharSequence t, int threshold) {
/* 6850 */     if (s == null || t == null) {
/* 6851 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/* 6853 */     if (threshold < 0) {
/* 6854 */       throw new IllegalArgumentException("Threshold must not be negative");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6901 */     int n = s.length();
/* 6902 */     int m = t.length();
/*      */ 
/*      */     
/* 6905 */     if (n == 0)
/* 6906 */       return (m <= threshold) ? m : -1; 
/* 6907 */     if (m == 0) {
/* 6908 */       return (n <= threshold) ? n : -1;
/*      */     }
/*      */     
/* 6911 */     if (n > m) {
/*      */       
/* 6913 */       CharSequence tmp = s;
/* 6914 */       s = t;
/* 6915 */       t = tmp;
/* 6916 */       n = m;
/* 6917 */       m = t.length();
/*      */     } 
/*      */     
/* 6920 */     int[] p = new int[n + 1];
/* 6921 */     int[] d = new int[n + 1];
/*      */ 
/*      */ 
/*      */     
/* 6925 */     int boundary = Math.min(n, threshold) + 1;
/* 6926 */     for (int i = 0; i < boundary; i++) {
/* 6927 */       p[i] = i;
/*      */     }
/*      */ 
/*      */     
/* 6931 */     Arrays.fill(p, boundary, p.length, 2147483647);
/* 6932 */     Arrays.fill(d, 2147483647);
/*      */ 
/*      */     
/* 6935 */     for (int j = 1; j <= m; j++) {
/* 6936 */       char t_j = t.charAt(j - 1);
/* 6937 */       d[0] = j;
/*      */ 
/*      */       
/* 6940 */       int min = Math.max(1, j - threshold);
/* 6941 */       int max = (j > Integer.MAX_VALUE - threshold) ? n : Math.min(n, j + threshold);
/*      */ 
/*      */       
/* 6944 */       if (min > max) {
/* 6945 */         return -1;
/*      */       }
/*      */ 
/*      */       
/* 6949 */       if (min > 1) {
/* 6950 */         d[min - 1] = Integer.MAX_VALUE;
/*      */       }
/*      */ 
/*      */       
/* 6954 */       for (int k = min; k <= max; k++) {
/* 6955 */         if (s.charAt(k - 1) == t_j) {
/*      */           
/* 6957 */           d[k] = p[k - 1];
/*      */         } else {
/*      */           
/* 6960 */           d[k] = 1 + Math.min(Math.min(d[k - 1], p[k]), p[k - 1]);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 6965 */       int[] _d = p;
/* 6966 */       p = d;
/* 6967 */       d = _d;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 6972 */     if (p[n] <= threshold) {
/* 6973 */       return p[n];
/*      */     }
/* 6975 */     return -1;
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
/*      */   public static double getJaroWinklerDistance(CharSequence first, CharSequence second) {
/* 7011 */     double DEFAULT_SCALING_FACTOR = 0.1D;
/*      */     
/* 7013 */     if (first == null || second == null) {
/* 7014 */       throw new IllegalArgumentException("Strings must not be null");
/*      */     }
/*      */     
/* 7017 */     double jaro = score(first, second);
/* 7018 */     int cl = commonPrefixLength(first, second);
/* 7019 */     double matchScore = Math.round((jaro + 0.1D * cl * (1.0D - jaro)) * 100.0D) / 100.0D;
/*      */     
/* 7021 */     return matchScore;
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
/*      */   private static double score(CharSequence first, CharSequence second) {
/*      */     String shorter, longer;
/* 7035 */     if (first.length() > second.length()) {
/* 7036 */       longer = first.toString().toLowerCase();
/* 7037 */       shorter = second.toString().toLowerCase();
/*      */     } else {
/* 7039 */       longer = second.toString().toLowerCase();
/* 7040 */       shorter = first.toString().toLowerCase();
/*      */     } 
/*      */ 
/*      */     
/* 7044 */     int halflength = shorter.length() / 2 + 1;
/*      */ 
/*      */ 
/*      */     
/* 7048 */     String m1 = getSetOfMatchingCharacterWithin(shorter, longer, halflength);
/* 7049 */     String m2 = getSetOfMatchingCharacterWithin(longer, shorter, halflength);
/*      */ 
/*      */ 
/*      */     
/* 7053 */     if (m1.length() == 0 || m2.length() == 0) {
/* 7054 */       return 0.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7059 */     if (m1.length() != m2.length()) {
/* 7060 */       return 0.0D;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 7065 */     int transpositions = transpositions(m1, m2);
/*      */ 
/*      */     
/* 7068 */     double dist = (m1.length() / shorter.length() + m2.length() / longer.length() + (m1.length() - transpositions) / m1.length()) / 3.0D;
/*      */ 
/*      */ 
/*      */     
/* 7072 */     return dist;
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
/*      */   private static String getSetOfMatchingCharacterWithin(CharSequence first, CharSequence second, int limit) {
/* 7087 */     StringBuilder common = new StringBuilder();
/* 7088 */     StringBuilder copy = new StringBuilder(second);
/*      */     
/* 7090 */     for (int i = 0; i < first.length(); i++) {
/* 7091 */       char ch = first.charAt(i);
/* 7092 */       boolean found = false;
/*      */ 
/*      */       
/* 7095 */       for (int j = Math.max(0, i - limit); !found && j < Math.min(i + limit, second.length()); j++) {
/* 7096 */         if (copy.charAt(j) == ch) {
/* 7097 */           found = true;
/* 7098 */           common.append(ch);
/* 7099 */           copy.setCharAt(j, '*');
/*      */         } 
/*      */       } 
/*      */     } 
/* 7103 */     return common.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int transpositions(CharSequence first, CharSequence second) {
/* 7113 */     int transpositions = 0;
/* 7114 */     for (int i = 0; i < first.length(); i++) {
/* 7115 */       if (first.charAt(i) != second.charAt(i)) {
/* 7116 */         transpositions++;
/*      */       }
/*      */     } 
/* 7119 */     return transpositions / 2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int commonPrefixLength(CharSequence first, CharSequence second) {
/* 7130 */     int result = getCommonPrefix(new String[] { first.toString(), second.toString() }).length();
/*      */ 
/*      */     
/* 7133 */     return (result > 4) ? 4 : result;
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
/*      */   public static boolean startsWith(CharSequence str, CharSequence prefix) {
/* 7162 */     return startsWith(str, prefix, false);
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
/*      */   public static boolean startsWithIgnoreCase(CharSequence str, CharSequence prefix) {
/* 7188 */     return startsWith(str, prefix, true);
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
/*      */   private static boolean startsWith(CharSequence str, CharSequence prefix, boolean ignoreCase) {
/* 7203 */     if (str == null || prefix == null) {
/* 7204 */       return (str == null && prefix == null);
/*      */     }
/* 7206 */     if (prefix.length() > str.length()) {
/* 7207 */       return false;
/*      */     }
/* 7209 */     return CharSequenceUtils.regionMatches(str, ignoreCase, 0, prefix, 0, prefix.length());
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
/*      */   public static boolean startsWithAny(CharSequence string, CharSequence... searchStrings) {
/* 7232 */     if (isEmpty(string) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 7233 */       return false;
/*      */     }
/* 7235 */     for (CharSequence searchString : searchStrings) {
/* 7236 */       if (startsWith(string, searchString)) {
/* 7237 */         return true;
/*      */       }
/*      */     } 
/* 7240 */     return false;
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
/*      */   public static boolean endsWith(CharSequence str, CharSequence suffix) {
/* 7270 */     return endsWith(str, suffix, false);
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
/*      */   public static boolean endsWithIgnoreCase(CharSequence str, CharSequence suffix) {
/* 7297 */     return endsWith(str, suffix, true);
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
/*      */   private static boolean endsWith(CharSequence str, CharSequence suffix, boolean ignoreCase) {
/* 7312 */     if (str == null || suffix == null) {
/* 7313 */       return (str == null && suffix == null);
/*      */     }
/* 7315 */     if (suffix.length() > str.length()) {
/* 7316 */       return false;
/*      */     }
/* 7318 */     int strOffset = str.length() - suffix.length();
/* 7319 */     return CharSequenceUtils.regionMatches(str, ignoreCase, strOffset, suffix, 0, suffix.length());
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
/*      */   public static String normalizeSpace(String str) {
/* 7364 */     if (str == null) {
/* 7365 */       return null;
/*      */     }
/* 7367 */     return WHITESPACE_PATTERN.matcher(trim(str)).replaceAll(" ");
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
/*      */   public static boolean endsWithAny(CharSequence string, CharSequence... searchStrings) {
/* 7389 */     if (isEmpty(string) || ArrayUtils.isEmpty((Object[])searchStrings)) {
/* 7390 */       return false;
/*      */     }
/* 7392 */     for (CharSequence searchString : searchStrings) {
/* 7393 */       if (endsWith(string, searchString)) {
/* 7394 */         return true;
/*      */       }
/*      */     } 
/* 7397 */     return false;
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
/*      */   private static String appendIfMissing(String str, CharSequence suffix, boolean ignoreCase, CharSequence... suffixes) {
/* 7412 */     if (str == null || isEmpty(suffix) || endsWith(str, suffix, ignoreCase)) {
/* 7413 */       return str;
/*      */     }
/* 7415 */     if (suffixes != null && suffixes.length > 0) {
/* 7416 */       for (CharSequence s : suffixes) {
/* 7417 */         if (endsWith(str, s, ignoreCase)) {
/* 7418 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/* 7422 */     return str + suffix.toString();
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
/*      */   public static String appendIfMissing(String str, CharSequence suffix, CharSequence... suffixes) {
/* 7460 */     return appendIfMissing(str, suffix, false, suffixes);
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
/*      */   public static String appendIfMissingIgnoreCase(String str, CharSequence suffix, CharSequence... suffixes) {
/* 7498 */     return appendIfMissing(str, suffix, true, suffixes);
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
/*      */   private static String prependIfMissing(String str, CharSequence prefix, boolean ignoreCase, CharSequence... prefixes) {
/* 7513 */     if (str == null || isEmpty(prefix) || startsWith(str, prefix, ignoreCase)) {
/* 7514 */       return str;
/*      */     }
/* 7516 */     if (prefixes != null && prefixes.length > 0) {
/* 7517 */       for (CharSequence p : prefixes) {
/* 7518 */         if (startsWith(str, p, ignoreCase)) {
/* 7519 */           return str;
/*      */         }
/*      */       } 
/*      */     }
/* 7523 */     return prefix.toString() + str;
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
/*      */   public static String prependIfMissing(String str, CharSequence prefix, CharSequence... prefixes) {
/* 7561 */     return prependIfMissing(str, prefix, false, prefixes);
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
/*      */   public static String prependIfMissingIgnoreCase(String str, CharSequence prefix, CharSequence... prefixes) {
/* 7599 */     return prependIfMissing(str, prefix, true, prefixes);
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
/*      */   @Deprecated
/*      */   public static String toString(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
/* 7619 */     return (charsetName != null) ? new String(bytes, charsetName) : new String(bytes, Charset.defaultCharset());
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
/*      */   public static String toEncodedString(byte[] bytes, Charset charset) {
/* 7636 */     return new String(bytes, (charset != null) ? charset : Charset.defaultCharset());
/*      */   }
/*      */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\StringUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */