/*     */ package net.minecraft.launcher.ui.tabs;
/*     */ import com.mojang.launcher.Http;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import com.mojang.launcher.versions.CompleteVersion;
/*     */ import java.awt.GridBagConstraints;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URI;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JEditorPane;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import net.minecraft.hopper.HopperService;
/*     */ import net.minecraft.hopper.SubmitResponse;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class CrashReportTab extends JPanel {
/*  26 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final Launcher minecraftLauncher;
/*     */   private final CompleteVersion version;
/*     */   private final File reportFile;
/*     */   private final String report;
/*  31 */   private final JEditorPane reportEditor = new JEditorPane();
/*  32 */   private final JScrollPane scrollPane = new JScrollPane(this.reportEditor);
/*     */   private final CrashInfoPane crashInfoPane;
/*     */   private final boolean isModded;
/*  35 */   private SubmitResponse hopperServiceResponse = null;
/*     */   
/*     */   public CrashReportTab(final Launcher minecraftLauncher, final CompleteVersion version, File reportFile, final String report) {
/*  38 */     super(true);
/*  39 */     this.minecraftLauncher = minecraftLauncher;
/*  40 */     this.version = version;
/*  41 */     this.reportFile = reportFile;
/*  42 */     this.report = report;
/*  43 */     this.crashInfoPane = new CrashInfoPane(minecraftLauncher);
/*     */     
/*  45 */     this.isModded = (!report.contains("Is Modded: Probably not") && !report.contains("Is Modded: Unknown"));
/*     */     
/*  47 */     setLayout(new BorderLayout());
/*  48 */     createInterface();
/*     */     
/*  50 */     if (minecraftLauncher.getProfileManager().getSelectedProfile().getUseHopperCrashService()) {
/*  51 */       minecraftLauncher.getLauncher().getVersionManager().getExecutorService().submit(new Runnable()
/*     */           {
/*     */             public void run() {
/*     */               try {
/*  55 */                 Map<String, String> environment = new HashMap<String, String>();
/*  56 */                 environment.put("launcher.version", LauncherConstants.getVersionName());
/*  57 */                 environment.put("launcher.title", minecraftLauncher.getUserInterface().getTitle());
/*  58 */                 environment.put("bootstrap.version", String.valueOf(minecraftLauncher.getBootstrapVersion()));
/*  59 */                 CrashReportTab.this.hopperServiceResponse = HopperService.submitReport(minecraftLauncher.getLauncher().getProxy(), report, "Minecraft", version.getId(), environment);
/*  60 */                 CrashReportTab.LOGGER.info("Reported crash to Mojang (ID " + CrashReportTab.this.hopperServiceResponse.getReport().getId() + ")");
/*     */                 
/*  62 */                 if (CrashReportTab.this.hopperServiceResponse.getProblem() != null) {
/*  63 */                   CrashReportTab.this.showKnownProblemPopup();
/*  64 */                 } else if (CrashReportTab.this.hopperServiceResponse.getReport().canBePublished()) {
/*  65 */                   CrashReportTab.this.showPublishReportPrompt();
/*     */                 } 
/*  67 */               } catch (IOException e) {
/*  68 */                 CrashReportTab.LOGGER.error("Couldn't report crash to Mojang", e);
/*     */               } 
/*     */             }
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private void showPublishReportPrompt() {
/*  76 */     String[] options = { "Publish Crash Report", "Cancel" };
/*  77 */     JLabel message = new JLabel();
/*     */     
/*  79 */     message.setText("<html><p>Sorry, but it looks like the game crashed and we don't know why.</p><p>Would you mind publishing this report so that " + (this.isModded ? "the mod authors" : "Mojang") + " can fix it?</p></html>");
/*     */ 
/*     */     
/*  82 */     int result = JOptionPane.showOptionDialog(this, message, "Uhoh, something went wrong!", 0, 1, null, (Object[])options, options[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     if (result == 0) {
/*     */       try {
/*  94 */         PublishResponse publishResponse = HopperService.publishReport(this.minecraftLauncher.getLauncher().getProxy(), this.hopperServiceResponse.getReport());
/*  95 */       } catch (IOException e) {
/*  96 */         LOGGER.error("Couldn't publish report " + this.hopperServiceResponse.getReport().getId(), e);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   private void showKnownProblemPopup() {
/* 102 */     if (this.hopperServiceResponse.getProblem().getUrl() == null) {
/* 103 */       JOptionPane.showMessageDialog(this, this.hopperServiceResponse.getProblem().getDescription(), this.hopperServiceResponse.getProblem().getTitle(), 1);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 110 */       String[] options = { "Fix The Problem", "Cancel" };
/* 111 */       int result = JOptionPane.showOptionDialog(this, this.hopperServiceResponse.getProblem().getDescription(), this.hopperServiceResponse.getProblem().getTitle(), 0, 1, null, (Object[])options, options[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 121 */       if (result == 0) {
/*     */         try {
/* 123 */           OperatingSystem.openLink(new URI(this.hopperServiceResponse.getProblem().getUrl()));
/* 124 */         } catch (URISyntaxException e) {
/* 125 */           LOGGER.error("Couldn't open help page ( " + this.hopperServiceResponse.getProblem().getUrl() + "  ) for crash", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/* 132 */     add(this.crashInfoPane, "North");
/* 133 */     add(this.scrollPane, "Center");
/*     */     
/* 135 */     this.reportEditor.setText(this.report);
/*     */     
/* 137 */     this.crashInfoPane.createInterface();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private class CrashInfoPane
/*     */     extends JPanel
/*     */     implements ActionListener
/*     */   {
/*     */     public static final String INFO_NORMAL = "<html><div style='width: 100%'><p><b>Uhoh, it looks like the game has crashed! Sorry for the inconvenience :(</b></p><p>Using magic and love, we've managed to gather some details about the crash and we will investigate this as soon as we can.</p><p>You can see the full report below.</p></div></html>";
/*     */ 
/*     */     
/*     */     public static final String INFO_MODDED = "<html><div style='width: 100%'><p><b>Uhoh, it looks like the game has crashed! Sorry for the inconvenience :(</b></p><p>We think your game may be modded, and as such we can't accept this crash report.</p><p>However, if you do indeed use mods, please send this to the mod authors to take a look at!</p></div></html>";
/*     */     
/* 151 */     private final JButton submitButton = new JButton("Report to Mojang");
/* 152 */     private final JButton openFileButton = new JButton("Open report file");
/*     */     
/*     */     protected CrashInfoPane(Launcher minecraftLauncher) {
/* 155 */       this.submitButton.addActionListener(this);
/* 156 */       this.openFileButton.addActionListener(this);
/*     */     }
/*     */     
/*     */     protected void createInterface() {
/* 160 */       setLayout(new GridBagLayout());
/* 161 */       GridBagConstraints constraints = new GridBagConstraints();
/*     */       
/* 163 */       constraints.anchor = 13;
/* 164 */       constraints.fill = 2;
/* 165 */       constraints.insets = new Insets(2, 2, 2, 2);
/*     */       
/* 167 */       constraints.gridx = 1;
/* 168 */       add(this.submitButton, constraints);
/* 169 */       constraints.gridy = 1;
/* 170 */       add(this.openFileButton, constraints);
/*     */       
/* 172 */       constraints.gridx = 0;
/* 173 */       constraints.gridy = 0;
/* 174 */       constraints.weightx = 1.0D;
/* 175 */       constraints.weighty = 1.0D;
/* 176 */       constraints.gridheight = 2;
/* 177 */       add(new JLabel(CrashReportTab.this.isModded ? "<html><div style='width: 100%'><p><b>Uhoh, it looks like the game has crashed! Sorry for the inconvenience :(</b></p><p>We think your game may be modded, and as such we can't accept this crash report.</p><p>However, if you do indeed use mods, please send this to the mod authors to take a look at!</p></div></html>" : "<html><div style='width: 100%'><p><b>Uhoh, it looks like the game has crashed! Sorry for the inconvenience :(</b></p><p>Using magic and love, we've managed to gather some details about the crash and we will investigate this as soon as we can.</p><p>You can see the full report below.</p></div></html>"), constraints);
/*     */       
/* 179 */       if (CrashReportTab.this.isModded) {
/* 180 */         this.submitButton.setEnabled(false);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent e) {
/* 186 */       if (e.getSource() == this.submitButton) {
/* 187 */         if (CrashReportTab.this.hopperServiceResponse != null) {
/* 188 */           if (CrashReportTab.this.hopperServiceResponse.getProblem() != null) {
/* 189 */             CrashReportTab.this.showKnownProblemPopup();
/* 190 */           } else if (CrashReportTab.this.hopperServiceResponse.getReport().canBePublished()) {
/* 191 */             CrashReportTab.this.showPublishReportPrompt();
/*     */           } 
/*     */         } else {
/*     */           try {
/* 195 */             Map<String, Object> args = new HashMap<String, Object>();
/*     */             
/* 197 */             args.put("pid", Integer.valueOf(10400));
/* 198 */             args.put("issuetype", Integer.valueOf(1));
/* 199 */             args.put("description", "Put the summary of the bug you're having here\n\n*What I expected to happen was...:*\nDescribe what you thought should happen here\n\n*What actually happened was...:*\nDescribe what happened here\n\n*Steps to Reproduce:*\n1. Put a step by step guide on how to trigger the bug here\n2. ...\n3. ...");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 211 */             args.put("environment", buildEnvironmentInfo());
/*     */             
/* 213 */             OperatingSystem.openLink(URI.create("https://bugs.mojang.com/secure/CreateIssueDetails!init.jspa?" + Http.buildQuery(args)));
/* 214 */           } catch (Throwable ex) {
/* 215 */             CrashReportTab.LOGGER.error("Couldn't open bugtracker", ex);
/*     */           } 
/*     */         } 
/* 218 */       } else if (e.getSource() == this.openFileButton) {
/* 219 */         OperatingSystem.openLink(CrashReportTab.this.reportFile.toURI());
/*     */       } 
/*     */     }
/*     */     
/*     */     private String buildEnvironmentInfo() {
/* 224 */       StringBuilder result = new StringBuilder();
/*     */       
/* 226 */       result.append("OS: ");
/* 227 */       result.append(System.getProperty("os.name"));
/* 228 */       result.append(" (ver ");
/* 229 */       result.append(System.getProperty("os.version"));
/* 230 */       result.append(", arch ");
/* 231 */       result.append(System.getProperty("os.arch"));
/* 232 */       result.append(")\nJava: ");
/* 233 */       result.append(System.getProperty("java.version"));
/* 234 */       result.append(" (by ");
/* 235 */       result.append(System.getProperty("java.vendor"));
/* 236 */       result.append(")\nLauncher: ");
/* 237 */       result.append(CrashReportTab.this.minecraftLauncher.getUserInterface().getTitle());
/* 238 */       result.append(" (bootstrap ");
/* 239 */       result.append(CrashReportTab.this.minecraftLauncher.getBootstrapVersion());
/* 240 */       result.append(")\nMinecraft: ");
/* 241 */       result.append(CrashReportTab.this.version.getId());
/* 242 */       result.append(" (updated ");
/* 243 */       result.append(CrashReportTab.this.version.getUpdatedTime());
/* 244 */       result.append(")");
/*     */       
/* 246 */       return result.toString();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\CrashReportTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */