package org.esblink.module.basedata.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.BaseDomain;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.common.util.AutoTool;
import org.esblink.module.auth.biz.IUserBiz;
import org.esblink.module.auth.domain.User;
import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.CodeTable;
import org.esblink.module.basedata.domain.LogDb;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.esblink.dev.util.CommMethod;

/**
 * 
 * </pre>
 */
@SuppressWarnings("serial")
@Controller("codeTableAction")
@Scope("prototype")
public class CodeTableAction extends BaseGridAction<CodeTable> {
	private String moduleCode="parameter";
	private String modelCode = getClass().getSimpleName();
	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private LogDb logDb=new LogDb(moduleCode, modelCode);;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}

	@Resource(name="userBiz")
	private IUserBiz userBiz;
	@Resource(name="codeTableBiz")
	private ICodeTableBiz codeTableBiz;
	private CodeTable codeTable;
	private String ids;
	private String msg;
	private Collection<CodeTable> codeTables;
	private int total;
	private String type;
//	private String masterIds;
	private static Map mapCache = new HashMap();
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}
	

//	public String getMasterIds() {
//		return masterIds;
//	}
//
//
//	public void setMasterIds(String masterIds) {
//		this.masterIds = masterIds;
//	}
	
	public String codeTableTree() {
//		CodeTable codeTable = new CodeTable();
//		codeTable.setTreeId("0");
//		codeTable.setChildName("基础代tt码维护");
//		codeTable.setLeaf(true);
//		Collection<CodeTable> ctList =new ArrayList<CodeTable>();
//		ctList.add(codeTable);
		codeTables= codeTableBiz.codeTableTree();
		int i=100;
		for (CodeTable ct : codeTables) {
			ct.setLeaf(true);
			ct.setTreeId(i);
			ct.setId(0l+i);
//			ctList.add(ct);
			i++;
		}
//		codeTables=ctList;
		return SUCCESS;
	}

	private static AutoTool codeTypeNameAt = new AutoTool();
	/**
	 * codeTable
	 * @return
	 */
	public String codeTableTreeSearch(){
		String codeTypeName = ServletActionContext.getRequest().getParameter("codeTypeName");
		String keys = this.getCurrentUser().getId()+"__"+codeTypeName;
		
		codeTypeNameAt.addMapCount(keys);
				
		
		codeTables= codeTableBiz.codeTableTree();
		List<CodeTable> ctList = new ArrayList<CodeTable>();
		int i=100;
		for (CodeTable ct : codeTables) {
			ct.setLeaf(true);
			ct.setTreeId(i);
			ct.setId(0l+i);
//			ctList.add(ct);
			i++;
			if(ct.getCodeType().indexOf(codeTypeName)>=0||
					ct.getCname().indexOf(codeTypeName)>=0){
				ctList.add(ct);
			}
		}
		
	
		
		
		int count = this.codeTypeNameAt.getMapCount(keys);
		log.info("-------codeTypeName="+codeTypeName+" ctList size="+ctList.size()+" count="+count);
		CodeTable dist=null;				
		if(ctList.size()>=count){
			int tmp_count=0;
			for(CodeTable tmp:ctList){
				dist = tmp;
				
				tmp_count++;	
				if(count==tmp_count){
					break;
				}
			}
			String ids = ""+dist.getTreeId();
			
			ids = "/0/"+ids;//+"/"+dist.getId();
			log.info("=======codeTypeNameSearch=="+ids);
			msg=ids;
			if(ctList.size()==count){
				this.codeTypeNameAt.setMapCount(keys, 0);
			}
//			System.out.println("=================="+keys+"---------"+count+" "+dist.getId());
		}
//		this.codeTypeNameAt.setMapCount(keys, 0);
		
		return SUCCESS;
	}



	public String saveCodeTable() {
		try {
			if(codeTable.getCreateUser()==null){
				codeTable.setCreateUser(super.getCurrentUser().getId());
			}
			codeTable.setModifyUser(super.getCurrentUser().getId());
			codeTableBiz.saveCodeTable(codeTable);
			mapCache.clear();
			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveCodeTable", "data_id="+codeTable.getDataId()+" codeType="+codeTable.getCodeType(),"", SUCCESS, codeTable);
//			msg = "ok";
			this.mapCache=new HashMap();
		} catch (Exception e) {			
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveCodeTable", "data_id="+codeTable.getDataId()+" codeType="+codeTable.getCodeType(),"error:"+e.getMessage(), FAILURE, codeTable);			
		}
		return SUCCESS;
	}
	
//	/**
//	 * 重置codeTable,comm.dict.js
//	 */
//	public String resetCodeTableJs(){
//		String infos = this.codeTableBiz.resetCodeTableJs(true, null);
//		if(infos ==null){
//			this.msg="ok";
//		}
//		else{
//			this.msg=infos;
//		}
//		return SUCCESS;
//	}
	
	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可对数据进行压缩
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @author chenjh
	 * @version 1.0 
	 */
	protected  void outJson(String xmlStr) {
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		try {
			PrintWriter out = null;			
			out = response.getWriter();			
			out.write(xmlStr);
			out.flush();
			out.close();
		} catch (IOException e) {
//			e.printStackTrace();
		}
	}
	public String find(){
//		codeTable = codeTableBiz.find(CodeTable.class,codeTable.getId());
		codeTable=this.codeTableBiz.findCodeTable(codeTable.getId());
		return SUCCESS;
	}
	/**
	 * 系统参数值
	 * @returnl
	 */
	public String findCodeTableByDataId(){		
		String codeType = ServletActionContext.getRequest().getParameter("codeType");
		String dataId = ServletActionContext.getRequest().getParameter("dataId");
	
		String value = null;
		CodeTable search = new CodeTable();
		search.setIfDel(0l);
		search.setStatus(1l);
		search.setCodeType(codeType);
		search.setDataId(CommMethod.getLongValue(dataId));
		Collection<CodeTable> ctList = this.codeTableBiz.findBy(search);
		
		CodeTable cTable=null;
		for(CodeTable ct:ctList){
			cTable=ct;
			break;
		}
		if(cTable!=null){
			value="{codeType:'"+codeTable+"', dataId:"+dataId +
					", display:"+cTable.getDisplay() +
					", showValues:'"+cTable.getShowValues()+"'" +
					", matchValues:'"+cTable.getMatchValues()+"'" +
					", referValues:'"+cTable.getReferValues()+"'" +
					", remark:'"+cTable.getRemark()+"'}";
		}
		else{
			value="{}";
		}
		outJson(value); 
		return null;
	}

//	public String findCodeTable() {
//		QueryObj queryObj = QueryObj.parseQueryObj(this);
//		String masterId=(String)queryObj.getQueryObject("masterId");
//		String condition=(String)queryObj.getQueryObject("condition");
//		if(!CommMethod.isEmpty(masterId)){
//			codeTables=codeTableBiz.codeTableByPromotion(masterId);
//			if(!CommMethod.isEmpty(condition)){
//				List<CodeTable> codeTableList=codeTableBiz.codeTableByType("PROMOTION");
//				for (CodeTable codeTable : codeTableList) {
//					if(codeTable.getDataId().longValue()==Long.parseLong(condition)){
//						codeTables.add(codeTable);
//					}
//				}
//			}
//			return SUCCESS;
//		}
////		CommMethod.printListln(codeTables);
//		
//		return SUCCESS;
//	}
//	public String findCodeTableByStatus() {
//		codeTables=codeTableBiz.codeTableByPromotion(masterIds);
//		if(codeTables.size()==0){
//			msg="big";
//		}
//		return SUCCESS;
//	}

	/**
	 * 根据type类型查找
	 * @param type
	 * @return
	 */
	public String codeTableByType() {
		Collection<CodeTable> ctList = null;
		String view = ServletActionContext.getRequest().getParameter("view");
		log.info("============"+type+" view="+view+this.getLocale().getLanguage());
		if(type!=null&&!type.equals("null")){
			if(mapCache.get(type)==null){
				ctList = codeTableBiz.codeTableByType(type);
				mapCache.put(type, ctList);
			}
			else{
				log.info(type+" read from cache");
				ctList = (Collection<CodeTable>)mapCache.get(type);
			}
			codeTables = new ArrayList<CodeTable>();
			if(view!=null&&view.trim().equals("all")){
				CodeTable all = new CodeTable();
				if(this.getLocale()!=null && "en".equals(this.getLocale().getLanguage())){
					all.setCname("All");		
					all.setShowValues("All");
					all.setMatchValues("All");
				}
				else{
					all.setCname("全部");		
					all.setShowValues("全部");
					all.setMatchValues("全部");
				}
				codeTables.add(all);
			}
			if(this.getLocale()!=null && "en".equals(this.getLocale().getLanguage())){
				for(CodeTable ct:ctList){
					ct.setShowValues(ct.getShowValuesEn());
				}
			}
			codeTables.addAll(ctList);
//			log.info("------codeTableByType--");
//			CommMethod.printList(codeTables);
		}
				
		
//		type = "[";
//		// {boxLabel: 'Item 1', name: 'cb-col-1'},
//		for(CodeTable ct:codeTables){
//			type = type+"{boxLabel:'"+ct.getShowValues()+"', name:'"+ct.getCname()+"'},";
//		}
//		
//		type = type.substring(0, type.length()-1);
//		type = type+"]";
		
		return SUCCESS;
	}
	
	
	public String deleteCodeTables() {
		try {
			codeTableBiz.deleteCodeTables(ids);
			mapCache.clear();
			this.logDbBiz.info(this.getCurrentUser(), logDb, "deleteCodeTables", "ids="+ids,"delete ids for:"+ids,SUCCESS,null);
			msg = "ok";
		} catch (Exception e) {
			msg = e.toString();
			log.error("error", e);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "deleteCodeTables", "ids="+ids, FAILURE+":"+e.getMessage(), FAILURE, null);			
		}
		return SUCCESS;
	}

	public String findByCodeTable() {
		codeTables = codeTableBiz.findBy(codeTable);
		return SUCCESS;
	}


	public String findPageByCodeTable() {
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setQueryObject("ifDel", 0l);
		queryObj.setSortField("codeType");
		IPage<CodeTable> page = codeTableBiz.findPageBy(queryObj);
		codeTables = page.getData();
		
		//begin修改人，创建人转换
				try{
					Collection<BaseDomain> domainList = new ArrayList<BaseDomain>();			
					for(CodeTable dist:codeTables){				
						domainList.add(dist);
					}
			
			//		如果数据库创建人修改人用的是id的话
					List<Long> idList = BaseDomain.getOperIds(domainList);
					log.info(idList.toString());
					idList = CommMethod.sortLong(idList);
					idList = CommMethod.distLong(idList);
					List<User> userList = this.userBiz.findUserByIds(idList, true);
			
					
					int i=1;
					
					for(CodeTable dist:codeTables){
						for(User user:userList){
							if(null!=dist.getCreateUser() && dist.getCreateUser().longValue()==user.getId().longValue()){
								dist.setCreate_disp(user.getUsername());
							}
							if(null!=dist.getModifyUser() && dist.getModifyUser().longValue()==user.getId().longValue()){
								dist.setModify_disp(user.getUsername());
							}
						}
						i++;
					}
				}catch(Exception e){
					log.error("---dataError dispShow--modifyUser", e);
				}
				//end修改人，创建人转换
		
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}

	public void setCodeTableBiz(ICodeTableBiz codeTableBiz) {
		this.codeTableBiz = codeTableBiz;
	}
	
	

//	public void setUserBiz(IUserBiz userBiz) {
//		this.userBiz = userBiz;
//	}

	public CodeTable getCodeTable() {
		return this.codeTable;
	}

	public void setCodeTable(CodeTable codeTable) {
		this.codeTable = codeTable;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getMsg() {
		return this.msg;
	}

	public Collection<CodeTable> getCodeTables() {
		return this.codeTables;
	}
	
	public Collection<CodeTable> getRows() {
		return this.codeTables;
	}

	public int getTotal() {
		return this.total;
	}
	
	public void setRows(int rows){
		this.total=rows;
	}
}
