package org.esblink.module.auth.biz;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.common.base.domain.ModuleType;
import org.esblink.common.base.gae.BaseBIZ;
import org.esblink.common.util.BeanUtils;
import org.esblink.module.auth.dao.IModuleDao;
import org.esblink.module.auth.domain.Module;

import com.esblink.dev.util.CommMethod;

/**
 * 
 */
//@Service("moduleBiz")
public class ModuleBiz extends BaseBIZ implements IModuleBiz {
	private static Logger log = Logger.getLogger(CommMethod.class);
	public static final int PAGE_SIZE_MAX=20;
	public static final int QTIP_TITLE_LENGTH=20;

	// moduleDao
//	@Resource(name="moduleDao")
	private IModuleDao moduleDao;

	public void setModuleDao(IModuleDao moduleDao) {
		this.moduleDao = moduleDao;
	}
	

	public void saveModule(Module module) {
		System.out.println("==============saveModule===============");
		if(module.getStatus()==null){
			module.setStatus(1l);
		}
		if (module.getCreateUser()==null && super.getCurrentUser()!=null) {
			module.setCreateUser(super.getCurrentUser().getId());
			module.setCreateDate(new Date());
		}
		if(super.getCurrentUser()!=null)
			module.setModifyUser(super.getCurrentUser().getId());
		module.setModifyDate(new Date());
		this.moduleDao.save(module);
		System.out.println("==============saveModule===2============");
	}

	public Module findModule(Long id) {
		// load module
		Module module = (Module)this.moduleDao.find(Module.class, id);

		return module;
	}
	
	/**
	 * 根据idList查moduleList
	 * @param idList
	 * @return
	 */
	public List<Module> findModules(final List<Long> idList) {
		return this.moduleDao.findModules(idList);
	}
	
	public List<Module> findModuleByRoleId(Long roleId){
		List<Long> mIdList = moduleDao.loadModuleIdByRoleId(roleId);
		Module search = new Module();
		search.setStatus(1l);
		Collection<Module> mList=this.moduleDao.findBy(search);
		List<Module> moduleList=new ArrayList<Module>();
		for(Module m:mList){
			for(Long id:mIdList){
				if(id.longValue()==m.getId().longValue()){
					moduleList.add(m);
					break;
				}
			}
		}
		return moduleList;
	}

	public Collection<Module> findBy(Module module) {
		if (module == null) {
			module = new Module();
		} else {
			BeanUtils.clearEmptyProperty(module);
		}		
		return this.moduleDao.findBy(module);
	}
	
	public List<Module> findModules(String moduleName) {
		return this.moduleDao.findModulesByName(moduleName);
	}
	
	public List<Module> findModulesByCode(final String moduleCode){
		return this.moduleDao.findModulesByCode(moduleCode);
	}

	public IPage<Module> findPageBy(QueryObj queryObj) {
		return this.moduleDao.findPageBy(queryObj);
	}

	public void deleteModules(String ids) {
//		String[] idArray = ids.split(",");
//		for (String sid : idArray) {
//			Long id = Long.parseLong(sid);
//			// delete Module
//			this.moduleDao.remove(id);
//		}
		String[] idArray = ids.split(",");
		Module d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);			
			d = this.findModule(id);
			if(d!=null){
				d.setStatus(0l);				
				this.saveModule(d);
			}
		}
	}
	
	public void ajaxMoveNode(long id, long oldParentId, long newParentId,
			long nodeIndex) {
		// TODO Auto-generated method stub
		log.debug("ajaxMoveNode:id="+id+" oldParentId="+oldParentId+" newParentId="+newParentId+" nodeInidex="+nodeIndex+" aaaaaa");
        Module func = this.findModule(id);   
        Integer nodeIndex_i=Long.valueOf(nodeIndex).intValue();
//        Integer newParentId_i = Long.valueOf(newParentId).intValue();
        long minIndex = 0;
        if(func.getSort()!=null){
        	minIndex=func.getSort();
        }
        long maxIndex = nodeIndex;   
//        log.debug("minIndex="+minIndex+" maxIndex="+maxIndex);
        if(oldParentId == newParentId && minIndex != maxIndex){   
            // 在同一个父节点下发生移动   
            if(minIndex < maxIndex){   
                // 当要移动的节点的序号小于要移动到的目标序号，则下移   
                this.downNode(oldParentId, minIndex, maxIndex);   
            }else if(minIndex > maxIndex){   
                // 当要移动的节点的序号大于要移动到的目标序号，则上移   
                maxIndex = minIndex;   
                minIndex = nodeIndex;   
                this.upNode(oldParentId, minIndex, maxIndex);   
            }   
            // 节点本身的序号设置成要移动到的目标序号   
            func.setSort(nodeIndex_i);   
//            this.update(func);   
            this.saveModule(func);
//            log.debug("updated1");
        }   
        if(oldParentId != newParentId){   
            // 在不同父节点下发生移动   
            //1、相当于要移动的节点在原父节点下下移到最后再删除掉，因此要指定移动发生时节点所在的位置   
            this.downNode(oldParentId, minIndex, -1);   
            //2、相当于要移动的节点在新父节点下上移到指定的位置，因此需要指定要移动到的位置   
            this.upNode(newParentId, maxIndex, -1);   
            // 节点本身的序号设置成要移动到的目标序号   
            func.setSort(nodeIndex_i);   
            func.setParent((Module)this.moduleDao.find(Module.class, newParentId));   
//            this.update(func);   
            this.saveModule(func);
//            log.debug("updated2");
        }   
	}

	public void ajaxRemoveNode(Long id) {
		// TODO Auto-generated method stub
		System.out.println("=========ajaxRemoveNode==="+id);
//		Module search = new Module();
//		Module parent = new Module();
//		parent.setId(id);		
//		search.setParent(parent);
		Collection<Module> list = this.moduleDao.findChildModules(id);
		log.info("------ajaxRemoveNode--");
//		CommMethod.printListHasDate(list);
    	for(Module func:list){ 
            ajaxRemoveNode(func.getId());   
        }   
//        this.removeById(id);   
        this.deleteModules(""+id);
	}

	public Boolean ajaxUpdateTitle(Long id, String title) {
		// TODO Auto-generated method stub
		Boolean flag = false;   
        Module func = this.findModule(id);   
        func.setName(title);
        this.saveModule(func);
        flag = true;
        return flag;   
	}

	public void downNode(long parentId, long minIndex, long maxIndex) {
		// TODO Auto-generated method stub
		
		Module module = this.findModule(parentId);
		if(maxIndex!=-1 && module.getSort()>minIndex && module.getSort()<=maxIndex){
			module.setSort(module.getSort()-1);
			this.saveModule(module);
		}
	}

	public void upNode(long parentId, long minIndex, long maxIndex) {
		// TODO Auto-generated method stub		
		Module module = this.findModule(parentId);
		if(maxIndex!=-1 && module.getSort()>minIndex && module.getSort()<=maxIndex){
			module.setSort(module.getSort()+1);
			this.saveModule(module);
		}
	}
	
	public Collection<Module> findModulesByParentId(Long parentId){
//		Module search = new Module();
//		search.setStatus(1l);
//		search.setParentId(parentId);
//		Collection<Module> list=this.findBy(search);
//		return list;
		return  this.moduleDao.findChildModules(parentId);
	}
	
	/**
	 * 递归查询所有子节点
	 * @param parentId
	 * @param districtId
	 * @return
	 */
	public Collection<Module> queryModuleByParent(Module parent, Collection<Module> modulesList){
		if(modulesList==null){
			modulesList=new ArrayList<Module>();
		}
		Collection<Module> taList = this.moduleDao.findChildModules(parent.getId());
//		if(taList.size()==0){
//			parent.setLeafValue(1);//true
//		}
//		log.info("------queryModuleByParent--");
//		CommMethod.printListHasDate(taList);
//		Module ta;
		for(Module ta:taList){
			ta.setParent(parent);
			ta.setParentName(parent.getName());
			modulesList.add(ta);
			if(!ta.isLeaf()){				
				this.queryModuleByParent(ta,  modulesList);
			}
			
		}
		return modulesList;
	}


	@Override
	public StringBuffer queryModuleJson(Module module, int pageSize,
			int pageNum, long rootModuleId) {
		// TODO Auto-generated method stub
		Module root = this.findModule(rootModuleId);
		Collection<Module> funcList = this.queryModuleByParent(root, null);
		log.info("------queryModuleJson--");
//		CommMethod.printListHasDate(funcList);
		log.info(" rootModuleId="+rootModuleId+" size="+funcList.size());		
		StringBuffer value=getJsonById(funcList,  rootModuleId, true);
		return value;
	}
	
	@Override
	public List<Module> queryModuleList(Module module, int pageSize,
			int pageNum, long rootModuleId) {
		Module root = this.findModule(rootModuleId);
		Collection<Module> funcList = this.queryModuleByParent(root, null);
		log.info("------queryModuleJson--");
//		CommMethod.printListHasDate(funcList);
		log.info(" rootModuleId="+rootModuleId+" size="+funcList.size());		
		return getModulesById(funcList,  rootModuleId, true);
	}

//	@Override
//	public PaginatedList queryModuleList(Module func, int pageSize, int pageNum) {
//		// TODO Auto-generated method stub
//		if (pageSize > PAGE_SIZE_MAX) {
//			String infos = "查询单页不能超出最大行数:" + PAGE_SIZE_MAX;
////			this.aMessage.addMessage(infos);
//			log.error(infos);
//			return new PaginatedList();
//		}
//
//		List<Module> moduleList = new ArrayList<Module>();
//		LogInfo logInfo = LogInfo.getLogger(ModuleBiz.class);
//		logInfo.info("module", func);
//		moduleList.addAll(this.findBy(func));
//		return this.getPaginatedList(moduleList, moduleList.size(), pageSize, pageNum);
//	}
	public static List getModulesById(Collection<Module> list, long parentId, boolean show) {
		List<Module> tempList = new ArrayList<Module>();

		//汇总子节点
		for(Module func:list){
			if(func.getParent()!=null && func.getParent().getId().longValue()==parentId){	
//				System.out.println(" modulesId="+func.getId()+" code="+func.getCode());
				tempList.add(func);				
			}									
		}
		return tempList;
	}
	
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
	public static StringBuffer getJsonById(Collection<Module> list, long parentId, boolean show) {
//		Module func=null;
		
		StringBuffer json = new StringBuffer("[");
		StringBuffer temp=null;
		List<Module> tempList = new ArrayList<Module>();

		//汇总子节点
		for(Module func:list){
			if(func.getParent()!=null && func.getParent().getId().longValue()==parentId){	
//				System.out.println(" modulesId="+func.getId()+" code="+func.getCode());
				tempList.add(func);				
			}									
		}
		Module func = null;
		String showTypeName=null; 
		//将子节点生成json菜单格式
		for(int i=0; i<tempList.size(); i++){
			func = (Module)tempList.get(i);
			showTypeName=""+func.getShowType();
//			if(func.getShowType()!=null && func.getShowType().intValue()==1){
//				showTypeName="显示";
//			}
			temp = new StringBuffer();
			temp.append("{");
			temp.append("id:").append(func.getId()).append(",")
				.append("text:'").append(CommMethod.getNotNullTrim(func.getName()+","+showTypeName)).append("',")
				.append("code:'").append(CommMethod.getNotNullTrim(func.getCode())).append("',")
				.append("number:").append(func.getSort()).append(",");	
			if(func.getName()!=null){
				temp.append("qtip:'").append(CommMethod.getShortTitle(func.getName(), QTIP_TITLE_LENGTH)).append("',");
			}
			if(func.getIcon()!=null){
				temp.append("icon:'"+func.getIcon()+"', ");			
			}
			if (func.isLeaf()) {
				temp.append("leaf:true, ");							
			} 
			else{		
				temp.append("leaf:false, ").append("children:").append(
						getJsonById(list,  func.getId(), show)).append(",");
			}
			temp.append("singleClickExpand:true}");
			if (i < tempList.size() - 1) {
				temp.append(",\n");
			}
//			System.out.println("temp="+temp);
			json.append(temp);
		}
			
	
		json.append("]");
		return json;
	}
	
	/**
	 * 带check的tree
	 * @param funcList
	 * @param userFuncList
	 * @param parentId
	 * @param show
	 * @return
	 */
	public static StringBuffer getJsonById(List<Module> funcList, List<Module> userFuncList, long parentId, boolean show) {
		Module func=null;
		Module uFunc = null;
		
		StringBuffer json = new StringBuffer("[");
		StringBuffer temp;
		List<Module> tempList = new ArrayList<Module>();

		//汇总子节点
		for (int i = 0; i < funcList.size(); i++) {
			func = (Module)funcList.get(i);			
			if(func.getParent()!=null && func.getParent().getId().longValue()==parentId){		
				tempList.add(func);				
			}									
		}
		boolean isExist =false;
		//将子节点生成json菜单格式
		for(int i=0; i<tempList.size(); i++){
			func = (Module)tempList.get(i);
			isExist = false;
			for(int j=0; j<userFuncList.size(); j++){
				uFunc = (Module)userFuncList.get(j);
				if(func.getCode().equals(uFunc.getCode())){
					isExist=true;
				}
			}
			
			temp = new StringBuffer();
			temp.append("{");
			temp.append("id:").append(func.getId()).append(",")
				.append("text:'").append(func.getName()).append("',")
				.append("number:").append(func.getSort()).append(",");
			if(func.getName()!=null){
				temp.append("qtip:'").append(CommMethod.getShortTitle(func.getName(), QTIP_TITLE_LENGTH)).append("',");
			}
			if (func.isLeaf()) {
				if(!show){
//					if(BeanUtils.isEmpty(func.getHref())){
//						continue;
//					}
				}				
//				if(func.getIconcls()!=null)
//					temp.append("iconCls:'").append(func.getIconcls()).append("',");	
				temp.append("leaf:true, ");
//				temp.append("hrefTarget:'").append(func.getHrefTarget()).append("',");			
//				temp.append("flag:'").append(func.getFlag()).append("',");
				if(isExist){
					temp.append("checked:true,");
				}
				else{
					temp.append("checked:false,");
				}
//				temp.append("href:'").append(func.getHref()).append("',");		
						
			} 
			else{	
				if(isExist){
					temp.append("checked:true,");
				}
				else{
					temp.append("checked:false,");
				}
//				if(func.getCls()!=null)
//					temp.append("cls:'").append(func.getCls()).append("',");		
				temp.append("leaf:false, ").append("children:").append(
						getJsonById(funcList, userFuncList, func.getId(), show)).append(",");
			}
			temp.append("singleClickExpand:true}");
			if (i < tempList.size() - 1) {
				temp.append(",\n");
			}
//			System.out.println("temp="+temp);
			json.append(temp);
		}
			
	
		json.append("]");
		return json;
	}
	
//	/**
//	 * 带check的tree
//	 * @param funcList
//	 * @param userFuncList
//	 * @param parentId
//	 * @param show
//	 * @return
//	 */
//	public static StringBuffer getJsonById(List roleFuncList, List roleFuncOperAllList, List roleFuncOperList, long parentId, boolean show) {
//		log.debug("parentId="+parentId+" roleFuncList size="+roleFuncList.size()+" roleFuncOperAllList="+roleFuncOperAllList.size()+" roleFuncOperList="+roleFuncOperList.size());
//		Module func=null;
//		Module uFunc = null;
//		ModuleOper fo = null;
//		ModuleOper ufo = null;
//		
//		StringBuffer json = new StringBuffer("[");
//		StringBuffer temp = new StringBuffer();
//		StringBuffer oper_sb = new StringBuffer();
//		String oper_str = "";
//		List tempList = new ArrayList();
////		boolean exist=false;
//		//汇总子节点
//		for (int i = 0; i < roleFuncList.size(); i++) {
//			func = (Module)roleFuncList.get(i);	
////			System.out.println(func.getPfuncid());
//			if(func.getParentId()!=null && func.getParentId().intValue()==parentId){		
//				tempList.add(func);				
//			}									
//		}
//		boolean isExist =false;
//		int count=0;
//		//将子节点生成json菜单格式
////		System.out.println("tempList="+tempList);
//		for(int i=0; i<tempList.size(); i++){
//			func = (Module)tempList.get(i);
//			isExist = false;
////			for(int j=0; j<userFuncList.size(); j++){
////				uFunc = (Func)userFuncList.get(j);
////				if(func.getFunccode().equals(uFunc.getFunccode())){
////					isExist=true;
////				}
////			}
//			
//			
//			
//			temp = new StringBuffer();
//			temp.append("{");
//			temp.append("id:").append(func.getId()).append(",")
//				.append("text:'").append(func.getName()).append("',")
//				.append("number:").append(func.getSort()).append(",");										
//			if (func.getLeafValue()==1) {
//				if(!show){
//					if(CommMethod.isEmpty(func.getAction())){
//						continue;
//					}
//				}
//				temp.append("cls:'").append("feeds-node").append("',");
////				if(func.getIconcls()!=null)
////					temp.append("iconCls:'").append(func.getIconcls()).append("',");	
//				
////				temp.append("hrefTarget:'").append(func.getHrefTarget()).append("',");			
////				temp.append("flag:'").append(func.getFlag()).append("',");
//				if(isExist){
//					temp.append("checked:true,");
//				}
//				else{
//					temp.append("checked:false,");
//				}
////				temp.append("href:'").append(func.getHref()).append("',");	
//				oper_sb=new StringBuffer();
//				oper_sb.append("children:[");
//				count=0;
//				for(int j=0; j<roleFuncOperAllList.size(); j++){
//					fo = (ModuleOper)roleFuncOperAllList.get(j);
//					isExist=false;					
//					if(func.getId().intValue()==fo.getModuleId().intValue()){
//						count++;
//						for(int k=0; k<roleFuncOperList.size(); k++){
//							ufo = (ModuleOper)roleFuncOperList.get(k);
//							if(fo.getId().intValue()==ufo.getId().intValue()){
//								isExist=true;								
//							}
//						}
//						
//						oper_sb.append("{");
//						oper_sb.append("id:").append(fo.getId()).append(",")
//							.append("text:'").append(fo.getCode()).append("',");	
//						oper_sb.append("number:"+count+", ");
//						oper_sb.append("iconCls:'").append("table_gear").append("',");
//						oper_sb.append("leaf:true, ");						
//						
//						if(isExist){
//							oper_sb.append("checked:true,");
//						}
//						else{
//							oper_sb.append("checked:false,");
//						}
//						oper_sb.append("singleClickExpand:true}\n");		
//						oper_sb.append(",");
//					}					
//				}
//				if(oper_sb.toString().endsWith(",")){
//					oper_str =oper_sb.toString();
//					oper_str = oper_str.substring(0, oper_str.length()-1);
//				}else{
//					oper_str = oper_sb.toString();
//				}
//				oper_str = oper_str+"],";
////				System.out.println("COUNT="+count+" oper_str="+oper_str);
//				if(count>0){
//					temp.append("leaf:false, ");
//					temp.append(oper_str);					
//				}
//				else{
//					temp.append("leaf:true, ");
//					temp.append("disabled:true, ");
//				}
//						
//			} 
//			else{		
////				if(func.getCls()!=null)
////					temp.append("cls:'").append(func.getCls()).append("',");		
//				temp.append("leaf:false, ").append("children:").append(
//						getJsonById(roleFuncList, roleFuncOperAllList, roleFuncOperList, func.getId(), show)).append(",");
//			}
//			temp.append("singleClickExpand:true}");
//			if (i < tempList.size() - 1) {
//				temp.append(",\n");
//			}
////			System.out.println("temp="+temp);
//			json.append(temp);
//		}
//			
//	
//		json.append("]");
//		return json;
//	}

	
//	/**
//	 * 查询转换
//	 * 
//	 * @param query
//	 * @param pageSize
//	 * @param pageNum
//	 * @return
//	 */
//	protected PaginatedList getPaginatedList(Query query, int pageSize,
//			int pageNum) {
//		int row = 0;
//		
//		ScrollableResults sr = query.scroll();
//		sr.last();
//
//		if (sr.getRowNumber() > 0) {
//			row = sr.getRowNumber() + 1;
//		}
//		query.setFirstResult(pageSize * (pageNum - 1));
//		query.setMaxResults(pageSize);
//		
//		PaginatedList pl = new PaginatedList();
//		pl.setPageTotal((int) Math.ceil((double) row / (double) pageSize));		
//		pl.setPageSize(pageSize);
//		pl.setPageNum(pageNum);
//		pl.setStartRownum(pageSize * (pageNum - 1));
//		pl.setEndRownum((pl.getStartRownum() + pageSize) - 1);
//		pl.setRowTotal(row);
//		pl.setResults(query.list());
//		
////		pl = this.getPaginatedList(query.list(), row, pageSize, pageNum);
//		return pl;
//	}
//
//	public PaginatedList getPaginatedList(List list, int totalRowCount, int pageSize, int pageNum){
////		log.debug("getPaginatedList:totalRowCount="+totalRowCount+" pageSize="+pageSize+" pageNum="+pageNum);
//		int startRowNum = pageNum>0 ? pageSize*(pageNum-1) : 0;				
//		int endRowNum=(startRowNum+pageSize)<totalRowCount?(startRowNum+pageSize):totalRowCount;
//		int pageTotal=(int) Math.ceil((double) totalRowCount/ (double) pageSize);
//		
//		PaginatedList pl = new PaginatedList();
//		pl.setPageTotal(pageTotal);
//		pl.setPageSize(pageSize);
//		pl.setPageNum(pageNum);
//		pl.setStartRownum(startRowNum);
//		pl.setEndRownum(endRowNum );
//		pl.setRowTotal(totalRowCount);
//		pl.setResults(list.subList(startRowNum, endRowNum));
//		
////		log.debug("getPaginatedList:pageTotal="+pl.getPageTotal()+" pageSize="+pl.getPageSize()+" pageNum="+pl.getPageNum()+" startRowNum="+pl.getStartRownum()+" maxRowNum="+pl.getEndRownum());
//		return pl;
//	}
	
//	/**
//     * 按模块自动生成BO层相关操作的认证信息到数据库
//     */
//    public String updateOperAsModule(String packageName, String auth_module, Long moduleId){
//    	log.debug("=====updateFuncAsModule--packageName="+packageName+" auth_module="+auth_module+" moduleId="+moduleId);
////    	List funcList = this.getDao().queryHql("from Func where status<>"+ConstParas.status_common.DELETE.getStatus());
////    	List fOperList = this.getDao().queryHql("from FuncOper where status<>"+ConstParas.status_common.DELETE.getStatus());
//    	
//    	ModuleOper moSearch= new ModuleOper();
//    	moSearch.setModuleId(moduleId);
//    	List fOperList = this.moduleOperDao.findBy(moSearch);
////    	List list = this.getDao().findByProperty(Func.PROP_CODE, "AUTH_FUNC_BO");
//    	
//    	Module search = new Module();
//    	search.setId(null);
//    	search.setCode(Constant.UPDATE_MODULE_PARENT_CODE);
//    	List list = this.moduleDao.findBy(search);
//    	
//    	int parentId=9;
//    	if(list!=null&&list.size()>0){
//    		Module func = (Module)list.get(0);
//    		parentId=func.getId().intValue();
//    	}
//    	
//    	List<Long> idList = new ArrayList<Long>();    	
//    	if(moduleId!=null){
//    		idList.add(moduleId);
//    	}
//    	List funcList = this.moduleDao.findModules(idList);
////    	log.info("-------funcList="+funcList.size());
//    	if(moduleId!=null&&funcList.size()>0){
//    		Module module = (Module)funcList.get(0);
//    		auth_module=module.getCode();
//    		if(module.getClassName()!=null){
//    			packageName=module.getClassName().substring(0, module.getClassName().lastIndexOf("."));
//    		}
//    	}
//    	
//    	if(CommMethod.isEmpty(packageName)||CommMethod.isEmpty(auth_module)){
//        	auth_module="AUTH_SECURITY";    	
//        	packageName="com.szair.monsys.security.biz";
//    	}
//    	
//    	this.updateFuncs(parentId, packageName, auth_module, funcList, fOperList);
//    	
////		this.exchangeFuncValue();
//		return "success";
//    }
//    
//    
//    /**
//     * 根据包名，模块首编码按模块自动生成BO层相关操作的认证信息到数据库
//     * 如果相同编码的菜单，可自动生成相同的方法级权限
//     * 如果类的方法发生改变，使其找不到，系统自动将其设为删除，如有增加则自动增加
//     * @param parentId
//     * @param packageName
//     * @param auth_module
//     * @param funcList
//     * @param fOperList
//     * @return
//     */
//    private String updateFuncs(int parentId, String packageName, String auth_module, List<Module> funcList, List<ModuleOper> fOperList){
//    	log.info("======updateFuncs======"+packageName+" "+auth_module+" funcList="+funcList.size()+" fOperList="+fOperList.size());
//    	Module func = null;   
//    	String[] classNames = ClassUtil.getPackageAllClassName(packageName);
//    	List<Module> moduleList = this.moduleDao.getModulesClassName();
//    	String className = null;
//		String auth_value = null;
////		System.out.println("=============package="+packageName);
//		
//		
//		List addList = new LinkedList();
//		List stopList = new LinkedList();
//		List<String> fList = new ArrayList();
//		List<ModuleOper> foList = new ArrayList();
//		List<ModuleOper> fmoList = null;
//		boolean isExist=false;
//		
//		for(String cName:classNames){
////			log.info("cName="+cName);
//			if(cName.indexOf(".class")==-1){
//				continue;
//			}			
//			className=packageName+"."+cName.substring(0, cName.indexOf(".class"));
//			fList.add(className);
//		}
//		List<Module> funFileList = null;
//		for(String cName:fList){			
//			className = cName;
//			addList = new LinkedList();	
//			stopList = new LinkedList();
//			auth_value = className.substring(className.lastIndexOf(".")+1);
//			if(!auth_value.startsWith("I")){
//				continue;
//			}
////			log.info("cName="+cName);
//			auth_value = auth_module+"_"+auth_value;
//			isExist=false;			
//			func = this.prepare4ModuleNode(parentId, auth_value);
//			func.setClassName(className);
//			funFileList = new ArrayList();//同一编码的功能
//			for(Module f:funcList){				
//				if(func.getCode().equals(f.getCode())){
//					isExist = true;
//					func.setId(f.getId());
//					funFileList.add(f);
//				}
//			}
////			log.info("cName0="+cName+" moduleId="+func.getId()+" funcList="+funcList.size()+" isExist="+isExist+" func.code="+func.getCode());
//			if(!isExist){
//				if(funcList.size()==1){			
//					for(Module modCur:funcList){
//						if(className.equals(modCur.getClassName())){
//							func = modCur;
//							isExist=true;
//							funFileList.add(func);
//							break;					
//						}
//					}
//					if(!isExist){
//						continue;
//					}
//				}
//				else{
//					for(Module modCur:moduleList){
//						if(className.equals(modCur.getClassName())){
//							func = modCur;
//							isExist=true;
//							funFileList.add(func);
//							break;					
//						}
//					}
//				}
//			}
////			log.info("cName1="+cName+" moduleId="+func.getId()+" funcList="+funcList.size()+" isExist="+isExist+" func.code="+func.getCode());
//			//有新的实现类，则自动生成功能菜单进行保存
//			if(!isExist){				
//				this.moduleDao.saveOrUpdate(func);
////				funcList.add(func);
//				funFileList.add(func);
//				
//			}
////			log.info("cName2="+cName+" moduleId="+func.getId()+" funFileList="+funFileList.size());
//			if(funFileList.size()>0){	
//				for(Module f: funFileList){
////					log.info("f_id1="+f.getId()+" funccode="+f.getCode()+" funcname="+f.getName());
//					isExist=false;
//					fmoList = this.getMethodFunc(f.getId().intValue(), className, null);//检查类里的每个方法，生成菜单的方法级权限（自动新增）
//					foList = new ArrayList();
//					for(ModuleOper fOper:fOperList){
//						if(fOper.getModuleId().intValue()==f.getId().intValue())
//							foList.add(fOper);
//					}
//					
////					log.info("f_id2="+f.getId()+" funccode="+f.getCode()+" funcname="+f.getName());
//					for(ModuleOper fmo:fmoList){					
//						if(fmo.getModuleId().intValue()==f.getId().intValue()){
//							isExist=false;
//							fmo.setModuleCode(f.getCode());
//							fmo.setModifyUser(this.getUserId());
//							fmo.setModifyDate(new Date());	
//							for(ModuleOper fOper:fOperList){
//								if(fOper.getModuleId().intValue()==f.getId().intValue()&&fmo.getCode().equals(fOper.getCode())){
//									isExist = true;
//								}
//							}
//							if(!isExist){							
//								addList.add(fmo);
//							}
//						}
//					}	
//					
//					//检查被删除或改名的方法，自动停止
//					for(ModuleOper fOper:foList){
//						isExist=false;
//						for(ModuleOper fmo:fmoList){					
//							if(fmo.getModuleId().intValue()==f.getId().intValue()){
//								if(fOper.getModuleId().intValue()==f.getId().intValue()&&fmo.getCode().equals(fOper.getCode())){
//									isExist = true;
//								}
//							}							
//						}
//						if(!isExist){
//							fOper.setStatus(ConstParas.status_common.STOP.getStatus());
//							stopList.add(fOper);
//						}
//						
//					}
//				}
//				CommMethod.printListHasDate(addList);
//				this.moduleDao.saveOrUpdate(addList.toArray());
////				log.debug("stop for remove function size:"+stopList.size());
//				CommMethod.printListHasDate(stopList);
//				this.moduleDao.update(stopList.toArray());
//				
//				
//				
//				log.debug("saveOrUpdate "+className+" add size="+addList.size()+" stop size="+stopList.size());
//			}else{
//				if(fmoList!=null){
//					for(int i=0; i<fmoList.size(); i++){
//						log.debug("i="+i+"  "+fmoList.get(i));
//					}
//				}
//			}
//			
//		}
//		
////		List delList = new ArrayList();
//	
////		/**
////		 * 检查已变更或已删除的菜单及方法
////		 */
////		for(Module f:funcList){		
////			isExist=false;
//////			System.out.println(" for delete f="+f.getFunccode()+" auth_module="+auth_module);
////			if(f.getCode().startsWith(auth_module)){
////				for(String cName:fList){
////					className = cName;
////					auth_value = className.substring(className.lastIndexOf(".")+1);
////					auth_value = auth_module+"_"+auth_value;
////					func = this.prepare4ModuleNode(parentId, auth_value);					
////					if(func.getCode().equals(f.getCode())){
////						isExist = true;
////					}					
////				}
////				//用于移除菜单
////				if(!isExist){
////					log.info("remove by Class change:func="+f.getCode());
////					f.setStatus(ConstParas.status_common.DELETE.getStatus());
//////					f.setStatus(3l);
////					delList.add(f);
////				}
//////				
////				isExist=false;
////				
////			} 
////		}
////				
////		this.moduleDao.update(delList.toArray());
////		log.debug("stop for remove function size:"+delList.size());
//		
//		return "success";
//    }
    
    /**
     * 预处理一个将要增加的子菜单对象
     * @param parentId
     * @param auth_value
     * @return
     */
    private Module prepare4ModuleNode(int parentId, String auth_value){
    	Module func = new Module();
		func.setCode(auth_value);
		func.setName(auth_value);
		func.setStatus(1l);
		func.setAction(null);			
		func.setType(ModuleType.MENU);
		func.setLeafValue(1);	
//		func.setLevel(0);
		func.setSort(0);		
		Module p = new Module();
		p.setId(0l+parentId);
		func.setParent(p);
//		func.setFlag("iframe");
//		func.setHrefTarget("mainTabPanel");
//		func.setIconcls("table_gear");
		return func;
    }
    
//   /**
//     * 预处理一个准备增加的FuncOper对象
//     * @param funcId
//     * @param operCode
//     * @param operResource
//     * @return
//     */
//    private ModuleOper prepare4ModuleOper(int funcId, String operCode, String operResource){
//    	ModuleOper fOper = new ModuleOper();
//    	fOper.setCode(operCode);
//		fOper.setResources(operResource);
//		fOper.setModuleId(0l+funcId);
//		fOper.setLevels(0);
//		fOper.setCreateUser(this.getUserId());
//		fOper.setCreateDate(new Date());
//		fOper.setModifyUser(this.getUserId());
//		fOper.setModifyDate(new Date());		
//		fOper.setOrderCount(0l);	
//		fOper.setStatus(ConstParas.status_common.ACTIVE.getStatus());
//		return fOper;
//    }
    
//    /**
//     * 
//     * @param funcId
//     * @param boClass
//     * @param subClass_name
//     * @return
//     */
//    private List getMethodFunc(int funcId, Class boClass, String subClass_name){
//    	List list = new LinkedList();
//    	
//		if(boClass==null){
//			return new LinkedList();
//		}else if(boClass.getSimpleName() .equals(Object.class.getSimpleName())){
//			return new LinkedList();
//		}
//		
//		ModuleOper fOper = null;
//		String class_name = boClass.getName();
//		if(subClass_name!=null){
//			class_name = subClass_name;
//		}
//		Method[] methods = boClass.getDeclaredMethods();		
//		Method method = null;
//		String method_str = null;
//		String value = null;
//		for(int i=0; i<methods.length; i++){
//			method = methods[i];
//			method_str = method.toGenericString();
//			if(method_str.startsWith("public")&&method.getName().indexOf("Dao")==-1){
////				if(class_name.indexOf("Impl")>0){
////					System.out.println(class_name);
////					class_name = class_name.substring(0, class_name.indexOf("Impl"));
////				}
//				if(funcId>0){									
//					fOper = this.prepare4ModuleOper(funcId, method.getName(), class_name+"."+method.getName());
//					list.add(fOper);
//				}
//				else{
//					value = (method.getName()+"="+class_name+"."+method.getName());					
//					list.add(value);
//				}
//			}
//		}
////		if(boClass.getSuperclass()!=null){
////			List listt = printMethodFunc(funcId, boClass.getSuperclass(), class_name);			
////			list.addAll(listt);
////		}
////		else if(boClass.getInterfaces().length>0){
////			List listt = printMethodFunc(funcId, boClass.getInterfaces()[0], class_name);
////			list.addAll(listt);
////		}
//		return list;
//	}
    
//    /**
//	 * 显示类中所有的public方法对应的方法全名（包括从父类继承的方法)
//	 * @param className 类的全名
//	 * exp:className=com.shenzhenair.framework.app.atom.biz.impl._BaseBOImpl
//	 * subClass_name 第一次使用为null,它是给自身使用的
//	 */
//	public List getMethodFunc(int funcId, String className, String subClass_name){
//		List list = new LinkedList();
//		Class classes = null;
//		try {
//			classes = Class.forName(className);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			log.error(e.getMessage());
//		}
//		if(classes!=null){
//			list = getMethodFunc(funcId, classes, null);
//		}
//		return list;
//	}
    
   
    
}
