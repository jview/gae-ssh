package org.esblink.module.organization.action.dto;

import java.util.Date;

import org.esblink.module.organization.domain.Department;

public class DepartmentDto {

	private Long id;
	private String deptCode;
	private String parentCode;
	private String deptName;
	private Date validDate;
	private Boolean deleteFlg;
	private String descs;
	public DepartmentDto(){
		
	}
	public DepartmentDto(Department dept){
		this.id=dept.getId();
		this.deptCode=dept.getDeptCode();
		this.deptName=dept.getDeptName();
		this.validDate=dept.getValidDate();
		this.deleteFlg=dept.getDeleteFlg();
		this.descs=dept.getDescs();
		
	}

	public Long getId() {
		return id;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDescs() {
		return descs;
	}

	public void setDescs(String descs) {
		this.descs = descs;
	}

	public Date getValidDate() {
		return validDate;
	}

	public void setValidDate(Date validDate) {
		this.validDate = validDate;
	}

	public Boolean getDeleteFlg() {
		return deleteFlg;
	}

	public void setDeleteFlg(Boolean deleteFlg) {
		this.deleteFlg = deleteFlg;
	}
	

}
