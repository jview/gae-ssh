package org.esblink.common.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

public abstract interface Serializer
{
  public abstract byte[] serialize(Serializable paramSerializable)
    throws IOException;

  public abstract Serializable deserialize(byte[] paramArrayOfByte)
    throws IOException;

  public abstract void serialize(Serializable paramSerializable, File paramFile)
    throws IOException;

  public abstract Serializable deserialize(File paramFile)
    throws IOException;

  public abstract void serialize(Serializable paramSerializable, OutputStream paramOutputStream)
    throws IOException;

  public abstract Serializable deserialize(InputStream paramInputStream)
    throws IOException;
}


 
 
