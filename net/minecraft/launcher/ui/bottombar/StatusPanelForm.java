/*    */ package net.minecraft.launcher.ui.bottombar;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.mojang.launcher.Http;
/*    */ import com.mojang.launcher.updater.LowerCaseEnumTypeAdapterFactory;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.net.URL;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import javax.swing.JLabel;
/*    */ import net.minecraft.launcher.Launcher;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class StatusPanelForm
/*    */   extends SidebarGridForm {
/* 20 */   private static final Logger LOGGER = LogManager.getLogger();
/*    */   
/*    */   private static final String SERVER_SESSION = "session.minecraft.net";
/*    */   private static final String SERVER_LOGIN = "login.minecraft.net";
/*    */   private final Launcher minecraftLauncher;
/* 25 */   private final JLabel sessionStatus = new JLabel("???");
/* 26 */   private final JLabel loginStatus = new JLabel("???");
/* 27 */   private final Gson gson = (new GsonBuilder()).registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory()).create();
/*    */ 
/*    */   
/*    */   public StatusPanelForm(Launcher minecraftLauncher) {
/* 31 */     this.minecraftLauncher = minecraftLauncher;
/*    */     
/* 33 */     createInterface();
/* 34 */     refreshStatuses();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void populateGrid(GridBagConstraints constraints) {
/* 39 */     add(new JLabel("Multiplayer:", 2), constraints, 0, 0, 0, 1, 17);
/* 40 */     add(this.sessionStatus, constraints, 1, 0, 1, 1);
/*    */     
/* 42 */     add(new JLabel("Login:", 2), constraints, 0, 1, 0, 1, 17);
/* 43 */     add(this.loginStatus, constraints, 1, 1, 1, 1);
/*    */   }
/*    */   
/*    */   public JLabel getSessionStatus() {
/* 47 */     return this.sessionStatus;
/*    */   }
/*    */   
/*    */   public JLabel getLoginStatus() {
/* 51 */     return this.loginStatus;
/*    */   }
/*    */   
/*    */   public void refreshStatuses() {
/* 55 */     this.minecraftLauncher.getLauncher().getVersionManager().getExecutorService().submit(new Runnable()
/*    */         {
/*    */           public void run() {
/*    */             try {
/* 59 */               TypeToken<List<Map<String, StatusPanelForm.ServerStatus>>> token = new TypeToken<List<Map<String, StatusPanelForm.ServerStatus>>>() {  };
/* 60 */               List<Map<String, StatusPanelForm.ServerStatus>> statuses = (List<Map<String, StatusPanelForm.ServerStatus>>)StatusPanelForm.this.gson.fromJson(Http.performGet(new URL("http://status.mojang.com/check"), StatusPanelForm.this.minecraftLauncher.getLauncher().getProxy()), token.getType());
/*    */               
/* 62 */               for (Map<String, StatusPanelForm.ServerStatus> serverStatusInformation : statuses) {
/* 63 */                 if (serverStatusInformation.containsKey("login.minecraft.net")) {
/* 64 */                   StatusPanelForm.this.loginStatus.setText((serverStatusInformation.get("login.minecraft.net")).title); continue;
/* 65 */                 }  if (serverStatusInformation.containsKey("session.minecraft.net")) {
/* 66 */                   StatusPanelForm.this.sessionStatus.setText((serverStatusInformation.get("session.minecraft.net")).title);
/*    */                 }
/*    */               } 
/* 69 */             } catch (Exception e) {
/* 70 */               StatusPanelForm.LOGGER.error("Couldn't get server status", e);
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public enum ServerStatus {
/* 77 */     GREEN("Online, no problems detected."),
/* 78 */     YELLOW("May be experiencing issues."),
/* 79 */     RED("Offline, experiencing problems.");
/*    */     
/*    */     private final String title;
/*    */     
/*    */     ServerStatus(String title) {
/* 84 */       this.title = title;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\bottombar\StatusPanelForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */