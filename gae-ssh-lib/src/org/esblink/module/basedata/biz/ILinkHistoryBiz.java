package org.esblink.module.basedata.biz;

import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.IBaseBIZ;
import org.esblink.module.basedata.domain.LinkHistory;
import org.esblink.module.basedata.domain.LinkHistoryCount;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkHistory BIZ接口定义
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
public interface ILinkHistoryBiz extends IBaseBIZ {

	public void saveLinkHistory(LinkHistory linkHistory);

	public LinkHistory findLinkHistory(Long id);

	public Collection<LinkHistory> findBy(LinkHistory linkHistory);

	public IPage<LinkHistory> findPageBy(QueryObj queryObj);

	public void deleteLinkHistorys(String ids);
	
	public InputStream exportExcel(QueryObj queryObj);
	
	/**
	 * 统计访问量
	 * @param moduleId
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByModule(Long moduleId);
	
	/**
	 * 根据用户统计各模块访问量
	 * @param userId,为空表示统计所有用户
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByUser(Long userId);

}
