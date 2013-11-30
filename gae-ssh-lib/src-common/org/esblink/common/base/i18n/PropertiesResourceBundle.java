package org.esblink.common.base.i18n;
 
 import java.util.Enumeration;
 import java.util.Properties;
 import java.util.ResourceBundle;
 
 public class PropertiesResourceBundle extends ResourceBundle
 {
   private Properties properties;
 
   public PropertiesResourceBundle(Properties properties)
   {
     this.properties = (properties == null ? new Properties() : properties);
   }
 
   public Object handleGetObject(String key) {
     if (key == null)
       return null;
     return this.properties.get(key);
   }
 
   public Enumeration<String> getKeys()
   {
     ResourceBundle parent = this.parent;
     return new ResourceBundleEnumeration(this.properties.keySet(), parent != null ? parent.getKeys() : null);
   }
 
   public void setParent(ResourceBundle parent) {
     super.setParent(parent);
   }
 }


 
 
