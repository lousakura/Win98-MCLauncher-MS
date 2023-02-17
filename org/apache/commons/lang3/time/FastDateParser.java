/*     */ package org.apache.commons.lang3.time;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.Serializable;
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TimeZone;
/*     */ import java.util.TreeMap;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FastDateParser
/*     */   implements DateParser, Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 2L;
/*  68 */   static final Locale JAPANESE_IMPERIAL = new Locale("ja", "JP", "JP");
/*     */ 
/*     */   
/*     */   private final String pattern;
/*     */ 
/*     */   
/*     */   private final TimeZone timeZone;
/*     */ 
/*     */   
/*     */   private final Locale locale;
/*     */ 
/*     */   
/*     */   private final int century;
/*     */   
/*     */   private final int startYear;
/*     */   
/*     */   private transient Pattern parsePattern;
/*     */   
/*     */   private transient Strategy[] strategies;
/*     */   
/*     */   private transient String currentFormatField;
/*     */   
/*     */   private transient Strategy nextStrategy;
/*     */ 
/*     */   
/*     */   protected FastDateParser(String pattern, TimeZone timeZone, Locale locale) {
/*  94 */     this(pattern, timeZone, locale, null);
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
/*     */   protected FastDateParser(String pattern, TimeZone timeZone, Locale locale, Date centuryStart) {
/*     */     int centuryStartYear;
/* 109 */     this.pattern = pattern;
/* 110 */     this.timeZone = timeZone;
/* 111 */     this.locale = locale;
/*     */     
/* 113 */     Calendar definingCalendar = Calendar.getInstance(timeZone, locale);
/*     */     
/* 115 */     if (centuryStart != null) {
/* 116 */       definingCalendar.setTime(centuryStart);
/* 117 */       centuryStartYear = definingCalendar.get(1);
/*     */     }
/* 119 */     else if (locale.equals(JAPANESE_IMPERIAL)) {
/* 120 */       centuryStartYear = 0;
/*     */     }
/*     */     else {
/*     */       
/* 124 */       definingCalendar.setTime(new Date());
/* 125 */       centuryStartYear = definingCalendar.get(1) - 80;
/*     */     } 
/* 127 */     this.century = centuryStartYear / 100 * 100;
/* 128 */     this.startYear = centuryStartYear - this.century;
/*     */     
/* 130 */     init(definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void init(Calendar definingCalendar) {
/* 141 */     StringBuilder regex = new StringBuilder();
/* 142 */     List<Strategy> collector = new ArrayList<Strategy>();
/*     */     
/* 144 */     Matcher patternMatcher = formatPattern.matcher(this.pattern);
/* 145 */     if (!patternMatcher.lookingAt()) {
/* 146 */       throw new IllegalArgumentException("Illegal pattern character '" + this.pattern.charAt(patternMatcher.regionStart()) + "'");
/*     */     }
/*     */ 
/*     */     
/* 150 */     this.currentFormatField = patternMatcher.group();
/* 151 */     Strategy currentStrategy = getStrategy(this.currentFormatField, definingCalendar);
/*     */     while (true) {
/* 153 */       patternMatcher.region(patternMatcher.end(), patternMatcher.regionEnd());
/* 154 */       if (!patternMatcher.lookingAt()) {
/* 155 */         this.nextStrategy = null;
/*     */         break;
/*     */       } 
/* 158 */       String nextFormatField = patternMatcher.group();
/* 159 */       this.nextStrategy = getStrategy(nextFormatField, definingCalendar);
/* 160 */       if (currentStrategy.addRegex(this, regex)) {
/* 161 */         collector.add(currentStrategy);
/*     */       }
/* 163 */       this.currentFormatField = nextFormatField;
/* 164 */       currentStrategy = this.nextStrategy;
/*     */     } 
/* 166 */     if (patternMatcher.regionStart() != patternMatcher.regionEnd()) {
/* 167 */       throw new IllegalArgumentException("Failed to parse \"" + this.pattern + "\" ; gave up at index " + patternMatcher.regionStart());
/*     */     }
/* 169 */     if (currentStrategy.addRegex(this, regex)) {
/* 170 */       collector.add(currentStrategy);
/*     */     }
/* 172 */     this.currentFormatField = null;
/* 173 */     this.strategies = collector.<Strategy>toArray(new Strategy[collector.size()]);
/* 174 */     this.parsePattern = Pattern.compile(regex.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPattern() {
/* 184 */     return this.pattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TimeZone getTimeZone() {
/* 192 */     return this.timeZone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Locale getLocale() {
/* 200 */     return this.locale;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   Pattern getParsePattern() {
/* 209 */     return this.parsePattern;
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
/*     */   public boolean equals(Object obj) {
/* 222 */     if (!(obj instanceof FastDateParser)) {
/* 223 */       return false;
/*     */     }
/* 225 */     FastDateParser other = (FastDateParser)obj;
/* 226 */     return (this.pattern.equals(other.pattern) && this.timeZone.equals(other.timeZone) && this.locale.equals(other.locale));
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
/*     */   public int hashCode() {
/* 238 */     return this.pattern.hashCode() + 13 * (this.timeZone.hashCode() + 13 * this.locale.hashCode());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 248 */     return "FastDateParser[" + this.pattern + "," + this.locale + "," + this.timeZone.getID() + "]";
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
/*     */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/* 262 */     in.defaultReadObject();
/*     */     
/* 264 */     Calendar definingCalendar = Calendar.getInstance(this.timeZone, this.locale);
/* 265 */     init(definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source) throws ParseException {
/* 273 */     return parse(source);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source) throws ParseException {
/* 281 */     Date date = parse(source, new ParsePosition(0));
/* 282 */     if (date == null) {
/*     */       
/* 284 */       if (this.locale.equals(JAPANESE_IMPERIAL)) {
/* 285 */         throw new ParseException("(The " + this.locale + " locale does not support dates before 1868 AD)\n" + "Unparseable date: \"" + source + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */       }
/*     */ 
/*     */       
/* 289 */       throw new ParseException("Unparseable date: \"" + source + "\" does not match " + this.parsePattern.pattern(), 0);
/*     */     } 
/* 291 */     return date;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object parseObject(String source, ParsePosition pos) {
/* 299 */     return parse(source, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Date parse(String source, ParsePosition pos) {
/* 307 */     int offset = pos.getIndex();
/* 308 */     Matcher matcher = this.parsePattern.matcher(source.substring(offset));
/* 309 */     if (!matcher.lookingAt()) {
/* 310 */       return null;
/*     */     }
/*     */     
/* 313 */     Calendar cal = Calendar.getInstance(this.timeZone, this.locale);
/* 314 */     cal.clear();
/*     */     
/* 316 */     for (int i = 0; i < this.strategies.length; ) {
/* 317 */       Strategy strategy = this.strategies[i++];
/* 318 */       strategy.setCalendar(this, cal, matcher.group(i));
/*     */     } 
/* 320 */     pos.setIndex(offset + matcher.end());
/* 321 */     return cal.getTime();
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
/*     */   private static StringBuilder escapeRegex(StringBuilder regex, String value, boolean unquote) {
/* 335 */     regex.append("\\Q");
/* 336 */     for (int i = 0; i < value.length(); i++) {
/* 337 */       char c = value.charAt(i);
/* 338 */       switch (c) {
/*     */         case '\'':
/* 340 */           if (unquote) {
/* 341 */             if (++i == value.length()) {
/* 342 */               return regex;
/*     */             }
/* 344 */             c = value.charAt(i);
/*     */           } 
/*     */           break;
/*     */         case '\\':
/* 348 */           if (++i == value.length()) {
/*     */             break;
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 358 */           regex.append(c);
/* 359 */           c = value.charAt(i);
/* 360 */           if (c == 'E') {
/* 361 */             regex.append("E\\\\E\\");
/* 362 */             c = 'Q';
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 368 */       regex.append(c);
/*     */     } 
/* 370 */     regex.append("\\E");
/* 371 */     return regex;
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
/*     */   private static Map<String, Integer> getDisplayNames(int field, Calendar definingCalendar, Locale locale) {
/* 383 */     return definingCalendar.getDisplayNames(field, 0, locale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int adjustYear(int twoDigitYear) {
/* 392 */     int trial = this.century + twoDigitYear;
/* 393 */     return (twoDigitYear >= this.startYear) ? trial : (trial + 100);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   boolean isNextNumber() {
/* 401 */     return (this.nextStrategy != null && this.nextStrategy.isNumber());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int getFieldWidth() {
/* 409 */     return this.currentFormatField.length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static abstract class Strategy
/*     */   {
/*     */     private Strategy() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 423 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     abstract boolean addRegex(FastDateParser param1FastDateParser, StringBuilder param1StringBuilder);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 451 */   private static final Pattern formatPattern = Pattern.compile("D+|E+|F+|G+|H+|K+|M+|S+|W+|Z+|a+|d+|h+|k+|m+|s+|w+|y+|z+|''|'[^']++(''[^']*+)*+'|[^'A-Za-z]++");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getStrategy(String formatField, Calendar definingCalendar) {
/* 461 */     switch (formatField.charAt(0))
/*     */     { case '\'':
/* 463 */         if (formatField.length() > 2) {
/* 464 */           return new CopyQuotedStrategy(formatField.substring(1, formatField.length() - 1));
/*     */         }
/*     */       
/*     */       default:
/* 468 */         return new CopyQuotedStrategy(formatField);
/*     */       case 'D':
/* 470 */         return DAY_OF_YEAR_STRATEGY;
/*     */       case 'E':
/* 472 */         return getLocaleSpecificStrategy(7, definingCalendar);
/*     */       case 'F':
/* 474 */         return DAY_OF_WEEK_IN_MONTH_STRATEGY;
/*     */       case 'G':
/* 476 */         return getLocaleSpecificStrategy(0, definingCalendar);
/*     */       case 'H':
/* 478 */         return MODULO_HOUR_OF_DAY_STRATEGY;
/*     */       case 'K':
/* 480 */         return HOUR_STRATEGY;
/*     */       case 'M':
/* 482 */         return (formatField.length() >= 3) ? getLocaleSpecificStrategy(2, definingCalendar) : NUMBER_MONTH_STRATEGY;
/*     */       case 'S':
/* 484 */         return MILLISECOND_STRATEGY;
/*     */       case 'W':
/* 486 */         return WEEK_OF_MONTH_STRATEGY;
/*     */       case 'a':
/* 488 */         return getLocaleSpecificStrategy(9, definingCalendar);
/*     */       case 'd':
/* 490 */         return DAY_OF_MONTH_STRATEGY;
/*     */       case 'h':
/* 492 */         return MODULO_HOUR_STRATEGY;
/*     */       case 'k':
/* 494 */         return HOUR_OF_DAY_STRATEGY;
/*     */       case 'm':
/* 496 */         return MINUTE_STRATEGY;
/*     */       case 's':
/* 498 */         return SECOND_STRATEGY;
/*     */       case 'w':
/* 500 */         return WEEK_OF_YEAR_STRATEGY;
/*     */       case 'y':
/* 502 */         return (formatField.length() > 2) ? LITERAL_YEAR_STRATEGY : ABBREVIATED_YEAR_STRATEGY;
/*     */       case 'Z':
/*     */       case 'z':
/* 505 */         break; }  return getLocaleSpecificStrategy(15, definingCalendar);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 510 */   private static final ConcurrentMap<Locale, Strategy>[] caches = (ConcurrentMap<Locale, Strategy>[])new ConcurrentMap[17];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ConcurrentMap<Locale, Strategy> getCache(int field) {
/* 518 */     synchronized (caches) {
/* 519 */       if (caches[field] == null) {
/* 520 */         caches[field] = new ConcurrentHashMap<Locale, Strategy>(3);
/*     */       }
/* 522 */       return caches[field];
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Strategy getLocaleSpecificStrategy(int field, Calendar definingCalendar) {
/* 533 */     ConcurrentMap<Locale, Strategy> cache = getCache(field);
/* 534 */     Strategy strategy = cache.get(this.locale);
/* 535 */     if (strategy == null) {
/* 536 */       strategy = (field == 15) ? new TimeZoneStrategy(this.locale) : new TextStrategy(field, definingCalendar, this.locale);
/*     */ 
/*     */       
/* 539 */       Strategy inCache = cache.putIfAbsent(this.locale, strategy);
/* 540 */       if (inCache != null) {
/* 541 */         return inCache;
/*     */       }
/*     */     } 
/* 544 */     return strategy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class CopyQuotedStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final String formatField;
/*     */ 
/*     */ 
/*     */     
/*     */     CopyQuotedStrategy(String formatField) {
/* 558 */       this.formatField = formatField;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 566 */       char c = this.formatField.charAt(0);
/* 567 */       if (c == '\'') {
/* 568 */         c = this.formatField.charAt(1);
/*     */       }
/* 570 */       return Character.isDigit(c);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 578 */       FastDateParser.escapeRegex(regex, this.formatField, true);
/* 579 */       return false;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class TextStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */     
/*     */     private final Map<String, Integer> keyValues;
/*     */ 
/*     */ 
/*     */     
/*     */     TextStrategy(int field, Calendar definingCalendar, Locale locale) {
/* 597 */       this.field = field;
/* 598 */       this.keyValues = FastDateParser.getDisplayNames(field, definingCalendar, locale);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 606 */       regex.append('(');
/* 607 */       for (String textKeyValue : this.keyValues.keySet()) {
/* 608 */         FastDateParser.escapeRegex(regex, textKeyValue, false).append('|');
/*     */       }
/* 610 */       regex.setCharAt(regex.length() - 1, ')');
/* 611 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 619 */       Integer iVal = this.keyValues.get(value);
/* 620 */       if (iVal == null) {
/* 621 */         StringBuilder sb = new StringBuilder(value);
/* 622 */         sb.append(" not in (");
/* 623 */         for (String textKeyValue : this.keyValues.keySet()) {
/* 624 */           sb.append(textKeyValue).append(' ');
/*     */         }
/* 626 */         sb.setCharAt(sb.length() - 1, ')');
/* 627 */         throw new IllegalArgumentException(sb.toString());
/*     */       } 
/* 629 */       cal.set(this.field, iVal.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class NumberStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final int field;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     NumberStrategy(int field) {
/* 645 */       this.field = field;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean isNumber() {
/* 653 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 662 */       if (parser.isNextNumber()) {
/* 663 */         regex.append("(\\p{Nd}{").append(parser.getFieldWidth()).append("}+)");
/*     */       } else {
/*     */         
/* 666 */         regex.append("(\\p{Nd}++)");
/*     */       } 
/* 668 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/* 676 */       cal.set(this.field, modify(Integer.parseInt(value)));
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     int modify(int iValue) {
/* 685 */       return iValue;
/*     */     }
/*     */   }
/*     */   
/* 689 */   private static final Strategy ABBREVIATED_YEAR_STRATEGY = new NumberStrategy(1)
/*     */     {
/*     */ 
/*     */       
/*     */       void setCalendar(FastDateParser parser, Calendar cal, String value)
/*     */       {
/* 695 */         int iValue = Integer.parseInt(value);
/* 696 */         if (iValue < 100) {
/* 697 */           iValue = parser.adjustYear(iValue);
/*     */         }
/* 699 */         cal.set(1, iValue);
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private static class TimeZoneStrategy
/*     */     extends Strategy
/*     */   {
/*     */     private final String validTimeZoneChars;
/*     */     
/* 709 */     private final SortedMap<String, TimeZone> tzNames = new TreeMap<String, TimeZone>(String.CASE_INSENSITIVE_ORDER);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int ID = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int LONG_STD = 1;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SHORT_STD = 2;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int LONG_DST = 3;
/*     */ 
/*     */ 
/*     */     
/*     */     private static final int SHORT_DST = 4;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     TimeZoneStrategy(Locale locale) {
/* 737 */       String[][] zones = DateFormatSymbols.getInstance(locale).getZoneStrings();
/* 738 */       for (String[] zone : zones) {
/* 739 */         if (!zone[0].startsWith("GMT")) {
/*     */ 
/*     */           
/* 742 */           TimeZone tz = TimeZone.getTimeZone(zone[0]);
/* 743 */           if (!this.tzNames.containsKey(zone[1])) {
/* 744 */             this.tzNames.put(zone[1], tz);
/*     */           }
/* 746 */           if (!this.tzNames.containsKey(zone[2])) {
/* 747 */             this.tzNames.put(zone[2], tz);
/*     */           }
/* 749 */           if (tz.useDaylightTime()) {
/* 750 */             if (!this.tzNames.containsKey(zone[3])) {
/* 751 */               this.tzNames.put(zone[3], tz);
/*     */             }
/* 753 */             if (!this.tzNames.containsKey(zone[4])) {
/* 754 */               this.tzNames.put(zone[4], tz);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/* 759 */       StringBuilder sb = new StringBuilder();
/* 760 */       sb.append("(GMT[+\\-]\\d{0,1}\\d{2}|[+\\-]\\d{2}:?\\d{2}|");
/* 761 */       for (String id : this.tzNames.keySet()) {
/* 762 */         FastDateParser.escapeRegex(sb, id, false).append('|');
/*     */       }
/* 764 */       sb.setCharAt(sb.length() - 1, ')');
/* 765 */       this.validTimeZoneChars = sb.toString();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     boolean addRegex(FastDateParser parser, StringBuilder regex) {
/* 773 */       regex.append(this.validTimeZoneChars);
/* 774 */       return true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     void setCalendar(FastDateParser parser, Calendar cal, String value) {
/*     */       TimeZone tz;
/* 783 */       if (value.charAt(0) == '+' || value.charAt(0) == '-') {
/* 784 */         tz = TimeZone.getTimeZone("GMT" + value);
/*     */       }
/* 786 */       else if (value.startsWith("GMT")) {
/* 787 */         tz = TimeZone.getTimeZone(value);
/*     */       } else {
/*     */         
/* 790 */         tz = this.tzNames.get(value);
/* 791 */         if (tz == null) {
/* 792 */           throw new IllegalArgumentException(value + " is not a supported timezone name");
/*     */         }
/*     */       } 
/* 795 */       cal.setTimeZone(tz);
/*     */     }
/*     */   }
/*     */   
/* 799 */   private static final Strategy NUMBER_MONTH_STRATEGY = new NumberStrategy(2)
/*     */     {
/*     */       int modify(int iValue) {
/* 802 */         return iValue - 1;
/*     */       }
/*     */     };
/* 805 */   private static final Strategy LITERAL_YEAR_STRATEGY = new NumberStrategy(1);
/* 806 */   private static final Strategy WEEK_OF_YEAR_STRATEGY = new NumberStrategy(3);
/* 807 */   private static final Strategy WEEK_OF_MONTH_STRATEGY = new NumberStrategy(4);
/* 808 */   private static final Strategy DAY_OF_YEAR_STRATEGY = new NumberStrategy(6);
/* 809 */   private static final Strategy DAY_OF_MONTH_STRATEGY = new NumberStrategy(5);
/* 810 */   private static final Strategy DAY_OF_WEEK_IN_MONTH_STRATEGY = new NumberStrategy(8);
/* 811 */   private static final Strategy HOUR_OF_DAY_STRATEGY = new NumberStrategy(11);
/* 812 */   private static final Strategy MODULO_HOUR_OF_DAY_STRATEGY = new NumberStrategy(11)
/*     */     {
/*     */       int modify(int iValue) {
/* 815 */         return iValue % 24;
/*     */       }
/*     */     };
/* 818 */   private static final Strategy MODULO_HOUR_STRATEGY = new NumberStrategy(10)
/*     */     {
/*     */       int modify(int iValue) {
/* 821 */         return iValue % 12;
/*     */       }
/*     */     };
/* 824 */   private static final Strategy HOUR_STRATEGY = new NumberStrategy(10);
/* 825 */   private static final Strategy MINUTE_STRATEGY = new NumberStrategy(12);
/* 826 */   private static final Strategy SECOND_STRATEGY = new NumberStrategy(13);
/* 827 */   private static final Strategy MILLISECOND_STRATEGY = new NumberStrategy(14);
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\time\FastDateParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */