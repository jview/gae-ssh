package org.esblink.module.organization.biz;

import java.io.InputStream;
import java.util.Collection;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.organization.domain.Department;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Department1 BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.08.20     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface IDepartmentBiz extends IBaseBIZ {
	
//	public String 

	public void saveDepartment(Department employee);

	public Department findDepartment(Long empId);

	public Collection<Department> findBy(Department employee);

	public IPage<Department> findPageBy(QueryObj queryObj);

	public void deleteDepartments(String empIds);

	public InputStream exportDepartment(QueryObj queryObj);

}
