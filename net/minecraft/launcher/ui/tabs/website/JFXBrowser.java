/*     */ package net.minecraft.launcher.ui.tabs.website;
/*     */ 
/*     */ import com.mojang.launcher.OperatingSystem;
/*     */ import java.awt.Component;
/*     */ import java.awt.Dimension;
/*     */ import java.net.URI;
/*     */ import javafx.application.Platform;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.concurrent.Worker;
/*     */ import javafx.embed.swing.JFXPanel;
/*     */ import javafx.scene.Group;
/*     */ import javafx.scene.Scene;
/*     */ import javafx.scene.web.WebEngine;
/*     */ import javafx.scene.web.WebView;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.events.Event;
/*     */ import org.w3c.dom.events.EventListener;
/*     */ import org.w3c.dom.events.EventTarget;
/*     */ 
/*     */ public class JFXBrowser implements Browser {
/*  28 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*  30 */   private final Object lock = new Object();
/*  31 */   private final JFXPanel fxPanel = new JFXPanel();
/*     */   private String urlToBrowseTo;
/*     */   private Dimension size;
/*     */   private WebView browser;
/*     */   private WebEngine webEngine;
/*     */   
/*     */   public JFXBrowser() {
/*  38 */     Platform.runLater(new Runnable()
/*     */         {
/*     */           public void run() {
/*  41 */             Group root = new Group();
/*  42 */             Scene scene = new Scene(root);
/*     */             
/*  44 */             JFXBrowser.this.fxPanel.setScene(scene);
/*     */             
/*  46 */             synchronized (JFXBrowser.this.lock) {
/*  47 */               JFXBrowser.this.browser = new WebView();
/*  48 */               JFXBrowser.this.browser.setContextMenuEnabled(false);
/*     */               
/*  50 */               if (JFXBrowser.this.size != null) {
/*  51 */                 JFXBrowser.this.resize(JFXBrowser.this.size);
/*     */               }
/*     */               
/*  54 */               JFXBrowser.this.webEngine = JFXBrowser.this.browser.getEngine();
/*  55 */               JFXBrowser.this.webEngine.setJavaScriptEnabled(false);
/*  56 */               JFXBrowser.this.webEngine.getLoadWorker().stateProperty().addListener(new ChangeListener<Worker.State>()
/*     */                   {
/*     */                     public void changed(ObservableValue<? extends Worker.State> observableValue, Worker.State oldState, Worker.State newState) {
/*  59 */                       if (newState == Worker.State.SUCCEEDED) {
/*  60 */                         EventListener listener = new EventListener() {
/*     */                             public void handleEvent(Event event) {
/*  62 */                               if (event.getTarget() instanceof Element) {
/*  63 */                                 Element element = (Element)event.getTarget();
/*  64 */                                 String href = element.getAttribute("href");
/*     */                                 
/*  66 */                                 while (StringUtils.isEmpty(href) && element.getParentNode() instanceof Element) {
/*  67 */                                   element = (Element)element.getParentNode();
/*  68 */                                   href = element.getAttribute("href");
/*     */                                 } 
/*     */                                 
/*  71 */                                 if (href != null && href.length() > 0) {
/*     */                                   try {
/*  73 */                                     OperatingSystem.openLink(new URI(href));
/*  74 */                                   } catch (Exception e) {
/*  75 */                                     JFXBrowser.LOGGER.error("Unexpected exception opening link " + href, e);
/*     */                                   } 
/*  77 */                                   event.preventDefault();
/*  78 */                                   event.stopPropagation();
/*     */                                 } 
/*     */                               } 
/*     */                             }
/*     */                           };
/*     */                         
/*  84 */                         Document doc = JFXBrowser.this.webEngine.getDocument();
/*  85 */                         if (doc != null) {
/*  86 */                           NodeList elements = doc.getElementsByTagName("a");
/*  87 */                           for (int i = 0; i < elements.getLength(); i++) {
/*  88 */                             Node item = elements.item(i);
/*  89 */                             if (item instanceof EventTarget) {
/*  90 */                               ((EventTarget)item).addEventListener("click", listener, false);
/*     */                             }
/*     */                           } 
/*     */                         } 
/*     */                       } 
/*     */                     }
/*     */                   });
/*     */               
/*  98 */               if (JFXBrowser.this.urlToBrowseTo != null) {
/*  99 */                 JFXBrowser.this.loadUrl(JFXBrowser.this.urlToBrowseTo);
/*     */               }
/*     */             } 
/* 102 */             root.getChildren().add(JFXBrowser.this.browser);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadUrl(final String url) {
/* 109 */     synchronized (this.lock) {
/* 110 */       this.urlToBrowseTo = url;
/*     */       
/* 112 */       if (this.webEngine != null) {
/* 113 */         Platform.runLater(new Runnable()
/*     */             {
/*     */               public void run() {
/* 116 */                 JFXBrowser.this.webEngine.load(url);
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Component getComponent() {
/* 125 */     return this.fxPanel;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resize(Dimension size) {
/* 130 */     synchronized (this.lock) {
/* 131 */       this.size = size;
/*     */       
/* 133 */       if (this.browser != null) {
/* 134 */         this.browser.setMinSize(size.getWidth(), size.getHeight());
/* 135 */         this.browser.setMaxSize(size.getWidth(), size.getHeight());
/* 136 */         this.browser.setPrefSize(size.getWidth(), size.getHeight());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\net\minecraft\launche\\ui\tabs\website\JFXBrowser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */