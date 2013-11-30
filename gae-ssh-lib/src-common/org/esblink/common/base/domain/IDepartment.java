package org.esblink.common.base.domain;

import org.esblink.common.base.IEntity;

public abstract interface IDepartment extends IEntity
{
  public abstract String getDeptCode();

  public abstract String getDeptName();

  public abstract String getParentCode();
}


 
 
