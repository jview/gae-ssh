package org.esblink.module.basedata.biz.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.auth.dao.IModuleDao;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.basedata.biz.ILinkHistoryBiz;
import org.esblink.module.basedata.biz.ILinkRecentlyBiz;
import org.esblink.module.basedata.dao.ILinkRecentlyDao;
import org.esblink.module.basedata.domain.LinkHistory;
import org.esblink.module.basedata.domain.LinkRecently;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRecently BIZ接口实现
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@Service("linkRecentlyBiz")
public class LinkRecentlyBiz extends BaseBIZ implements ILinkRecentlyBiz {

	// linkRecentlyDao
	@Resource(name="linkRecentlyDao")
	private ILinkRecentlyDao linkRecentlyDao;
	@Resource(name="linkHistoryBiz")
	private ILinkHistoryBiz linkHistoryBiz;
	@Resource(name="moduleDao")
	private IModuleDao moduleDao;

	public void setLinkRecentlyDao(ILinkRecentlyDao linkRecentlyDao) {
		this.linkRecentlyDao = linkRecentlyDao;
	}
	public void setModuleDao(IModuleDao moduleDao){
		this.moduleDao=moduleDao;
	}

	public void saveLinkRecently(LinkRecently linkRecently) {
		linkRecently.setModifyUser(super.getCurrentUser().getId());
		linkRecently.setModifyDate(new Date());
		log.info("-----saveLinkRecently--linkRecently.id="+linkRecently.getId());;
		// save linkRecently
		if(linkRecently.getId()!=null && linkRecently.getId()>0){
			this.linkRecentlyDao.update(linkRecently);
		}
		else{
			this.linkRecentlyDao.save(linkRecently);
		}
		
		LinkHistory history=new LinkHistory();
		history.setUserId(getCurrentUser().getId());
		history.setModuleId(linkRecently.getModuleId());
		history.setActionUrl(linkRecently.getActionUrl());
		history.setModuleName(linkRecently.getModuleName());
		history.setModifyDate(new Date());
		history.setModifyUser(getCurrentUser().getId());
		history.setParamTypeId(linkRecently.getParamTypeId());
		history.setParamValue(linkRecently.getParamValue());
		//将数据保存到历史数据表
		linkHistoryBiz.saveLinkHistory(history);
	}

	public LinkRecently findLinkRecently(Long id) {
		// load linkRecently
		LinkRecently linkRecently = this.linkRecentlyDao.load(id);

		return linkRecently;
	}

	public Collection<LinkRecently> findBy(LinkRecently linkRecently) {
		if (linkRecently == null) {
			linkRecently = new LinkRecently();
		} else {
			BeanUtils.clearEmptyProperty(linkRecently);
		}
		return this.linkRecentlyDao.findBy(linkRecently);
	}

	public IPage<LinkRecently> findPageBy(QueryObj queryObj) {
		return this.linkRecentlyDao.findPageBy(queryObj);
	}

	public void deleteLinkRecentlys(String ids) {
		String[] idArray = ids.split(",");
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			// delete LinkRecently
//			this.linkRecentlyDao.remove(id);
		}
	}
	
	@Override
	public void deleteLinkRecentlys(Long synUserId) throws Exception {
		// TODO Auto-generated method stub
		linkRecentlyDao.deleteLinkRecentlys(synUserId);
	}

	@Override
	public List<LinkRecently> findRecently(Long userId,final int count){
		// TODO Auto-generated method stub
		return linkRecentlyDao.findRecently(userId,count);
	}
	
	@Override
	public Module getModuleByUrl(String url) {
		// TODO Auto-generated method stub
		return this.moduleDao.getModuleByUrl(url);
	}
	public void setLinkHistoryBiz(ILinkHistoryBiz linkHistoryBiz) {
		this.linkHistoryBiz = linkHistoryBiz;
	}
	
	
}
