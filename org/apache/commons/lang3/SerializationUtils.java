/*     */ package org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.ObjectStreamClass;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SerializationUtils
/*     */ {
/*     */   public static <T extends Serializable> T clone(T object) {
/*  79 */     if (object == null) {
/*  80 */       return null;
/*     */     }
/*  82 */     byte[] objectData = serialize((Serializable)object);
/*  83 */     ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
/*     */     
/*  85 */     ClassLoaderAwareObjectInputStream in = null;
/*     */     
/*     */     try {
/*  88 */       in = new ClassLoaderAwareObjectInputStream(bais, object.getClass().getClassLoader());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  96 */       Serializable serializable = (Serializable)in.readObject();
/*  97 */       return (T)serializable;
/*     */     }
/*  99 */     catch (ClassNotFoundException ex) {
/* 100 */       throw new SerializationException("ClassNotFoundException while reading cloned object data", ex);
/* 101 */     } catch (IOException ex) {
/* 102 */       throw new SerializationException("IOException while reading cloned object data", ex);
/*     */     } finally {
/*     */       try {
/* 105 */         if (in != null) {
/* 106 */           in.close();
/*     */         }
/* 108 */       } catch (IOException ex) {
/* 109 */         throw new SerializationException("IOException on closing cloned object data InputStream.", ex);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */   
/*     */   public static <T extends Serializable> T roundtrip(T msg) {
/* 126 */     return (T)deserialize(serialize((Serializable)msg));
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void serialize(Serializable obj, OutputStream outputStream) {
/* 147 */     if (outputStream == null) {
/* 148 */       throw new IllegalArgumentException("The OutputStream must not be null");
/*     */     }
/* 150 */     ObjectOutputStream out = null;
/*     */     
/*     */     try {
/* 153 */       out = new ObjectOutputStream(outputStream);
/* 154 */       out.writeObject(obj);
/*     */     }
/* 156 */     catch (IOException ex) {
/* 157 */       throw new SerializationException(ex);
/*     */     } finally {
/*     */       try {
/* 160 */         if (out != null) {
/* 161 */           out.close();
/*     */         }
/* 163 */       } catch (IOException ex) {}
/*     */     } 
/*     */   }
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
/*     */   public static byte[] serialize(Serializable obj) {
/* 178 */     ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
/* 179 */     serialize(obj, baos);
/* 180 */     return baos.toByteArray();
/*     */   }
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
/*     */   public static <T> T deserialize(InputStream inputStream) {
/* 216 */     if (inputStream == null) {
/* 217 */       throw new IllegalArgumentException("The InputStream must not be null");
/*     */     }
/* 219 */     ObjectInputStream in = null;
/*     */     
/*     */     try {
/* 222 */       in = new ObjectInputStream(inputStream);
/*     */       
/* 224 */       T obj = (T)in.readObject();
/* 225 */       return obj;
/*     */     }
/* 227 */     catch (ClassCastException ex) {
/* 228 */       throw new SerializationException(ex);
/* 229 */     } catch (ClassNotFoundException ex) {
/* 230 */       throw new SerializationException(ex);
/* 231 */     } catch (IOException ex) {
/* 232 */       throw new SerializationException(ex);
/*     */     } finally {
/*     */       try {
/* 235 */         if (in != null) {
/* 236 */           in.close();
/*     */         }
/* 238 */       } catch (IOException ex) {}
/*     */     } 
/*     */   }
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
/*     */   
/*     */   public static <T> T deserialize(byte[] objectData) {
/* 265 */     if (objectData == null) {
/* 266 */       throw new IllegalArgumentException("The byte[] must not be null");
/*     */     }
/* 268 */     return deserialize(new ByteArrayInputStream(objectData));
/*     */   }
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
/*     */   
/*     */   static class ClassLoaderAwareObjectInputStream
/*     */     extends ObjectInputStream
/*     */   {
/* 285 */     private static final Map<String, Class<?>> primitiveTypes = new HashMap<String, Class<?>>();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final ClassLoader classLoader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public ClassLoaderAwareObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
/* 297 */       super(in);
/* 298 */       this.classLoader = classLoader;
/*     */       
/* 300 */       primitiveTypes.put("byte", byte.class);
/* 301 */       primitiveTypes.put("short", short.class);
/* 302 */       primitiveTypes.put("int", int.class);
/* 303 */       primitiveTypes.put("long", long.class);
/* 304 */       primitiveTypes.put("float", float.class);
/* 305 */       primitiveTypes.put("double", double.class);
/* 306 */       primitiveTypes.put("boolean", boolean.class);
/* 307 */       primitiveTypes.put("char", char.class);
/* 308 */       primitiveTypes.put("void", void.class);
/*     */     }
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
/*     */     protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
/* 321 */       String name = desc.getName();
/*     */       try {
/* 323 */         return Class.forName(name, false, this.classLoader);
/* 324 */       } catch (ClassNotFoundException ex) {
/*     */         try {
/* 326 */           return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
/* 327 */         } catch (ClassNotFoundException cnfe) {
/* 328 */           Class<?> cls = primitiveTypes.get(name);
/* 329 */           if (cls != null) {
/* 330 */             return cls;
/*     */           }
/* 332 */           throw cnfe;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\SerializationUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */