package org.esblink.module.auth.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.struts2.ServletActionContext;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.action.BaseGridAction;
import org.esblink.common.base.domain.LogInfo;
import org.esblink.common.base.domain.ModuleType;
import org.esblink.common.util.AutoTool;
import org.esblink.common.util.EscapeUnescape;
import org.esblink.module.auth.biz.IModuleBiz;
import org.esblink.module.auth.domain.Module;
import org.esblink.module.auth.util.ConfigUtil;
import org.esblink.module.basedata.biz.ILogDbBiz;
import org.esblink.module.basedata.domain.LogDb;

import com.esblink.dev.util.CommMethod;

/**
 * 
 * Description: Module Action，模块菜单管理
 * 
 */
@SuppressWarnings("serial")
//@Controller("moduleManagerAction")
//@Scope("prototype")
public class ModuleManagerAction extends BaseGridAction<Module> {
	private String moduleCode="moduleManager";
	private LogInfo logInfo = LogInfo.getLogger(ModuleManagerAction.class);
	private String modelCode = getClass().getSimpleName();
	private LogDb logDb=new LogDb(moduleCode, modelCode);;
	public void setLogDbBiz(ILogDbBiz logDbBiz) {
		this.logDbBiz = logDbBiz;
	}

//	@Resource(name="moduleBiz")
	private IModuleBiz moduleBiz;
//	@Resource(name="logDbBiz")
	private ILogDbBiz logDbBiz;
	private Module module=new Module();
	private String ids;
	private String status;
	private Collection<Module> modules;
	private int total;
	private boolean saveType=false;
	private boolean isOk=false;
	private String infos=null;
	private int limit=20;
	private int start;
//	private PaginatedList paginateTask;
	private String jsonData;
	private String codeTypeName;
	private Collection rows;

	
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
	
//	/**
//	 * 保存或修改
//	 * @return
//	 */
//	public String saveModules(){
//		log.info("==========saveFuncs=========");
//		String jsonData=null;
//		String jsonString=null;
//		try {
//			
////			logger.debug("开始调用 saveSabreInfo()");
//			log.debug("页面返回的所有修改数据： "+ jsonData);
//			List<Module> modified = null;
//			modified = getListFromJson(jsonData, Module.class);
//			List<Module> funcList = this.paginateTask.getResults();
//			
//			List<Module> mList = new ArrayList<Module>();
//			for(Module func:funcList){
//				for(Module modi: modified){
//					if(func.getId().intValue()==modi.getId().intValue()){
//						func.setName(modi.getName());
//						func.setSort(modi.getSort());
////						func.setLevel(modi.getLevel());
//						mList.add(func);
//					}
//				}
//			}
//			for(Object obj:mList){
//				this.moduleBiz.saveModule((Module)obj);
//			}
//			
//			log.debug("修改的行数 = " + mList.size()+ " 修改的第一行的内容是= "+ modified.get(0));
//			isOk=true;
//			infos="保存"+mList.size()+"行成功";
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			isOk=false;
//			infos=e.getLocalizedMessage();			
//			e.printStackTrace();
//		}
//		
////		System.out.println(super.getLoginId()+" func="+func);
//		
//		if(module!=null){
//			try{
////				LogDbs.info(super.getLoginId(), "saveFuncs:"+func.getName(), "saveFuncs:"+this.saveType, "saveFuncs="+this.func_ids+" name="+func.getName()+","+infos, func);
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		String returnValue =this.retValue(isOk, infos, null);
//		outJson(returnValue);
//		jsonString=returnValue;
//		
////		outJson("{success:true,msg:'保存成功'}");
//		return "json";
//	}
	
	

	/**
	 * 构建自定义String，通过HttpServletResponse发送给浏览器
	 * 可由转向后的页面进行输出
	 * @param json  
	 * @author jviewes
	 * Email: jviewes@gmail.com
	 * Website: www.esblink.net
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
	private static void setDataFormat2JAVA(){   
        //设定日期转换格式   
        JSONUtils.getMorpherRegistry().registerMorpher(
        		new DateMorpher(new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"})
        );   
    }
	/**  
	 * 从json数组中得到javaBean List
	 * json形如：["123", "456"]  
	 * @param jsonString  
	 * @return  
	 */  
	@SuppressWarnings("unchecked")
	protected static List getListFromJson(String jsonString, Class beanClass) {   
		setDataFormat2JAVA();
		if(jsonString == null || "".equals(jsonString)) return null;
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		List result = new ArrayList();
		for(int i=0; i<jsonArray.size();i++){
			result.add(JSONObject.toBean(jsonArray.getJSONObject(i), beanClass));
		}
		return result;
	}
	
	
	/**
	 * 取id最大的值
	 * @param tList
	 * @return
	 */
	private Module getMaxId(Collection<Module> tList){
		long max=0;
		Module task=null;
		Iterator<Module> iter = tList.iterator();
		Module t = null;
		while(iter.hasNext()){
			t = (Module)iter.next();
			if(t.getId()>max){
				max = t.getId();
				task=t;
			}
		}
		return task;
	}
	
	private Integer getMaxOrderCount(Collection<Module> list){
		int max=0;
		Module task=null;
		Iterator<Module> iter = list.iterator();
		Module t = null;
		while(iter.hasNext()){
			t = (Module)iter.next();
			if(t.getSort()>max){
				max = t.getSort();
				task=t;
			}
		}
		return max;
	}
	
	private void prepareRequestList(){
		HashMap typeFlagListMap = new LinkedHashMap();
		typeFlagListMap.put(4,this.getText("typeFlag.Menu"));
		typeFlagListMap.put(5,this.getText("typeFlag.Bundle"));
		typeFlagListMap.put(6,this.getText("typeFlag.Panel"));
		typeFlagListMap.put(7,this.getText("typeFlag.Widget"));
		typeFlagListMap.put(8,this.getText("typeFlag.IOS"));
		ServletActionContext.getRequest().setAttribute("typeFlagList",typeFlagListMap);
		
		HashMap showTypeMap = new LinkedHashMap();
		showTypeMap.put(1,this.getText("msg.yes"));
		showTypeMap.put(0,this.getText("msg.no"));
		ServletActionContext.getRequest().setAttribute("showTypeList",typeFlagListMap);
		
		HashMap leafTypeMap = new LinkedHashMap();
		leafTypeMap.put(1,this.getText("msg.yes"));
		leafTypeMap.put(0,this.getText("msg.no"));
		ServletActionContext.getRequest().setAttribute("leafTypeList",leafTypeMap);
		
		HashMap ifThirdMap = new LinkedHashMap();
		ifThirdMap.put(0,this.getText("msg.no"));
		ifThirdMap.put(1,this.getText("msg.yes"));
		ServletActionContext.getRequest().setAttribute("ifThirdList",ifThirdMap);
		
		HashMap buttonsMap = new LinkedHashMap();
		buttonsMap.put(1,this.getText("button.search"));
		buttonsMap.put(2,this.getText("button.add"));
		buttonsMap.put(3,this.getText("button.modify"));
		buttonsMap.put(4,this.getText("button.del"));
		ServletActionContext.getRequest().setAttribute("buttonsList",buttonsMap);
	}
	
	public String prepare4AddModule(){
		String parentIds = ServletActionContext.getRequest().getParameter("parentId");
		//增加子目录,叶子节点	
		String ifLeafs = ServletActionContext.getRequest().getParameter("leafValue");
		Module parent=this.moduleBiz.findModule(CommMethod.getLongValue(parentIds));
		log.info("-----prepare4AddModule ids="+parentIds+" ifLeafs="+ifLeafs);
		if(parent!=null){
			Collection<Module> list=this.moduleBiz.findModulesByParentId(parent.getId());
			this.module=new Module();
			this.module.setParentId(parent.getId());
			this.module.setParentName(parent.getName());
			this.module.setSort(this.getMaxOrderCount(list)+1);
			this.module.setStatus(1l);
			this.module.setTypeFlag(parent.getTypeFlag());
			this.module.setApplyTypeFlag(parent.getApplyTypeFlag());
			this.module.setType(ModuleType.MENU);
			
			if(ifLeafs!=null&&ifLeafs.equals("1")){//增加子节点		
				this.module.setLeafValue(1);
			}			
			else{//增加子目录				
				this.module.setLeafValue(0);
			}	
			
		}
		else{
			this.status="Invalid systemInfo id";
		}
		prepareRequestList();
		return SUCCESS;
	}
	
	public String prepare4EditModule(){
		
		//编辑时
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String title=ServletActionContext.getRequest().getParameter("title");
		String funccode=ServletActionContext.getRequest().getParameter("funccode");
		String parentIds = ServletActionContext.getRequest().getParameter("parentId");
		String parentText=ServletActionContext.getRequest().getParameter("parentText");
		String parentCode=ServletActionContext.getRequest().getParameter("parentCode");
		
		String numbers = ServletActionContext.getRequest().getParameter("number");
		//增加子目录,叶子节点	
		String isLeaf = ServletActionContext.getRequest().getParameter("leafValue");		
		
		
		try {
			log.info("----prepare4EditModule-parentText1="+parentText+" title="+title);
			if(parentText!=null){
//				parentText = CommMethod.paraEncode(parentText);
//				parentText = new String(parentText.getBytes("GBK"), "UTF8");
				parentText = new String(Base64.decodeBase64(parentText.getBytes()));
				parentText = EscapeUnescape.unescape(parentText);
			}

			if(title!=null){
//				title = CommMethod.paraEncode(title);
//				title = new String(title.getBytes("GBK"), "UTF8");
				title = new String(Base64.decodeBase64(title.getBytes()));
				title = EscapeUnescape.unescape(title);
			}
			log.info("----parentText2="+parentText+" title="+title);
			
		} catch (Exception e) {
			log.error(parentText+" \n"+e.getLocalizedMessage(), e);
			// TODO Auto-generated catch block
		}
		
		Integer number=0;
		Module search = null;
		Module parent = null;
		Module current = null;
		long funcId=0;
		long parentId=0;
		try{
			//node
			if(ids!=null && (ids.equals("0")||ids.startsWith("xnode-"))){//ids为xnode-[number]时，以标题取值处理
				search = new Module();
				if(CommMethod.isEmpty(funccode)){
					search.setName(title);
				}
				search.setCode(funccode);
				search.setStatus(1l);
				parentId=CommMethod.getLongValue(parentIds);
				if(parentId>0){
					search.setParentId(parentId);
				}
				Collection<Module> pList = this.moduleBiz.findBy(search);
//				this.logInfo.info("search_node"+title, search);
				current = this.getMaxId(pList);
				funcId=current.getId();
				this.module=current;
			}
			else{
				funcId = Long.parseLong(ids);
				this.module=this.moduleBiz.findModule(funcId);
				
			}
			if(numbers!=null)
				number = Integer.parseInt(numbers);	
			else
				number=0;
			this.module.setParentName(parentText);
		}catch(Exception e){
			log.error("", e);
		}
		prepareRequestList();
		
		return "success";
	}
	
	/**
	 * 准备更新，用于编辑节点，增加子目录，增加子节点
	 * @return
	 */
	public String preUpdate(){
		log.info("==========preUpdate=========");		
		
		long funcId=0;
		long parentId=1;
		long number=0;
		this.module = new Module();
		Module parent=null;
		Module taskEdit=null;
		
		//---end
		
		//用于检查是否有根节点
		Module search = new Module();
		search.setType(ModuleType.ROOT);
//		search.setStatus(0l);
//		search.setDistrictId(module_moduleDistId);
		Collection<Module> rootlist = this.moduleBiz.findBy(search);
		
		
		
		//编辑时
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String title=ServletActionContext.getRequest().getParameter("title");
		String funccode=ServletActionContext.getRequest().getParameter("funccode");
		String parentIds = ServletActionContext.getRequest().getParameter("parentId");
		String parentText=ServletActionContext.getRequest().getParameter("parentText");
		String parentCode=ServletActionContext.getRequest().getParameter("parentCode");
		String isNew=ServletActionContext.getRequest().getParameter("isNew");
			
		String numbers = ServletActionContext.getRequest().getParameter("number");
		//增加子目录,叶子节点	
		String leafValue = ServletActionContext.getRequest().getParameter("leafValue");		
		if(isNew!=null&&isNew.equals("true")){
			this.saveType=true;
		}
		else{
			this.saveType=false;
		}
		
		try {
			log.info("----parentText1="+parentText+" title="+title);
			if(parentText!=null){
//				parentText = CommMethod.paraEncode(parentText);
//				parentText = new String(parentText.getBytes("GBK"), "UTF8");
				parentText = new String(Base64.decodeBase64(parentText.getBytes()));
				parentText = EscapeUnescape.unescape(parentText);
			}

			if(title!=null){
//				title = CommMethod.paraEncode(title);
//				title = new String(title.getBytes("GBK"), "UTF8");
				title = new String(Base64.decodeBase64(title.getBytes()));
				title = EscapeUnescape.unescape(title);
			}
			log.info("----parentText2="+parentText+" title="+title);
			
		} catch (Exception e) {
			log.error(parentText+" \n"+e.getLocalizedMessage(), e);
			// TODO Auto-generated catch block
		}
		
		if(this.saveType){
			try{
				//parent 解决无刷新连续加子节点形成的id不存在问题，按名称进行查找，取id最大值
				if(parentIds!=null&&parentIds.startsWith("xnode-")){
					search = new Module();
//					search.setName(parentText);
					if(CommMethod.isEmpty(parentCode)){
						search.setName(parentText);
					}
					search.setCode(parentCode);
					search.setStatus(1l);
//					this.logInfo.info("search_parent"+parentText, search);
					Collection<Module> pList = this.moduleBiz.findBy(search);
					parent = this.getMaxId(pList);				
					parentId=parent.getId();				
				}else if(parentIds!=null){
					parentId = Long.parseLong(parentIds);
					parent = this.moduleBiz.findModule(parentId);
				}
				else{
					parent = new Module();
					parent.setId(parentId);
				}
				this.module.setParent(parent);
//				this.module.setParentId(parentId);
				
			}catch(Exception e){
				log.error("", e);
			}
			
		}
		else{
			try{
				//node
				if(ids!=null && (ids.equals("0")||ids.startsWith("xnode-"))){//ids为xnode-[number]时，以标题取值处理
					search = new Module();
					if(CommMethod.isEmpty(funccode)){
						search.setName(title);
					}
					search.setCode(funccode);
					search.setStatus(1l);
					if(parentId>0){
						if(parent!=null){
							search.setParent(parent);
						}
						else{
							Module pp = new Module();
							pp.setId(parentId);
							search.setParent(pp);
						}
//						search.setParentId(parentId);
					}
//					search.setDistrictId(module_moduleDistId);
					Collection<Module> pList = this.moduleBiz.findBy(search);
//					this.logInfo.info("search_node"+title, search);
					taskEdit = this.getMaxId(pList);
					funcId=taskEdit.getId();
				}
				else{
					funcId = Long.parseLong(ids);
				}
				if(numbers!=null)
					number = Integer.parseInt(numbers);	
				else
					number=0;
			}catch(Exception e){
				log.error("", e);
			}
		}
		
		
		
		
		
		
		//String paras = ServletActionContext.getRequest().getParameter("paras");
//		log.info("---preUpdate------funcId="+ids+" title="+title+" parentId="+parentIds+" parentText="+parentText+" number="+numbers+" leaf="+leafValue);
		

		//编辑
		if(!this.saveType){
			if(taskEdit!=null){
				this.module=taskEdit;
			}else{
				this.module = this.moduleBiz.findModule(funcId);
			}
		}
		else{			
			this.module.setParent(parent);								
			this.module.setTypeFlag(parent.getTypeFlag());
			this.module.setApplyTypeFlag(parent.getApplyTypeFlag());
			this.module.setType(ModuleType.MENU);
			
			if(leafValue!=null&&leafValue.equals("1")){//增加子节点		
				this.module.setLeafValue(1);
			}			
			else{//增加子目录				
				this.module.setLeafValue(0);
			}			
//			this.module.setStatus(0l);
			this.module.setSort(Long.valueOf(number).intValue());

		}
		
		Module taskP = null;
		if(parent!=null&&parentId>0){
			taskP=parent;
		}
		else{
			if(this.module.getParent()!=null){
				taskP= this.moduleBiz.findModule(this.module.getParent().getId());
			}
			else{
				taskP = this.moduleBiz.findModule(this.module.getParentId());
			}
		}
		//ids!=null为编辑模式，否则为增加模式
		if((CommMethod.isEmpty(ids) || ids.equals("0")) && taskP!=null){
			this.module.setParentName(taskP.getName());
			if(this.module.getLeafValue().intValue()==1){
				module.setCode("new"+CommMethod.upperStart(taskP.getCode()));
				module.setName("新"+taskP.getName());
				module.setAction(this.getActionButton(taskP.getAction(), "new"));
			}
		}
		else{
			//编辑时，父节点名称为空问题处理
			if(module.getParentName()==null && taskP!=null){
				this.module.setParentName(taskP.getName());
			}
//			this.module.setDistrictId(module_moduleDistId);
		}
		
		log.info("---preUpdate-----------------------saveType="+this.saveType+" ids="+ids+" title="+title+" code="+funccode+" parentId="+parentId+" parentText="+parentText+" parentCode="+parentCode+" parentId="+this.module.getParentId()+" parentName="+this.module.getParentName());
		//如果检查根节点不存在则加为根节点
		if(rootlist.size()!=0){
//			this.module.setDistrictId(module_moduleDistId);
			this.module.setType(ModuleType.ROOT);
			this.module.setParent(null);
			this.module.setParentId(null);
		}
//		log.info("---preUpdate------rootlist.size="+rootlist.size()+"--saveType="+this.saveType+" ids="+ids+" parentId="+parentId+" parentText="+parentText+" parentId="+this.module.getParentId()+" parentName="+this.module.getParentName());
//		outJson("sucaaaaaaaaaaaacess");
		return "success";
	}
	
	private String moduleNameAtKey=this.getClass().getSimpleName()+"_moduleNameAt";
	private String moduleListKey=this.getClass().getSimpleName()+"_moduleList";
	/**
	 * 返回找到用户所在的层级
	 * @return
	 */
	public String moduleTreeSearch(){
		String moduleName = ServletActionContext.getRequest().getParameter("moduleName");
		log.info("-------moduleTreeSearch--moduleName="+moduleName);
		String keys = this.getCurrentUser().getId()+"__"+moduleName;
		HttpSession session = ServletActionContext.getRequest().getSession();
		try{
		AutoTool moduleNameAt=(AutoTool)session.getAttribute(moduleNameAtKey);
		if(moduleNameAt==null){
			moduleNameAt = new AutoTool();
			session.setAttribute(moduleNameAtKey, moduleNameAt);
		}
		moduleNameAt.addMapCount(keys);
		
		List<Module> moduleList = this.moduleBiz.findModules(moduleName);
		
		int count = moduleNameAt.getMapCount(keys);
		Module dist=null;				
		if(moduleList.size()>=count){
			int tmp_count=0;
			for(Module tmp:moduleList){
				if(tmp.getApplyTypeFlag()==null){
					tmp.setApplyTypeFlag(0);
				}
				if(tmp.getTypeFlag()==null){
					tmp.setTypeFlag(0);
				}
				dist = tmp;
				tmp_count++;	
				if(count==tmp_count){
					break;
				}
			}
			String ids = this.getTreePath(dist);
			
			ids = ""+ids+"/"+dist.getId();
//			log.info("=======moduleTreeSearch=="+ids);
			this.jsonData=ids;
			if(moduleList.size()==count){
				moduleNameAt.setMapCount(keys, 0);
			}
//			System.out.println("=================="+keys+"---------"+count+" "+dist.getId());
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}

	/**
	 * 递归取得用户所在部门层级
	 * @param dist
	 * @return
	 */
	private String getTreePath(Module dist){
		HttpSession session = ServletActionContext.getRequest().getSession();
		List<Module> moduleList = (List<Module>)session.getAttribute(moduleListKey);
		if(moduleList==null){
			moduleList = this.moduleBiz.findModules("");
			session.setAttribute(moduleListKey, moduleList);
		}
		Module parent = null;		
		String ids = "";
		
		if(dist!=null&&dist.getParentId()!=null&&dist.getParentId()>0){
//			parent= this.moduleBiz.findModule(dist.getParentId());
			for(Module module:moduleList){
				if(module.getId().longValue()==dist.getParentId().longValue()){
					parent=module;
					break;
				}
			}
			return getTreePath(parent)+"/"+dist.getParentId();
		}
		else{
			return "";
		}
//		return parent;
	}
	
	/**
	 * module树查询
	 * @return
	 */
	public String queryModuleJson(){
		log.info("================queryModuleJson=======================");
		this.module = new Module(); 
		long moduleRootId=this.getId();		
		
		StringBuffer sb = moduleBiz.queryModuleJson(this.module, limit, start / limit , moduleRootId);
		outJson(sb.toString());
		
//		System.out.println("sb="+sb.toString());
		return "json";
	}
	
	public String queryModuleList(){
		log.info("================queryModuleJson=======================");
		this.module = new Module(); 
		if(this.getId()==null){
			this.modules=new ArrayList();
			return "success";
		}
		long moduleRootId=this.getId();		
		
		List list = moduleBiz.queryModuleList(this.module, limit, start / limit , moduleRootId);
		this.modules=list;
		
//		System.out.println("sb="+sb.toString());
		return "success";
	}
	
	/**
	 * ajax更新标题
	 * @return
	 */
	public String ajaxUpdateTitle(){
		
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String title = ServletActionContext.getRequest().getParameter("title");
		String title_old = ServletActionContext.getRequest().getParameter("oldTitle");

		
		Long id = 0l;
		Module search = null;
		try{
			title = CommMethod.paraEncode(title);
			title_old = CommMethod.paraEncode(title_old);
			log.info("==========ajaxUpdateTitle==============id="+ids+" title_old="+title_old+" new_title="+title);
			//node
			if(ids!=null&&ids.startsWith("xnode-")){//ids为xnode-[number]时，以标题取值处理
				search = new Module();
				search.setName(title_old);
//				search.setStatus(0l);			
				Collection<Module> pList = this.moduleBiz.findBy(search);
				Module taskEdit = this.getMaxId(pList);
				id=taskEdit.getId();
			}
			else{
				id = Long.parseLong(ids);
			}
		
		}
		catch(Exception e){
			e.printStackTrace();
		}
				
		try {
			moduleBiz.ajaxUpdateTitle(id, title);
			isOk=true;
			infos="修改标题成功";
			this.logDbBiz.info(this.getCurrentUser(), logDb, "ajaxUpdateTitle", "id="+id+" title="+title,infos, SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			isOk=false;
			infos=e.getLocalizedMessage();			
			e.printStackTrace();
			this.logDbBiz.error(this.getCurrentUser(), logDb, "ajaxUpdateTitle", "id="+id+" title="+title,infos, FAILURE, null);
		}
//		LogDbs.info(super.getLoginId(), "ajaxUpdateTitle:"+title, "ajaxUpdateTitle", "ajaxUpdateTitle:"+title+","+infos, null);
		String returnValue =this.retValue(isOk, infos, null, null);
		outJson(returnValue);
		
		
		
		return "json";
	}
	
	/**
	 * ajax删除节点
	 */
	public void ajaxRemoveNode(){		
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String title = ServletActionContext.getRequest().getParameter("title");
		
		
		long id = 0l;		
		try{
			title = CommMethod.paraEncode(title);
			log.info("==========ajaxRemoveNode==============id="+ids+" title="+title);
			//node
			if(ids!=null&&ids.startsWith("xnode-")){//ids为xnode-[number]时，以标题取值处理
				Module search = new Module();
				search.setName(title);
//				search.setStatus(0l);			
				Collection<Module> pList = this.moduleBiz.findBy(search);
				Module taskEdit = this.getMaxId(pList);
				id=taskEdit.getId();
			}
			else{
				id = Long.parseLong(ids);
			}
		}
		catch(Exception e){
			
		}
		try {
			moduleBiz.ajaxRemoveNode(id);
			isOk=true;
			infos="删除节点成功";		
			this.logDbBiz.info(this.getCurrentUser(), logDb, "ajaxRemoveNode", "id="+id,infos, SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			isOk=false;
			infos=e.getLocalizedMessage();			
			e.printStackTrace();
			this.logDbBiz.error(this.getCurrentUser(), logDb, "ajaxRemoveNode", "id="+id,infos, FAILURE, null);
		}
//		LogDbs.info(super.getLoginId(), "ajaxRemoveNode:"+id, "ajaxRemoveNode", "ajaxRemoveNode:"+id+","+infos, null);
		String returnValue =this.retValue(isOk, infos, null, null);
		outJson(returnValue);
		
	}
	
	/**
	 * 拖动修改节点位置
	 */
	public void ajaxMoveNode(){
		log.debug("==========ajaxMoveNode==============");
		String ids = ServletActionContext.getRequest().getParameter("ids");
		String title = ServletActionContext.getRequest().getParameter("title");
		String oldParentIds = ServletActionContext.getRequest().getParameter("oldParentIds");
		String oldParentTitle = ServletActionContext.getRequest().getParameter("oldParentTitle");
		String newParentIds = ServletActionContext.getRequest().getParameter("newParentIds");
		String newParentTitle = ServletActionContext.getRequest().getParameter("newParentTitle");
		String nodeIndexs = ServletActionContext.getRequest().getParameter("nodeIndexs");
		Long id=0l, oldParentId=0l, newParentId=0l, nodeIndex=0l;
		
		Module search =null;
		Module taskEdit =null;
		try{
			title = CommMethod.paraEncode(title);
			oldParentTitle = CommMethod.paraEncode(oldParentTitle);
			newParentTitle = CommMethod.paraEncode(newParentTitle);
//			id = Long.parseLong(ids);
			//node
			if(ids!=null&&ids.startsWith("xnode-")){//ids为xnode-[number]时，以标题取值处理
				search = new Module();
				search.setName(title);
//				search.setStatus(0l);			
				Collection<Module> pList = this.moduleBiz.findBy(search);
				taskEdit = this.getMaxId(pList);
				id=taskEdit.getId();
			}
			else{
				id = Long.parseLong(ids);
			}
			
			//oldParent
			if(oldParentIds!=null&&oldParentIds.startsWith("xnode-")){//ids为xnode-[number]时，以标题取值处理
				search = new Module();
				search.setName(oldParentTitle);
//				search.setStatus(0l);			
				Collection<Module> pList = this.moduleBiz.findBy(search);
				taskEdit = this.getMaxId(pList);
				oldParentId=taskEdit.getId();
			}
			else{
				oldParentId = Long.parseLong(oldParentIds);
			}
			
			//newParent
			if(newParentIds!=null&&newParentIds.startsWith("xnode-")){//ids为xnode-[number]时，以标题取值处理
				search = new Module();
				search.setName(newParentTitle);
//				search.setStatus(0l);			
				Collection<Module> pList = this.moduleBiz.findBy(search);
				taskEdit = this.getMaxId(pList);
				newParentId=taskEdit.getId();
			}
			else{
				newParentId = Long.parseLong(newParentIds);
			}
			
			nodeIndex = Long.parseLong(nodeIndexs);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		log.debug("------------id="+id+" oldParentId="+oldParentId+" newParentId="+newParentId+" nodeIndex="+nodeIndex);
		try {
			moduleBiz.ajaxMoveNode(id, oldParentId, newParentId, nodeIndex);
			isOk=true;
			infos="移动节点成功";
			this.logDbBiz.info(this.getCurrentUser(), logDb, "ajaxMoveNode", "id="+id+" oldParentId="+oldParentId+" newParentId="+newParentId,infos, SUCCESS, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			isOk=false;
			infos=e.getLocalizedMessage();			
			e.printStackTrace();
			this.logDbBiz.error(this.getCurrentUser(), logDb, "ajaxMoveNode", "id="+id+" oldParentId="+oldParentId+" newParentId="+newParentId,infos, FAILURE, null);
		}
//		LogDbs.info(super.getLoginId(), "ajaxMoveNode:"+id, "ajaxMoveNode", "ajaxMoveNode:"+id+","+infos, null);
		String returnValue =this.retValue(isOk, infos, null, null);
		outJson(returnValue);
//		send("{success:true,msg:'move success'}");
	}
	
//	/**
//	 * 按模块自动生成BO层相关操作的认证信息到数据库
//	 * 接口类所有方法生成类对应方法表
//	 * @return
//	 */
//	public String updateOperAsModule(){
//		 String packageName = ServletActionContext.getRequest().getParameter("packageName");
//		 String auth_module =ServletActionContext.getRequest().getParameter("auth_module");
//		try {
//			log.info("packageName="+packageName+" auth_module="+auth_module);
//			this.moduleBiz.updateOperAsModule(packageName, auth_module, null);
//			isOk=true;
//			infos="自动按类的方法成生权限方法信息到数据库成功";
//			this.logDbBiz.info(this.getCurrentUser(), this.moduleCode, modelCode, "updateOperAsModule", "auth_module="+auth_module,infos, "packageName="+packageName+" auth_module="+auth_module);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			isOk=false;
//			infos=e.getLocalizedMessage();			
//			e.printStackTrace();
//			this.logDbBiz.error(this.getCurrentUser(), this.moduleCode, modelCode, "updateOperAsModule", "auth_module="+auth_module,FAILURE+":"+e.getMessage(), "packageName="+packageName+" auth_module="+auth_module);
//		}
////		LogDbs.info(super.getLoginId(), "updateFuncAsModule", "updateFuncAsModule", "updateFuncAsModule:"+","+infos, null);
//		String returnValue =this.retValue(isOk, infos, null);
//		outJson(returnValue);
////		outJson("{success:true,msg:'updated funcs success'}");
//		return "json";
//	}
	
	
	
	/**
	 * 保存module
	 * @return
	 */
	public String saveModule() {
		try {
			log.info("=========saveModule-0===============");
//			CommMethod.printObject(this.module);
			this.logInfo.info("saveModule:module", module);
//			if(this.module!=null && this.module.getParent()==null){
//				this.module.setParent(null);
//			}
			if(this.module.getParent()==null&&this.module.getParentId()!=null){
				this.module.setParent(this.moduleBiz.findModule(this.module.getParentId()));
			}
			if(!CommMethod.isEmpty(module.getName())&& !CommMethod.isEmpty(module.getCode())){
				moduleBiz.saveModule(module);
			}
			String buttons="";
			if(module.getButtons()!=null){
				Module m = null;
				String pCode = module.getParent().getCode();
				buttons+=pCode+"|";
				String pName = module.getParent().getName();
				String pAction = module.getParent().getAction();
				pCode=CommMethod.upperStart(pCode);
				String buttonName=null;
				for(String button:module.getButtons()){
					m = new Module();
					m.setLeafValue(module.getLeafValue());
					m.setApplyTypeFlag(module.getApplyTypeFlag());
					m.setShowType(0);
					m.setStatus(1l);
					m.setType(ModuleType.NONE);
					m.setParent(this.module.getParent());
					if("1".equals(button)){
						buttonName="search";
						m.setSort(1);
						m.setName("查询"+pName);
						m.setCode(buttonName+pCode);
						m.setAction(this.getActionButton(pAction, buttonName));
						m.setIcon(ConfigUtil.buttonIconSearch);
						moduleBiz.saveModule(m);
						buttons+=buttonName+":"+m.getId()+",";
					}
					else if("2".equals(button)){
						buttonName="add";
						m.setSort(2);
						m.setName("增加"+pName);
						m.setCode("add"+pCode);
						m.setAction(this.getActionButton(pAction, buttonName));
						m.setIcon(ConfigUtil.buttonIconAdd);
						moduleBiz.saveModule(m);
						buttons+=buttonName+":"+m.getId()+",";
					}
					else if("3".equals(button)){
						buttonName="edit";
						m.setSort(3);
						m.setName("修改"+pName);
						m.setCode("edit"+pCode);
						m.setAction(this.getActionButton(pAction, buttonName));
						m.setIcon(ConfigUtil.buttonIconEdit);
						moduleBiz.saveModule(m);
						buttons+=buttonName+":"+m.getId()+",";
					}
					else if("4".equals(button)){
						buttonName="delete";
						m.setSort(4);
						m.setName("删除"+pName);
						m.setCode("delete"+pCode);
						m.setAction(this.getActionButton(pAction, buttonName));
						m.setIcon(ConfigUtil.buttonIconDelete);
						moduleBiz.saveModule(m);
						buttons+=buttonName+":"+m.getId()+",";
					}
					
				}
			}
			isOk=true;			
			status = "ok";
			this.jsonData=this.retValue(isOk, status, null,this.module);
			this.logDbBiz.info(this.getCurrentUser(), logDb, "saveModule", "id="+module.getId()+" code="+module.getCode()+" name="+module.getName(),SUCCESS+"|"+buttons, SUCCESS, module);
//			this.outJson(jsonData);
		} catch (Exception e) {
			status = e.toString();
//			outJson(this.retValue(isOk, status, null));			
			log.error("error", e);
			this.jsonData=this.retValue(isOk, status, null,this.module);
			this.logDbBiz.error(this.getCurrentUser(), logDb, "saveModule", "id="+module.getId()+" code="+module.getCode()+" name="+module.getName(),FAILURE+":"+e.getMessage(), FAILURE, module);
			outJson(this.jsonData);
			return "error";
		}
		
		return SUCCESS;
	}
	
	public String saveOrUpdateModule() {
		try {
			System.out.println("---------saveOrUpdateModule--"+module.getCode());
			moduleBiz.saveModule(module);
		} catch (Exception e) {
			status = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}
	
	private String getActionButton(String action, String button){
		if(action==null){
			return null;
		}
	
		int index = action.lastIndexOf("/");
		String start=action.substring(0, index+1);
		String end=action.substring(index+1);
		return start+button+CommMethod.upperStart(end);
	}

	public String findModule() {
		module = moduleBiz.findModule(module.getId());
		return SUCCESS;
	}

	public String findByModule() {
//		if(this.module.getStatus()==null){
//			this.module.setStatus(0l);
//		}
		modules = moduleBiz.findBy(module);
		return SUCCESS;
	}

	
	public String deleteModules() {
		try {
			moduleBiz.deleteModules(ids);
			status = "ok";
		} catch (Exception e) {
			status = e.toString();
			log.error("error", e);
		}
		return SUCCESS;
	}

//	public String moduleTree() {
//		modules = moduleBiz.moduleTree(getId());
//		return SUCCESS;
//	}

	public String findPageByModule() {		
		QueryObj queryObj = QueryObj.parseQueryObj(this);
		queryObj.setSortField("parentId");;
		IPage<Module> page = moduleBiz.findPageBy(queryObj);
		modules = page.getData();
		this.rows=modules;
		total = (int) page.getTotalSize();
		return SUCCESS;
	}

	public boolean isSuccess() {
		return true;
	}


	public void setModuleBiz(IModuleBiz moduleBiz) {
		this.moduleBiz = moduleBiz;
	}

	public Module getModule() {
		return this.module;
	}

	public void setModule(Module module) {
		this.module = module;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getStatus() {
		return this.status;
	}

	public Collection<Module> getModules() {
		return this.modules;
	}

	public int getTotal() {
		return this.total;
	}
	
	public String getJsonData() {
		return jsonData;
	}

	public void setJsonData(String jsonData) {
		this.jsonData = jsonData;
	}

	public boolean isSaveType() {
		return saveType;
	}

	public String getCodeTypeName() {
		return codeTypeName;
	}

	public void setCodeTypeName(String codeTypeName) {
		this.codeTypeName = codeTypeName;
	}

	public Collection getRows() {
		return rows;
	}
	
	
	
}
