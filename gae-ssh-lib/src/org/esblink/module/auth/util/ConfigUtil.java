package org.esblink.module.auth.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;



public class ConfigUtil {
	private static Logger log4 = Logger.getLogger(ConfigUtil.class);
	
	public final static String buttonIconSearch="../images/search.gif";
	public final static String buttonIconAdd="../images/add.gif";
	public final static String buttonIconEdit="../images/edit.gif";
	public final static String buttonIconDelete="../images/delete.gif";
	protected static Properties config;//配置信息
	private final static Map initMap=new HashMap();
	public static enum config_key{
		INTF_URL_ADDRESS("intfUrlAddress", "https://vps.kpifa.cn/jcc_pws/http/soap/", "接口服务地址"),
		INTF_REGIST_WSDL("intfRegistWsdl", "http://vps.kpifa.cn:8070/jcc_idp/service/esbManageService", "接口自动注册wsdl地址"),
		INTF_REGIST_TYPE("intfRegistType", "esb", "接口自动注册方式(ws/esb)"),
		INTF_REGIST_SERVICE("intfRegistService", "false", "是否注册服务"),
		INTF_UNREGIST_SERVICE("intfUnRegistService", "false", "是否可取消服务"),
		INTF_REGIST_DELAY_TIME("intfRegistDelayTime", "30000", "接口启动延迟注册时间(单位毫秒)"),
		SYS_PASSWORD("sysPassword", "123456", "系统设置密码"),
		SYS_WEB_SERVICE("sysWebService", "false", "是否只是接口服务,(true时加载WEB模块功能)"),
		SYS_RESET_CODE_TABLE_JS("sysResetCodeTableJs", "false", "是否重新生成comm.dict.js"),
		SYS_LOGIN_DOMAIN("sysLoginDomain", "false", "是否以域方式登入"),
		SYS_LOGIN_VALID_CODE("sysLoginValidCode", "false", "是否支持登入验证码"),
		
		SYS_LOGIN_DOMAIN_LDAP("sysLoginDomainLdap", "ldap://10.0.15.2:389/", "域认证地址"),
		SYS_ENABLE_CACHE("sysEnableCache", "true", "是否启用用户数据缓存功能，如ws服务查询结果缓存"),
		SYS_FIND_IP_MAC("sysFindIpMac", "false", "是否反向查用户IP的MAC地址"),
		SYS_FIND_IP_MAC_THREAD_COUNT("sysFindIpMacThreadCount", "10", "多线程udp查客户端mac地址的线程数"),
		SYS_IF_TEST("sysIfTest", "false", "是否测试系统");
		private String code;
		private String defaultValue;
		private String remark;
		config_key(String code, String defaultValue, String remark){
			this.code=code;
			this.defaultValue=defaultValue;
			this.remark=remark;
		}
		
		public String getCode() {
			return code;
		}

		public static config_key getConfigKey(String code){
			config_key[] keys = config_key.values();
			config_key rKey=null;
			for(config_key key:keys){
				if(key.code.equals(code)){
					rKey=key;
					break;
				}
			}
			return rKey;
		}
		
	}
	
	public static String getConfigValue(String code){
		config_key key = config_key.getConfigKey(code);
		if(key==null){
			if(config!=null){
				return ""+config.get(code);
			}
			else{
				log4.error("-----Invalid config code:"+code);
				return null;
			}
		}
		return getConfigValue(key);
	}
	
	public static String getConfigValue(config_key cKey){
		Object obj = null;
		if(config!=null){
			obj=config.get(cKey.code);
		}
		if(initMap.get(cKey.code)!=null){
			return ""+initMap.get(cKey.code);
		}
		if(obj==null){
			return cKey.defaultValue;
		}
		else{
			return ""+obj;
		}
	}
	
	public static void setConfigValue(config_key cKey, String value){
		if(config!=null){
			config.setProperty(cKey.code, value);
		}
		else{
			initMap.put(cKey.code, value);
		}
	}
	
	private static Map loadDataMap = new HashMap();	
	
	public static Object getCacheData(Long userId, String key){
		key = userId+"_"+key;
		Object obj = loadDataMap.get(key);
		return obj;
	}
	
	/**
	 * 清除全部用户缓存
	 * @param userId
	 * @param mmKey 模块key,为空时表示清除全部
	 */
	public static void clearCacheData(Long userId){
		clearCacheData(userId, null);
	}
	
	/**
	 * 清除用户缓存
	 * @param userId
	 * @param mmKey 模块key,为空时表示清除全部
	 */
	public static void clearCacheData(Long userId, String mmKey){
		String key = userId+"_";
		
		Iterator itor = loadDataMap.entrySet().iterator();
		Entry ent = null;
						
		String curKey = "";
		String info = null;
		List<String> keyList=new ArrayList<String>();
		while(itor.hasNext()){
			ent = (Entry)itor.next();
			curKey = ""+ent.getKey();
			if(curKey.startsWith(key)){
				keyList.add(curKey);
			}			
		}
		for(String mKey:keyList){
			if(mmKey==null||mKey.indexOf(mmKey)>=0){
				loadDataMap.remove(mKey);
				info = "---clearCacheData for:"+mKey;				
				log4.info(info);
			}
		}
	}
	
	public static void putCacheData(Long userId, String key, Object obj){
		key = userId+"_"+key;
		log4.info("---putCacheData for:"+key);
		loadDataMap.put(key, obj);
	}
}
