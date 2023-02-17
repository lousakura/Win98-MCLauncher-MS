/*    */ package com.mojang.authlib.yggdrasil.response;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ 
/*    */ public class AuthenticationResponse extends Response {
/*    */   private String accessToken;
/*    */   private String clientToken;
/*    */   private GameProfile selectedProfile;
/*    */   private GameProfile[] availableProfiles;
/*    */   private User user;
/*    */   
/*    */   public String getAccessToken() {
/* 13 */     return this.accessToken;
/*    */   }
/*    */   
/*    */   public String getClientToken() {
/* 17 */     return this.clientToken;
/*    */   }
/*    */   
/*    */   public GameProfile[] getAvailableProfiles() {
/* 21 */     return this.availableProfiles;
/*    */   }
/*    */   
/*    */   public GameProfile getSelectedProfile() {
/* 25 */     return this.selectedProfile;
/*    */   }
/*    */   
/*    */   public User getUser() {
/* 29 */     return this.user;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\yggdrasil\response\AuthenticationResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */