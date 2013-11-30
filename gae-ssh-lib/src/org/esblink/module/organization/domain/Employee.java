package org.esblink.module.organization.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IDepartment;
import org.esblink.common.base.domain.IEmployee;

/**
 * 职员实体类
 *
 *
 */
@SuppressWarnings("serial")
@Entity
public class Employee extends BaseEntity implements IEmployee {

	private static final long serialVersionUID = -1179505761827919825L;

	/**
	 * 职员工号(职员编码)
	 */
	@Column(name="emp_code")
	private String empCode;

	/**
	 * 姓名
	 */
	@Column(name="emp_name")
	private String empName;

	/**
	 * 所属机构编码
	 */
	@Transient
	private Department dept;
	@Column(name="emp_duty_name")
	private String empDutyName;
	@Column(name="emp_type_name")
	private String empTypeName;
	@Column(name="emp_gender")
	private String empGender;
	@Column(name="emp_email")
	private String empEmail;
	@Column(name="emp_mobile")
	private String empMobile;
	@Column(name="emp_officephone")
	private String empOfficephone;
	@Column(name="emp_status")
	private String empStatus;
	@Column(name="register_date")
	private Date registerDate;
	@Column(name="logout_date")
	private Date logoutDate;
	@Column(name="emp_desc")
	private String empDesc;
	@Column(name="change_zone_time")
	private Date changeZoneTime;
	@Column(name="dept_code")
	private String deptCode;
	@Column(name="valid_flg")
	private Long validFlg;
	@Column(name="inner_flg")
	private Long innerFlg;
	
	// 更新人员
	@Column(name="update_emp")
	private String updateEmp;
	// 更新时间
	@Column(name="update_tm")
	private Date updateTm;
	// 创建人员
	@Column(name="create_emp")
	private String createEmp;
	// 创建时间
	@Column(name="create_tm")
	private Date createTm;

	

	public String getUpdateEmp() {
		return updateEmp;
	}

	public void setUpdateEmp(String updateEmp) {
		this.updateEmp = updateEmp;
	}

	public Date getUpdateTm() {
		return updateTm;
	}

	public void setUpdateTm(Date updateTm) {
		this.updateTm = updateTm;
	}

	public String getCreateEmp() {
		return createEmp;
	}

	public void setCreateEmp(String createEmp) {
		this.createEmp = createEmp;
	}

	public Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(Date createTm) {
		this.createTm = createTm;
	}

	public String getEmpTypeName() {
		return empTypeName;
	}

	public void setEmpTypeName(String empTypeName) {
		this.empTypeName = empTypeName;
	}

	public String getEmpGender() {
		return empGender;
	}

	public void setEmpGender(String empGender) {
		this.empGender = empGender;
	}

	public String getEmpMobile() {
		return empMobile;
	}

	public void setEmpMobile(String empMobile) {
		this.empMobile = empMobile;
	}

	public String getEmpStatus() {
		return empStatus;
	}

	public void setEmpStatus(String empStatus) {
		this.empStatus = empStatus;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getLogoutDate() {
		return logoutDate;
	}

	public void setLogoutDate(Date logoutDate) {
		this.logoutDate = logoutDate;
	}

	public String getEmpDesc() {
		return empDesc;
	}

	public void setEmpDesc(String empDesc) {
		this.empDesc = empDesc;
	}

	public Date getChangeZoneTime() {
		return changeZoneTime;
	}

	public void setChangeZoneTime(Date changeZoneTime) {
		this.changeZoneTime = changeZoneTime;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	

	public Long getValidFlg() {
		return validFlg;
	}

	public void setValidFlg(Long validFlg) {
		this.validFlg = validFlg;
	}

	public Long getInnerFlg() {
		return innerFlg;
	}

	public void setInnerFlg(Long innerFlg) {
		this.innerFlg = innerFlg;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getEmpOfficephone() {
		return empOfficephone;
	}

	public void setEmpOfficephone(String empOfficephone) {
		this.empOfficephone = empOfficephone;
	}

	public String getEmpDutyName() {
		return empDutyName;
	}

	public void setEmpDutyName(String empDutyName) {
		this.empDutyName = empDutyName;
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

	public Department getDept() {
		return dept;
	}

	public void setDept(Department dept) {
		this.dept = dept;
	}

	public IDepartment getDepartment() {
		return this.getDept();
	}

	public String getName() {
		return this.getEmpName();
	}
	public String getCode() {
		return this.getEmpCode();
	}


}