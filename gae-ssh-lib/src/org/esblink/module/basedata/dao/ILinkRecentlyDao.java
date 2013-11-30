package org.esblink.module.basedata.dao;

import java.util.List;

import org.esblink.common.base.gae.IBaseDAO;
import org.esblink.module.basedata.domain.LinkRecently;

//@Service("linkRecentlyDao")
public interface ILinkRecentlyDao extends IBaseDAO<LinkRecently> {
	public void save(List<LinkRecently> entitys) throws Exception;

//	public List<LinkRecently> findAll();

	public LinkRecently findById(Long id);

	public boolean deleteById(Long id);
	
	/**
	 * 根据用户ID删除最近访问
	 * @param userId
	 */
	
	public void deleteLinkRecentlys(Long synUserId) throws Exception;
	/**
	 * 根据用户ID查找最近访问
	 * @param userId,count
	 * @return
	 */
	
	public List<LinkRecently> findRecently(final Long userId,final int count);

}
