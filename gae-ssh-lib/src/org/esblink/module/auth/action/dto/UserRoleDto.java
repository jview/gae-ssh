package org.esblink.module.auth.action.dto;

import org.esblink.module.auth.domain.UserRole;

/**
 * 用户的角色Bean对象
 * @author chenjh
 */
public class UserRoleDto extends RoleDto{
	public UserRoleDto(){
		
	}
	public UserRoleDto(UserRole ur){
		this.userId=ur.getUserId();
		this.setRoleId(ur.getRoleId());
		this.setId(ur.getRoleId());
		this.isDefault=ur.getIfDefault().byteValue();
		
	}
    private Long userId;
    private Byte isDefault;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return super.getId();
    }

    public void setRoleId(Long roleId) {
        super.setId(roleId);
    }

    public Byte getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Byte isDefault) {
        this.isDefault = isDefault;
    }

    public String getRoleName() {
        return super.getName();
    }

    public void setRoleName(String roleName) {
        super.setName(roleName);
    }

}
