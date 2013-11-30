 package org.esblink.module.framework.action;
 
 import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.FrameworkListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
 
 @Controller("forwardAction")
 @Scope("prototype")
 public class ForwardAction extends BaseAction
 {
   private static final long serialVersionUID = 1L;
   public String execute()
     throws Exception
   {
	   if(!FrameworkListener.isLoad){
		   ApplicationContext.init(ServletActionContext.getServletContext());
		   FrameworkListener.isLoad=true;
	   }
     return "success";
   }
 }


 
 
