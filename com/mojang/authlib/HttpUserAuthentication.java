/*    */ package com.mojang.authlib;
/*    */ 
/*    */ public abstract class HttpUserAuthentication extends BaseUserAuthentication {
/*    */   protected HttpUserAuthentication(HttpAuthenticationService authenticationService) {
/*  5 */     super(authenticationService);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpAuthenticationService getAuthenticationService() {
/* 10 */     return (HttpAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\HttpUserAuthentication.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */