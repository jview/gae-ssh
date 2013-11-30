/**
 * TODO
 */
package org.esblink.module.basedata.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.util.AutoTool;
import org.esblink.common.util.CommonComparator;
import org.esblink.module.basedata.dao.ICodeTableDao;
import org.esblink.module.basedata.domain.CodeTable;

import com.esblink.dev.support.SpringDAOUtil;


public class CodeTables {
	private static final Log log = LogFactory.getLog(CodeTables.class);
//	private static ICodeTableDao codeTableDao;
	private static HashMap<String, List> codeTableListMap = null;
	public static void refresh(){
		log.info("============refresh()============");
		CodeTable search = new CodeTable();
		search.setStatus(1l);
		search.setIfDel(0l);
		String beanName="codeTableDao";
		ICodeTableDao codeTableDao=(ICodeTableDao)SpringDAOUtil.getBean(beanName);
		if(codeTableDao==null){
			codeTableListMap = new HashMap<String, List>();
			log.error("beans.xml not found beanName:"+beanName);
			return;
		}
		java.util.Collection<CodeTable>pl= codeTableDao.findBy(search);
		List<CodeTable> plist=new ArrayList<CodeTable>();
		plist.addAll(pl);
		
		CommonComparator comparator = new CommonComparator();   
		comparator.setFields(new String[]{ "codeType", "display"});   
		Collections.sort(plist, comparator);
		
		codeTableListMap = new HashMap<String, List>();
		CodeTable vo = null;
		List list = new ArrayList();
		AutoTool at = new AutoTool();
		String pCode="";
		for(CodeTable p: plist){
			vo = new CodeTable();
			at.addMapCount(p.getCodeType());
			if(at.getMapCount(p.getCodeType())==1){
				if(list.size()>0){
					codeTableListMap.put(pCode, list);
				}
				pCode=p.getCodeType();
				list = new ArrayList();
			}
			vo.setCodeType(p.getCodeType());
			vo.setDataId(p.getDataId());
			vo.setShowColor(p.getShowColor());
			vo.setShowValues(p.getShowValues());
			vo.setMatchValues(p.getMatchValues());
			vo.setReferValues(p.getReferValues());
			vo.setDisplay(p.getDisplay());
			vo.setStatus(p.getStatus());
			list.add(vo);
//			codeTableMap.put(vo.getCodeType()+"__"+vo.getDataId(), vo);
			//log4.info("key:"+p.getKey()+",value:"+ p.getValue());
		}
		codeTableListMap.put(pCode, list);
	}
	


	public static CodeTable getCodeTable(String codeType, Long dataId){
		if(codeTableListMap==null){			
			refresh();			
		}
		if(codeTableListMap==null)
			return null;
		List<CodeTable> list= codeTableListMap.get(codeType);
		CodeTable codeTable = null;
		for(CodeTable ct:list){
			if(ct.getDataId().longValue()==dataId.longValue()){
				codeTable=ct;
				break;
			}
		}
		return codeTable;
	}
	
	public static List<CodeTable> getCodeTablesByType(String codeType){
		if(codeTableListMap==null){
			refresh();
		}
		if(codeTableListMap==null)
			return null;
		log.info("------getCodeTablesByType--"+codeType);
		return codeTableListMap.get(codeType);
	}
	
	/**
	 * 如果value为空则取valueDefault
	 */
	public static String getMatchValue(String codeType, Long dataId){
		CodeTable p = getCodeTable(codeType, dataId);
		if(p==null){
			return null;
		}
		else{
			String value = p.getMatchValues();
			return value;
		}
	}
	
	public static String getShowValue(String codeType, Long dataId){
		CodeTable p = getCodeTable(codeType, dataId);
		if(p!=null){
			return p.getShowValues();
		}
		else{
			return null;
		}
		
	}


	public static HashMap<String, List> getCodeTableListMap() {
		return codeTableListMap;
	}



//	public void setCodeTableDao(ICodeTableDao codeTableDao) {
//		CodeTables.codeTableDao = codeTableDao;
//	} 

	
	

}
