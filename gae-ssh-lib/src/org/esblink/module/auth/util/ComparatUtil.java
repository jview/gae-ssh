package org.esblink.module.auth.util;

import java.util.Comparator;

import org.esblink.module.auth.action.dto.RoleDto;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.domain.Role;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.organization.domain.Department;

public class ComparatUtil {

	public static final Comparator<DepartmentDto> DepartmentDtoComparator = new Comparator<DepartmentDto>() {
		public int compare(DepartmentDto o1, DepartmentDto o2) {
			return o1.getDeptCode().compareTo(o2.getDeptCode());
		}
	};
	
	public static final Comparator<Department> DepartmentComparator = new Comparator<Department>(){
		public int compare(Department o1, Department o2) {
			return o1.getDeptCode().compareTo(o2.getDeptCode());
		}
	};

	public static final Comparator<Role> RoleComparator = new Comparator<Role>() {
		public int compare(Role o1, Role o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};

	public static final Comparator<RoleDto> RoleDtoComparator = new Comparator<RoleDto>() {
		public int compare(RoleDto o1, RoleDto o2) {
			return o1.getName().compareTo(o2.getName());
		}
	};

	public static final Comparator<UserRoleDeptDto> UserRoleDeptDtoComparator = new Comparator<UserRoleDeptDto>() {
		public int compare(UserRoleDeptDto o1, UserRoleDeptDto o2) {
			return o1.getDeptCode().compareTo(o2.getDeptCode());
		}
	};

}
