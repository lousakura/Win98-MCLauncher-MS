/*      */ package org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.commons.lang3.ObjectUtils;
/*      */ import org.apache.commons.lang3.SystemUtils;
/*      */ import org.apache.commons.lang3.builder.Builder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrBuilder
/*      */   implements CharSequence, Appendable, Serializable, Builder<String>
/*      */ {
/*      */   static final int CAPACITY = 32;
/*      */   private static final long serialVersionUID = 7628716375283629643L;
/*      */   protected char[] buffer;
/*      */   protected int size;
/*      */   private String newLine;
/*      */   private String nullText;
/*      */   
/*      */   public StrBuilder() {
/*  103 */     this(32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(int initialCapacity) {
/*  113 */     if (initialCapacity <= 0) {
/*  114 */       initialCapacity = 32;
/*      */     }
/*  116 */     this.buffer = new char[initialCapacity];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(String str) {
/*  127 */     if (str == null) {
/*  128 */       this.buffer = new char[32];
/*      */     } else {
/*  130 */       this.buffer = new char[str.length() + 32];
/*  131 */       append(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNewLineText() {
/*  142 */     return this.newLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNewLineText(String newLine) {
/*  152 */     this.newLine = newLine;
/*  153 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNullText() {
/*  163 */     return this.nullText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNullText(String nullText) {
/*  173 */     if (nullText != null && nullText.isEmpty()) {
/*  174 */       nullText = null;
/*      */     }
/*  176 */     this.nullText = nullText;
/*  177 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  188 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setLength(int length) {
/*  200 */     if (length < 0) {
/*  201 */       throw new StringIndexOutOfBoundsException(length);
/*      */     }
/*  203 */     if (length < this.size) {
/*  204 */       this.size = length;
/*  205 */     } else if (length > this.size) {
/*  206 */       ensureCapacity(length);
/*  207 */       int oldEnd = this.size;
/*  208 */       int newEnd = length;
/*  209 */       this.size = length;
/*  210 */       for (int i = oldEnd; i < newEnd; i++) {
/*  211 */         this.buffer[i] = Character.MIN_VALUE;
/*      */       }
/*      */     } 
/*  214 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  224 */     return this.buffer.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder ensureCapacity(int capacity) {
/*  234 */     if (capacity > this.buffer.length) {
/*  235 */       char[] old = this.buffer;
/*  236 */       this.buffer = new char[capacity * 2];
/*  237 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  239 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder minimizeCapacity() {
/*  248 */     if (this.buffer.length > length()) {
/*  249 */       char[] old = this.buffer;
/*  250 */       this.buffer = new char[length()];
/*  251 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  253 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  266 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  278 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder clear() {
/*  293 */     this.size = 0;
/*  294 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char charAt(int index) {
/*  309 */     if (index < 0 || index >= length()) {
/*  310 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  312 */     return this.buffer[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setCharAt(int index, char ch) {
/*  326 */     if (index < 0 || index >= length()) {
/*  327 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  329 */     this.buffer[index] = ch;
/*  330 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteCharAt(int index) {
/*  343 */     if (index < 0 || index >= this.size) {
/*  344 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  346 */     deleteImpl(index, index + 1, 1);
/*  347 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray() {
/*  357 */     if (this.size == 0) {
/*  358 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  360 */     char[] chars = new char[this.size];
/*  361 */     System.arraycopy(this.buffer, 0, chars, 0, this.size);
/*  362 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray(int startIndex, int endIndex) {
/*  376 */     endIndex = validateRange(startIndex, endIndex);
/*  377 */     int len = endIndex - startIndex;
/*  378 */     if (len == 0) {
/*  379 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  381 */     char[] chars = new char[len];
/*  382 */     System.arraycopy(this.buffer, startIndex, chars, 0, len);
/*  383 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getChars(char[] destination) {
/*  393 */     int len = length();
/*  394 */     if (destination == null || destination.length < len) {
/*  395 */       destination = new char[len];
/*      */     }
/*  397 */     System.arraycopy(this.buffer, 0, destination, 0, len);
/*  398 */     return destination;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getChars(int startIndex, int endIndex, char[] destination, int destinationIndex) {
/*  412 */     if (startIndex < 0) {
/*  413 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/*  415 */     if (endIndex < 0 || endIndex > length()) {
/*  416 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/*  418 */     if (startIndex > endIndex) {
/*  419 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/*  421 */     System.arraycopy(this.buffer, startIndex, destination, destinationIndex, endIndex - startIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendNewLine() {
/*  435 */     if (this.newLine == null) {
/*  436 */       append(SystemUtils.LINE_SEPARATOR);
/*  437 */       return this;
/*      */     } 
/*  439 */     return append(this.newLine);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendNull() {
/*  448 */     if (this.nullText == null) {
/*  449 */       return this;
/*      */     }
/*  451 */     return append(this.nullText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(Object obj) {
/*  462 */     if (obj == null) {
/*  463 */       return appendNull();
/*      */     }
/*  465 */     return append(obj.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(CharSequence seq) {
/*  478 */     if (seq == null) {
/*  479 */       return appendNull();
/*      */     }
/*  481 */     return append(seq.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(CharSequence seq, int startIndex, int length) {
/*  496 */     if (seq == null) {
/*  497 */       return appendNull();
/*      */     }
/*  499 */     return append(seq.toString(), startIndex, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String str) {
/*  510 */     if (str == null) {
/*  511 */       return appendNull();
/*      */     }
/*  513 */     int strLen = str.length();
/*  514 */     if (strLen > 0) {
/*  515 */       int len = length();
/*  516 */       ensureCapacity(len + strLen);
/*  517 */       str.getChars(0, strLen, this.buffer, len);
/*  518 */       this.size += strLen;
/*      */     } 
/*  520 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String str, int startIndex, int length) {
/*  534 */     if (str == null) {
/*  535 */       return appendNull();
/*      */     }
/*  537 */     if (startIndex < 0 || startIndex > str.length()) {
/*  538 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  540 */     if (length < 0 || startIndex + length > str.length()) {
/*  541 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  543 */     if (length > 0) {
/*  544 */       int len = length();
/*  545 */       ensureCapacity(len + length);
/*  546 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  547 */       this.size += length;
/*      */     } 
/*  549 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String format, Object... objs) {
/*  562 */     return append(String.format(format, objs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuffer str) {
/*  573 */     if (str == null) {
/*  574 */       return appendNull();
/*      */     }
/*  576 */     int strLen = str.length();
/*  577 */     if (strLen > 0) {
/*  578 */       int len = length();
/*  579 */       ensureCapacity(len + strLen);
/*  580 */       str.getChars(0, strLen, this.buffer, len);
/*  581 */       this.size += strLen;
/*      */     } 
/*  583 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuffer str, int startIndex, int length) {
/*  596 */     if (str == null) {
/*  597 */       return appendNull();
/*      */     }
/*  599 */     if (startIndex < 0 || startIndex > str.length()) {
/*  600 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  602 */     if (length < 0 || startIndex + length > str.length()) {
/*  603 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  605 */     if (length > 0) {
/*  606 */       int len = length();
/*  607 */       ensureCapacity(len + length);
/*  608 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  609 */       this.size += length;
/*      */     } 
/*  611 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuilder str) {
/*  623 */     if (str == null) {
/*  624 */       return appendNull();
/*      */     }
/*  626 */     int strLen = str.length();
/*  627 */     if (strLen > 0) {
/*  628 */       int len = length();
/*  629 */       ensureCapacity(len + strLen);
/*  630 */       str.getChars(0, strLen, this.buffer, len);
/*  631 */       this.size += strLen;
/*      */     } 
/*  633 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuilder str, int startIndex, int length) {
/*  647 */     if (str == null) {
/*  648 */       return appendNull();
/*      */     }
/*  650 */     if (startIndex < 0 || startIndex > str.length()) {
/*  651 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  653 */     if (length < 0 || startIndex + length > str.length()) {
/*  654 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  656 */     if (length > 0) {
/*  657 */       int len = length();
/*  658 */       ensureCapacity(len + length);
/*  659 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  660 */       this.size += length;
/*      */     } 
/*  662 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StrBuilder str) {
/*  673 */     if (str == null) {
/*  674 */       return appendNull();
/*      */     }
/*  676 */     int strLen = str.length();
/*  677 */     if (strLen > 0) {
/*  678 */       int len = length();
/*  679 */       ensureCapacity(len + strLen);
/*  680 */       System.arraycopy(str.buffer, 0, this.buffer, len, strLen);
/*  681 */       this.size += strLen;
/*      */     } 
/*  683 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StrBuilder str, int startIndex, int length) {
/*  696 */     if (str == null) {
/*  697 */       return appendNull();
/*      */     }
/*  699 */     if (startIndex < 0 || startIndex > str.length()) {
/*  700 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  702 */     if (length < 0 || startIndex + length > str.length()) {
/*  703 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  705 */     if (length > 0) {
/*  706 */       int len = length();
/*  707 */       ensureCapacity(len + length);
/*  708 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  709 */       this.size += length;
/*      */     } 
/*  711 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char[] chars) {
/*  722 */     if (chars == null) {
/*  723 */       return appendNull();
/*      */     }
/*  725 */     int strLen = chars.length;
/*  726 */     if (strLen > 0) {
/*  727 */       int len = length();
/*  728 */       ensureCapacity(len + strLen);
/*  729 */       System.arraycopy(chars, 0, this.buffer, len, strLen);
/*  730 */       this.size += strLen;
/*      */     } 
/*  732 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char[] chars, int startIndex, int length) {
/*  745 */     if (chars == null) {
/*  746 */       return appendNull();
/*      */     }
/*  748 */     if (startIndex < 0 || startIndex > chars.length) {
/*  749 */       throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length);
/*      */     }
/*  751 */     if (length < 0 || startIndex + length > chars.length) {
/*  752 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/*  754 */     if (length > 0) {
/*  755 */       int len = length();
/*  756 */       ensureCapacity(len + length);
/*  757 */       System.arraycopy(chars, startIndex, this.buffer, len, length);
/*  758 */       this.size += length;
/*      */     } 
/*  760 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(boolean value) {
/*  770 */     if (value) {
/*  771 */       ensureCapacity(this.size + 4);
/*  772 */       this.buffer[this.size++] = 't';
/*  773 */       this.buffer[this.size++] = 'r';
/*  774 */       this.buffer[this.size++] = 'u';
/*  775 */       this.buffer[this.size++] = 'e';
/*      */     } else {
/*  777 */       ensureCapacity(this.size + 5);
/*  778 */       this.buffer[this.size++] = 'f';
/*  779 */       this.buffer[this.size++] = 'a';
/*  780 */       this.buffer[this.size++] = 'l';
/*  781 */       this.buffer[this.size++] = 's';
/*  782 */       this.buffer[this.size++] = 'e';
/*      */     } 
/*  784 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char ch) {
/*  796 */     int len = length();
/*  797 */     ensureCapacity(len + 1);
/*  798 */     this.buffer[this.size++] = ch;
/*  799 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(int value) {
/*  809 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(long value) {
/*  819 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(float value) {
/*  829 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(double value) {
/*  839 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(Object obj) {
/*  852 */     return append(obj).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String str) {
/*  864 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String str, int startIndex, int length) {
/*  878 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String format, Object... objs) {
/*  891 */     return append(format, objs).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuffer str) {
/*  903 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuilder str) {
/*  915 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuilder str, int startIndex, int length) {
/*  929 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuffer str, int startIndex, int length) {
/*  943 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StrBuilder str) {
/*  955 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StrBuilder str, int startIndex, int length) {
/*  969 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char[] chars) {
/*  981 */     return append(chars).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char[] chars, int startIndex, int length) {
/*  995 */     return append(chars, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(boolean value) {
/* 1006 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char ch) {
/* 1017 */     return append(ch).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(int value) {
/* 1028 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(long value) {
/* 1039 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(float value) {
/* 1050 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(double value) {
/* 1061 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> StrBuilder appendAll(T... array) {
/* 1076 */     if (array != null && array.length > 0) {
/* 1077 */       for (T element : array) {
/* 1078 */         append(element);
/*      */       }
/*      */     }
/* 1081 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendAll(Iterable<?> iterable) {
/* 1094 */     if (iterable != null) {
/* 1095 */       for (Object o : iterable) {
/* 1096 */         append(o);
/*      */       }
/*      */     }
/* 1099 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendAll(Iterator<?> it) {
/* 1112 */     if (it != null) {
/* 1113 */       while (it.hasNext()) {
/* 1114 */         append(it.next());
/*      */       }
/*      */     }
/* 1117 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Object[] array, String separator) {
/* 1132 */     if (array != null && array.length > 0) {
/*      */       
/* 1134 */       String sep = ObjectUtils.toString(separator);
/* 1135 */       append(array[0]);
/* 1136 */       for (int i = 1; i < array.length; i++) {
/* 1137 */         append(sep);
/* 1138 */         append(array[i]);
/*      */       } 
/*      */     } 
/* 1141 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Iterable<?> iterable, String separator) {
/* 1155 */     if (iterable != null) {
/*      */       
/* 1157 */       String sep = ObjectUtils.toString(separator);
/* 1158 */       Iterator<?> it = iterable.iterator();
/* 1159 */       while (it.hasNext()) {
/* 1160 */         append(it.next());
/* 1161 */         if (it.hasNext()) {
/* 1162 */           append(sep);
/*      */         }
/*      */       } 
/*      */     } 
/* 1166 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Iterator<?> it, String separator) {
/* 1180 */     if (it != null) {
/*      */       
/* 1182 */       String sep = ObjectUtils.toString(separator);
/* 1183 */       while (it.hasNext()) {
/* 1184 */         append(it.next());
/* 1185 */         if (it.hasNext()) {
/* 1186 */           append(sep);
/*      */         }
/*      */       } 
/*      */     } 
/* 1190 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String separator) {
/* 1215 */     return appendSeparator(separator, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String standard, String defaultIfEmpty) {
/* 1246 */     String str = isEmpty() ? defaultIfEmpty : standard;
/* 1247 */     if (str != null) {
/* 1248 */       append(str);
/*      */     }
/* 1250 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char separator) {
/* 1273 */     if (size() > 0) {
/* 1274 */       append(separator);
/*      */     }
/* 1276 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char standard, char defaultIfEmpty) {
/* 1291 */     if (size() > 0) {
/* 1292 */       append(standard);
/*      */     } else {
/* 1294 */       append(defaultIfEmpty);
/*      */     } 
/* 1296 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String separator, int loopIndex) {
/* 1321 */     if (separator != null && loopIndex > 0) {
/* 1322 */       append(separator);
/*      */     }
/* 1324 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char separator, int loopIndex) {
/* 1349 */     if (loopIndex > 0) {
/* 1350 */       append(separator);
/*      */     }
/* 1352 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendPadding(int length, char padChar) {
/* 1364 */     if (length >= 0) {
/* 1365 */       ensureCapacity(this.size + length);
/* 1366 */       for (int i = 0; i < length; i++) {
/* 1367 */         this.buffer[this.size++] = padChar;
/*      */       }
/*      */     } 
/* 1370 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadLeft(Object obj, int width, char padChar) {
/* 1386 */     if (width > 0) {
/* 1387 */       ensureCapacity(this.size + width);
/* 1388 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1389 */       if (str == null) {
/* 1390 */         str = "";
/*      */       }
/* 1392 */       int strLen = str.length();
/* 1393 */       if (strLen >= width) {
/* 1394 */         str.getChars(strLen - width, strLen, this.buffer, this.size);
/*      */       } else {
/* 1396 */         int padLen = width - strLen;
/* 1397 */         for (int i = 0; i < padLen; i++) {
/* 1398 */           this.buffer[this.size + i] = padChar;
/*      */         }
/* 1400 */         str.getChars(0, strLen, this.buffer, this.size + padLen);
/*      */       } 
/* 1402 */       this.size += width;
/*      */     } 
/* 1404 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadLeft(int value, int width, char padChar) {
/* 1418 */     return appendFixedWidthPadLeft(String.valueOf(value), width, padChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
/* 1433 */     if (width > 0) {
/* 1434 */       ensureCapacity(this.size + width);
/* 1435 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1436 */       if (str == null) {
/* 1437 */         str = "";
/*      */       }
/* 1439 */       int strLen = str.length();
/* 1440 */       if (strLen >= width) {
/* 1441 */         str.getChars(0, width, this.buffer, this.size);
/*      */       } else {
/* 1443 */         int padLen = width - strLen;
/* 1444 */         str.getChars(0, strLen, this.buffer, this.size);
/* 1445 */         for (int i = 0; i < padLen; i++) {
/* 1446 */           this.buffer[this.size + strLen + i] = padChar;
/*      */         }
/*      */       } 
/* 1449 */       this.size += width;
/*      */     } 
/* 1451 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadRight(int value, int width, char padChar) {
/* 1465 */     return appendFixedWidthPadRight(String.valueOf(value), width, padChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, Object obj) {
/* 1479 */     if (obj == null) {
/* 1480 */       return insert(index, this.nullText);
/*      */     }
/* 1482 */     return insert(index, obj.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, String str) {
/* 1495 */     validateIndex(index);
/* 1496 */     if (str == null) {
/* 1497 */       str = this.nullText;
/*      */     }
/* 1499 */     if (str != null) {
/* 1500 */       int strLen = str.length();
/* 1501 */       if (strLen > 0) {
/* 1502 */         int newSize = this.size + strLen;
/* 1503 */         ensureCapacity(newSize);
/* 1504 */         System.arraycopy(this.buffer, index, this.buffer, index + strLen, this.size - index);
/* 1505 */         this.size = newSize;
/* 1506 */         str.getChars(0, strLen, this.buffer, index);
/*      */       } 
/*      */     } 
/* 1509 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char[] chars) {
/* 1522 */     validateIndex(index);
/* 1523 */     if (chars == null) {
/* 1524 */       return insert(index, this.nullText);
/*      */     }
/* 1526 */     int len = chars.length;
/* 1527 */     if (len > 0) {
/* 1528 */       ensureCapacity(this.size + len);
/* 1529 */       System.arraycopy(this.buffer, index, this.buffer, index + len, this.size - index);
/* 1530 */       System.arraycopy(chars, 0, this.buffer, index, len);
/* 1531 */       this.size += len;
/*      */     } 
/* 1533 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char[] chars, int offset, int length) {
/* 1548 */     validateIndex(index);
/* 1549 */     if (chars == null) {
/* 1550 */       return insert(index, this.nullText);
/*      */     }
/* 1552 */     if (offset < 0 || offset > chars.length) {
/* 1553 */       throw new StringIndexOutOfBoundsException("Invalid offset: " + offset);
/*      */     }
/* 1555 */     if (length < 0 || offset + length > chars.length) {
/* 1556 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/* 1558 */     if (length > 0) {
/* 1559 */       ensureCapacity(this.size + length);
/* 1560 */       System.arraycopy(this.buffer, index, this.buffer, index + length, this.size - index);
/* 1561 */       System.arraycopy(chars, offset, this.buffer, index, length);
/* 1562 */       this.size += length;
/*      */     } 
/* 1564 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, boolean value) {
/* 1576 */     validateIndex(index);
/* 1577 */     if (value) {
/* 1578 */       ensureCapacity(this.size + 4);
/* 1579 */       System.arraycopy(this.buffer, index, this.buffer, index + 4, this.size - index);
/* 1580 */       this.buffer[index++] = 't';
/* 1581 */       this.buffer[index++] = 'r';
/* 1582 */       this.buffer[index++] = 'u';
/* 1583 */       this.buffer[index] = 'e';
/* 1584 */       this.size += 4;
/*      */     } else {
/* 1586 */       ensureCapacity(this.size + 5);
/* 1587 */       System.arraycopy(this.buffer, index, this.buffer, index + 5, this.size - index);
/* 1588 */       this.buffer[index++] = 'f';
/* 1589 */       this.buffer[index++] = 'a';
/* 1590 */       this.buffer[index++] = 'l';
/* 1591 */       this.buffer[index++] = 's';
/* 1592 */       this.buffer[index] = 'e';
/* 1593 */       this.size += 5;
/*      */     } 
/* 1595 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char value) {
/* 1607 */     validateIndex(index);
/* 1608 */     ensureCapacity(this.size + 1);
/* 1609 */     System.arraycopy(this.buffer, index, this.buffer, index + 1, this.size - index);
/* 1610 */     this.buffer[index] = value;
/* 1611 */     this.size++;
/* 1612 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, int value) {
/* 1624 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, long value) {
/* 1636 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, float value) {
/* 1648 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, double value) {
/* 1660 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteImpl(int startIndex, int endIndex, int len) {
/* 1673 */     System.arraycopy(this.buffer, endIndex, this.buffer, startIndex, this.size - endIndex);
/* 1674 */     this.size -= len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder delete(int startIndex, int endIndex) {
/* 1687 */     endIndex = validateRange(startIndex, endIndex);
/* 1688 */     int len = endIndex - startIndex;
/* 1689 */     if (len > 0) {
/* 1690 */       deleteImpl(startIndex, endIndex, len);
/*      */     }
/* 1692 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(char ch) {
/* 1703 */     for (int i = 0; i < this.size; i++) {
/* 1704 */       if (this.buffer[i] == ch) {
/* 1705 */         int start = i; do {  }
/* 1706 */         while (++i < this.size && 
/* 1707 */           this.buffer[i] == ch);
/*      */ 
/*      */ 
/*      */         
/* 1711 */         int len = i - start;
/* 1712 */         deleteImpl(start, i, len);
/* 1713 */         i -= len;
/*      */       } 
/*      */     } 
/* 1716 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(char ch) {
/* 1726 */     for (int i = 0; i < this.size; i++) {
/* 1727 */       if (this.buffer[i] == ch) {
/* 1728 */         deleteImpl(i, i + 1, 1);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1732 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(String str) {
/* 1743 */     int len = (str == null) ? 0 : str.length();
/* 1744 */     if (len > 0) {
/* 1745 */       int index = indexOf(str, 0);
/* 1746 */       while (index >= 0) {
/* 1747 */         deleteImpl(index, index + len, len);
/* 1748 */         index = indexOf(str, index);
/*      */       } 
/*      */     } 
/* 1751 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(String str) {
/* 1761 */     int len = (str == null) ? 0 : str.length();
/* 1762 */     if (len > 0) {
/* 1763 */       int index = indexOf(str, 0);
/* 1764 */       if (index >= 0) {
/* 1765 */         deleteImpl(index, index + len, len);
/*      */       }
/*      */     } 
/* 1768 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(StrMatcher matcher) {
/* 1783 */     return replace(matcher, null, 0, this.size, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(StrMatcher matcher) {
/* 1797 */     return replace(matcher, null, 0, this.size, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void replaceImpl(int startIndex, int endIndex, int removeLen, String insertStr, int insertLen) {
/* 1812 */     int newSize = this.size - removeLen + insertLen;
/* 1813 */     if (insertLen != removeLen) {
/* 1814 */       ensureCapacity(newSize);
/* 1815 */       System.arraycopy(this.buffer, endIndex, this.buffer, startIndex + insertLen, this.size - endIndex);
/* 1816 */       this.size = newSize;
/*      */     } 
/* 1818 */     if (insertLen > 0) {
/* 1819 */       insertStr.getChars(0, insertLen, this.buffer, startIndex);
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
/*      */   public StrBuilder replace(int startIndex, int endIndex, String replaceStr) {
/* 1835 */     endIndex = validateRange(startIndex, endIndex);
/* 1836 */     int insertLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1837 */     replaceImpl(startIndex, endIndex, endIndex - startIndex, replaceStr, insertLen);
/* 1838 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(char search, char replace) {
/* 1851 */     if (search != replace) {
/* 1852 */       for (int i = 0; i < this.size; i++) {
/* 1853 */         if (this.buffer[i] == search) {
/* 1854 */           this.buffer[i] = replace;
/*      */         }
/*      */       } 
/*      */     }
/* 1858 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(char search, char replace) {
/* 1870 */     if (search != replace) {
/* 1871 */       for (int i = 0; i < this.size; i++) {
/* 1872 */         if (this.buffer[i] == search) {
/* 1873 */           this.buffer[i] = replace;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1878 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(String searchStr, String replaceStr) {
/* 1890 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 1891 */     if (searchLen > 0) {
/* 1892 */       int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1893 */       int index = indexOf(searchStr, 0);
/* 1894 */       while (index >= 0) {
/* 1895 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/* 1896 */         index = indexOf(searchStr, index + replaceLen);
/*      */       } 
/*      */     } 
/* 1899 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(String searchStr, String replaceStr) {
/* 1910 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 1911 */     if (searchLen > 0) {
/* 1912 */       int index = indexOf(searchStr, 0);
/* 1913 */       if (index >= 0) {
/* 1914 */         int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1915 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/*      */       } 
/*      */     } 
/* 1918 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(StrMatcher matcher, String replaceStr) {
/* 1934 */     return replace(matcher, replaceStr, 0, this.size, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(StrMatcher matcher, String replaceStr) {
/* 1949 */     return replace(matcher, replaceStr, 0, this.size, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replace(StrMatcher matcher, String replaceStr, int startIndex, int endIndex, int replaceCount) {
/* 1972 */     endIndex = validateRange(startIndex, endIndex);
/* 1973 */     return replaceImpl(matcher, replaceStr, startIndex, endIndex, replaceCount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrBuilder replaceImpl(StrMatcher matcher, String replaceStr, int from, int to, int replaceCount) {
/* 1994 */     if (matcher == null || this.size == 0) {
/* 1995 */       return this;
/*      */     }
/* 1997 */     int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1998 */     char[] buf = this.buffer;
/* 1999 */     for (int i = from; i < to && replaceCount != 0; i++) {
/* 2000 */       int removeLen = matcher.isMatch(buf, i, from, to);
/* 2001 */       if (removeLen > 0) {
/* 2002 */         replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
/* 2003 */         to = to - removeLen + replaceLen;
/* 2004 */         i = i + replaceLen - 1;
/* 2005 */         if (replaceCount > 0) {
/* 2006 */           replaceCount--;
/*      */         }
/*      */       } 
/*      */     } 
/* 2010 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder reverse() {
/* 2020 */     if (this.size == 0) {
/* 2021 */       return this;
/*      */     }
/*      */     
/* 2024 */     int half = this.size / 2;
/* 2025 */     char[] buf = this.buffer;
/* 2026 */     for (int leftIdx = 0, rightIdx = this.size - 1; leftIdx < half; leftIdx++, rightIdx--) {
/* 2027 */       char swap = buf[leftIdx];
/* 2028 */       buf[leftIdx] = buf[rightIdx];
/* 2029 */       buf[rightIdx] = swap;
/*      */     } 
/* 2031 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder trim() {
/* 2042 */     if (this.size == 0) {
/* 2043 */       return this;
/*      */     }
/* 2045 */     int len = this.size;
/* 2046 */     char[] buf = this.buffer;
/* 2047 */     int pos = 0;
/* 2048 */     while (pos < len && buf[pos] <= ' ') {
/* 2049 */       pos++;
/*      */     }
/* 2051 */     while (pos < len && buf[len - 1] <= ' ') {
/* 2052 */       len--;
/*      */     }
/* 2054 */     if (len < this.size) {
/* 2055 */       delete(len, this.size);
/*      */     }
/* 2057 */     if (pos > 0) {
/* 2058 */       delete(0, pos);
/*      */     }
/* 2060 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(String str) {
/* 2073 */     if (str == null) {
/* 2074 */       return false;
/*      */     }
/* 2076 */     int len = str.length();
/* 2077 */     if (len == 0) {
/* 2078 */       return true;
/*      */     }
/* 2080 */     if (len > this.size) {
/* 2081 */       return false;
/*      */     }
/* 2083 */     for (int i = 0; i < len; i++) {
/* 2084 */       if (this.buffer[i] != str.charAt(i)) {
/* 2085 */         return false;
/*      */       }
/*      */     } 
/* 2088 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean endsWith(String str) {
/* 2100 */     if (str == null) {
/* 2101 */       return false;
/*      */     }
/* 2103 */     int len = str.length();
/* 2104 */     if (len == 0) {
/* 2105 */       return true;
/*      */     }
/* 2107 */     if (len > this.size) {
/* 2108 */       return false;
/*      */     }
/* 2110 */     int pos = this.size - len;
/* 2111 */     for (int i = 0; i < len; i++, pos++) {
/* 2112 */       if (this.buffer[pos] != str.charAt(i)) {
/* 2113 */         return false;
/*      */       }
/*      */     } 
/* 2116 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSequence subSequence(int startIndex, int endIndex) {
/* 2126 */     if (startIndex < 0) {
/* 2127 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2129 */     if (endIndex > this.size) {
/* 2130 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/* 2132 */     if (startIndex > endIndex) {
/* 2133 */       throw new StringIndexOutOfBoundsException(endIndex - startIndex);
/*      */     }
/* 2135 */     return substring(startIndex, endIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int start) {
/* 2146 */     return substring(start, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int startIndex, int endIndex) {
/* 2163 */     endIndex = validateRange(startIndex, endIndex);
/* 2164 */     return new String(this.buffer, startIndex, endIndex - startIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String leftString(int length) {
/* 2180 */     if (length <= 0)
/* 2181 */       return ""; 
/* 2182 */     if (length >= this.size) {
/* 2183 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2185 */     return new String(this.buffer, 0, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String rightString(int length) {
/* 2202 */     if (length <= 0)
/* 2203 */       return ""; 
/* 2204 */     if (length >= this.size) {
/* 2205 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2207 */     return new String(this.buffer, this.size - length, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String midString(int index, int length) {
/* 2228 */     if (index < 0) {
/* 2229 */       index = 0;
/*      */     }
/* 2231 */     if (length <= 0 || index >= this.size) {
/* 2232 */       return "";
/*      */     }
/* 2234 */     if (this.size <= index + length) {
/* 2235 */       return new String(this.buffer, index, this.size - index);
/*      */     }
/* 2237 */     return new String(this.buffer, index, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(char ch) {
/* 2248 */     char[] thisBuf = this.buffer;
/* 2249 */     for (int i = 0; i < this.size; i++) {
/* 2250 */       if (thisBuf[i] == ch) {
/* 2251 */         return true;
/*      */       }
/*      */     } 
/* 2254 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(String str) {
/* 2264 */     return (indexOf(str, 0) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(StrMatcher matcher) {
/* 2279 */     return (indexOf(matcher, 0) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch) {
/* 2290 */     return indexOf(ch, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch, int startIndex) {
/* 2301 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2302 */     if (startIndex >= this.size) {
/* 2303 */       return -1;
/*      */     }
/* 2305 */     char[] thisBuf = this.buffer;
/* 2306 */     for (int i = startIndex; i < this.size; i++) {
/* 2307 */       if (thisBuf[i] == ch) {
/* 2308 */         return i;
/*      */       }
/*      */     } 
/* 2311 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String str) {
/* 2323 */     return indexOf(str, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String str, int startIndex) {
/* 2337 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2338 */     if (str == null || startIndex >= this.size) {
/* 2339 */       return -1;
/*      */     }
/* 2341 */     int strLen = str.length();
/* 2342 */     if (strLen == 1) {
/* 2343 */       return indexOf(str.charAt(0), startIndex);
/*      */     }
/* 2345 */     if (strLen == 0) {
/* 2346 */       return startIndex;
/*      */     }
/* 2348 */     if (strLen > this.size) {
/* 2349 */       return -1;
/*      */     }
/* 2351 */     char[] thisBuf = this.buffer;
/* 2352 */     int len = this.size - strLen + 1;
/*      */     
/* 2354 */     for (int i = startIndex; i < len; i++) {
/* 2355 */       int j = 0; while (true) { if (j < strLen) {
/* 2356 */           if (str.charAt(j) != thisBuf[i + j])
/*      */             break;  j++;
/*      */           continue;
/*      */         } 
/* 2360 */         return i; }
/*      */     
/* 2362 */     }  return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(StrMatcher matcher) {
/* 2376 */     return indexOf(matcher, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(StrMatcher matcher, int startIndex) {
/* 2392 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2393 */     if (matcher == null || startIndex >= this.size) {
/* 2394 */       return -1;
/*      */     }
/* 2396 */     int len = this.size;
/* 2397 */     char[] buf = this.buffer;
/* 2398 */     for (int i = startIndex; i < len; i++) {
/* 2399 */       if (matcher.isMatch(buf, i, startIndex, len) > 0) {
/* 2400 */         return i;
/*      */       }
/*      */     } 
/* 2403 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch) {
/* 2414 */     return lastIndexOf(ch, this.size - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch, int startIndex) {
/* 2425 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2426 */     if (startIndex < 0) {
/* 2427 */       return -1;
/*      */     }
/* 2429 */     for (int i = startIndex; i >= 0; i--) {
/* 2430 */       if (this.buffer[i] == ch) {
/* 2431 */         return i;
/*      */       }
/*      */     } 
/* 2434 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String str) {
/* 2446 */     return lastIndexOf(str, this.size - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String str, int startIndex) {
/* 2460 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2461 */     if (str == null || startIndex < 0) {
/* 2462 */       return -1;
/*      */     }
/* 2464 */     int strLen = str.length();
/* 2465 */     if (strLen > 0 && strLen <= this.size) {
/* 2466 */       if (strLen == 1) {
/* 2467 */         return lastIndexOf(str.charAt(0), startIndex);
/*      */       }
/*      */ 
/*      */       
/* 2471 */       for (int i = startIndex - strLen + 1; i >= 0; i--) {
/* 2472 */         int j = 0; while (true) { if (j < strLen) {
/* 2473 */             if (str.charAt(j) != this.buffer[i + j])
/*      */               break;  j++;
/*      */             continue;
/*      */           } 
/* 2477 */           return i; }
/*      */       
/*      */       } 
/* 2480 */     } else if (strLen == 0) {
/* 2481 */       return startIndex;
/*      */     } 
/* 2483 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(StrMatcher matcher) {
/* 2497 */     return lastIndexOf(matcher, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(StrMatcher matcher, int startIndex) {
/* 2513 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2514 */     if (matcher == null || startIndex < 0) {
/* 2515 */       return -1;
/*      */     }
/* 2517 */     char[] buf = this.buffer;
/* 2518 */     int endIndex = startIndex + 1;
/* 2519 */     for (int i = startIndex; i >= 0; i--) {
/* 2520 */       if (matcher.isMatch(buf, i, 0, endIndex) > 0) {
/* 2521 */         return i;
/*      */       }
/*      */     } 
/* 2524 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrTokenizer asTokenizer() {
/* 2561 */     return new StrBuilderTokenizer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader asReader() {
/* 2585 */     return new StrBuilderReader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer asWriter() {
/* 2610 */     return new StrBuilderWriter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equalsIgnoreCase(StrBuilder other) {
/* 2652 */     if (this == other) {
/* 2653 */       return true;
/*      */     }
/* 2655 */     if (this.size != other.size) {
/* 2656 */       return false;
/*      */     }
/* 2658 */     char[] thisBuf = this.buffer;
/* 2659 */     char[] otherBuf = other.buffer;
/* 2660 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2661 */       char c1 = thisBuf[i];
/* 2662 */       char c2 = otherBuf[i];
/* 2663 */       if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
/* 2664 */         return false;
/*      */       }
/*      */     } 
/* 2667 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(StrBuilder other) {
/* 2678 */     if (this == other) {
/* 2679 */       return true;
/*      */     }
/* 2681 */     if (this.size != other.size) {
/* 2682 */       return false;
/*      */     }
/* 2684 */     char[] thisBuf = this.buffer;
/* 2685 */     char[] otherBuf = other.buffer;
/* 2686 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2687 */       if (thisBuf[i] != otherBuf[i]) {
/* 2688 */         return false;
/*      */       }
/*      */     } 
/* 2691 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 2703 */     if (obj instanceof StrBuilder) {
/* 2704 */       return equals((StrBuilder)obj);
/*      */     }
/* 2706 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2716 */     char[] buf = this.buffer;
/* 2717 */     int hash = 0;
/* 2718 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2719 */       hash = 31 * hash + buf[i];
/*      */     }
/* 2721 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2736 */     return new String(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer toStringBuffer() {
/* 2746 */     return (new StringBuffer(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuilder toStringBuilder() {
/* 2757 */     return (new StringBuilder(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String build() {
/* 2768 */     return toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int validateRange(int startIndex, int endIndex) {
/* 2782 */     if (startIndex < 0) {
/* 2783 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2785 */     if (endIndex > this.size) {
/* 2786 */       endIndex = this.size;
/*      */     }
/* 2788 */     if (startIndex > endIndex) {
/* 2789 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/* 2791 */     return endIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateIndex(int index) {
/* 2801 */     if (index < 0 || index > this.size) {
/* 2802 */       throw new StringIndexOutOfBoundsException(index);
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
/*      */   class StrBuilderTokenizer
/*      */     extends StrTokenizer
/*      */   {
/*      */     protected List<String> tokenize(char[] chars, int offset, int count) {
/* 2822 */       if (chars == null) {
/* 2823 */         return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
/*      */       }
/* 2825 */       return super.tokenize(chars, offset, count);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getContent() {
/* 2831 */       String str = super.getContent();
/* 2832 */       if (str == null) {
/* 2833 */         return StrBuilder.this.toString();
/*      */       }
/* 2835 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class StrBuilderReader
/*      */     extends Reader
/*      */   {
/*      */     private int pos;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int mark;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read() {
/* 2865 */       if (!ready()) {
/* 2866 */         return -1;
/*      */       }
/* 2868 */       return StrBuilder.this.charAt(this.pos++);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(char[] b, int off, int len) {
/* 2874 */       if (off < 0 || len < 0 || off > b.length || off + len > b.length || off + len < 0)
/*      */       {
/* 2876 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2878 */       if (len == 0) {
/* 2879 */         return 0;
/*      */       }
/* 2881 */       if (this.pos >= StrBuilder.this.size()) {
/* 2882 */         return -1;
/*      */       }
/* 2884 */       if (this.pos + len > StrBuilder.this.size()) {
/* 2885 */         len = StrBuilder.this.size() - this.pos;
/*      */       }
/* 2887 */       StrBuilder.this.getChars(this.pos, this.pos + len, b, off);
/* 2888 */       this.pos += len;
/* 2889 */       return len;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 2895 */       if (this.pos + n > StrBuilder.this.size()) {
/* 2896 */         n = (StrBuilder.this.size() - this.pos);
/*      */       }
/* 2898 */       if (n < 0L) {
/* 2899 */         return 0L;
/*      */       }
/* 2901 */       this.pos = (int)(this.pos + n);
/* 2902 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean ready() {
/* 2908 */       return (this.pos < StrBuilder.this.size());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean markSupported() {
/* 2914 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mark(int readAheadLimit) {
/* 2920 */       this.mark = this.pos;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void reset() {
/* 2926 */       this.pos = this.mark;
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
/*      */   class StrBuilderWriter
/*      */     extends Writer
/*      */   {
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int c) {
/* 2958 */       StrBuilder.this.append((char)c);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf) {
/* 2964 */       StrBuilder.this.append(cbuf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf, int off, int len) {
/* 2970 */       StrBuilder.this.append(cbuf, off, len);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str) {
/* 2976 */       StrBuilder.this.append(str);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str, int off, int len) {
/* 2982 */       StrBuilder.this.append(str, off, len);
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\text\StrBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */