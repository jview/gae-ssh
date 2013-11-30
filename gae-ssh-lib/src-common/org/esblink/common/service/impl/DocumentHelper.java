package org.esblink.common.service.impl;
 
 import java.lang.reflect.Method;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 
 public class DocumentHelper
 {
   private static Log log = LogFactory.getLog(DocumentHelper.class);
 
   public static Map<String, Object> getGetMethodInfo(Object obj)
   {
     Map result = new HashMap();
     Class cls = obj.getClass();
     Method[] methods = cls.getMethods();
     for (int i = 0; i < methods.length; i++) {
       if (isGetMethod(methods[i])) {
         try {
           String key = getFieldName(methods[i]);
           Object value = methods[i].invoke(obj, new Object[0]);
           if (value == null) {
             value = "";
           }
           result.put(key, value);
         } catch (Exception e) {
           log.error(e.getMessage(), e);
         }
       }
     }
     return result;
   }
 
   public static List<String> getKeysFromObject(Object obj)
   {
     List list = new ArrayList();
     if ((obj instanceof Map)) {
       Map map = (Map)obj;
       Object[] keys = map.keySet().toArray();
       for (int i = 0; i < keys.length; i++)
         list.add(keys[i].toString());
     }
     else {
       Class cls = obj.getClass();
       Method[] methods = cls.getMethods();
       for (int i = 0; i < methods.length; i++) {
         if (isGetMethod(methods[i])) {
           list.add(getFieldName(methods[i]));
         }
       }
     }
     return list;
   }
 
   public static void invokeSetMethods(Object obj, String[] keys, String[] values)
   {
     Method[] methods = obj.getClass().getMethods();
     for (int i = 0; i < keys.length; i++)
       for (int j = 0; j < methods.length; j++)
         if ((isSetMethod(methods[j])) && 
           (keys[i] != null) && (keys[i].equalsIgnoreCase(getFieldName(methods[j])))) {
           Class[] clss = methods[j].getParameterTypes();
           if ((clss != null) && (clss.length > 0)) {
             Object[] params = getParams(clss[0], values[i]);
             try {
               methods[j].invoke(obj, new Object[] { params[0] });
             }
             catch (Exception e) {
               log.error(e.getMessage(), e);
             }
           }
         }
   }
 
   private static boolean isGetMethod(Method method)
   {
     if ((method.getName().startsWith("get")) && ((method.getModifiers() & 0x1) == 1))
     {
       return true;
     }
     return false;
   }
 
   private static boolean isSetMethod(Method method)
   {
     if ((method.getName().startsWith("set")) && ((method.getModifiers() & 0x1) == 1))
     {
       return true;
     }
     return false;
   }
 
   private static String getFieldName(Method method)
   {
     String methodName = method.getName();
 
     String fieldName = methodName.substring(3);
     if ((fieldName != null) && (fieldName.length() > 0))
     {
       fieldName = convertStartCharToLowerCase(fieldName);
     }
     return fieldName;
   }
 
   private static String convertStartCharToLowerCase(String str)
   {
     String start = str.charAt(0) + "";
     start = start.toLowerCase();
     String end = "";
     if (str.length() > 1) {
       end = str.substring(1);
     }
     return start + end;
   }
 
   public static String convertStartCharToUpperCase(String str)
   {
     String start = str.charAt(0) + "";
     start = start.toUpperCase();
     String end = "";
     if (str.length() > 1) {
       end = str.substring(1);
     }
     return start + end;
   }
 
   public static Object[] getParams(Class cls, String value)
   {
     Object[] objs = null;
     if ((value == null) || (value.length() < 1)) {
       objs = new Integer[1];
       objs[0] = null;
       return objs;
     }
     if ((cls == Integer.TYPE) || (cls == Integer.class)) {
       objs = new Integer[1];
       objs[0] = Integer.valueOf(Integer.parseInt(value));
     } else if ((cls == Boolean.TYPE) || (cls == Boolean.class)) {
       objs = new Boolean[1];
       objs[0] = Boolean.valueOf(Boolean.parseBoolean(value));
     } else if ((cls == Short.TYPE) || (cls == Short.class)) {
       objs = new Short[1];
       objs[0] = Short.valueOf(Short.parseShort(value));
     } else if ((cls == Long.TYPE) || (cls == Long.class)) {
       objs = new Long[1];
       objs[0] = Long.valueOf(Long.parseLong(value.substring(0, value.indexOf("."))));
     } else if ((cls == Float.TYPE) || (cls == Float.class)) {
       objs = new Float[1];
       objs[0] = Float.valueOf(Float.parseFloat(value));
     } else if ((cls == Double.TYPE) || (cls == Double.class)) {
       objs = new Double[1];
       objs[0] = Double.valueOf(Double.parseDouble(value));
     } else {
       objs = new String[1];
       objs[0] = value;
     }
     return objs;
   }
 }


 
 
