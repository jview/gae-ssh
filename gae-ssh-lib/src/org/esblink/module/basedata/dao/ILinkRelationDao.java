package org.esblink.module.basedata.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.LinkRelation;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRelation DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkRelationDao extends IBaseDAO<LinkRelation> {

	public Collection<LinkRelation> findBy(QueryObj queryObj);

	public IPage<LinkRelation> findPageBy(QueryObj queryObj);
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<LinkRelation> findRecently(final Long moduleId);

}
