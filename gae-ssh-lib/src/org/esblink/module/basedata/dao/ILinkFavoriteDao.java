package org.esblink.module.basedata.dao;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.LinkFavorite;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkFavorite DAO接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkFavoriteDao extends IBaseDAO<LinkFavorite> {

	public Collection<LinkFavorite> findBy(QueryObj queryObj);

	public IPage<LinkFavorite> findPageBy(QueryObj queryObj);
	
	/**
	 * 根据用户ID查找收藏夹
	 * @param userId
	 * @return
	 */

	public List<LinkFavorite> findFavorite(final Long userId);
	/**
	 * 根据用户ID删除收藏夹
	 * @param userId
	 */
	public void deleteLinkFavorites(Long userId) throws Exception;

}
