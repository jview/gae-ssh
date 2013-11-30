 package org.esblink.common.base.cache;
 
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public abstract class LRUBaseCacheDataProvider extends BaseCacheDataProvider
 {
   protected final Log logger = LogFactory.getLog(LRUBaseCacheDataProvider.class);
   long ttl;
   long sleepTime;
   int initialCapacity;
   int concurrencyLevel;
   int threshHold;
   int maxSize;
 
   protected ICache createCache()
   {
     if (this.initialCapacity <= 0) this.initialCapacity = 100;
     if (this.concurrencyLevel <= 0) this.concurrencyLevel = 100;
     LRUBaseCache cache = new LRUBaseCache(this.initialCapacity, this.concurrencyLevel);
     cache.setCacheName(this.cacheName);
     cache.setTtl(this.ttl * 60L * 1000L);
 
     if (this.sleepTime <= 0L) this.sleepTime = 5L;
     cache.setSleepTime(this.sleepTime * 60L * 1000L);
 
     if (this.threshHold <= 0) this.threshHold = 1000;
     if (this.maxSize < this.threshHold + 10) this.maxSize = (2 * this.threshHold);
     cache.setThreshHold(this.threshHold);
     cache.setMaxSize(this.maxSize);
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
 
   public long getSleepTime()
   {
     return this.sleepTime;
   }
 
   public void setSleepTime(long sleepTime)
   {
     this.sleepTime = sleepTime;
   }
 
   public int getMaxSize()
   {
     return this.maxSize;
   }
 
   public void setMaxSize(int maxSize)
   {
     this.maxSize = maxSize;
   }
 
   public int getInitialCapacity()
   {
     return this.initialCapacity;
   }
 
   public int getConcurrencyLevel()
   {
     return this.concurrencyLevel;
   }
 
   public int getThreshHold()
   {
     return this.threshHold;
   }
 
   public void setInitialCapacity(int initialCapacity)
   {
     this.initialCapacity = initialCapacity;
   }
 
   public void setConcurrencyLevel(int concurrencyLevel)
   {
     this.concurrencyLevel = concurrencyLevel;
   }
 
   public void setThreshHold(int threshHold)
   {
     this.threshHold = threshHold;
   }
 }


 
 
