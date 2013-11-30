package org.esblink.module.framework.cache;

import java.util.HashMap;
import java.util.List;

import org.esblink.common.base.cache.ICacheDataProvider;
import org.esblink.common.base.domain.IRole;
import org.esblink.module.auth.dao.IRoleDao;
import org.esblink.module.auth.domain.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 角色缓存供给器 当用户尝试从缓存中获取角色时，
 *   如果需要获取的角色不存在于缓存中，则使用供给器从数据库总加载
 * </pre>
 */
public class RoleCacheProvider implements ICacheDataProvider {

	private static Logger logger = LoggerFactory.getLogger(RoleCacheProvider.class);

	private IRoleDao roleDao;

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public Object getData(Object key) {
		try {
			List<Role> roles = roleDao.findAll();
			if (roles == null) {
				logger.debug("Can not get Role from RoleCache");
				return null;
			}

			HashMap<Long, IRole> hm = new HashMap<Long, IRole>();
			for (Role role : roles) {
				if (role.getId() != null) {
					hm.put(role.getId(), role);
				}
			}

			List<Long[]> list = roleDao.getAllRoleModuleIds();
			for (Long[] itemes : list) {
				if (itemes != null && itemes.length == 2) {
					Long roleId = itemes[0];
					Long moduleId = itemes[1];
					Role r = (Role) hm.get(roleId);
					if (r != null) {
						r.addModuleId(moduleId);
					}
				}
			}
			return hm;
		} catch (Exception e) {
			logger.error("ERROR when getRole cache", e);
			return null;
		}
	}

}
