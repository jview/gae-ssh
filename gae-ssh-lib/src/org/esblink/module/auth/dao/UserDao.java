package org.esblink.module.auth.dao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.esblink.common.base.QueryObj;
import org.esblink.common.base.domain.UserStatus;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.auth.biz.dto.UserAuthDto;
import org.esblink.module.auth.domain.User;
import org.esblink.module.auth.util.DepartmentUtil;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.esblink.dev.util.DbInfo;

//@Service("userDao")
public class UserDao extends BaseDAO<User> implements IUserDao {

    public void saveUserAuth(final long userId, final Map<Long, UserAuthDto> userAuthMap) {
//        getHibernateTemplate().execute(new HibernateCallback<Object>() {
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                String deleteRoleDeptSQL = "delete from ts_user_role_dept t where t.user_id = ?";
//                SQLQuery delete = session.createSQLQuery(deleteRoleDeptSQL);
//                delete.setLong(0, userId);
//                delete.executeUpdate();
//
//                String deleteRoleSQL = "delete from ts_user_role t where t.user_id = ?";
//                delete = session.createSQLQuery(deleteRoleSQL);
//                delete.setLong(0, userId);
//                delete.executeUpdate();
//
//                if (userAuthMap.isEmpty()) {
//                    return null;
//                }
//
//                String insertRoleSQL = "insert into ts_user_role(USER_ID,ROLE_ID,UNUSED_TM) values(?,?,?)";
//                SQLQuery insertRole = session.createSQLQuery(insertRoleSQL);
//                for (Entry<Long, UserAuthDto> e : userAuthMap.entrySet()) {
//                    insertRole.setLong(0, userId);
//                    insertRole.setLong(1, e.getKey());
//                    insertRole.setDate(2, e.getValue().getUnusedTm());
//                    insertRole.executeUpdate();
//                }
//
//                String insertRoleDeptSQL = "insert into ts_user_role_dept(USER_ID,ROLE_ID,DEPT_CODE,INHERITED_FLG) values(?,?,?,?)";
//                SQLQuery insertRoleDept = session.createSQLQuery(insertRoleDeptSQL);
//                for (Entry<Long, UserAuthDto> e : userAuthMap.entrySet()) {
//                    for (Entry<String, UserRoleDeptDto> de : e.getValue().getDeptMap().entrySet()) {
//                        insertRoleDept.setLong(0, userId);
//                        insertRoleDept.setLong(1, e.getKey());
//                        insertRoleDept.setString(2, de.getKey());
//                        insertRoleDept.setByte(3, de.getValue().getInherited());
//                        insertRoleDept.executeUpdate();
//                    }
//                }
//                return null;
//            }
//        });

    }

	public List<User> loadBy(Map<String, Object> params) {
		if (params.size() == 0)
			return new ArrayList<User>(0);

		QueryObj queryObj = new QueryObj(-1, -1, null, false);
		for (Entry<String, Object> e : params.entrySet()) {
			String key = e.getKey();
			Object val = e.getValue();
			if (key != null && val != null) {
				queryObj.setQueryObject(key, val);
			}
		}
		Collection<User> list= super.findBy(queryObj);
		List<User> uList=new ArrayList<User>();
		uList.addAll(list);
		return uList;
	}

	public User findUserByName(String username) {
		
		User search = new User();
		search.setUsername(username);
//		search.setStatusFlag("enable");
		Collection<User> list=super.findBy(search);
		User user = null;
		for(User u:list){
//			System.out.println("------"+u.getStatusFlag()+" "+u.getStatus());
			if(u.getStatus().equals(UserStatus.ENABLE)||u.getStatus().equals(UserStatus.ROOT)){
				user=u;
				break;
			}
		}
//		System.out.println("user="+user+" list="+list.size()+"---"+user.getPassword());
		return user;
	}
	public User findById(Long id)
	{
		User user = (User)this.find(User.class, id);
		return user;
	}
	public User findUserByNo(final String userNo) {
		long start = System.nanoTime();
		User search = new User();
		search.setUsername(userNo);
		Collection<User> list=super.findBy(search);
		User user = null;
		for(User u:list){
			user=u;
			break;
		}
		return user;
	}

