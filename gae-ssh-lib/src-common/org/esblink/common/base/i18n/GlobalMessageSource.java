package org.esblink.common.base.i18n;
 
 import java.text.MessageFormat;
 import java.util.Locale;
 import java.util.MissingResourceException;
 import java.util.ResourceBundle;
 
 public class GlobalMessageSource
   implements IGlobalMessageSource
 {
   private final ResourceBundle resourceBundle;
   private final Locale locale;
 
   public GlobalMessageSource(ResourceBundle resourceBundle, Locale locale)
   {
     this.resourceBundle = resourceBundle;
     this.locale = locale;
   }
 
   public String getMessage(String key)
   {
     String retVal = null;
     try {
       retVal = this.resourceBundle.getString(key);
     } catch (MissingResourceException e) {
       retVal = null;
     }
     return retVal;
   }
 
   public String getMessage(String key, Object[] args)
   {
     return formatMessage(getMessage(key), args);
   }
 
   public String getMessage(String key, String defaultValue)
   {
     if ((this.resourceBundle == null) || (key == null))
       return defaultValue;
     String msg;
     try {
       msg = this.resourceBundle.getString(key);
     } catch (MissingResourceException e) {
       msg = null;
     }
     if (msg == null)
       return defaultValue;
     return msg;
   }
 
   public String getMessage(String key, Object[] args, String defaultValue)
   {
     return formatMessage(getMessage(key, defaultValue), args);
   }
 
   public String getMessage(String key, String propertyValue, String defaultValue)
   {
     String k = key + "." + propertyValue;
     return getMessage(k, defaultValue);
   }
 
   public String getMessageWithoutDefValue(String key, String propertyValue)
   {
     String k = key + "." + propertyValue;
     return getMessage(k, "");
   }
 
   public String getMessage(String key, Boolean b)
   {
     String k = key + "." + "false";
     if (b.booleanValue()) {
       k = key + "." + "true";
     }
     return getMessage(k);
   }
 
   private final String formatMessage(String msg, Object[] args)
   {
     if ((msg != null) && (msg.length() > 0) && (args != null) && (args.length > 0))
     {
       return new MessageFormat(msg, this.locale).format(args);
     }
     return msg;
   }
 }


 
 
