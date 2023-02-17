/*    */ package com.mojang.authlib.yggdrasil.request;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
/*    */ 
/*    */ public class AuthenticationRequest {
/*    */   private Agent agent;
/*    */   private String username;
/*    */   private String password;
/*    */   private String clientToken;
/*    */   private boolean requestUser = true;
/*    */   
/*    */   public AuthenticationRequest(YggdrasilUserAuthentication authenticationService, String username, String password) {
/* 14 */     this.agent = authenticationService.getAgent();
/* 15 */     this.username = username;
/* 16 */     this.clientToken = authenticationService.getAuthenticationService().getClientToken();
/* 17 */     this.password = password;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\yggdrasil\request\AuthenticationRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */