/*     */ package net.minecraft.launcher.ui.tabs;
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPopupMenu;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.SwingUtilities;
/*     */ import javax.swing.event.PopupMenuEvent;
/*     */ import javax.swing.event.PopupMenuListener;
/*     */ import javax.swing.table.AbstractTableModel;
/*     */ import net.minecraft.launcher.Launcher;
/*     */ import net.minecraft.launcher.LauncherConstants;
/*     */ import net.minecraft.launcher.SwingUserInterface;
/*     */ import net.minecraft.launcher.profile.AuthenticationDatabase;
/*     */ import net.minecraft.launcher.profile.Profile;
/*     */ import net.minecraft.launcher.profile.ProfileManager;
/*     */ import net.minecraft.launcher.profile.RefreshedProfilesListener;
/*     */ import net.minecraft.launcher.ui.popups.profile.ProfileEditorPopup;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ProfileListTab extends JScrollPane implements RefreshedProfilesListener {
/*  32 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private static final int COLUMN_NAME = 0;
/*     */   private static final int COLUMN_VERSION = 1;
/*     */   private static final int NUM_COLUMNS = 2;
/*     */   private final Launcher minecraftLauncher;
/*  38 */   private final ProfileTableModel dataModel = new ProfileTableModel();
/*  39 */   private final JTable table = new JTable(this.dataModel);
/*  40 */   private final JPopupMenu popupMenu = new JPopupMenu();
/*  41 */   private final JMenuItem addProfileButton = new JMenuItem("Add Profile");
/*  42 */   private final JMenuItem copyProfileButton = new JMenuItem("Copy Profile");
/*  43 */   private final JMenuItem deleteProfileButton = new JMenuItem("Delete Profile");
/*  44 */   private final JMenuItem browseGameFolder = new JMenuItem("Open Game Folder");
/*     */ 
/*     */ 
/*     */   
/*     */   public ProfileListTab(Launcher minecraftLauncher) {
/*  49 */     this.minecraftLauncher = minecraftLauncher;
/*     */     
/*  51 */     setViewportView(this.table);
/*  52 */     createInterface();
/*     */     
/*  54 */     minecraftLauncher.getProfileManager().addRefreshedProfilesListener(this);
/*     */   }
/*     */   
/*     */   protected void createInterface() {
/*  58 */     this.popupMenu.add(this.addProfileButton);
/*  59 */     this.popupMenu.add(this.copyProfileButton);
/*  60 */     this.popupMenu.add(this.deleteProfileButton);
/*  61 */     this.popupMenu.add(this.browseGameFolder);
/*     */     
/*  63 */     this.table.setFillsViewportHeight(true);
/*  64 */     this.table.setSelectionMode(0);
/*     */     
/*  66 */     this.popupMenu.addPopupMenuListener(new PopupMenuListener()
/*     */         {
/*     */           public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
/*  69 */             int[] selection = ProfileListTab.this.table.getSelectedRows();
/*  70 */             boolean hasSelection = (selection != null && selection.length > 0);
/*     */             
/*  72 */             ProfileListTab.this.copyProfileButton.setEnabled(hasSelection);
/*  73 */             ProfileListTab.this.deleteProfileButton.setEnabled(hasSelection);
/*  74 */             ProfileListTab.this.browseGameFolder.setEnabled(hasSelection);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void popupMenuCanceled(PopupMenuEvent e) {}
/*     */         });
/*  86 */     this.addProfileButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  89 */             Profile profile = new Profile();
/*  90 */             profile.setName("New Profile");
/*     */             
/*  92 */             while (ProfileListTab.this.minecraftLauncher.getProfileManager().getProfiles().containsKey(profile.getName())) {
/*  93 */               profile.setName(profile.getName() + "_");
/*     */             }
/*     */             
/*  96 */             ProfileEditorPopup.showEditProfileDialog(ProfileListTab.this.getMinecraftLauncher(), profile);
/*     */           }
/*     */         });
/*     */     
/* 100 */     this.copyProfileButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 103 */             int selection = ProfileListTab.this.table.getSelectedRow();
/* 104 */             if (selection < 0 || selection >= ProfileListTab.this.table.getRowCount())
/*     */               return; 
/* 106 */             Profile current = ProfileListTab.this.dataModel.profiles.get(selection);
/* 107 */             Profile copy = new Profile(current);
/* 108 */             copy.setName("Copy of " + current.getName());
/*     */             
/* 110 */             while (ProfileListTab.this.minecraftLauncher.getProfileManager().getProfiles().containsKey(copy.getName())) {
/* 111 */               copy.setName(copy.getName() + "_");
/*     */             }
/*     */             
/* 114 */             ProfileEditorPopup.showEditProfileDialog(ProfileListTab.this.getMinecraftLauncher(), copy);
/*     */           }
/*     */         });
/*     */     
/* 118 */     this.browseGameFolder.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 121 */             int selection = ProfileListTab.this.table.getSelectedRow();
/* 122 */             if (selection < 0 || selection >= ProfileListTab.this.table.getRowCount())
/*     */               return; 
/* 124 */             Profile profile = ProfileListTab.this.dataModel.profiles.get(selection);
/* 125 */             OperatingSystem.openFolder((profile.getGameDir() == null) ? ProfileListTab.this.minecraftLauncher.getLauncher().getWorkingDirectory() : profile.getGameDir());
/*     */           }
/*     */         });
/*     */     
/* 129 */     this.deleteProfileButton.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 132 */             int selection = ProfileListTab.this.table.getSelectedRow();
/* 133 */             if (selection < 0 || selection >= ProfileListTab.this.table.getRowCount())
/*     */               return; 
/* 135 */             Profile current = ProfileListTab.this.dataModel.profiles.get(selection);
/*     */             
/* 137 */             int result = JOptionPane.showOptionDialog(((SwingUserInterface)ProfileListTab.this.minecraftLauncher.getUserInterface()).getFrame(), "Are you sure you want to delete this profile?", "Profile Confirmation", 0, 2, null, (Object[])LauncherConstants.CONFIRM_PROFILE_DELETION_OPTIONS, LauncherConstants.CONFIRM_PROFILE_DELETION_OPTIONS[0]);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 147 */             if (result == 0) {
/* 148 */               ProfileListTab.this.minecraftLauncher.getProfileManager().getProfiles().remove(current.getName());
/*     */               
/*     */               try {
/* 151 */                 ProfileListTab.this.minecraftLauncher.getProfileManager().saveProfiles();
/* 152 */                 ProfileListTab.this.minecraftLauncher.getProfileManager().fireRefreshEvent();
/* 153 */               } catch (IOException ex) {
/* 154 */                 ProfileListTab.LOGGER.error("Couldn't save profiles whilst deleting '" + current.getName() + "'", ex);
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 160 */     this.table.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e) {
/* 163 */             if (e.getClickCount() == 2) {
/* 164 */               int row = ProfileListTab.this.table.getSelectedRow();
/*     */               
/* 166 */               if (row >= 0 && row < ProfileListTab.this.dataModel.profiles.size()) {
/* 167 */                 ProfileEditorPopup.showEditProfileDialog(ProfileListTab.this.getMinecraftLauncher(), ProfileListTab.this.dataModel.profiles.get(row));
/*     */               }
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent e) {
/* 174 */             if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
/* 175 */               int r = ProfileListTab.this.table.rowAtPoint(e.getPoint());
/* 176 */               if (r >= 0 && r < ProfileListTab.this.table.getRowCount()) {
/* 177 */                 ProfileListTab.this.table.setRowSelectionInterval(r, r);
/*     */               } else {
/* 179 */                 ProfileListTab.this.table.clearSelection();
/*     */               } 
/*     */               
/* 182 */               ProfileListTab.this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
/*     */             } 
/*     */           }
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent e) {
/* 188 */             if (e.isPopupTrigger() && e.getComponent() instanceof JTable) {
/* 189 */               int r = ProfileListTab.this.table.rowAtPoint(e.getPoint());
/* 190 */               if (r >= 0 && r < ProfileListTab.this.table.getRowCount()) {
/* 191 */                 ProfileListTab.this.table.setRowSelectionInterval(r, r);
/*     */               } else {
/* 193 */                 ProfileListTab.this.table.clearSelection();
/*     */               } 
/*     */               
/* 196 */               ProfileListTab.this.popupMenu.show(e.getComponent(), e.getX(), e.getY());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   public Launcher getMinecraftLauncher() {
/* 203 */     return this.minecraftLauncher;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onProfilesRefreshed(final ProfileManager manager) {
/* 208 */     SwingUtilities.invokeLater(new Runnable()
/*     */         {
/*     */           public void run() {
/* 211 */             ProfileListTab.this.dataModel.setProfiles(manager.getProfiles().values());
/*     */           }
/*     */         });
/*     */   }
/*     */   
/*     */   private class ProfileTableModel extends AbstractTableModel { private ProfileTableModel() {
/* 217 */       this.profiles = new ArrayList<Profile>();
/*     */     }
/*     */     private final List<Profile> profiles;
/*     */     public int getRowCount() {
/* 221 */       return this.profiles.size();
/*     */     }
/*     */ 
/*     */     
/*     */     public int getColumnCount() {
/* 226 */       return 2;
/*     */     }
/*     */ 
/*     */     
/*     */     public Class<?> getColumnClass(int columnIndex) {
/* 231 */       return String.class;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getColumnName(int column) {
/* 236 */       switch (column) {
/*     */         case 1:
/* 238 */           return "Version";
/*     */         case 0:
/* 240 */           return "Version name";
/*     */       } 
/* 242 */       return super.getColumnName(column);
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getValueAt(int rowIndex, int columnIndex) {
/* 247 */       Profile profile = this.profiles.get(rowIndex);
/* 248 */       AuthenticationDatabase authDatabase = ProfileListTab.this.minecraftLauncher.getProfileManager().getAuthDatabase();
/*     */       
/* 250 */       switch (columnIndex) {
/*     */         case 0:
/* 252 */           return profile.getName();
/*     */         case 1:
/* 254 */           if (profile.getLastVersionId() == null) {
/* 255 */             return "(Latest version)";
/*     */           }
/* 257 */           return profile.getLastVersionId();
/*     */       } 
/*     */ 
/*     */       
/* 261 */       return null;
/*     */     }
/*     */     
/*     */     public void setProfiles(Collection<Profile> profiles) {
/* 265 */       this.profiles.clear();
/* 266 */       this.profiles.addAll(profiles);
/* 267 */       Collections.sort(this.profiles);
/* 268 */       fireTableDataChanged();
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\ProfileListTab.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */