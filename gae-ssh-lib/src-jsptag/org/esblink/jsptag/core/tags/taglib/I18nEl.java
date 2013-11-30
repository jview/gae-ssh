 package org.esblink.jsptag.core.tags.taglib;
 
 import java.util.Locale;

import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.ModuleContext;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.i18n.IGlobalMessageSource;
import org.esblink.common.base.i18n.MessageSourceCache;
 
 public class I18nEl
 {
   static MessageSourceCache msCache = MessageSourceCache.getInstance();
 
   public static final String concat(String str1, String str2) {
     return str1 + str2;
   }
 
   public static final String i18n(String key)
   {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName ="loginmgmt";
     if(ModuleContext.getContext()!=null && ModuleContext.getContext().getModule()!=null){
    	 moduleName = ModuleContext.getContext().getModule().getName();
     }
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessage(key);
     }
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key);
     }
 
     return retVal;
   }
 
   public static final String i18n_arg1(String key, Object arg0) {
     return i18n_args(key, new Object[] { arg0 });
   }
 
   public static final String i18n_arg2(String key, Object arg0, Object arg1) {
     return i18n_args(key, new Object[] { arg0, arg1 });
   }
 
   public static final String i18n_arg3(String key, Object arg0, Object arg1, Object arg2) {
     return i18n_args(key, new Object[] { arg0, arg1, arg2 });
   }
 
   public static final String i18n_arg4(String key, Object arg0, Object arg1, Object arg2, Object arg3) {
     return i18n_args(key, new Object[] { arg0, arg1, arg2, arg3 });
   }
 
   public static final String i18n_func_def(String key, String propertyValue, String defaultValue) {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessageWithoutDefValue(key, propertyValue);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, propertyValue, defaultValue);
     }
 
     return retVal;
   }
 
   public static final String i18n_func(String key, String propertyValue) {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessageWithoutDefValue(key, propertyValue);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessageWithoutDefValue(key, propertyValue);
     }
 
     return retVal;
   }
 
   public static final String i18n_func_bln(String key, Boolean b) {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, b);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, b);
     }
 
     return retVal;
   }
 
   public static final String i18n_args(String key, Object[] args) {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, args);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, args);
     }
 
     return retVal;
   }
 
   public static final String i18n_def(String key, String defaultValue)
   {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName ="loginmgmt";
     if(ModuleContext.getContext()!=null && ModuleContext.getContext().getModule()!=null){
    	 moduleName = ModuleContext.getContext().getModule().getName();
     }
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessage(key);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, defaultValue);
     }
 
     return retVal;
   }
 
   public static final String i18n_def_arg1(String key, String defaultValue, Object arg0)
   {
     return i18n_def_args(key, defaultValue, new Object[] { arg0 });
   }
 
   public static final String i18n_def_arg2(String key, String defaultValue, Object arg0, Object arg1) {
     return i18n_def_args(key, defaultValue, new Object[] { arg0, arg1 });
   }
 
   public static final String i18n_def_arg3(String key, String defaultValue, Object arg0, Object arg1, Object arg2) {
     return i18n_def_args(key, defaultValue, new Object[] { arg0, arg1, arg2 });
   }
 
   public static final String i18n_def_arg4(String key, String defaultValue, Object arg0, Object arg1, Object arg2, Object arg3) {
     return i18n_def_args(key, defaultValue, new Object[] { arg0, arg1, arg2, arg3 });
   }
 
   public static final String i18n_def_args(String key, String defaultValue, Object[] args) {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
 
     IGlobalMessageSource msgRes = msCache.getModuleMs(locale, moduleName);
     String retVal = null;
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, args);
     }
 
     if (null != retVal) {
       return retVal;
     }
 
     msgRes = msCache.getGlobalMs(locale);
     if (msgRes != null) {
       retVal = msgRes.getMessage(key, args, defaultValue);
     }
 
     return retVal;
   }
 
   public static final String i18n_menu(String key) {
     return ApplicationContext.getContext().getMenuMessageSource().getMessage(key);
   }
 
   public static final String i18n_menu_def(String key, String defaultValue) {
     return ApplicationContext.getContext().getMenuMessageSource().getMessage(key, defaultValue);
   }
 }


 
 
