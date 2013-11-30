package org.esblink.module.auth.action.dto;

public class UserRoleDeptDto {

	private String deptCode;
	private String deptName;
	private Byte inherited;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Byte getInherited() {
        return inherited;
    }

    public void setInherited(Byte inherited) {
        this.inherited = inherited;
    }
}
