package org.esblink.module.basedata.biz.impl;

import java.io.InputStream;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.basedata.biz.ILinkHistoryBiz;
import org.esblink.module.basedata.dao.ILinkHistoryDao;
import org.esblink.module.basedata.domain.LinkHistory;
import org.esblink.module.basedata.domain.LinkHistoryCount;
import org.esblink.module.basedata.util.Sysconfigs;
import org.esblink.module.framework.excel.ExcelExport;
import org.esblink.module.framework.excel.TableDefine;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkHistory BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkHistoryBiz")
public class LinkHistoryBiz extends BaseBIZ implements ILinkHistoryBiz {

	// linkHistoryDao
	@Resource(name="linkHistoryDao")
	private ILinkHistoryDao linkHistoryDao;

	public void setLinkHistoryDao(ILinkHistoryDao linkHistoryDao) {
		this.linkHistoryDao = linkHistoryDao;
	}

	public void saveLinkHistory(LinkHistory linkHistory) {
		linkHistory.setModifyUser(super.getCurrentUser().getId());
		linkHistory.setModifyDate(new Date());
		// save linkHistory
		this.linkHistoryDao.save(linkHistory);
	}

	public LinkHistory findLinkHistory(Long id) {
		// load linkHistory
		LinkHistory linkHistory = this.linkHistoryDao.load(id);

		return linkHistory;
	}

	public Collection<LinkHistory> findBy(LinkHistory linkHistory) {
		if (linkHistory == null) {
			linkHistory = new LinkHistory();
		} else {
			BeanUtils.clearEmptyProperty(linkHistory);
		}
		return this.linkHistoryDao.findBy(linkHistory);
	}

	public IPage<LinkHistory> findPageBy(QueryObj queryObj) {
		return this.linkHistoryDao.findPageBy(queryObj);
	}

	public void deleteLinkHistorys(String ids) {
		String[] idArray = ids.split(",");
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete LinkHistory
//			this.linkHistoryDao.remove(id);
		}
	}
	
	public InputStream exportExcel(QueryObj queryObj) {
		try {
//			for (String key : queryObj.getQueryFields()) {
//				String value = new String(queryObj.getQueryValue(key).getBytes("ISO-8859-1"), "UTF-8");
//				queryObj.setQueryValue(key, value);
//			}
//			queryObj.setPageSize(Sysconfigs.getInt(Sysconfigs.EXPORT_EXCEL_MAX_SIXE));
//			queryObj.setPageIndex(0);
//			Collection<LinkHistory> data = linkHistoryDao.findPageBy(queryObj);
//			TableDefine table = new TableDefine(getMessage("linkHistory"));
//			table.addColumn("userId", getMessage("linkHistory.userId"), 0);
//			table.addColumn("moduleId", getMessage("linkHistory.moduleId"), 0);
//			table.addColumn("moduleName", getMessage("linkHistory.moduleName"), 4000);
//			table.addColumn("actionUrl", getMessage("linkHistory.actionUrl"), 0);
//			table.addColumn("modifyUser", getMessage("linkHistory.modifyUser"), 4000);
//			table.addColumn("modifyDate", getMessage("linkHistory.modifyDate"), 5000);
//			return new ExcelExport(table).export(data);
			return null;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getMessage(String key) {
		return getMessageSource().getMessage(key, key);
	}
	
	/**
	 * 统计访问量
	 * @param moduleId
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByModule(Long moduleId){
		return this.linkHistoryDao.calVisitByModule(moduleId);
	}
	
	/**
	 * 根据用户统计各模块访问量
	 * @param userId,为空表示统计所有用户
	 * @return
	 */
	public List<LinkHistoryCount> calVisitByUser(Long userId){
		return this.linkHistoryDao.calVisitByUser(userId);
	}
}