	@SuppressWarnings("unchecked")
    public List<String> authDeptCodes(final long userId, final long roleId) {
//        return (List<String>) getHibernateTemplate().execute(new HibernateCallback<Object>() {
//            @SuppressWarnings("rawtypes")
//            public Object doInHibernate(Session session) throws HibernateException, SQLException {
//                SQLQuery sqlQuery = session
//                        .createSQLQuery("select urd.dept_code from ts_user_role_dept urd where urd.user_id = ? and urd.role_id = ? and urd.inherited_flg = 0");
//                sqlQuery.addScalar("dept_code", Hibernate.STRING);
//                sqlQuery.setLong(0, userId);
//                sqlQuery.setLong(1, roleId);
//                List res1 = sqlQuery.list();
//
//                sqlQuery = session
//                        .createSQLQuery("select urd.dept_code from ts_user_role_dept urd where urd.user_id = ? and urd.role_id = ? and urd.inherited_flg = 1");
//                sqlQuery.addScalar("dept_code", Hibernate.STRING);
//                sqlQuery.setLong(0, userId);
//                sqlQuery.setLong(1, roleId);
//                List res2 = sqlQuery.list();
//
//                Set<String> set = new HashSet<String>();
//                set.addAll(res1);
//                for (String code : (List<String>) res2) {
//                    set.add(code);
//                    set.addAll(DepartmentCacheBiz.getAllChildrenCode(code));
//                }
//                List res = new ArrayList<String>(set.size());
//                res.addAll(set);
//                return res;
//            }
//        });
//		return null;
		List<String> deptCodeList=new ArrayList();
		deptCodeList.add("001");
		deptCodeList.add("002");
		return deptCodeList;
		
    }

	public List<User> loadByEmpCodes(String[] codes) {
//		DetachedCriteria detachedCriteria = DetachedCriteria
//				.forClass(User.class);
//		detachedCriteria.add(Restrictions.in("empCode", codes));
//		detachedCriteria.add(Restrictions.in("statusFlag",
//				new String[] { "enable" }));
//		return super.findBy(detachedCriteria);
		return null;
	}

	public List<Long> authDeptIds(long userId, long roleId) {
		List<String> codes = this.authDeptCodes(userId, roleId);
		return DepartmentUtil.convertCodeToId(codes);
	}

	public User loadByEmpCode(String empCode) {
//		return super.loadBy("empCode", empCode);
//		return null;
		User search = new User();
		search.setEmpCode(empCode);
		Collection<User> list=super.findBy(search);
		User user = null;
		for(User u:list){
			user=u;
			break;
		}
		return user;
	}
	
	/**
	 * 加密处理
	 */
	public String encrypt(String username, String password){
//		String encrypt_pwd= new Md5Encoder().encode(username + password);
		String encrypt_pwd= new Md5PasswordEncoder().encodePassword(password, username);
		return encrypt_pwd;
	}
	
	public void modUserPassword(String modPassword,
			String userName) throws Exception {
		if(modPassword == null || userName == null
				   || "".equals(modPassword) || "".equals(userName)){
			return;
		}
		User user = this.loadByEmpCode(userName);
		if(user != null){
//			String password = new Md5Encoder().encode(userName + modPassword);
//			if(ConfigUtil.isSpringSecurity){			
//				password = new Md5PasswordEncoder().encodePassword(modPassword, userName);
//			}
			String password = this.encrypt(userName, modPassword);
//			log.info("-----modUserPassword---username="+userName+" password="+password+" "+ConfigUtil.isSpringSecurity+" encrypt_pwd="+password);
//			saveUserSync(user.getId(), "PASSWORD", password);
			user.setPassword(password);
			user.setUpdateTm(new Date());
			this.update(user);

			long start = System.nanoTime();
			
			log.finer("[profile]UserDao : modUserPassword user_id is  " + user.getId() + "   " + new DecimalFormat("###,##0").format(System.nanoTime() - start) + " ns");
			
		}
	}
	
