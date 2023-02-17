/*      */ package org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.text.ParseException;
/*      */ import java.text.ParsePosition;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.Locale;
/*      */ import java.util.NoSuchElementException;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DateUtils
/*      */ {
/*      */   public static final long MILLIS_PER_SECOND = 1000L;
/*      */   public static final long MILLIS_PER_MINUTE = 60000L;
/*      */   public static final long MILLIS_PER_HOUR = 3600000L;
/*      */   public static final long MILLIS_PER_DAY = 86400000L;
/*      */   public static final int SEMI_MONTH = 1001;
/*   76 */   private static final int[][] fields = new int[][] { { 14 }, { 13 }, { 12 }, { 11, 10 }, { 5, 5, 9 }, { 2, 1001 }, { 1 }, { 0 } };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_SUNDAY = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_MONDAY = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_RELATIVE = 3;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_WEEK_CENTER = 4;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_SUNDAY = 5;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final int RANGE_MONTH_MONDAY = 6;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_TRUNCATE = 0;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_ROUND = 1;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int MODIFY_CEILING = 2;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Date date1, Date date2) {
/*  156 */     if (date1 == null || date2 == null) {
/*  157 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  159 */     Calendar cal1 = Calendar.getInstance();
/*  160 */     cal1.setTime(date1);
/*  161 */     Calendar cal2 = Calendar.getInstance();
/*  162 */     cal2.setTime(date2);
/*  163 */     return isSameDay(cal1, cal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameDay(Calendar cal1, Calendar cal2) {
/*  180 */     if (cal1 == null || cal2 == null) {
/*  181 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  183 */     return (cal1.get(0) == cal2.get(0) && cal1.get(1) == cal2.get(1) && cal1.get(6) == cal2.get(6));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Date date1, Date date2) {
/*  201 */     if (date1 == null || date2 == null) {
/*  202 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  204 */     return (date1.getTime() == date2.getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameInstant(Calendar cal1, Calendar cal2) {
/*  219 */     if (cal1 == null || cal2 == null) {
/*  220 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  222 */     return (cal1.getTime().getTime() == cal2.getTime().getTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSameLocalTime(Calendar cal1, Calendar cal2) {
/*  239 */     if (cal1 == null || cal2 == null) {
/*  240 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  242 */     return (cal1.get(14) == cal2.get(14) && cal1.get(13) == cal2.get(13) && cal1.get(12) == cal2.get(12) && cal1.get(11) == cal2.get(11) && cal1.get(6) == cal2.get(6) && cal1.get(1) == cal2.get(1) && cal1.get(0) == cal2.get(0) && cal1.getClass() == cal2.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, String... parsePatterns) throws ParseException {
/*  268 */     return parseDate(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDate(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  291 */     return parseDateWithLeniency(str, locale, parsePatterns, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, String... parsePatterns) throws ParseException {
/*  311 */     return parseDateStrictly(str, null, parsePatterns);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date parseDateStrictly(String str, Locale locale, String... parsePatterns) throws ParseException {
/*  333 */     return parseDateWithLeniency(str, null, parsePatterns, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date parseDateWithLeniency(String str, Locale locale, String[] parsePatterns, boolean lenient) throws ParseException {
/*      */     SimpleDateFormat parser;
/*  355 */     if (str == null || parsePatterns == null) {
/*  356 */       throw new IllegalArgumentException("Date and Patterns must not be null");
/*      */     }
/*      */ 
/*      */     
/*  360 */     if (locale == null) {
/*  361 */       parser = new SimpleDateFormat();
/*      */     } else {
/*  363 */       parser = new SimpleDateFormat("", locale);
/*      */     } 
/*      */     
/*  366 */     parser.setLenient(lenient);
/*  367 */     ParsePosition pos = new ParsePosition(0);
/*  368 */     for (String parsePattern : parsePatterns) {
/*      */       
/*  370 */       String pattern = parsePattern;
/*      */ 
/*      */       
/*  373 */       if (parsePattern.endsWith("ZZ")) {
/*  374 */         pattern = pattern.substring(0, pattern.length() - 1);
/*      */       }
/*      */       
/*  377 */       parser.applyPattern(pattern);
/*  378 */       pos.setIndex(0);
/*      */       
/*  380 */       String str2 = str;
/*      */       
/*  382 */       if (parsePattern.endsWith("ZZ")) {
/*  383 */         str2 = str.replaceAll("([-+][0-9][0-9]):([0-9][0-9])$", "$1$2");
/*      */       }
/*      */       
/*  386 */       Date date = parser.parse(str2, pos);
/*  387 */       if (date != null && pos.getIndex() == str2.length()) {
/*  388 */         return date;
/*      */       }
/*      */     } 
/*  391 */     throw new ParseException("Unable to parse the date: " + str, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addYears(Date date, int amount) {
/*  405 */     return add(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMonths(Date date, int amount) {
/*  419 */     return add(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addWeeks(Date date, int amount) {
/*  433 */     return add(date, 3, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addDays(Date date, int amount) {
/*  447 */     return add(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addHours(Date date, int amount) {
/*  461 */     return add(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMinutes(Date date, int amount) {
/*  475 */     return add(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addSeconds(Date date, int amount) {
/*  489 */     return add(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date addMilliseconds(Date date, int amount) {
/*  503 */     return add(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date add(Date date, int calendarField, int amount) {
/*  518 */     if (date == null) {
/*  519 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  521 */     Calendar c = Calendar.getInstance();
/*  522 */     c.setTime(date);
/*  523 */     c.add(calendarField, amount);
/*  524 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setYears(Date date, int amount) {
/*  539 */     return set(date, 1, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMonths(Date date, int amount) {
/*  554 */     return set(date, 2, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setDays(Date date, int amount) {
/*  569 */     return set(date, 5, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setHours(Date date, int amount) {
/*  585 */     return set(date, 11, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMinutes(Date date, int amount) {
/*  600 */     return set(date, 12, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setSeconds(Date date, int amount) {
/*  615 */     return set(date, 13, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date setMilliseconds(Date date, int amount) {
/*  630 */     return set(date, 14, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Date set(Date date, int calendarField, int amount) {
/*  647 */     if (date == null) {
/*  648 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*      */     
/*  651 */     Calendar c = Calendar.getInstance();
/*  652 */     c.setLenient(false);
/*  653 */     c.setTime(date);
/*  654 */     c.set(calendarField, amount);
/*  655 */     return c.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar toCalendar(Date date) {
/*  668 */     Calendar c = Calendar.getInstance();
/*  669 */     c.setTime(date);
/*  670 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Date date, int field) {
/*  701 */     if (date == null) {
/*  702 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  704 */     Calendar gval = Calendar.getInstance();
/*  705 */     gval.setTime(date);
/*  706 */     modify(gval, field, 1);
/*  707 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar round(Calendar date, int field) {
/*  738 */     if (date == null) {
/*  739 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  741 */     Calendar rounded = (Calendar)date.clone();
/*  742 */     modify(rounded, field, 1);
/*  743 */     return rounded;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date round(Object date, int field) {
/*  775 */     if (date == null) {
/*  776 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  778 */     if (date instanceof Date)
/*  779 */       return round((Date)date, field); 
/*  780 */     if (date instanceof Calendar) {
/*  781 */       return round((Calendar)date, field).getTime();
/*      */     }
/*  783 */     throw new ClassCastException("Could not round " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Date date, int field) {
/*  804 */     if (date == null) {
/*  805 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  807 */     Calendar gval = Calendar.getInstance();
/*  808 */     gval.setTime(date);
/*  809 */     modify(gval, field, 0);
/*  810 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar truncate(Calendar date, int field) {
/*  829 */     if (date == null) {
/*  830 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  832 */     Calendar truncated = (Calendar)date.clone();
/*  833 */     modify(truncated, field, 0);
/*  834 */     return truncated;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date truncate(Object date, int field) {
/*  854 */     if (date == null) {
/*  855 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  857 */     if (date instanceof Date)
/*  858 */       return truncate((Date)date, field); 
/*  859 */     if (date instanceof Calendar) {
/*  860 */       return truncate((Calendar)date, field).getTime();
/*      */     }
/*  862 */     throw new ClassCastException("Could not truncate " + date);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Date date, int field) {
/*  884 */     if (date == null) {
/*  885 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  887 */     Calendar gval = Calendar.getInstance();
/*  888 */     gval.setTime(date);
/*  889 */     modify(gval, field, 2);
/*  890 */     return gval.getTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Calendar ceiling(Calendar date, int field) {
/*  910 */     if (date == null) {
/*  911 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  913 */     Calendar ceiled = (Calendar)date.clone();
/*  914 */     modify(ceiled, field, 2);
/*  915 */     return ceiled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Date ceiling(Object date, int field) {
/*  936 */     if (date == null) {
/*  937 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*  939 */     if (date instanceof Date)
/*  940 */       return ceiling((Date)date, field); 
/*  941 */     if (date instanceof Calendar) {
/*  942 */       return ceiling((Calendar)date, field).getTime();
/*      */     }
/*  944 */     throw new ClassCastException("Could not find ceiling of for type: " + date.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void modify(Calendar val, int field, int modType) {
/*  958 */     if (val.get(1) > 280000000) {
/*  959 */       throw new ArithmeticException("Calendar value too large for accurate calculations");
/*      */     }
/*      */     
/*  962 */     if (field == 14) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  972 */     Date date = val.getTime();
/*  973 */     long time = date.getTime();
/*  974 */     boolean done = false;
/*      */ 
/*      */     
/*  977 */     int millisecs = val.get(14);
/*  978 */     if (0 == modType || millisecs < 500) {
/*  979 */       time -= millisecs;
/*      */     }
/*  981 */     if (field == 13) {
/*  982 */       done = true;
/*      */     }
/*      */ 
/*      */     
/*  986 */     int seconds = val.get(13);
/*  987 */     if (!done && (0 == modType || seconds < 30)) {
/*  988 */       time -= seconds * 1000L;
/*      */     }
/*  990 */     if (field == 12) {
/*  991 */       done = true;
/*      */     }
/*      */ 
/*      */     
/*  995 */     int minutes = val.get(12);
/*  996 */     if (!done && (0 == modType || minutes < 30)) {
/*  997 */       time -= minutes * 60000L;
/*      */     }
/*      */ 
/*      */     
/* 1001 */     if (date.getTime() != time) {
/* 1002 */       date.setTime(time);
/* 1003 */       val.setTime(date);
/*      */     } 
/*      */ 
/*      */     
/* 1007 */     boolean roundUp = false;
/* 1008 */     for (int[] aField : fields) {
/* 1009 */       for (int element : aField) {
/* 1010 */         if (element == field) {
/*      */           
/* 1012 */           if (modType == 2 || (modType == 1 && roundUp)) {
/* 1013 */             if (field == 1001) {
/*      */ 
/*      */ 
/*      */               
/* 1017 */               if (val.get(5) == 1) {
/* 1018 */                 val.add(5, 15);
/*      */               } else {
/* 1020 */                 val.add(5, -15);
/* 1021 */                 val.add(2, 1);
/*      */               }
/*      */             
/* 1024 */             } else if (field == 9) {
/*      */ 
/*      */ 
/*      */               
/* 1028 */               if (val.get(11) == 0) {
/* 1029 */                 val.add(11, 12);
/*      */               } else {
/* 1031 */                 val.add(11, -12);
/* 1032 */                 val.add(5, 1);
/*      */               }
/*      */             
/*      */             }
/*      */             else {
/*      */               
/* 1038 */               val.add(aField[0], 1);
/*      */             } 
/*      */           }
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 1045 */       int offset = 0;
/* 1046 */       boolean offsetSet = false;
/*      */       
/* 1048 */       switch (field) {
/*      */         case 1001:
/* 1050 */           if (aField[0] == 5) {
/*      */ 
/*      */ 
/*      */             
/* 1054 */             offset = val.get(5) - 1;
/*      */ 
/*      */             
/* 1057 */             if (offset >= 15) {
/* 1058 */               offset -= 15;
/*      */             }
/*      */             
/* 1061 */             roundUp = (offset > 7);
/* 1062 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */         case 9:
/* 1066 */           if (aField[0] == 11) {
/*      */ 
/*      */             
/* 1069 */             offset = val.get(11);
/* 1070 */             if (offset >= 12) {
/* 1071 */               offset -= 12;
/*      */             }
/* 1073 */             roundUp = (offset >= 6);
/* 1074 */             offsetSet = true;
/*      */           } 
/*      */           break;
/*      */       } 
/*      */ 
/*      */       
/* 1080 */       if (!offsetSet) {
/* 1081 */         int min = val.getActualMinimum(aField[0]);
/* 1082 */         int max = val.getActualMaximum(aField[0]);
/*      */         
/* 1084 */         offset = val.get(aField[0]) - min;
/*      */         
/* 1086 */         roundUp = (offset > (max - min) / 2);
/*      */       } 
/*      */       
/* 1089 */       if (offset != 0) {
/* 1090 */         val.set(aField[0], val.get(aField[0]) - offset);
/*      */       }
/*      */     } 
/* 1093 */     throw new IllegalArgumentException("The field " + field + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Date focus, int rangeStyle) {
/* 1123 */     if (focus == null) {
/* 1124 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1126 */     Calendar gval = Calendar.getInstance();
/* 1127 */     gval.setTime(focus);
/* 1128 */     return iterator(gval, rangeStyle);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<Calendar> iterator(Calendar focus, int rangeStyle) {
/* 1156 */     if (focus == null) {
/* 1157 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1159 */     Calendar start = null;
/* 1160 */     Calendar end = null;
/* 1161 */     int startCutoff = 1;
/* 1162 */     int endCutoff = 7;
/* 1163 */     switch (rangeStyle) {
/*      */       
/*      */       case 5:
/*      */       case 6:
/* 1167 */         start = truncate(focus, 2);
/*      */         
/* 1169 */         end = (Calendar)start.clone();
/* 1170 */         end.add(2, 1);
/* 1171 */         end.add(5, -1);
/*      */         
/* 1173 */         if (rangeStyle == 6) {
/* 1174 */           startCutoff = 2;
/* 1175 */           endCutoff = 1;
/*      */         } 
/*      */         break;
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/* 1183 */         start = truncate(focus, 5);
/* 1184 */         end = truncate(focus, 5);
/* 1185 */         switch (rangeStyle) {
/*      */ 
/*      */ 
/*      */           
/*      */           case 2:
/* 1190 */             startCutoff = 2;
/* 1191 */             endCutoff = 1;
/*      */             break;
/*      */           case 3:
/* 1194 */             startCutoff = focus.get(7);
/* 1195 */             endCutoff = startCutoff - 1;
/*      */             break;
/*      */           case 4:
/* 1198 */             startCutoff = focus.get(7) - 3;
/* 1199 */             endCutoff = focus.get(7) + 3;
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       default:
/* 1206 */         throw new IllegalArgumentException("The range style " + rangeStyle + " is not valid.");
/*      */     } 
/* 1208 */     if (startCutoff < 1) {
/* 1209 */       startCutoff += 7;
/*      */     }
/* 1211 */     if (startCutoff > 7) {
/* 1212 */       startCutoff -= 7;
/*      */     }
/* 1214 */     if (endCutoff < 1) {
/* 1215 */       endCutoff += 7;
/*      */     }
/* 1217 */     if (endCutoff > 7) {
/* 1218 */       endCutoff -= 7;
/*      */     }
/* 1220 */     while (start.get(7) != startCutoff) {
/* 1221 */       start.add(5, -1);
/*      */     }
/* 1223 */     while (end.get(7) != endCutoff) {
/* 1224 */       end.add(5, 1);
/*      */     }
/* 1226 */     return new DateIterator(start, end);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterator<?> iterator(Object focus, int rangeStyle) {
/* 1246 */     if (focus == null) {
/* 1247 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1249 */     if (focus instanceof Date)
/* 1250 */       return iterator((Date)focus, rangeStyle); 
/* 1251 */     if (focus instanceof Calendar) {
/* 1252 */       return iterator((Calendar)focus, rangeStyle);
/*      */     }
/* 1254 */     throw new ClassCastException("Could not iterate based on " + focus);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Date date, int fragment) {
/* 1290 */     return getFragment(date, fragment, TimeUnit.MILLISECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Date date, int fragment) {
/* 1328 */     return getFragment(date, fragment, TimeUnit.SECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Date date, int fragment) {
/* 1366 */     return getFragment(date, fragment, TimeUnit.MINUTES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Date date, int fragment) {
/* 1404 */     return getFragment(date, fragment, TimeUnit.HOURS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Date date, int fragment) {
/* 1442 */     return getFragment(date, fragment, TimeUnit.DAYS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMilliseconds(Calendar calendar, int fragment) {
/* 1480 */     return getFragment(calendar, fragment, TimeUnit.MILLISECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInSeconds(Calendar calendar, int fragment) {
/* 1517 */     return getFragment(calendar, fragment, TimeUnit.SECONDS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInMinutes(Calendar calendar, int fragment) {
/* 1555 */     return getFragment(calendar, fragment, TimeUnit.MINUTES);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInHours(Calendar calendar, int fragment) {
/* 1593 */     return getFragment(calendar, fragment, TimeUnit.HOURS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getFragmentInDays(Calendar calendar, int fragment) {
/* 1633 */     return getFragment(calendar, fragment, TimeUnit.DAYS);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Date date, int fragment, TimeUnit unit) {
/* 1648 */     if (date == null) {
/* 1649 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/* 1651 */     Calendar calendar = Calendar.getInstance();
/* 1652 */     calendar.setTime(date);
/* 1653 */     return getFragment(calendar, fragment, unit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static long getFragment(Calendar calendar, int fragment, TimeUnit unit) {
/* 1668 */     if (calendar == null) {
/* 1669 */       throw new IllegalArgumentException("The date must not be null");
/*      */     }
/*      */     
/* 1672 */     long result = 0L;
/*      */     
/* 1674 */     int offset = (unit == TimeUnit.DAYS) ? 0 : 1;
/*      */ 
/*      */     
/* 1677 */     switch (fragment) {
/*      */       case 1:
/* 1679 */         result += unit.convert((calendar.get(6) - offset), TimeUnit.DAYS);
/*      */         break;
/*      */       case 2:
/* 1682 */         result += unit.convert((calendar.get(5) - offset), TimeUnit.DAYS);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1688 */     switch (fragment) {
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*      */       case 2:
/*      */       case 5:
/*      */       case 6:
/* 1696 */         result += unit.convert(calendar.get(11), TimeUnit.HOURS);
/*      */       
/*      */       case 11:
/* 1699 */         result += unit.convert(calendar.get(12), TimeUnit.MINUTES);
/*      */       
/*      */       case 12:
/* 1702 */         result += unit.convert(calendar.get(13), TimeUnit.SECONDS);
/*      */       
/*      */       case 13:
/* 1705 */         result += unit.convert(calendar.get(14), TimeUnit.MILLISECONDS);
/*      */ 
/*      */ 
/*      */       
/*      */       case 14:
/* 1710 */         return result;
/*      */     } 
/*      */     throw new IllegalArgumentException("The fragment " + fragment + " is not supported");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Calendar cal1, Calendar cal2, int field) {
/* 1727 */     return (truncatedCompareTo(cal1, cal2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean truncatedEquals(Date date1, Date date2, int field) {
/* 1744 */     return (truncatedCompareTo(date1, date2, field) == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Calendar cal1, Calendar cal2, int field) {
/* 1762 */     Calendar truncatedCal1 = truncate(cal1, field);
/* 1763 */     Calendar truncatedCal2 = truncate(cal2, field);
/* 1764 */     return truncatedCal1.compareTo(truncatedCal2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int truncatedCompareTo(Date date1, Date date2, int field) {
/* 1782 */     Date truncatedDate1 = truncate(date1, field);
/* 1783 */     Date truncatedDate2 = truncate(date2, field);
/* 1784 */     return truncatedDate1.compareTo(truncatedDate2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static class DateIterator
/*      */     implements Iterator<Calendar>
/*      */   {
/*      */     private final Calendar endFinal;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Calendar spot;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     DateIterator(Calendar startFinal, Calendar endFinal) {
/* 1804 */       this.endFinal = endFinal;
/* 1805 */       this.spot = startFinal;
/* 1806 */       this.spot.add(5, -1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean hasNext() {
/* 1816 */       return this.spot.before(this.endFinal);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Calendar next() {
/* 1826 */       if (this.spot.equals(this.endFinal)) {
/* 1827 */         throw new NoSuchElementException();
/*      */       }
/* 1829 */       this.spot.add(5, 1);
/* 1830 */       return (Calendar)this.spot.clone();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void remove() {
/* 1841 */       throw new UnsupportedOperationException();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\time\DateUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */