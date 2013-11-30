package org.esblink.module.organization.dao;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.organization.domain.Department;

public class DepartmentDao extends BaseDAO<Department> implements
		IDepartmentDao {
	public IPage<Department> findPageBy(QueryObj queryObj) {
		return super.findPageBy(queryObj);
	}
}
