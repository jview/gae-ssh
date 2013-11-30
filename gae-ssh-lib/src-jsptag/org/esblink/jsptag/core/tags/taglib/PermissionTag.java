 package org.esblink.jsptag.core.tags.taglib;
 
 import javax.servlet.jsp.JspException;

import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.security.ModuleContext;
import org.esblink.common.base.security.UserSecurity;
import org.esblink.jsptag.base.tags.BaseBodyTag;
 
 public class PermissionTag extends BaseBodyTag
 {
   private static final long serialVersionUID = -516656449548427206L;
   private String action;
 
   public String getAction()
   {
     return this.action;
   }
 
   public void setAction(String action) {
     this.action = action;
   }
 
   public String getCode() {
     return getAction();
   }
 
   public void setCode(String code) {
     setAction(code);
   }
 
   public int doStartTag() throws JspException
   {
     if (hasPermission()) {
       return 1;
     }
     return 0;
   }
 
   public int doEndTag()
     throws JspException
   {
     return 6;
   }
 
   protected boolean hasPermission() {
     if (this.action == null)
       return false;
     IModule m = ModuleContext.getModule(this.action);
     if (null == m) return true;
     return UserSecurity.hasPrivilege(UserContext.getContext().getCurrentUser(), m);
   }
 }


 
 
