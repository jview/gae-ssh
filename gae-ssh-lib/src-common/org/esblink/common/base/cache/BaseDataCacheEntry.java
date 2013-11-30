 package org.esblink.common.base.cache;

import org.springframework.stereotype.Service;
 
 @Service("baseDataCacheEntry")
 public class BaseDataCacheEntry extends CacheEntry
 {
   long createTime = System.currentTimeMillis();
 
   public boolean isExpired(long ttl) {
     if (ttl < 0L)
       return false;
     return System.currentTimeMillis() - this.createTime > ttl;
   }
 
   public long getCreateTime()
   {
     return this.createTime;
   }
 
   public void setCreateTime(long createTime)
   {
     this.createTime = createTime;
   }
 }


 
 
