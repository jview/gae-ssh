package org.esblink.module.basedata.biz;

import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.basedata.domain.LinkRecently;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRecently BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkRecentlyBiz extends IBaseBIZ {

	public void saveLinkRecently(LinkRecently linkRecently);

	public LinkRecently findLinkRecently(Long id);

	public Collection<LinkRecently> findBy(LinkRecently linkRecently);

	public IPage<LinkRecently> findPageBy(QueryObj queryObj);
	
	/**
	 * 根据ID删除最近访问
	 * @param userId
	 */
	public void deleteLinkRecentlys(String ids);
	/**
	 * 根据用户ID查找最近访问
	 * @param userId,count
	 * @return
	 */
	
	public List<LinkRecently> findRecently(Long userId,final int count);
	/**
	 * 根据用户ID删除最近访问
	 * @param userId
	 */
	public void deleteLinkRecentlys(Long synUserId) throws Exception;
	
	public Module getModuleByUrl(String url) ;

}
