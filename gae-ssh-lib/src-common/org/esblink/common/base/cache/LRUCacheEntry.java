 package org.esblink.common.base.cache;
 
 public class LRUCacheEntry extends CacheEntry
   implements Comparable<LRUCacheEntry>
 {
   long lastAccessTime = System.currentTimeMillis();
 
   public long getLastAccessTime() {
     return this.lastAccessTime;
   }
 
   public void setLastAccessTime(long time) {
     this.lastAccessTime = time;
   }
 
   public boolean isExpired(long ttl) {
     if (ttl < 0L)
       return false;
     return System.currentTimeMillis() - this.lastAccessTime > ttl;
   }
 
   public int compareTo(LRUCacheEntry o) {
     if (this.lastAccessTime > o.getLastAccessTime())
       return 1;
     if (this.lastAccessTime < o.getLastAccessTime())
       return -1;
     return 0;
   }
 }


 
 
