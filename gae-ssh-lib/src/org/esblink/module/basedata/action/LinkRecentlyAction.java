package org.esblink.module.basedata.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.common.base.domain.IUser;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.basedata.biz.ILinkRecentlyBiz;
import org.esblink.module.basedata.domain.LinkRecently;
import org.esblink.module.framework.domain.CacheUser;
import org.springframework.stereotype.Controller;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRecently Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("linkRecentlyAction")
public class LinkRecentlyAction extends BaseGridAction<LinkRecently> {
	@Resource(name="linkRecentlyBiz")
	private ILinkRecentlyBiz linkRecentlyBiz;
	private LinkRecently linkRecently=new LinkRecently();
//	private ILinkHistoryBiz linkHistoryBiz;
	private String synicon;
	private String synmoduleId;
	private String synmoduleName;
	private String synactionUrl;
	private String ids;
	private String msg;
	private Collection<LinkRecently> linkRecentlys;
	private List<LinkRecently> listRecently;
	private int total;
	private IUser getSessionUser() {
		IUser user =  (IUser)getCurrentUser();
		if(user == null || user.getId()==null )
		{
			user = new CacheUser();
		}
		return user;
	}
	public String saveLinkRecently() {
		try {
			
			//System.out.println("synmoduleName===="+synmoduleName);
			linkRecently=new LinkRecently();
			linkRecently.setUserId(getCurrentUser().getId());
			if(synmoduleId!=null&&synmoduleId.matches("\\d+")){
				linkRecently.setModuleId(new Long(synmoduleId));
			}
			else{
				//在模块id不是数字的情况下，用url反向查到对应的模块id
				Module module = this.linkRecentlyBiz.getModuleByUrl(synactionUrl);
				if(module!=null){
					linkRecently.setModuleId(module.getId());
				}
				else{
					log.error("synmoduleId="+synmoduleId+" synactionUrl="+synactionUrl+" module not found!");
				}
			}
			linkRecently.setActionUrl(synactionUrl);
			linkRecently.setModuleName(synmoduleName);
			linkRecently.setModifyDate(new Date());
			linkRecently.setModifyUser(getCurrentUser().getId());
			linkRecently.setOrderCount(new Long(0));
			if(synicon!=null&&!"null".equals(synicon)){
				linkRecently.setIcon(synicon);
			}
			
			//将数据保存到最近访问表
			linkRecentlyBiz.saveLinkRecently(linkRecently);
			msg = "ok";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	public String findRecently() {
		//数据显示条数
		int count=10;
		listRecently = linkRecentlyBiz.findRecently(getCurrentUser().getId(),count);
		List lists = new ArrayList();
		int i=1;
		for(LinkRecently list:listRecently){
			//设置编号
			list.setNum(new Long(i));
			//截取时间  例如：2011-05-04 18:46 截取18:46
			list.setTempTm(list.getModifyDate().toString().substring(11, 16));
			lists.add(list);
			i++;
		}
		listRecently=lists;
		return SUCCESS;
	}

	public String findLinkRecently() {
		linkRecently = linkRecentlyBiz.findLinkRecently(linkRecently.getId());
		return SUCCESS;
	}

	public String findByLinkRecently() {
		linkRecentlys = linkRecentlyBiz.findBy(linkRecently);
		return SUCCESS;
	}

	public String deleteLinkRecentlys() {
		try {
			linkRecentlyBiz.deleteLinkRecentlys(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	
	public String deleteAllRecently() {
		try {
			linkRecentlyBiz.deleteLinkRecentlys(getSessionUser().getId());
			msg = "ok";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByLinkRecently() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		IPage<LinkRecently> page = linkRecentlyBiz.findPageBy(queryObj);
		linkRecentlys = page.getData();
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setLinkRecentlyBiz(ILinkRecentlyBiz linkRecentlyBiz) {
		this.linkRecentlyBiz = linkRecentlyBiz;
	}

	public LinkRecently getLinkRecently() {
		return this.linkRecently;
	}

	public void setLinkRecently(LinkRecently linkRecently) {
		this.linkRecently = linkRecently;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<LinkRecently> getLinkRecentlys() {
		return this.linkRecentlys;
	}

	public int getTotal() {
		return this.total;
	}
	public Collection<LinkRecently> getRows() {
		return this.linkRecentlys;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}
	
	public String getSynicon() {
		return synicon;
	}
	public void setSynicon(String synicon) {
		this.synicon = synicon;
	}
	public String getSynmoduleId() {
		return synmoduleId;
	}
	public void setSynmoduleId(String synmoduleId) {
		this.synmoduleId = synmoduleId;
	}
	public String getSynmoduleName() {
		return synmoduleName;
	}
	public void setSynmoduleName(String synmoduleName) {
		this.synmoduleName = synmoduleName;
	}
	public String getSynactionUrl() {
		return synactionUrl;
	}
	public void setSynactionUrl(String synactionUrl) {
		this.synactionUrl = synactionUrl;
	}
//	public void setLinkHistoryBiz(ILinkHistoryBiz linkHistoryBiz) {
//		this.linkHistoryBiz = linkHistoryBiz;
//	}
	public List<LinkRecently> getListRecently() {
		return listRecently;
	}
	
	
}
