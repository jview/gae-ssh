package org.esblink.module.framework.cache;import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.esblink.common.base.cache.ICacheDataProvider;import org.esblink.common.base.domain.IModule;import org.esblink.module.auth.dao.IModuleDao;import org.esblink.module.auth.domain.Module;import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 模块缓存供给器 当用户尝试从缓存中获取模块时，
 *   如果需要获取的模块不存在于缓存中，则使用供给器从数据库总加载
 * </pre>
 */
public class ModuleCacheProvider implements ICacheDataProvider {

	private static Logger logger = LoggerFactory.getLogger(ModuleCacheProvider.class);

	public static final String CACHE_TYPE_URL = "URL";

	public static final String CACHE_TYPE_ID = "ID";

	public static final String CACHE_TYPE_CODE = "CODE";

	public static final String CACHE_TYPE_APP = "APPLICATION";
	// Module Dao
	private IModuleDao moduleDao;

	public Object getData(Object key) {
		try {
			if ((null == key) || (!key.getClass().isAssignableFrom(String.class))) {
				logger.warn("can not accept param [appCode] as " + key);
				return null;
			}

			String appCode = (String) key;
			Collection<Module> modules = getModulesFromDatabase(appCode);			System.out.println("--------appCode="+appCode+" modules list="+modules.size());
			Map<String, Map<?, ?>> cacheObject = new HashMap<String, Map<?, ?>>();
			Map<Long, IModule> idMap = prepareIDMap(modules);
			Map<String, IModule> urlMap = prepareURLMap(modules);
			Map<String, IModule> codeMap = prepareCodeMap(modules);
			Map<String, Module> appMap = getApplications();
			cacheObject.put(CACHE_TYPE_URL, urlMap);
			cacheObject.put(CACHE_TYPE_ID, idMap);
			cacheObject.put(CACHE_TYPE_APP, appMap);
			cacheObject.put(CACHE_TYPE_CODE, codeMap);
			return cacheObject;
		} catch (Exception e) {
			logger.error("get module cache failed", e);
			return null;
		}
	}

	/**
	 * 从数据库获取模块
	 * 
	 * @param moduleCode
	 * @return
	 */
	private Collection<Module> getModulesFromDatabase(String moduleCode) {
		Collection<Module> children = moduleDao.getModulesByCode(moduleCode);
		return children;
	}

	/**
	 * 封装以ID为key的模块缓存map
	 * 
	 * @param modules
	 * @return
	 */
	private Map<Long, IModule> prepareIDMap(Collection<Module> modules) throws Exception {
		Map<Long, IModule> modulesMap = new HashMap<Long, IModule>();
		for (Module module : modules) {
			if (null == module)
				continue;
//			modulesMap.put(module.getId(), module);
			//只加入要显示的菜单
			if(module.getShowType().intValue()==1){				for (Module m : modules) {					if(module.getParent()==null && module.getParentId()!=null && module.getParentId().longValue()==m.getId().longValue()){						module.setParent(m);						break;					}				}
				modulesMap.put(module.getId(), module);
			}
		}
		return modulesMap;
	}

	/**
	 * 封装以URL属性作为key的模块缓存map
	 * 
	 * @param modules
	 * @return
	 */
	private Map<String, IModule> prepareURLMap(Collection<Module> modules) throws Exception {
		Map<String, IModule> modulesMap = new HashMap<String, IModule>();
		for (Module module : modules) {
			if (null == module)
				continue;
			if (null == module.getUrl())
				continue;
			modulesMap.put(module.getUrl(), module);
		}
		return modulesMap;
	}

	private Map<String, IModule> prepareCodeMap(Collection<Module> modules) throws Exception {
		Map<String, IModule> modulesMap = new HashMap<String, IModule>();
		for (Module module : modules) {
			if (null == module)
				continue;
			if (null == module.getCode())
				continue;
			modulesMap.put(module.getCode(), module);
		}
		return modulesMap;
	}

	/**
	 * 获取type=2的模块
	 * 
	 * @return
	 */
	private Map<String, Module> getApplications() throws Exception {
		List<Module> modules = moduleDao.getApplicationTypeModules();
		Map<String, Module> hm = new HashMap<String, Module>();

		for (IModule module : modules) {
			if (null == module)
				continue;
			hm.put(module.getCode(), (Module) module);
		}
		return hm;
	}

	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
}
