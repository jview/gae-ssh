package org.esblink.common.base.domain;

import org.esblink.common.base.IEntity;

public abstract interface IBundle extends IEntity
{
  public abstract IModule getModule();

  public abstract String getVersion();

  public abstract String getFileName();

  public abstract String getName();

  public abstract String getDescription();

  public abstract String getDepend();

  public abstract boolean isBase();
}


 
 
