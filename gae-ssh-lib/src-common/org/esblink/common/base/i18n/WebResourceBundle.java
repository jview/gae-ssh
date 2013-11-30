package org.esblink.common.base.i18n;
 
 import java.io.FileInputStream;
 import java.util.PropertyResourceBundle;
 import java.util.ResourceBundle;
 
 public class WebResourceBundle extends PropertyResourceBundle
 {
   public WebResourceBundle(String name, ResourceBundle parent)
     throws Exception
   {
     super(new FileInputStream(name));
     super.setParent(parent);
   }
 }


 
 
