package org.esblink.common.base.interceptor;
 
 import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

import org.apache.struts2.dispatcher.ServletDispatcherResult;
import org.esblink.common.base.context.ModuleContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ResultConfig;
 
 
 
 
 
 
 
 
 
 
 public class PathInterceptor extends BaseInterceptor
 {
   private static final long serialVersionUID = -7506471938571588297L;
   private static final String LOCATION = "location";
   private static final String ROOT = "/";
 
   public String intercept(ActionInvocation invocation)
     throws Exception
   {
     Map results = invocation.getProxy().getConfig().getResults();
     String pages;
     if (results != null) {
    	 if( ModuleContext.getContext()==null||ModuleContext.getContext().getModule()==null){
    		 return invocation.invoke();
    	 }
       pages = ModuleContext.getContext().getModule().getPagesExportDirectory() + "/";
			  Collection<ResultConfig> values = results.values();
       for (ResultConfig result : values) {
         if (ServletDispatcherResult.class.getName().equals(result.getClassName())) {
           Map params = result.getParams();
           if (params != null) {
             String location = (String)params.get("location");
             if (location != null) {
               location = location.trim();
               if (!location.startsWith("/"))
               {
                 modifyParams(params, "location", pages + location);
               }
             }
           }
         }
       }
     }
     return invocation.invoke();
   }
 
   private void modifyParams(Map<String, String> params, String key, String value) {
     try {
       params.put(key, value);
     } catch (Exception e) {
       modifyParamsByReflect(params, key, value);
     }
   }
 
   private void modifyParamsByReflect(Map<String, String> params, String key, String value)
   {
     try {
       Field field = params.getClass().getDeclaredField("m");
       field.setAccessible(true);
       Map map = (Map)field.get(params);
       map.put(key, value);
     } catch (Exception e) {
       e.printStackTrace();
     }
   }
 }


 
 
