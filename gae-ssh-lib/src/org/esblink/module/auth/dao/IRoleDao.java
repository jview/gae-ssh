package org.esblink.module.auth.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.domain.Role;

public interface IRoleDao extends IBaseDAO<Role> {

	List<Long[]> getAllRoleModuleIds();

	void saveRoleModule(final long roleId, final List<Long> moduleIds);
	
	/**
	 * 更新角色具有的权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleModules(Role role) throws Exception;

	/**
	 * Object[]中第一个是roleId，第二个是失效时间，失效时间如果为空则表示永不失效。
	 * 
	 * @param userId
	 * @return
	 */
	List<Object[]> getUserRoleIds(final long userId);

	List<Role> loadByIds(List<Long> ids);
	
	List<Long> findRoleModuleIds(final Long roleId);
	
	List<UserRoleDto> loadUserRole(long userId);
	
	void saveUserRoleDept(long userId, long roleId, List<UserRoleDeptDto> userRoleDeptList);

	List<UserRoleDeptDto> getUserRoleDept(final long userId, final long roleId);
	
	/**
	 * 根据角色名称查找角色
	 * @param roleName
	 * @return
	 */
	List<Role> findRolesByName(String roleName);

}
