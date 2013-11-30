package org.esblink.common.base.interceptor;
 
 import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.JSONResult;
import org.esblink.common.base.action.BaseDataAction;
import org.esblink.common.base.action.BaseDataBundleAction;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.security.ModuleContext;
import org.esblink.common.base.security.UserSecurity;
import org.esblink.common.exception.WithoutPermissionException;
import org.esblink.common.service.impl.NotLoginException;
import org.esblink.common.util.Constants;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
 
 public class SecurityInterceptor extends BaseInterceptor
 {
   private static final long serialVersionUID = -3891951321635576000L;
   private static final String LOGIN = "login";
   private static final String JSON_LOGIN = "jsonlogin";
   private static final String FORBIDDEN = "forbidden";
   private static final Log logger = LogFactory.getLog(BaseInterceptor.class);
 
   public String intercept(ActionInvocation invocation)
     throws Exception
   {
     String actionName = invocation.getProxy().getActionName();
     String namespace = invocation.getProxy().getNamespace();
     String actionUrl = namespace + "/" + actionName + ".action";
     Object action = invocation.getAction();
     logger.debug(" Current Entry is [" + actionUrl + "]");
     if (!isChecked(actionUrl))
       return invocation.invoke();
     if (!isLogin())
     {
       logger.debug(" [LOGIN] User not login and this URL must login ");
       Map results = invocation.getProxy().getConfig().getResults();
       ResultConfig result = (ResultConfig)results.get("success");
       if (result == null)
         result = (ResultConfig)results.get("");
       if ((result != null) && ((JSONResult.class.getName().equals(result.getClassName())) || (JSONTreeResult.class.getName().equals(result.getClassName()))))
       {
         if ((action instanceof ValidationAware))
         {
           Method m = action.getClass().getMethod("setSuccess", new Class[] { 
             Boolean.TYPE });
 
           if (m != null) {
             m.invoke(action, new Object[] { 
               Boolean.valueOf(false) });
           }
           ValidationAware validationAwareAction = (ValidationAware)action;
           validationAwareAction.addActionError(getTextMessage("user.not.login", UserContext.getContext().getLocale()));
         }
         return "jsonlogin";
       }
       if (((action instanceof BaseDataAction)) || ((action instanceof BaseDataBundleAction))) {
         throw new NotLoginException();
       }
 
       if ((actionName != null) && (actionName.equals("jframe"))) {
         return "jlogin";
       }
 
       return "jlogin";
     }
 
     if (!isRegistered(actionUrl))
       return invocation.invoke();
     if (!isAccessable(actionUrl))
     {
       logger.debug(" [FORBIDDEN] User cann't access this URL  ");
       if (((action instanceof BaseDataAction)) || ((action instanceof BaseDataBundleAction))) {
         throw new WithoutPermissionException();
       }
       return "forbidden";
     }
//     System.out.println("---------invocation"+invocation.getClass().getName()+" "+invocation.getAction());
     return invocation.invoke();
   }
 
   private boolean isChecked(String actionUrl)
   {
     return !Constants.UNCHECD_ACTION_SET.contains(actionUrl);
   }
 
   private boolean isLogin()
   {
     IUser user = UserContext.getContext().getCurrentUser();
     return user != null;
   }
 
   private boolean isRegistered(String actionUrl)
   {
     Map modules = ModuleContext.getApplicationURLModules();
     if (modules == null)
     {
       logger.debug(" module cache is ERROR ");
       return false;
     }
     if (modules.containsKey(actionUrl))
     {
       return true;
     }
 
     logger.debug("not regisstered URL [" + actionUrl + "]");
     return false;
   }
 
   private boolean isAccessable(String actionUrl)
   {
     IModule module = ModuleContext.getModule(actionUrl);
     return UserSecurity.hasPrivilege(UserContext.getContext().getCurrentUser(), module);
   }
 
   private String getTextMessage(String messageKey, Locale locale)
   {
     return LocalizedTextUtil.findText(getClass(), messageKey, locale);
   }
 }


 
 
