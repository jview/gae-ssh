package org.esblink.module.auth.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.biz.IUserRoleDeptBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserRoleDept;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRoleDept Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("userRoleDeptAction")
public class UserRoleDeptAction extends BaseGridAction<UserRoleDept> {
	private String moduleCode="userRoleDept";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="userRoleDeptBiz")
	private IUserRoleDeptBiz userRoleDeptBiz;
	private UserRoleDept userRoleDept;
	private String ids;
	private String msg;
	private Collection<UserRoleDept> userRoleDepts;
	private int total;

	public String saveUserRoleDept() {
		try {
			userRoleDeptBiz.saveUserRoleDept(userRoleDept);
//			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+userRoleDept.getId()+" code="+userRoleDept.getCode(),"", SUCCESS, userRoleDept);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+userRoleDept.getId()+" code="+userRoleDept.getCode(),FAILURE+":"+e.getMessage(), FAILURE, userRoleDept);
		}
		return SUCCESS;
	}

	public String findUserRoleDept() {
		userRoleDept = userRoleDeptBiz.findUserRoleDept(userRoleDept.getId());
		return SUCCESS;
	}

	public String findByUserRoleDept() {
		userRoleDepts = userRoleDeptBiz.findBy(userRoleDept);
		return SUCCESS;
	}

	public String deleteUserRoleDepts() {
		try {
			userRoleDeptBiz.deleteUserRoleDepts(ids);
			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids, "delete ids for:"+ids, SUCCESS, null);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids,FAILURE+":"+e.getMessage(), FAILURE, null);
		}
		return SUCCESS;
	}

	public String findPageByUserRoleDept() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
		IPage<UserRoleDept> page = userRoleDeptBiz.findPageBy(queryObj);
		userRoleDepts = page.getData();
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setUserRoleDeptBiz(IUserRoleDeptBiz userRoleDeptBiz) {
		this.userRoleDeptBiz = userRoleDeptBiz;
	}

	public UserRoleDept getUserRoleDept() {
		return this.userRoleDept;
	}

	public void setUserRoleDept(UserRoleDept userRoleDept) {
		this.userRoleDept = userRoleDept;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<UserRoleDept> getUserRoleDepts() {
		return this.userRoleDepts;
	}
	
	public Collection<UserRoleDept> getRows() {
		return this.userRoleDepts;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return this.total;
	}
	
	
}
