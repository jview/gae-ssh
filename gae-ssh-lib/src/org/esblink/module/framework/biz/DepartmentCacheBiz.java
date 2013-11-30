package org.esblink.module.framework.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.util.Constants;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.organization.domain.Department;
import org.springframework.stereotype.Service;

final public class DepartmentCacheBiz {

	private static DepartmentCacheBiz manager = new DepartmentCacheBiz();

	private static final Log logger = LogFactory
			.getLog(DepartmentCacheBiz.class);

	private ICache cache;

	private DepartmentCacheBiz() {
		cache = CacheManager.getInstance().getCache(Constants.DEPARTMENT_CACHE);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static Map<String, Map> getDepartmentCacheMap() {
		Map<String, Map> departmentsMap = (Map<String, Map>) manager.cache
				.getData("");
		return departmentsMap;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<String, Department> getCodeMap() {
		Map<String, Map> map = getDepartmentCacheMap();
		Map<String, Department> departments = null;
		if (null == map) {
			logger.error(
					"can not load departments cache from ehcache storage.",
					new RuntimeException(
							"can not load departments cache from ehcache storage."));
		}
		else{
			departments=(Map<String, Department>) map.get("CODE");
		}
		return departments;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Map<Long, Department> getIdMap() {
		Map<String, Map> map = getDepartmentCacheMap();
		Map<Long, Department> departments = null;
		if (null == map) {
			logger.error(
					"can not load departments cache from ehcache storage.",
					new RuntimeException(
							"can not load departments cache from ehcache storage."));
		}
		else{
			departments = (Map<Long, Department>) map
				.get("ID");
		}
		return departments;
	}

	@SuppressWarnings("rawtypes")
	public static Department getRoot() {
		Map map = getDepartmentCacheMap();
		Department dept = null;
		if (null == map) {
			logger.error(
					"can not load departments cache from ehcache storage.",
					new RuntimeException(
							"can not load departments cache from ehcache storage."));
		}
		else{
			dept=(Department) map.get("ROOT");
		}
		return dept;
	}

	public static Department getByCode(String deptCode) {
		if (deptCode == null)
			return null;
		Map<String, Department> codeMap = getCodeMap();
		return codeMap.get(deptCode);
	}

	public static Collection<Department> getAllDepartment() {
		Map<String, Department> codeMap = getCodeMap();
		return Collections.unmodifiableCollection(codeMap.values());
	}

	public static Department getById(long id) {
		Map<Long, Department> idMap = getIdMap();
		return idMap.get(id);
	}

	public static List<Department> getByIds(List<Long> ids) {
		if (ids == null)
			return new ArrayList<Department>(0);
		Map<Long, Department> idMap = getIdMap();
		List<Department> res = new ArrayList<Department>(ids.size());
		for (Long id : ids) {
			Department dept = idMap.get(id);
			if (dept != null)
				res.add(dept);
		}
		return res;
	}

	public static List<Department> getByCodes(Collection<String> codes) {
		if (codes == null)
			return new ArrayList<Department>(0);

		Map<String, Department> codeMap = getCodeMap();
		List<Department> res = new ArrayList<Department>(codes.size());
		for (String code : codes) {
			Department dept = codeMap.get(code);
			if (dept != null)
				res.add(dept);
		}
		return res;
	}

	/**
	 * 获得所有的下级网点代码（不包括自己）
	 * 
	 * @param deptCode
	 * @return
	 */
	public static Collection<String> getAllChildrenCode(String deptCode) {
		Collection<String> res = new HashSet<String>();
		Department dept = getByCode(deptCode);
		if (dept != null) {
			List<Department> children = dept.getChildren();
			for (Department d : children) {
				res.add(d.getDeptCode());
				res.addAll(getAllChildrenCode(d.getDeptCode()));
			}
		}
		return res;
	}

	public static List<Department> getChildrenByCode(String deptCode) {
		Department dept = getByCode(deptCode);
		if (dept != null)
			return Collections.unmodifiableList(dept.getChildren());
		else
			return new ArrayList<Department>(0);
	}

	public static List<Department> getChildrenById(long id) {
		Department dept = getById(id);
		if (dept != null)
			return Collections.unmodifiableList(dept.getChildren());
		else
			return new ArrayList<Department>(0);
	}

	public static DepartmentDto convertToDto(Department dept) {
		if (dept == null)
			return null;
		DepartmentDto dto = new DepartmentDto();
		dto.setId(dept.getId());
		dto.setDeptCode(dept.getDeptCode());
		dto.setDeptName(dept.getDeptName());
		dto.setParentCode(dept.getParentCode());
		return dto;
	}

	public static List<DepartmentDto> convertToDto(Collection<Department> depts) {
		List<DepartmentDto> res = null;
		if (depts == null)
			res = new ArrayList<DepartmentDto>(0);
		else {
			res = new ArrayList<DepartmentDto>(depts.size());
			for (Department dept : depts) {
				DepartmentDto dto = convertToDto(dept);
				if (dto != null)
					res.add(dto);
			}
		}
		return res;
	}

	public static List<DepartmentDto> convertToDto(Department... depts) {
		Collection<Department> coll = new ArrayList<Department>(depts.length);
		for (Department dept : depts) {
			coll.add(dept);
		}
		return convertToDto(coll);
	}
}
