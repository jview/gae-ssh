package org.esblink.common.exception;


public abstract class BaseRuntimeException extends RuntimeException
  implements IException
{
  private final String key;
  private final Object[] args;
  private final int code;
  private final ExceptionLevel level;
  private final ExceptionSource source;

  public BaseRuntimeException(String key, int code, ExceptionLevel level, ExceptionSource source)
  {
    this.key = key;
    this.args = new Object[] { Integer.valueOf(code), level, source };
    this.code = code;
    this.level = level;
    this.source = source;
  }

  public BaseRuntimeException(String key, Object[] args, int code, ExceptionLevel level, ExceptionSource source) {
    this.key = key;
    this.args = args;
    this.code = code;
    this.level = level;
    this.source = source;
  }

  public String getMessage() {
    return this.key;
  }

  public String getMessageKey() {
    return this.key;
  }

  public Object[] getMessageArgs() {
    return this.args;
  }

  public int getCode() {
    return this.code;
  }

  public ExceptionLevel getLevel() {
    return this.level;
  }

  public ExceptionSource getSource() {
    return this.source;
  }
}




