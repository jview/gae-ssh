package org.esblink.module.auth.action;

import java.util.Collection;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IRoleModuleBiz;
import org.esblink.module.auth.domain.RoleModule;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.springframework.stereotype.Controller;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: RoleModule Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.04.18     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("roleModuleAction")
public class RoleModuleAction extends BaseGridAction<RoleModule> {
	private String moduleCode="roleModule";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
	@Resource(name="roleModuleBiz")
	private IRoleModuleBiz roleModuleBiz;
	private RoleModule roleModule;
	private String ids;
	private String msg;
	private Collection<RoleModule> roleModules;
	private int total;

	public String saveRoleModule() {
		try {
			roleModuleBiz.saveRoleModule(roleModule);
//			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+roleModule.getId()+" code="+roleModule.getCode(),"", SUCCESS, roleModule);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "save"+modelCode, "id="+roleModule.getId()+" code="+roleModule.getCode(),FAILURE+":"+e.getMessage(), FAILURE, roleModule);
		}
		return SUCCESS;
	}

	public String findRoleModule() {
		roleModule = roleModuleBiz.findRoleModule(roleModule.getId());
		return SUCCESS;
	}

	public String findByRoleModule() {
		roleModules = roleModuleBiz.findBy(roleModule);
		return SUCCESS;
	}

	public String deleteRoleModules() {
		try {
			roleModuleBiz.deleteRoleModules(ids);
			this.msg="ok";
			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids, "delete ids for:"+ids, SUCCESS, null);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "delete"+modelCode+"s", "ids="+ids,FAILURE+":"+e.getMessage(), FAILURE, null);
		}
		return SUCCESS;
	}

	public String findPageByRoleModule() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		
		IPage<RoleModule> page = roleModuleBiz.findPageBy(queryObj);
		roleModules = page.getData();
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setRoleModuleBiz(IRoleModuleBiz roleModuleBiz) {
		this.roleModuleBiz = roleModuleBiz;
	}

	public RoleModule getRoleModule() {
		return this.roleModule;
	}

	public void setRoleModule(RoleModule roleModule) {
		this.roleModule = roleModule;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<RoleModule> getRoleModules() {
		return this.roleModules;
	}
	
	public Collection<RoleModule> getRows() {
		return this.roleModules;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return this.total;
	}


	
}
