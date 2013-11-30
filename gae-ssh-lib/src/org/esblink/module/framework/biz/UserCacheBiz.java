package org.esblink.module.framework.biz;

import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.util.Constants;


public class UserCacheBiz {

	private static ICache cache;

	static {
		cache = CacheManager.getInstance().getCache(Constants.USER_CACHE);
	}

	public static void removeData(long userId, long roleId) {
		cache.removeData(userId + "_" + roleId);
	}
}
