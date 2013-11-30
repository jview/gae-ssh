package org.esblink.module.organization.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.organization.biz.IEmployeeBiz;
import org.esblink.module.organization.domain.Employee;

/**
 * 
 */
@SuppressWarnings("serial")
public class EmployeeAction extends BaseGridAction<Employee> {
//	@Resource(name="employeeBiz")
	private IEmployeeBiz employeeBiz;
	private Employee employee;
	private String empIds;
	private String msg;
	private Collection<Employee> employees;
	private int total;
	private InputStream stream;
	
	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @version 1.0 
	 */
	protected  void outJson(String xmlStr) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String saveEmployee() {
		try {
			System.out.println("---------saveEmployee--"+employee.getCode());
			employeeBiz.saveEmployee(employee);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findEmployee() {
		employee = employeeBiz.findEmployee(employee.getId());
		return SUCCESS;
	}

	public String findByEmployee() {
		employees = employeeBiz.findBy(employee);
		return SUCCESS;
	}

	public String deleteEmployees() {
		try {
			employeeBiz.deleteEmployees(empIds);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByEmployee() {
//		if(true){
//			outJson("{\"total\":28,\"employees\":[]}");
//			return null;
//		}
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		String order = ServletActionContext.getRequest().getParameter("order");
		boolean isAsc=false;
		if("asc".equals(order)){
			isAsc=true;
			queryObj.setAsc(isAsc);
		}
		try {
			queryObj.setQueryObject("validFlg", 1l);
			
			IPage<Employee> page = employeeBiz.findPageBy(queryObj);
//			System.out.println("page="+page.getData());
			employees = page.getData();
//		CommMethod.printListHasDate(employees);
			total = (int) page.getTotalSize();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return SUCCESS;
	}

	public String exportEmployee() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		stream = employeeBiz.exportEmployee(queryObj);
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

//	public IEmployeeBiz getEmployeeBiz() {
//		return employeeBiz;
//	}

	public void setEmployeeBiz(IEmployeeBiz employeeBiz) {
		this.employeeBiz = employeeBiz;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
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

	public Collection<Employee> getRows() {
		return employees;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

//	public void setEmployees(Collection<Employee> employees) {
//		this.employees = employees;
//	}

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
