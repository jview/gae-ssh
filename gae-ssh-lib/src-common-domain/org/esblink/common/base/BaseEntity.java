package org.esblink.common.base;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.esblink.common.base.IEntity;
 
@Entity
@MappedSuperclass
 public abstract class BaseEntity 
   implements IEntity
 {
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;
	 @Basic
   private String code;
 
   public int compareTo(Object o)
   {
     if (null == o)
       return 1;
     if (!IEntity.class.isAssignableFrom(o.getClass())) {
       return 1;
     }
     IEntity target = (IEntity)o;
     if (null == getId())
       return 1;
     if (null == target.getId())
       return -1;
     return getId().compareTo(target.getId());
   }
 
   public BaseEntity()
   {
   }
 
   public BaseEntity(Long id)
   {
     this.id = id;
   }
 
   public Long getId() {
     return this.id;
   }
 
   public void setId(Long id) {
     this.id = id;
   }
 
   public String getCode()
   {
     return this.code;
   }
 
   public void setCode(String code) {
     this.code = code;
   }
 
   public int hashCode()
   {
     int prime = 31;
     int result = 1;
     result = 31 * result + (this.id == null ? 0 : this.id.hashCode());
     return result;
   }
 
   public boolean equals(Object obj)
   {
     if (this == obj)
       return true;
     if (obj == null)
       return false;
     if (getClass() != obj.getClass())
       return false;
     BaseEntity other = (BaseEntity)obj;
     if ((this.id != null) && (other.id != null))
       return this.id.equals(other.id);
     return false;
   }
 
   public String toString()
   {
     return getClass().getName() + ":" + this.id;
   }
 }


 
 
