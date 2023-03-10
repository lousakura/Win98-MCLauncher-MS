/*    */ package com.mojang.authlib.legacy;
/*    */ 
/*    */ import com.mojang.authlib.Agent;
/*    */ import com.mojang.authlib.GameProfileRepository;
/*    */ import com.mojang.authlib.HttpAuthenticationService;
/*    */ import com.mojang.authlib.UserAuthentication;
/*    */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*    */ import java.net.Proxy;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LegacyAuthenticationService
/*    */   extends HttpAuthenticationService
/*    */ {
/*    */   protected LegacyAuthenticationService(Proxy proxy) {
/* 20 */     super(proxy);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public LegacyUserAuthentication createUserAuthentication(Agent agent) {
/* 34 */     Validate.notNull(agent);
/* 35 */     if (agent != Agent.MINECRAFT) throw new IllegalArgumentException("Legacy authentication cannot handle anything but Minecraft"); 
/* 36 */     return new LegacyUserAuthentication(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public LegacyMinecraftSessionService createMinecraftSessionService() {
/* 41 */     return new LegacyMinecraftSessionService(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public GameProfileRepository createProfileRepository() {
/* 46 */     throw new UnsupportedOperationException("Legacy authentication service has no profile repository");
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\com\mojang\authlib\legacy\LegacyAuthenticationService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */