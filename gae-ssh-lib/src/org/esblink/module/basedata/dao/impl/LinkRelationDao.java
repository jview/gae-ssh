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
import org.esblink.module.basedata.dao.ILinkRelationDao;
import org.esblink.module.basedata.domain.LinkRelation;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRelation DAO接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkRelationDao")
public class LinkRelationDao extends BaseDAO<LinkRelation> implements ILinkRelationDao {
	private static final Logger	logger	= Logger.getLogger(LinkRelationDao.class.getName());
	public Collection<LinkRelation> findBy(QueryObj queryObj) {
		return super.findBy(queryObj);
	}

	public IPage<LinkRelation> findPageBy(QueryObj queryObj) {
		IPage<LinkRelation> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=super.findPageBy(LinkRelation.class, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.warning("-----findPageBy--"+queryObj.getQueryValues()+", exception:"+e.getMessage());
		}
		return result;
	}


	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<LinkRelation> findRecently(final Long moduleId){
		QueryObj queryObj = new QueryObj(-1, -1, "orderCount", true);
		queryObj.setQueryObject("moduleId", moduleId);
		Collection<LinkRelation> list=this.findBy(queryObj);
		List<LinkRelation> res = new ArrayList();
		res.addAll(list);
		return res;
	}
}
