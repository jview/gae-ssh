package org.esblink.module.auth.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.domain.RoleModule;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: RoleModule DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IRoleModuleDao extends IBaseDAO<RoleModule> {

	public Collection<RoleModule> findBy(QueryObj queryObj);

	public IPage<RoleModule> findPageBy(QueryObj queryObj);
	
	/**
	 * 根据用户或角色查询用户权限
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public List<RoleModule> findRoleModuleByRole(final Long roleId);

}
