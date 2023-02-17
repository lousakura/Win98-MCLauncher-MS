/*    */ package com.mojang.authlib.minecraft;
/*    */ import com.mojang.authlib.AuthenticationService;
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ 
/*    */ public abstract class HttpMinecraftSessionService extends BaseMinecraftSessionService {
/*    */   protected HttpMinecraftSessionService(HttpAuthenticationService authenticationService) {
/*  7 */     super((AuthenticationService)authenticationService);
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpAuthenticationService getAuthenticationService() {
/* 12 */     return (HttpAuthenticationService)super.getAuthenticationService();
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\minecraft\HttpMinecraftSessionService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */