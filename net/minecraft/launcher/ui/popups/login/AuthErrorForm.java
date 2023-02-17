/*    */ package net.minecraft.launcher.ui.popups.login;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import com.google.gson.GsonBuilder;
/*    */ import com.google.gson.TypeAdapterFactory;
/*    */ import com.google.gson.reflect.TypeToken;
/*    */ import com.mojang.launcher.Http;
/*    */ import com.mojang.launcher.updater.LowerCaseEnumTypeAdapterFactory;
/*    */ import java.net.URL;
/*    */ import java.util.Map;
/*    */ import javax.swing.JLabel;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.SwingUtilities;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import org.apache.commons.lang3.exception.ExceptionUtils;
/*    */ 
/*    */ public class AuthErrorForm extends JPanel {
/* 18 */   private final JLabel errorLabel = new JLabel(); private final LogInPopup popup;
/* 19 */   private final Gson gson = (new GsonBuilder()).registerTypeAdapterFactory((TypeAdapterFactory)new LowerCaseEnumTypeAdapterFactory()).create();
/*    */ 
/*    */   
/*    */   public AuthErrorForm(LogInPopup popup) {
/* 23 */     this.popup = popup;
/*    */     
/* 25 */     createInterface();
/* 26 */     clear();
/*    */   }
/*    */   
/*    */   protected void createInterface() {
/* 30 */     setBorder(new EmptyBorder(0, 0, 15, 0));
/* 31 */     this.errorLabel.setFont(this.errorLabel.getFont().deriveFont(1));
/* 32 */     add(this.errorLabel);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 36 */     setVisible(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setVisible(boolean value) {
/* 41 */     super.setVisible(value);
/* 42 */     this.popup.repack();
/*    */   }
/*    */   
/*    */   public void displayError(final Throwable throwable, String... lines) {
/* 46 */     if (SwingUtilities.isEventDispatchThread()) {
/* 47 */       String error = "";
/* 48 */       for (String line : lines) {
/* 49 */         error = error + "<p>" + line + "</p>";
/*    */       }
/* 51 */       if (throwable != null) error = error + "<p style='font-size: 0.9em; font-style: italic;'>(" + ExceptionUtils.getRootCauseMessage(throwable) + ")</p>"; 
/* 52 */       this.errorLabel.setText("<html><div style='text-align: center;'>" + error + " </div></html>");
/* 53 */       if (!isVisible()) refreshStatuses(); 
/* 54 */       setVisible(true);
/*    */     } else {
/* 56 */       SwingUtilities.invokeLater(new Runnable()
/*    */           {
/*    */             public void run() {
/* 59 */               AuthErrorForm.this.displayError(throwable, lines);
/*    */             }
/*    */           });
/*    */     } 
/*    */   }
/*    */   
/*    */   public void refreshStatuses() {
/* 66 */     this.popup.getMinecraftLauncher().getLauncher().getVersionManager().getExecutorService().submit(new Runnable()
/*    */         {
/*    */           public void run() {
/*    */             try {
/* 70 */               TypeToken<Map<String, AuthErrorForm.ServerStatus>> token = new TypeToken<Map<String, AuthErrorForm.ServerStatus>>() {  }
/*    */                 ;
/* 72 */               Map<String, AuthErrorForm.ServerStatus> statuses = (Map<String, AuthErrorForm.ServerStatus>)AuthErrorForm.this.gson.fromJson(Http.performGet(new URL("http://status.mojang.com/check?service=authserver.mojang.com"), AuthErrorForm.this.popup.getMinecraftLauncher().getLauncher().getProxy()), token.getType());
/*    */               
/* 74 */               if (statuses.get("authserver.mojang.com") == AuthErrorForm.ServerStatus.RED) {
/* 75 */                 AuthErrorForm.this.displayError((Throwable)null, new String[] { "It looks like our servers are down right now. Sorry!", "We're already working on the problem and will have it fixed soon.", "Please try again later!" });
/*    */               }
/* 77 */             } catch (Exception exception) {}
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   public enum ServerStatus
/*    */   {
/* 84 */     GREEN("Online, no problems detected."),
/* 85 */     YELLOW("May be experiencing issues."),
/* 86 */     RED("Offline, experiencing problems.");
/*    */     
/*    */     private final String title;
/*    */     
/*    */     ServerStatus(String title) {
/* 91 */       this.title = title;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\login\AuthErrorForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */