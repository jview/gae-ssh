package org.esblink.common.base.domain;

import java.util.Collection;

import org.esblink.common.base.IEntity;

public abstract interface IRole extends IEntity
{
  public abstract String getName();

  public abstract String getDescription();

  public abstract Collection<Long> getModuleIds();

  public abstract boolean constainsModule(Long paramLong);

  public abstract void addModule(Long paramLong);

  public abstract void setModuleIds(Collection<Long> paramCollection);
}


 
 
