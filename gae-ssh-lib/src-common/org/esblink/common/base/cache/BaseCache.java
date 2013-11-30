 package org.esblink.common.base.cache;
 
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
 
 @Service("baseCache")
 public class BaseCache
   implements ICache
 {
   String name;
   ICacheStore cacheStore;
   ICacheDataProvider cacheProvider;
   protected final Log logger = LogFactory.getLog(BaseCache.class);
 
   public String getCacheName() {
     return this.name;
   }
 
   public void setCacheName(String name) {
     this.name = name;
   }
 
   public ICacheStore getCacheStore() {
     return this.cacheStore;
   }
 
   public void setCacheStore(ICacheStore cacheStore) {
     this.cacheStore = cacheStore;
   }
 
   public ICacheDataProvider getCacheDataProvider() {
     return this.cacheProvider;
   }
 
   public void setCacheProvider(ICacheDataProvider dataProvider) {
     this.cacheProvider = dataProvider;
   }
 
   public Object getData(Object key)
   {
     if (this.cacheProvider == null) {
       return null;
     }
 
     Object data = this.cacheStore.getCacheData(key);
     if (data == null) {
       data = this.cacheProvider.getData(key);
       this.cacheStore.putCacheData(key, data);
     }
     return data;
   }
 
   public void setData(Object key, Object data) {
     this.cacheStore.putCacheData(key, data);
   }
 
   public void refresh()
   {
   }
 
   public void shutdown() {
     if (this.cacheStore != null)
       this.cacheStore.shutdown();
   }
 
   public void removeData(Object key) {
     if (this.cacheStore != null)
       this.cacheStore.removeCacheData(key);
   }
 
   public boolean containsKey(Object key)
   {
     if (this.cacheStore != null) {
       Object data = this.cacheStore.getCacheData(key);
       return data != null;
     }
     return false;
   }
 }


 
 
