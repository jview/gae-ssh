package org.esblink.module.auth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseEntity;

@Entity
public class UserRoleDept extends BaseEntity{
	@Column(name="user_id")
	private Long userId;
	@Column(name="role_id")
	private Long roleId;
	@Column(name="dept_code")
	private String deptCode;
	@Column(name="inherited_flg")
	private Long inheritedFlg;
	
	@Column(name="create_user")
	private String createUser;
	@Column(name="update_user")
	private String updateUser;
	@Column(name="create_tm")
	private Date createTm;
	@Column(name="update_tm")
	private Date updateTm;
	@Column(name="if_del")
	private Long ifDel;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public Long getInheritedFlg() {
		return inheritedFlg;
	}
	public void setInheritedFlg(Long inheritedFlg) {
		this.inheritedFlg = inheritedFlg;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateTm() {
		return createTm;
	}
	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}
	public Date getUpdateTm() {
		return updateTm;
	}
	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}
	public Long getIfDel() {
		return ifDel;
	}
	public void setIfDel(Long ifDel) {
		this.ifDel = ifDel;
	}
	
	
}
