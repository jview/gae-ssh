package org.esblink.common.service.impl;
 
 import java.util.Collection;
 import java.util.Collections;
 
 public class AnonymousPermission
   implements Permission
 {
   private static final long serialVersionUID = 1L;
   private static final Permission INSTANCE = new AnonymousPermission();
 
   public static final Permission getInstance()
   {
     return INSTANCE;
   }
 
   public void checkSecurity(Object resource) throws SecurityException {
     throw new NotLoginException();
   }
 
   public boolean canAccess(Object resource) {
     return false;
   }
 
   public Collection<?> getAccessible(Collection<?> resources) {
     return Collections.emptySet();
   }
 }


 
 
