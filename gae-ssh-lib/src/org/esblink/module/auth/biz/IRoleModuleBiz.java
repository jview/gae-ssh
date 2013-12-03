package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.domain.RoleModule;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: RoleModule BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IRoleModuleBiz extends IBaseBIZ {

	public void saveRoleModule(RoleModule roleModule);

	public RoleModule findRoleModule(Long id);

	public Collection<RoleModule> findBy(RoleModule roleModule);

	public IPage<RoleModule> findPageBy(QueryObj queryObj);

	public void deleteRoleModules(String ids);
	
	
	
	List<RoleModule> getRoleModuleIds(final long roleId);
	

}
