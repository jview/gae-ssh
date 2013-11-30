package org.esblink.common.base.domain;
 
 import java.util.Collection;
import java.util.HashSet;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IRole;
 
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
 public abstract class BaseRole extends BaseEntity
   implements IRole
 {
   private static final long serialVersionUID = 5222519139696309145L;
   @Transient
   private Collection<Long> moduleIds = new HashSet();
 
   public final void addModule(Long moduleId) {
     if (null != moduleId)
       this.moduleIds.add(moduleId);
   }
 
   public final Collection<Long> getModuleIds() {
     return this.moduleIds;
   }
 
   public final boolean constainsModule(Long moduleId) {
     if (null == moduleId) {
       return true;
     }
     return this.moduleIds.contains(moduleId);
   }
 
   public final void setModuleIds(Collection<Long> modules) {
     for (Long moduleId : modules)
       this.moduleIds.add(moduleId);
   }
 
   public final void addModuleId(Long moduleId)
   {
     this.moduleIds.add(moduleId);
   }
 }


 
 
