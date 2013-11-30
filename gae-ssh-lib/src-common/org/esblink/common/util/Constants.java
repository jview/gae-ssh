package org.esblink.common.util;

 
 import java.io.InputStream;
 import java.util.Collection;
 import java.util.HashSet;
 import java.util.Iterator;
 import java.util.Properties;
 import java.util.Set;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public class Constants
 {
   private static Logger logger = LoggerFactory.getLogger(Constants.class);
   public static final String USER_CACHE = "_user_cache_region";
   public static final String MODULE_CACHE = "_module_cache_region";
   public static final String ROLE_CACHE = "_role_cache_region";
   public static final String DEPARTMENT_CACHE = "_department_cache_region";
   public static final String APPLICATION_CACHE = "_application_cache_region";
   public static final String BASE_GLOSSARY_CACHE = "_glossary_cache_region";
   public static final String BASE_EXPRESS_TYPE_CACHE = "_express_type_cache_region";
   public static final String BASE_CARGO_TYPE_CACHE = "_cargo_type_cache_region";
   public static final String BASE_CURRENCY_TYPE_CACHE = "_currency_cache_region";
   public static final String BASE_DEPARTMENT_CACHE = "_base_dept_cache_region";
   public static final String BASE_LIMIT_TYPE_CACHE = "_limit_type_cache_region";
   public static final String BASE_TRANSPORT_TYPE_CACHE = "_transport_type_cache_region";
   public static final String BASE_DISTANCE_TYPE_CACHE = "_distance_type_cache_region";
   public static final String BASE_BAR_OPT_CODE_CACHE = "_bar_opt_code_cache_region";
   public static final String BASE_BAR_STAY_WHY_CODE_CACHE = "_bar_stay_cause_cache_region";
   public static final String BASE_DISTRICT_CACHE = "_district_cache_region";
   public static final Set<String> UNCHECD_ACTION_SET = new HashSet();
 
   static {
     try { InputStream is = Constants.class.getClassLoader().getResourceAsStream("uncheckAction.properties");
 
       Properties pros = new Properties();
       pros.load(is);
       Collection actions = pros.values();
       for (Iterator i$ = actions.iterator(); i$.hasNext(); ) { Object o = i$.next();
         if (o != null)
           UNCHECD_ACTION_SET.add((String)o);
       }
     }
     catch (Exception e)
     {
       Iterator i$;
       logger.error("", e);
       UNCHECD_ACTION_SET.add("/loginmgmt/index.action");
       UNCHECD_ACTION_SET.add("/loginmgmt/switch_locale.action");
       UNCHECD_ACTION_SET.add("/loginmgmt/login.action");
       UNCHECD_ACTION_SET.add("/loginmgmt/jlogin.action");
       UNCHECD_ACTION_SET.add("/loginmgmt/jlogout.action");
     }
   }
 }


 
 
