package org.esblink.module.auth.action.dto;

import org.esblink.module.auth.domain.UserRoleDept;

public class UserRoleDeptDto {
	public UserRoleDeptDto(){
		
	}
	public UserRoleDeptDto(UserRoleDept urd){
		this.deptCode=urd.getDeptCode();
		this.inherited=urd.getInheritedFlg().byteValue();
	}

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
