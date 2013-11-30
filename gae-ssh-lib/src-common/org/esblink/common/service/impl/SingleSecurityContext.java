package org.esblink.common.service.impl;
 
 public class SingleSecurityContext
   implements SecurityContext
 {
   private final LoginLevel loginLevel;
   private final Object identity;
   private final Permission permission;
 
   public SingleSecurityContext(LoginLevel loginLevel, Object identity, Permission permission)
   {
     this.loginLevel = loginLevel;
     this.identity = identity;
     this.permission = permission;
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
     return this.permission;
   }
 }


 
 
