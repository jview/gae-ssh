package org.esblink.common.service.impl;
 
 import java.io.IOException;
 import javax.servlet.Filter;
 import javax.servlet.FilterChain;
 import javax.servlet.FilterConfig;
 import javax.servlet.ServletException;
 import javax.servlet.ServletRequest;
 import javax.servlet.ServletResponse;
 
 public class ExceptionFilter
   implements Filter
 {
   public void destroy()
   {
   }
 
   public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
     throws IOException, ServletException
   {
     try
     {
       chain.doFilter(req, res);
     }
     catch (BaseException e) {
       ExceptionManager.handleServletException(req, res, e);
     }
   }
 
   public void init(FilterConfig arg0)
     throws ServletException
   {
   }
 }


 
 
