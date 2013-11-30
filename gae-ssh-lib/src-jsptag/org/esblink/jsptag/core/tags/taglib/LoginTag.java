 package org.esblink.jsptag.core.tags.taglib;
 
 import javax.servlet.jsp.JspException;

import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IUser;
import org.esblink.jsptag.base.tags.BaseBodyTag;
 
 public class LoginTag extends BaseBodyTag
 {
   private static final long serialVersionUID = -9159817398463144347L;
 
   public int doStartTag()
     throws JspException
   {
     if (hasLogin()) {
       return 1;
     }
     return 0;
   }
 
   public int doEndTag()
     throws JspException
   {
     return 6;
   }
 
   protected boolean hasLogin() {
     IUser user = UserContext.getContext().getCurrentUser();
     if (this.log.isDebugEnabled()) {
       this.log.debug(getClass().getSimpleName() + " userID:" + user.getId() + "roleId:" + user.getRoleId());
     }
     return user != null;
   }
 }


 
 
