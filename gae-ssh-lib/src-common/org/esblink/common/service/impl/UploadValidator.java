package org.esblink.common.service.impl;

import java.io.File;
import java.io.IOException;

public abstract interface UploadValidator
{
  public abstract boolean isAccept(File paramFile)
    throws IOException;
}


 
 
