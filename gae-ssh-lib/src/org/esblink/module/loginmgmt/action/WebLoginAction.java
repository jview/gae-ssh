/*
 **********************************************
 * Copyright esblink.net.
 * All rights reserved.
 *
 */
package org.esblink.module.loginmgmt.action;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.util.Monitor;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.exception.AuthorizeException;
import org.esblink.module.auth.exception.UserRoleException;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.esblink.module.loginmgmt.actionLoginFilter.LoginSessionContainer;
import org.esblink.module.loginmgmt.biz.ILoginBiz;
import org.esblink.module.loginmgmt.util.Auth;
import org.esblink.module.loginmgmt.util.MacThread;

import com.esblink.dev.util.CommMethod;

/**
 * 应用入口请求控制类, 包括登录, 退出.
 * 
 * 
 */
public class WebLoginAction extends BaseAction {

	private static final long serialVersionUID = 4233455390628430818L;
	private String moduleCode="login";
	private String modelCode = getClass().getSimpleName();
	private LogDb logDb = new LogDb(moduleCode, modelCode);
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	private static ExecutorService pool =null;

	// ---- Action ----

	public String index() throws Exception {
		return SUCCESS;
	}

	public String checkPassword() {
		String loginIp = ServletActionContext.getRequest().getRemoteAddr();
		String sessionId = ServletActionContext.getRequest().getSession()
				.getId();
		
		String remoteHost = ServletActionContext.getRequest().getRemoteHost();
		String remoteUser = ServletActionContext.getRequest().getRemoteUser();
		String remoteInfos = " loginIp="+loginIp+" remoteHost="+remoteHost+" remoteUser="+remoteUser;
		//如果是本机则不用查mac
		if (remoteHost.indexOf("127.0.0.1") >= 0||remoteHost.indexOf("0:0:0:0:0:0:0:1")>=0) {
			//none
		} else {			
			//查mac地址
//			log.debug("ip="+ips);
			if(CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_FIND_IP_MAC))){
				pool = Executors.newFixedThreadPool(CommMethod.getIntegerValue(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_FIND_IP_MAC_THREAD_COUNT)));
				pool.submit(new MacThread(username, "", remoteHost));					
			}
		} 
		
		if("true".equalsIgnoreCase(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_VALID_CODE)))
		{
        	HttpSession session = ServletActionContext.getRequest().getSession();//session
        	String randomCode=ServletActionContext.getRequest().getParameter("randomCode");
    		String l_randomCode = (String)session.getAttribute("RANDOMVALIDATECODEKEY");//session中验证码
    		if(l_randomCode==null || randomCode==null||  !l_randomCode.toUpperCase().equals(randomCode.toUpperCase()))
			{
            	logDbBiz.error(this.getCurrentUser(), logDb, "login", "login--username="+username, remoteInfos+"\n"+FAILURE+":验证码错误", FAILURE, this.getCurrentUser());
//            	throw new AuthorizeException("Authorize.illegal.randomCodeException");
//            	msg="Authorize.illegal.randomCodeException";
            	msg="8";
            	return SUCCESS;
			}
        }
		
		
		try {
			this.loginBiz.checkPassword(username, password);
			msg = "0";
//			System.out.println("-----loginId="+this.getId());
		} catch (AuthorizeException e) {
			if ("Authorize.not.exist.user".equals(e.getMessage())) {
				msg = "1";// 用户不存在
			} else if ("Authorize.invalid.user".equals(e.getMessage())) {
				msg = "2";// 用户被禁用
			} else if ("Authorize.illegal.username".equals(e.getMessage())) {
				msg = "3";// 用户名不合法
			} else if ("Authorize.illegal.password".equals(e.getMessage())) {
				msg = "4";// 密码不合法
			} else if ("Authorize.error.password".equals(e.getMessage())) {
				msg = "5";// 密码错误
			} else {
				msg = "6";// 系统错误
			}
			Object userId=ServletActionContext.getRequest().getSession().getAttribute(UserContext.USER_ID_KEY);
			if(userId!=null)
				logDbBiz.error(this.getCurrentUser(), logDb, "checkPassword", "checkPasswordError--username="+username, remoteInfos+"\n"+FAILURE+":"+e.getMessage(), FAILURE, this.getCurrentUser());
			else{
				logDbBiz.error(null, logDb, "checkPassword", "checkPasswordError--username="+username, remoteInfos+"\n"+FAILURE+":"+e.getMessage(), FAILURE, null);
			}
		}
		return SUCCESS;
	}

	/**
	 * 登录请求处理
	 * 
	 * 根据部署环境的不同，有不同的登录流程： * 1、台湾B/S登录 首先通过大陆环境的WEB SERVICE进行登录校验；
	 * S1若认证成功且密码过期，则在台湾本地环境下创建用户SESSION/CONTEXT，并导航重定向到大陆环境的密码修改页；
	 * S2若认证成功且密码不过期，则在台湾本地环境下创建用户SESSION/CONTEXT，并导航到台湾环境的子系统导航页；
	 * S3若认证失败，则重定向到登录页； S4若WEB
	 * SERVICE超时，则进行台湾本地登录校验（若成功，则不再作密码过期检查，直接导航到台湾环境的子系统导航页
	 * ），若密码错误，则增加一类异常，表示大陆不可连接且密码错误，提示用户可能需要用旧密码登录；
	 * 
	 * 2、大陆环境则仅作本地登录认证
	 * 
	 * @return 跳转索引
	 * @throws Exception
	 *             抛出所有非业务异常, 由框架统一处理
	 */
	public String login() throws Exception {
		Monitor.beginAction();
		if (app == null || app.length() == 0)
			app = ApplicationContext.getContext().getApplication().getName();

		String loginIp = ServletActionContext.getRequest().getRemoteAddr();
		String sessionId = ServletActionContext.getRequest().getSession()
				.getId();
		
		String remoteHost = ServletActionContext.getRequest().getRemoteHost();
		String remoteUser = ServletActionContext.getRequest().getRemoteUser();
		String remoteInfos = " loginIp="+loginIp+" remoteHost="+remoteHost+" remoteUser="+remoteUser;
		String empName=null;
		try {
			User user = null;
			Long roleId = null;
			
			if("true".equalsIgnoreCase(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_VALID_CODE)))
			{
	        	HttpSession session = ServletActionContext.getRequest().getSession();//session
	        	String randomCode=ServletActionContext.getRequest().getParameter("randomCode");
	    		String l_randomCode = (String)session.getAttribute("RANDOMVALIDATECODEKEY");//session中验证码
	    		if(l_randomCode==null || randomCode==null||  !l_randomCode.toUpperCase().equals(randomCode.toUpperCase()))
				{
	            	logDbBiz.error(this.getCurrentUser(), logDb, "login", "login--username="+username, remoteInfos+"\n"+FAILURE+":验证码错误", FAILURE, this.getCurrentUser());
//	            	throw new AuthorizeException("Authorize.illegal.randomCodeException");
//	            	msg="Authorize.illegal.randomCodeException";
	            	msg="8";
	            	return SUCCESS;
				}
	        }
			
			//检查用户在系统中是否存在
        	user = loginBiz.findUserByNo(username);
        	if(user!=null){      	
        		empName=user.getEmpName();
        	}
        	
        	//如果是本机则不用查mac
			if (remoteHost.indexOf("127.0.0.1") >= 0||remoteHost.indexOf("0:0:0:0:0:0:0:1")>=0) {
				//none
			} else {			
				//查mac地址
//				log.debug("ip="+ips);
				if(CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_FIND_IP_MAC))){
					pool = Executors.newFixedThreadPool(CommMethod.getIntegerValue(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_FIND_IP_MAC_THREAD_COUNT)));
					pool.submit(new MacThread(username, empName, remoteHost));					
				}
			} 
			
			if(!CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN))){
				user = loginBiz.login(loginIp, username, password);
				roleId = loginBiz.loadDefaultRoleByUsername(user.getUsername());
				UserContext.getContext().setUserIdAndRoleId(user.getId(), roleId);
				LoginSessionContainer.login(user.getId(), sessionId);
	
			}
			else{
				if(user==null){
	        		msg="1";
	        		logDbBiz.error(null, logDb, "login", "loginByDomain--username="+username, remoteInfos+"\n"+FAILURE+":帐户不存在", FAILURE, null);
	            	return FAILURE;
	        	}  
            	// 采用域统一认证
            	Auth auth =new Auth();
                if(username!=null&&auth.authUser(username, password)){
                	roleId = loginBiz.loadDefaultRoleByUsername(user.getUsername());
                	UserContext.getContext().setUserIdAndRoleId(user.getId(), roleId);
                	LoginSessionContainer.login(user.getId(), sessionId);
//                	log.info("---------auth--userId="+user.getId()+" roleId="+roleId);
                }
                else{
                	msg="5";
                	logDbBiz.error(null, logDb, "login", "loginByDomain--username="+username, remoteInfos+"\n"+FAILURE+":密码错误", FAILURE, null);
                	return FAILURE;
                }
			}
			IUser iuser = (IUser) UserContext.getContext().getUserCached(
					user.getId(), roleId);
			if (iuser == null)
				throw new UserRoleException();
			ConfigUtil.clearCacheData(user.getId());
			ServletActionContext.getRequest().getSession().setAttribute("login_userId", user.getId());
			msg = "0";
			
			logDbBiz.info(this.getCurrentUser(), logDb, "login", "login--username="+username, remoteInfos, SUCCESS, this.getCurrentUser());
			return SUCCESS;
		} catch (AuthorizeException e) {
			log.warn(e.getMessage());
			if ("Authorize.not.exist.user".equals(e.getMessage())) {
				msg = "1";// 用户不存在
			} else if ("Authorize.invalid.user".equals(e.getMessage())) {
				msg = "2";// 用户被禁用
			} else if ("Authorize.illegal.username".equals(e.getMessage())) {
				msg = "3";// 用户名不合法
			} else if ("Authorize.illegal.password".equals(e.getMessage())) {
				msg = "4";// 密码不合法
			} else if ("Authorize.error.password".equals(e.getMessage())) {
				msg = "5";// 密码错误
			} else if ("Authorize.illegal.userRole".equals(e.getMessage())) {
				msg = "7";
			} else {
				msg = "6";// 系统错误
			}
			logDbBiz.error(null, logDb, "login", "loginFail--username="+username, remoteInfos+"\n"+FAILURE+":"+e.getMessage(),FAILURE, null);
			// return FAILURE;
			return SUCCESS;
		} finally {
			Monitor.endAction();
		}
	}

	/**
	 * 退出登录请求处理
	 * 
	 * @return 跳转索引
	 * @throws Exception
	 *             抛出所有非业务异常, 由框架统一处理
	 */
	public String logout() throws Exception {
		IUser user = getCurrentUser();
		Long userId=null;
		if (null != user) {
			userId=user.getId();
			username = user.getUsername();
			String remoteHost = ServletActionContext.getRequest().getRemoteHost();
			String referer=ServletActionContext.getRequest().getHeader("referer");
			logDbBiz.info(this.getCurrentUser(), logDb, "logout", "username="+username+" remoteHost="+remoteHost+" referer="+referer, "", SUCCESS, this.getCurrentUser());
			loginBiz.logout(user);
		}
		
		// 防止session复用
		try {
			ConfigUtil.clearCacheData(userId);
			UserContext.getContext().removeUserIdAndRoleId();
			ServletActionContext.getRequest().getSession().invalidate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return SUCCESS;
	}

	// ---- Biz ----
	private ILoginBiz loginBiz;

	public void setLoginBiz(ILoginBiz loginBiz) {
		this.loginBiz = loginBiz;
	}

	// ---- Form ----

	private String username;
	private String password;
	private String app;
	private String msg;

	public String getMsg() {
		return msg;
	}

	public void setUsername(String username) {
		this.username = (username == null ? null : username.toLowerCase());
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getApp() {
		return app;
	}

	public void setApp(String app) {
		this.app = app;
	}

	public boolean isCurrentApplication() {
		return ApplicationContext.getContext().getApplication().getName()
				.equals(app);
	}
}
