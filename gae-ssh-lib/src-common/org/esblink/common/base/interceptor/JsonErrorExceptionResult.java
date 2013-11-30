package org.esblink.common.base.interceptor;
 
 import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.JavaScriptUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.util.LocalizedTextUtil;
 
 public class JsonErrorExceptionResult extends BaseResult
 {
   private static final long serialVersionUID = 5404536084903245255L;
 
   protected void doExecute(String location, ActionInvocation invocation)
     throws Exception
   {
     List err = null;
     Collection val = null;
     Object action = invocation.getAction();
     if ((action instanceof ValidationAware)) {
       ValidationAware validationAwareAction = (ValidationAware)action;
       if (validationAwareAction.hasErrors()) {
         err = (List)validationAwareAction.getActionErrors();
       }
       if (((err == null) || (err.size() <= 0)) && (validationAwareAction.hasFieldErrors()))
       {
         Map m = validationAwareAction.getFieldErrors();
         if ((null != m) && (m.size() > 0)) {
           val = m.values();
           if ((null != val) && (val.size() > 0)) {
             err = new ArrayList();
             err.addAll(val);
           }
         }
       }
     }
     String errMsg = "";
//     Iterator i$;
     if ((null != err) && (err.size() > 0)) {
       for (Iterator i$ = err.iterator(); i$.hasNext(); ) { Object o = i$.next();
         if (null != o) {
           if (o.getClass().isAssignableFrom(ArrayList.class)) {
             List msgList = (List)err.get(0);
             for (Iterator j$ = msgList.iterator(); j$.hasNext(); ) { Object msg = j$.next();
               if (null != msg) {
                 errMsg = msg.toString();
                 break;
               }
             }
 
             break;
           }errMsg = o.toString();
           if ((null == errMsg) || (!errMsg.startsWith("the request was rejected because its size ("))) {
             break;
           }
           ActionContext ac = invocation.getInvocationContext();
 
           errMsg = getTextMessage("struts.messages.error.file.too.large", ac.getLocale());
 
           break;
         }
       }
 
     }
 
     if ((null == errMsg) || ("".equals(errMsg))) {
       errMsg = "It seens some unknow error prisent when you access :[" + action.toString() + "]";
     }
 
     errMsg = JavaScriptUtils.javaScriptEscape(errMsg);
 
     HttpServletResponse response = (HttpServletResponse)invocation.getInvocationContext().get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
 
     response.setContentType("text/html; charset=utf-8");
     ServletOutputStream out = response.getOutputStream();
     out.print("{success:false,error:'" + errMsg + "'}");
     out.flush();
   }
 
   private String getTextMessage(String messageKey, Locale locale) {
     return LocalizedTextUtil.findText(getClass(), messageKey, locale);
   }
 }


 
 
