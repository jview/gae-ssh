package org.esblink.module.organization.biz;

import java.io.InputStream;
import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.organization.domain.Employee;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Employee1 BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.08.20     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IEmployeeBiz extends IBaseBIZ {
	
//	public String 

	public void saveEmployee(Employee employee);

	public Employee findEmployee(Long empId);

	public Collection<Employee> findBy(Employee employee);

	public IPage<Employee> findPageBy(QueryObj queryObj);

	public void deleteEmployees(String empIds);

	public InputStream exportEmployee(QueryObj queryObj);

}
