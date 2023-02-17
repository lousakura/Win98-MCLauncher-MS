/*     */ package org.apache.commons.lang3.text;
/*     */ 
/*     */ import java.text.Format;
/*     */ import java.text.MessageFormat;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang3.ObjectUtils;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExtendedMessageFormat
/*     */   extends MessageFormat
/*     */ {
/*     */   private static final long serialVersionUID = -2362048321261811743L;
/*     */   private static final int HASH_SEED = 31;
/*     */   private static final String DUMMY_PATTERN = "";
/*     */   private static final String ESCAPED_QUOTE = "''";
/*     */   private static final char START_FMT = ',';
/*     */   private static final char END_FE = '}';
/*     */   private static final char START_FE = '{';
/*     */   private static final char QUOTE = '\'';
/*     */   private String toPattern;
/*     */   private final Map<String, ? extends FormatFactory> registry;
/*     */   
/*     */   public ExtendedMessageFormat(String pattern) {
/*  90 */     this(pattern, Locale.getDefault());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedMessageFormat(String pattern, Locale locale) {
/* 101 */     this(pattern, locale, (Map<String, ? extends FormatFactory>)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ExtendedMessageFormat(String pattern, Map<String, ? extends FormatFactory> registry) {
/* 112 */     this(pattern, Locale.getDefault(), registry);
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
/*     */   public ExtendedMessageFormat(String pattern, Locale locale, Map<String, ? extends FormatFactory> registry) {
/* 124 */     super("");
/* 125 */     setLocale(locale);
/* 126 */     this.registry = registry;
/* 127 */     applyPattern(pattern);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toPattern() {
/* 135 */     return this.toPattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void applyPattern(String pattern) {
/* 145 */     if (this.registry == null) {
/* 146 */       super.applyPattern(pattern);
/* 147 */       this.toPattern = super.toPattern();
/*     */       return;
/*     */     } 
/* 150 */     ArrayList<Format> foundFormats = new ArrayList<Format>();
/* 151 */     ArrayList<String> foundDescriptions = new ArrayList<String>();
/* 152 */     StringBuilder stripCustom = new StringBuilder(pattern.length());
/*     */     
/* 154 */     ParsePosition pos = new ParsePosition(0);
/* 155 */     char[] c = pattern.toCharArray();
/* 156 */     int fmtCount = 0;
/* 157 */     while (pos.getIndex() < pattern.length()) {
/* 158 */       int start, index; Format format; String formatDescription; switch (c[pos.getIndex()]) {
/*     */         case '\'':
/* 160 */           appendQuotedString(pattern, pos, stripCustom, true);
/*     */           continue;
/*     */         case '{':
/* 163 */           fmtCount++;
/* 164 */           seekNonWs(pattern, pos);
/* 165 */           start = pos.getIndex();
/* 166 */           index = readArgumentIndex(pattern, next(pos));
/* 167 */           stripCustom.append('{').append(index);
/* 168 */           seekNonWs(pattern, pos);
/* 169 */           format = null;
/* 170 */           formatDescription = null;
/* 171 */           if (c[pos.getIndex()] == ',') {
/* 172 */             formatDescription = parseFormatDescription(pattern, next(pos));
/*     */             
/* 174 */             format = getFormat(formatDescription);
/* 175 */             if (format == null) {
/* 176 */               stripCustom.append(',').append(formatDescription);
/*     */             }
/*     */           } 
/* 179 */           foundFormats.add(format);
/* 180 */           foundDescriptions.add((format == null) ? null : formatDescription);
/* 181 */           Validate.isTrue((foundFormats.size() == fmtCount));
/* 182 */           Validate.isTrue((foundDescriptions.size() == fmtCount));
/* 183 */           if (c[pos.getIndex()] != '}') {
/* 184 */             throw new IllegalArgumentException("Unreadable format element at position " + start);
/*     */           }
/*     */           break;
/*     */       } 
/*     */       
/* 189 */       stripCustom.append(c[pos.getIndex()]);
/* 190 */       next(pos);
/*     */     } 
/*     */     
/* 193 */     super.applyPattern(stripCustom.toString());
/* 194 */     this.toPattern = insertFormats(super.toPattern(), foundDescriptions);
/* 195 */     if (containsElements(foundFormats)) {
/* 196 */       Format[] origFormats = getFormats();
/*     */ 
/*     */       
/* 199 */       int i = 0;
/* 200 */       for (Iterator<Format> it = foundFormats.iterator(); it.hasNext(); i++) {
/* 201 */         Format f = it.next();
/* 202 */         if (f != null) {
/* 203 */           origFormats[i] = f;
/*     */         }
/*     */       } 
/* 206 */       super.setFormats(origFormats);
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
/*     */   public void setFormat(int formatElementIndex, Format newFormat) {
/* 219 */     throw new UnsupportedOperationException();
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
/*     */   public void setFormatByArgumentIndex(int argumentIndex, Format newFormat) {
/* 231 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormats(Format[] newFormats) {
/* 242 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormatsByArgumentIndex(Format[] newFormats) {
/* 253 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 264 */     if (obj == this) {
/* 265 */       return true;
/*     */     }
/* 267 */     if (obj == null) {
/* 268 */       return false;
/*     */     }
/* 270 */     if (!super.equals(obj)) {
/* 271 */       return false;
/*     */     }
/* 273 */     if (ObjectUtils.notEqual(getClass(), obj.getClass())) {
/* 274 */       return false;
/*     */     }
/* 276 */     ExtendedMessageFormat rhs = (ExtendedMessageFormat)obj;
/* 277 */     if (ObjectUtils.notEqual(this.toPattern, rhs.toPattern)) {
/* 278 */       return false;
/*     */     }
/* 280 */     if (ObjectUtils.notEqual(this.registry, rhs.registry)) {
/* 281 */       return false;
/*     */     }
/* 283 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 292 */     int result = super.hashCode();
/* 293 */     result = 31 * result + ObjectUtils.hashCode(this.registry);
/* 294 */     result = 31 * result + ObjectUtils.hashCode(this.toPattern);
/* 295 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Format getFormat(String desc) {
/* 305 */     if (this.registry != null) {
/* 306 */       String name = desc;
/* 307 */       String args = null;
/* 308 */       int i = desc.indexOf(',');
/* 309 */       if (i > 0) {
/* 310 */         name = desc.substring(0, i).trim();
/* 311 */         args = desc.substring(i + 1).trim();
/*     */       } 
/* 313 */       FormatFactory factory = this.registry.get(name);
/* 314 */       if (factory != null) {
/* 315 */         return factory.getFormat(name, args, getLocale());
/*     */       }
/*     */     } 
/* 318 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int readArgumentIndex(String pattern, ParsePosition pos) {
/* 329 */     int start = pos.getIndex();
/* 330 */     seekNonWs(pattern, pos);
/* 331 */     StringBuilder result = new StringBuilder();
/* 332 */     boolean error = false;
/* 333 */     for (; !error && pos.getIndex() < pattern.length(); next(pos)) {
/* 334 */       char c = pattern.charAt(pos.getIndex());
/* 335 */       if (Character.isWhitespace(c)) {
/* 336 */         seekNonWs(pattern, pos);
/* 337 */         c = pattern.charAt(pos.getIndex());
/* 338 */         if (c != ',' && c != '}') {
/* 339 */           error = true;
/*     */           continue;
/*     */         } 
/*     */       } 
/* 343 */       if ((c == ',' || c == '}') && result.length() > 0) {
/*     */         try {
/* 345 */           return Integer.parseInt(result.toString());
/* 346 */         } catch (NumberFormatException e) {}
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 351 */       error = !Character.isDigit(c);
/* 352 */       result.append(c); continue;
/*     */     } 
/* 354 */     if (error) {
/* 355 */       throw new IllegalArgumentException("Invalid format argument index at position " + start + ": " + pattern.substring(start, pos.getIndex()));
/*     */     }
/*     */ 
/*     */     
/* 359 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
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
/*     */   private String parseFormatDescription(String pattern, ParsePosition pos) {
/* 371 */     int start = pos.getIndex();
/* 372 */     seekNonWs(pattern, pos);
/* 373 */     int text = pos.getIndex();
/* 374 */     int depth = 1;
/* 375 */     for (; pos.getIndex() < pattern.length(); next(pos)) {
/* 376 */       switch (pattern.charAt(pos.getIndex())) {
/*     */         case '{':
/* 378 */           depth++;
/*     */           break;
/*     */         case '}':
/* 381 */           depth--;
/* 382 */           if (depth == 0) {
/* 383 */             return pattern.substring(text, pos.getIndex());
/*     */           }
/*     */           break;
/*     */         case '\'':
/* 387 */           getQuotedString(pattern, pos, false);
/*     */           break;
/*     */       } 
/*     */ 
/*     */     
/*     */     } 
/* 393 */     throw new IllegalArgumentException("Unterminated format element at position " + start);
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
/*     */   private String insertFormats(String pattern, ArrayList<String> customPatterns) {
/* 405 */     if (!containsElements(customPatterns)) {
/* 406 */       return pattern;
/*     */     }
/* 408 */     StringBuilder sb = new StringBuilder(pattern.length() * 2);
/* 409 */     ParsePosition pos = new ParsePosition(0);
/* 410 */     int fe = -1;
/* 411 */     int depth = 0;
/* 412 */     while (pos.getIndex() < pattern.length()) {
/* 413 */       char c = pattern.charAt(pos.getIndex());
/* 414 */       switch (c) {
/*     */         case '\'':
/* 416 */           appendQuotedString(pattern, pos, sb, false);
/*     */           continue;
/*     */         case '{':
/* 419 */           depth++;
/* 420 */           sb.append('{').append(readArgumentIndex(pattern, next(pos)));
/*     */           
/* 422 */           if (depth == 1) {
/* 423 */             fe++;
/* 424 */             String customPattern = customPatterns.get(fe);
/* 425 */             if (customPattern != null) {
/* 426 */               sb.append(',').append(customPattern);
/*     */             }
/*     */           } 
/*     */           continue;
/*     */         case '}':
/* 431 */           depth--;
/*     */           break;
/*     */       } 
/* 434 */       sb.append(c);
/* 435 */       next(pos);
/*     */     } 
/*     */     
/* 438 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void seekNonWs(String pattern, ParsePosition pos) {
/* 448 */     int len = 0;
/* 449 */     char[] buffer = pattern.toCharArray();
/*     */     do {
/* 451 */       len = StrMatcher.splitMatcher().isMatch(buffer, pos.getIndex());
/* 452 */       pos.setIndex(pos.getIndex() + len);
/* 453 */     } while (len > 0 && pos.getIndex() < pattern.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ParsePosition next(ParsePosition pos) {
/* 463 */     pos.setIndex(pos.getIndex() + 1);
/* 464 */     return pos;
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
/*     */   private StringBuilder appendQuotedString(String pattern, ParsePosition pos, StringBuilder appendTo, boolean escapingOn) {
/* 479 */     int start = pos.getIndex();
/* 480 */     char[] c = pattern.toCharArray();
/* 481 */     if (escapingOn && c[start] == '\'') {
/* 482 */       next(pos);
/* 483 */       return (appendTo == null) ? null : appendTo.append('\'');
/*     */     } 
/* 485 */     int lastHold = start;
/* 486 */     for (int i = pos.getIndex(); i < pattern.length(); i++) {
/* 487 */       if (escapingOn && pattern.substring(i).startsWith("''")) {
/* 488 */         appendTo.append(c, lastHold, pos.getIndex() - lastHold).append('\'');
/*     */         
/* 490 */         pos.setIndex(i + "''".length());
/* 491 */         lastHold = pos.getIndex();
/*     */       } else {
/*     */         
/* 494 */         switch (c[pos.getIndex()]) {
/*     */           case '\'':
/* 496 */             next(pos);
/* 497 */             return (appendTo == null) ? null : appendTo.append(c, lastHold, pos.getIndex() - lastHold);
/*     */         } 
/*     */         
/* 500 */         next(pos);
/*     */       } 
/*     */     } 
/* 503 */     throw new IllegalArgumentException("Unterminated quoted string at position " + start);
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
/*     */   private void getQuotedString(String pattern, ParsePosition pos, boolean escapingOn) {
/* 516 */     appendQuotedString(pattern, pos, (StringBuilder)null, escapingOn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean containsElements(Collection<?> coll) {
/* 525 */     if (coll == null || coll.isEmpty()) {
/* 526 */       return false;
/*     */     }
/* 528 */     for (Object name : coll) {
/* 529 */       if (name != null) {
/* 530 */         return true;
/*     */       }
/*     */     } 
/* 533 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\text\ExtendedMessageFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */