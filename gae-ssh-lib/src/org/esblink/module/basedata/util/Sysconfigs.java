/**
 * TODO
 */
package org.esblink.module.basedata.util;

import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.module.basedata.dao.ISysconfigDao;
import org.esblink.module.basedata.domain.Sysconfig;

import com.esblink.dev.support.SpringDAOUtil;
import com.esblink.dev.util.CommMethod;


public class Sysconfigs {
	public static final String EXPORT_EXCEL_MAX_SIXE="EXPORT_EXCEL_MAX_SIXE";//导出Excel最大记录数
	public static final String FLIGHT_LOG_MAX_SEARCH_DAY="FLIGHT_LOG_MAX_SEARCH_DAY";//任务书查询时段最大天数
	public static final String FLIGHT_TYPE_TRANSPORT="FLIGHT_TYPE_TRANSPORT";//民航运输航班性质编码
	public static final String FLIGHT_TYPE_GENERAL="FLIGHT_TYPE_GENERAL";//民航通用航班性质编码
	public static final String FLIGHT_SECT_MANY="FLIGHT_SECT_MANY";//联程的同航班号
	public static final String USER_REGIST_ENABLE="USER_REGIST_ENABLE";//用户注册功能启用
	
	
	private static final Log log = LogFactory.getLog(Sysconfigs.class);
	private static HashMap<String,Sysconfig> sysconfigMap= null;	
	public static void refresh(){
		log.info("============refresh()============");
//		if(statiDao==null){
//			statiDao=BeanUtils.getSysconfigDao();
//		}
//		java.util.List<Sysconfig>pl= HibDb.queryHQL("from Sysconfig as p where p.status='"+ConstParas.status_common.ACTIVE.getStatus()+"'");
		Sysconfig search = new Sysconfig();
//		search.setStatus(1);
		search.setIfDel(0l);
		String beanName="sysconfigDao";
		ISysconfigDao sysconfigDao=(ISysconfigDao)SpringDAOUtil.getBean(beanName);
		if(sysconfigDao==null){
			sysconfigMap = new HashMap<String, Sysconfig>();
			log.error("beans.xml not found beanName:"+beanName);
			return;
		}
		java.util.Collection<Sysconfig>pl= sysconfigDao.findBy(search);
		sysconfigMap= new HashMap<String,Sysconfig>();
		for(Sysconfig p: pl){
			sysconfigMap.put(p.getCode(),p);
			//log4.info("key:"+p.getKey()+",value:"+ p.getValue());
		}
	}
	


	public static Sysconfig getSysconfig(String paraKey){
		if(sysconfigMap==null){			
			refresh();			
		}
		if(sysconfigMap==null)
			return null;
		return sysconfigMap.get(paraKey);
	}
	
	
//	public static Sysconfig getSysconfig(Session s, String paraKey){
//		if(sysconfigMap==null)
//			refresh();
//		if(sysconfigMap==null)
//			return null;
//		return sysconfigMap.get(paraKey);
//	}
	/**
	 * 如果value为空则取valueDefault
	 */
	public static String getValue(String code){
		Sysconfig p = getSysconfig(code);
		if(p==null){
			return null;
		}
		else{
			String value=null;
			if(p.getStatus().intValue()==1){
				value = p.getValue();
				if(CommMethod.isEmpty(value)){
					value = p.getValueDefault();
				}
			}
			else{
				value = p.getValueDefault();
			}
			return value;
		}
	}
	public static int getInt(String code){
		try{
			String value = getValue(code);
			if(value==null){
				return 0;
			}
			else{
				return Integer.parseInt(value);
			}
			
		}catch(Exception e){
			log.error(e.getMessage());
			return 0;
		}
	}
	public static float getFloat(String code){
		try{
			String value = getValue(code);
			if(value==null){
				return 0f;
			}
			else{
				return Float.parseFloat(value);
			}			
		}catch(Exception e){
			log.error(e.getMessage());
			return 0f;
		}
	}
	public static String getParaName(String code){
		Sysconfig p = getSysconfig(code);
		if(p!=null){
			return p.getName();
		}
		else{
			return null;
		}
		
	}

	public  HashMap<String, Sysconfig> getSysconfigMap() {		
		return sysconfigMap;
	} 

//	public  void setSysconfigMap(HashMap<String, Sysconfig> sysconfigMap) {
//		Sysconfigs.sysconfigMap = sysconfigMap;
//	}
	
	

//	public  ISysconfigDao getSysconfigDao() {
//		return sysconfigDao;
//	}

//	public  void setSysconfigDao(ISysconfigDao sysconfigDao) {
//		Sysconfigs.sysconfigDao = sysconfigDao;
//	}	
	

}
