package org.esblink.common.service.impl;

import java.io.File;
import java.io.IOException;

public abstract interface FileManager
{
  public abstract boolean isFileExist(String paramString)
    throws IOException;

  public abstract File getFile(String paramString)
    throws IOException;

  public abstract File createFile(String paramString)
    throws IOException;

  public abstract void deleteFile(String paramString)
    throws IOException;

  public abstract boolean isDirectoryExist(String paramString)
    throws IOException;

  public abstract File getDirectory(String paramString)
    throws IOException;

  public abstract File createDirectory(String paramString)
    throws IOException;

  public abstract void deleteDirectory(String paramString)
    throws IOException;
}


 
 
