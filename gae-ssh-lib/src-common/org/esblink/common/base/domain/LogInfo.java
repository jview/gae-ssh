package org.esblink.common.base.domain;

import java.util.Collection;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggerFactory;
import org.esblink.common.util.JsonInfo;

/**
 * 带json的日志处理
 * @author chenjh
 *
 */
public class LogInfo  {
	
	
	public Logger logger;
	
	private String jsonInfo = null;
	/**
	 * 对象类型json转换
	 * @param jsonObj
	 * @return
	 */
	public String object2JsonString(Object jsonObj){
		if(jsonObj!=null){
			if(jsonObj instanceof Collection){
				return this.array2JsonString((Collection)jsonObj);
			}
			jsonInfo = " json:\n"+JsonInfo.bean2jsonCfg(jsonObj);
			jsonInfo = jsonInfo.replaceAll(",", ",\n");
		}
		else{
			jsonInfo = " json:"+null;
		}		
		return jsonInfo;
	}
	/**
	 * 数组对象类型json转换
	 * @param jsonObj
	 * @return
	 */
	public String array2JsonString(Object[] jsonObjs){
		if(jsonObjs==null){
			jsonInfo = " json:"+null;
			return jsonInfo;
		}
		jsonInfo = " json length:" + jsonObjs.length + ":\n";
		if (jsonObjs.length < 30) {
			int count=0;
			for (Object obj : jsonObjs) {
				count++;
				jsonInfo = jsonInfo +count+ JsonInfo.bean2jsonCfg(obj) + "\n\n";
			}
		}
		jsonInfo = jsonInfo.replaceAll(",", ",\n");
		return jsonInfo;
	}
	/**
	 * Collection容器类型json转换
	 * @param jsonObj
	 * @return
	 */
	public String array2JsonString(Collection jsonObjs){
		if(jsonObjs==null){
			jsonInfo = " json:null";
			return jsonInfo;
		}
		jsonInfo = " json size:" + jsonObjs.size() + ":\n";
		if (jsonObjs.size() < 30) {
			int count=0;
			for (Object obj : jsonObjs) {
				count++;
				jsonInfo = jsonInfo +count+ JsonInfo.bean2jsonCfg(obj) + "\n\n";
			}
		}
		jsonInfo = jsonInfo.replaceAll(",", ",\n");
		return jsonInfo;
	}
	
	
	/**
	 * 写日志
	 * @param level
	 * @param message
	 * @param jsonObj
	 */
	public void write(Level level, Object message, Object jsonObj){		
		String value =null;
		if(jsonObj!=null){
			if(jsonObj instanceof String){
				value = ""+jsonObj;
			}
			else{
				value = this.object2JsonString(jsonObj);
			}
		}
		String log_info=message+" "+ value!=null?value:null;		
		
		if(level==Level.WARN){
			this.logger.warn(log_info);
		}
		else if(level==Level.DEBUG){
			this.logger.debug(log_info);
		}
		else if(level==Level.INFO){
			this.logger.info(log_info);
		}
		else if(level==Level.ERROR){
			this.logger.error(log_info);
		}
		else if(level==Level.FATAL){
			this.logger.fatal(log_info);
		}	
		else if(level==Level.TRACE){
			this.logger.trace(log_info);
		}	
	}
	
	
	/**
	 * 写日志
	 * @param level
	 * @param message
	 * @param jsonObjs
	 */
	public void write(Level level, Object message, Object[] jsonObjs){
		String log_info=message+" "+ this.array2JsonString(jsonObjs);					
		if(level==Level.WARN){
			this.logger.warn(log_info);
		}
		else if(level==Level.DEBUG){
			this.logger.debug(log_info);
		}
		else if(level==Level.INFO){
			this.logger.info(log_info);
		}
		else if(level==Level.ERROR){
			this.logger.error(log_info);
		}
		else if(level==Level.FATAL){
			this.logger.fatal(log_info);
		}	
		else if(level==Level.TRACE){
			this.logger.trace(log_info);
		}	
	}

	public void debug(Object message, Object jsonObj) {		
		this.write(Level.DEBUG, message, jsonObj);
	}

	public void debug(Object message, Object[] jsonObjs) {
		this.write(Level.DEBUG, message, jsonObjs);
	}
	
	public void error(Object message, Object jsonObj) {
		this.write(Level.ERROR, message, jsonObj);
	}

	public void error(Object message, Object[] jsonObjs) {
		this.write(Level.ERROR, message, jsonObjs);
	}
	
	public void info(Object message, Object jsonObj) {
		this.write(Level.INFO, message, jsonObj);
	}

	public void info(Object message, Object[] jsonObjs) {
		this.write(Level.INFO, message, jsonObjs);
	}
	
	public void warn(Object message, Object jsonObj) {
		this.write(Level.WARN, message, jsonObj);
	}

	public void warn(Object message, Object[] jsonObjs) {
		this.write(Level.WARN, message, jsonObjs);
	}

	
	public void fatal(Object message, Object jsonObj) {
		this.write(Level.FATAL, message, jsonObj);
	}

	public void fatal(Object message, Object[] jsonObjs) {
		this.write(Level.FATAL, message, jsonObjs);
	}
	
	public void trace(Object message, Object jsonObj) {
		this.write(Level.TRACE, message, jsonObj);
	}

	public void trace(Object message, Object[] jsonObjs) {
		this.write(Level.TRACE, message, jsonObjs);
	}
	

	public static LogInfo getLogger(String name) {
		LogInfo logInfo = new LogInfo();
		logInfo.logger=LogManager.getLogger(name);
		return logInfo;
	}

	public static LogInfo getLogger(Class clazz) {
		LogInfo logInfo = new LogInfo();
		logInfo.logger=LogManager.getLogger(clazz.getName());
		return logInfo;
		
	}

	public static LogInfo getRootLogger() {
		LogInfo logInfo = new LogInfo();
		
		logInfo.logger=LogManager.getRootLogger();
		return logInfo;		
	}

	public static LogInfo getLogger(String name, LoggerFactory factory) {
		LogInfo logInfo = new LogInfo();
		logInfo.logger=LogManager.getLogger(name, factory);
		return logInfo;
	
	}

	
	

}
