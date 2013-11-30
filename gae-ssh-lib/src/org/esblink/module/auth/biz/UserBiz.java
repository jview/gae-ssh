package org.esblink.module.auth.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esblink.common.base.IPage;
import org.esblink.common.base.Page;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.QueryParam;
import org.esblink.common.base.domain.IUser;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.DateUtils;
import org.esblink.common.util.StringUtils;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.biz.dto.UserAuthDto;
import org.esblink.module.auth.dao.IRoleDao;
import org.esblink.module.auth.dao.IUserDao;
import org.esblink.module.auth.dao.IUserEmpDao;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserEmp;
import org.esblink.module.auth.util.ComparatUtil;
import org.esblink.module.auth.util.DepartmentUtil;
import org.esblink.module.basedata.util.Sysconfigs;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.framework.biz.DepartmentCacheBiz;
import org.esblink.module.framework.biz.UserCacheBiz;
import org.esblink.module.organization.dao.IEmployeeDao;
import org.esblink.module.organization.domain.Department;
import org.esblink.module.organization.domain.Employee;

import com.esblink.dev.util.CommMethod;

//@Service("userBiz")
public class UserBiz extends BaseBIZ implements IUserBiz {
//	@Resource(name="userDao")
	private IUserDao userDao;
//	@Resource(name="userEmpDao")
	private IUserEmpDao userEmpDao;
//	@Resource(name="employeeDao")
	private IEmployeeDao employeeDao;
//	@Resource(name="roleDao")
	private IRoleDao roleDao;

	
	@Override
	public List<UserEmp> findByQuery(QueryObj queryObj, String query) {
		// TODO Auto-generated method stub
		if(query!=null){
			query = query.toLowerCase();
		}
		IPage<UserEmp> page = this.userEmpDao.findPageBy(queryObj);			
		
		UserEmp temp;
		List<UserEmp> queryList = new ArrayList<UserEmp>();
		List<UserEmp> userList = new ArrayList<UserEmp>();
		userList.addAll(page.getData());
//		Collections.sort(userList, new UserCnameComparator(true));
		for (UserEmp target:userList) {			
			temp = new UserEmp();
			temp.setId(target.getId());
			temp.setUserId(target.getUserId());
			temp.setDutyName(target.getDutyName());
			temp.setDeptCode(target.getDeptCode());
			temp.setEmpCode(target.getEmpCode());
			temp.setEmpName(target.getEmpName());
			if (query != null && !"".equals(query)) {
				if (temp.getEmpCode().toLowerCase().indexOf(query) != -1 || temp.getEmpName().indexOf(query) != -1) {
					queryList.add(temp);
				}
			} else {
				queryList.add(temp);
			}
		}
		return queryList;
	}
	
	public IPage<DepartmentDto> loadDeptForComboBox(String text, int start,
			int limit) {
		IUser user = super.getCurrentUser();
		if (UserStatus.ROOT.equals(user.getStatus()))
			return this.loadDeptForComboBoxNoAuth(text, start, limit);
		return this.loadDeptForComboBox(DepartmentCacheBiz.getByCodes(user
				.getAuthDepartmentCodes()), text, start, limit);
	}

	public IPage<DepartmentDto> loadDeptForComboBoxNoAuth(String text,
			int start, int limit) {
		Collection<Department> deptList = DepartmentCacheBiz.getAllDepartment();
		return loadDeptForComboBox(deptList, text, start, limit);
	}

	private IPage<DepartmentDto> loadDeptForComboBox(
			Collection<Department> deptList, String text, int start, int limit) {
		Pattern p1 = Pattern.compile(".*" + text + ".*");
		Pattern p2 = Pattern.compile(".*" + text + ".*");

		List<DepartmentDto> dtoList = new ArrayList<DepartmentDto>();
		for (Department dept : deptList) {
			Matcher mc = p1.matcher(dept.getDeptCode());
			Matcher mn = p2.matcher(dept.getDeptName());
			if (mn.find() || mc.find()) {
				DepartmentDto dto = DepartmentCacheBiz.convertToDto(dept);
				if (dto != null)
					dtoList.add(dto);
			}
		}

		Collections.sort(dtoList, ComparatUtil.DepartmentDtoComparator);
		int s, f;
		s = f = dtoList.size();
		if (start < s)
			s = start;
		if (start + limit < f)
			f = start + limit;
		Collection<DepartmentDto> res = dtoList.subList(s, f);
		return new Page<DepartmentDto>(res, dtoList.size(), limit, start
				/ limit);
	}
	
