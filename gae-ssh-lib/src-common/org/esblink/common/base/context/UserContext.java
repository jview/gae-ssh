package org.esblink.common.base.context;
 
 import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.IRole;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.ModuleApplyType;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.security.ModuleContext;

import com.opensymphony.xwork2.ActionContext;
 
 public class UserContext extends BaseContext
 {
   public static final String USER_ID_KEY = "__com.idplink.server.core.USER_ID_KEY__";
   public static final String USER_ROLE_KEY = "__com.idplink.server.core.USER_ROLE_KEY__";
   public static final String LOCALE_KEY = "__com.idplink.server.core.LOCALE_KEY__";
   public static final String STYLE_KEY = "__com.idplink.server.core.STYLE_KEY__";
   public static final String OUT_USER_ID_KEY = "TOKEN_USER";
   private static UserContext context = new UserContext();
 
   private static final Log logger = LogFactory.getLog(UserContext.class);
 
   public static UserContext getContext()
   {
     return context;
   }
 
   private ICache getUserCache()
   {
     return CacheManager.getInstance().getCache("_user_cache_region");
   }
 
   public Object getUserCached(Long userId, Long roleId)
   {
     return getUserCache().getData(userId + "_" + (roleId == null ? "" : roleId));
   }
 
   public IUser getCurrentUser()
   {
     Long userId = getContext().getUserId();
     Long roleId = getContext().getRoleId();
     if (null == userId) {
       this.log.debug(" Current Session Not Found UserID ");
       return null;
     }
 
     IUser user = (IUser)getUserCached(userId, roleId);
     if (null == user) {
       this.log.debug(" can not found User from Cache by  UserID [ " + userId + "]");
     }
 
     return user;
   }
 
   private Long getUserId()
   {
     return (Long)ServletActionContext.getRequest().getSession().getAttribute("__com.idplink.server.core.USER_ID_KEY__");
   }
 
   private Long getRoleId()
   {
     return (Long)ServletActionContext.getRequest().getSession().getAttribute("__com.idplink.server.core.USER_ROLE_KEY__");
   }
 
   public String getOutUserId()
   {
     return (String)ServletActionContext.getRequest().getSession().getAttribute("TOKEN_USER");
   }
 
   public void setUserIdAndRoleId(Long id, Long roleId)
   {
     ServletActionContext.getRequest().getSession().setAttribute("__com.idplink.server.core.USER_ID_KEY__", id);
 
     ServletActionContext.getRequest().getSession().setAttribute("__com.idplink.server.core.USER_ROLE_KEY__", roleId);
   }
 
   public void setRoleId(long roleId)
   {
     ServletActionContext.getRequest().getSession().setAttribute("__com.idplink.server.core.USER_ROLE_KEY__", Long.valueOf(roleId));
   }
 
   public void removeUserIdAndRoleId()
   {
     Long userId = getUserId();
     Long roleId = getRoleId();
     try {
       if (userId != null) {
         ServletActionContext.getRequest().getSession().setAttribute("__com.idplink.server.core.USER_ID_KEY__", null);
       }
 
       if (roleId != null)
         ServletActionContext.getRequest().getSession().setAttribute("__com.idplink.server.core.USER_ROLE_KEY__", null);
     }
     catch (Exception e)
     {
       logger.error("Error happen when removeUserIdAndRoleId[" + userId.toString() + "," + roleId.toString() + "]", e);
     }
   }
 
   public String getUserStyle()
   {
     String style = (String)ActionContext.getContext().getSession().get("__com.idplink.server.core.STYLE_KEY__");
     if (style == null) {
       Cookie[] cookies = ServletActionContext.getRequest().getCookies();
       if (cookies != null) {
         for (Cookie cookie : cookies) {
           if ((cookie != null) && ("style".equalsIgnoreCase(cookie.getName())) && (cookie.getValue() != null) && (cookie.getValue().length() > 0)) {
             ActionContext.getContext().getSession().put("__com.idplink.server.core.STYLE_KEY__", cookie.getValue());
             break;
           }
         }
       }
     }
     return style;
   }
 
   public void setUserStyle(String style) {
     if (isChange(getUserStyle(), style)) {
       ActionContext.getContext().getSession().put("__com.idplink.server.core.STYLE_KEY__", style);
       Cookie cookie = new Cookie("style", style);
       cookie.setMaxAge(60000);
       cookie.setPath("/");
       ServletActionContext.getResponse().addCookie(cookie);
     }
   }
 
   public Locale getUserLocale()
   {
     Locale locale = (Locale)ActionContext.getContext().getSession().get("__com.idplink.server.core.LOCALE_KEY__");
 
     if (locale == null) {
       Cookie[] cookies = ServletActionContext.getRequest().getCookies();
       if (cookies != null) {
         for (Cookie cookie : cookies) {
           if ((cookie != null) && ("locale".equalsIgnoreCase(cookie.getName())) && (cookie.getValue() != null) && (cookie.getValue().length() > 0))
           {
             String[] tokens = cookie.getValue().split("_");
             if (tokens != null) {
               if (tokens.length >= 3) {
                 locale = new Locale(tokens[0], tokens[1], tokens[2]);
               }
               else if (tokens.length >= 2)
                 locale = new Locale(tokens[0], tokens[1]);
               else if (tokens.length >= 1) {
                 locale = new Locale(tokens[0]);
               }
             }
             ActionContext.getContext().getSession().put("__com.idplink.server.core.LOCALE_KEY__", locale);
 
             break;
           }
         }
       }
     }
     return locale;
   }
 
   public void setUserLocale(Locale locale)
   {
     if (isChange(getUserLocale(), locale)) {
       ActionContext.getContext().getSession().put("__com.idplink.server.core.LOCALE_KEY__", locale);
       Cookie cookie = new Cookie("locale", locale.toString());
       cookie.setMaxAge(60000);
       cookie.setPath("/");
       ServletActionContext.getResponse().addCookie(cookie);
     }
   }
 
   public Locale getLocale() {
     Locale locale = getUserLocale();
     if (locale != null)
       return locale;
     locale = ServletActionContext.getRequest().getLocale();
     if (locale != null)
       return locale;
     return Locale.getDefault();
   }
 
   private boolean isChange(Object oldObj, Object newObj)
   {
     if ((oldObj == null) && (newObj == null))
       return false;
     if ((oldObj == null) || (newObj == null))
       return true;
     return !oldObj.equals(newObj);
   }
 
   public List<IModule> getUserPrivilege() {
     List userModules = new ArrayList();
     IUser user = getCurrentUser();
     if (null == user) {
       return userModules;
     }
     if (UserStatus.ROOT.equals(user.getStatus())) {
       userModules.addAll(getRootUserModules());
       return userModules;
     }
 
     Long roleId = user.getRoleId();
 
     IRole r = RoleContext.getRole(roleId);
     if (null == r) {
       if (logger.isDebugEnabled()) {
         logger.debug("the role gotten from roleCache is null. roleId [" + roleId + "]", new RuntimeException("the role gotten from roleCache is null. roleId [" + roleId + "]"));
       }
 
       return userModules;
     }
     Collection<Long> moduleIds = r.getModuleIds();
     if (null == moduleIds) {
       if (logger.isDebugEnabled()) {
         logger.debug("the moduleIds gotten from role is null. roleId [" + roleId + "]", new RuntimeException("the role gotten from roleCache is null. roleId [" + roleId + "]"));
       }
 
       return userModules;
     }
     for (Long moduleId : moduleIds) {
       IModule m = ModuleContext.getModule(moduleId);
 
       if (null != m)
       {
         userModules.add(m);
       }
     }
     return userModules;
   }
 
   private Collection<IModule> getRootUserModules() {
     Map modulesMap = ModuleContext.getApplicationModule();
// 
     Collection modules = modulesMap.values();
     return modules;
//	   return null;
   }
 
   public List<IModule> getUserGUIModule()
   {
     List<IModule> userModules = getUserPrivilege();
     List result = new ArrayList();
     for (IModule m : userModules) {
       if (ModuleApplyType.GUI == m.getApplyType()) {
         result.add(m);
       }
     }
     return result;
   }
// 
   public List<IModule> getUserWebModule() {
     List userModules = getUserPrivilege();
     if ((null == userModules) && 
       (logger.isDebugEnabled())) {
       logger.debug("user module is null", new RuntimeException("user module is null"));
     }
     userModules = (List)ModuleContext.filter(userModules);
     return userModules;
   }
 
   static
   {
     //Locale.setDefault(Locale.US);
     
   }
 }


 
 
