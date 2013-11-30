package org.esblink.common.base.i18n;
 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

//import org.esblink.common.base.context.Application;
//import org.esblink.common.base.context.ApplicationContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.context.Application;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.Module;
 
 public class MessageSourceCache
 {
   public static final String GLOBAL_MESSAGE_BASE_NAME = "/WEB-INF/i18n/";
   static MessageSourceCache messageSourceCache = new MessageSourceCache();
 
   protected final Log logger = LogFactory.getLog(MessageSourceCache.class);
 
   String globalMsPath = "";
 
   ResourceBundle defaultRs = null;
 
   HashMap<Locale, IGlobalMessageSource> globalMs = new HashMap();
 
   HashSet<Locale> missedGlobalMs = new HashSet(0);
 
   HashMap<String, HashMap<Locale, IGlobalMessageSource>> moduleMs = new HashMap();
 
   public static MessageSourceCache getInstance()
   {
     return messageSourceCache;
   }
 
   public String getGlobalMsPath()
   {
     return this.globalMsPath;
   }
 
   public void setGlobalMsPath(String globalMsPath)
   {
     try
     {
       File f = new File(globalMsPath);
       if (!f.exists()) {
    	   logger.error("globalMsPath="+globalMsPath+" not found!");
         return;
       }
       this.globalMsPath = (globalMsPath + "/messages");
       this.defaultRs = new PropertyResourceBundle(new FileInputStream(this.globalMsPath + ".properties"));
     }
     catch (FileNotFoundException e) {
       if (this.logger.isDebugEnabled())
         this.logger.warn("Global resource file: Can't load default global i18n resource file:" + globalMsPath);
     }
     catch (IOException e)
     {
       if (this.logger.isDebugEnabled())
         this.logger.warn("Global resource file: Can't load default global i18n resource file" + globalMsPath);
     }
   }
 
   public IGlobalMessageSource getGlobalMs(Locale locale)
   {
     if ((null == this.globalMsPath) || ("".equals(this.globalMsPath))) {
       return null;
     }
     if (this.missedGlobalMs.contains(locale)) {
       return null;
     }
     IGlobalMessageSource msgsource = (IGlobalMessageSource)this.globalMs.get(locale);
     if (msgsource == null) {
       synchronized (this.globalMs) {
         msgsource = (IGlobalMessageSource)this.globalMs.get(locale);
         if (msgsource == null) {
           String baseName = this.globalMsPath + "_" + locale.getLanguage();
           if ((null != locale.getCountry()) && (!"".equals(locale.getCountry())))
           {
             baseName = baseName + "_" + locale.getCountry();
           }
           baseName = baseName + ".properties";
           try
           {
             ResourceBundle rs = new WebResourceBundle(baseName, this.defaultRs);
 
             msgsource = new GlobalMessageSource(rs, locale);
             this.globalMs.put(locale, msgsource);
           } catch (Exception e) {
             if (this.logger.isDebugEnabled()) {
               this.logger.debug("Can't find bundle for base name:" + baseName);
             }
           }
         }
       }
     }
 
     if (msgsource == null) {
       this.missedGlobalMs.add(locale);
     }
     return msgsource;
   }
 
   boolean existsMessageBundleFile(String path) {
     File tmpFile = new File(path);
     boolean exists = tmpFile.exists();
     tmpFile = null;
     return exists;
   }
 
   public IGlobalMessageSource getModuleMs(Locale locale, String moduleName)
   {
     HashMap sourceMap = (HashMap)this.moduleMs.get(moduleName);
 
     if (sourceMap == null) {
       synchronized (this.moduleMs) {
         sourceMap = (HashMap)this.moduleMs.get(moduleName);
         if (sourceMap == null) {
           sourceMap = new HashMap();
           this.moduleMs.put(moduleName, sourceMap);
         }
       }
     }
 
     IGlobalMessageSource msgsource = (IGlobalMessageSource)sourceMap.get(locale);
     if (msgsource == null) {
       synchronized (sourceMap) {
         msgsource = (IGlobalMessageSource)sourceMap.get(locale);
         if (msgsource == null)
         {
           Application app = ApplicationContext.getContext().getApplication();
 
           if (null == app) {
             this.logger.debug("Can not find the applicatin with module name [" + moduleName + "]");
 
             return null;
           }
 
           Module module = app.getModule(moduleName);
           if (null == module) {
             this.logger.debug("The Module [" + moduleName + "] is not be registed.");
 
             return null;
           }
        	 
           String baseName = module.getI18nBaseName();
//        	 String baseName=moduleName;
           try {
             ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
 
             msgsource = new GlobalMessageSource(resourceBundle, locale);
 
             sourceMap.put(locale, msgsource);
           } catch (MissingResourceException e) {
             if (this.logger.isDebugEnabled()) {
               this.logger.debug("Can't find bundle for base name:" + baseName);
             }
           }
         }
       }
     }
     return msgsource;
   }
 }


 
 
