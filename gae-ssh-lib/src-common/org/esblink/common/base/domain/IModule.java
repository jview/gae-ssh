package org.esblink.common.base.domain;

import java.util.Collection;

public abstract interface IModule extends IResourceEntity
{
  public abstract IModule getParent();

  public abstract Collection<IModule> getChildren();

  public abstract void setChildren(Collection<IModule> paramCollection);

  public abstract String getName();

  public abstract String getDescription();

  public abstract ModuleType getType();

  public abstract ModuleApplyType getApplyType();

  public abstract String getIcon();

  public abstract String getUrl();

  public abstract Integer getSort();

  public abstract String getHelpUrl();
  
  public String getParamTypeId();
  public String getParamValue();
  public String getPermitValue();
}


 
 
