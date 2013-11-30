package org.esblink.common.base.interceptor;
 
 import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.esblink.common.util.Toolkit;

import com.opensymphony.xwork2.ActionInvocation;
 
 public class ExceptionResult extends BaseResult
 {
   private static final long serialVersionUID = 1L;
 
   protected void doExecute(String location, ActionInvocation invocation)
     throws Exception
   {
     Exception result = null;
     try {
       result = (Exception)Class.forName(location.trim()).newInstance();
     } catch (Exception e) {
       result = new RuntimeException("Exception: " + location);
     }
     HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
     ServletOutputStream out = response.getOutputStream();
     out.write(Toolkit.getSerializer().serialize(result));
     out.flush();
   }
 }


 
 
