/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DurationFormatUtils
/*     */ {
/*     */   public static final String ISO_EXTENDED_FORMAT_PATTERN = "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'";
/*     */   
/*     */   public static String formatDurationHMS(long durationMillis) {
/*  82 */     return formatDuration(durationMillis, "H:mm:ss.SSS");
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
/*     */   public static String formatDurationISO(long durationMillis) {
/*  97 */     return formatDuration(durationMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false);
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
/*     */   public static String formatDuration(long durationMillis, String format) {
/* 111 */     return formatDuration(durationMillis, format, true);
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
/*     */   public static String formatDuration(long durationMillis, String format, boolean padWithZeros) {
/* 128 */     Token[] tokens = lexx(format);
/*     */     
/* 130 */     long days = 0L;
/* 131 */     long hours = 0L;
/* 132 */     long minutes = 0L;
/* 133 */     long seconds = 0L;
/* 134 */     long milliseconds = durationMillis;
/*     */     
/* 136 */     if (Token.containsTokenWithValue(tokens, d)) {
/* 137 */       days = milliseconds / 86400000L;
/* 138 */       milliseconds -= days * 86400000L;
/*     */     } 
/* 140 */     if (Token.containsTokenWithValue(tokens, H)) {
/* 141 */       hours = milliseconds / 3600000L;
/* 142 */       milliseconds -= hours * 3600000L;
/*     */     } 
/* 144 */     if (Token.containsTokenWithValue(tokens, m)) {
/* 145 */       minutes = milliseconds / 60000L;
/* 146 */       milliseconds -= minutes * 60000L;
/*     */     } 
/* 148 */     if (Token.containsTokenWithValue(tokens, s)) {
/* 149 */       seconds = milliseconds / 1000L;
/* 150 */       milliseconds -= seconds * 1000L;
/*     */     } 
/*     */     
/* 153 */     return format(tokens, 0L, 0L, days, hours, minutes, seconds, milliseconds, padWithZeros);
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
/*     */   public static String formatDurationWords(long durationMillis, boolean suppressLeadingZeroElements, boolean suppressTrailingZeroElements) {
/* 175 */     String duration = formatDuration(durationMillis, "d' days 'H' hours 'm' minutes 's' seconds'");
/* 176 */     if (suppressLeadingZeroElements) {
/*     */       
/* 178 */       duration = " " + duration;
/* 179 */       String tmp = StringUtils.replaceOnce(duration, " 0 days", "");
/* 180 */       if (tmp.length() != duration.length()) {
/* 181 */         duration = tmp;
/* 182 */         tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/* 183 */         if (tmp.length() != duration.length()) {
/* 184 */           duration = tmp;
/* 185 */           tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/* 186 */           duration = tmp;
/* 187 */           if (tmp.length() != duration.length()) {
/* 188 */             duration = StringUtils.replaceOnce(tmp, " 0 seconds", "");
/*     */           }
/*     */         } 
/*     */       } 
/* 192 */       if (duration.length() != 0)
/*     */       {
/* 194 */         duration = duration.substring(1);
/*     */       }
/*     */     } 
/* 197 */     if (suppressTrailingZeroElements) {
/* 198 */       String tmp = StringUtils.replaceOnce(duration, " 0 seconds", "");
/* 199 */       if (tmp.length() != duration.length()) {
/* 200 */         duration = tmp;
/* 201 */         tmp = StringUtils.replaceOnce(duration, " 0 minutes", "");
/* 202 */         if (tmp.length() != duration.length()) {
/* 203 */           duration = tmp;
/* 204 */           tmp = StringUtils.replaceOnce(duration, " 0 hours", "");
/* 205 */           if (tmp.length() != duration.length()) {
/* 206 */             duration = StringUtils.replaceOnce(tmp, " 0 days", "");
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 212 */     duration = " " + duration;
/* 213 */     duration = StringUtils.replaceOnce(duration, " 1 seconds", " 1 second");
/* 214 */     duration = StringUtils.replaceOnce(duration, " 1 minutes", " 1 minute");
/* 215 */     duration = StringUtils.replaceOnce(duration, " 1 hours", " 1 hour");
/* 216 */     duration = StringUtils.replaceOnce(duration, " 1 days", " 1 day");
/* 217 */     return duration.trim();
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
/*     */   public static String formatPeriodISO(long startMillis, long endMillis) {
/* 231 */     return formatPeriod(startMillis, endMillis, "'P'yyyy'Y'M'M'd'DT'H'H'm'M's.S'S'", false, TimeZone.getDefault());
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
/*     */   public static String formatPeriod(long startMillis, long endMillis, String format) {
/* 244 */     return formatPeriod(startMillis, endMillis, format, true, TimeZone.getDefault());
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
/*     */   public static String formatPeriod(long startMillis, long endMillis, String format, boolean padWithZeros, TimeZone timezone) {
/* 279 */     Token[] tokens = lexx(format);
/*     */ 
/*     */ 
/*     */     
/* 283 */     Calendar start = Calendar.getInstance(timezone);
/* 284 */     start.setTime(new Date(startMillis));
/* 285 */     Calendar end = Calendar.getInstance(timezone);
/* 286 */     end.setTime(new Date(endMillis));
/*     */ 
/*     */     
/* 289 */     int milliseconds = end.get(14) - start.get(14);
/* 290 */     int seconds = end.get(13) - start.get(13);
/* 291 */     int minutes = end.get(12) - start.get(12);
/* 292 */     int hours = end.get(11) - start.get(11);
/* 293 */     int days = end.get(5) - start.get(5);
/* 294 */     int months = end.get(2) - start.get(2);
/* 295 */     int years = end.get(1) - start.get(1);
/*     */ 
/*     */     
/* 298 */     while (milliseconds < 0) {
/* 299 */       milliseconds += 1000;
/* 300 */       seconds--;
/*     */     } 
/* 302 */     while (seconds < 0) {
/* 303 */       seconds += 60;
/* 304 */       minutes--;
/*     */     } 
/* 306 */     while (minutes < 0) {
/* 307 */       minutes += 60;
/* 308 */       hours--;
/*     */     } 
/* 310 */     while (hours < 0) {
/* 311 */       hours += 24;
/* 312 */       days--;
/*     */     } 
/*     */     
/* 315 */     if (Token.containsTokenWithValue(tokens, M)) {
/* 316 */       while (days < 0) {
/* 317 */         days += start.getActualMaximum(5);
/* 318 */         months--;
/* 319 */         start.add(2, 1);
/*     */       } 
/*     */       
/* 322 */       while (months < 0) {
/* 323 */         months += 12;
/* 324 */         years--;
/*     */       } 
/*     */       
/* 327 */       if (!Token.containsTokenWithValue(tokens, y) && years != 0) {
/* 328 */         while (years != 0) {
/* 329 */           months += 12 * years;
/* 330 */           years = 0;
/*     */         }
/*     */       
/*     */       }
/*     */     } else {
/*     */       
/* 336 */       if (!Token.containsTokenWithValue(tokens, y)) {
/* 337 */         int target = end.get(1);
/* 338 */         if (months < 0)
/*     */         {
/* 340 */           target--;
/*     */         }
/*     */         
/* 343 */         while (start.get(1) != target) {
/* 344 */           days += start.getActualMaximum(6) - start.get(6);
/*     */ 
/*     */           
/* 347 */           if (start instanceof java.util.GregorianCalendar && start.get(2) == 1 && start.get(5) == 29)
/*     */           {
/*     */             
/* 350 */             days++;
/*     */           }
/*     */           
/* 353 */           start.add(1, 1);
/*     */           
/* 355 */           days += start.get(6);
/*     */         } 
/*     */         
/* 358 */         years = 0;
/*     */       } 
/*     */       
/* 361 */       while (start.get(2) != end.get(2)) {
/* 362 */         days += start.getActualMaximum(5);
/* 363 */         start.add(2, 1);
/*     */       } 
/*     */       
/* 366 */       months = 0;
/*     */       
/* 368 */       while (days < 0) {
/* 369 */         days += start.getActualMaximum(5);
/* 370 */         months--;
/* 371 */         start.add(2, 1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 380 */     if (!Token.containsTokenWithValue(tokens, d)) {
/* 381 */       hours += 24 * days;
/* 382 */       days = 0;
/*     */     } 
/* 384 */     if (!Token.containsTokenWithValue(tokens, H)) {
/* 385 */       minutes += 60 * hours;
/* 386 */       hours = 0;
/*     */     } 
/* 388 */     if (!Token.containsTokenWithValue(tokens, m)) {
/* 389 */       seconds += 60 * minutes;
/* 390 */       minutes = 0;
/*     */     } 
/* 392 */     if (!Token.containsTokenWithValue(tokens, s)) {
/* 393 */       milliseconds += 1000 * seconds;
/* 394 */       seconds = 0;
/*     */     } 
/*     */     
/* 397 */     return format(tokens, years, months, days, hours, minutes, seconds, milliseconds, padWithZeros);
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
/*     */   static String format(Token[] tokens, long years, long months, long days, long hours, long minutes, long seconds, long milliseconds, boolean padWithZeros) {
/* 417 */     StringBuilder buffer = new StringBuilder();
/* 418 */     boolean lastOutputSeconds = false;
/* 419 */     int sz = tokens.length;
/* 420 */     for (int i = 0; i < sz; i++) {
/* 421 */       Token token = tokens[i];
/* 422 */       Object value = token.getValue();
/* 423 */       int count = token.getCount();
/* 424 */       if (value instanceof StringBuilder) {
/* 425 */         buffer.append(value.toString());
/*     */       }
/* 427 */       else if (value == y) {
/* 428 */         buffer.append(paddedValue(years, padWithZeros, count));
/* 429 */         lastOutputSeconds = false;
/* 430 */       } else if (value == M) {
/* 431 */         buffer.append(paddedValue(months, padWithZeros, count));
/* 432 */         lastOutputSeconds = false;
/* 433 */       } else if (value == d) {
/* 434 */         buffer.append(paddedValue(days, padWithZeros, count));
/* 435 */         lastOutputSeconds = false;
/* 436 */       } else if (value == H) {
/* 437 */         buffer.append(paddedValue(hours, padWithZeros, count));
/* 438 */         lastOutputSeconds = false;
/* 439 */       } else if (value == m) {
/* 440 */         buffer.append(paddedValue(minutes, padWithZeros, count));
/* 441 */         lastOutputSeconds = false;
/* 442 */       } else if (value == s) {
/* 443 */         buffer.append(paddedValue(seconds, padWithZeros, count));
/* 444 */         lastOutputSeconds = true;
/* 445 */       } else if (value == S) {
/* 446 */         if (lastOutputSeconds) {
/*     */           
/* 448 */           int width = padWithZeros ? Math.max(3, count) : 3;
/* 449 */           buffer.append(paddedValue(milliseconds, true, width));
/*     */         } else {
/* 451 */           buffer.append(paddedValue(milliseconds, padWithZeros, count));
/*     */         } 
/* 453 */         lastOutputSeconds = false;
/*     */       } 
/*     */     } 
/*     */     
/* 457 */     return buffer.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String paddedValue(long value, boolean padWithZeros, int count) {
/* 462 */     String longString = Long.toString(value);
/* 463 */     return padWithZeros ? StringUtils.leftPad(longString, count, '0') : longString;
/*     */   }
/*     */   
/* 466 */   static final Object y = "y";
/* 467 */   static final Object M = "M";
/* 468 */   static final Object d = "d";
/* 469 */   static final Object H = "H";
/* 470 */   static final Object m = "m";
/* 471 */   static final Object s = "s";
/* 472 */   static final Object S = "S";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Token[] lexx(String format) {
/* 481 */     ArrayList<Token> list = new ArrayList<Token>(format.length());
/*     */     
/* 483 */     boolean inLiteral = false;
/*     */ 
/*     */     
/* 486 */     StringBuilder buffer = null;
/* 487 */     Token previous = null;
/* 488 */     for (int i = 0; i < format.length(); i++) {
/* 489 */       char ch = format.charAt(i);
/* 490 */       if (inLiteral && ch != '\'') {
/* 491 */         buffer.append(ch);
/*     */       } else {
/*     */         
/* 494 */         Object value = null;
/* 495 */         switch (ch) {
/*     */           
/*     */           case '\'':
/* 498 */             if (inLiteral) {
/* 499 */               buffer = null;
/* 500 */               inLiteral = false; break;
/*     */             } 
/* 502 */             buffer = new StringBuilder();
/* 503 */             list.add(new Token(buffer));
/* 504 */             inLiteral = true;
/*     */             break;
/*     */           
/*     */           case 'y':
/* 508 */             value = y;
/*     */             break;
/*     */           case 'M':
/* 511 */             value = M;
/*     */             break;
/*     */           case 'd':
/* 514 */             value = d;
/*     */             break;
/*     */           case 'H':
/* 517 */             value = H;
/*     */             break;
/*     */           case 'm':
/* 520 */             value = m;
/*     */             break;
/*     */           case 's':
/* 523 */             value = s;
/*     */             break;
/*     */           case 'S':
/* 526 */             value = S;
/*     */             break;
/*     */           default:
/* 529 */             if (buffer == null) {
/* 530 */               buffer = new StringBuilder();
/* 531 */               list.add(new Token(buffer));
/*     */             } 
/* 533 */             buffer.append(ch);
/*     */             break;
/*     */         } 
/* 536 */         if (value != null) {
/* 537 */           if (previous != null && previous.getValue() == value) {
/* 538 */             previous.increment();
/*     */           } else {
/* 540 */             Token token = new Token(value);
/* 541 */             list.add(token);
/* 542 */             previous = token;
/*     */           } 
/* 544 */           buffer = null;
/*     */         } 
/*     */       } 
/* 547 */     }  if (inLiteral) {
/* 548 */       throw new IllegalArgumentException("Unmatched quote in format: " + format);
/*     */     }
/* 550 */     return list.<Token>toArray(new Token[list.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class Token
/*     */   {
/*     */     private final Object value;
/*     */ 
/*     */ 
/*     */     
/*     */     private int count;
/*     */ 
/*     */ 
/*     */     
/*     */     static boolean containsTokenWithValue(Token[] tokens, Object value) {
/* 567 */       int sz = tokens.length;
/* 568 */       for (int i = 0; i < sz; i++) {
/* 569 */         if (tokens[i].getValue() == value) {
/* 570 */           return true;
/*     */         }
/*     */       } 
/* 573 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Token(Object value) {
/* 585 */       this.value = value;
/* 586 */       this.count = 1;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Token(Object value, int count) {
/* 597 */       this.value = value;
/* 598 */       this.count = count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void increment() {
/* 605 */       this.count++;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int getCount() {
/* 614 */       return this.count;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     Object getValue() {
/* 623 */       return this.value;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean equals(Object obj2) {
/* 634 */       if (obj2 instanceof Token) {
/* 635 */         Token tok2 = (Token)obj2;
/* 636 */         if (this.value.getClass() != tok2.value.getClass()) {
/* 637 */           return false;
/*     */         }
/* 639 */         if (this.count != tok2.count) {
/* 640 */           return false;
/*     */         }
/* 642 */         if (this.value instanceof StringBuilder)
/* 643 */           return this.value.toString().equals(tok2.value.toString()); 
/* 644 */         if (this.value instanceof Number) {
/* 645 */           return this.value.equals(tok2.value);
/*     */         }
/* 647 */         return (this.value == tok2.value);
/*     */       } 
/*     */       
/* 650 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 662 */       return this.value.hashCode();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 672 */       return StringUtils.repeat(this.value.toString(), this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\time\DurationFormatUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */