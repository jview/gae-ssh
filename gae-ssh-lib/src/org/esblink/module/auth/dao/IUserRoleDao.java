package org.esblink.module.auth.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.domain.UserRole;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRole DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IUserRoleDao extends IBaseDAO<UserRole> {

	public Collection<UserRole> findBy(QueryObj queryObj);

	public IPage<UserRole> findPageBy(QueryObj queryObj);
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<UserRole> findRecently(final Long moduleId);

}
