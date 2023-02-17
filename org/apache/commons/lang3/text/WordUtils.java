/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.commons.lang3.SystemUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WordUtils
/*     */ {
/*     */   public static String wrap(String str, int wrapLength) {
/*  97 */     return wrap(str, wrapLength, null, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String wrap(String str, int wrapLength, String newLineStr, boolean wrapLongWords) {
/* 173 */     if (str == null) {
/* 174 */       return null;
/*     */     }
/* 176 */     if (newLineStr == null) {
/* 177 */       newLineStr = SystemUtils.LINE_SEPARATOR;
/*     */     }
/* 179 */     if (wrapLength < 1) {
/* 180 */       wrapLength = 1;
/*     */     }
/* 182 */     int inputLineLength = str.length();
/* 183 */     int offset = 0;
/* 184 */     StringBuilder wrappedLine = new StringBuilder(inputLineLength + 32);
/*     */     
/* 186 */     while (inputLineLength - offset > wrapLength) {
/* 187 */       if (str.charAt(offset) == ' ') {
/* 188 */         offset++;
/*     */         continue;
/*     */       } 
/* 191 */       int spaceToWrapAt = str.lastIndexOf(' ', wrapLength + offset);
/*     */       
/* 193 */       if (spaceToWrapAt >= offset) {
/*     */         
/* 195 */         wrappedLine.append(str.substring(offset, spaceToWrapAt));
/* 196 */         wrappedLine.append(newLineStr);
/* 197 */         offset = spaceToWrapAt + 1;
/*     */         
/*     */         continue;
/*     */       } 
/* 201 */       if (wrapLongWords) {
/*     */         
/* 203 */         wrappedLine.append(str.substring(offset, wrapLength + offset));
/* 204 */         wrappedLine.append(newLineStr);
/* 205 */         offset += wrapLength;
/*     */         continue;
/*     */       } 
/* 208 */       spaceToWrapAt = str.indexOf(' ', wrapLength + offset);
/* 209 */       if (spaceToWrapAt >= 0) {
/* 210 */         wrappedLine.append(str.substring(offset, spaceToWrapAt));
/* 211 */         wrappedLine.append(newLineStr);
/* 212 */         offset = spaceToWrapAt + 1; continue;
/*     */       } 
/* 214 */       wrappedLine.append(str.substring(offset));
/* 215 */       offset = inputLineLength;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     wrappedLine.append(str.substring(offset));
/*     */     
/* 224 */     return wrappedLine.toString();
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
/*     */   public static String capitalize(String str) {
/* 252 */     return capitalize(str, null);
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
/*     */   public static String capitalize(String str, char... delimiters) {
/* 285 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 286 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 287 */       return str;
/*     */     }
/* 289 */     char[] buffer = str.toCharArray();
/* 290 */     boolean capitalizeNext = true;
/* 291 */     for (int i = 0; i < buffer.length; i++) {
/* 292 */       char ch = buffer[i];
/* 293 */       if (isDelimiter(ch, delimiters)) {
/* 294 */         capitalizeNext = true;
/* 295 */       } else if (capitalizeNext) {
/* 296 */         buffer[i] = Character.toTitleCase(ch);
/* 297 */         capitalizeNext = false;
/*     */       } 
/*     */     } 
/* 300 */     return new String(buffer);
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
/*     */   public static String capitalizeFully(String str) {
/* 324 */     return capitalizeFully(str, null);
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
/*     */   public static String capitalizeFully(String str, char... delimiters) {
/* 354 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 355 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 356 */       return str;
/*     */     }
/* 358 */     str = str.toLowerCase();
/* 359 */     return capitalize(str, delimiters);
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
/*     */   public static String uncapitalize(String str) {
/* 381 */     return uncapitalize(str, null);
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
/*     */   public static String uncapitalize(String str, char... delimiters) {
/* 410 */     int delimLen = (delimiters == null) ? -1 : delimiters.length;
/* 411 */     if (StringUtils.isEmpty(str) || delimLen == 0) {
/* 412 */       return str;
/*     */     }
/* 414 */     char[] buffer = str.toCharArray();
/* 415 */     boolean uncapitalizeNext = true;
/* 416 */     for (int i = 0; i < buffer.length; i++) {
/* 417 */       char ch = buffer[i];
/* 418 */       if (isDelimiter(ch, delimiters)) {
/* 419 */         uncapitalizeNext = true;
/* 420 */       } else if (uncapitalizeNext) {
/* 421 */         buffer[i] = Character.toLowerCase(ch);
/* 422 */         uncapitalizeNext = false;
/*     */       } 
/*     */     } 
/* 425 */     return new String(buffer);
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
/*     */   public static String swapCase(String str) {
/* 452 */     if (StringUtils.isEmpty(str)) {
/* 453 */       return str;
/*     */     }
/* 455 */     char[] buffer = str.toCharArray();
/*     */     
/* 457 */     boolean whitespace = true;
/*     */     
/* 459 */     for (int i = 0; i < buffer.length; i++) {
/* 460 */       char ch = buffer[i];
/* 461 */       if (Character.isUpperCase(ch)) {
/* 462 */         buffer[i] = Character.toLowerCase(ch);
/* 463 */         whitespace = false;
/* 464 */       } else if (Character.isTitleCase(ch)) {
/* 465 */         buffer[i] = Character.toLowerCase(ch);
/* 466 */         whitespace = false;
/* 467 */       } else if (Character.isLowerCase(ch)) {
/* 468 */         if (whitespace) {
/* 469 */           buffer[i] = Character.toTitleCase(ch);
/* 470 */           whitespace = false;
/*     */         } else {
/* 472 */           buffer[i] = Character.toUpperCase(ch);
/*     */         } 
/*     */       } else {
/* 475 */         whitespace = Character.isWhitespace(ch);
/*     */       } 
/*     */     } 
/* 478 */     return new String(buffer);
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
/*     */   public static String initials(String str) {
/* 505 */     return initials(str, null);
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
/*     */   public static String initials(String str, char... delimiters) {
/* 536 */     if (StringUtils.isEmpty(str)) {
/* 537 */       return str;
/*     */     }
/* 539 */     if (delimiters != null && delimiters.length == 0) {
/* 540 */       return "";
/*     */     }
/* 542 */     int strLen = str.length();
/* 543 */     char[] buf = new char[strLen / 2 + 1];
/* 544 */     int count = 0;
/* 545 */     boolean lastWasGap = true;
/* 546 */     for (int i = 0; i < strLen; i++) {
/* 547 */       char ch = str.charAt(i);
/*     */       
/* 549 */       if (isDelimiter(ch, delimiters)) {
/* 550 */         lastWasGap = true;
/* 551 */       } else if (lastWasGap) {
/* 552 */         buf[count++] = ch;
/* 553 */         lastWasGap = false;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 558 */     return new String(buf, 0, count);
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
/*     */   private static boolean isDelimiter(char ch, char[] delimiters) {
/* 570 */     if (delimiters == null) {
/* 571 */       return Character.isWhitespace(ch);
/*     */     }
/* 573 */     for (char delimiter : delimiters) {
/* 574 */       if (ch == delimiter) {
/* 575 */         return true;
/*     */       }
/*     */     } 
/* 578 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\text\WordUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */