package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.auth.dao.IRoleModuleDao;
import org.esblink.module.auth.domain.RoleModule;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: RoleModule BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
//@Service("roleModuleBiz")
public class RoleModuleBiz extends BaseBIZ implements IRoleModuleBiz {

	// roleModuleDao
//	@Resource(name="roleModuleDao")
	private IRoleModuleDao roleModuleDao;

	public void setRoleModuleDao(IRoleModuleDao roleModuleDao) {
		this.roleModuleDao = roleModuleDao;
	}

	public void saveRoleModule(RoleModule roleModule) {
		if (roleModule.getCreateUser()==null) {
			if(super.getCurrentUser()!=null)
				roleModule.setCreateUser(super.getCurrentUser().getCode());
			roleModule.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null)
			roleModule.setUpdateUser(super.getCurrentUser().getCode());
		roleModule.setUpdateTm(new Date());
		if(roleModule.getIfDel()==null){
			roleModule.setIfDel(0l);
		}
		// save roleModule
		this.roleModuleDao.save(roleModule);
	}

	public RoleModule findRoleModule(Long id) {
		// load roleModule
		RoleModule roleModule = this.roleModuleDao.load(id);

		return roleModule;
	}

	public Collection<RoleModule> findBy(RoleModule roleModule) {
		if (roleModule == null) {
			roleModule = new RoleModule();
		} else {
			BeanUtils.clearEmptyProperty(roleModule);
		}
		return this.roleModuleDao.findBy(roleModule);
	}

	public IPage<RoleModule> findPageBy(QueryObj queryObj) {
		return this.roleModuleDao.findPageBy(queryObj);
	}

	public void deleteRoleModules(String ids) {
		String[] idArray = ids.split(",");
		RoleModule d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete RoleModule
//			this.roleModuleDao.remove(id);
			d=this.findRoleModule(id);
			
			
		}
	}
	

	
	public List<RoleModule> getRoleModuleIds(final long roleId){
		List<RoleModule> urList=this.roleModuleDao.findRoleModuleByRole(roleId);
		return urList;
	}
	

}
