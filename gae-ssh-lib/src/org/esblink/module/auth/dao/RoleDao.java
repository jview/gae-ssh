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
//			queryObj.setQueryObject("_like_name", roleName+"%");
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

   
    
	public List<Role> loadByIds(List<Long> ids) {
		if (ids.size() == 0)
			return new ArrayList<Role>(0);
//		DetachedCriteria detachedCriteria = DetachedCriteria
//				.forClass(Role.class);
//		detachedCriteria.add(Restrictions.in("id", ids));
//		return super.findBy(detachedCriteria);
		return null;
	}


}
