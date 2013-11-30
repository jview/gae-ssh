package org.esblink.common.service.impl;
 
 import java.util.Map;
 
 public class MultiSecurityContext
   implements SecurityContext
 {
   private final LoginLevel loginLevel;
   private final Object identity;
   private final Permission permission;
   private final Map<String, Permission> permissions;
 
   public MultiSecurityContext(LoginLevel loginLevel, Object identity, Map<String, Permission> permissions)
   {
     this.loginLevel = loginLevel;
     this.identity = identity;
     this.permissions = permissions;
     this.permission = new MultiPermission(permissions.values());
   }
 
   public LoginLevel getLoginLevel() {
     return this.loginLevel;
   }
 
   public Object getIdentity() {
     return this.identity;
   }
 
   public Permission getPermission() {
     return this.permission;
   }
 
   public Permission getPermission(String name) {
     return (Permission)this.permissions.get(name);
   }
 }


 
 
