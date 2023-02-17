/*    */ package net.minecraft.launcher.game;
/*    */ 
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.mojang.launcher.versions.ReleaseType;
/*    */ import com.mojang.launcher.versions.ReleaseTypeFactory;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class MinecraftReleaseTypeFactory implements ReleaseTypeFactory<MinecraftReleaseType> {
/*  9 */   private static final MinecraftReleaseTypeFactory FACTORY = new MinecraftReleaseTypeFactory();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MinecraftReleaseType getTypeByName(String name) {
/* 16 */     return MinecraftReleaseType.getByName(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public MinecraftReleaseType[] getAllTypes() {
/* 21 */     return MinecraftReleaseType.values();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<MinecraftReleaseType> getTypeClass() {
/* 26 */     return MinecraftReleaseType.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<MinecraftReleaseType> iterator() {
/* 31 */     return (Iterator<MinecraftReleaseType>)Iterators.forArray((Object[])MinecraftReleaseType.values());
/*    */   }
/*    */   
/*    */   public static MinecraftReleaseTypeFactory instance() {
/* 35 */     return FACTORY;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launcher\game\MinecraftReleaseTypeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */