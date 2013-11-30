package org.esblink.module.auth.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.Page;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.context.UserContext;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserEmp;
import org.esblink.module.organization.biz.IEmployeeBiz;
import org.esblink.module.organization.domain.Employee;

//@Service("userEmpDao")
public class UserEmpDao extends BaseDAO<UserEmp> implements IUserEmpDao {

    private IUserDao userDao;
    private IEmployeeBiz employeeBiz;
    
    protected boolean ignoreAuthorizationCheck(){
        if(UserContext.getContext().getCurrentUser() != null){
            return UserStatus.ROOT.equals(UserContext.getContext().getCurrentUser().getStatus());
        }
        return false;
    }
    
    public IPage<UserEmp> findPageBy(final QueryObj queryObj){
        List<String> authDeptCodes = null;
        //在非admin登录的情况下，如果查询条件包含了网点部门，而且用户对改网点部门没有权限，则直接返回一个无值对象
        if (!ignoreAuthorizationCheck() && UserContext.getContext()!=null && UserContext.getContext().getCurrentUser()!=null) {
            authDeptCodes = UserContext.getContext().getCurrentUser().getAuthDepartmentCodes();
            if (queryObj.containsQueryField("deptCode") && !authDeptCodes.contains(queryObj.getQueryObject("deptCode"))) {
                return new Page<UserEmp>(new ArrayList<UserEmp>(), 0, queryObj.getPageSize(), queryObj.getPageIndex());
            }
        }
        
        Collection<User> userList=this.userDao.findBy(new User());
       
        queryObj.setQueryObject("validFlg", 1l);
        Employee empSearch = new Employee();
        empSearch.setValidFlg(1l);
        Collection<Employee> empList= this.employeeBiz.findBy(empSearch);
        //System.out.println("empList="+empList+" userList="+userList);
//                IPage<Employee> page=this.employeeBiz.findPageBy(queryObj);
//                System.out.println("empList="+empList.size()+" userList="+userList.size());
        List<UserEmp> userEmpList=new ArrayList();
        UserEmp userEmp = null;
        boolean isValid=false;
        for(Employee emp:empList){
        	userEmp = new UserEmp(emp);
        	isValid=false;
        	for(User user:userList){
        		if(!"disable".equals(user.getStatusFlag()) && user.getUsername().equals(emp.getEmpCode())){
        			isValid=true;
        			userEmp.setUserId(user.getId());
        			break;
        		}
        	}
        	userEmp.setIsValid(isValid);
        	userEmpList.add(userEmp);
        }
        
        return new Page<UserEmp>(userEmpList, userEmpList.size(), queryObj.getPageSize(), queryObj.getPageIndex());
    }

    public IUserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

	public void setEmployeeBiz(IEmployeeBiz employeeBiz) {
		this.employeeBiz = employeeBiz;
	}
    
    
}
