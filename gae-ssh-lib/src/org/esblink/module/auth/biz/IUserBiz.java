package org.esblink.module.auth.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.QueryParam;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.domain.UserEmp;
import org.esblink.module.framework.action.dto.DepartmentDto;
import org.esblink.module.organization.domain.Employee;

/**
 * 服务器端用户管理业务接口
 * 
 * 
 */
public interface IUserBiz extends IBaseBIZ {
	public List<UserEmp> findByQuery(QueryObj queryObj, String query);
	
	void savePastAuth(String fromEmpCode, String[] toEmpCodes);
	
	IPage<DepartmentDto> loadDeptForComboBox(String text, int start, int limit);

	IPage<DepartmentDto> loadDeptForComboBoxNoAuth(String text, int start,
			int limit);

	User findUserByName(String username);
	
	/**
	 * 加密处理
	 * @param username
	 * @param password
	 * @return
	 */
	public String encrypt(String username, String password);
	
	public boolean validUser(String workerNo, String password);
	
	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findUserByNo(String userNo);

	List<User> loadUser(Map<String, String> params);

	List<DepartmentDto> getDeptListNoAuthEff(long deptId);

	List<DepartmentDto> getDeptListNoAuth(long deptId);

	List<DepartmentDto> getDeptList(long deptId);

	List<DepartmentDto> getDeptListEff(long deptId);

	IPage<UserEmp> loadUserEmpPageBy(QueryObj queryObj);

	public void addUsers(String[] empCodes);

	public void deleteUsers(String[] empCode);

	void addUserRole(String empCode, long roleId, Date unusedTm);

	void deleteUserRole(String empCode, long roleId);

	void saveUserRoleAsDefault(long userId, long roleId);
	
	void saveUserRoleDept(long userId, long roleId, List<UserRoleDeptDto> userRoleDeptList);
	
	boolean addCheckLdapUserForSystem(String user_code,String user_pwd, Map<String, String> ldapMap);
	void saveUserAndRole(int roleId, String deptCode, String username, String password, String name, String email, String mobile);

	List<UserRoleDeptDto> getUserRoleDept(String empCode, long roleId);
	
	public User getUserById(Long id) ;
	
	public void modUserPassword(String modPassword,
			String userName) throws Exception ;
	
	/**
	 * 根据idList查user
	 * @author chenjh
	 * @param idList
	 * @param opt, false正常的hibernate返回，true(优化:只返回userId,user_no,username,dept_id)
	 * @return
	 */
	List<User> findUserByIds(List<Long> idList, boolean opt);
	
	public Employee findUserEmpById(Long userId);
	public List<Employee> findUserEmpByIds(List<Long> userIdList);
	/**
	 * 根据idList查user(优化:只返回userId,user_no,username,dept_id)
	 * @author chenjh
	 * @param noList
	 * @param opt, false正常的hibernate返回，true(优化:只返回userId,user_no,username,dept_id)
	 * @return
	 */
	List<User> findUserByNos(List<String> noList, boolean opt);
}
