package org.esblink.common.base;

import java.io.Serializable;

public abstract interface IEntity extends Serializable, Comparable<Object>
{
  public abstract Long getId();

  public abstract String getCode();
}


 
 
