package org.esblink.common.service.impl;

public abstract interface SecurityContext
{
  public abstract LoginLevel getLoginLevel();

  public abstract Object getIdentity();

  public abstract Permission getPermission();

  public abstract Permission getPermission(String paramString);
}


 
 
