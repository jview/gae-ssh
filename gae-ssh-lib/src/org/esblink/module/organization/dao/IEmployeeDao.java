package org.esblink.module.organization.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.organization.domain.Employee;

public interface IEmployeeDao extends IBaseDAO<Employee> {

	Employee loadByCode(String empCode);

	List<Employee> loadByEmpName(String empName);
	
	public List<Employee> findByEmpCodeList(List<String> empCodeList);
	
	public Collection<Employee> findBy(QueryObj queryObj);
	
	public Collection<Employee> findBy(Employee entity);

	public IPage<Employee> findPageBy(QueryObj queryObj);

}
