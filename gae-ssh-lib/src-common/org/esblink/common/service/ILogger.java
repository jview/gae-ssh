package org.esblink.common.service;

public abstract interface ILogger
{
  public abstract void debug(String paramString);

  public abstract void debug(String paramString, Throwable paramThrowable);

  public abstract void info(String paramString);

  public abstract void warn(String paramString);

  public abstract void warn(String paramString, Throwable paramThrowable);

  public abstract void error(String paramString);

  public abstract void error(String paramString, Throwable paramThrowable);

  public abstract boolean isDebugEnabled();

  public abstract boolean isInfoEnabled();

  public abstract boolean isWarnEnabled();

  public abstract boolean isErrorEnabled();

  public abstract boolean isFatalEnabled();
}


 
 
