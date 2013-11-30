package org.esblink.common.base.interceptor;
 
 import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.json.JSONResult;
import org.apache.struts2.json.JSONUtil;
import org.apache.struts2.json.SerializationParams;
import org.esblink.common.util.ClassUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.ValueStack;
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 public class JSONTreeResult extends JSONResult
 {
   private static final long serialVersionUID = 1L;
   private Log log = LogFactory.getLog(getClass());
   private static final String TEXT_FIELD = "textField";
   private static final String ID_FIELD = "idField";
   private static final String LEAF_FIELD = "leafField";
   private static final String CLS_FIELD = "clsField";
   private static final String CHILDREN_FIELD = "childrenField";
 
   public void execute(ActionInvocation invocation)
     throws Exception
   {
     this.log.debug("begin JSONTreeResult");
     ActionContext actionContext = invocation.getInvocationContext();
     HttpServletRequest request = (HttpServletRequest)actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");
     HttpServletResponse response = (HttpServletResponse)actionContext.get("com.opensymphony.xwork2.dispatcher.HttpServletResponse");
     try {
       ValueStack stack = invocation.getStack();
       Object rootObject = stack.findValue(getRoot());
//       Object node;
       Object node;
       if (null == rootObject) {
         node = convertNode(invocation.getAction(), request);
       }
       else
       {
//         Object node;
         if ((rootObject instanceof Collection)) {
           node = convertNodes((Collection)rootObject, request);
         }
         else
         {
//           Object node;
           if ((rootObject instanceof Object[]))
             node = convertNodes((Object[])rootObject, request);
           else
             node = convertNode(rootObject, request); 
         }
       }
       if (this.log.isDebugEnabled())
         this.log.debug("node: " + node);
       if (node != null) {
         String json = JSONUtil.serialize(node);
         SerializationParams sp = new SerializationParams(response, "UTF-8", isWrapWithComments(), json, false, false, false, 0, 0, false, null, null, null);
 
         JSONUtil.writeJSONToResponse(sp);
       }
     } catch (Exception e) {
       this.log.error("JSONTreeResult error!!!!", e);
     }
   }
 
   private Map<String, Object> convertNode(Object target, HttpServletRequest request) throws Exception {
     String textField = request.getParameter("textField");
     String idField = request.getParameter("idField");
     String leafField = request.getParameter("leafField");
     String clsField = request.getParameter("clsField");
     String childrenField = request.getParameter("childrenField");
     Map node = new HashMap();
     Map properties = ClassUtils.getObjectAllProperties(target);

			  Set<Map.Entry> entrySet=properties.entrySet();
     for (Map.Entry entry : entrySet) {
       String name = (String)entry.getKey();
       if ((name != null) && (name.length() != 0))
       {
         Object value = entry.getValue();
         if ((value != null) && (!(value instanceof Date)))
         {
           if ((value instanceof Long)) {
             value = value.toString();
           }
           if ((textField != null) && (textField.equals(name)))
             node.put("text", value);
           else if ((idField != null) && (idField.equals(name)))
             node.put("id", value);
           else if ((leafField != null) && (leafField.equals(name)))
             node.put("leaf", value);
           else if ((clsField != null) && (clsField.equals(name)))
             node.put("cls", value);
           else if ((childrenField != null) && (childrenField.equals(name))) {
             node.put("children", value);
           }
           else if (!node.containsKey(name))
             node.put(name, value); 
         }
       }
     }
     return node;
   }
 
   private List<Map<String, Object>> convertNodes(Collection<Object> target, HttpServletRequest request) throws Exception {
     List nodes = new ArrayList();
     for (Iterator i$ = target.iterator(); i$.hasNext(); ) { Object node = i$.next();
       nodes.add(convertNode(node, request));
     }
     return nodes;
   }
 
   private List<Map<String, Object>> convertNodes(Object[] target, HttpServletRequest request) throws Exception {
     List nodes = new ArrayList();
     for (Object node : target) {
       nodes.add(convertNode(node, request));
     }
     return nodes;
   }
 }


 
 
