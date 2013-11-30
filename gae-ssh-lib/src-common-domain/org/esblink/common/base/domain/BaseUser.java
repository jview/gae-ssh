package org.esblink.common.base.domain;
 
 import java.util.ArrayList;
import java.util.List;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.IUser;
 
 public abstract class BaseUser extends BaseEntity
   implements IUser
 {
   private static final long serialVersionUID = -4215136201976770116L;
   private List<IModule> authModules = new ArrayList();
 
   public final List<IModule> getAuthModules() {
     return this.authModules;
   }
 
   public List<Long> getAuthDepartmentIds()
   {
     return null;
   }
 
   public final void setAuthModules(List<IModule> modules) {
     this.authModules = modules;
   }
 
   public List<Long> getRoleIdList()
   {
     return null;
   }
 }


 
 
