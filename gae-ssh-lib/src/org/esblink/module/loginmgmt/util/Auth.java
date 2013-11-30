package org.esblink.module.loginmgmt.util;
import org.apache.log4j.Logger;


public class Auth{
	private static Logger log4 = Logger.getLogger(Auth.class);
 
// public static final String ENTRY_NAME = "OU=esblink.net,DC=esblink,DC=net";
 
// public static final String Ldap_Path = "ldap://10.0.15.2:389/";
 
 public boolean authUser(String userName, String passWd)
 {
//     	Hashtable env = new Hashtable();
//     	env.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
//   		env.put(Context.PROVIDER_URL, ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN_LDAP));    //login the ldap server
//   		String domainUserName = "esblink\\" + userName;     //注意用户名的写法：domain\User 或 User@domain.com
//   		env.put(Context.SECURITY_PRINCIPAL, domainUserName);
//   		env.put(Context.SECURITY_CREDENTIALS, passWd);
//  
//  		try
//  		{
//   				DirContext ctx = new InitialDirContext(env);
//   				ctx.close();
//  		}      
//  		catch(NamingException ex)
//  		{
//  			log4.error("loginAuthDomain error "+userName+":"+ex.getExplanation());
//   			return false;
//  		}  
  		return true;
 }
}
