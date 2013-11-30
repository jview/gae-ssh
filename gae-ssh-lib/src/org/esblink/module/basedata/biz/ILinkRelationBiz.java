package org.esblink.module.basedata.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.LinkRelation;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRelation BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkRelationBiz extends IBaseBIZ {

	public void saveLinkRelation(LinkRelation linkRelation);

	public LinkRelation findLinkRelation(Long id);

	public Collection<LinkRelation> findBy(LinkRelation linkRelation);

	public IPage<LinkRelation> findPageBy(QueryObj queryObj);

	public void deleteLinkRelations(String ids);
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<LinkRelation> findRecently(final Long moduleId);

}