	public void saveUserSync(Long userId, String conditions,
			String conditionsValue) {
		StringBuffer updateSql = new StringBuffer();
		String datekey = "sysdate";
		if(DbInfo.isPostgresql()){
			datekey = "now()";
		}
		updateSql.append("update TS_USER set MODIFY_DATE="+datekey+" , PWD_MODIFIED_TM="+datekey+" , ");
		updateSql.append(conditions);
		updateSql.append("='");
		updateSql.append(conditionsValue);
		updateSql.append("' where user_id='");
		updateSql.append(userId);
		updateSql.append("'");

		//final String updateSql = "update TS_USER set "+ conditions + "='" + conditionsValue +"' where user_id='" + userId +"'";
//		final String updateSqls = updateSql.toString();
//		getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				Query query = session.createSQLQuery(updateSqls);
//				query.executeUpdate();
//				return null;
//			}
//		});
	}

	public void addUserRole(final long userId, final long roleId,
			final Date unusedTm) {
//		getHibernateTemplate().execute(new HibernateCallback<Object>() {
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//			    String countSQL = "select count(1) from ts_user_role where user_id = ? and role_id = ?";
//			    SQLQuery count = session.createSQLQuery(countSQL);
//			    count.setLong(0, userId);
//			    count.setLong(1, roleId);
//			    int i = ((Number)count.uniqueResult()).intValue();
//			    if(i > 0){
//			        String updateSQL = "update ts_user_role set unused_tm = ? where user_id = ? and role_id = ?";
//			        SQLQuery update = session.createSQLQuery(updateSQL);
//			        update.setDate(0, unusedTm);
//			        update.setLong(1, userId);
//			        update.setLong(2, roleId);
//			        update.executeUpdate();
//			    }else{
//                    String insertSQL = "insert into ts_user_role(role_id,user_id,unused_tm) values(?,?,?)";
//                    SQLQuery insert = session.createSQLQuery(insertSQL);
//                    insert.setLong(0, roleId);
//                    insert.setLong(1, userId);
//                    insert.setDate(2, unusedTm);
//                    insert.executeUpdate();
//			    }
//				return null;
//			}
//		});
		
	}

	public void deleteUserRole(final long userId, final long roleId) {
//		getHibernateTemplate().execute(new HibernateCallback<Object>() {
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//                SQLQuery delete = session.createSQLQuery("delete from ts_user_role_dept where user_id = ? and role_id = ?");
//                delete.setLong(0, userId);
//                delete.setLong(1, roleId);
//                delete.executeUpdate();
//
//                delete = session.createSQLQuery("delete from ts_user_role where user_id = ? and role_id = ?");
//                delete.setLong(0, userId);
//                delete.setLong(1, roleId);
//                delete.executeUpdate();
//				return null;
//			}
//		});
	}

	public void saveUserRoleAsDefault(final long userId, final long roleId){
//        getHibernateTemplate().execute(new HibernateCallback<Object>() {
//            public Object doInHibernate(Session session)
//                    throws HibernateException, SQLException {
//                SQLQuery insert = session.createSQLQuery("update ts_user_role set is_default = (case when role_id = ? " +
//                		"then 1 else 0 end) where user_id = ?");
//                insert.setLong(0, roleId);
//                insert.setLong(1, userId);
//                insert.executeUpdate();
//                return null;
//            }
//        });	    
	}
	
	@Override
	public List<User> findUserByIds(final List<Long> idList) {
		// TODO Auto-generated method stub
		if(idList==null||idList.size()==0){
			return new ArrayList();
		}
		List<User> tempList = null;
//		tempList = (List<User>)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				Criteria cr = session.createCriteria(User.class);
//				if (idList != null && idList.size() != 0) {
//					cr.add(Restrictions.in("id", idList));
//				}
//				
//				return cr.list();
//			}
//		});
		Map<String, Object> params = new HashMap();
//		params.put(SQL_PRE_IN+"id", "("+CommMethod.getListIds(idList)+")");
		params.put(SQL_PRE_IN+"id", idList);
		tempList=this.loadBy(params);
		
		
		return tempList;
	}
	
