package org.esblink.common.base.i18n;
 
 import java.util.List;
import java.util.Locale;

import org.esblink.common.base.IEntity;
import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
 public class ApplicationResourceBundle
 {
   private static Logger log = LoggerFactory.getLogger(ApplicationResourceBundle.class);
 
   public static void requiredLocaleTranslate(IEntity iEntity, String tableName, String domain, String[] propertyNames)
   {
     if ("base".equals(tableName))
       _reqLocaleTranslate(iEntity, domain, propertyNames[0]);
     else
       _reqLocaleTranslate(iEntity, domain, propertyNames);
   }
 
   public static void requiredLocaleTranslateList(List<IEntity> iEntityList, String tableName, String domain, String[] propertyNames)
   {
     if ("base".equals(tableName))
       _reqLocaleTranslateList(iEntityList, domain, propertyNames[0]);
     else
       _reqLocaleTranslateList(iEntityList, domain, propertyNames);
   }
 
   public static void requiredLocaleTranslateObject(Object o, String tableName, String domain, String code, String[] propertyNames)
   {
     if ("base".equals(tableName))
       _reqLocaleTranslateObject(o, domain, code, propertyNames[0]);
     else
       _reqLocaleTranslateObject(o, domain, code, propertyNames);
   }
 
   public static void requiredLocaleTranslateObjects(List<?> o, String tableName, String domain, String code, String[] propertyNames)
   {
     if ("base".equals(tableName))
       _reqLocaleTranslateObjects(o, domain, code, propertyNames[0]);
     else
       _reqLocaleTranslateObjects(o, domain, code, propertyNames);
   }
 
   private static void _reqLocaleTranslate(IEntity iEntity, String domain, String propertyName)
   {
     _reqLocaleTranslateObject(iEntity, domain, "code", propertyName);
   }
 
   private static void _reqLocaleTranslateList(List<IEntity> iEntityList, String domain, String propertyName)
   {
     _reqLocaleTranslateObject(iEntityList, domain, "code", propertyName);
   }
 
   private static void _reqLocaleTranslateObject(Object o, String domain, String code, String propertyName)
   {
     if (null == o)
       return;
     if (!StringUtils.isNotEmpty(domain))
       return;
     if (!StringUtils.isNotEmpty(code))
       return;
     if (!StringUtils.isNotEmpty(propertyName))
       return;
     try
     {
       String code_n = StringUtils.initialString(code);
       String propertyValue = (String)o.getClass().getMethod("get" + code_n, new Class[0]).invoke(o, new Object[0]);
 
       Locale locale = UserContext.getContext().getLocale();
       String locale_str = locale.getLanguage() + (locale.getCountry() == null ? "" : new StringBuilder().append("_").append(locale.getCountry()).toString());
       locale_str = locale_str.toLowerCase();
       String key = locale_str + "#" + domain + "#" + propertyValue;
       String keyValue = (String)CacheManager.getInstance().getCache("_glossary_cache_region").getData(key);
 
       if (StringUtils.isNotEmpty(keyValue)) {
         String name = StringUtils.initialString(propertyName);
         o.getClass().getMethod("set" + name, new Class[] { String.class }).invoke(o, new Object[] { keyValue });
       }
     }
     catch (Exception e)
     {
     }
   }
 
   private static void _reqLocaleTranslateObject(Object o, String domain, String code, String[] propertyNames)
   {
     if (null == o)
       return;
     if (null == propertyNames)
       return;
     try {
       String code_n = StringUtils.initialString(code);
       String propertyValue = (String)o.getClass().getMethod("get" + code_n, new Class[0]).invoke(o, new Object[0]);
 
       ICache cache = CacheManager.getInstance().getCache("_glossary_cache_region");
       for (int i = 0; i < propertyNames.length; i++)
         try {
           Locale locale = UserContext.getContext().getLocale();
           String locale_str = locale.getLanguage() + (locale.getCountry() == null ? "" : new StringBuilder().append("_").append(locale.getCountry()).toString());
           locale_str = locale_str.toLowerCase();
           String key = locale_str + "#" + domain + "#" + propertyNames[i] + "#" + propertyValue;
 
           String keyValue = (String)cache.getData(key);
 
           if (StringUtils.isNotEmpty(keyValue)) {
             String propertyName = StringUtils.initialString(propertyNames[i]);
 
             o.getClass().getMethod("set" + propertyName, new Class[] { String.class }).invoke(o, new Object[] { keyValue });
           }
         }
         catch (Exception e)
         {
         }
     }
     catch (Exception e) {
       log.error(e.getMessage(), e);
     }
   }
 
   private static void _reqLocaleTranslateObjects(List<?> o, String domain, String code, String propertyName)
   {
     for (int i = 0; i < o.size(); i++)
       _reqLocaleTranslateObject(o.get(i), domain, code, propertyName);
   }
 
   private static void _reqLocaleTranslate(IEntity iEntity, String domain, String[] propertyNames)
   {
     _reqLocaleTranslateObject(iEntity, domain, "code", propertyNames);
   }
 
   private static void _reqLocaleTranslateList(List<IEntity> iEntityList, String domain, String[] propertyNames)
   {
     _reqLocaleTranslateObject(iEntityList, domain, "code", propertyNames);
   }
 
   private static void _reqLocaleTranslateObjects(List<?> o, String domain, String code, String[] propertyNames)
   {
     for (int i = 0; i < o.size(); i++)
       _reqLocaleTranslateObject(o.get(i), domain, code, propertyNames);
   }
 }


 
 
