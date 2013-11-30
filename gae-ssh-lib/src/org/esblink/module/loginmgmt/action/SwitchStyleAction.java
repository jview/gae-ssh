package org.esblink.module.loginmgmt.action;

import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.UserContext;


public class SwitchStyleAction extends BaseAction {

	private static final long serialVersionUID = -1317924499914628959L;

	public String execute() {
		if(style != null)
			UserContext.getContext().setUserStyle(style);
		return SUCCESS;
	}

	public String style;

	public String getStyle() {
		return style;
	}

}
