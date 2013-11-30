package org.esblink.common.base.interceptor;
 
 import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
 
 public class ErrorInterceptor extends BaseInterceptor
 {
   private static final long serialVersionUID = 7563014655616490865L;
   private static final String ERROR = "error";
 
   public String intercept(ActionInvocation invocation)
     throws Exception
   {
     Object action = invocation.getAction();
     if ((action instanceof ValidationAware)) {
       ValidationAware validationAwareAction = (ValidationAware)action;
       if (validationAwareAction.hasErrors()) {
         this.log.debug(" [FORBIDDEN] Error happened when access this URL  ");
         return "error";
       }
     }
     try {
       return invocation.invoke();
     }
     catch (Exception e) {
       ((ValidationAware)action).addActionError(e.getMessage());
       this.log.error("The Error is Intercepted by ErrorInterceptor.", e);
     }return "error";
   }
 }


 
 
