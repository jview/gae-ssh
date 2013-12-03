package org.esblink.module.auth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseEntity;

@Entity
public class RoleModule extends BaseEntity{
	
	@Column(name="role_id")
	private Long roleId;
	@Column(name="module_id")
	private Long moduleId;
	
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
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public Long getModuleId() {
		return moduleId;
	}
	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
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
