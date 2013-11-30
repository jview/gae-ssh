 package org.esblink.jsptag.core.tags.taglib;
 
 public class NotPermissionTag extends PermissionTag
 {
   private static final long serialVersionUID = -1039547369619409951L;
 
   protected boolean hasPermission()
   {
     return !super.hasPermission();
   }
 }


 
 
