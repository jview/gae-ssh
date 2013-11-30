package org.esblink.common.service.impl;
 
 public abstract class BaseException extends RuntimeException
 {
   protected String model;
 
   public BaseException()
   {
   }
 
   public BaseException(String message)
   {
     super(message);
   }
 
   public BaseException(String message, Throwable cause) {
     super(message, cause);
   }
 
   public BaseException(Throwable cause) {
     super(cause);
   }
 
   public String getModel() {
     return this.model;
   }
 
   public void setModel(String model) {
     this.model = model;
   }
 }


 
 
