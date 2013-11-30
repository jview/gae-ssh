package org.esblink.module.loginmgmt.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.esblink.common.base.domain.ModuleApplyType;
import org.esblink.common.base.domain.ModuleType;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.module.auth.biz.IModuleBiz;
import org.esblink.module.auth.biz.IRoleBiz;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.biz.IUserRoleBiz;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.auth.domain.Role;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserRole;
import org.esblink.module.auth.util.DateUtils;
import org.esblink.module.organization.biz.IDepartmentBiz;
import org.esblink.module.organization.biz.IEmployeeBiz;
import org.esblink.module.organization.domain.Department;
import org.esblink.module.organization.domain.Employee;
import org.springframework.stereotype.Service;

@Service("initBiz")
public class InitBiz implements IInitBiz {
	private static Logger log4 = Logger.getLogger(InitBiz.class);
	@Resource(name="employeeBiz")
	private IEmployeeBiz employeeBiz;
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	@Resource(name="roleBiz")
	private IRoleBiz roleBiz;
	@Resource(name="userRoleBiz")
	private IUserRoleBiz userRoleBiz;
	@Resource(name="moduleBiz")
	private IModuleBiz moduleBiz;
	@Resource(name="departmentBiz")
	private IDepartmentBiz departmentBiz;
	
	public void initAll(){
		Collection list=this.roleBiz.findRoleByName("admin%");
		if(list.size()==0){
			this.initDept();
			this.initEmployee();
			this.initRole();
			this.initModule();
			this.initUser();
			this.initUserRole();
		}
		else{
			log4.warn("------initAll--admin exist:"+list.size());
		}
	}
	
	@Override
	public void initUser() {
		log4.info("------initUser--");
		// TODO Auto-generated method stub
		String[] userCodes={"esblink", "admin"};
		this.userBiz.addUsers(userCodes);
		try {
			log4.info("------initUser-admin password-");
			this.userBiz.modUserPassword("idp123456", "admin");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void initDept(){
		log4.info("------initDept--");
		Department dept = null;
		dept = new Department();
		dept.setCode("001");
		dept.setDeptName("esblink");
		dept.setDeleteFlg(false);
		dept.setValidDate(new Date());
		this.departmentBiz.saveDepartment(dept);
	}

	@Override
	public void initEmployee() {
		log4.info("------initEmployee--");
		// TODO Auto-generated method stub
		List<Employee> empList=new ArrayList();
		Employee emp;
		emp = new Employee();
		emp.setEmpCode("admin");
		emp.setEmpName("admin");
		emp.setDeptCode("001");
		emp.setEmpEmail("jviewes@gmail.com");
		emp.setEmpMobile("");
		this.employeeBiz.saveEmployee(emp);
		
		emp = new Employee();
		emp.setEmpCode("test");
		emp.setEmpName("test");
		emp.setDeptCode("001");
		emp.setEmpEmail("jview@esblink.com");
		emp.setEmpMobile("");
		
		emp = new Employee();
		emp.setEmpCode("esblink");
		emp.setEmpName("esblink");
		emp.setDeptCode("001");
		emp.setEmpEmail("jview@esblink.com");
		emp.setEmpMobile("");
		
		this.employeeBiz.saveEmployee(emp);
		
		
	}

	@Override
	public void initModule() {
		log4.info("------initModule--");
		// TODO Auto-generated method stub
		Module module = null;
		Long parentId=0l;
		Long moduleParentId=0l;
		module = new Module();
		module.setParentId(parentId);
		module.setName("esblink");
		module.setCode("esblink");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(0);
		module.setLeafValue(0);
		module.setType(ModuleType.ROOT);
		this.moduleBiz.saveModule(module);
		parentId=module.getId();
		
		module = new Module();
		module.setParentId(parentId);
		module.setName("basemodule");
		module.setCode("basemodule");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(0);
		module.setType(ModuleType.APPLICATION);
		this.moduleBiz.saveModule(module);
		parentId=module.getId();
		
		//basedata
		module = new Module();
		module.setParentId(parentId);
		module.setName("basedata");
		module.setCode("basedata");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(0);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		moduleParentId=module.getId();
		//basedata/codeTable
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("codeTable");
		module.setCode("codeTable");
		module.setAction("basedata/codeTable.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		
		//auth
		module = new Module();
		module.setParentId(parentId);
		module.setName("auth");
		module.setCode("auth");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(0);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		moduleParentId=module.getId();
		
		//auth/module
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("module");
		module.setCode("module");
		module.setAction("auth/moduleObj.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//auth/role
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("role");
		module.setCode("role");
		module.setAction("auth/role.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//auth/role
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("user");
		module.setCode("user");
		module.setAction("auth/user.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//customer
		module = new Module();
		module.setParentId(parentId);
		module.setName("customer");
		module.setCode("customer");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(0);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		moduleParentId=module.getId();
		
		//customer/userReport
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("userReport");
		module.setCode("userReport");
		module.setAction("customer/userReport.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//customer/contactInfo
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("contactInfo");
		module.setCode("contactInfo");
		module.setAction("customer/contactInfo.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//organization
		module = new Module();
		module.setParentId(parentId);
		module.setName("organization");
		module.setCode("organization");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(0);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		moduleParentId=module.getId();
		
		//organization/dept
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("department");
		module.setCode("department");
		module.setAction("organization/dept.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
		//organization/employee
		module = new Module();
		module.setParentId(moduleParentId);
		module.setName("employee");
		module.setCode("employee");
		module.setAction("organization/employee.action");
		module.setApplyType(ModuleApplyType.WEB);
		module.setShowType(1);
		module.setLeafValue(1);
		module.setType(ModuleType.MENU);
		this.moduleBiz.saveModule(module);
		
	}

	@Override
	public void initRole() {
		log4.info("------initRole--");
		// TODO Auto-generated method stub
		Role role = null;
		role = new Role();
		role.setCode("admin");
		role.setName("admin");
		this.roleBiz.saveOrUpdateRole(role);
	}
	
	
	
	
	
//	@Override
	public void initUserRole() {
		log4.info("------initUserRole--");
		// TODO Auto-generated method stub
		
		List<Role> roleList = this.roleBiz.findRoleByName("admin");
		User user = this.userBiz.findUserByName("admin");
		if(user!=null && roleList!=null && roleList.size()>0){
			Role role = roleList.get(0);
//			this.userBiz.addUserRole("admin", role.getId(), DateUtils.reduceDay(new Date(), -100l));
			UserRole ur = new UserRole();
			ur.setRoleId(role.getId());
			ur.setUserId(user.getId());
			ur.setIfDel(0l);
			ur.setUnusedTm(DateUtils.reduceDay(new Date(), -100l));
			this.userRoleBiz.saveUserRole(ur);
		}
	}

}
