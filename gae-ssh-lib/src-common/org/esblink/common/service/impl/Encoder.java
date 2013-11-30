package org.esblink.common.service.impl;

public abstract interface Encoder
{
  public abstract char[] encode(byte[] paramArrayOfByte);

  public abstract String encode(String paramString);
}


 
 
