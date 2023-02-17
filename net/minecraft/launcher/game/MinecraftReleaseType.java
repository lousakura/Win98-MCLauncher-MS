/*    */ package net.minecraft.launcher.game;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import com.mojang.launcher.versions.ReleaseType;
/*    */ import java.util.Map;
/*    */ 
/*    */ public enum MinecraftReleaseType
/*    */   implements ReleaseType {
/*  9 */   SNAPSHOT("snapshot", "Enable experimental development versions (\"snapshots\")"),
/* 10 */   RELEASE("release", null),
/* 11 */   OLD_BETA("old_beta", "Allow use of old \"Beta\" Minecraft versions (From 2010-2011)"),
/* 12 */   OLD_ALPHA("old_alpha", "Allow use of old \"Alpha\" Minecraft versions (From 2010)");
/*    */   
/*    */   private static final String POPUP_DEV_VERSIONS = "Are you sure you want to enable development builds?\nThey are not guaranteed to be stable and may corrupt your world.\nYou are advised to run this in a separate directory or run regular backups.";
/*    */   private static final String POPUP_OLD_VERSIONS = "These versions are very out of date and may be unstable. Any bugs, crashes, missing features or\nother nasties you may find will never be fixed in these versions.\nIt is strongly recommended you play these in separate directories to avoid corruption.\nWe are not responsible for the damage to your nostalgia or your save files!";
/*    */   private static final Map<String, MinecraftReleaseType> LOOKUP;
/*    */   private final String name;
/*    */   private final String description;
/*    */   
/*    */   static {
/* 21 */     LOOKUP = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 27 */     for (MinecraftReleaseType type : values()) {
/* 28 */       LOOKUP.put(type.getName(), type);
/*    */     }
/*    */   }
/*    */   
/*    */   MinecraftReleaseType(String name, String description) {
/* 33 */     this.name = name;
/* 34 */     this.description = description;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 38 */     return this.name;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 42 */     return this.description;
/*    */   }
/*    */   
/*    */   public String getPopupWarning() {
/* 46 */     if (this.description == null) return null; 
/* 47 */     if (this == SNAPSHOT) return "Are you sure you want to enable development builds?\nThey are not guaranteed to be stable and may corrupt your world.\nYou are advised to run this in a separate directory or run regular backups."; 
/* 48 */     if (this == OLD_BETA) return "These versions are very out of date and may be unstable. Any bugs, crashes, missing features or\nother nasties you may find will never be fixed in these versions.\nIt is strongly recommended you play these in separate directories to avoid corruption.\nWe are not responsible for the damage to your nostalgia or your save files!"; 
/* 49 */     if (this == OLD_ALPHA) return "These versions are very out of date and may be unstable. Any bugs, crashes, missing features or\nother nasties you may find will never be fixed in these versions.\nIt is strongly recommended you play these in separate directories to avoid corruption.\nWe are not responsible for the damage to your nostalgia or your save files!"; 
/* 50 */     return null;
/*    */   }
/*    */   
/*    */   public static MinecraftReleaseType getByName(String name) {
/* 54 */     return LOOKUP.get(name);
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\game\MinecraftReleaseType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */