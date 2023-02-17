/*     */ package net.minecraft.launcher.ui.popups.profile;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.GridBagLayout;
/*     */ import java.awt.event.ItemEvent;
/*     */ import java.awt.event.ItemListener;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JCheckBox;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.event.DocumentEvent;
/*     */ import javax.swing.event.DocumentListener;
/*     */ 
/*     */ public class ProfileJavaPanel extends JPanel {
/*  15 */   private final JCheckBox javaPathCustom = new JCheckBox("Executable:"); private final ProfileEditorPopup editor;
/*  16 */   private final JTextField javaPathField = new JTextField();
/*  17 */   private final JCheckBox javaArgsCustom = new JCheckBox("JVM Arguments:");
/*  18 */   private final JTextField javaArgsField = new JTextField();
/*     */   
/*     */   public ProfileJavaPanel(ProfileEditorPopup editor) {
/*  21 */     this.editor = editor;
/*     */     
/*  23 */     setLayout(new GridBagLayout());
/*  24 */     setBorder(BorderFactory.createTitledBorder("Java Settings (Advanced)"));
/*     */     
/*  26 */     createInterface();
/*  27 */     fillDefaultValues();
/*  28 */     addEventHandlers();
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  32 */     GridBagConstraints constraints = new GridBagConstraints();
/*  33 */     constraints.insets = new Insets(2, 2, 2, 2);
/*  34 */     constraints.anchor = 17;
/*     */     
/*  36 */     constraints.gridy = 0;
/*     */     
/*  38 */     add(this.javaPathCustom, constraints);
/*  39 */     constraints.fill = 2;
/*  40 */     constraints.weightx = 1.0D;
/*  41 */     add(this.javaPathField, constraints);
/*  42 */     constraints.weightx = 0.0D;
/*  43 */     constraints.fill = 0;
/*     */     
/*  45 */     constraints.gridy++;
/*     */     
/*  47 */     add(this.javaArgsCustom, constraints);
/*  48 */     constraints.fill = 2;
/*  49 */     constraints.weightx = 1.0D;
/*  50 */     add(this.javaArgsField, constraints);
/*  51 */     constraints.weightx = 0.0D;
/*  52 */     constraints.fill = 0;
/*     */     
/*  54 */     constraints.gridy++;
/*     */   }
/*     */   
/*     */   protected void fillDefaultValues() {
/*  58 */     String javaPath = this.editor.getProfile().getJavaPath();
/*  59 */     if (javaPath != null) {
/*  60 */       this.javaPathCustom.setSelected(true);
/*  61 */       this.javaPathField.setText(javaPath);
/*     */     } else {
/*  63 */       this.javaPathCustom.setSelected(false);
/*  64 */       this.javaPathField.setText(OperatingSystem.getCurrentPlatform().getJavaDir());
/*     */     } 
/*  66 */     updateJavaPathState();
/*     */     
/*  68 */     String args = this.editor.getProfile().getJavaArgs();
/*  69 */     if (args != null) {
/*  70 */       this.javaArgsCustom.setSelected(true);
/*  71 */       this.javaArgsField.setText(args);
/*     */     } else {
/*  73 */       this.javaArgsCustom.setSelected(false);
/*  74 */       this.javaArgsField.setText("-Xmx1G -XX:+UseConcMarkSweepGC -XX:+CMSIncrementalMode -XX:-UseAdaptiveSizePolicy -Xmn128M");
/*     */     } 
/*  76 */     updateJavaArgsState();
/*     */   }
/*     */   
/*     */   protected void addEventHandlers() {
/*  80 */     this.javaPathCustom.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/*  83 */             ProfileJavaPanel.this.updateJavaPathState();
/*     */           }
/*     */         });
/*     */     
/*  87 */     this.javaPathField.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void insertUpdate(DocumentEvent e) {
/*  90 */             ProfileJavaPanel.this.updateJavaPath();
/*     */           }
/*     */ 
/*     */           
/*     */           public void removeUpdate(DocumentEvent e) {
/*  95 */             ProfileJavaPanel.this.updateJavaPath();
/*     */           }
/*     */ 
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {
/* 100 */             ProfileJavaPanel.this.updateJavaPath();
/*     */           }
/*     */         });
/*     */     
/* 104 */     this.javaArgsCustom.addItemListener(new ItemListener()
/*     */         {
/*     */           public void itemStateChanged(ItemEvent e) {
/* 107 */             ProfileJavaPanel.this.updateJavaArgsState();
/*     */           }
/*     */         });
/*     */     
/* 111 */     this.javaArgsField.getDocument().addDocumentListener(new DocumentListener()
/*     */         {
/*     */           public void insertUpdate(DocumentEvent e) {
/* 114 */             ProfileJavaPanel.this.updateJavaArgs();
/*     */           }
/*     */ 
/*     */           
/*     */           public void removeUpdate(DocumentEvent e) {
/* 119 */             ProfileJavaPanel.this.updateJavaArgs();
/*     */           }
/*     */ 
/*     */           
/*     */           public void changedUpdate(DocumentEvent e) {
/* 124 */             ProfileJavaPanel.this.updateJavaArgs();
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private void updateJavaPath() {
/* 130 */     if (this.javaPathCustom.isSelected()) {
/* 131 */       this.editor.getProfile().setJavaDir(this.javaPathField.getText());
/*     */     } else {
/* 133 */       this.editor.getProfile().setJavaDir(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateJavaPathState() {
/* 138 */     if (this.javaPathCustom.isSelected()) {
/* 139 */       this.javaPathField.setEnabled(true);
/* 140 */       this.editor.getProfile().setJavaDir(this.javaPathField.getText());
/*     */     } else {
/* 142 */       this.javaPathField.setEnabled(false);
/* 143 */       this.editor.getProfile().setJavaDir(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateJavaArgs() {
/* 148 */     if (this.javaArgsCustom.isSelected()) {
/* 149 */       this.editor.getProfile().setJavaArgs(this.javaArgsField.getText());
/*     */     } else {
/* 151 */       this.editor.getProfile().setJavaArgs(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateJavaArgsState() {
/* 156 */     if (this.javaArgsCustom.isSelected()) {
/* 157 */       this.javaArgsField.setEnabled(true);
/* 158 */       this.editor.getProfile().setJavaArgs(this.javaArgsField.getText());
/*     */     } else {
/* 160 */       this.javaArgsField.setEnabled(false);
/* 161 */       this.editor.getProfile().setJavaArgs(null);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\popups\profile\ProfileJavaPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */