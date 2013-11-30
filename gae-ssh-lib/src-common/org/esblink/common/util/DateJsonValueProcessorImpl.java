package org.esblink.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class DateJsonValueProcessorImpl implements JsonValueProcessor {
	private final Log log = LogFactory.getLog(DateJsonValueProcessorImpl.class);
	private String format = "yyyy-MM-dd hh:mm:ss";

	public DateJsonValueProcessorImpl(String datePattern) {   
		this.format = datePattern;   
    }   

	public Object processArrayValue(Object value, JsonConfig arg1) {
		return process(value);
	}

	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}
	
	private Object process(Object value){
		if(value==null){
//			log.info("json process value="+null);
			return null;
		}
		return new SimpleDateFormat(format).format((Date)value);
	}

//	public Object processArrayValue(Object value) {
//		// TODO Auto-generated method stub
//		return process(value);
//	}

//	public Object processObjectValue(String arg0, Object arg1) {
//		// TODO Auto-generated method stub
//		return new SimpleDateFormat(format).format((Date)value);
//	}
}
