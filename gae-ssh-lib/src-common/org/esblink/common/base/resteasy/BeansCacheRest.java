package org.esblink.common.base.resteasy;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

public class BeansCacheRest {
	private static Logger log4 = Logger.getLogger(BeansCacheRest.class);
	private static Map beansMap = new HashMap();
	private static Map serviceMap = new HashMap();
	public static void putBean(Class clz, String beanName, Object obj){
		if(clz==null){
			log4.error("registService service is "+clz);
			return;
		}
		serviceMap.put(clz.getSimpleName(), clz);
		beansMap.put(clz.getSimpleName()+"_"+beanName, obj);
	}
	public static Object getBean(Class clz, String beanName){
		return beansMap.get(clz.getSimpleName()+"_"+beanName);
	}
	public static Map getServiceMap() {
		return serviceMap;
	}
	
	
}
