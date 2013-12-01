package org.esblink.module.auth.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.biz.IUserRoleBiz;
import org.esblink.module.auth.domain.UserRole;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.springframework.stereotype.Controller;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRole Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("userRoleAction")
public class UserRoleAction extends BaseGridAction<UserRole> {
	private String moduleCode="userRole";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="userRoleBiz")
	private IUserRoleBiz userRoleBiz;
	private UserRole userRole;
	private String ids;
	private String msg;
	private Collection<UserRole> userRoles;
	private int total;

	public String saveUserRole() {
		try {
			userRoleBiz.saveUserRole(userRole);
//			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+userRole.getId()+" code="+userRole.getCode(),"", SUCCESS, userRole);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+userRole.getId()+" code="+userRole.getCode(),FAILURE+":"+e.getMessage(), FAILURE, userRole);
		}
		return SUCCESS;
	}

	public String findUserRole() {
		userRole = userRoleBiz.findUserRole(userRole.getId());
		return SUCCESS;
	}

	public String findByUserRole() {
		userRoles = userRoleBiz.findBy(userRole);
		return SUCCESS;
	}

	public String deleteUserRoles() {
		try {
			userRoleBiz.deleteUserRoles(ids);
			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids, "delete ids for:"+ids, SUCCESS, null);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids,FAILURE+":"+e.getMessage(), FAILURE, null);
		}
		return SUCCESS;
	}

	public String findPageByUserRole() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
		IPage<UserRole> page = userRoleBiz.findPageBy(queryObj);
		userRoles = page.getData();
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setUserRoleBiz(IUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}

	public UserRole getUserRole() {
		return this.userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<UserRole> getUserRoles() {
		return this.userRoles;
	}
	
	public Collection<UserRole> getRows() {
		return this.userRoles;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return this.total;
	}


	
}