	@Override
	public List<User> findUserByIdsOpt(final List<Long> idList) {
		if(idList==null||idList.size()==0){
			return new ArrayList();
		}
//		idList.add(1l);
//		idList.add(2l);
//		StringBuffer jpql = new StringBuffer(
//                "SELECT o FROM xx as o WHERE o.xx IN (");
//        jpql.append( StringUtils.repeat(" ? ", ",", idList.size()));
//        jpql.append(") ");
//        System.out.println(jpql.toString());
//        if(true){
//        	return null;
//        }
		// TODO Auto-generated method stub
//		return (List<User>) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					@SuppressWarnings("serial")
//					public Object doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						String ids="";
//						for(Long id:idList){
//							ids = ids+id+",";
//						}
//						ids = ids.substring(0, ids.length()-1);
//						SQLQuery sqlQuery = session
//								.createSQLQuery("select user_id, emp_code, username, emp_name, dept_code from ts_user where user_id in ("+ids+")");
//						List list = sqlQuery.list();
//												
//						List<User> tempList = new ArrayList<User>();
//						User temp = null;
//						Object[] objs;
//						for(Object obj:list){
//							objs = (Object[])obj;
////							System.out.print(objs[0]+" "+objs[1]+" "+objs[2]+" "+objs[3]);
//							temp = new User();
//							temp.setId(Long.parseLong(""+objs[0]));
//							temp.setEmpCode((String)objs[1]);
//							temp.setUsername((String)objs[2]);
//							temp.setEmpName((String)objs[3]);
//							temp.setDeptCode((String)objs[4]);
//							tempList.add(temp);
//							
//						}
//						
//						return tempList;
//					}
//				});
		Map<String, Object> params = new HashMap();
//		params.put(SQL_PRE_IN+"id", "("+CommMethod.getListIds(idList)+")");
		params.put(SQL_PRE_IN+"id", idList);
		List<User> list=this.loadBy(params);

		List<User> userList=new ArrayList();
		//优化返回
		for(User temp:list){
			userList.add(new User(temp));
		}
		return userList;
	}

	@Override
	public List<User> findUserByNos(final List<String> noList) {
		if(null==noList||noList.size()==0){
			return new ArrayList();
		}
		// TODO Auto-generated method stub
		List<User> tempList = null;
//		tempList = (List<User>)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				Criteria cr = session.createCriteria(User.class);
//				if (noList != null && noList.size() != 0) {
//					cr.add(Restrictions.in("username", noList));
//				}
//				
//				return cr.list();
//			}
//		});
		Map<String, Object> params = new HashMap();
		params.put(SQL_PRE_IN+"username", noList);
		tempList=this.loadBy(params);
//		//不返回密码信息
//		for(User temp:tempList){
//			temp.setPassword(null);
//		}
		return tempList;
	}
	
	
	@Override
	public List<User> findUserByNosOpt(final List<String> noList) {
		if(null==noList||noList.size()==0){
			return new ArrayList();
		}
		// TODO Auto-generated method stub
//		return (List<User>) getHibernateTemplate().execute(
//				new HibernateCallback() {
//					@SuppressWarnings("serial")
//					public Object doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						String ids="";
//						for(String id:noList){
//							ids = ids+"'"+id+"',";
//						}
//						ids = ids.substring(0, ids.length()-1);
//						SQLQuery sqlQuery = session
//								.createSQLQuery("select user_id, emp_code, username, dept_code from ts_user where emp_code in ("+ids+")");
//						List list = sqlQuery.list();
//												
//						List<User> tempList = new ArrayList<User>();
//						User temp = null;
//						Object[] objs;
//						for(Object obj:list){
//							objs = (Object[])obj;
//							temp = new User();
//							temp.setId(Long.parseLong(""+objs[0]));
//							temp.setEmpCode((String)objs[1]);
//							temp.setUsername((String)objs[2]);
//							temp.setDeptCode((String)objs[3]);
//							tempList.add(temp);							
//						}
//						
//						return tempList;
//					}
//				});
		Map<String, Object> params = new HashMap();
		params.put(SQL_PRE_IN+"empCode", noList);
		List<User> tempList=this.loadBy(params);
		return tempList;
	}
}
