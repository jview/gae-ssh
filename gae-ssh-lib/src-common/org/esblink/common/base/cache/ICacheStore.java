 package org.esblink.common.base.cache;

public abstract interface ICacheStore
{
  public abstract Object getCacheData(Object paramObject);

  public abstract void removeCacheData(Object paramObject);

  public abstract void putCacheData(Object paramObject1, Object paramObject2);

  public abstract boolean isDataInCache(Object paramObject);

  public abstract void shutdown();
}


 
 
