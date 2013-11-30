package org.esblink.module.loginmgmt.action;

import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.UserContext;

public class SwitchRoleAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -781609816814502452L;

	public String execute() {
		if(roleId != null)
			UserContext.getContext().setRoleId(roleId);
		return SUCCESS;
	}

	public Long roleId;

	public Long getRoleId() {
		return roleId;
	}

}
