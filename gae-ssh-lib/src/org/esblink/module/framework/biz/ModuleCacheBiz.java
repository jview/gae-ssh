package org.esblink.module.framework.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.util.Constants;
import org.esblink.module.framework.cache.ModuleCacheProvider;

public class ModuleCacheBiz {

	private static ICache cache;
	private static String app;

	static {
		cache = CacheManager.getInstance().getCache(Constants.MODULE_CACHE);
		app = ApplicationContext.getContext().getApplication().getName();
	}

	private ModuleCacheBiz() {
	}

	@SuppressWarnings("unchecked")
	private static Map<Long, IModule> getIdMap() {
		Map<String, Map<?, ?>> cacheObject = (Map<String, Map<?, ?>>) cache
				.getData(app);
		return (Map<Long, IModule>) cacheObject
				.get(ModuleCacheProvider.CACHE_TYPE_ID);
	}

	public static IModule getById(long id) {
		Map<Long, IModule> idMap = getIdMap();
		return idMap.get(id);
	}

	public static Collection<IModule> getAll() {
		return getIdMap().values();
	}

	public static List<IModule> getById(List<Long> ids) {
		if (ids == null)
			return new ArrayList<IModule>(0);
		Map<Long, IModule> idMap = getIdMap();
		List<IModule> res = new ArrayList<IModule>(ids.size());
		for (Long id : ids) {
			if (id != null) {
				IModule m = idMap.get(id);
				if (m != null)
					res.add(m);
			}
		}
		return res;
	}
}
