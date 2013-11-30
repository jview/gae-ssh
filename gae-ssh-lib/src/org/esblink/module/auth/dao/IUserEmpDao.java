package org.esblink.module.auth.dao;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.domain.UserEmp;
public interface IUserEmpDao extends IBaseDAO<UserEmp> {
	
//	UserEmp loadByEmpCode(String empCode);
//	List<UserEmp> loadByEmpName(String empName);
//	List<UserEmp> loadByDeptCode(String deptCode);
    IPage<UserEmp> findPageBy(QueryObj queryObj);
}
