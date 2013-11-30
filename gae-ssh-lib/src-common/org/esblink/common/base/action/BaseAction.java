package org.esblink.common.base.action;
 
 import java.util.List;
import java.util.Locale;

import org.esblink.common.base.IEntity;
import org.esblink.common.base.context.ModuleContext;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.i18n.ApplicationResourceBundle;
import org.esblink.common.base.i18n.IGlobalMessageSource;
import org.esblink.common.base.i18n.MessageSourceCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;
 
 public abstract class BaseAction extends ActionSupport
 {
   private static final long serialVersionUID = -6733216227546854659L;
   public static final String FAILURE = "failure";
   protected Logger log = LoggerFactory.getLogger(getClass());
   private Long id;
 
   public Locale getLocale()
   {
     return UserContext.getContext().getLocale();
   }
 
   protected IGlobalMessageSource getMessageSource()
   {
     Locale locale = UserContext.getContext().getLocale();
     String moduleName = ModuleContext.getContext().getModule().getName();
     return MessageSourceCache.getInstance().getModuleMs(locale, moduleName);
   }
 
   /** @deprecated
    * 
    * @param iEntity
    * @param tableName
    * @param domain
    * @param propertyNames
    */
   public void requiredLocaleTranslateForIEntity(IEntity iEntity, String tableName, String domain, String[] propertyNames)
   {
     ApplicationResourceBundle.requiredLocaleTranslate(iEntity, tableName, domain, propertyNames);
   }
 
   /** @deprecated
    * 
    * @param iEntityList
    * @param tableName
    * @param domain
    * @param propertyNames
    */
   public void requiredLocaleTranslateForIEntity(List<IEntity> iEntityList, String tableName, String domain, String[] propertyNames)
   {
     ApplicationResourceBundle.requiredLocaleTranslateList(iEntityList, tableName, domain, propertyNames);
   }
 
   /** @deprecated
    * 
    * @param o
    * @param tableName
    * @param domain
    * @param code
    * @param propertyNames
    */
   public void requiredLocaleTranslateForObject(Object o, String tableName, String domain, String code, String[] propertyNames)
   {
     ApplicationResourceBundle.requiredLocaleTranslateObject(o, tableName, domain, code, propertyNames);
   }
 
   /** @deprecated
    * 
    * @param o
    * @param tableName
    * @param domain
    * @param code
    * @param propertyNames
    */
   public void requiredLocaleTranslateForObjects(List<?> o, String tableName, String domain, String code, String[] propertyNames)
   {
     ApplicationResourceBundle.requiredLocaleTranslateObjects(o, tableName, domain, code, propertyNames);
   }
 
   protected Object getUserCached(Long userId, Long roleId)
   {
     return UserContext.getContext().getUserCached(userId, roleId);
   }
 
   protected IUser getCurrentUser()
   {
     return UserContext.getContext().getCurrentUser();
   }
 
   public Long getId()
   {
     return this.id;
   }
 
   public void setId(Long id) {
     this.id = id;
   }
 }


 
 
