package org.esblink.module.customer.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseDAO;
import org.esblink.module.customer.domain.ContactInfo;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: ContactInfo DAO接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2013.04.29     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public class ContactInfoDao extends BaseDAO<ContactInfo> implements IContactInfoDao {

	public Collection<ContactInfo> findBy(QueryObj queryObj) {
		return super.findBy(queryObj);
	}

	public IPage<ContactInfo> findPageBy(QueryObj queryObj) {
		IPage<ContactInfo> result=null;
		try {
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for(String queryField:queryObj.getQueryObjects()){
				paramsMap.put(queryField, queryObj.getQueryObject(queryField));
			}
//			System.out.println("------paramsMap="+paramsMap+" queryField="+queryObj.getQueryObjects());
			result=super.findPageBy(ContactInfo.class, paramsMap, queryObj.getPageSize(), queryObj.getPageIndex(),  queryObj.getSortField(), queryObj.isAsc());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}


}
