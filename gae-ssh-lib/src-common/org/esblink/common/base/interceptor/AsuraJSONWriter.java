package org.esblink.common.base.interceptor;
 
 import java.beans.BeanInfo;
 import java.beans.Introspector;
 import java.beans.PropertyDescriptor;
 import java.lang.reflect.Array;
 import java.lang.reflect.Method;
 import java.text.CharacterIterator;
 import java.text.DateFormat;
 import java.text.SimpleDateFormat;
 import java.text.StringCharacterIterator;
 import java.util.Calendar;
 import java.util.Collection;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.Locale;
 import java.util.Map;
 import java.util.Map.Entry;
 import java.util.Set;
 import java.util.Stack;
 import java.util.regex.Matcher;
 import java.util.regex.Pattern;
 import org.apache.commons.logging.Log;
 import org.apache.commons.logging.LogFactory;
 import org.apache.struts2.json.JSONException;
 import org.apache.struts2.json.annotations.JSON;
 
 public class AsuraJSONWriter
 {
   private static final Log log = LogFactory.getLog(AsuraJSONWriter.class);
   public static final boolean ENUM_AS_BEAN_DEFAULT = false;
   static final String RFC3339_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
   static char[] hex = "0123456789ABCDEF".toCharArray();
   private StringBuilder buf = new StringBuilder();
   private Stack stack = new Stack();
   private boolean ignoreHierarchy = true;
   private Object root;
   private boolean buildExpr = true;
   private String exprStack = "";
   private Collection<Pattern> excludeProperties;
   private Collection<Pattern> includeProperties;
   private DateFormat formatter;
   private boolean enumAsBean = false;
   private boolean excludeNullProperties;
 
   public String write(Object object)
     throws JSONException
   {
     return write(object, null, null, false);
   }
 
   public String write(Object object, Collection<Pattern> excludeProperties, Collection<Pattern> includeProperties, boolean excludeNullProperties)
     throws JSONException
   {
     this.excludeNullProperties = excludeNullProperties;
     this.buf.setLength(0);
     this.root = object;
     this.exprStack = "";
     this.buildExpr = (((excludeProperties != null) && (!excludeProperties.isEmpty())) || ((includeProperties != null) && (!includeProperties.isEmpty())));
     this.excludeProperties = excludeProperties;
     this.includeProperties = includeProperties;
     value(object, null);
 
     return this.buf.toString();
   }
 
   private void value(Object object, Method method)
     throws JSONException
   {
     if (object == null) {
       add("null");
 
       return;
     }
 
     if (this.stack.contains(object)) {
       Class clazz = object.getClass();
 
       if ((clazz.isPrimitive()) || (clazz.equals(String.class))) {
         process(object, method);
       } else {
         if (log.isDebugEnabled()) {
           log.debug("Cyclic reference detected on " + object);
         }
 
         add("null");
       }
 
       return;
     }
 
     process(object, method);
   }
 
   private void process(Object object, Method method)
     throws JSONException
   {
     this.stack.push(object);
 
     if ((object instanceof Class)) {
       string(object);
     } else if ((object instanceof Boolean)) {
       bool(((Boolean)object).booleanValue());
     } else if ((object instanceof Long)) {
       add('"');
       add(object);
       add('"');
     } else if ((object instanceof Number)) {
       add(object);
     } else if ((object instanceof String)) {
       string(object);
     } else if ((object instanceof Character)) {
       string(object);
     } else if ((object instanceof Map)) {
       map((Map)object, method);
     } else if (object.getClass().isArray()) {
       array(object, method);
     } else if ((object instanceof Iterable)) {
       array(((Iterable)object).iterator(), method);
     } else if ((object instanceof Date)) {
       date((Date)object, method);
     } else if ((object instanceof Calendar)) {
       date(((Calendar)object).getTime(), method);
     } else if ((object instanceof Locale)) {
       string(object);
     } else if ((object instanceof Enum)) {
       enumeration((Enum)object);
     } else {
       bean(object);
     }
 
     this.stack.pop();
   }
 
   private void bean(Object object)
     throws JSONException
   {
     add("{");
     try
     {
       Class clazz = object.getClass();
 
       BeanInfo info = (object == this.root) && (this.ignoreHierarchy) ? Introspector.getBeanInfo(clazz, clazz.getSuperclass()) : Introspector.getBeanInfo(clazz);
 
       PropertyDescriptor[] props = info.getPropertyDescriptors();
 
       boolean hasData = false;
       for (int i = 0; i < props.length; i++) {
         PropertyDescriptor prop = props[i];
         String name = prop.getName();
         Method accessor = prop.getReadMethod();
         Method baseAccessor = null;
         if (clazz.getName().indexOf("$$EnhancerByCGLIB$$") > -1)
           try {
             baseAccessor = Class.forName(clazz.getName().substring(0, clazz.getName().indexOf("$$"))).getMethod(accessor.getName(), accessor.getParameterTypes());
           }
           catch (Exception ex)
           {
             log.debug(ex.getMessage(), ex);
           }
         else {
           baseAccessor = accessor;
         }
         if (baseAccessor != null)
         {
           JSON json = (JSON)baseAccessor.getAnnotation(JSON.class);
           if (json != null) {
             if (!json.serialize())
               continue;
             if (json.name().length() > 0) {
               name = json.name();
             }
           }
 
           if (!shouldExcludeProperty(clazz, prop))
           {
             String expr = null;
             if (this.buildExpr) {
               expr = expandExpr(name);
               if (!shouldExcludeProperty(expr))
               {
                 expr = setExprStack(expr);
               }
             } else {
               Object value = accessor.invoke(object, new Object[0]);
               boolean propertyPrinted = add(name, value, accessor, hasData);
               hasData = (hasData) || (propertyPrinted);
               if (this.buildExpr) {
                 setExprStack(expr);
               }
             }
           }
         }
       }
       if ((object instanceof Enum)) {
         Object value = ((Enum)object).name();
         add("_name", value, object.getClass().getMethod("name", new Class[0]), hasData);
       }
     } catch (Exception e) {
       throw new JSONException(e);
     }
 
     add("}");
   }
 
   private void enumeration(Enum enumeration)
     throws JSONException
   {
     if (this.enumAsBean)
       bean(enumeration);
     else
       string(enumeration.name());
   }
 
   private boolean shouldExcludeProperty(Class clazz, PropertyDescriptor prop)
     throws SecurityException, NoSuchFieldException
   {
     String name = prop.getName();
 
     if ((name.equals("class")) || (name.equals("declaringClass"))) {
       return true;
     }
 
     return false;
   }
 
   private String expandExpr(int i) {
     return this.exprStack + "[" + i + "]";
   }
 
   private String expandExpr(String property) {
     if (this.exprStack.length() == 0)
       return property;
     return this.exprStack + "." + property;
   }
 
   private String setExprStack(String expr) {
     String s = this.exprStack;
     this.exprStack = expr;
     return s;
   }
 
   private boolean shouldExcludeProperty(String expr) {
     if (this.excludeProperties != null) {
       for (Pattern pattern : this.excludeProperties) {
         if (pattern.matcher(expr).matches()) {
           if (log.isDebugEnabled())
             log.debug("Ignoring property because of exclude rule: " + expr);
           return true;
         }
       }
     }
 
     if (this.includeProperties != null) {
       for (Pattern pattern : this.includeProperties) {
         if (pattern.matcher(expr).matches()) {
           return false;
         }
       }
 
       if (log.isDebugEnabled())
         log.debug("Ignoring property because of include rule:  " + expr);
       return true;
     }
 
     return false;
   }
 
   private boolean add(String name, Object value, Method method, boolean hasData)
     throws JSONException
   {
     if ((!this.excludeNullProperties) || (value != null)) {
       if (hasData) {
         add(',');
       }
       add('"');
       add(name);
       add("\":");
       value(value, method);
       return true;
     }
 
     return false;
   }
 
   private void map(Map map, Method method)
     throws JSONException
   {
     add("{");
 
     Iterator it = map.entrySet().iterator();
 
     boolean hasData = false;
     while (it.hasNext()) {
       Map.Entry entry = (Map.Entry)it.next();
       Object key = entry.getKey();
       String expr = null;
       if (this.buildExpr) {
         if (key == null) {
           log.error("Cannot build expression for null key in " + this.exprStack);
         }
         else
         {
           expr = expandExpr(key.toString());
           if (!shouldExcludeProperty(expr))
           {
             expr = setExprStack(expr);
           }
         }
       } else { if (hasData) {
           add(',');
         }
         hasData = true;
         value(key, method);
         add(":");
         value(entry.getValue(), method);
         if (this.buildExpr) {
           setExprStack(expr);
         }
       }
     }
     add("}");
   }
 
   private void date(Date date, Method method)
   {
     JSON json = null;
     if (method != null)
       json = (JSON)method.getAnnotation(JSON.class);
     if (this.formatter == null) {
       this.formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
     }
     DateFormat formatter = (json != null) && (json.format().length() > 0) ? new SimpleDateFormat(json.format()) : this.formatter;
 
     string(formatter.format(date));
   }
 
   private void array(Iterator it, Method method)
     throws JSONException
   {
     add("[");
 
     boolean hasData = false;
     for (int i = 0; it.hasNext(); i++) {
       String expr = null;
       if (this.buildExpr) {
         expr = expandExpr(i);
         if (shouldExcludeProperty(expr)) {
           it.next();
         }
         else
           expr = setExprStack(expr);
       } else {
         if (hasData) {
           add(',');
         }
         hasData = true;
         value(it.next(), method);
         if (this.buildExpr) {
           setExprStack(expr);
         }
       }
     }
     add("]");
   }
 
   private void array(Object object, Method method)
     throws JSONException
   {
     add("[");
 
     int length = Array.getLength(object);
 
     boolean hasData = false;
     for (int i = 0; i < length; i++) {
       String expr = null;
       if (this.buildExpr) {
         expr = expandExpr(i);
         if (!shouldExcludeProperty(expr))
         {
           expr = setExprStack(expr);
         }
       } else { if (hasData) {
           add(',');
         }
         hasData = true;
         value(Array.get(object, i), method);
         if (this.buildExpr) {
           setExprStack(expr);
         }
       }
     }
     add("]");
   }
 
   private void bool(boolean b)
   {
     add(b ? "true" : "false");
   }
 
   private void string(Object obj)
   {
     add('"');
 
     CharacterIterator it = new StringCharacterIterator(obj.toString());
 
     for (char c = it.first(); c != 65535; c = it.next()) {
       if (c == '"')
         add("\\\"");
       else if (c == '\\')
         add("\\\\");
       else if (c == '/')
         add("\\/");
       else if (c == '\b')
         add("\\b");
       else if (c == '\f')
         add("\\f");
       else if (c == '\n')
         add("\\n");
       else if (c == '\r')
         add("\\r");
       else if (c == '\t')
         add("\\t");
       else if (Character.isISOControl(c))
         unicode(c);
       else {
         add(c);
       }
     }
 
     add('"');
   }
 
   private void add(Object obj)
   {
     this.buf.append(obj);
   }
 
   private void add(char c)
   {
     this.buf.append(c);
   }
 
   private void unicode(char c)
   {
     add("\\u");
 
     int n = c;
 
     for (int i = 0; i < 4; i++) {
       int digit = (n & 0xF000) >> 12;
 
       add(hex[digit]);
       n <<= 4;
     }
   }
 
   public void setIgnoreHierarchy(boolean ignoreHierarchy) {
     this.ignoreHierarchy = ignoreHierarchy;
   }
 
   public void setEnumAsBean(boolean enumAsBean)
   {
     this.enumAsBean = enumAsBean;
   }
 }


 
 
