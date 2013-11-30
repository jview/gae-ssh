package org.esblink.module.basedata.util;

import java.util.Collection;
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.module.basedata.dao.IParameterDao;
import org.esblink.module.basedata.dao.IParameterUserDao;
import org.esblink.module.basedata.domain.Parameter;
import org.esblink.module.basedata.domain.ParameterUser;

import com.esblink.dev.support.SpringDAOUtil;
import com.esblink.dev.util.CommMethod;

public class ParameterUsers {
	private static final Log log = LogFactory.getLog(ParameterUsers.class);
	public static final String OPER_DIALOG_AUTO_CLOSE_SECOND="OPER_DIALOG_AUTO_CLOSE_SECOND";

	private static HashMap<String, ParameterUser> parameterUserMap = null;
	private static HashMap<String, Parameter> parameterMap = null;
	public static void refresh(){
		log.info("============refresh()============");
		ParameterUser search = new ParameterUser();
//		search.setStatus(1);		
//		search.setIfDel(0);
		String beanName="parameterUserDao";
		IParameterUserDao parameterUserDao=(IParameterUserDao)SpringDAOUtil.getBean(beanName);
		if(parameterUserDao==null){
			parameterUserMap = new HashMap<String, ParameterUser>();
			log.error("beans.xml not found beanName:"+beanName);
			return;
		}
		beanName="parameterDao";
		IParameterDao parameterDao=(IParameterDao)SpringDAOUtil.getBean(beanName);
		if(parameterDao==null){
			parameterMap = new HashMap<String, Parameter>();
			log.error("beans.xml not found beanName:"+beanName);
			return;
		}
		Collection<ParameterUser> paraUserList= parameterUserDao.findBy(search);
		
		Parameter pSearch = new Parameter();
		pSearch.setIfDel(0l);
		Collection<Parameter> paraList = parameterDao.findBy(pSearch);
		
		
		parameterMap= new HashMap<String,Parameter>();
		
		for(Parameter para:paraList){
			para.setCode(para.getCode().trim());
			parameterMap.put(para.getCode(), para);
		}
		
		parameterUserMap= new HashMap<String,ParameterUser>();
		String mapKey = null;
		for(ParameterUser paraUser: paraUserList){
			for(Parameter para:paraList){
				if(paraUser.getParameterId().longValue()==para.getId().longValue()){
					paraUser.setCode(para.getCode());
					paraUser.setName(para.getName());
					paraUser.setDefaultValue(para.getValue());
					paraUser.setStatus(para.getStatus());					
					break;
				}
			}
			mapKey = paraUser.getUserId()+"__"+paraUser.getCode();
			parameterUserMap.put(mapKey, paraUser);
			//log4.info("key:"+p.getKey()+",value:"+ p.getValue());
		}
		
		
		
	}
	
	public static ParameterUser getParameterUser(Long userId, String paraKey){
		log.debug("-----getParameterUser--userId="+userId+" paraKey="+paraKey);
		ParameterUser paraUser = null;
		if(parameterUserMap==null){			
			refresh();			
		}
		if(parameterUserMap==null)
			return null;
		String mapKey = userId+"__"+paraKey;
		
		paraUser= parameterUserMap.get(mapKey);
		if(paraUser==null){
			Parameter para = parameterMap.get(paraKey);
			paraUser = new ParameterUser(para);
			paraUser.setUserId(userId);
		}
		return paraUser;
	}
	
	/**
	 * 如果value为空则取valueDefault
	 */
	public static String getValue(Long userId, String code){
		ParameterUser p = getParameterUser(userId, code);
		if(p==null){
			return null;
		}
		else{
			String value=null;
			if(p.getStatus().intValue()==1){
				value = p.getValue();
				if(CommMethod.isEmpty(value)){
					value = p.getDefaultValue();
				}
			}
			else{
				value = p.getDefaultValue();
			}
			return value;
		}
	}
	public static int getInt(Long userId, String code){
		try{
			String value = getValue(userId, code);
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
	public static float getFloat(Long userId, String code){
		try{
			String value = getValue(userId, code);
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
	public static String getParaName(Long userId, String code){
		ParameterUser p = getParameterUser(userId, code);
		if(p!=null){
			return p.getName();
		}
		else{
			return null;
		}
		
	}

	public  HashMap<String, ParameterUser> getParameterUserMap() {		
		return parameterUserMap;
	} 
	
}
