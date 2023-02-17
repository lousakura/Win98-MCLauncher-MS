/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.ConcurrentMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocaleUtils
/*     */ {
/*  42 */   private static final ConcurrentMap<String, List<Locale>> cLanguagesByCountry = new ConcurrentHashMap<String, List<Locale>>();
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final ConcurrentMap<String, List<Locale>> cCountriesByLanguage = new ConcurrentHashMap<String, List<Locale>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Locale toLocale(String str) {
/*  91 */     if (str == null) {
/*  92 */       return null;
/*     */     }
/*  94 */     if (str.isEmpty()) {
/*  95 */       return new Locale("", "");
/*     */     }
/*  97 */     if (str.contains("#")) {
/*  98 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 100 */     int len = str.length();
/* 101 */     if (len < 2) {
/* 102 */       throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */     }
/* 104 */     char ch0 = str.charAt(0);
/* 105 */     if (ch0 == '_') {
/* 106 */       if (len < 3) {
/* 107 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 109 */       char ch1 = str.charAt(1);
/* 110 */       char ch2 = str.charAt(2);
/* 111 */       if (!Character.isUpperCase(ch1) || !Character.isUpperCase(ch2)) {
/* 112 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 114 */       if (len == 3) {
/* 115 */         return new Locale("", str.substring(1, 3));
/*     */       }
/* 117 */       if (len < 5) {
/* 118 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 120 */       if (str.charAt(3) != '_') {
/* 121 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */       }
/* 123 */       return new Locale("", str.substring(1, 3), str.substring(4));
/*     */     } 
/*     */     
/* 126 */     String[] split = str.split("_", -1);
/* 127 */     int occurrences = split.length - 1;
/* 128 */     switch (occurrences) {
/*     */       case 0:
/* 130 */         if (StringUtils.isAllLowerCase(str) && (len == 2 || len == 3)) {
/* 131 */           return new Locale(str);
/*     */         }
/* 133 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */ 
/*     */       
/*     */       case 1:
/* 137 */         if (StringUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && split[1].length() == 2 && StringUtils.isAllUpperCase(split[1]))
/*     */         {
/*     */           
/* 140 */           return new Locale(split[0], split[1]);
/*     */         }
/* 142 */         throw new IllegalArgumentException("Invalid locale format: " + str);
/*     */ 
/*     */       
/*     */       case 2:
/* 146 */         if (StringUtils.isAllLowerCase(split[0]) && (split[0].length() == 2 || split[0].length() == 3) && (split[1].length() == 0 || (split[1].length() == 2 && StringUtils.isAllUpperCase(split[1]))) && split[2].length() > 0)
/*     */         {
/*     */ 
/*     */           
/* 150 */           return new Locale(split[0], split[1], split[2]);
/*     */         }
/*     */         break;
/*     */     } 
/*     */     
/* 155 */     throw new IllegalArgumentException("Invalid locale format: " + str);
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
/*     */   public static List<Locale> localeLookupList(Locale locale) {
/* 173 */     return localeLookupList(locale, locale);
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
/*     */   public static List<Locale> localeLookupList(Locale locale, Locale defaultLocale) {
/* 195 */     List<Locale> list = new ArrayList<Locale>(4);
/* 196 */     if (locale != null) {
/* 197 */       list.add(locale);
/* 198 */       if (locale.getVariant().length() > 0) {
/* 199 */         list.add(new Locale(locale.getLanguage(), locale.getCountry()));
/*     */       }
/* 201 */       if (locale.getCountry().length() > 0) {
/* 202 */         list.add(new Locale(locale.getLanguage(), ""));
/*     */       }
/* 204 */       if (!list.contains(defaultLocale)) {
/* 205 */         list.add(defaultLocale);
/*     */       }
/*     */     } 
/* 208 */     return Collections.unmodifiableList(list);
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
/*     */   public static List<Locale> availableLocaleList() {
/* 222 */     return SyncAvoid.AVAILABLE_LOCALE_LIST;
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
/*     */   public static Set<Locale> availableLocaleSet() {
/* 236 */     return SyncAvoid.AVAILABLE_LOCALE_SET;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isAvailableLocale(Locale locale) {
/* 247 */     return availableLocaleList().contains(locale);
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
/*     */   public static List<Locale> languagesByCountry(String countryCode) {
/* 261 */     if (countryCode == null) {
/* 262 */       return Collections.emptyList();
/*     */     }
/* 264 */     List<Locale> langs = cLanguagesByCountry.get(countryCode);
/* 265 */     if (langs == null) {
/* 266 */       langs = new ArrayList<Locale>();
/* 267 */       List<Locale> locales = availableLocaleList();
/* 268 */       for (int i = 0; i < locales.size(); i++) {
/* 269 */         Locale locale = locales.get(i);
/* 270 */         if (countryCode.equals(locale.getCountry()) && locale.getVariant().isEmpty())
/*     */         {
/* 272 */           langs.add(locale);
/*     */         }
/*     */       } 
/* 275 */       langs = Collections.unmodifiableList(langs);
/* 276 */       cLanguagesByCountry.putIfAbsent(countryCode, langs);
/* 277 */       langs = cLanguagesByCountry.get(countryCode);
/*     */     } 
/* 279 */     return langs;
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
/*     */   public static List<Locale> countriesByLanguage(String languageCode) {
/* 293 */     if (languageCode == null) {
/* 294 */       return Collections.emptyList();
/*     */     }
/* 296 */     List<Locale> countries = cCountriesByLanguage.get(languageCode);
/* 297 */     if (countries == null) {
/* 298 */       countries = new ArrayList<Locale>();
/* 299 */       List<Locale> locales = availableLocaleList();
/* 300 */       for (int i = 0; i < locales.size(); i++) {
/* 301 */         Locale locale = locales.get(i);
/* 302 */         if (languageCode.equals(locale.getLanguage()) && locale.getCountry().length() != 0 && locale.getVariant().isEmpty())
/*     */         {
/*     */           
/* 305 */           countries.add(locale);
/*     */         }
/*     */       } 
/* 308 */       countries = Collections.unmodifiableList(countries);
/* 309 */       cCountriesByLanguage.putIfAbsent(languageCode, countries);
/* 310 */       countries = cCountriesByLanguage.get(languageCode);
/*     */     } 
/* 312 */     return countries;
/*     */   }
/*     */ 
/*     */   
/*     */   static class SyncAvoid
/*     */   {
/*     */     private static final List<Locale> AVAILABLE_LOCALE_LIST;
/*     */     
/*     */     private static final Set<Locale> AVAILABLE_LOCALE_SET;
/*     */ 
/*     */     
/*     */     static {
/* 324 */       List<Locale> list = new ArrayList<Locale>(Arrays.asList(Locale.getAvailableLocales()));
/* 325 */       AVAILABLE_LOCALE_LIST = Collections.unmodifiableList(list);
/* 326 */       AVAILABLE_LOCALE_SET = Collections.unmodifiableSet(new HashSet<Locale>(list));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\LocaleUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */