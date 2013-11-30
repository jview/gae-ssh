package org.esblink.module.loginmgmt.util;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.module.auth.exception.ErrorPasswordException;
import org.esblink.module.loginmgmt.exception.DomainUserAuthException;

public class DomainUserAuthUtil {
	private static Log logger = LogFactory.getLog(DomainUserAuthUtil.class);
	public final static String DomainUserAuth_FILE = "net/esblink/module/loginmgmt/META-INF/config/domainUserAuth.properties";
	private static Properties pros = null;
	private static String ENTRY_NAME = "";
	private static String Ldap_Path = "";
	static {
		try {
			InputStream is = DomainUserAuthUtil.class.getClassLoader()
					.getResourceAsStream(DomainUserAuth_FILE);
			pros = new Properties();
			pros.load(is);
			ENTRY_NAME = pros.getProperty("entry_name");
			Ldap_Path = pros.getProperty("ldap_path");

		} catch (Exception e) {
			logger.error("init exception:", e);
		}

	}

	public static void authUser(String userName, String passWd) {

//		try {
//			Hashtable<String, String> env = new Hashtable<String, String>();
//			env.put(Context.INITIAL_CONTEXT_FACTORY,
//					"com.sun.jndi.ldap.LdapCtxFactory");
//			env.put(Context.PROVIDER_URL, Ldap_Path); // login the ldap server
//			String domainUserName = "sf\\" + userName; // 注意用户名的写法：domain\User 或
//
//			env.put(Context.SECURITY_PRINCIPAL, domainUserName);
//			env.put(Context.SECURITY_CREDENTIALS, passWd);
//
//			new InitialDirContext(env);
//
//		} catch (AuthenticationException e) {
//
//			throw new ErrorPasswordException();
//
//		} catch (NamingException e) {
//
//			throw new DomainUserAuthException();
//		}

	}

	public static void main(String args[]) {
		DomainUserAuthUtil.authUser("164372", "password");
		System.out.println("OK");

	}

}