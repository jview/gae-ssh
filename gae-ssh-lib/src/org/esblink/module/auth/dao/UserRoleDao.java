package org.esblink.module.auth.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.auth.domain.UserRole;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: UserRole DAO接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
//@Service("userRoleDao")
public class UserRoleDao extends BaseDAO<UserRole> implements IUserRoleDao {
	private static final Logger	logger	= Logger.getLogger(UserRoleDao.class.getName());
	public Collection<UserRole> findBy(QueryObj queryObj) {
		return super.findBy(queryObj);
	}

	public IPage<UserRole> findPageBy(QueryObj queryObj) {
		IPage<UserRole> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=super.findPageBy(UserRole.class, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.warning("-----findPageBy--"+queryObj.getQueryValues()+", exception:"+e.getMessage());
		}
		return result;
	}


	
	/**
	 * 根据用户或角色查询用户权限
	 * @param userId
	 * @param roleId
	 * @return
	 */
	public List<UserRole> findUserRoleByUserOrRole(final Long userId, final Long roleId){
		QueryObj queryObj = new QueryObj(-1, -1, null, true);
		if(userId!=null&&userId>0){
			queryObj.setQueryObject("userId", userId);
		}
		if(roleId!=null&&roleId>0){
			queryObj.setQueryObject("roleId", roleId);
		}
		Collection<UserRole> list=this.findBy(queryObj);
		List<UserRole> res = new ArrayList();
		res.addAll(list);
		return res;
	}
}
