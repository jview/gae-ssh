package org.esblink.common.base.domain;

import org.esblink.common.base.IEntity;

public abstract interface IEmployee extends IEntity
{
  public abstract String getName();

  public abstract String getCode();

  public abstract IDepartment getDepartment();
}


 
 
