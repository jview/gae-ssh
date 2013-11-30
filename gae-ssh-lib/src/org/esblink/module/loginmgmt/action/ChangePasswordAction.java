package org.esblink.module.loginmgmt.action;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.action.BaseAction;
import org.esblink.module.loginmgmt.biz.ILoginBiz;

public class ChangePasswordAction extends BaseAction {

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

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String change() throws Exception {
		log.info("-------change--");
		String loginIp = ServletActionContext.getRequest().getRemoteHost();
		if (log.isDebugEnabled()) {
			log.debug("getUserId(): " + super.getCurrentUser().getId());
			log.debug("loginIp: " + loginIp);
		}
		try {
			loginBiz.changePassword(loginIp, super.getCurrentUser().getId(), oldPassword, newPassword);
			success = true;
		} catch (Exception e) {
			success = false;
		}
		return SUCCESS;
	}

}
