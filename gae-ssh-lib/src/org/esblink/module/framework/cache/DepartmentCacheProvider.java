package org.esblink.module.framework.cache;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.esblink.common.base.cache.ICacheDataProvider;
import org.esblink.module.organization.dao.IDepartmentDao;
import org.esblink.module.organization.domain.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved. 
 * Description: 模块缓存供给器 当用户尝试从缓存中获取模块时，
 *   如果需要获取的模块不存在于缓存中，则使用供给器从数据库总加载
 * </pre>
 */
public class DepartmentCacheProvider implements ICacheDataProvider {

	private static Logger logger = LoggerFactory
			.getLogger(DepartmentCacheProvider.class);

	private final String CACHE_TYPE_CODE = "CODE";
	private final String CACHE_TYPE_ID = "ID";
	private final String CACHE_TYPE_ROOT = "ROOT";

	private IDepartmentDao deptDao;

	public void setDepartmentDao(IDepartmentDao deptDao) {
		this.deptDao = deptDao;
	}

	public Object getData(Object key) {
		try {
			logger.info("=============================Begin Load Department From DataBase In DepartmentCacheProvider=======================");
			Collection<Department> departments = deptDao.findAll();
			logger.info("=============================End Load Department From DataBase In DepartmentCacheProvider=======================");
			if (null == departments || departments.size() <= 0) {
				logger.error("Can not load departments from database",
						new RuntimeException(
								"Can not load departments from database"));
				return null;
			}

			return prepareCache(departments);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return null;
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map prepareCache(Collection<Department> departments) {

		Map cacheObject = new HashMap();
		Map<String, Department> codeMap = prepareCodeMap(departments);
		Map<Long, Department> idMap = prepareIdMap(codeMap);
		setParents(codeMap, departments);
		Department root = getRoot(codeMap);

		if (null == root) {
			logger.error("There hasn't root.", new RuntimeException(
					"departments must has a root which parent_code is null"));
			return null;
		}

		cacheObject.put(CACHE_TYPE_CODE, codeMap);
		cacheObject.put(CACHE_TYPE_ID, idMap);
		cacheObject.put(CACHE_TYPE_ROOT, root);

		return cacheObject;
	}

	private Map<Long, Department> prepareIdMap(Map<String, Department> codeMap) {
		Map<Long, Department> idMap = new HashMap<Long, Department>();
		for (Department dept : codeMap.values()) {
			if (dept == null)
				continue;
			idMap.put(dept.getId(), dept);
		}
		return idMap;
	}

	private Map<String, Department> prepareCodeMap(
			Collection<Department> departments) {
		Map<String, Department> departmentMap = new HashMap<String, Department>();
		for (Department department : departments) {
			if (null == department)
				continue;
			if (null == department.getDeptCode()
					|| "".equals(department.getDeptCode()))
				continue;
			departmentMap.put(department.getDeptCode(), department);
		}
		return departmentMap;
	}

	private void setParents(Map<String, Department> codeMap,
			Collection<Department> departments) {
		for (Department department : departments) {
			String parentCode = department.getParentCode();
			if (parentCode == null || parentCode.length() == 0) {
				department.setParent(null);
			} else {
				Department parent = codeMap.get(parentCode);
				department.setParent(parent);
			}
		}
	}

	private Department getRoot(Map<String, Department> codeMap) {
		for (Department dept : codeMap.values()) {
			String parentCode = dept.getParentCode();
			if (parentCode == null)
				return dept;
		}
		return null;
	}
}
