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
import org.esblink.module.organization.dao.IEmployeeDao;
import org.esblink.module.organization.domain.Employee;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: Employee1 BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.08.20     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public class EmployeeBiz extends BaseBIZ implements IEmployeeBiz {

	// employeeDao
	private IEmployeeDao employeeDao;

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void saveEmployee(Employee employee) {
		if (!StringUtils.isNotEmpty(employee.getCreateEmp())) {
			if(super.getCurrentUser()!=null){
				employee.setCreateEmp(super.getCurrentUser().getUsername());
			}
			employee.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null){
			employee.setUpdateEmp(super.getCurrentUser().getUsername());
		}
		employee.setUpdateTm(new Date());
		if(employee.getValidFlg()==null){
			employee.setValidFlg(1l);
		}
		// save employee
		this.employeeDao.save(employee);
	}

	public Employee findEmployee(Long empId) {
		// load employee
		Employee employee = (Employee)this.employeeDao.find(Employee.class, empId);

		return employee;
	}

	public Collection<Employee> findBy(Employee employee) {
		if (employee == null) {
			employee = new Employee();
		} else {
			BeanUtils.clearEmptyProperty(employee);
		}
		return this.employeeDao.findBy(employee);
	}

	public IPage<Employee> findPageBy(QueryObj queryObj) {
		return this.employeeDao.findPageBy(queryObj);
	}

	public void deleteEmployees(String empIds) {
		String[] empIdArray = empIds.split(",");
		Employee d = null;
		for (String sempId : empIdArray) {
			Long empId = Long.parseLong(sempId);
			// delete Employee1
//			this.employeeDao.remove(empId);
			d = this.findEmployee(empId);
			if(d!=null){
				d.setEmpStatus("0");
				this.saveEmployee(d);
			}
		}
	}

	public InputStream exportEmployee(QueryObj queryObj) {
		try {
			for (String key : queryObj.getQueryObjects()) {
				String value = new String(((String)queryObj.getQueryObject(key)).getBytes("ISO-8859-1"), "UTF-8");
				queryObj.setQueryObject(key, value);
			}
			Collection<Employee> data = employeeDao.findBy(queryObj);
			TableDefine table = new TableDefine(getMessage("employee"));
			table.addColumn("empId", getMessage("employee.empId"), 0);
			table.addColumn("empCode", getMessage("employee.empCode"), 0);
			table.addColumn("empDutyName", getMessage("employee.empDutyName"), 0);
			table.addColumn("empTypeName", getMessage("employee.empTypeName"), 0);
			table.addColumn("empName", getMessage("employee.empName"), 0);
			table.addColumn("empGender", getMessage("employee.empGender"), 0);
			table.addColumn("empEmail", getMessage("employee.empEmail"), 0);
			table.addColumn("empMobile", getMessage("employee.empMobile"), 0);
			table.addColumn("empOfficephone", getMessage("employee.empOfficephone"), 0);
			table.addColumn("empDesc", getMessage("employee.empDesc"), 0);
			table.addColumn("deptCode", getMessage("employee.deptCode"), 0);
			return new ExcelExport(table).export(data);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String getMessage(String key) {
		return getMessageSource().getMessage(key, key);
	}
}
