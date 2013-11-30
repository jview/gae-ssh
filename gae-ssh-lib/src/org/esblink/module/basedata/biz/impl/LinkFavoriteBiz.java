package org.esblink.module.basedata.biz.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.ILinkFavoriteBiz;
import org.esblink.module.basedata.dao.ILinkFavoriteDao;
import org.esblink.module.basedata.domain.LinkFavorite;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkFavorite BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkFavoriteBiz")
public class LinkFavoriteBiz extends BaseBIZ implements ILinkFavoriteBiz {

	// linkFavoriteDao
	@Resource(name="linkFavoriteDao")
	private ILinkFavoriteDao linkFavoriteDao;

	public void setLinkFavoriteDao(ILinkFavoriteDao linkFavoriteDao) {
		this.linkFavoriteDao = linkFavoriteDao;
	}

	public void saveLinkFavorite(LinkFavorite linkFavorite) {
		linkFavorite.setModifyUser(super.getCurrentUser().getId());
		linkFavorite.setModifyDate(new Date());
		// save linkFavorite
		this.linkFavoriteDao.save(linkFavorite);
	}

	public LinkFavorite findLinkFavorite(Long id) {
		// load linkFavorite
		LinkFavorite linkFavorite = this.linkFavoriteDao.load(id);

		return linkFavorite;
	}

	public Collection<LinkFavorite> findBy(LinkFavorite linkFavorite) {
		if (linkFavorite == null) {
			linkFavorite = new LinkFavorite();
		} else {
			BeanUtils.clearEmptyProperty(linkFavorite);
		}
		return this.linkFavoriteDao.findBy(linkFavorite);
	}

	public IPage<LinkFavorite> findPageBy(QueryObj queryObj) {
		return this.linkFavoriteDao.findPageBy(queryObj);
	}

	public void deleteLinkFavorites(String ids) {
		String[] idArray = ids.split(",");
		LinkFavorite d= null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete LinkFavorite
//			this.linkFavoriteDao.remove(id);
			d = this.findLinkFavorite(id);
			
			
		}
	}
	
	@Override
	public List<LinkFavorite> findFavorite(Long userId) {
		// TODO Auto-generated method stub
		return linkFavoriteDao.findFavorite(userId);
	}
	@Override
	public void deleteLinkFavorites(Long synUserId) throws Exception {
		// TODO Auto-generated method stub
		linkFavoriteDao.deleteLinkFavorites(synUserId);
	}
}
