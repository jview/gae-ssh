package org.esblink.common.service.impl;
 
 public class NotLoginException extends SecurityException
 {
   private static final long serialVersionUID = 1L;
 
   public NotLoginException()
   {
   }
 
   public NotLoginException(String message, Throwable cause)
   {
     super(message, cause);
   }
 
   public NotLoginException(String message) {
     super(message);
   }
 
   public NotLoginException(Throwable cause) {
     super(cause);
   }
 }


 
 
