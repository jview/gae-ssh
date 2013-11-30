package org.esblink.module.auth.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.auth.action.dto.UserRoleDeptDto;
import org.esblink.module.auth.action.dto.UserRoleDto;
import org.esblink.module.auth.domain.Role;

import com.esblink.dev.util.DbInfo;

//@Service("roleDao")
public class RoleDao extends BaseDAO<Role> implements IRoleDao {

    public List<UserRoleDeptDto> getUserRoleDept(final long userId, final long roleId) {
//        return getHibernateTemplate().execute(new HibernateCallback<List<UserRoleDeptDto>>() {
//            public List<UserRoleDeptDto> doInHibernate(Session session) throws HibernateException, SQLException {
//                String SQL = "select urd.DEPT_CODE, d.dept_name, urd.INHERITED_FLG from ts_user_role_dept urd inner join " +
//                		"tm_department d on urd.dept_code = d.dept_code where urd.user_id = ? and urd.role_id = ?";
//                SQLQuery sqlQuery = session.createSQLQuery(SQL);
//                sqlQuery.addScalar("DEPT_CODE", new StringType());
//                sqlQuery.addScalar("dept_name", new StringType());
//                sqlQuery.addScalar("INHERITED_FLG", new ByteType());
//                sqlQuery.setLong(0, userId);
//                sqlQuery.setLong(1, roleId);
//
//                List<?> tempList = sqlQuery.list();
//
//                List<UserRoleDeptDto> retList = new ArrayList<UserRoleDeptDto>();
//                for (Object temp : tempList) {
//                    Object[] at = (Object[]) temp;
//                    UserRoleDeptDto dto = new UserRoleDeptDto();
//                    dto.setDeptCode((String) at[0]);
//                    dto.setDeptName((String) at[1]);
//                    dto.setInherited((Byte) at[2]);
//                    retList.add(dto);
//                }
//                Collections.sort(retList, ComparatUtil.UserRoleDeptDtoComparator);
//                return retList;
//            }
//        });
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<Role> findRolesByName(String roleName) {
		if (roleName == null || "".equals(roleName.trim())) {
			return findAll();
		} else {
//			StringBuffer querySql = new StringBuffer();
//			querySql.append("from org.esblink.module.auth.domain.Role where name like '%");
//			querySql.append(roleName);
//			querySql.append("%'");
////			return super.getHibernateTemplate().find(querySql.toString());
//			return null;
			roleName=roleName.trim();
			QueryObj queryObj = new QueryObj(-1, -1, null, false);
			queryObj.setQueryObject("name", roleName);
			Collection<Role> roleList=this.findBy(queryObj);
			List<Role> rList=new ArrayList<Role>();
			rList.addAll(roleList);
			return rList;
			
		}
	}

	public List<Long[]> getAllRoleModuleIds() {
//		return getHibernateTemplate().execute(
//				new HibernateCallback<List<Long[]>>() {
//					@SuppressWarnings("rawtypes")
//					public List<Long[]> doInHibernate(Session session)
//							throws HibernateException, SQLException {
//						SQLQuery sqlQuery = session
//								.createSQLQuery("select role_id as rid, module_id as mid from ts_role_module");
//						sqlQuery.addScalar("rid", Hibernate.LONG);
//						sqlQuery.addScalar("mid", Hibernate.LONG);
//						List tempList = sqlQuery.list();
//						List<Long[]> retList = new ArrayList<Long[]>();
//						for (Object temp : tempList) {
//							Object[] at = (Object[]) temp;
//							Long[] ids = new Long[2];
//							ids[0] = (Long) at[0];
//							ids[1] = (Long) at[1];
//							retList.add(ids);
//						}
//						return retList;
//					}
//				});
		return null;
	}

	public void saveRoleModule(final long roleId, final List<Long> moduleIds) {
		final StringBuilder sb = new StringBuilder();
		if (moduleIds != null && moduleIds.size() > 0) {
			sb.append("insert into ts_role_module(role_id, module_id) ");

			boolean first = true;
			for (Long moduleId : moduleIds) {
				if (first)
					first = false;
				else
					sb.append("union ");
				sb.append("select ");
				sb.append(roleId);
				sb.append(", ");
				sb.append(moduleId);
				if(DbInfo.isOracle()){
					sb.append(" from dual ");
				}
			}
		}
	}
	

	/**
	 * 
	 * @param ids 1,2,3,4
	 * @return
	 */
	private String getRangeIds(String ids){
		if(ids==null||ids.length()==0){
			return "(0)";
		}
		ids = ids.trim();
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		if(ids.startsWith("(")){
			return ids;
		}
		else{
			return "("+ids+")";
		}
	}
	
	public void updateRoleModules(final Role role) throws Exception {
//		getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				String module_ids = role.getUncheckIds();
//				module_ids = getRangeIds(module_ids);
//				String sql = "delete from ts_role_module where role_id=" + role.getId()+" and module_id in "+module_ids;
//				session.createSQLQuery(sql).executeUpdate();
//				session.flush();
//				for (Module module : role.getRoleModules()) {
//					sql = "insert into ts_role_module(role_id,module_id) values(" + role.getId() + "," + module.getId() + ")";
//					session.createSQLQuery(sql).executeUpdate();
//				}
//				return null;
//			}
//		});
	}

	@SuppressWarnings("unchecked")
    public List<Object[]> getUserRoleIds(final long userId) {
//        return getHibernateTemplate().execute(new HibernateCallback<List<Object[]>>() {
//            public List<Object[]> doInHibernate(Session session) throws HibernateException, SQLException {
//                SQLQuery sqlQuery = session.createSQLQuery("select role_id, UNUSED_TM from ts_user_role where user_id = ?");
//                sqlQuery.addScalar("role_id", Hibernate.LONG);
//                sqlQuery.addScalar("UNUSED_TM", Hibernate.DATE);
//                sqlQuery.setLong(0, userId);
//                return sqlQuery.list();
//            }
//        });
    	return null;
    }
    
    @SuppressWarnings("unchecked")
	public List<Long> findRoleModuleIds(final Long roleId) {
//		return (List<Long>)getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				StringBuffer sqls = new StringBuffer();
//				sqls.append("select TS_ROLE_MODULE.MODULE_ID from TS_ROLE_MODULE  where TS_ROLE_MODULE.ROLE_ID=");
//				sqls.append(roleId);
//				Query query = session.createSQLQuery(sqls.toString()).addScalar("MODULE_ID", Hibernate.LONG);
//				List<Long> temp = query.list();
//				return temp;
//			}
//		});
    	return null;
	}

    public List<UserRoleDto> loadUserRole(final long userId) {
//        return getHibernateTemplate().execute(new HibernateCallback<List<UserRoleDto>>() {
//            @SuppressWarnings("unchecked")
//            public List<UserRoleDto> doInHibernate(Session session) throws HibernateException, SQLException {
//                String SQL = "select r.role_id, r.role_name, r.role_desc, ur.is_default, ur.UNUSED_TM from ts_user_role ur inner join "
//                        + "ts_role r on ur.role_id = r.role_id where ur.user_id = ?";
//                SQLQuery sqlQuery = session.createSQLQuery(SQL);
//                sqlQuery.addScalar("role_id", new LongType());
//                sqlQuery.addScalar("role_name", new StringType());
//                sqlQuery.addScalar("role_desc", new StringType());
//                sqlQuery.addScalar("is_default", new ByteType());
//                sqlQuery.addScalar("UNUSED_TM", new DateType());
//                sqlQuery.setLong(0, userId);
//                List<Object[]> result = sqlQuery.list();
//                List<UserRoleDto> queryRs = new ArrayList<UserRoleDto>();
//                for(Object[] tmp : result){
//                    UserRoleDto userEmp = new UserRoleDto();
//                    userEmp.setUserId(userId);
//                    userEmp.setRoleId((Long)tmp[0]);
//                    userEmp.setRoleName((String)tmp[1]);
//                    userEmp.setDescription((String)tmp[2]);
//                    userEmp.setIsDefault((Byte)tmp[3]);
//                    userEmp.setUnusedTm((Date)tmp[4]);
//                    queryRs.add(userEmp);
//                }
//                return queryRs;
//            }
//        });
    	return null;
    }
    
	public List<Role> loadByIds(List<Long> ids) {
		if (ids.size() == 0)
			return new ArrayList<Role>(0);
//		DetachedCriteria detachedCriteria = DetachedCriteria
//				.forClass(Role.class);
//		detachedCriteria.add(Restrictions.in("id", ids));
//		return super.findBy(detachedCriteria);
		return null;
	}

	public void saveUserRoleDept(final long userId, final long roleId, final List<UserRoleDeptDto> userRoleDeptList) {
//		getHibernateTemplate().execute(new HibernateCallback<Object>() {
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
//			    String deleteSQL = "delete from ts_user_role_dept where user_id = ? and role_id = ?";
//				SQLQuery delete = session.createSQLQuery(deleteSQL);
//				delete.setLong(0, userId);
//				delete.setLong(1, roleId);
//				delete.executeUpdate();
//				
//                if (userRoleDeptList == null || userRoleDeptList.size() == 0) {
//                    return null;
//                }
//                String insertSQL = "insert into ts_user_role_dept(USER_ID ,ROLE_ID ,DEPT_CODE ,INHERITED_FLG) values(?,?,?,?)";
//                SQLQuery insert = session.createSQLQuery(insertSQL);
//                for (UserRoleDeptDto tmp : userRoleDeptList) {
//                    insert.setLong(0, userId);
//                    insert.setLong(1, roleId);
//                    insert.setString(2, tmp.getDeptCode());
//                    insert.setByte(3, tmp.getInherited());
//                    insert.executeUpdate();
//                }
//				return null;
//			}
//		});
		

	}
}
