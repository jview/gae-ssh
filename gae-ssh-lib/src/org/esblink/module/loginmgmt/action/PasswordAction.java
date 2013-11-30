package org.esblink.module.loginmgmt.action;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.UserContext;
import org.esblink.module.loginmgmt.biz.ILoginBiz;

public class PasswordAction extends BaseAction {

	private static final long serialVersionUID = 1L;

    // ---- Biz ----
    private ILoginBiz loginBiz;

	public void setLoginBiz(ILoginBiz loginBiz) {
        this.loginBiz = loginBiz;
    }

	private String oldPassword;

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	private String newPassword;

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	private String redirect;

	public String getRedirect() {
		return redirect;
	}

	public void setRedirect(String redirect) {
		this.redirect = redirect;
	}

	private boolean expiry;

	public boolean isExpiry() {
		return expiry;
	}

	public void setExpiry(boolean expiry) {
		this.expiry = expiry;
	}

	private boolean error;

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String forward() throws Exception {
		if (userId == null) {
			if (UserContext.getContext().getCurrentUser().getId() == null)
				return FAILURE;
			userId = UserContext.getContext().getCurrentUser().getId();
		}
		return SUCCESS;
	}

	public String submit() throws Exception {
		String loginIp = ServletActionContext.getRequest().getRemoteHost();
		if (log.isDebugEnabled()) {
			log.debug("getUserId(): " + userId);
			log.debug("change password loginIp: " + loginIp);
		}
		try {
			loginBiz.changePassword(loginIp, userId, oldPassword, newPassword);
			return SUCCESS;
		} catch (Exception e) {
			error = true;
			return FAILURE;
		}
	}

	private Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
