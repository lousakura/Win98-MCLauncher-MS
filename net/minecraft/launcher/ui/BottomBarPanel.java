/*    */ package net.minecraft.launcher.ui;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import java.awt.GridLayout;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ import net.minecraft.launcher.Launcher;
/*    */ import net.minecraft.launcher.ui.bottombar.PlayButtonPanel;
/*    */ import net.minecraft.launcher.ui.bottombar.PlayerInfoPanel;
/*    */ import net.minecraft.launcher.ui.bottombar.ProfileSelectionPanel;
/*    */ 
/*    */ public class BottomBarPanel
/*    */   extends JPanel {
/*    */   private final Launcher minecraftLauncher;
/*    */   private final ProfileSelectionPanel profileSelectionPanel;
/*    */   
/*    */   public BottomBarPanel(Launcher minecraftLauncher) {
/* 20 */     this.minecraftLauncher = minecraftLauncher;
/*    */     
/* 22 */     int border = 4;
/* 23 */     setBorder(new EmptyBorder(border, border, border, border));
/*    */     
/* 25 */     this.profileSelectionPanel = new ProfileSelectionPanel(minecraftLauncher);
/* 26 */     this.playerInfoPanel = new PlayerInfoPanel(minecraftLauncher);
/* 27 */     this.playButtonPanel = new PlayButtonPanel(minecraftLauncher);
/*    */     
/* 29 */     createInterface();
/*    */   }
/*    */   private final PlayerInfoPanel playerInfoPanel; private final PlayButtonPanel playButtonPanel;
/*    */   protected void createInterface() {
/* 33 */     setLayout(new GridLayout(1, 3));
/*    */     
/* 35 */     add(wrapSidePanel((JPanel)this.profileSelectionPanel, 17));
/* 36 */     add((Component)this.playButtonPanel);
/* 37 */     add(wrapSidePanel((JPanel)this.playerInfoPanel, 13));
/*    */   }
/*    */   
/*    */   protected JPanel wrapSidePanel(JPanel target, int side) {
/* 41 */     JPanel wrapper = new JPanel(new GridBagLayout());
/* 42 */     GridBagConstraints constraints = new GridBagConstraints();
/* 43 */     constraints.anchor = side;
/* 44 */     constraints.weightx = 1.0D;
/* 45 */     constraints.weighty = 1.0D;
/*    */     
/* 47 */     wrapper.add(target, constraints);
/*    */     
/* 49 */     return wrapper;
/*    */   }
/*    */   
/*    */   public Launcher getMinecraftLauncher() {
/* 53 */     return this.minecraftLauncher;
/*    */   }
/*    */   
/*    */   public ProfileSelectionPanel getProfileSelectionPanel() {
/* 57 */     return this.profileSelectionPanel;
/*    */   }
/*    */   
/*    */   public PlayerInfoPanel getPlayerInfoPanel() {
/* 61 */     return this.playerInfoPanel;
/*    */   }
/*    */   
/*    */   public PlayButtonPanel getPlayButtonPanel() {
/* 65 */     return this.playButtonPanel;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\BottomBarPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */