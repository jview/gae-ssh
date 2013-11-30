package org.esblink.module.framework.biz;

import java.util.Map;

import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.domain.IRole;
import org.esblink.common.util.Constants;

public class RoleCacheBiz {

	private static ICache cache;
	
	static{
		cache = CacheManager.getInstance().getCache(Constants.MODULE_CACHE);
	}
	
	@SuppressWarnings("unchecked")
	public IRole getById(long id){
		Map<Long, IRole> hm = (Map<Long, IRole>) cache.getData("");
		return hm.get(id);
	}
}
