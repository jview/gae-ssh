package org.esblink.common.service.impl;

import java.io.File;
import java.util.List;

public abstract interface Compresser
{
  public abstract void compress(List<File> paramList, File paramFile);

  public abstract List<File> decompress(File paramFile);

  public abstract void compress(File paramFile1, File paramFile2);

  public abstract void decompress(File paramFile1, File paramFile2);
}


 
 
