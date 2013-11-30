package org.esblink.module.auth.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.framework.biz.DepartmentCacheBiz;
import org.esblink.module.organization.domain.Department;

public class DepartmentUtil {

	public static List<Department> getRoots(List<String> deptCodes) {
		Map<String, Department> codeMap = getCodeMap(deptCodes);
		List<Department> res = new ArrayList<Department>(8);
		for (Department dept : codeMap.values()) {
			if (dept.getParent() == null
					|| (dept.getParent() != null && codeMap.get(dept
							.getParent().getDeptCode()) == null))
				res.add(dept);
		}
		return res;
	}

	public static List<Long> convertCodeToId(List<String> codes) {
		List<Long> res = new ArrayList<Long>(codes.size());
		for (String code : codes) {
			Department dept = DepartmentCacheBiz.getByCode(code);
			if (dept != null)
				res.add(dept.getId());
		}
		return res;
	}

	public static List<Department> getChildren(long parentId, List<String> codes) {
		Map<Long, Department> idMap = getIdMap(codes);
		Department parent = DepartmentCacheBiz.getById(parentId);
		List<Department> allChildren = parent.getChildren();
		List<Department> res = new ArrayList<Department>(allChildren.size());
		for (Department dept : allChildren) {
			if (idMap.containsKey(dept.getId()))
				res.add(dept);
		}
		return res;
	}

	public static Map<Long, Department> getIdMap(List<String> deptCodes) {
		Map<Long, Department> idMap = new HashMap<Long, Department>();
		for (String code : deptCodes) {
			Department dept = DepartmentCacheBiz.getByCode(code);
			if (dept != null)
				idMap.put(dept.getId(), dept);
		}
		return idMap;
	}

	public static Map<String, Department> getCodeMap(List<String> deptCodes) {
		Map<String, Department> codeMap = new HashMap<String, Department>();
		for (String code : deptCodes) {
			Department dept = DepartmentCacheBiz.getByCode(code);
			if (dept != null)
				codeMap.put(dept.getDeptCode(), dept);
		}
		return codeMap;
	}

	public static Collection<DepartmentDto> convertDeptName(
			Collection<DepartmentDto> dtoList) {
		if (dtoList != null)
			for (DepartmentDto dto : dtoList)
				dto.setDeptName(dto.getDeptCode() + "/" + dto.getDeptName());
		return dtoList;
	}

}
