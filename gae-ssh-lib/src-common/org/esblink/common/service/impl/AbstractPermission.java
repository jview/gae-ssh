package org.esblink.common.service.impl;
 
 import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
 
 public abstract class AbstractPermission
   implements Permission
 {
   public void checkSecurity(Object resource)
     throws SecurityException
   {
     if (!canAccess(resource))
       throw new WithoutPermissionException(resource);
   }
 
   public Collection<?> getAccessible(Collection<?> resources) {
     Collection accessibles = new HashSet();
     for (Iterator iterator = resources.iterator(); iterator.hasNext(); ) {
       Object resource = iterator.next();
       if (canAccess(resource))
         accessibles.add(resource);
     }
     return accessibles;
   }
 }


 
 
