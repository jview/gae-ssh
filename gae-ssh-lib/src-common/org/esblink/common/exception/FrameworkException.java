package org.esblink.common.exception;


public abstract class FrameworkException extends SystemException
{
  private static final long serialVersionUID = -3066975138398772502L;

  public FrameworkException(int code)
  {
    super(code, ExceptionLevel.NORMAL, ExceptionSource.FRAMEWORK);
  }

  public FrameworkException(String originalMessage, int code)
  {
    super(originalMessage, code, ExceptionLevel.NORMAL, ExceptionSource.FRAMEWORK);
  }

  public FrameworkException(Throwable originalException, int code)
  {
    super(originalException, code, ExceptionLevel.NORMAL, ExceptionSource.FRAMEWORK);
  }

  public FrameworkException(int code, ExceptionLevel level)
  {
    super(code, level, ExceptionSource.FRAMEWORK);
  }

  public FrameworkException(String originalMessage, int code, ExceptionLevel level)
  {
    super(originalMessage, code, level, ExceptionSource.FRAMEWORK);
  }

  public FrameworkException(Throwable originalException, int code, ExceptionLevel level)
  {
    super(originalException, code, level, ExceptionSource.FRAMEWORK);
  }
}




