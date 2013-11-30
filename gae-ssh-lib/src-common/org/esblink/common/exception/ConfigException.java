package org.esblink.common.exception;


public class ConfigException extends FrameworkException
{
  private static final long serialVersionUID = 6222182975130049859L;

  public ConfigException(String originalMessage)
  {
    super(originalMessage, 99903001);
  }
}




