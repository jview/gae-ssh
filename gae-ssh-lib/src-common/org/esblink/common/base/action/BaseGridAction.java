package org.esblink.common.base.action;
 
 import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.util.JsonUtils;


 
 public abstract class BaseGridAction<T> extends BaseTreeAction
 {
   private static final long serialVersionUID = 8993518654310348419L;
   private boolean success = true;
   private Map<Object, Object> query = new HashMap();
   protected final Class<T> gridClass;
 
   public boolean isSuccess()
   {
     return this.success;
   }
 
   public void setSuccess(boolean success) {
     this.success = success;
   }
 
   public BaseGridAction()
   {
     this.gridClass = ((Class)((java.lang.reflect.ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
   }
 
   public int getPageIndex()
   {
     int start = getPageStart();
     int limit = getPageSize();
     if ((start == 0) || (limit == 0) || (start < limit))
       return 0;
     return start / limit;
   }
 
   public int getPageStart()
   {
     String start = ServletActionContext.getRequest().getParameter("start");
     if ((start == null) || (start.length() == 0))
       return 0;
     return Integer.parseInt(start);
   }
 
   public int getDefaultPageSize()
   {
     return 20;
   }
 
   public int getPageSize()
   {
     String limit = ServletActionContext.getRequest().getParameter("limit");
     if ((limit == null) || (limit.length() == 0))
       return getDefaultPageSize();
     return Integer.parseInt(limit);
   }
 
   public String getSortField()
   {
     return ServletActionContext.getRequest().getParameter("sort");
   }
 
   public String getSortDirection()
   {
     return ServletActionContext.getRequest().getParameter("dir");
   }
 
   public List<T> getSelected()
   {
     try
     {
       return JsonUtils.jsonToList(ServletActionContext.getRequest().getParameter("selected"), this.gridClass);
     } catch (InstantiationException e) {
       throw new RuntimeException(e);
     } catch (IllegalAccessException e) {
       throw new RuntimeException(e);
     } catch (InvocationTargetException e) {
       throw new RuntimeException(e);
     }
   }
 
   public List<T> getSynchronized()
   {
     try
     {
       return JsonUtils.jsonToList(ServletActionContext.getRequest().getParameter("synchronized"), this.gridClass);
     } catch (InstantiationException e) {
       throw new RuntimeException(e);
     } catch (IllegalAccessException e) {
       throw new RuntimeException(e);
     } catch (InvocationTargetException e) {
       throw new RuntimeException(e);
     }
   }
 
   public List<T> getModified()
   {
     try
     {
       return JsonUtils.jsonToList(ServletActionContext.getRequest().getParameter("modified"), this.gridClass);
     } catch (InstantiationException e) {
       throw new RuntimeException(e);
     } catch (IllegalAccessException e) {
       throw new RuntimeException(e);
     } catch (InvocationTargetException e) {
       throw new RuntimeException(e);
     }
   }
 
   public Map<Object, Object> getQuery() {
     return this.query;
   }
 }


 
 
