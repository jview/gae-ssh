package org.esblink.module.auth.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.PagingParams;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseTreeAction;
import org.esblink.common.util.JsonUtils;
import org.esblink.module.auth.action.dto.UserDto;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserEmp;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.auth.util.DepartmentUtil;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.framework.biz.EmployeeCacheBiz;
import org.esblink.module.organization.domain.Employee;

import com.esblink.dev.util.CommMethod;
import com.esblink.reader.client.domain.Heard;
import com.esblink.reader.client.util.EsbReader;

//@Controller("userAction")
//@Scope("prototype")
public class UserAction extends BaseTreeAction {
	private static final long serialVersionUID = -8990136186060846944L;
	private String moduleCode="user";
	private String modelCode = getClass().getSimpleName();
//	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb = new LogDb(moduleCode, modelCode);
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}
//	@Resource(name="userBiz")
	private IUserBiz userBiz;
	private Collection<DepartmentDto> deptList;
	private Long roleId;
	private Long userId;
	private String empCode;
	private String empName;
	private String deptCode;
	private Collection<UserEmp> userEmpList;
	private Collection rows;
	private Date unusedTm;
	private String targetEmpCodes;
	private String msg;
	private List<UserRoleDeptDto> userRoleDeptList;
	private String userRoleDeptStr;
	private List<UserDto> userList;
	private long total;
	private String query;
	private String selectUserId;
    private String modPassword;
    private Heard heard;
    @Override
    public String execute() throws Exception {
    	 return SUCCESS;
    }

	public String pastAuth() {
		userBiz.savePastAuth(empCode, targetEmpCodes.split(","));
		return SUCCESS;
	}
	public boolean isLoginDomain(){
		return CommMethod.getBoolean(ConfigUtil.getConfigValue(ConfigUtil.config_key.SYS_LOGIN_DOMAIN));
	}
	public void setQuery(String query) {
		this.query = query;
	}

	public String loadDeptForComboBox() {
	    PagingParams pagingParams = new PagingParams(ServletActionContext.getRequest().getParameterMap());
		IPage<DepartmentDto> page = userBiz.loadDeptForComboBox(
				query == null ? "" : query, pagingParams.getPageStart(), pagingParams.getPageSize());
		deptList = page.getData();
		total = page.getTotalSize();
		return SUCCESS;
	}

	public String loadDeptForComboBoxNoAuth() {
	    PagingParams pagingParams = new PagingParams(ServletActionContext.getRequest().getParameterMap());
		IPage<DepartmentDto> page = userBiz.loadDeptForComboBoxNoAuth(
				query == null ? "" : query, pagingParams.getPageStart(), pagingParams.getPageSize());
		deptList = page.getData();
		total = page.getTotalSize();
		return SUCCESS;
	}

	public String loadUser() {
		Map<String, String> params = new HashMap<String, String>();
		if (empCode != null && empCode.length() > 0)
			params.put("empCode", empCode);
		if (empName != null && empName.length() > 0)
			params.put("empName", empName);
		if (deptCode != null && deptCode.length() > 0)
			params.put("deptCode", deptCode);
		List<User> ul = userBiz.loadUser(params);
		if (ul == null)
			return SUCCESS;
		userList = new ArrayList<UserDto>(ul.size());
		for (User u : ul) {
			if (u != null) {
				Employee e = EmployeeCacheBiz.getEmployee(u.getEmpCode());
				if (e == null)
					continue;
				UserDto dto = new UserDto();
				dto.setId(u.getId());
				dto.setEmpCode(e.getEmpCode());
				dto.setUsername(u.getUsername());
				dto.setEmpName(e.getEmpName());
				userList.add(dto);
			}
		}
		return SUCCESS;
	}

	public List<UserRoleDeptDto> getUserRoleDeptList() {
		return userRoleDeptList;
	}

	public String loadUserRoleDept() {
		userRoleDeptList = userBiz.getUserRoleDept(empCode, roleId);
		return SUCCESS;
	}

	public String getMsg() {
		return msg;
	}

	public String saveUserRoleDept() {
		try {
		    List<UserRoleDeptDto> userRoleDepts = null;
		    if(userRoleDeptStr != null && !"".equals(userRoleDeptStr)){
		        userRoleDepts = JsonUtils.jsonToList(userRoleDeptStr, UserRoleDeptDto.class);
		    }
			userBiz.saveUserRoleDept(userId, roleId, userRoleDepts);
			msg = "ok";
			logDbBiz.info(this.getCurrentUser(), logDb, "saveUserRoleDept", "userId="+userId+" roleId="+roleId+" userRoleDeptStr="+userRoleDeptStr, msg, SUCCESS, null);
		} catch (Exception e) {
			msg = "err";
			log.error("", e);
			logDbBiz.info(this.getCurrentUser(), logDb, "saveUserRoleDept", "userId="+userId+" roleId="+roleId+" userRoleDeptStr="+userRoleDeptStr, msg+":"+e.getMessage(),SUCCESS, null);
		}
		return SUCCESS;
	}
	
	/*
	 * 用户修改密码操作
	 */
    public String modUserPassword() throws Exception {
        try {
//        	System.out.println("----modUserPassword--empCode="+empCode);        	
        	userBiz.modUserPassword(modPassword, empCode);
        	msg = "true";
        	logDbBiz.info(this.getCurrentUser(), logDb, "modUserPassword", "empCode="+empCode+" selectUserId="+selectUserId, msg, SUCCESS, null);
		} catch (Exception e) {
			if(log.isDebugEnabled()){
        		log.debug("userAction modUserPassword:["+e.getMessage()+"]");
        	}
			msg = "false";
			logDbBiz.error(this.getCurrentUser(), logDb, "modUserPassword", "empCode="+empCode+" selectUserId="+selectUserId, msg+":"+e.getMessage(), FAILURE, null);
		}
    	return SUCCESS;
    }
    
    public String addLdapUsers(){
		log.info("------addLdapUsers---"+empCode);
		//是否启用用户登录初始化功能
    	
		try 
		{
			String deptCode=ServletActionContext.getRequest().getParameter("deptCode");
			String[] codes=empCode.split(",");
			EsbReader reader = new EsbReader(String.class, String.class);
			String funcCode="getUserInforSysType";
			User user = null;
			String existUsers="";
			for(String code:codes){
				try {
					user = this.userBiz.findUserByNo(code);
				} catch (Exception e) {
					// TODO Auto-generated catch block
//					e.printStackTrace();
				}
				if(user!=null){
					existUsers+=code;
					continue;
				}
    			String params="user_code:"+code+",user_pwd:,sysType:ipad";
    			String intfUrlAddress=ConfigUtil.getConfigValue(ConfigUtil.config_key.INTF_URL_ADDRESS);
    			String ldapInfo=(String)reader.findData(intfUrlAddress, heard, funcCode, params);
    			if(!reader.getReturnResult().getReturnFlag().equals("0")){
					log.error("-----messageInfor-"+reader.getReturnResult().getMessageInfor());
					log.error("-----messageBody-"+reader.getReturnResult().getMessageBody());
				}
    			if(CommMethod.isEmpty(ldapInfo)){
    				ldapInfo = reader.getReturnResult().getMessageBody();
    			}
    			if(!CommMethod.isEmpty(ldapInfo)){
					Map<String, String> map = this.getLdapInfo(ldapInfo);
					String nameCode=map.get("samaccountname");
					String name = null;
					if(nameCode.indexOf("(")>0){
						name=nameCode.substring(0, nameCode.indexOf("("));
					}
					map.put("deptCode", deptCode);
					map.put("name", name);
					boolean flag=this.userBiz.addCheckLdapUserForSystem(code, "" ,map);
					if(!flag){
						existUsers+=code;
					}
				}
			}
			if(!CommMethod.isEmpty(existUsers)){
				this.msg=existUsers;
			}
			else{
				this.msg="ok";
			}
//    			// 采用域统一认证
//            	Auth auth =new Auth();
//				String company="";
//				SearchResult si = auth.getUserDN(username,password,username);//查询user的LDAP信息
//				
//				if(si!=null && si.getAttributes().get("company").toString() != null)
//				{
//					company= si.getAttributes().get("company").toString().split(":")[1].trim();
//				}
//				String company_name=Sysconfigs.getValue("USER_LOGIN_INIT_ON_DEPT_NAME");
//				
//				/**
//				 * 判断用户是否存在数据库中，如果不存在则新增用户，
//				 * 部门为company:'航空公司'，电话统一为:00000000000，权限:普通用户
//				 */
//				this.userBiz.addCheckLdapUserForSystem(username,password,si);
				
		} catch (Exception e) {
		  	log.error("======user_code="+empCode+"----------的ex="+e);
		  	e.printStackTrace();
		}
		
		return SUCCESS;
    	
	}
    private Map<String,String> getLdapInfo(String ldapStr){
		Map<String,String> map = new HashMap<String,String>();
		String[] strs=ldapStr.split(",");
		String[] entrys=null;
		for(String str:strs){
			str = str.trim();
			entrys=str.split(":");
			if(entrys.length>1){
				map.put(entrys[0], entrys[1].trim());
			}
		}
		return map;
	}

	public void setUnusedTm(Date unusedTm) {
		this.unusedTm = unusedTm;
	}

	public String deleteUserRole() {
		userBiz.deleteUserRole(empCode, roleId);
		return SUCCESS;
	}

	public String addUserRole() {
		userBiz.addUserRole(empCode, roleId, unusedTm);
		logDbBiz.info(this.getCurrentUser(), logDb, "addUserRole", "empCode="+empCode+" roleId="+roleId, msg, SUCCESS, null);
		return SUCCESS;
	}

	public String saveUserRoleAsDefault(){
        userBiz.saveUserRoleAsDefault(userId, roleId);
        logDbBiz.info(this.getCurrentUser(), logDb, "saveUserRoleAsDefault", "userId="+userId+" roleId="+roleId, msg, SUCCESS, null);
        return SUCCESS;	    
	}
	
	public String addUser() {
		if (empCode != null)
			this.userBiz.addUsers(empCode.split(","));
		msg=null;
		return SUCCESS;
	}

	public String deleteUser() {
		if (empCode != null){
			this.userBiz.deleteUsers(empCode.split(","));
			logDbBiz.info(this.getCurrentUser(), logDb, "deleteUser", "empCode="+empCode, msg, SUCCESS, null);
		}
		return SUCCESS;
	}

	public String loadUserEmp() {
        IPage<UserEmp> page = userBiz.loadUserEmpPageBy(new QueryObj(ServletActionContext.getRequest().getParameterMap()));
//        userEmpList = page.getData();
        this.rows=page.getData();
        total = page.getTotalSize();
		return SUCCESS;
	}

	public String loadDeptListNoAuth() {
		deptList = userBiz.getDeptListNoAuth(super.getId());
		return SUCCESS;
	}

	public String loadDeptListNoAuthEff() {
		deptList = userBiz.getDeptListNoAuthEff(super.getId());
		return SUCCESS;
	}

	public String loadDeptListEff() {
		deptList = userBiz.getDeptListEff(super.getId());
		return SUCCESS;
	}

	public String loadDeptList() {
		deptList = userBiz.getDeptList(super.getId());
		return SUCCESS;
	}

	public Collection<DepartmentDto> getDeptList() {
		return DepartmentUtil.convertDeptName(deptList);
	}

	public long getTotal() {
    	return total;
    }

    public List<UserDto> getUserList() {
    	return userList;
    }

    public Collection<UserEmp> getUserEmpList() {
    	return userEmpList;
    }

    public void setRoleId(Long roleId) {
    	this.roleId = roleId;
    }

    public void setEmpCode(String empCode) {
    	this.empCode = empCode;
    }

    public void setEmpName(String empName) {
    	this.empName = empName;
    }

    public void setDeptCode(String deptCode) {
    	this.deptCode = deptCode;
    }

    public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

    public String getTargetEmpCodes() {
        return targetEmpCodes;
    }

    public void setTargetEmpCodes(String targetEmpCodes) {
        this.targetEmpCodes = targetEmpCodes;
    }

    public String getUserRoleDeptStr() {
        return userRoleDeptStr;
    }

    public void setUserRoleDeptStr(String userRoleDeptStr) {
        this.userRoleDeptStr = userRoleDeptStr;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

	public void setSelectUserId(String selectUserId) {
		this.selectUserId = selectUserId;
	}

	public void setModPassword(String modPassword) {
		this.modPassword = modPassword;
	}

	public void setHeard(Heard heard) {
		this.heard = heard;
	}

	public Collection<UserEmp> getRows() {
		return rows;
	}
    
}
