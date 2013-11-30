package org.esblink.module.framework.biz;

import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.module.framework.domain.FlyweightCacheUser;
import org.esblink.module.framework.util.Constants;

public class FlyweightUserCacheBiz {

	private static ICache cache;

	static {
		cache = CacheManager.getInstance().getCache(
				Constants.FLYWEIGHT_USER_CACHE_REGION);
	}

	static public FlyweightCacheUser getByUserId(long userId) {
		return (FlyweightCacheUser) cache.getData(userId);
	}
}
