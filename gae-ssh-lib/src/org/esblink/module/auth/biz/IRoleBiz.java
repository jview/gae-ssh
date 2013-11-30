package org.esblink.module.auth.biz;

import java.util.List;

import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.action.dto.ModuleDto;
import org.esblink.module.auth.action.dto.RoleDto;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.domain.ModuleCheckVO;
import org.esblink.module.auth.domain.Role;

public interface IRoleBiz extends IBaseBIZ {

	List<Role> loadAllRole();

	List<RoleDto> loadAllRoleDto();

	List<UserRoleDto> loadRoleByUsername(String username);
	
	List<UserRoleDto> loadMyRole();
	
	/**
	 * 根据应用和角色获取该角色所具有的模块权限
	 * @param applicationId
	 * @param roleId
	 * @return
	 */
	List<ModuleCheckVO> findRoleModulesByModuleAndRole(long applicationId, Long roleId);
	


	/**
	 * 因为没有人可以配置ROOT用户的权限， 所以这个方法对ROOT用户不做特殊处理。 ROOT用户如果要取自己的角色就用loadMyRole()或loadAllRole()方法。
	 * 
	 * @param userId
	 * @return
	 */
	List<UserRoleDto> loadUserRole(long userId);

	List<UserRoleDto> loadUserRole(String empCode);

	ModuleDto loadRoleModule(Long roleId);

	String loadRoleModuleId(Long roleId);

	void saveOrUpdateRole(Role role);
	
	/**
	 * 复制一个角色，并保存到数据库中
	 * @param role
	 * @throws Exception
	 */
	public void copyRole(Role role) throws Exception;
	
	/**
	 * 通过角色名称查找角色
	 * @param roleName
	 * @return
	 */
	public List<Role> findRoleByName(String roleName);

	void deleteRole(long roleId);

	void saveRoleModule(Role role, List<Long> moduleIds);
}
