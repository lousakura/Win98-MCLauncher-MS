/*    */ package net.minecraft.launcher.ui.bottombar;
/*    */ 
/*    */ import java.awt.Component;
/*    */ import java.awt.GridBagConstraints;
/*    */ import java.awt.GridBagLayout;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ public abstract class SidebarGridForm
/*    */   extends JPanel
/*    */ {
/*    */   protected void createInterface() {
/* 12 */     GridBagLayout layout = new GridBagLayout();
/* 13 */     GridBagConstraints constraints = new GridBagConstraints();
/* 14 */     setLayout(layout);
/*    */     
/* 16 */     populateGrid(constraints);
/*    */   }
/*    */   
/*    */   protected abstract void populateGrid(GridBagConstraints paramGridBagConstraints);
/*    */   
/*    */   protected <T extends Component> T add(T component, GridBagConstraints constraints, int x, int y, int weight, int width) {
/* 22 */     return add(component, constraints, x, y, weight, width, 10);
/*    */   }
/*    */   
/*    */   protected <T extends Component> T add(T component, GridBagConstraints constraints, int x, int y, int weight, int width, int anchor) {
/* 26 */     constraints.gridx = x;
/* 27 */     constraints.gridy = y;
/* 28 */     constraints.weightx = weight;
/* 29 */     constraints.weighty = 1.0D;
/* 30 */     constraints.gridwidth = width;
/* 31 */     constraints.anchor = anchor;
/*    */     
/* 33 */     add((Component)component, constraints);
/* 34 */     return component;
/*    */   }
/*    */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\bottombar\SidebarGridForm.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */