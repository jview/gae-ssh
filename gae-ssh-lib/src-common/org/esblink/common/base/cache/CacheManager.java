 package org.esblink.common.base.cache;
 
 import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
 
 @Service("cacheManager")
 public class CacheManager
 {
   static CacheManager singleton = new CacheManager();
 
   private Map<String, ICache> storages = new ConcurrentHashMap();
 
   public static CacheManager getInstance()
   {
     return singleton;
   }
 
   public ICache getCache(String name)
   {
     return (ICache)this.storages.get(name);
   }
 
   public void regCache(String name, ICache icache)
   {
     this.storages.put(name, icache);
   }
 }


 
 
