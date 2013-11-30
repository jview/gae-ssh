package org.esblink.common.service.impl;
 
 public class WithoutPermissionException extends SecurityException
 {
   private static final long serialVersionUID = 1L;
   private final Object resource;
 
   public WithoutPermissionException(Object resource)
   {
     this.resource = resource;
   }
 
   public WithoutPermissionException(Object resource, String message, Throwable cause) {
     super(message, cause);
     this.resource = resource;
   }
 
   public WithoutPermissionException(Object resource, String message) {
     super(message);
     this.resource = resource;
   }
 
   public WithoutPermissionException(Object resource, Throwable cause) {
     super(cause);
     this.resource = resource;
   }
 
   public Object getResource()
   {
     return this.resource;
   }
 }


 
 
