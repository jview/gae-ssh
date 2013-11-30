package org.esblink.module.auth.biz;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.auth.dao.IUserRoleDao;
import org.esblink.module.auth.domain.UserRole;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRole BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("userRoleBiz")
public class UserRoleBiz extends BaseBIZ implements IUserRoleBiz {

	// userRoleDao
	@Resource(name="userRoleDao")
	private IUserRoleDao userRoleDao;

	public void setUserRoleDao(IUserRoleDao userRoleDao) {
		this.userRoleDao = userRoleDao;
	}

	public void saveUserRole(UserRole userRole) {
		if (userRole.getCreateUser()==null) {
			if(super.getCurrentUser()!=null)
				userRole.setCreateUser(super.getCurrentUser().getCode());
			userRole.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null)
			userRole.setUpdateUser(super.getCurrentUser().getCode());
		userRole.setUpdateTm(new Date());
		if(userRole.getIfDel()==null){
			userRole.setIfDel(0l);
		}
		// save userRole
		this.userRoleDao.save(userRole);
	}

	public UserRole findUserRole(Long id) {
		// load userRole
		UserRole userRole = this.userRoleDao.load(id);

		return userRole;
	}

	public Collection<UserRole> findBy(UserRole userRole) {
		if (userRole == null) {
			userRole = new UserRole();
		} else {
			BeanUtils.clearEmptyProperty(userRole);
		}
		return this.userRoleDao.findBy(userRole);
	}

	public IPage<UserRole> findPageBy(QueryObj queryObj) {
		return this.userRoleDao.findPageBy(queryObj);
	}

	public void deleteUserRoles(String ids) {
		String[] idArray = ids.split(",");
		UserRole d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete UserRole
//			this.userRoleDao.remove(id);
			d=this.findUserRole(id);
			
			
		}
	}
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<UserRole> findRecently(final Long moduleId) {
		// TODO Auto-generated method stub
		return userRoleDao.findRecently(moduleId);
	}
}
