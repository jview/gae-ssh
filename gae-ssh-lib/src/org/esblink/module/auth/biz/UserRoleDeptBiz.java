package org.esblink.module.auth.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.dao.IUserRoleDeptDao;
import org.esblink.module.auth.domain.UserRoleDept;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRoleDept BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
//@Service("userRoleDeptBiz")
public class UserRoleDeptBiz extends BaseBIZ implements IUserRoleDeptBiz {

	// userRoleDeptDao
//	@Resource(name="userRoleDeptDao")
	private IUserRoleDeptDao userRoleDeptDao;

	public void setUserRoleDeptDao(IUserRoleDeptDao userRoleDeptDao) {
		this.userRoleDeptDao = userRoleDeptDao;
	}

	public void saveUserRoleDept(UserRoleDept userRoleDept) {
		if (userRoleDept.getCreateUser()==null) {
			if(super.getCurrentUser()!=null)
				userRoleDept.setCreateUser(super.getCurrentUser().getCode());
			userRoleDept.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null)
			userRoleDept.setUpdateUser(super.getCurrentUser().getCode());
		userRoleDept.setUpdateTm(new Date());
		if(userRoleDept.getIfDel()==null){
			userRoleDept.setIfDel(0l);
		}
		// save userRoleDept
		this.userRoleDeptDao.save(userRoleDept);
	}

	public UserRoleDept findUserRoleDept(Long id) {
		// load userRoleDept
		UserRoleDept userRoleDept = this.userRoleDeptDao.load(id);

		return userRoleDept;
	}

	public Collection<UserRoleDept> findBy(UserRoleDept userRoleDept) {
		if (userRoleDept == null) {
			userRoleDept = new UserRoleDept();
		} else {
			BeanUtils.clearEmptyProperty(userRoleDept);
		}
		return this.userRoleDeptDao.findBy(userRoleDept);
	}

	public IPage<UserRoleDept> findPageBy(QueryObj queryObj) {
		return this.userRoleDeptDao.findPageBy(queryObj);
	}

	public void deleteUserRoleDepts(String ids) {
		String[] idArray = ids.split(",");
		UserRoleDept d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete UserRoleDept
//			this.userRoleDeptDao.remove(id);
			d=this.findUserRoleDept(id);
			
			
		}
	}
	

	
	public List<UserRoleDept> getUserRoleDeptIds(final long userId){
		List<UserRoleDept> urList=this.userRoleDeptDao.findUserRoleDeptByUserOrRole(userId, null);
		return urList;
		
	}
	
	public void saveUserRoleDept(long userId, long roleId, List<UserRoleDeptDto> userRoleDeptDeptList){
		List<UserRoleDept> urList=this.userRoleDeptDao.findUserRoleDeptByUserOrRole(userId, roleId);
		UserRoleDept userRoleDeptDept;
		boolean isExist=false;
		for(UserRoleDeptDto dto:userRoleDeptDeptList){
			isExist=false;
			for(UserRoleDept urd:urList){
				if(urd.getDeptCode().equals(dto.getDeptCode())){
					isExist=true;
					urd.setInheritedFlg(dto.getInherited().longValue());
					this.userRoleDeptDao.update(urd);
					break;
				}
			}
			if(!isExist){
				userRoleDeptDept = new UserRoleDept();
				userRoleDeptDept.setUserId(userId);
				userRoleDeptDept.setRoleId(roleId);
				userRoleDeptDept.setDeptCode(dto.getDeptCode());
				userRoleDeptDept.setInheritedFlg(dto.getInherited().longValue());
				this.userRoleDeptDao.save(userRoleDeptDept);
			}
		}
	}

	public List<UserRoleDeptDto> getUserRoleDept(final long userId, final long roleId){
//		return this.userRoleDeptDao.findUserRoleDeptByUserOrRole(userId, roleId);
		List<UserRoleDept> urList=this.userRoleDeptDao.findUserRoleDeptByUserOrRole(userId, roleId);
		List<UserRoleDeptDto> list = new ArrayList();
		UserRoleDeptDto urdDto = null;
		for(UserRoleDept urd:urList){
			urdDto = new UserRoleDeptDto(urd);
			list.add(urdDto);
		}
		
		return list;
		
	}
}
