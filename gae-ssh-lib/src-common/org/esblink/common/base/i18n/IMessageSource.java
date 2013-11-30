package org.esblink.common.base.i18n;

public abstract interface IMessageSource
{
  public abstract String getMessage(String paramString);

  public abstract String getMessage(String paramString, Object[] paramArrayOfObject);

  public abstract String getMessage(String paramString1, String paramString2);

  public abstract String getMessage(String paramString1, Object[] paramArrayOfObject, String paramString2);

  public abstract String getMessage(String paramString, Boolean paramBoolean);
}


 
 
