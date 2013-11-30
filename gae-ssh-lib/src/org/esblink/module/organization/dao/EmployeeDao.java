package org.esblink.module.organization.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.organization.domain.Employee;

/**
 * 职员Dao实现类
 * 
 */
public class EmployeeDao extends BaseDAO<Employee> implements
		IEmployeeDao {

	public Employee loadByCode(String empCode) {
		// return super.loadBy("empCode", empCode);
		Employee search = new Employee();
		search.setEmpCode(empCode);
		Collection<Employee> list=this.findBy(search);
		Employee emp = null;
		for(Employee e:list){
			emp = e;
			break;
		}
		return emp;
	}

	public List<Employee> loadByEmpName(String empName) {
//		DetachedCriteria detachedCriteria = DetachedCriteria
//				.forClass(Employee.class);
//		detachedCriteria.add(Restrictions.eq("empName", empName));
//		detachedCriteria.addOrder(Order.asc("empCode"));
//		return super.findBy(detachedCriteria);
		return null;
	}
	
	public List<Employee> findByEmpCodeList(List<String> empCodeList) {
		if(empCodeList==null||empCodeList.size()==0){
			return new ArrayList();
		}
//		DetachedCriteria detachedCriteria = DetachedCriteria
//				.forClass(Employee.class);
//		detachedCriteria.add(Restrictions.in("empCode", empCodeList));
//		detachedCriteria.addOrder(Order.asc("empCode"));
//		return super.findBy(detachedCriteria);
		return null;
	}
	
	public Collection<Employee> findBy(Employee entity){
		return super.findBy(entity, null, false);
	}
	
	public Collection<Employee> findBy(QueryObj queryObj) {
		return super.findBy(Employee.class, queryObj);
	}

	public IPage<Employee> findPageBy(QueryObj queryObj) {
		return super.findPageBy(queryObj);
	}


}
