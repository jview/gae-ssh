 package org.esblink.common.base.security;
 
 import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.IRole;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.UserStatus;
 
 public final class UserSecurity
 {
   private static final Log logger = LogFactory.getLog(UserSecurity.class);
 
   public static boolean hasPrivilege(IUser user, IModule module) {
     if (null == module)
       return true;
     if (null == user)
       return false;
     if (UserStatus.ROOT == user.getStatus())
       return true;
     Long roleId = user.getRoleId();
     if (null == roleId)
       return false;
     IRole r = RoleContext.getRole(roleId);
     if (null == r) {
       logger.error("why not found this role with ID [" + roleId + "]");
       return false;
     }
     if (r.constainsModule(module.getId()))
       return true;
     return false;
   }
 }


 
 
