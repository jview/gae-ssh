package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.domain.UserRoleDept;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRoleDept BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IUserRoleDeptBiz extends IBaseBIZ {

	public void saveUserRoleDept(UserRoleDept userRole);

	public UserRoleDept findUserRoleDept(Long id);

	public Collection<UserRoleDept> findBy(UserRoleDept userRole);

	public IPage<UserRoleDept> findPageBy(QueryObj queryObj);

	public void deleteUserRoleDepts(String ids);
	
	public void saveUserRoleDept(long userId, long roleId, List<UserRoleDeptDto> userRoleDeptDeptList);

	List<UserRoleDeptDto> getUserRoleDept(final long userId, final long roleId);

}
