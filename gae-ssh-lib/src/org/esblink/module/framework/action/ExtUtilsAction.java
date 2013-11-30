package org.esblink.module.framework.action;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.module.framework.util.Constants;

public class ExtUtilsAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1673364880426152794L;

	private String newCSS;
	private String msg;

	public String getMsg() {
		return msg == null ? "ok" : msg;
	}

	public void setNewCSS(String newCSS) {
		this.newCSS = newCSS;
	}

	public String changeExtDress() {
		if ("blue".equals(newCSS)) {
			Cookie cookie = new Cookie(Constants.EXT_DRESS,
					Constants.EXT_BLUE_DRESS);
			cookie.setMaxAge(3600 * 24 * 30);
			cookie.setPath("/");
			ServletActionContext.getResponse().addCookie(cookie);
		} else if ("gray".equals(newCSS)) {
			Cookie cookie = new Cookie(Constants.EXT_DRESS,
					Constants.EXT_GRAY_DRESS);
			cookie.setMaxAge(3600 * 24 * 30);
			cookie.setPath("/");
			ServletActionContext.getResponse().addCookie(cookie);
		} else if ("access".equals(newCSS)) {
			Cookie cookie = new Cookie(Constants.EXT_DRESS,
					Constants.EXT_ACCESS_DRESS);
			cookie.setMaxAge(3600 * 24 * 30);
			cookie.setPath("/");
			ServletActionContext.getResponse().addCookie(cookie);
		} else if ("no".equals(newCSS)) {
			Cookie cookie = new Cookie(Constants.EXT_DRESS,
					Constants.EXT_NO_DRESS);
			cookie.setMaxAge(3600 * 24 * 30);
			cookie.setPath("/");
			ServletActionContext.getResponse().addCookie(cookie);
		} else
			msg = "err";
		return SUCCESS;
	}

}