	public User getUserById(Long id) {
		return (User)this.userDao.find(User.class, id);
	}
	
	public void modUserPassword(String modPassword,
			String userName) throws Exception {
		userDao.modUserPassword(modPassword, userName);
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}

	public void setUserEmpDao(IUserEmpDao userEmpDao) {
		this.userEmpDao = userEmpDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public User findUserByName(String username) {
		return this.userDao.findUserByName(username);
	}
	
	/**
	 * 加密处理
	 */
	public String encrypt(String username, String password){
		return this.userDao.encrypt(username, password);
	}
	
	public boolean validUser(String workerNo, String password){
		User user = this.findUserByNo(workerNo);
		if(user==null){
			return false;
		}
		String pwd=this.encrypt(workerNo, password);
		if(user.getPassword().equalsIgnoreCase(pwd)){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	public User findUserByNo(String userNo){
		return this.userDao.findUserByNo(userNo);
	}

	public List<DepartmentDto> getDeptListEff(long deptId) {
		return getDeptListCase(deptId, true);
	}

	public List<DepartmentDto> getDeptList(long deptId) {
		return getDeptListCase(deptId, false);
	}

	public List<DepartmentDto> getDeptListNoAuthEff(long deptId) {
		List<Department> resList = getRootUserDeptList(deptId);
		resList = filterDepartmentStatus(resList);
		List<DepartmentDto> res = DepartmentCacheBiz.convertToDto(resList);
		Collections.sort(res, ComparatUtil.DepartmentDtoComparator);
		return res;
	}

	public List<DepartmentDto> getDeptListNoAuth(long deptId) {
		List<Department> resList = getRootUserDeptList(deptId);
		List<DepartmentDto> res = DepartmentCacheBiz.convertToDto(resList);
		Collections.sort(res, ComparatUtil.DepartmentDtoComparator);
		return res;
	}

	private List<Department> filterDepartmentStatus(List<Department> deptList) {
		if (deptList == null)
			return new ArrayList<Department>(0);
		List<Department> res = new ArrayList<Department>(deptList.size());
		for (Department d : deptList) {
			if (!d.getDeleteFlg())
				res.add(d);
		}
		return res;
	}

	private List<DepartmentDto> getDeptListCase(long deptId,
			boolean filterStatus) {
		IUser user = super.getCurrentUser();
		List<Department> resList = null;
		if (UserStatus.ROOT.equals(user.getStatus())) {
			resList = getRootUserDeptList(deptId);
		} else {
			resList = getNormalUserDeptList(deptId);
		}
		if (filterStatus) {
			resList = filterDepartmentStatus(resList);
		}
		List<DepartmentDto> res = DepartmentCacheBiz.convertToDto(resList);
		Collections.sort(res, ComparatUtil.DepartmentDtoComparator);
		return res;
	}

	private List<Department> getRootUserDeptList(long deptId) {
		if (deptId == 0L) {
			List<Department> resList = new ArrayList<Department>(1);
			resList.add(DepartmentCacheBiz.getRoot());
			return resList;
		} else {
			return DepartmentCacheBiz.getChildrenById(deptId);
		}
	}

	private List<Department> getNormalUserDeptList(long deptId) {
		IUser user = super.getCurrentUser();
		List<String> authCodes = user.getAuthDepartmentCodes();
		if (deptId == 0L) {
			return DepartmentUtil.getRoots(authCodes);
		} else {
			return DepartmentUtil.getChildren(deptId, authCodes);
		}
	}

	/**
	 * 不是当前用户，
	 * 当前用户的当前角色，
	 * 拥有改部门的权限，
	 * 如果是root用户，则不用进行权限的校验
	 * 
	 * @param queryObj
	 * @return
	 */
	public IPage<UserEmp> loadUserEmpPageBy(QueryObj queryObj) {
		return this.userEmpDao.findPageBy(queryObj);
	}

	private List<User> filterCurrentUser2(List<User> ul) {
		if (ul == null)
			return new ArrayList<User>(0);

		String currentEmpCode = super.getCurrentUser().getEmpCode();
		List<User> res = new ArrayList<User>(ul.size());
		for (User user : ul)
			if (!currentEmpCode.equals(user.getEmpCode())
					&& !UserStatus.ROOT.equals(user.getStatus()))
				res.add(user);
		return res;
	}

	private List<User> filterUserDeptAuth2(List<User> ul) {
		if (ul == null)
			return new ArrayList<User>(0);
		if (UserStatus.ROOT.equals(super.getCurrentUser().getStatus()))
			return ul;
		List<String> authCodes = super.getCurrentUser()
				.getAuthDepartmentCodes();
		Map<String, Department> codeMap = DepartmentUtil.getCodeMap(authCodes);
		List<User> res = new ArrayList<User>(ul.size());
		for (User user : ul)
			if (codeMap.containsKey(user.getDeptCode()))
				res.add(user);
		return res;
	}

	public void addUsers(String[] empCodes) {
		for (String code : empCodes)
			addUser(code);
	}

	public List<User> loadUser(Map<String, String> params) {
		String empCode = params.get("empCode");
		List<User> res = new ArrayList<User>();
		if (empCode != null && empCode.length() > 0) {
			User user = userDao.loadByEmpCode(empCode);
			if (user != null)
				res.add(user);
		} else {
			String empName = params.get("empName");
			String deptCode = params.get("deptCode");
			Map<String, Object> ps = new HashMap<String, Object>();
			if (empName != null && empName.length() > 0) {
				List<Employee> emp = this.employeeDao.loadByEmpName(empName);
				if (emp != null && emp.size() > 0) {
					List<String> empCodes = new ArrayList<String>(emp.size());
					for (Employee e : emp)
						if (e != null && e.getEmpCode() != null)
							empCodes.add(e.getEmpCode());
					ps.put("empCode", empCodes);
				}
			}
			if (deptCode != null && deptCode.length() > 0)
				ps.put("deptCode", deptCode);

			res = userDao.loadBy(ps);
		}
		res = this.filterCurrentUser2(res);
		res = this.filterUserDeptAuth2(res);
		return res;
	}

	private void addUser(String empCode) {
		User u = this.userDao.loadByEmpCode(empCode);
		if (u != null) {
//			if("admin".equals(u.getUsername())){
//				u.setStatus(UserStatus.ROOT);
//				u.setUpdateTm(new Date());
//				if(super.getCurrentUser()!=null){
//					u.setUpdateEmpCode(super.getCurrentUser().getEmpCode());
//				}
//				this.userDao.update(u);
//				return;
//			}
			if (UserStatus.ROOT.equals(u.getStatus())
					|| UserStatus.ENABLE.equals(u.getStatus()))
				return;
			u.setStatus(UserStatus.ENABLE);
			u.setUpdateTm(new Date());
			if(super.getCurrentUser()!=null){
				u.setUpdateEmpCode(super.getCurrentUser().getEmpCode());
			}
			this.userDao.update(u);
		} else {
			Employee e = this.employeeDao.loadByCode(empCode);
			if(e==null){
				log.error("-------addUser unexist employee for empCode:"+empCode);
				return;
			}
			User user = new User();
			if(super.getCurrentUser()!=null){
				user.setCreateEmpCode(super.getCurrentUser().getEmpCode());
			}
			user.setCreateTm(new Date());
			user.setDeptCode(e.getDeptCode());
			user.setEmpCode(empCode);
			if(empCode.equals("admin")){
				user.setStatus(UserStatus.ROOT);
			}
			else{
				user.setStatus(UserStatus.ENABLE);
			}
			user.setUsername(empCode);
			user.setEmpName(e.getEmpName());
			this.userDao.save(user);
		}
	}

	public void deleteUsers(String[] empCode) {
		for (String code : empCode)
			deleteUser(code);
	}

	private void deleteUser(String empCode) {
		User u = this.userDao.loadByEmpCode(empCode);
		if (u != null && !UserStatus.ROOT.equals(u.getStatus())) {
			u.setStatus(UserStatus.DISABLE);
			u.setUpdateTm(new Date());
			u.setUpdateEmpCode(super.getCurrentUser().getEmpCode());
			this.userDao.update(u);

			// 删除用户时，清除用户的缓存。
			List<Object[]> roleIdList = this.roleDao.getUserRoleIds(u.getId());
			for (Object[] rs : roleIdList) {
				Long roleId = (Long) rs[0];
				if (roleId != null)
					UserCacheBiz.removeData(u.getId(), roleId);
			}
		}
	}

	public void addUserRole(String empCode, long roleId, Date unusedTm) {
		User u = this.userDao.loadByEmpCode(empCode);
		if (u == null)
			return;
		this.userDao.addUserRole(u.getId(), roleId, unusedTm);
	}

	public void deleteUserRole(String empCode, long roleId) {
		User u = this.userDao.loadByEmpCode(empCode);
		if (u == null)
			return;
		this.userDao.deleteUserRole(u.getId(), roleId);
	}
	
	public void saveUserRoleAsDefault(long userId, long roleId){
	    this.userDao.saveUserRoleAsDefault(userId, roleId);
	}
	
	public void saveUserRoleDept(long userId, long roleId, List<UserRoleDeptDto> userRoleDeptList) {
		// 如果当前用户不是ROOT要看一下是不是真的可以给部门包涵下级的权限。
		if (!UserStatus.ROOT.equals(super.getCurrentUser().getStatus())) {
			long currentUserId = super.getCurrentUser().getId();
			long currentRoleId = getCurrentUser().getRoleId();
			List<UserRoleDeptDto> urdl = this.roleDao.getUserRoleDept(currentUserId, currentRoleId);
			Map<String, Byte> m = new HashMap<String, Byte>();
			for(UserRoleDeptDto dto: urdl){
				m.put(dto.getDeptCode(), dto.getInherited());
			}
            for (UserRoleDeptDto tmp : userRoleDeptList) {
                if (tmp.getInherited() != null && tmp.getInherited().intValue() == 1) {
                    Byte inhm = m.get(tmp.getDeptCode());
                    if (inhm == null || inhm.intValue() == 0) {
                        tmp.setInherited((byte)0);
                    }
                }
            }
		}

		this.roleDao.saveUserRoleDept(userId, roleId, userRoleDeptList);
	}

	public List<UserRoleDeptDto> getUserRoleDept(String empCode, long roleId) {
		User user = this.userDao.loadByEmpCode(empCode);
		if (user == null)
			return new ArrayList<UserRoleDeptDto>(0);
		return this.roleDao.getUserRoleDept(user.getId(), roleId);
	}

	public void savePastAuth(String fromEmpCode, String[] toEmpCodes) {
		if (fromEmpCode == null || fromEmpCode.length() == 0
				|| toEmpCodes == null || toEmpCodes.length == 0)
			return;

		User fromUser = this.userDao.loadByEmpCode(fromEmpCode);
		if (fromUser == null)
			return;
		List<User> toUsers = this.userDao.loadByEmpCodes(toEmpCodes);

		Map<Long, UserAuthDto> fromUserAuth = getUserAuth(fromUser);
		Map<Long, Map<Long, UserAuthDto>> toUserAuthMap = new HashMap<Long, Map<Long, UserAuthDto>>();
		for (User u : toUsers) {
			if (u != null)
				toUserAuthMap.put(u.getId(), getUserAuth(u));
		}

		for (Entry<Long, UserAuthDto> e : fromUserAuth.entrySet()) {
			Long roleId = e.getKey();
			UserAuthDto ua = e.getValue();
			for (Entry<Long, Map<Long, UserAuthDto>> tuae : toUserAuthMap
					.entrySet()) {
				Map<Long, UserAuthDto> tuaMap = tuae.getValue();
				UserAuthDto tua = tuaMap.get(roleId);
				if (tua == null) {
					tuaMap.put(roleId, ua);
				} else {
					for (Entry<String, UserRoleDeptDto> urde : ua.getDeptMap()
							.entrySet()) {
						tua.putDeptAuth(urde.getValue());
					}
				}
			}
		}

		for (Entry<Long, Map<Long, UserAuthDto>> e : toUserAuthMap.entrySet()) {
			this.userDao.saveUserAuth(e.getKey(), e.getValue());
		}
	}
	
	public boolean addCheckLdapUserForSystem(String user_code,String user_pwd, Map<String, String> ldapMap)
    {
    	boolean flag = true;
    	
    	String mail= ldapMap.get("mail");
    	String company= ldapMap.get("company");
    	String user_name = ldapMap.get("name");
//    	System.out.println(user_name);
    	String deptCode=ldapMap.get("deptCode");
    	
    	String dept_code=null;
		if(!CommMethod.isEmpty(deptCode)){
			dept_code=deptCode;
		}
		else{
			dept_code=Sysconfigs.getValue("USER_LOGIN_INIT_DEPT_CODE");
		}
    	
		int role_id=Sysconfigs.getInt("USER_LOGIN_INIT_ROLE_ID");
		
    	this.saveUserAndRole(role_id, dept_code, user_code, user_pwd, user_name, mail, "0000000000");
    	
		return flag;
    }
	
	public void saveUserAndRole(int roleId, String deptCode, String username, String password, String name, String email, String mobile){
		
		
		String invalid_role_date=Sysconfigs.getValue("USER_LOGIN_INIT_ROLE_INVALID_DATE");
		Employee emp = new Employee();
		emp.setEmpCode(username);
		emp.setEmpName(name);
		emp.setEmpEmail(email);
		emp.setEmpMobile(mobile);
		emp.setEmpStatus("1");
		
		emp.setDeptCode(deptCode);
		emp.setValidFlg(1l);
		
		if (!StringUtils.isNotEmpty(emp.getCreateEmp())) {
			if(super.getCurrentUser()!=null){
				emp.setCreateEmp(super.getCurrentUser().getUsername());
			}
			emp.setCreateTm(new Date());
		}
		if(super.getCurrentUser()!=null){
			emp.setUpdateEmp(super.getCurrentUser().getUsername());
		}
		emp.setUpdateTm(new Date());
		this.employeeDao.save(emp);
		
		String[] empCodes={username};
		this.addUsers(empCodes);
		Date date = DateUtils.parseDate(invalid_role_date);
		this.addUserRole(username, roleId, date);
	}

	private Map<Long, UserAuthDto> getUserAuth(User user) {
		Map<Long, UserAuthDto> userAuth = new HashMap<Long, UserAuthDto>();
		List<Object[]> rs = this.roleDao.getUserRoleIds(user.getId());
		for (Object[] os : rs) {
			UserAuthDto ua = new UserAuthDto();

			Long roleId = (Long) os[0];
			Date unusedTm = (Date) os[1];

			ua.setRoleId(roleId.longValue());
			ua.setUnusedTm(unusedTm);

			List<UserRoleDeptDto> urdl = this.roleDao.getUserRoleDept(user
					.getId(), roleId);
			for (UserRoleDeptDto dto : urdl) {
				ua.putDeptAuth(dto);
			}
			userAuth.put(roleId, ua);
		}
		return userAuth;
	}
	
	@Override
	public List<User> findUserByIds(List<Long> idList, boolean opt) {
		if(null==idList ||idList.size()==0){
			return new ArrayList();
		}
		// TODO Auto-generated method stub
		if(opt){
			return this.userDao.findUserByIdsOpt(idList);
		}
		else{
			return this.userDao.findUserByIds(idList);
		}
	}
	
	public Employee findUserEmpById(Long userId){
		Employee emp = null;
		User user=this.getUserById(userId);
		if(user!=null){
			Employee search = new Employee();
			search.setValidFlg(1l);
			search.setInnerFlg(1l);
			search.setEmpCode(user.getEmpCode());
			Collection<Employee> empList = this.employeeDao.findBy(search);

			for(Employee cur:empList){
				emp = cur;
				break;
			}
		}
		return emp;
	}
	
	public List<Employee> findUserEmpByIds(List<Long> userIdList){
		List<User> userList = this.findUserByIds(userIdList, true);
		List<String> empCodeList = new ArrayList<String>();
		for(User user:userList){
			empCodeList.add(user.getEmpCode());
		}
		return this.employeeDao.findByEmpCodeList(empCodeList);
	}
	
	@Override
	/**
	 * @param opt 是否简化查询true只查需要属性，false查全部
	 */
	public List<User> findUserByNos(List<String> noList, boolean opt) {
		if(null==noList||noList.size()==0){
			return new ArrayList();
		}
		// TODO Auto-generated method stub
		if(opt){
			return this.userDao.findUserByNosOpt(noList);
		}
		else{
			return this.userDao.findUserByNos(noList);
		}
	}
}
