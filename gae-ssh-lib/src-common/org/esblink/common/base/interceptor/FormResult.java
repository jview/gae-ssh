package org.esblink.common.base.interceptor;
 
 import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.esblink.common.util.ClassUtils;

import com.opensymphony.xwork2.ActionInvocation;
 
 public class FormResult extends BaseResult
 {
   private static final long serialVersionUID = 1L;
 
   protected void doExecute(String location, ActionInvocation invocation)
     throws Exception
   {
     HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
 
     Object action = invocation.getAction();
     String[] properties = location.split("\\,");
     Map result = ClassUtils.getObjectProperties(action, properties);
 
     ServletOutputStream out = response.getOutputStream();
//     out.write(Toolkit.getSerializer().serialize((Serializable)result));
     out.flush();
   }
 }


 
 
