 package org.esblink.common.base.cache;
 
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
 
 @Service("baseDataCacheDataProvider")
 public abstract class BaseDataCacheDataProvider extends BaseCacheDataProvider
 {
   protected final Log logger = LogFactory.getLog(BaseDataCacheDataProvider.class);
   long ttl;
 
   protected ICache createCache()
   {
     BaseDataCache cache = new BaseDataCache();
     cache.setCacheName(this.cacheName);
     cache.setTtl(this.ttl * 60L * 1000L);
     return cache;
   }
 
   public long getTtl()
   {
     return this.ttl;
   }
 
   public void setTtl(long ttl)
   {
     this.ttl = ttl;
   }
 }


 
 
