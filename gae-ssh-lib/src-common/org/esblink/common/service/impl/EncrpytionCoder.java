package org.esblink.common.service.impl;
 
 public abstract class EncrpytionCoder extends AbstractCoder
 {
   private String key;
 
   public EncrpytionCoder()
   {
   }
 
   public EncrpytionCoder(String key)
   {
     this.key = key;
   }
 
   public final void setKey(String key)
   {
     this.key = key;
   }
 
   protected final String getKey() {
     return this.key;
   }
 }


 
 
