package org.esblink.module.auth.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.UserStatus;

/**
 * 用户帐号实体
 */
/**
 * esblink
 */
@SuppressWarnings("serial")
@Entity
public class User extends BaseEntity implements Cloneable {
	public User(){
		
	}
	public User(User user){
		this.setId(user.getId());
		this.username=user.getUsername();
		this.empCode=user.getEmpCode();
		this.empName=user.getEmpName();
		this.deptCode=user.getDeptCode();
		this.deptId=user.getDeptId();
		this.statusFlag=user.getStatusFlag();
	}

	private static final long serialVersionUID = 5368545246936510333L;

	private String username;
	private String password;
	@Column(name="status_flag")
	private String statusFlag;
	@Column(name="dept_code")
	private String deptCode;
	@Column(name="emp_code")
	private String empCode;
	@Column(name="emp_name")
	private String empName;
	@Column(name="create_emp_code")
	private String createEmpCode;
	@Column(name="update_emp_code")
	private String updateEmpCode;
	@Column(name="create_tm")
	private Date createTm;
	@Column(name="update_tm")
	private Date updateTm;
	@Column(name="dept_id")
	private Long deptId;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getCreateEmpCode() {
		return createEmpCode;
	}

	public void setCreateEmpCode(String createEmpCode) {
		this.createEmpCode = createEmpCode;
	}

	public String getUpdateEmpCode() {
		return updateEmpCode;
	}

	public void setUpdateEmpCode(String updateEmpCode) {
		this.updateEmpCode = updateEmpCode;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		// 忽略大写小
		this.username = (username == null ? null : username.toLowerCase());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		if ("root".equals(statusFlag))
			return UserStatus.ROOT;
		else if ("enable".equals(statusFlag))
			return UserStatus.ENABLE;
		else if ("disable".equals(statusFlag))
			return UserStatus.DISABLE;
		else if ("forbidden".equals(statusFlag))
			return UserStatus.FORBIDDEN;
		else
			return UserStatus.NONE;
	}

	public void setStatus(UserStatus status) {
		if (status == UserStatus.ROOT)
			setStatusFlag("root");
		else if (status == UserStatus.ENABLE)
			setStatusFlag("enable");
		else if (status == UserStatus.DISABLE)
			setStatusFlag("disable");
		else if (status == UserStatus.FORBIDDEN)
			setStatusFlag("forbidden");
		else
			setStatusFlag("none");
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			throw new InternalError(e.toString());
		}
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}
	
	
}
