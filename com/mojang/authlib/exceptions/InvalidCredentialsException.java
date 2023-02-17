/*    */ package com.mojang.authlib.exceptions;
/*    */ 
/*    */ public class InvalidCredentialsException
/*    */   extends AuthenticationException {
/*    */   public InvalidCredentialsException() {}
/*    */   
/*    */   public InvalidCredentialsException(String message) {
/*  8 */     super(message);
/*    */   }
/*    */   
/*    */   public InvalidCredentialsException(String message, Throwable cause) {
/* 12 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public InvalidCredentialsException(Throwable cause) {
/* 16 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\exceptions\InvalidCredentialsException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */