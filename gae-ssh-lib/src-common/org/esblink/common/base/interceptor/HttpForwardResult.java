package org.esblink.common.base.interceptor;
 
 import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.ActionInvocation;
 
 public class HttpForwardResult extends BaseResult
 {
   private static final long serialVersionUID = 5404536084903245255L;
 
   protected void doExecute(String location, ActionInvocation invocation)
     throws Exception
   {
     HttpServletRequest request = (HttpServletRequest)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
 
     HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
 
     response.setContentType("text/html; charset=utf-8");
     ServletOutputStream out = response.getOutputStream();
     if ((location != null) && (!location.startsWith("/"))) {
       location = "/" + location;
     }
     String context = request.getContextPath();
     if ((context != null) && (!"/".equals(context))) {
       location = context + location;
     }
     out.print("<html><body><script type='text/javascript'>window.location.href='" + location + "';</script></body></html>");
     out.flush();
   }
 }


 
 
