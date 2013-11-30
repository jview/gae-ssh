package org.esblink.module.basedata.action;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.basedata.biz.ILinkHistoryBiz;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LinkHistory;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkHistory Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("linkHistoryAction")
public class LinkHistoryAction extends BaseGridAction<LinkHistory> {
	private String moduleCode="linkHistory";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	@Resource(name="linkHistoryBiz")
	private ILinkHistoryBiz linkHistoryBiz;
	private LinkHistory linkHistory;
	private String ids;
	private String msg;
	private Collection<LinkHistory> linkHistorys;
	private int total;
	private InputStream stream;

	public String saveLinkHistory() {
		try {
//			linkHistoryBiz.saveLinkHistory(linkHistory);
			msg="error";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findLinkHistory() {
		linkHistory = linkHistoryBiz.findLinkHistory(linkHistory.getId());
		return SUCCESS;
	}

	public String findByLinkHistory() {
		linkHistorys = linkHistoryBiz.findBy(linkHistory);
		return SUCCESS;
	}

	public String deleteLinkHistorys() {
		try {
//			linkHistoryBiz.deleteLinkHistorys(ids);
			msg="error";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByLinkHistory() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		IPage<LinkHistory> page = linkHistoryBiz.findPageBy(queryObj);
		linkHistorys = page.getData();
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(LinkHistory dist:linkHistorys){				
				domainList.add(dist);
			}
	
	//				如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
			
			for(LinkHistory dist:linkHistorys){
				for(User user:userList){
//					if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
//						dist.setCreate_disp(user.getUsername());
//					}
					if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
						dist.setModify_disp(user.getUsername());
					}
				}
	
			}
		}
		catch(Exception e){
			log.error("---dataError dispShow--modifyUser", e);
		}
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}
	
	public String exportExcel() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		String queryInfo = queryObj.toString();
		stream = linkHistoryBiz.exportExcel(queryObj);
		this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "exportExcel", "queryInfo="+queryInfo, "", SUCCESS,linkHistory);
		return SUCCESS;
	} 

	public boolean isSuccess() {
		return true;
	}

	public void setLinkHistoryBiz(ILinkHistoryBiz linkHistoryBiz) {
		this.linkHistoryBiz = linkHistoryBiz;
	}
	

	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}

	public LinkHistory getLinkHistory() {
		return this.linkHistory;
	}

	public void setLinkHistory(LinkHistory linkHistory) {
		this.linkHistory = linkHistory;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<LinkHistory> getLinkHistorys() {
		return this.linkHistorys;
	}

	public int getTotal() {
		return this.total;
	}
	
	public Collection<LinkHistory> getRows() {
		return this.linkHistorys;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	public InputStream getStream() {
		return stream;
	}
	
}
