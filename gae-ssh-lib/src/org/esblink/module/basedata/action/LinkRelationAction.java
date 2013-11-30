package org.esblink.module.basedata.action;

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
import org.esblink.module.basedata.biz.ILinkRelationBiz;
import org.esblink.module.basedata.domain.LinkRelation;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkRelation Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("linkRelationAction")
public class LinkRelationAction extends BaseGridAction<LinkRelation> {
	@Resource(name="linkRelationBiz")
	private ILinkRelationBiz linkRelationBiz;
	private LinkRelation linkRelation;
	private String ids;
	private String msg;
	private Collection<LinkRelation> linkRelations;
	private int total;
	private String synmoduleId;
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	
	//根据模块Id查询关联的子模块
	public String findRelation(){
		log.info("-------findRelation--code="+this.synmoduleId);
		if(null!=synmoduleId){
			linkRelations = linkRelationBiz.findRecently(new Long(synmoduleId));
			total=linkRelations.size();
			log.info("=====findRelation=synmoduleId="+synmoduleId+" totalSize="+total);
		}
		
		return SUCCESS;
	}
	public String saveLinkRelation() {
		try {
			linkRelationBiz.saveLinkRelation(linkRelation);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findLinkRelation() {
		linkRelation = linkRelationBiz.findLinkRelation(linkRelation.getId());
		return SUCCESS;
	}

	public String findByLinkRelation() {
		linkRelations = linkRelationBiz.findBy(linkRelation);
		return SUCCESS;
	}

	public String deleteLinkRelations() {
		try {
			linkRelationBiz.deleteLinkRelations(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByLinkRelation() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		IPage<LinkRelation> page = linkRelationBiz.findPageBy(queryObj);
		linkRelations = page.getData();
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(LinkRelation dist:linkRelations){				
				domainList.add(dist);
			}
	
	//				如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
			
			for(LinkRelation dist:linkRelations){
				for(User user:userList){
					if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
						dist.setCreate_disp(user.getUsername());
					}
					if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
						dist.setModify_disp(user.getUsername());
					}
				}
	
			}
		}
		catch(Exception e){
			log.error("---dataError dispShow--createUser/modifyUser", e);
		}
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setLinkRelationBiz(ILinkRelationBiz linkRelationBiz) {
		this.linkRelationBiz = linkRelationBiz;
	}

	public LinkRelation getLinkRelation() {
		return this.linkRelation;
	}

	public void setLinkRelation(LinkRelation linkRelation) {
		this.linkRelation = linkRelation;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<LinkRelation> getLinkRelations() {
		return this.linkRelations;
	}

	public int getTotal() {
		return this.total;
	}
	public Collection<LinkRelation> getRows() {
		return this.linkRelations;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}
	
	public String getSynmoduleId() {
		return synmoduleId;
	}
	public void setSynmoduleId(String synmoduleId) {
		this.synmoduleId = synmoduleId;
	}
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}
	
	
}
