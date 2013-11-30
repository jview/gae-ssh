package org.esblink.module.framework.domain;

import java.util.List;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IEmployee;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.module.framework.biz.EmployeeCacheBiz;

public class CacheUser extends BaseEntity implements IUser {

	/**
	 *
	 */
	private static final long serialVersionUID = -2821343783649959605L;

	private FlyweightCacheUser cu;
	private Long roleId;
	public List<String> authDepartmentCodes;

	public void setCu(FlyweightCacheUser cu) {
		this.cu = cu;
	}

	public String getCode() {
		return cu.getEmpCode();
	}

	public Long getId() {
		return cu.getId();
	}

	public String getUsername() {
		return cu.getUsername();
	}

	public String getEmpCode() {
		return cu.getEmpCode();
	}

	public String getEmpName() {
		return cu.getEmpName();
	}

	public String getEmpEmail() {
		return cu.getEmpEmail();
	}

	public String getPassword() {
		return cu.getPassword();
	}

	public void setPassword(String password) {
		cu.setPassword(password);
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public UserStatus getStatus() {
		return cu.getStatus();
	}

	public String getDeptCode() {
		return cu.getDeptCode();
	}

	public List<String> getAuthDepartmentCodes() {
		return authDepartmentCodes;
	}

	public void setAuthDepartmentCodes(List<String> authDepartmentCodes) {
		this.authDepartmentCodes = authDepartmentCodes;
	}

	public IEmployee getEmployee() {
		return EmployeeCacheBiz.getEmployee(cu.getEmpCode());
	}

}
