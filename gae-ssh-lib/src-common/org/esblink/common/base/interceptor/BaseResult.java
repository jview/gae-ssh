package org.esblink.common.base.interceptor;
 
 import com.opensymphony.xwork2.Result;
 import org.apache.struts2.dispatcher.StrutsResultSupport;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public abstract class BaseResult extends StrutsResultSupport
   implements Result
 {
   private static final long serialVersionUID = 6848302445732409026L;
   protected Logger log = LoggerFactory.getLogger(getClass());
 }


 
 
