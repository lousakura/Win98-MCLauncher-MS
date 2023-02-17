/*    */ package net.minecraft.launcher.ui.tabs;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import javax.swing.JTabbedPane;
/*    */ import net.minecraft.launcher.Launcher;
/*    */ 
/*    */ public class LauncherTabPanel
/*    */   extends JTabbedPane {
/*    */   private final Launcher minecraftLauncher;
/*    */   private final WebsiteTab blog;
/*    */   private final ConsoleTab console;
/*    */   private CrashReportTab crashReportTab;
/*    */   
/*    */   public LauncherTabPanel(Launcher minecraftLauncher) {
/* 15 */     super(1);
/*    */     
/* 17 */     this.minecraftLauncher = minecraftLauncher;
/* 18 */     this.blog = new WebsiteTab(minecraftLauncher);
/* 19 */     this.console = new ConsoleTab(minecraftLauncher);
/*    */     
/* 21 */     createInterface();
/*    */   }
/*    */   
/*    */   protected void createInterface() {
/* 25 */     addTab("Update Notes", this.blog);
/* 26 */     addTab("Launcher Log", this.console);
/* 27 */     addTab("Profile Editor", new ProfileListTab(this.minecraftLauncher));
/*    */   }
/*    */ 
/*    */   
/*    */   public Launcher getMinecraftLauncher() {
/* 32 */     return this.minecraftLauncher;
/*    */   }
/*    */   
/*    */   public WebsiteTab getBlog() {
/* 36 */     return this.blog;
/*    */   }
/*    */   
/*    */   public ConsoleTab getConsole() {
/* 40 */     return this.console;
/*    */   }
/*    */   
/*    */   public void showConsole() {
/* 44 */     setSelectedComponent(this.console);
/*    */   }
/*    */   
/*    */   public void setCrashReport(CrashReportTab newTab) {
/* 48 */     if (this.crashReportTab != null) removeTab(this.crashReportTab); 
/* 49 */     this.crashReportTab = newTab;
/* 50 */     addTab("Crash Report", this.crashReportTab);
/* 51 */     setSelectedComponent(newTab);
/*    */   }
/*    */   
/*    */   protected void removeTab(Component tab) {
/* 55 */     for (int i = 0; i < getTabCount(); i++) {
/* 56 */       if (getTabComponentAt(i) == tab) {
/* 57 */         removeTabAt(i);
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void removeTab(String name) {
/* 64 */     int index = indexOfTab(name);
/* 65 */     if (index > -1)
/* 66 */       removeTabAt(index); 
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\LauncherTabPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */