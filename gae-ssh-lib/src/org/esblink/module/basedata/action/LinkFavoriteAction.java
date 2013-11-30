package org.esblink.module.basedata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.common.base.domain.IUser;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.auth.domain.User;
import org.esblink.module.basedata.action.dto.LinkFavoriteDto;
import org.esblink.module.basedata.biz.ILinkFavoriteBiz;
import org.esblink.module.basedata.biz.ILinkRecentlyBiz;
import org.esblink.module.basedata.domain.LinkFavorite;
import org.esblink.module.basedata.domain.LinkRecently;
import org.esblink.module.framework.domain.CacheUser;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * <pre>
 * *********************************************
 * Copyright esblink.net.
 * All rights reserved.
 * Description: LinkFavorite Action
 * HISTORY
 * *********************************************
 *  ID   DATE           PERSON          REASON
 *  1    2012.07.24     CodeGen         Create
 * 
 * *********************************************
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("linkFavoriteAction")
@Scope("prototype")
public class LinkFavoriteAction extends BaseGridAction<LinkFavorite> {
	@Resource(name="linkFavoriteBiz")
	private ILinkFavoriteBiz linkFavoriteBiz;
	private LinkFavorite linkFavorite;
	private String ids;
	private String msg;
	private Collection<LinkFavorite> linkFavorites;
	private List<LinkRecently> listRecently;
	private List<LinkFavorite> listFavorite;
	private List<LinkFavoriteDto> favoList;
	@Resource(name="linkRecentlyBiz")
	private ILinkRecentlyBiz linkRecentlyBiz;
	@Resource(name="userBiz")
	private IUserBiz userBiz;
	private String moduleId;
	private String synicon;
	private String syncmoduleId;
	private String syncmoduleName;
	private String syncactionUrl;
	private String syncorderCount;
	private String paramTypeId;
	private String paramValue;
	
	private int total;
	
	/**
	 * 生成返回信息
	 * @param isOk 操作是否成功
	 * @param msg
	 * @param log4j
	 * @return
	 */
	protected String retValue(boolean isOk, String msg, Log log, Module module){
		if(!isOk){
			if(log!=null)
				log.error(msg);
			else{
				this.log.error(msg);
			}
				
		}
		
		if(module!=null){
			return "{success:"+isOk+",id:"+module.getId()+",leafValue:"+module.getLeafValue()+",name:'"+module.getName()+"',code:'"+module.getCode()+"',msg:'"+msg+"'}";
		}else{
			return "{success:"+isOk+",msg:'"+msg+"'}";
		}
		
//		return "{success:"+isOk+",msg:'"+msg+"'}";
	}
	
	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @version 1.0 
	 */
	protected  void outJson(String xmlStr) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private IUser getSessionUser() {
		IUser user =  (IUser)getCurrentUser();
		if(user == null || user.getId()==null )
		{
			user = new CacheUser();
		}
		return user;
	}
	public String saveLinkFavorite() {
		try {
			linkFavoriteBiz.saveLinkFavorite(linkFavorite);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findLinkFavorite() {
		linkFavorite = linkFavoriteBiz.findLinkFavorite(linkFavorite.getId());
		return SUCCESS;
	}

	public String findByLinkFavorite() {
		linkFavorites = linkFavoriteBiz.findBy(linkFavorite);
		return SUCCESS;
	}
	
	public String saveLinkFavorites() {
		if(paramTypeId==null){
			paramTypeId="";
		}
		if(paramValue==null){
			paramValue="";
		}
		String[] tempIcon=synicon.split(",");
		String[] tempModuleIds = syncmoduleId.split(",");
		String[] tempModuleNames = syncmoduleName.split(",");
		String[] tempActionUrls = syncactionUrl.split(",");
		String[] tempOrderCounts = syncorderCount.split(",");
		String[] paramTypeIds = paramTypeId.split(",");
		String[] paramValues = paramValue.split(",");
		
		try {
			//根据用户Id删除收藏夹
			linkFavoriteBiz.deleteLinkFavorites(getSessionUser().getId());
			for(int i=0;i<tempModuleNames.length;i++){
				LinkFavorite favorite=new LinkFavorite();
				if(!"null".equals(tempIcon[i])&& tempIcon[i] !=null){
					favorite.setIcon(tempIcon[i]);
				}
				favorite.setModuleId(new Long(tempModuleIds[i]));
				favorite.setModuleName(tempModuleNames[i]);
				favorite.setOrderCount(new Long(tempOrderCounts[i]));
				favorite.setActionUrl(tempActionUrls[i]);
				favorite.setParamTypeId(paramTypeIds[i]);
				favorite.setParamValue(paramValues[i]);
				favorite.setUserId(getCurrentUser().getId());
				favorite.setModifyDate(new Date());
				//保存收藏夹
				linkFavoriteBiz.saveLinkFavorite(favorite);
			}
			msg = "ok";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String findFavoriteByUserIdJson(){
		String jsonInfo = "";
		
		this.findFavoriteByUserId();
		Long id = this.getId();
		if(id==null){
			id=0l;
		}
		jsonInfo=this.getJsonById(this.favoList, id, true).toString().replaceAll("\'", "\"");
		
//		System.out.println("-------jsonInfo="+jsonInfo);
		outJson(jsonInfo);
		
//		return "json";
		return null;
	}
	public static final int QTIP_TITLE_LENGTH=20;
	/**
	 * 通过递归算法生成树的JSON数据
	 * <pre>
	 * [{id : '1',
	 *   text : '目录一',
	 *   leaf : false,
	 *   children : 
	 *   [{id : '6',
	 *     text : '标题一',
	 *     leaf : true,
	 *     singleClickExpand:true}],
	 *   singleClickExpand:true},
	 *   {id : '2',
	 *   text : '目录二',
	 *   leaf : false,
	 *   children : [],
	 *   singleClickExpand:true}]
	 * </pre>
	 * @param list 功能表
	 * @param parentId 根节点id
	 * @param show 是否不显示href为null的子节点, true显示，false不显示
	 * @return
	 */
	public static StringBuffer getJsonById(Collection<LinkFavoriteDto> list, long parentId, boolean show) {
//		System.out.println("------"+list.size()+" parentId="+parentId);
//		Module func=null;
		
		StringBuffer json = new StringBuffer("[");
		StringBuffer temp=null;
		List<LinkFavoriteDto> tempList = new ArrayList<LinkFavoriteDto>();

		//汇总子节点
		for(LinkFavoriteDto func:list){
			if(parentId==0){
				func.setExpanded(true);
			}
//			System.out.println("-----func.parentId="+func.getParentId());
			if(func.getParentId()!=null && func.getParentId().longValue()==parentId){	
//				System.out.println(" modulesId="+func.getId()+" code="+func.getModuleName());
				tempList.add(func);				
			}									
		}
		LinkFavoriteDto func = null;
		String showTypeName=null; 
		//将子节点生成json菜单格式
		for(int i=0; i<tempList.size(); i++){
			func = (LinkFavoriteDto)tempList.get(i);
			showTypeName="";
//			if(func.getShowType()!=null && func.getShowType().intValue()==1){
//				showTypeName="显示";
//			}
			temp = new StringBuffer();
			temp.append("{");
			temp.append("'id':").append("'"+func.getId()).append("',")
				.append("'text':'").append(CommMethod.getNotNullTrim(func.getModuleName())).append("',")
				.append("'attributes':{'actionUrl':'").append(CommMethod.getNotNullTrim(func.getActionUrl())).append("'")
				.append(",'moduleId':'"+func.getModuleId()+"'")
				.append(",'moduleName':'"+func.getQtip()+"'")
				.append(",'icon':'"+func.getIcon()+"'")
				.append(",'paramTypeId':'"+func.getParamTypeId()+"'")
				.append(",'paramValue':'"+func.getParamValue()+"'")
				.append("},");
//				.append("number:").append(i).append(",");	
//			if(func.getModuleName()!=null){
//				temp.append("qtip:'").append(CommMethod.getShortTitle(func.getModuleName(), QTIP_TITLE_LENGTH)).append("',");
//			}
//			if(func.getIcon()!=null){
//				temp.append("icon:'"+func.getIcon()+"', ");			
//			}
//			if (func.isExpanded()) {
//				temp.append("'expand':'true'");
//			} 
//			else{
//				temp.append("'expand':'false'");
//			}
			
			if (func.isLeaf()) {
				temp.append("'state':'open'");							
			} 
			else{
				temp.append("'state':'closed'");
//				temp.append("leaf:false, ").append("children:").append(
//						getJsonById(list,  func.getId(), show)).append("");
			}
//			temp.append("singleClickExpand:true}");
			temp.append("}");
			if (i < tempList.size() - 1) {
				temp.append(",\n");
			}
//			System.out.println("temp="+temp);
			json.append(temp);
		}
			
	
		json.append("]");
		return json;
	}
	
	private List findFavoriteById(Long id){
		List list = new ArrayList();
		LinkRecently linkRoot = new LinkRecently();
		linkRoot.setId(1l);
		linkRoot.setModuleName(this.getText("fav.recently"));
		linkRoot.setLeaf(false);
		linkRoot.setExpanded(true);
		
		LinkFavorite linkRoot2 = new LinkFavorite();
		linkRoot2.setId(2l);
		linkRoot2.setModuleName(this.getText("fav.favorite"));
		linkRoot2.setLeaf(false);
		linkRoot2.setExpanded(true);
		if(id==null){
			return list;
		}
		else if(id.longValue()==0){
			list.add(linkRoot);
			list.add(linkRoot2);
			listFavorite=list;
			this.favoList=new ArrayList<LinkFavoriteDto>();
			for(Object obj:list){
				if(obj instanceof LinkFavorite){
					this.favoList.add(new LinkFavoriteDto((LinkFavorite)obj));
				}
				else{
					this.favoList.add(new LinkFavoriteDto((LinkRecently)obj));
				}
			}
		}else if(id.longValue()==linkRoot.getId().longValue()){
			int count=10;
			listRecently = linkRecentlyBiz.findRecently(getCurrentUser().getId(),count);
			for(LinkRecently temp:listRecently){
				if(temp.getModifyDate()==null){
					temp.setModifyDate(new Date());
//					this.linkRecentlyBiz.saveLinkRecently(temp);
				}
				temp.setQtip(temp.getModuleName());
				//防止ID重复
				temp.setId(temp.getId()+1000000l);
				//temp.getUpdateTm().toString().substring(11,16) 截取时间  例如：2011-05-04 18:46 截取18:46
				temp.setModuleName(temp.getModifyDate().toString().substring(11,16)+" "+temp.getModuleName());
				temp.setParentId(linkRoot.getId());
				temp.setLeaf(true);
				temp.setExpanded(false);
				list.add(temp);
			}
		}
		else if(id.longValue()==linkRoot2.getId().longValue()){
			listFavorite = linkFavoriteBiz.findFavorite(getCurrentUser().getId());
			for(LinkFavorite temp:listFavorite){	
				temp.setQtip(temp.getModuleName());
				//防止ID重复
				temp.setId(temp.getId()+10);
				temp.setParentId(linkRoot2.getId());
				temp.setLeaf(true);
				temp.setExpanded(false);
				list.add(temp);
			}
		}
		return list;
	}
	
	//将最近访问与收藏夹数据构造成树形数据
	public String findFavoriteByUserId() {
		log.info("--------findFavoriteByUserId--"+this.getId());
		if(this.getId()==null){
			this.setId(0l);
		}
		String id = ServletActionContext.getRequest().getParameter("id");
		if(!CommMethod.isEmpty(id)){
			this.setId(Long.parseLong(id));
		}
		
		
		List list = this.findFavoriteById(this.getId());
		listFavorite=list;
//		System.out.println(list.size());
		this.favoList=new ArrayList<LinkFavoriteDto>();
		for(Object obj:list){
			if(obj instanceof LinkFavorite){
				this.favoList.add(new LinkFavoriteDto((LinkFavorite)obj));
			}
			else{
				this.favoList.add(new LinkFavoriteDto((LinkRecently)obj));
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 根据用户Id查询收藏夹
	 * return
	 */
	public String findLinkFavoriteByUserId() {
		listFavorite = linkFavoriteBiz.findFavorite(getCurrentUser().getId());
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(LinkFavorite dist:listFavorite){				
				domainList.add(dist);
			}
	
	//		如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
	
			
			int i=1;
			
			for(LinkFavorite dist:listFavorite){
				for(User user:userList){
					if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
						dist.setCreate_disp(user.getUsername());
					}
					if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
						dist.setModify_disp(user.getUsername());
					}
				}
				//设置编号
				dist.setNum(Long.valueOf(i));
				//截取时间  例如：2011-05-04 18:46 截取18:46
				dist.setTempTm(dist.getModifyDate().toString().substring(11, 16));
	//			lists.add(dist);
				i++;
			}
		}catch(Exception e){
			log.error("---dataError dispShow--createUser/modifyUser", e);
		}
		//end修改人，创建人转换
		
		return SUCCESS;
	}
	
	public String deleteLinkFavorites() {
		try {
			linkFavoriteBiz.deleteLinkFavorites(ids);
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

	public String findPageByLinkFavorite() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("userId", this.getCurrentUser().getId());
		IPage<LinkFavorite> page = linkFavoriteBiz.findPageBy(queryObj);
		linkFavorites = page.getData();
		
		//begin修改人，创建人转换
		try{
			Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
			for(LinkFavorite dist:linkFavorites){				
				domainList.add(dist);
			}
	
	//		如果数据库创建人修改人用的是id的话
			List<Long> idList = BaseDomain.getOperIds(domainList);
			log.info(idList.toString());
			idList = CommMethod.sortLong(idList);
			idList = CommMethod.distLong(idList);
			List<User> userList = this.userBiz.findUserByIds(idList, true);
			
			for(LinkFavorite dist:linkFavorites){
				for(User user:userList){
					if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
						dist.setCreate_disp(user.getUsername());
					}
					if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
						dist.setModify_disp(user.getUsername());
					}
				}
			}
		}catch(Exception e){
			log.error("---dataError dispShow--createUser/modifyUser", e);
		}
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setLinkFavoriteBiz(ILinkFavoriteBiz linkFavoriteBiz) {
		this.linkFavoriteBiz = linkFavoriteBiz;
	}

	public LinkFavorite getLinkFavorite() {
		return this.linkFavorite;
	}

	public void setLinkFavorite(LinkFavorite linkFavorite) {
		this.linkFavorite = linkFavorite;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<LinkFavorite> getLinkFavorites() {
		return this.linkFavorites;
	}
	
	

	public List<LinkRecently> getListRecently() {
		return listRecently;
	}
	public List<LinkFavorite> getListFavorite() {
		return listFavorite;
	}
	
	
	public List<LinkFavoriteDto> getFavoList() {
		return favoList;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
	public int getTotal() {
		return this.total;
	}
	
	public Collection<LinkFavorite> getRows() {
		return this.linkFavorites;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}

	public void setLinkRecentlyBiz(ILinkRecentlyBiz linkRecentlyBiz) {
		this.linkRecentlyBiz = linkRecentlyBiz;
	}
	
	public void setUserBiz(IUserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public void setSyncmoduleId(String syncmoduleId) {
		this.syncmoduleId = syncmoduleId;
	}

	public void setSyncmoduleName(String syncmoduleName) {
		this.syncmoduleName = syncmoduleName;
	}

	public void setSyncactionUrl(String syncactionUrl) {
		this.syncactionUrl = syncactionUrl;
	}

	public void setSyncorderCount(String syncorderCount) {
		this.syncorderCount = syncorderCount;
	}

	public void setSynicon(String synicon) {
		this.synicon = synicon;
	}
	public void setParamTypeId(String paramTypeId) {
		this.paramTypeId = paramTypeId;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	
	
}
