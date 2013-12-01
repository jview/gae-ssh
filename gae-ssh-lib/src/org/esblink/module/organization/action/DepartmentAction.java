package org.esblink.module.organization.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.organization.action.dto.DepartmentDto;
import org.esblink.module.organization.biz.IDepartmentBiz;
import org.esblink.module.organization.domain.Department;

/**
 * 
 */
@SuppressWarnings("serial")
public class DepartmentAction extends BaseGridAction<Department> {
//	@Resource(name="departmentBiz")
	private IDepartmentBiz departmentBiz;
	private DepartmentDto department;
	private String empIds;
	private String msg;
	private Collection<DepartmentDto> departments;
	private int total;
	private InputStream stream;

	public String saveDepartment() {
		try {
			Department dept = null;
			if(this.department.getId()!=null && this.department.getId()>0){
				dept = this.departmentBiz.findDepartment(this.department.getId());
				dept.load(this.department);
			}
			else{
				dept= new Department(this.department);
			}
			departmentBiz.saveDepartment(dept);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findDepartment() {
		Department dept = departmentBiz.findDepartment(department.getId());
		this.department=new DepartmentDto(dept);
		return SUCCESS;
	}

	public String findByDepartment() {
		Department search = new Department(this.department);
		Collection<Department> depts = departmentBiz.findBy(search);
		List<DepartmentDto> dtoList = new ArrayList();
		for(Department dept:depts){
			dtoList.add(new DepartmentDto(dept));
		}
		this.departments=dtoList;
		return SUCCESS;
	}

	public String deleteDepartments() {
		try {
			departmentBiz.deleteDepartments(empIds);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByDepartment() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		String order = ServletActionContext.getRequest().getParameter("order");
		boolean isAsc=false;
		if("asc".equals(order)){
			isAsc=true;
			queryObj.setAsc(isAsc);
		}
		try {
			queryObj.setQueryObject("deleteFlg", false);
			IPage<Department> page = departmentBiz.findPageBy(queryObj);
			Collection<Department> depts = page.getData();
			List<DepartmentDto> dtoList = new ArrayList();
			for(Department dept:depts){
				dtoList.add(new DepartmentDto(dept));
			}
			this.departments=dtoList;
//		CommMethod.printListHasDate(departments);
			total = (int) page.getTotalSize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public String exportDepartment() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		stream = departmentBiz.exportDepartment(queryObj);
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

//	public IDepartmentBiz getDepartmentBiz() {
//		return departmentBiz;
//	}

	public void setDepartmentBiz(IDepartmentBiz departmentBiz) {
		this.departmentBiz = departmentBiz;
	}

	public DepartmentDto getDepartment() {
		return department;
	}

	public void setDepartment(DepartmentDto department) {
		this.department = department;
	}

	public String getEmpIds() {
		return empIds;
	}

	public void setEmpIds(String empIds) {
		this.empIds = empIds;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Collection<DepartmentDto> getRows() {
		return departments;
	}

	public void setDepartments(Collection<DepartmentDto> departments) {
		this.departments = departments;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public InputStream getStream() {
		return stream;
	}

	public void setStream(InputStream stream) {
		this.stream = stream;
	}
}
