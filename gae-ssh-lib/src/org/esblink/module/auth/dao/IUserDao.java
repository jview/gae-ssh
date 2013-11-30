package org.esblink.module.auth.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.auth.biz.dto.UserAuthDto;
import org.esblink.module.auth.domain.User;

public interface IUserDao extends IBaseDAO<User> {
	void saveUserAuth(final long userId,
			final Map<Long, UserAuthDto> userAuthMap);

	User findUserByName(String username);
	
	User findUserByNo(String userNo) ;

	User loadByEmpCode(String empCode);
	
	User findById(Long id);
	

	List<User> loadByEmpCodes(String[] empCodes);

	List<String> authDeptCodes(final long userId, final long roleId);

	List<Long> authDeptIds(long userId, long roleId);

	void addUserRole(final long userId, final long roleId, final Date unusedTm);

	void deleteUserRole(final long userId, final long roleId);
	
	void saveUserRoleAsDefault(long userId, long roleId);
	
	List<User> loadBy(Map<String, Object> params);
	/**
	 * 加密处理
	 */
	public String encrypt(String username, String password);
	/**
	 * 修改密码
	 * @param selectUserId
	 * @param modPassword
	 * @param userName
	 * @throws Exception
	 */
	public void modUserPassword(String modPassword,
			String userName) throws Exception ;
	
	/**
	 * 根据idList查user
	 * @param idList
	 * @return
	 */
	public List<User> findUserByIds(final List<Long> idList);
	
	/**
	 * 根据idList查user(优化:只返回userId,user_no,username,dept_id)
	 * @param idList
	 * @return
	 */
	public List<User> findUserByIdsOpt(final List<Long> idList);
	/**
	 * 根据user_noList查user
	 * @param noList
	 * @return
	 */
	public List<User> findUserByNos(final List<String> noList);
	/**
	 * 根据user_noList查user(优化:只返回userId,user_no,username,dept_id)
	 * @param noList
	 * @return
	 */
	public List<User> findUserByNosOpt(final List<String> noList);
}
