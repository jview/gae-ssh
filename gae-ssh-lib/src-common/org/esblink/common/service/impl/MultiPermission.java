package org.esblink.common.service.impl;
 
 import java.util.Collection;
 import java.util.Iterator;
 
 public class MultiPermission extends AbstractPermission
 {
   private static final long serialVersionUID = 1L;
   private final Collection<Permission> permissions;
 
   public MultiPermission(Collection<Permission> permissions)
   {
     this.permissions = permissions;
   }
 
   public boolean canAccess(Object resource) {
     for (Iterator iterator = this.permissions.iterator(); iterator.hasNext(); ) {
       Permission permission = (Permission)iterator.next();
       if (permission.canAccess(resource))
         return true;
     }
     return false;
   }
 }


 
 
