package org.esblink.common.base.interceptor;
 
 import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.esblink.common.base.DataBundle;
import org.esblink.common.base.action.IDataAction;
import org.esblink.common.base.action.IDataBundleAction;
import org.esblink.common.util.Toolkit;

import com.opensymphony.xwork2.ActionInvocation;
 
 public class DataInterceptor extends BaseInterceptor
 {
   private static final long serialVersionUID = 1L;
 
   public String intercept(ActionInvocation invocation)
     throws Exception
   {
     HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
 
     Object action = invocation.getAction();
     if ((action instanceof IDataAction)) {
       IDataAction richAction = (IDataAction)action;
       Serializable model = Toolkit.getSerializer().deserialize(request.getInputStream());
 
       richAction.setRequestData(model);
     } else if ((action instanceof IDataBundleAction)) {
       IDataBundleAction richAction = (IDataBundleAction)action;
       Serializable model = Toolkit.getSerializer().deserialize(request.getInputStream());
 
       richAction.setRequestDataBundle((DataBundle)model);
     }
     return invocation.invoke();
   }
 }


 
 
