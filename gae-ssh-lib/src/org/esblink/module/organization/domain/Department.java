package org.esblink.module.organization.domain;

/**********************************************
 * 部门实体类
 *
 * Copyright esblink.net.
 * All rights reserved.
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import org.esblink.common.base.BaseEntity;
import org.esblink.common.base.domain.IDepartment;
import org.esblink.module.organization.action.dto.DepartmentDto;

@SuppressWarnings("serial")
@Entity
public class Department extends BaseEntity implements IDepartment {
	/**
	 * 
	 */
	public Department(){
		
	}
	public Department(DepartmentDto dept){
		this.load(dept);
	}
	
	public void load(DepartmentDto dept){
		this.setId(dept.getId());
		this.code=dept.getDeptCode();
		this.deptName=dept.getDeptName();
		this.parentCode=dept.getParentCode();
		this.validDate=dept.getValidDate();
		this.deleteFlg=dept.getDeleteFlg();
		this.descs=dept.getDescs();
	}
	private static final long serialVersionUID = 721167073139344002L;
	@Basic
	private String code;
	@Basic
	@Column(name="dept_name")
	private String deptName;
	@Basic
	@Column(name="parent_code")
	private String parentCode;
	@Basic
	@Column(name="create_emp_code")
	private String createEmpCode;
	@Basic
	@Column(name="create_tm")
	private Date createTm;
	@Basic
	@Column(name="modified_emp_code")
	private String modifiedEmpCode;
	@Basic
	@Column(name="modified_tm")
	private Date modifiedTm;
	@Basic
	@Column(name="valid_date")
	private Date validDate;
	@Basic
	@Column(name="valid_time")
	private Date invalidTime;
	@Basic
	@Column(name="delete_flg")
	private Boolean deleteFlg;
	@Basic
	private String descs;
	
	@Transient
	private Department parent;

	@Transient
	private List<Department> children = new ArrayList<Department>();

	public String getException() throws Exception {
		throw new Exception("因为这里有parent和children，所以如果用这个类型做为json返回页面，必然会内存溢出");
	}

	public Department getParent() {
		return parent;
	}
	
	public void setParent(Department parent) {
		if (parent == null) {
			this.parent = null;
			this.parentCode = null;
		} else {
			parent.addChild(this);
		}
	}

	public void addChild(Department dept) {
		dept.parentCode = code;
		dept.parent = this;
		children.add(dept);
	}

	public List<Department> getChildren() {
		return children;
	}

	public java.lang.String getCode() {
		return code;
	}

	public void setCode(java.lang.String code) {
		this.code = code;
	}

	public java.lang.String getDeptName() {
		return deptName;
	}

	public void setDeptName(java.lang.String deptName) {
		this.deptName = deptName;
	}

	public java.lang.String getParentCode() {
		return parentCode;
	}

	public void setParentCode(java.lang.String parentCode) {
		this.parentCode = parentCode;
	}

	public java.lang.String getCreateEmpCode() {
		return createEmpCode;
	}

	public void setCreateEmpCode(java.lang.String createEmpCode) {
		this.createEmpCode = createEmpCode;
	}

	public java.util.Date getCreateTm() {
		return createTm;
	}

	public void setCreateTm(java.util.Date createTm) {
		this.createTm = createTm;
	}

	public java.lang.String getModifiedEmpCode() {
		return modifiedEmpCode;
	}

	public void setModifiedEmpCode(java.lang.String modifiedEmpCode) {
		this.modifiedEmpCode = modifiedEmpCode;
	}

	public java.util.Date getModifiedTm() {
		return modifiedTm;
	}

	public void setModifiedTm(java.util.Date modifiedTm) {
		this.modifiedTm = modifiedTm;
	}

	public java.util.Date getValidDate() {
		return validDate;
	}

	public void setValidDate(java.util.Date validDate) {
		this.validDate = validDate;
	}

	public java.util.Date getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(java.util.Date invalidTime) {
		this.invalidTime = invalidTime;
	}

	public java.lang.Boolean getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(java.lang.Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}

	public String getDeptCode() {
		return this.code;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}
	
}
