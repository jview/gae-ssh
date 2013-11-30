package org.esblink.module.basedata.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.basedata.dao.ILinkFavoriteDao;
import org.esblink.module.basedata.domain.LinkFavorite;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkFavorite DAO接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkFavoriteDao")
public class LinkFavoriteDao extends BaseDAO<LinkFavorite> implements ILinkFavoriteDao {
	private static final Logger	logger	= Logger.getLogger(LinkFavoriteDao.class.getName());
	public Collection<LinkFavorite> findBy(QueryObj queryObj) {
		return super.findBy(queryObj);
	}

	public IPage<LinkFavorite> findPageBy(QueryObj queryObj) {
		IPage<LinkFavorite> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=super.findPageBy(LinkFavorite.class, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.warning("-----findPageBy--"+queryObj.getQueryValues()+", exception:"+e.getMessage());
		}
		return result;
	}


	
	/**
	 * 根据用户ID查找收藏夹
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<LinkFavorite> findFavorite(final Long userId){
		QueryObj queryObj = new QueryObj(-1, -1, "orderCount", true);
		queryObj.setQueryObject("userId", userId);
		Collection<LinkFavorite> list=this.findBy(queryObj);
		List<LinkFavorite> res = new ArrayList();
		res.addAll(list);
		return res;
	}
	/**
	 * 根据用户ID删除收藏夹
	 * @param userId
	 */
	public void deleteLinkFavorites(Long synUserId) throws Exception {
		final String syncSQL = "delete from ts_link_favorite where user_id=" + synUserId;
//		getHibernateTemplate().execute(new HibernateCallback(){
//			public Object doInHibernate(Session session){
//				Query query = session.createSQLQuery(syncSQL);
//				query.executeUpdate();
//				return null;
//			}
//		});
	}
}
