package org.esblink.common.util;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONUtils;

/**
 * json处理
 * @author chenjh
 *
 */
public class JsonInfo {
	private static JsonConfig cfg;
	private static JsonConfig cfgDate;
	private static JsonConfig cfgDateTime;
	private static JsonConfig singletonCfg;
	static{
		String[] dateFormats = new String[] {"yyyy-MM-dd HH:mm:ss"}; 
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats));		
	}
	public JsonInfo(){
		config(null);		
	}
	
	public JsonInfo(String datePattern){
		config(datePattern);
	}	
	public void config(String datePattern){
		if(datePattern==null||datePattern.length()<2){
			datePattern = "yyyy-MM-dd HH:mm:ss";	
		}
		
		DateJsonValueProcessorImpl jvp = new DateJsonValueProcessorImpl(datePattern);
		if(cfg==null){
			cfg = new JsonConfig();
			cfg.setExcludes(new String[] {""});
	        cfg.setIgnoreDefaultExcludes(false);
			cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
			cfg.registerJsonValueProcessor(java.util.Date.class, jvp);
			cfg.registerJsonValueProcessor(java.sql.Date.class, jvp);
		}
	}
	
	public static JsonConfig getSingletonCfg(){
		if(singletonCfg==null){
			singletonCfg = new JsonConfig();
		}
		return singletonCfg;
	}
	
	public static JsonConfig getCfg(){
		if(cfg==null){
			String datePattern = "yyyy-MM-dd HH:mm:ss";	
			DateJsonValueProcessorImpl jvp = new DateJsonValueProcessorImpl(datePattern);
			cfg = new JsonConfig();
			cfg.setExcludes(new String[] {""});
	        cfg.setIgnoreDefaultExcludes(false);
			cfg.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
			cfg.registerJsonValueProcessor(java.util.Date.class, jvp);
			cfg.registerJsonValueProcessor(java.sql.Date.class, jvp);
		}
		return cfg;
	}
	
	public static JsonConfig getCfgDateTime(){
		if(cfgDateTime==null){
			String datePattern = "yyyy-MM-dd HH:mm";	
			DateJsonValueProcessorImpl jvp = new DateJsonValueProcessorImpl(datePattern);
			cfgDateTime = new JsonConfig();
			cfgDateTime.setExcludes(new String[] {""});
			cfgDateTime.setIgnoreDefaultExcludes(false);
			cfgDateTime.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
			cfgDateTime.registerJsonValueProcessor(java.util.Date.class, jvp);
//			cfgDateTime.registerJsonValueProcessor(java.sql.Date.class, jvp);
		}
		return cfgDateTime;
	}
	
	public static JsonConfig getCfgDate(){
		if(cfgDate==null){
			String datePattern = "yyyy-MM-dd";	
			DateJsonValueProcessorImpl jvp = new DateJsonValueProcessorImpl(datePattern);
			cfgDate = new JsonConfig();
			cfgDate.setExcludes(new String[] {""});
			cfgDate.setIgnoreDefaultExcludes(false);
			cfgDate.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);   
			cfgDate.registerJsonValueProcessor(java.util.Date.class, jvp);
			cfgDate.registerJsonValueProcessor(java.sql.Date.class, jvp);
		}
		return cfgDate;
	}
	
	
	
	// 将POJO转换成JSON     
	public static String bean2json(Object object) { 
		String rValue=null;
	
		if(object==null){
			rValue = "null";
		}
		if(object instanceof String||object instanceof Long||object instanceof Integer){
			rValue= ""+object;
		}
		else{
			JSONObject jsonObject = JSONObject.fromObject(object, getSingletonCfg()); 
			rValue = jsonObject.toString();
		}
        return rValue;     
    }    
	
	// 将POJO转换成JSON     
	public static String bean2jsonCfg(Object object) {    
		String rValue=null;
		
		if(object==null){
			rValue = "null";
		}
		if(object instanceof String||object instanceof Long||object instanceof Integer){
			rValue= ""+object;
		}
		else{
			JSONObject jsonObject = JSONObject.fromObject(object, getCfg());  
			rValue = jsonObject.toString();
		}
           
        return rValue;     
    }    
	
	// 将POJO转换成JSON     
	public static String bean2jsonCfgDateTime(Object object) {    
		String rValue=null;
		
		if(object==null){
			rValue = "null";
		}
		if(object instanceof String||object instanceof Long||object instanceof Integer){
			rValue= ""+object;
		}
		else{
			JSONObject jsonObject = JSONObject.fromObject(object, getCfgDateTime());  
			rValue = jsonObject.toString();
		}
           
        return rValue;     
    }    
	
	// 将POJO转换成JSON     
	public static String bean2jsonCfgDate(Object object) {    
		String rValue=null;
		
		if(object==null){
			rValue = "null";
		}
		if(object instanceof String||object instanceof Long||object instanceof Integer){
			rValue= ""+object;
		}
		else{
			JSONObject jsonObject = JSONObject.fromObject(object, getCfgDate());  
			rValue = jsonObject.toString();
		}
           
        return rValue;     
    }    
	
	// 将JSON转换成POJO,其中beanClz为POJO的Class     
    public static Object json2Object(String json, Class beanClz) {     
        return JSONObject.toBean(JSONObject.fromObject(json), beanClz);     
    }  
    
 // 将JSON转换成POJO,其中beanClz为POJO的Class     
    public static Object json2ObjectCfg(String json, Class beanClz) {     
        return JSONObject.toBean(JSONObject.fromObject(json), beanClz, getCfg());     
    }  
    // 将JSON转换成POJO,其中beanClz为POJO的Class     
    public static Object json2ObjectCfgDateTime(String json, Class beanClz) {     
        return JSONObject.toBean(JSONObject.fromObject(json), beanClz, getCfgDateTime());     
    }  
}
