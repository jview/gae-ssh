package org.esblink.common.exception;


public abstract class SystemException extends BaseRuntimeException
{
  private static final long serialVersionUID = 1L;
  private final String originalMessage;

  public SystemException(int code, ExceptionLevel level, ExceptionSource source)
  {
    this("", code, level, source);
  }

  public SystemException(Throwable originalException, int code, ExceptionLevel level, ExceptionSource source)
  {
    this(originalException.getMessage(), code, level, source);
  }

  public SystemException(String originalMessage, int code, ExceptionLevel level, ExceptionSource source)
  {
    super(String.valueOf(code), code, level, source);
    this.originalMessage = originalMessage;
  }

  public String getOriginalMessage()
  {
    return this.originalMessage;
  }

  public String getMessage()
  {
    return getOriginalMessage();
  }
}




