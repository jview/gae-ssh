package org.esblink.common.service.impl;
 
 public class AnonymousSecurityContext
   implements SecurityContext
 {
   private static final SecurityContext INSTANCE = new AnonymousSecurityContext();
 
   public static final SecurityContext getInstance()
   {
     return INSTANCE;
   }
 
   public LoginLevel getLoginLevel() {
     return LoginLevel.AnonymousLogin;
   }
 
   public Object getIdentity() {
     return null;
   }
 
   public Permission getPermission() {
     return AnonymousPermission.getInstance();
   }
 
   public Permission getPermission(String name) {
     return AnonymousPermission.getInstance();
   }
 }


 
 
