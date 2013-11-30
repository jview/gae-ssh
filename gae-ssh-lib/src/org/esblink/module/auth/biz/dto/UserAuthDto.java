package org.esblink.module.auth.biz.dto;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.esblink.module.auth.action.dto.UserRoleDeptDto;

public class UserAuthDto {

	private long roleId;
	private Date unusedTm;
	private Map<String, UserRoleDeptDto> deptMap = new HashMap<String, UserRoleDeptDto>();

	public Map<String, UserRoleDeptDto> getDeptMap() {
		return deptMap;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Date getUnusedTm() {
		return unusedTm;
	}

	public void setUnusedTm(Date unusedTm) {
		this.unusedTm = unusedTm;
	}

	public void putDeptAuth(UserRoleDeptDto dto) {
		if (dto == null)
			return;
		if (dto.getDeptCode() == null)
			return;

		UserRoleDeptDto d = this.getDeptAuth(dto.getDeptCode());
		if (d != null) {
			if (dto.getInherited() != null && dto.getInherited().intValue() == 1){
				d.setInherited((byte)1);
			}
		} else
			deptMap.put(dto.getDeptCode(), dto);
	}

	public UserRoleDeptDto getDeptAuth(String deptCode) {
		if (deptCode == null)
			return null;
		return deptMap.get(deptCode);
	}

}
