 package org.esblink.jsptag.core.tags.taglib;
 
 import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
 
 public class BaseDataEl
 {
   static ICache cache = CacheManager.getInstance().getCache("_district_cache_region");
 
   public static final String getDistrictName(String dist_code)
   {
     if (cache == null)
       return "";
     return (String)cache.getData(dist_code);
   }
 
   public static final String getDistrictName(String dist_code, String defaultValue)
   {
     if (cache == null)
       return defaultValue;
     String distName = (String)cache.getData(dist_code);
     if ((null == distName) || ("".equals(distName)))
       distName = defaultValue;
     return distName;
   }
 }


 
 
