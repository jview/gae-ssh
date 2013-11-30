package org.esblink.common.service.impl;
 
 import java.util.Collection;
import java.util.Collections;
 
 public class AccessiblePermission extends AbstractPermission
 {
   private static final long serialVersionUID = 1L;
   private final Collection<?> resources;
 
   public AccessiblePermission(Collection<?> resources)
   {
     if (resources == null)
       this.resources = Collections.emptySet();
     else
       this.resources = resources;
   }
 
   public boolean canAccess(Object resource) {
     return this.resources.contains(resource);
   }
 }


 
 
