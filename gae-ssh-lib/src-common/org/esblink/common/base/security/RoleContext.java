 package org.esblink.common.base.security;
 
 import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.domain.IRole;
 
 public final class RoleContext
 {
   private static RoleContext context;
   private static final Log logger = LogFactory.getLog(RoleContext.class);
   private ICache cache;
 
   private static void init()
   {
     if (null == context)
       context = new RoleContext();
   }
 
   private RoleContext() {
     this.cache = CacheManager.getInstance().getCache("_role_cache_region");
   }
 
   public static Map<Long, IRole> get()
   {
     if (null == context)
       init();
     Map roles = (Map)context.cache.getData("_role_cache_region");
 
     return roles;
   }
 
   public static IRole getRole(Long id) {
     if ((null == id) || (0L == id.longValue())) {
       logger.error("not give role ID ", new RuntimeException("not give RoleID"));
     }
     Map roles = get();
     if (null != roles)
       return (IRole)roles.get(id);
     return null;
   }
 }


 
 
