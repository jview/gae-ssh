package org.esblink.common.base.i18n;
 
 import org.esblink.common.base.cache.CacheManager;
 
 public class BaseCacheHelper
 {
   public static String getExpressType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_express_type_cache_region").getData(code);
   }
 
   public static String getDistanceType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_distance_type_cache_region").getData(code);
   }
 
   public static String getLimitType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_limit_type_cache_region").getData(code);
   }
 
   public static String getCargoType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_cargo_type_cache_region").getData(code);
   }
 
   public static String getTransportType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_transport_type_cache_region").getData(code);
   }
 
   public static String getDepartment(String code)
   {
     return (String)CacheManager.getInstance().getCache("_base_dept_cache_region").getData(code);
   }
 
   public static String getCurrencyType(String code)
   {
     return (String)CacheManager.getInstance().getCache("_currency_cache_region").getData(code);
   }
 
   public static String getBarOptCode(String code)
   {
     return (String)CacheManager.getInstance().getCache("_bar_opt_code_cache_region").getData(code);
   }
 
   public static String getBarStayWhyCode(String code)
   {
     return (String)CacheManager.getInstance().getCache("_bar_stay_cause_cache_region").getData(code);
   }
 
   public static String getDistrictName(String distCode)
   {
     return (String)CacheManager.getInstance().getCache("_district_cache_region").getData(distCode);
   }
 }


 
 
