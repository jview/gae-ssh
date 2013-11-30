package org.esblink.common.service.impl;
 
 import java.io.Serializable;
 
 public abstract class ModelEntity
   implements Serializable
 {
   private Long id;
 
   public ModelEntity()
   {
   }
 
   public ModelEntity(Long id)
   {
     this.id = id;
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setId(Long id)
   {
     this.id = id;
   }
 
   public boolean isNew()
   {
     return (this.id == null) || (this.id.longValue() <= 0L);
   }
 
   public String toString()
   {
     return "";
   }
 
   public boolean equals(ModelEntity o)
   {
     return false;
   }
 }


 
 
