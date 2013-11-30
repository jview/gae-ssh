 package org.esblink.common.base.cache;

public abstract interface ICache
{
  public abstract String getCacheName();

  public abstract void setCacheName(String paramString);

  public abstract ICacheStore getCacheStore();

  public abstract void setCacheStore(ICacheStore paramICacheStore);

  public abstract ICacheDataProvider getCacheDataProvider();

  public abstract void setCacheProvider(ICacheDataProvider paramICacheDataProvider);

  public abstract boolean containsKey(Object paramObject);

  public abstract Object getData(Object paramObject);

  public abstract void setData(Object paramObject1, Object paramObject2);

  public abstract void removeData(Object paramObject);

  public abstract void refresh();

  public abstract void shutdown();
}


 
 
