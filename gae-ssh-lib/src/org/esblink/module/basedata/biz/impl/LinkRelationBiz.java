package org.esblink.module.basedata.biz.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.ILinkRelationBiz;
import org.esblink.module.basedata.dao.ILinkRelationDao;
import org.esblink.module.basedata.domain.LinkRelation;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRelation BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkRelationBiz")
public class LinkRelationBiz extends BaseBIZ implements ILinkRelationBiz {

	// linkRelationDao
	@Resource(name="linkRelationDao")
	private ILinkRelationDao linkRelationDao;

	public void setLinkRelationDao(ILinkRelationDao linkRelationDao) {
		this.linkRelationDao = linkRelationDao;
	}

	public void saveLinkRelation(LinkRelation linkRelation) {
		if (linkRelation.getCreateUser()==null) {
			linkRelation.setCreateUser(super.getCurrentUser().getId());
			linkRelation.setCreateDate(new Date());
		}
		linkRelation.setModifyUser(super.getCurrentUser().getId());
		linkRelation.setModifyDate(new Date());
		// save linkRelation
		this.linkRelationDao.save(linkRelation);
	}

	public LinkRelation findLinkRelation(Long id) {
		// load linkRelation
		LinkRelation linkRelation = this.linkRelationDao.load(id);

		return linkRelation;
	}

	public Collection<LinkRelation> findBy(LinkRelation linkRelation) {
		if (linkRelation == null) {
			linkRelation = new LinkRelation();
		} else {
			BeanUtils.clearEmptyProperty(linkRelation);
		}
		return this.linkRelationDao.findBy(linkRelation);
	}

	public IPage<LinkRelation> findPageBy(QueryObj queryObj) {
		return this.linkRelationDao.findPageBy(queryObj);
	}

	public void deleteLinkRelations(String ids) {
		String[] idArray = ids.split(",");
		LinkRelation d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete LinkRelation
//			this.linkRelationDao.remove(id);
			d=this.findLinkRelation(id);
			
			
		}
	}
	
	/**
	 * 根据菜单栏moduleId查找关联的菜单
	 * @param moduleId
	 * @return
	 */
	public List<LinkRelation> findRecently(final Long moduleId) {
		// TODO Auto-generated method stub
		return linkRelationDao.findRecently(moduleId);
	}
}
