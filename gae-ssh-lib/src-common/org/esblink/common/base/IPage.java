package org.esblink.common.base;

import java.util.Collection;

public abstract interface IPage<T> extends Collection<T>
{
  public abstract long getTotalSize();

  public abstract int getPageSize();

  public abstract long getTotalPage();

  public abstract long getCurrentPage();

  public abstract boolean isFirstPage();

  public abstract boolean isLastPage();

  public abstract long getPageBegin();

  public abstract long getPageEnd();

  public abstract Collection<T> getData();
}


 
 
