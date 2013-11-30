package org.esblink.module.auth.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.module.auth.action.dto.ModuleDto;
import org.esblink.module.auth.action.dto.RoleDto;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.dao.IModuleDao;
import org.esblink.module.auth.dao.IRoleDao;
import org.esblink.module.auth.dao.IUserDao;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.auth.domain.ModuleCheckVO;
import org.esblink.module.auth.domain.Role;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserRole;
import org.esblink.module.auth.util.ComparatUtil;

//@Service("roleBiz")
public class RoleBiz extends BaseBIZ implements IRoleBiz {
//	@Resource(name="roleDao")
	private IRoleDao roleDao;
//	@Resource(name="moduleDao")
	private IModuleDao moduleDao;
//	@Resource(name="userDao")
	private IUserDao userDao;
	private IUserRoleBiz userRoleBiz;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}

	public void setUserRoleBiz(IUserRoleBiz userRoleBiz) {
		this.userRoleBiz = userRoleBiz;
	}

	public List<Role> loadAllRole() {
		List<Role> roles = roleDao.findAll();
		Collections.sort(roles, ComparatUtil.RoleComparator);
		return roles;
	}

	public List<RoleDto> loadAllRoleDto() {
		List<Role> roleList=roleDao.findAll();
		List<RoleDto> res = convertToDto(roleList, null);
		Collections.sort(res, ComparatUtil.RoleDtoComparator);
		return res;
	}

	public List<UserRoleDto> loadRoleByUsername(String username){
        User user = this.userDao.findUserByName(username);
        if (user == null)
            return new ArrayList<UserRoleDto>(0);

        if (UserStatus.ROOT.equals(user.getStatus())){
            List<UserRoleDto> result = new ArrayList<UserRoleDto>();
            for (RoleDto roleDto : loadAllRoleDto()) {
                UserRoleDto tmp = new UserRoleDto();
                tmp.setId(roleDto.getId());
                tmp.setDescription(roleDto.getDescription());
                tmp.setName(roleDto.getName());
                tmp.setUnusedTm(roleDto.getUnusedTm());
                tmp.setUserId(user.getId());
                tmp.setIsDefault((byte) 0);
                result.add(tmp);
            }
            return result;
        }

        return loadUserRole(user.getId());	    
	}
	
	public List<UserRoleDto> loadMyRole() {
		IUser user = super.getCurrentUser();
		if (UserStatus.ROOT.equals(user.getStatus())) {
		    List<UserRoleDto> result = new ArrayList<UserRoleDto>();
            for (RoleDto roleDto : loadAllRoleDto()) {
                UserRoleDto tmp = new UserRoleDto();
                tmp.setId(roleDto.getId());
                tmp.setDescription(roleDto.getDescription());
                tmp.setName(roleDto.getName());
                tmp.setUnusedTm(roleDto.getUnusedTm());
                tmp.setUserId(user.getId());
                tmp.setIsDefault((byte) 0);
                result.add(tmp);
            }
		    return result;
		}
		return loadUserRole(user.getId());
	}

	public List<UserRoleDto> loadUserRole(long userId) {
//	    return this.roleDao.loadUserRole(userId);
		UserRole search = new UserRole();
		search.setUserId(userId);
		search.setIfDel(0l);
		Collection<UserRole> urList=this.userRoleBiz.findBy(search);
		List<UserRoleDto> userRoleList = new ArrayList();
		UserRoleDto userRole = null;
		for(UserRole ur:urList){
			userRole = new UserRoleDto(ur);
			userRoleList.add(userRole);
		}
		return userRoleList;
	}
	
	public List<ModuleCheckVO> findRoleModulesByModuleAndRole(long applicationId,
			Long roleId) {
		List<Module> retList = this.moduleDao.findChildModules(applicationId);
		List<ModuleCheckVO> retCheckList = new ArrayList<ModuleCheckVO>();
		for(Module m:retList){
			retCheckList.add(new ModuleCheckVO(m));
		}
		List<Long> roleModulesIds = new ArrayList<Long>();
		if(roleId>0){
			roleModulesIds = this.roleDao.findRoleModuleIds(roleId);
		}
		for (Iterator<ModuleCheckVO> iterator = retCheckList.iterator(); iterator.hasNext();) {
			ModuleCheckVO module = iterator.next();
			for (Long tempId : roleModulesIds) {
				if (tempId.longValue() == module.getId()) {
					module.setChecked(true);
					break;
				}
			}
		}
		return retCheckList;
	}

	private List<RoleDto> convertToDto(List<Role> roleList, Map<Long, Date> unusedTmMap) {
		if (roleList == null)
			return new ArrayList<RoleDto>(0);
		List<RoleDto> res = new ArrayList<RoleDto>(roleList.size());
		for (Role role : roleList) {
			RoleDto dto = new RoleDto();
			dto.setId(role.getId());
			dto.setName(role.getName());
			dto.setDescription(role.getDescription());
			if (unusedTmMap != null)
				dto.setUnusedTm(unusedTmMap.get(role.getId()));
			res.add(dto);
		}
		return res;
	}

	public List<UserRoleDto> loadUserRole(String empCode) {
		User user = this.userDao.loadByEmpCode(empCode);
		if (user == null)
			return new ArrayList<UserRoleDto>(0);
		return this.loadUserRole(user.getId());
	}

	public ModuleDto loadRoleModule(Long roleId) {
		Set<Long> idSet = new HashSet<Long>();
		if (roleId != null) {
			List<Long> ml = moduleDao.loadModuleIdByRoleId(roleId);
			idSet.addAll(ml);
		}

		List<ModuleDto> allModule = moduleDao.loadDtoAll();
//		List<ModuleDto> tmpList = new ArrayList<ModuleDto>();
//		for(ModuleDto module:allModule){
//			if(module.getId()!=1l){
//				tmpList.add(module);
//			}
//		}
//		allModule = tmpList;

		Map<Long, ModuleDto> map = this.createModuleIdMap(allModule);
		ModuleDto moduleRoot = this.getModuleRoot(map);
		this.prepareRelationship(map);

		if (idSet.size() > 0) {
			for (ModuleDto module : allModule) {
				if (idSet.contains(module.getId())) {
					module.setChecked(true);
				}
				if (module.getChildren().size() == 0)
					module.setLeaf(true);
			}
		} else {
			for (ModuleDto module : allModule) {
				if (module.getChildren().size() == 0)
					module.setLeaf(true);
			}
		}

		return moduleRoot;
	}

	public String loadRoleModuleId(Long roleId) {
		List<Long> ids = moduleDao.loadModuleIdByRoleId(roleId);
		StringBuilder sb = new StringBuilder();
		for (Long id : ids) {
			sb.append(id).append(",");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	private void prepareRelationship(Map<Long, ModuleDto> map) {
		for (Entry<Long, ModuleDto> e : map.entrySet()) {
			ModuleDto module = e.getValue();
			if (module != null) {
				if (module.getParentId() != null) {
					ModuleDto pm = map.get(module.getParentId());
					if(pm!=null)
					pm.addChild(module);
				}
			}
		}
	}

	private ModuleDto getModuleRoot(Map<Long, ModuleDto> map) {
		for (Entry<Long, ModuleDto> e : map.entrySet()) {
			ModuleDto module = e.getValue();
			if (module != null) {
				if (module.getParentId() == null)
					return module;
				ModuleDto pm = map.get(module.getParentId());
				if (pm == null)
					return module;
			}
		}
		return null;
	}

	private Map<Long, ModuleDto> createModuleIdMap(List<ModuleDto> moduleList) {
		Map<Long, ModuleDto> map = new HashMap<Long, ModuleDto>();
		for (ModuleDto dto : moduleList)
			map.put(dto.getId(), dto);
		return map;
	}

	public void deleteRole(long roleId) {
//		this.roleDao.remove(roleId);
	}
	
	public void copyRole(Role role) throws Exception {
		// 先新增一条记录，然后再把模板后用户全部加到这个角色下面
		Long oldRoleid = role.getId();
		role.setId(null);
		List<Long> roleModuleIds = this.roleDao.findRoleModuleIds(oldRoleid);
		Set<Module> modules = new HashSet<Module>();
		for (Long moduleId : roleModuleIds) {
			Module module = new Module();
			module.setId(moduleId);
			module.setApplyTypeFlag(0);
			module.setTypeFlag(0);
			modules.add(module);
		}
		role.setRoleModules(modules);
		roleDao.save(role);
		this.roleDao.updateRoleModules(role);
	}
	
	public List<Role> findRoleByName(String roleName) {
		return this.roleDao.findRolesByName(roleName);
	}

	public void saveOrUpdateRole(Role role) {
		IUser user = super.getCurrentUser();
		if (role.getId() == null) {
			if(user!=null)
				role.setCreateEmpCode(user.getEmpCode());
			role.setCreateTm(new Date());
		}
		if(user!=null)
			role.setUpdateEmpCode(user.getEmpCode());
		this.roleDao.save(role);
	}

	public void saveRoleModule(Role role, List<Long> moduleIds) {
		this.roleDao.saveRoleModule(role.getId(), moduleIds);
		saveOrUpdateRole(role);
	}
}
