 package org.esblink.jsptag.core.tags.taglib;
 
 public class NotLoginTag extends LoginTag
 {
   private static final long serialVersionUID = -411193541125033507L;
 
   protected boolean hasLogin()
   {
     return !super.hasLogin();
   }
 }


 
 
