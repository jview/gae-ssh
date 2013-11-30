package org.esblink.common.base.context;
 
 import java.util.Locale;
import java.util.ResourceBundle;

import org.esblink.common.base.i18n.IMessageSource;
import org.esblink.common.base.i18n.MessageSource;
 
 public class ModuleContext extends BaseContext
 {
   private static ThreadLocal<ModuleContext> local = new ThreadLocal();
   private final Module module;
   private IMessageSource messageSource;
 
   public static ModuleContext getContext()
   {
     return (ModuleContext)local.get();
   }
 
   public static void init(Module module)
   {
     local.set(new ModuleContext(module));
   }
 
   private ModuleContext(Module module) {
     this.module = module;
   }
 
   public Module getModule()
   {
     return this.module;
   }
 
   public IMessageSource getMessageSource()
   {
     if (this.messageSource == null) {
       Locale locale = UserContext.getContext().getLocale();
       String baseName = getModule().getI18nBaseName();
       ResourceBundle resourceBundle = ResourceBundle.getBundle(baseName, locale, Thread.currentThread().getContextClassLoader());
       if (this.log.isDebugEnabled()) {
         this.log.debug("I18n Locale: " + locale);
         this.log.debug("I18n BaseName: " + baseName);
         this.log.debug("I18n ResourceBundle Locale: " + resourceBundle.getLocale());
       }
       this.messageSource = new MessageSource(resourceBundle, locale);
     }
     return this.messageSource;
   }
 }


 
 
