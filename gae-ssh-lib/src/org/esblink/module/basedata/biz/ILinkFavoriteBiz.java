package org.esblink.module.basedata.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.LinkFavorite;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkFavorite BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkFavoriteBiz extends IBaseBIZ {

	public void saveLinkFavorite(LinkFavorite linkFavorite);

	public LinkFavorite findLinkFavorite(Long id);

	public Collection<LinkFavorite> findBy(LinkFavorite linkFavorite);

	public IPage<LinkFavorite> findPageBy(QueryObj queryObj);

	public void deleteLinkFavorites(String ids);
	public List<LinkFavorite> findFavorite(Long userId) ;
	/**
	 * 根据用户ID删除收藏夹
	 * @param userId
	 */
	public void deleteLinkFavorites(Long synUserId) throws Exception;

}
