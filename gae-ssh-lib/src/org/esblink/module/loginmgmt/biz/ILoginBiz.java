/*
 **********************************************
 * Copyright esblink.net.
 * All rights reserved.
 */
package org.esblink.module.loginmgmt.biz;

import java.util.List;

import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.exception.AuthorizeException;

/**
 * 入口业务处理, 包括系统登录与退出
 *
 *
 */
public interface ILoginBiz extends IBaseBIZ {

	/**
	 * 系统登录
	 *
	 * @param username 用户名
	 * @param password 密码
	 * @return 登录后用户信息
	 * @throws AuthorizeException 登录失败时抛出
	 */
	public User login(String loginIp,String username, String password) throws AuthorizeException;
	/**
	 *  根据username查用户信息，并检查用户状态
	 * @param username
	 * @return
	 * @throws AuthorizeException
	 */
	public User findUserByNo(String username) throws AuthorizeException;

	/**
	 * 退出登录
	 */
	public void logout(IUser user) throws AuthorizeException;
	
	void checkPassword(String username, String password);
	
    public List<UserRoleDto> loadRoleByUsername(String username);
    
    public Long loadDefaultRoleByUsername(String username);
    
    /**
	 * 修改密码
	 *
	 * @param loginIp
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @throws AuthorizeException
	 */
	public void changePassword(String loginIp, Long userId, String oldPassword, String newPassword) throws AuthorizeException;

}
