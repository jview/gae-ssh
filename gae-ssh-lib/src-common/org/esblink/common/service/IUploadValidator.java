package org.esblink.common.service;

import java.io.File;
import java.io.IOException;

public abstract interface IUploadValidator
{
  public abstract boolean isAccept(File paramFile)
    throws IOException;
}


 
 
