package org.esblink.module.framework.cache;

import org.esblink.common.base.cache.ICacheDataProvider;
import org.esblink.module.organization.dao.IEmployeeDao;

public class EmployeeCacheProvider implements ICacheDataProvider {
	
	private IEmployeeDao employeeDao;

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public Object getData(Object key) {
		return employeeDao.loadByCode((String)key);
	}

}
