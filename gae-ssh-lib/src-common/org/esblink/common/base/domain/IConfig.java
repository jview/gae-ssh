package org.esblink.common.base.domain;

import org.esblink.common.base.IEntity;

public abstract interface IConfig extends IEntity
{
  public abstract IUser getUser();

  public abstract String getDomain();

  public abstract String getPropertyName();

  public abstract String getPropertyValue();
}


 
 
