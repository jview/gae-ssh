package org.esblink.common.base.interceptor;
 
 import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.Module;
import org.esblink.common.base.context.ModuleContext;
import org.esblink.common.exception.ConfigException;

import com.opensymphony.xwork2.ActionInvocation;
 
 public class ModuleInterceptor extends BaseInterceptor
 {
   private static final long serialVersionUID = 4051577171168020618L;
   private static final Log logger = LogFactory.getLog(ModuleInterceptor.class);
 
   public String intercept(ActionInvocation invocation) throws Exception
   {
     String moduleName = invocation.getProxy().getNamespace();
     if (moduleName == null) {
       throw new ConfigException("Action: " + invocation.getProxy().getActionName() + " 所在包未配置名称空间! 请检查模块下/META-INF/config/action.xml, 配置如: <package namespace=\"/模块名\">");
     }
     if (moduleName.charAt(0) == '/')
       moduleName = moduleName.substring(1);
     Module module = ApplicationContext.getContext().getApplication().getModule(moduleName);
     if (module == null) {
       throw new ConfigException("Action: " + invocation.getProxy().getActionName() + " 所在包配置的名称空间必需与模块名相同! 请检查模块下/META-INF/config/action.xml, 配置如: <package namespace=\"/模块名\">");
     }
     ModuleContext.init(module);
     HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
     request.setAttribute("images", "../images/" + moduleName);
     request.setAttribute("scripts", "../scripts/" + moduleName);
     request.setAttribute("styles", "../styles/" + moduleName);
     long start = System.currentTimeMillis();
     String result = invocation.invoke();
     long time = System.currentTimeMillis() - start;
     logger.info("Executed " + invocation.getAction().getClass().getName() + " in " + time + " ms.");
     return result;
   }
 }


 
 
