 package org.esblink.common.util;
 
 import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
 
 public final class ClassUtils
 {
	 /**
	     * 从类中取所有的属性(包含父类的属性,不包括Object)
	     * @param clazz 实体类:ClassUtil.class
	     * @param genericString 方法类型:public,protected,private
	     * @author chenjh
	     * @return
	     */
	    public static List<Field> getClassFields(Class clazz, String genericString){
	    	List<Field> list = new LinkedList<Field>();
	    	//父类处理
	    	if(clazz.getSuperclass()!=null&&!(clazz.getSuperclass().getSimpleName().endsWith("Object"))){
	    		List<Field> listt = getClassFields(clazz.getSuperclass(), genericString);			
				list.addAll(listt);
	    	}
	    	
	    	Field[] fields = clazz.getDeclaredFields();
	    	Field field = null;
	    	String field_str = null;
	    	for(int i=0; i<fields.length; i++){
	    		field = fields[i];			
	    		field_str = field.toGenericString();
				if(field_str.startsWith(genericString)){
					list.add(field);
				}
	    	}
	    	
	    	return list;
	    }
	    
   public static Class<?> forName(String className)
     throws ClassNotFoundException
   {
     if (className == null)
       throw new NullPointerException("类名不能为空！");
     return Class.forName(className);
   }
 
   public static Class<?> forCanonicalName(String className)
     throws ClassNotFoundException
   {
     if (className == null)
       throw new NullPointerException("类名不能为空！");
     if (className.endsWith("[]"))
       className = "[L" + className.substring(0, className.length() - 2) + ";";
     return Class.forName(className);
   }
 
   public static Object getObjectProperty(Object object, String property)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     if (object == null)
       return null;
     Method getter = getClassGetter(object.getClass(), property);
     return getter.invoke(object, new Object[0]);
   }
 
   public static Map<String, ?> getObjectProperties(Object object, String[] properties)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     if ((object == null) || (properties == null))
       return null;
     Map map = new HashMap(properties.length);
     int i = 0; for (int n = properties.length; i < n; i++) {
       String prop = properties[i];
       if ((prop != null) && (prop.length() > 0))
         map.put(prop, getObjectProperty(object, prop));
     }
     return map;
   }
 
   public static Map<String, Object> getObjectAllProperties(Object object)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     Map map = new HashMap();
     if (object != null) {
       Method[] methods = object.getClass().getMethods();
       int i = 0; for (int n = methods.length; i < n; i++) {
         Method method = methods[i];
         if (((method.getModifiers() & 0x1) == 1) && (method.getDeclaringClass() != Object.class) && ((method.getParameterTypes() == null) || (method.getParameterTypes().length == 0)))
         {
           String property = method.getName();
           if (property.startsWith("get")) {
             property = property.substring(3);
             String lower = property.substring(0, 1).toLowerCase() + property.substring(1);
             map.put(lower, method.invoke(object, new Object[0]));
           } else if (property.startsWith("is")) {
             property = property.substring(2);
             String lower = property.substring(0, 1).toLowerCase() + property.substring(1);
             map.put(lower, method.invoke(object, new Object[0]));
           }
         }
       }
     }
     return map;
   }
 
   public static List<String> getClassAllProperties(Class<?> clazz)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     if (clazz == null)
       return null;
     List list = new ArrayList();
     Method[] methods = clazz.getMethods();
     int i = 0; for (int n = methods.length; i < n; i++) {
       Method method = methods[i];
       if (((method.getModifiers() & 0x1) == 1) && (method.getDeclaringClass() != Object.class) && ((method.getParameterTypes() == null) || (method.getParameterTypes().length == 0)))
       {
         String property = method.getName();
         if ((property.startsWith("get")) && (!"getClass".equals(property))) {
           property = property.substring(3);
           String lower = property.substring(0, 1).toLowerCase() + property.substring(1);
           list.add(lower);
         } else if (property.startsWith("is")) {
           property = property.substring(2);
           String lower = property.substring(0, 1).toLowerCase() + property.substring(1);
           list.add(lower);
         }
       }
     }
     return list;
   }
 
   public static void setObjectProperty(Object object, String property, Object value)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     if (object == null)
       return;
     Method setter = getClassSetter(object.getClass(), property);
     setter.invoke(object, new Object[] { value });
   }
 
   public static void setObjectProperties(Object object, Map<String, Object> properties)
     throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
   {
     if (object == null)
       return;
     for (Iterator iterator = properties.entrySet().iterator(); iterator.hasNext(); ) {
       Map.Entry entry = (Map.Entry)iterator.next();
       String property = (String)entry.getKey();
       Object value = entry.getValue();
       Method setter = getClassSetter(object.getClass(), property);
       setter.invoke(object, new Object[] { value });
     }
   }
 
   public static Method getClassGetter(Class<?> clazz, String property)
     throws NoSuchMethodException, SecurityException
   {
     if (clazz == null)
       throw new NullPointerException("类元不能为空！");
     if (property == null)
       throw new NullPointerException("属性名不能为空！");
     property = property.trim();
     String upper = property.substring(0, 1).toUpperCase() + property.substring(1);
     try {
       return clazz.getMethod("get" + upper, new Class[0]); } catch (Exception e) {
     }
     return clazz.getMethod("is" + upper, new Class[0]);
   }
 
   public static Method getClassSetter(Class<?> clazz, String property)
     throws NoSuchMethodException, SecurityException
   {
     if (clazz == null)
       throw new NullPointerException("类元不能为空！");
     if (property == null)
       throw new NullPointerException("属性名不能为空！");
     property = property.trim();
     String upper = property.substring(0, 1).toUpperCase() + property.substring(1);
     return clazz.getMethod("set" + upper, new Class[0]);
   }
 }


 
 
