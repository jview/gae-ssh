package org.esblink.common.service;

public abstract interface IEncoder
{
  public abstract char[] encode(byte[] paramArrayOfByte);

  public abstract String encode(String paramString);
}


 
 
