package org.esblink.common.service.impl;

import java.util.Collection;

public abstract interface Permission
{
  public abstract void checkSecurity(Object paramObject)
    throws SecurityException;

  public abstract boolean canAccess(Object paramObject);

  public abstract Collection<?> getAccessible(Collection<?> paramCollection);
}


 
 
