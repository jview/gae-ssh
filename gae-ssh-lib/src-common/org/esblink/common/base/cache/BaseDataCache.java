 package org.esblink.common.base.cache;
 
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
 
 @Service("baseDataCache")
 public class BaseDataCache extends BaseCache
 {
   BaseDataCacheEntry cacheObject = new BaseDataCacheEntry();
 
   protected final Log logger = LogFactory.getLog(BaseDataCache.class);
 
   long ttl = 60000L;
 
   public long getTtl()
   {
     return this.ttl;
   }
 
   public void setTtl(long ttl)
   {
     this.ttl = ttl;
   }
 
   public Object getData(Object key)
   {
     if (this.cacheProvider == null) {
       return null;
     }
 
     if ((this.cacheObject.getData() == null) || (this.cacheObject.isExpired(this.ttl)))
     {
       synchronized (this) {
         if ((this.cacheObject.getData() == null) || (this.cacheObject.isExpired(this.ttl))) {
           Object data = this.cacheProvider.getData(key);
           this.cacheObject.setData(data);
           this.cacheObject.setKey(key);
           this.cacheObject.setCreateTime(System.currentTimeMillis());
         }
       }
     }
     return this.cacheObject.getData();
   }
 
   public void refresh()
   {
     synchronized (this) {
       if (this.cacheObject.getData() != null) {
         Object data = this.cacheProvider.getData(this.cacheObject.getKey());
         this.cacheObject.setData(data);
         this.cacheObject.setCreateTime(System.currentTimeMillis());
       }
     }
   }
 
   public void removeData(Object key) {
     this.cacheObject.setData(null);
   }
 }


 
 
