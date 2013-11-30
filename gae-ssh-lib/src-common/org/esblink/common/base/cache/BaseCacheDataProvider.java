 package org.esblink.common.base.cache;
 
 import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
 
 @Service("baseCacheDataProvider")
 public abstract class BaseCacheDataProvider
   implements ICacheDataProvider, InitializingBean, DisposableBean
 {
   protected final Log logger = LogFactory.getLog(BaseCacheDataProvider.class);
   String cacheName;
   @Resource(name="cache")
   protected ICache cache = null;
 
   public String getCacheName()
   {
     return this.cacheName;
   }
 
   public void setCacheName(String cacheName)
   {
     this.cacheName = cacheName;
   }
 
   public void afterPropertiesSet()
     throws Exception
   {
     ICache cache = initializeCache();
     if (cache != null)
       CacheManager.getInstance().regCache(cache.getCacheName(), cache);
   }
 
   protected ICache initializeCache()
   {
     ICache cache = getCache();
     if (cache == null) {
       cache = createCache();
       cache.setCacheProvider(this);
       setCache(cache);
     }
     return cache;
   }
 
   protected ICache createCache()
   {
     ICache cache = new BaseCache();
     cache.setCacheName(this.cacheName);
     CacheStore store = new CacheStore(this.cacheName);
     cache.setCacheStore(store);
     return cache;
   }
 
   protected ICache getCache()
   {
     return this.cache;
   }
 
   protected void setCache(ICache cache)
   {
     this.cache = cache;
   }
 
   public void destroy() throws Exception
   {
     ICache cache = getCache();
     if (cache != null) cache.shutdown();
   }
 }


 
 
