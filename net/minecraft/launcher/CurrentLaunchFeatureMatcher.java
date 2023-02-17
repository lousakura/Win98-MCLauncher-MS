/*    */ package net.minecraft.launcher;
/*    */ 
/*    */ import com.google.common.base.Objects;
/*    */ import com.mojang.authlib.UserAuthentication;
/*    */ import net.minecraft.launcher.profile.Profile;
/*    */ import net.minecraft.launcher.updater.CompleteMinecraftVersion;
/*    */ 
/*    */ public class CurrentLaunchFeatureMatcher implements CompatibilityRule.FeatureMatcher {
/*    */   private final Profile profile;
/*    */   private final CompleteMinecraftVersion version;
/*    */   private final UserAuthentication auth;
/*    */   
/*    */   public CurrentLaunchFeatureMatcher(Profile profile, CompleteMinecraftVersion version, UserAuthentication auth) {
/* 14 */     this.profile = profile;
/* 15 */     this.version = version;
/* 16 */     this.auth = auth;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasFeature(String name, Object value) {
/* 21 */     if (name.equals("is_demo_user")) {
/* 22 */       return Objects.equal(Boolean.valueOf((this.auth.getSelectedProfile() == null)), value);
/*    */     }
/*    */     
/* 25 */     if (name.equals("has_custom_resolution")) {
/* 26 */       return Objects.equal(Boolean.valueOf((this.profile.getResolution() != null)), value);
/*    */     }
/*    */     
/* 29 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\CurrentLaunchFeatureMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */