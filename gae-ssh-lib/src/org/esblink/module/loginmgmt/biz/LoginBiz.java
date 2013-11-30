/*
 **********************************************
 * Copyright esblink.net.
 * All rights reserved.
 */

package org.esblink.module.loginmgmt.biz;

import java.text.DecimalFormat;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.Monitor;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.biz.IRoleBiz;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.exception.AuthorizeException;
import org.esblink.module.auth.exception.ErrorPasswordException;
import org.esblink.module.auth.exception.IllPasswordException;
import org.esblink.module.auth.exception.IllegalUserNameException;
import org.esblink.module.auth.exception.InvalidUserException;
import org.esblink.module.auth.exception.NotExistUserException;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.loginmgmt.util.Auth;

import com.esblink.dev.util.CommMethod;

/**
 * 入口业务实现
 * 
 * @author jview
 * 
 */
public class LoginBiz extends BaseBIZ implements ILoginBiz {
	private IUserBiz userBiz;
	private IRoleBiz roleBiz;
	@Resource(name="initBiz")
	private IInitBiz initBiz;

	public void checkPassword(String username, String password) {
		if (null == username)
			throw new IllegalUserNameException();

		if (null == password)
			throw new IllPasswordException();

		username = username.toLowerCase();
		User user = userBiz.findUserByName(username);

		if (null == user)
			throw new NotExistUserException();

		if (user.getStatus().equals(UserStatus.DISABLE)
				|| user.getStatus().equals(UserStatus.FORBIDDEN))
			throw new InvalidUserException();

		checkPassword(user, password);
	}

	private void checkPassword(User user, String password) {
		//log.info("-----checkPassword--"+user.getEmpCode()+" password="+password);
		if(!CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN))){
			password = this.userBiz.encrypt(user.getEmpCode(), password);
//			log.info("-----checkPassword--"+user.getEmpCode()+" password="+password+" userPwd="+user.getPassword());
			if (user.getPassword() == null || !user.getPassword().equalsIgnoreCase(password)) {
				throw new ErrorPasswordException();
			}
		}
		else{
//			DomainUserAuthUtil.authUser(user.getEmpCode(), password);
			Auth auth =new Auth();
			auth.authUser(user.getEmpCode(), password);
		}
		user.setPassword(password);
	}
	
	/**
	 * 修改密码
	 *
	 * @param loginIp
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @throws AuthorizeException
	 */
	public void changePassword(String loginIp, Long userId, String oldPassword, String newPassword) throws AuthorizeException{
		long start = System.nanoTime();
		// 用户不能为空
		if (null == userId || userId.equals(0L))
			throw new AuthorizeException();
		// 密码不能为空并且必需大于六个字符
		if (null == oldPassword || oldPassword.length() < 6)
			throw new AuthorizeException();
		if (null == newPassword || newPassword.length() < 6)
			throw new AuthorizeException();
		User u = userBiz.getUserById(userId);
		// 用户名不能为空
		if (null == u)
			throw new AuthorizeException();
		if (!this.userBiz.encrypt(u.getEmpCode(), oldPassword).equalsIgnoreCase(
				u.getPassword()))
			throw new AuthorizeException();

//		try {
//			this.userBiz.modUserPassword(newPassword, u.getEmpCode());
//		} catch (Exception e) {
//			throw new AuthorizeException(e.toString());
//		}
		if (log.isDebugEnabled())
			log.debug("[profile]SecurityBiz : changePassword user_id is "
					+ u.getId()
					+ "  "
					+ new DecimalFormat("###,##0").format(System.nanoTime()
							- start) + " ns");
	}
	
    public List<UserRoleDto> loadRoleByUsername(String username) {
    	return roleBiz.loadRoleByUsername(username);
    }
    
    public Long loadDefaultRoleByUsername(String username){
    	List<UserRoleDto> userRoles= this.loadRoleByUsername(username);
    	boolean isDefault = false;
    	if(userRoles!=null){
	    	for(UserRoleDto ur : userRoles){
	    		if(ur.getIsDefault() != null && ur.getIsDefault() == 1){
	    			isDefault = true;
	    			return ur.getId();
	    		}
	    	}
	    	if(!userRoles.isEmpty() && !isDefault){
	    		return userRoles.get(0).getId();
	    	}
    	}
    	
    	return null;
    }
    
    /**
	 *  根据username查用户信息，并检查用户状态
	 * @param username
	 * @return
	 * @throws AuthorizeException
	 */
	public User findUserByNo(String username) throws AuthorizeException{
		User user = userBiz.findUserByNo(username);
		if (null == user){
			if(this.initBiz!=null && username.equals("esblink")){
				this.initBiz.initAll();
			}
			throw new NotExistUserException();
		}
//		try {
//			this.userBiz.modUserPassword("esblink123", username);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// 用户处于无效状态不能登录
		if (UserStatus.DISABLE == user.getStatus())
			throw new InvalidUserException();
		return user;		
	}

	public User login(String loginIp, String username, String password)
			throws AuthorizeException {
		Monitor.beginBiz();
		long start = System.nanoTime();
		if (null == username)
			throw new IllegalUserNameException();

		if (null == password)
			throw new IllPasswordException();
		username = username.toLowerCase(); // 忽略大写小

		User user = userBiz.findUserByName(username);
		// 用户不存在
		if (null == user){
			throw new NotExistUserException();
			
		}

		// 用户处于无效状态不能登录
		if (user.getStatus().equals(UserStatus.DISABLE)
				|| user.getStatus().equals(UserStatus.FORBIDDEN))
			throw new InvalidUserException();

		checkPassword(user, password);

		if (log.isDebugEnabled())
			log.debug("[profile]SecurityBiz:"
					+ new DecimalFormat("###,##0").format(System.nanoTime()
							- start) + " ns");

		Monitor.endBiz();
		return user;
	}

	public void logout(IUser user) throws AuthorizeException {
		UserContext.getContext().removeUserIdAndRoleId();
	}

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public void setRoleBiz(IRoleBiz roleBiz) {
		this.roleBiz = roleBiz;
	}
	
}
