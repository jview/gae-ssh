package org.esblink.common.base.interceptor;
 
 import com.opensymphony.xwork2.ActionContext;
 import com.opensymphony.xwork2.ActionInvocation;
 import com.opensymphony.xwork2.util.ValueStack;
 import java.io.IOException;
 import javax.servlet.http.HttpServletResponse;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.struts2.json.JSONResult;
 import org.apache.struts2.json.JSONUtil;
 import org.apache.struts2.json.SerializationParams;
 
 public class AsuraJSONResult extends JSONResult
 {
   private static final long serialVersionUID = 1L;
   private Log log = LogFactory.getLog(getClass());
 
   public void execute(ActionInvocation invocation) throws Exception
   {
     ActionContext actionContext = invocation.getInvocationContext();
     HttpServletResponse response = (HttpServletResponse)actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
     try
     {
       Object rootObject;
//       Object rootObject;
       if (getRoot() != null) {
         ValueStack stack = invocation.getStack();
         rootObject = stack.findValue(getRoot());
       } else {
         rootObject = invocation.getAction();
       }
       AsuraJSONWriter writer = new AsuraJSONWriter();
       String json = writer.write(rootObject, getExcludePropertiesList(), getIncludePropertiesList(), false);
       SerializationParams sp = new SerializationParams(response, "UTF-8", isWrapWithComments(), json, false, false, false, 0, 0, false, null, null, null);
 
       JSONUtil.writeJSONToResponse(sp);
     }
     catch (IOException exception) {
       this.log.error(exception.getMessage(), exception);
       throw exception;
     }
   }
 }


 
 
