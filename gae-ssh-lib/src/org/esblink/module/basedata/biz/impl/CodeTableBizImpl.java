package org.esblink.module.basedata.biz.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.esblink.common.base.IPage;
import org.esblink.common.base.QueryObj;
import org.esblink.module.basedata.biz.ICodeTableBiz;
import org.esblink.module.basedata.dao.ICodeTableDao;
import org.esblink.module.basedata.domain.CodeTable;
import org.springframework.stereotype.Service;

/**
 * esblink
 */
@Service("codeTableBiz")
public class CodeTableBizImpl  implements ICodeTableBiz {
	@Resource(name="codeTableDao")
	private ICodeTableDao codeTableDao;
	
	

	public ICodeTableDao getCodeTableDao() {
		return codeTableDao;
	}

	public void setCodeTableDao(ICodeTableDao codeTableDao) {
		this.codeTableDao = codeTableDao;
	}

	public void saveCodeTable(CodeTable codeTable) {
		if (codeTable.getCreateDate() == null) {
			codeTable.setCreateDate(new Date());
		}
		if(codeTable.getIfDel() == null){
			codeTable.setIfDel(0l);
		}
		if(codeTable.getStatus() == null){
			codeTable.setStatus(1l);
		}
		if(!(codeTable.getCodeType() == null) && codeTable.getDataId()==null ){
			String dataNum=this.maxDataId(codeTable.getCodeType());
			Long dataId=Long.valueOf(dataNum)+1;
			codeTable.setDataId(dataId);
		}
		codeTable.setModifyDate(new Date());
		// save codeTable
		this.codeTableDao.save(codeTable);
	}
	
	private String maxDataId(String codeType){
		return "0";
	}

	public CodeTable findCodeTable(Long id) {
		// load codeTable
//		CodeTable codeTable = this.codeTableDao.load(id);
//		Query q = em.createQuery("select from " + CodeTable.class.getName());
//        q.setHint("datanucleus.appengine.datastoreReadConsistency", "EVENTUAL");
//        
//        
////		return codeTable;
//		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
//		orderby.put("id", "desc");
//		String wherejql="o.code=?1";
//		Object[] params=new Object[1];
//		params[0]="test";
//		QueryResult result= codeTableDao.getScrollData(CodeTable.class, 0, 100, wherejql, params, orderby);
		
//		QueryObj queryObj = new QueryObj(0, 10, null, false);
//		queryObj.setQueryObject("code", "test");
//		IPage<CodeTable> page = this.codeTableDao.findPageBy(queryObj);
//		System.out.println("----findPageBy--page="+page.getTotalSize());
//		
//		List<Long> idList=new ArrayList<Long>();
//		idList.add(5629499534213120l);
//		idList.add(5066549580791808l);
//		idList.add(4785074604081152l);
//		idList.add(5910974510923776l);
//		queryObj = new QueryObj(-1, -1, null, false);
//		queryObj.setQueryObject("_like_code", "test%");
//		page = this.codeTableDao.findPageBy(queryObj);
//		System.out.println("---findPageBy--page="+page.getTotalSize());
//		
//		CodeTable search = new CodeTable();
//		search.setIfDel(0l);
//		search.setStatus(1l);
//		search.setCode("test");
//		try {
//			Collection<CodeTable> list=this.codeTableDao.findBy(search);
//			System.out.println("----findBy--size="+list.size());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
        return this.codeTableDao.findById(id);
	}
	public IPage<CodeTable> findPageBy(QueryObj queryObj){
//		queryObj.setQueryObject("ifDel", 0l);
//		queryObj.setQueryObject("status", 1l);
		return this.codeTableDao.findPageBy(queryObj);
	}
	
	public Collection<CodeTable> findBy(CodeTable codeTable){
		if (codeTable == null) {
			codeTable = new CodeTable();
		} 
//		else {
//			BeanUtils.clearEmptyProperty(codeTable);
//		}
		return this.codeTableDao.findBy(codeTable);
//		return null;
	}



	public void deleteCodeTables(String ids) {
		String[] idArray = ids.split(",");
		CodeTable d = null;
		for (String sid : idArray) {
			Long id = Long.parseLong(sid);
			d = this.findCodeTable(id);
			if(d!=null){
				d.setIfDel(1l);
				this.saveCodeTable(d);
			}
		}
	}

	public Collection<CodeTable> codeTableTree() {
//		List<CodeTable> codeTableTree=this.codeTableDao.codeTableTree();
//		return codeTableTree;
		return null;
	}
	
	/**
	 * codeTableAll
	 * @return
	 */
	public List<CodeTable> codeTableAll() {
//        return this.codeTableDao.findAll();
		List<CodeTable> list=this.codeTableDao.getScrollData(CodeTable.class, -1, -1, null, null).getResultlist();
		return list;
	}
	
//	/**
//	 * 找出codeTableId重复的数据
//	 * @return
//	 */
//	public List<String> codeTableIdRepeat(){
//		return this.codeTableDao.codeTableIdRepeat();	
//	}
	
	/**
	 * 根据type类型查找
	 * @author chenjh 
	 * @param types
	 * @return
	 */
	@Override
	public List<CodeTable> findByTypes(String[] types) {
//		return this.codeTableDao.findByTypes(types);
//		return null;
		String wherejpql="";
		List<CodeTable> list=this.codeTableDao.getScrollData(CodeTable.class, -1, -1, wherejpql, null).getResultlist();
		return list;
	}
	/**
	 * 根据type类型查找
	 * @param type
	 * @return
	 */
	@Override
	public List<CodeTable> codeTableByType(String type) {
		// TODO Auto-generated method stub
//		return codeTableDao.codeTableByType(type);
//		CodeTable search = new CodeTable();
//		search.setCodeType(type);
//		search.setIfDel(0l);
//		search.setStatus(1l);
//		Collection list= this.findBy(search);
//		List<CodeTable> resultList = new ArrayList<CodeTable>();
//		resultList.addAll(list);
//		return resultList;
		String wherejpql=" codeType='"+type+"' and ifDel=0 and status=1";
		List<CodeTable> list=this.codeTableDao.getScrollData(CodeTable.class, -1, -1, wherejpql, null).getResultlist();
		return list;
	}
	
	private List<CodeTable> codeTableByType(Collection<CodeTable> list, String type) {
		// TODO Auto-generated method stub
//		return codeTableDao.codeTableByType(type);
	
		List<CodeTable> resultList = new ArrayList<CodeTable>();
		for(CodeTable ct:list){
			if(ct.getCodeType().equals(type)){
				resultList.add(ct);
			}
		}
		return resultList;
	}
	
	
}
