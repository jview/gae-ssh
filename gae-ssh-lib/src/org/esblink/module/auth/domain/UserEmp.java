package org.esblink.module.auth.domain;

import javax.persistence.Entity;

import org.esblink.common.base.BaseDomain;
import org.esblink.module.organization.domain.Employee;


/**
 * esblink
 */
@SuppressWarnings("serial")
//@Entity
public class UserEmp extends BaseDomain{
	public UserEmp(){
		
	}
	public UserEmp(Employee emp){
		this.setId(emp.getId());
		this.empCode=emp.getEmpCode();
		this.empName=emp.getEmpName();
		this.deptCode=emp.getDeptCode();
		this.dutyName=emp.getEmpDutyName();
	}
//	private Long empId;
	private String empCode;
	private String empName;
	private String dutyName;
	private String deptCode;
	private Boolean isValid;
	private Long userId;

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

    public Boolean getIsValid() {
        return isValid;
    }

    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }

//    public Long getEmpId() {
//        return empId;
//    }
//
//    public void setEmpId(Long empId) {
//        this.empId = empId;
//    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

}
