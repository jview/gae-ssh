package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.domain.UserRole;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRole BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IUserRoleBiz extends IBaseBIZ {

	public void saveUserRole(UserRole userRole);

	public UserRole findUserRole(Long id);

	public Collection<UserRole> findBy(UserRole userRole);

	public IPage<UserRole> findPageBy(QueryObj queryObj);

	public void deleteUserRoles(String ids);
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<UserRole> findRecently(final Long moduleId);

}
