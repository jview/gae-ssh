package org.esblink.common.service.impl;
 
 public abstract class NamedEntity extends ModelEntity
 {
   private String name;
 
   public String getName()
   {
     return this.name;
   }
 
   public void setName(String name) {
     this.name = name;
   }
 }


 
 
