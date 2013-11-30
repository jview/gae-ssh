 package org.esblink.common.base.cache;
 
 import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.stereotype.Service;
 
 @Service("cacheStore")
 public class CacheStore
   implements ICacheStore
 {
   private final Cache cache;
 
   public CacheStore(String name)
   {
     CacheManager manager = CacheManager.getInstance();
     Cache c = manager.getCache(name);
     if (c == null) {
       manager.addCache(name);
       c = manager.getCache(name);
     }
     this.cache = c;
     if (this.cache == null)
       throw new IllegalStateException("Can not create ehCache entity! Name[" + name + "]");
   }
 
   public Object getCacheData(Object key) {
     Element element = this.cache.get(key);
     if (element == null)
       return null;
     return element.getObjectValue();
   }
 
   public boolean isDataInCache(Object key) {
     return this.cache.isKeyInCache(key);
   }
 
   public void putCacheData(Object key, Object data) {
     this.cache.put(new Element(key, data));
   }
 
   public void shutdown()
   {
     this.cache.dispose();
   }
 
   public void removeCacheData(Object key) {
     if (this.cache != null)
       this.cache.remove(key);
   }
 }


 
 
