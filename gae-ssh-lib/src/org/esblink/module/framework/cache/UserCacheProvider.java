package org.esblink.module.framework.cache;

import org.esblink.common.base.cache.ICacheDataProvider;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.module.auth.dao.IUserDao;
import org.esblink.module.framework.biz.FlyweightUserCacheBiz;
import org.esblink.module.framework.domain.CacheUser;
import org.esblink.module.framework.domain.FlyweightCacheUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 用户缓存供给器 当用户尝试从缓存中获取用户时，
 *   如果需要获取的用户不存在于缓存中，则使用供给器从数据库总加载
 * </pre>
 */
public class UserCacheProvider implements ICacheDataProvider {

	private static Logger logger = LoggerFactory
			.getLogger(UserCacheProvider.class);

	private IUserDao userDao;

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public Object getData(Object key) {
		if (key == null||key.equals("null")) {
			logger
					.debug("Can not get User from UserCache without assign a userId##roleId");
			return null;
		}

		try {
			String[] ks = ((String) key).split("_");
			if(ks[0].equals("null")){
				logger
				.debug("Can not get User from UserCache without assign a userId="+ks[0]+" for key="+key);
				return null;
			}
			Long userId = Long.parseLong((String) ks[0]);
			Long roleId = null;
			if (ks.length > 1) {
				roleId = Long.parseLong((String) ks[1]);
			}
			if (roleId == null)
				throw new RuntimeException(key+", role_id cannot be null");

			FlyweightCacheUser fcu = FlyweightUserCacheBiz.getByUserId(userId);
			if(fcu == null)
				return null;

			CacheUser cu = new CacheUser();
			cu.setRoleId(roleId);
			cu.setCu(fcu);
			if (!cu.getStatus().equals(UserStatus.ROOT))
				cu
						.setAuthDepartmentCodes(userDao.authDeptCodes(userId,
								roleId));

			return cu;
		} catch (Exception e) {
			logger.debug("Can not get User from UserCache.'user_id'_'role_id'["
					+ (String) key + "]", e);
			return null;
		}
	}
}
