package org.esblink.common.base.interceptor;
 
 import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public abstract class BaseInterceptor extends AbstractInterceptor
 {
   private static final long serialVersionUID = 6887812502563592524L;
   protected Logger log = LoggerFactory.getLogger(getClass());
 }


 
 
