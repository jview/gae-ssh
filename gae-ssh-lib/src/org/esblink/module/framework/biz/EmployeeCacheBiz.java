package org.esblink.module.framework.biz;

import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.domain.IUser;
import org.esblink.module.framework.util.Constants;
import org.esblink.module.organization.domain.Employee;

public class EmployeeCacheBiz {

	private static ICache cache;

	static {
		cache = CacheManager.getInstance().getCache(Constants.EMPLOYEE_CACHE);
	}

	private EmployeeCacheBiz() {
	}

	public static Employee getEmployee(String empCode) {
		return (Employee) cache.getData(empCode);
	}

	public static Employee getEmployee(IUser user) {
		return getEmployee(user.getEmpCode());
	}

}
