 package org.esblink.common.base.cache;
 
 import java.util.Arrays;
 import java.util.Collection;
 import java.util.concurrent.ConcurrentHashMap;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class LRUBaseCache extends BaseCache
 {
   protected final Log logger = LogFactory.getLog(LRUBaseCache.class);
   ConcurrentHashMap<Object, LRUCacheEntry> cacheMap;
   int threshHold = 1000;
 
   int maxSize = 2 * this.threshHold;
 
   long ttl = 1800000L;
 
   boolean isRunning = true;
 
   long sleepTime = 300000L;
 
   public LRUBaseCache(int initialCapactiy, int concurrentLevel)
   {
     this.cacheMap = new ConcurrentHashMap(initialCapactiy, 0.75F, concurrentLevel);
 
     new Thread(new purgeJob()).start();
   }
 
   public int getThreshHold()
   {
     return this.threshHold;
   }
 
   public void setThreshHold(int threshHold) {
     this.threshHold = threshHold;
   }
 
   public int getMaxSize()
   {
     return this.maxSize;
   }
 
   public void setMaxSize(int maxSize) {
     this.maxSize = maxSize;
   }
 
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
 
     LRUCacheEntry cacheEntry = (LRUCacheEntry)this.cacheMap.get(key);
     if ((cacheEntry != null) && (!cacheEntry.isExpired(this.ttl))) {
       cacheEntry.setLastAccessTime(System.currentTimeMillis());
       return cacheEntry.getData();
     }
     if (this.cacheMap.size() >= this.maxSize)
       purgeCache();
     Object data = this.cacheProvider.getData(key);
     cacheEntry = new LRUCacheEntry();
     cacheEntry.setData(data);
     cacheEntry.setKey(key);
     this.cacheMap.put(key, cacheEntry);
     return data;
   }
 
   public void setData(Object key, Object data)
   {
     LRUCacheEntry cacheEntry = new LRUCacheEntry();
     cacheEntry.setData(data);
     cacheEntry.setKey(key);
     this.cacheMap.put(key, cacheEntry);
   }
 
   public void removeData(Object key) {
     this.cacheMap.remove(key);
   }
 
   synchronized void purgeCache()
   {
     int cacheSize = this.cacheMap.size();
     if (cacheSize > this.threshHold) {
       Object[] cacheValues = this.cacheMap.values().toArray();
       Arrays.sort(cacheValues);
       int purgeCount = cacheSize - this.threshHold;
       for (int i = 0; i < purgeCount; i++) {
         LRUCacheEntry cacheEntry = (LRUCacheEntry)cacheValues[i];
         this.cacheMap.remove(cacheEntry.getKey());
       }
     }
   }
 
   public void refresh()
   {
     Object[] cacheValues = this.cacheMap.values().toArray();
     for (Object obj : cacheValues) {
       LRUCacheEntry entry = (LRUCacheEntry)obj;
       if (entry.isExpired(this.ttl))
         this.cacheMap.remove(entry.getKey());
     }
   }
 
   public long getSleepTime()
   {
     return this.sleepTime;
   }
 
   public void setSleepTime(long sleepTime) {
     this.sleepTime = sleepTime;
   }
 
   public void shutdown()
   {
     this.isRunning = false;
     super.shutdown();
   }
 
   public boolean containsKey(Object key)
   {
     LRUCacheEntry cacheEntry = (LRUCacheEntry)this.cacheMap.get(key);
     return (cacheEntry != null) && (!cacheEntry.isExpired(this.ttl));
   }
 
   class purgeJob
     implements Runnable
   {
     purgeJob()
     {
     }
 
     public void run()
     {
       while (LRUBaseCache.this.isRunning) {
         LRUBaseCache.this.refresh();
         int cacheSize = LRUBaseCache.this.cacheMap.size();
         boolean needSleep = true;
         if (cacheSize > LRUBaseCache.this.threshHold) {
           LRUBaseCache.this.purgeCache();
           needSleep = false;
         }
         if (needSleep)
           try {
             Thread.sleep(LRUBaseCache.this.sleepTime);
           } catch (InterruptedException e) {
             LRUBaseCache.this.logger.error(e);
           }
       }
     }
   }
 }


 
 
