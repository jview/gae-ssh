package org.esblink.common.base.domain;
 
 import java.util.Collection;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IModule;
 
 public abstract class BaseModule extends BaseEntity
   implements IModule
 {
   private Collection<IModule> children;
 
   public final Collection<IModule> getChildren()
   {
     return this.children;
   }
 
   public final void setChildren(Collection<IModule> children) {
     this.children = children;
   }
 }


 
 
