package org.esblink.module.organization.biz;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.common.util.StringUtils;
import org.esblink.module.framework.excel.ExcelExport;
import org.esblink.module.framework.excel.TableDefine;
import org.esblink.module.organization.dao.IDepartmentDao;
import org.esblink.module.organization.domain.Department;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Department1 BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.08.20     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public class DepartmentBiz extends BaseBIZ implements IDepartmentBiz {

	// departmentDao
	private IDepartmentDao departmentDao;

	public void setDepartmentDao(IDepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	public void saveDepartment(Department department) {
		if (!StringUtils.isNotEmpty(department.getCreateEmpCode())) {
			if(super.getCurrentUser()!=null){
				department.setCreateEmpCode(super.getCurrentUser().getUsername());
			}
			department.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null){
			department.setModifiedEmpCode(super.getCurrentUser().getUsername());
		}
		department.setCreateTm(new Date());
		// save department
		if(department.getId()!=null && department.getId()>0){
			this.departmentDao.update(department);
		}
		else{
			this.departmentDao.save(department);
		}
	}

	public Department findDepartment(Long empId) {
		// load department
		Department department = (Department)this.departmentDao.find(Department.class, empId);

		return department;
	}

	public Collection<Department> findBy(Department department) {
		if (department == null) {
			department = new Department();
		} else {
			BeanUtils.clearEmptyProperty(department);
		}
		return this.departmentDao.findBy(department);
	}

	public IPage<Department> findPageBy(QueryObj queryObj) {
		return this.departmentDao.findPageBy(queryObj);
	}

	public void deleteDepartments(String empIds) {
		String[] empIdArray = empIds.split(",");
		Department d = null;
		for (String sempId : empIdArray) {
			Long empId = Long.parseLong(sempId);
			// delete Department1
//			this.departmentDao.remove(empId);
			d = this.findDepartment(empId);
			if(d!=null){
				
			}
		}
	}

	public InputStream exportDepartment(QueryObj queryObj) {
		try {
			for (String key : queryObj.getQueryObjects()) {
				String value = new String(((String)queryObj.getQueryObject(key)).getBytes("ISO-8859-1"), "UTF-8");
				queryObj.setQueryObject(key, value);
			}
			Collection<Department> data = departmentDao.findBy(queryObj);
			TableDefine table = new TableDefine(getMessage("department"));
			table.addColumn("empId", getMessage("department.empId"), 0);
			table.addColumn("empCode", getMessage("department.empCode"), 0);
			table.addColumn("empDutyName", getMessage("department.empDutyName"), 0);
			table.addColumn("empTypeName", getMessage("department.empTypeName"), 0);
			table.addColumn("empName", getMessage("department.empName"), 0);
			table.addColumn("empGender", getMessage("department.empGender"), 0);
			table.addColumn("empEmail", getMessage("department.empEmail"), 0);
			table.addColumn("empMobile", getMessage("department.empMobile"), 0);
			table.addColumn("empOfficephone", getMessage("department.empOfficephone"), 0);
			table.addColumn("empDesc", getMessage("department.empDesc"), 0);
			table.addColumn("deptCode", getMessage("department.deptCode"), 0);
			return new ExcelExport(table).export(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getMessage(String key) {
		return getMessageSource().getMessage(key, key);
	}
}
