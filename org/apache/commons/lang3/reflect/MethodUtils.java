/*     */ package org.apache.commons.lang3.reflect;
/*     */ 
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang3.ArrayUtils;
/*     */ import org.apache.commons.lang3.ClassUtils;
/*     */ import org.apache.commons.lang3.Validate;
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
/*     */ public class MethodUtils
/*     */ {
/*     */   public static Object invokeMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/*  95 */     args = ArrayUtils.nullToEmpty(args);
/*  96 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/*  97 */     return invokeMethod(object, methodName, args, parameterTypes);
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
/*     */   public static Object invokeMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 123 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 124 */     args = ArrayUtils.nullToEmpty(args);
/* 125 */     Method method = getMatchingAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*     */     
/* 127 */     if (method == null) {
/* 128 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
/*     */     }
/*     */ 
/*     */     
/* 132 */     return method.invoke(object, args);
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
/*     */   public static Object invokeExactMethod(Object object, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 156 */     args = ArrayUtils.nullToEmpty(args);
/* 157 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/* 158 */     return invokeExactMethod(object, methodName, args, parameterTypes);
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
/*     */   public static Object invokeExactMethod(Object object, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 184 */     args = ArrayUtils.nullToEmpty(args);
/* 185 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 186 */     Method method = getAccessibleMethod(object.getClass(), methodName, parameterTypes);
/*     */     
/* 188 */     if (method == null) {
/* 189 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on object: " + object.getClass().getName());
/*     */     }
/*     */ 
/*     */     
/* 193 */     return method.invoke(object, args);
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
/*     */   public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 219 */     args = ArrayUtils.nullToEmpty(args);
/* 220 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 221 */     Method method = getAccessibleMethod(cls, methodName, parameterTypes);
/* 222 */     if (method == null) {
/* 223 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
/*     */     }
/*     */     
/* 226 */     return method.invoke(null, args);
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
/*     */   public static Object invokeStaticMethod(Class<?> cls, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 256 */     args = ArrayUtils.nullToEmpty(args);
/* 257 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/* 258 */     return invokeStaticMethod(cls, methodName, args, parameterTypes);
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
/*     */   public static Object invokeStaticMethod(Class<?> cls, String methodName, Object[] args, Class<?>[] parameterTypes) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 287 */     args = ArrayUtils.nullToEmpty(args);
/* 288 */     parameterTypes = ArrayUtils.nullToEmpty(parameterTypes);
/* 289 */     Method method = getMatchingAccessibleMethod(cls, methodName, parameterTypes);
/*     */     
/* 291 */     if (method == null) {
/* 292 */       throw new NoSuchMethodException("No such accessible method: " + methodName + "() on class: " + cls.getName());
/*     */     }
/*     */     
/* 295 */     return method.invoke(null, args);
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
/*     */   public static Object invokeExactStaticMethod(Class<?> cls, String methodName, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
/* 319 */     args = ArrayUtils.nullToEmpty(args);
/* 320 */     Class<?>[] parameterTypes = ClassUtils.toClass(args);
/* 321 */     return invokeExactStaticMethod(cls, methodName, args, parameterTypes);
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
/*     */   public static Method getAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*     */     try {
/* 339 */       return getAccessibleMethod(cls.getMethod(methodName, parameterTypes));
/*     */     }
/* 341 */     catch (NoSuchMethodException e) {
/* 342 */       return null;
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
/*     */   public static Method getAccessibleMethod(Method method) {
/* 355 */     if (!MemberUtils.isAccessible(method)) {
/* 356 */       return null;
/*     */     }
/*     */     
/* 359 */     Class<?> cls = method.getDeclaringClass();
/* 360 */     if (Modifier.isPublic(cls.getModifiers())) {
/* 361 */       return method;
/*     */     }
/* 363 */     String methodName = method.getName();
/* 364 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*     */ 
/*     */     
/* 367 */     method = getAccessibleMethodFromInterfaceNest(cls, methodName, parameterTypes);
/*     */ 
/*     */ 
/*     */     
/* 371 */     if (method == null) {
/* 372 */       method = getAccessibleMethodFromSuperclass(cls, methodName, parameterTypes);
/*     */     }
/*     */     
/* 375 */     return method;
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
/*     */   private static Method getAccessibleMethodFromSuperclass(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/* 390 */     Class<?> parentClass = cls.getSuperclass();
/* 391 */     while (parentClass != null) {
/* 392 */       if (Modifier.isPublic(parentClass.getModifiers())) {
/*     */         try {
/* 394 */           return parentClass.getMethod(methodName, parameterTypes);
/* 395 */         } catch (NoSuchMethodException e) {
/* 396 */           return null;
/*     */         } 
/*     */       }
/* 399 */       parentClass = parentClass.getSuperclass();
/*     */     } 
/* 401 */     return null;
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
/*     */   private static Method getAccessibleMethodFromInterfaceNest(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/* 422 */     for (; cls != null; cls = cls.getSuperclass()) {
/*     */ 
/*     */       
/* 425 */       Class<?>[] interfaces = cls.getInterfaces();
/* 426 */       for (int i = 0; i < interfaces.length; i++) {
/*     */         
/* 428 */         if (Modifier.isPublic(interfaces[i].getModifiers()))
/*     */           
/*     */           try {
/*     */ 
/*     */             
/* 433 */             return interfaces[i].getDeclaredMethod(methodName, parameterTypes);
/*     */           }
/* 435 */           catch (NoSuchMethodException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 442 */             Method method = getAccessibleMethodFromInterfaceNest(interfaces[i], methodName, parameterTypes);
/*     */             
/* 444 */             if (method != null)
/* 445 */               return method; 
/*     */           }  
/*     */       } 
/*     */     } 
/* 449 */     return null;
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
/*     */   public static Method getMatchingAccessibleMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) {
/*     */     try {
/* 477 */       Method method = cls.getMethod(methodName, parameterTypes);
/* 478 */       MemberUtils.setAccessibleWorkaround(method);
/* 479 */       return method;
/* 480 */     } catch (NoSuchMethodException e) {
/*     */ 
/*     */       
/* 483 */       Method bestMatch = null;
/* 484 */       Method[] methods = cls.getMethods();
/* 485 */       for (Method method : methods) {
/*     */         
/* 487 */         if (method.getName().equals(methodName) && ClassUtils.isAssignable(parameterTypes, method.getParameterTypes(), true)) {
/*     */           
/* 489 */           Method accessibleMethod = getAccessibleMethod(method);
/* 490 */           if (accessibleMethod != null && (bestMatch == null || MemberUtils.compareParameterTypes(accessibleMethod.getParameterTypes(), bestMatch.getParameterTypes(), parameterTypes) < 0))
/*     */           {
/*     */ 
/*     */             
/* 494 */             bestMatch = accessibleMethod;
/*     */           }
/*     */         } 
/*     */       } 
/* 498 */       if (bestMatch != null) {
/* 499 */         MemberUtils.setAccessibleWorkaround(bestMatch);
/*     */       }
/* 501 */       return bestMatch;
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
/*     */   public static Set<Method> getOverrideHierarchy(Method method, ClassUtils.Interfaces interfacesBehavior) {
/* 513 */     Validate.notNull(method);
/* 514 */     Set<Method> result = new LinkedHashSet<Method>();
/* 515 */     result.add(method);
/*     */     
/* 517 */     Class<?>[] parameterTypes = method.getParameterTypes();
/*     */     
/* 519 */     Class<?> declaringClass = method.getDeclaringClass();
/*     */     
/* 521 */     Iterator<Class<?>> hierarchy = ClassUtils.hierarchy(declaringClass, interfacesBehavior).iterator();
/*     */     
/* 523 */     hierarchy.next();
/* 524 */     label21: while (hierarchy.hasNext()) {
/* 525 */       Class<?> c = hierarchy.next();
/* 526 */       Method m = getMatchingAccessibleMethod(c, method.getName(), parameterTypes);
/* 527 */       if (m == null) {
/*     */         continue;
/*     */       }
/* 530 */       if (Arrays.equals((Object[])m.getParameterTypes(), (Object[])parameterTypes)) {
/*     */         
/* 532 */         result.add(m);
/*     */         
/*     */         continue;
/*     */       } 
/* 536 */       Map<TypeVariable<?>, Type> typeArguments = TypeUtils.getTypeArguments(declaringClass, m.getDeclaringClass());
/* 537 */       for (int i = 0; i < parameterTypes.length; i++) {
/* 538 */         Type childType = TypeUtils.unrollVariables(typeArguments, method.getGenericParameterTypes()[i]);
/* 539 */         Type parentType = TypeUtils.unrollVariables(typeArguments, m.getGenericParameterTypes()[i]);
/* 540 */         if (!TypeUtils.equals(childType, parentType)) {
/*     */           continue label21;
/*     */         }
/*     */       } 
/* 544 */       result.add(m);
/*     */     } 
/* 546 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\luisc\Documents\VM Folder\launcher.jar!\org\apache\commons\lang3\reflect\MethodUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */