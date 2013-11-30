package org.esblink.common.base.context;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;


public class FrameworkTaskThread implements Runnable{
	private Logger log4 = Logger.getLogger(FrameworkTaskThread.class);
	private String moduleCode="frameworkTaskThread";
	private String modelCode = getClass().getSimpleName();

	private String infoType;
	private String contextPath;
	private ServletContext servletContext;
	
	public FrameworkTaskThread(String infoType, ServletContext servletContext){
		this.servletContext=servletContext;
		this.infoType=infoType;
	}
	public FrameworkTaskThread(){
		
	}
	public void run(){
		log4.info("------frameworkTask--infoType="+infoType);
		if("initSys".equals(infoType)){
			ApplicationContext.init(servletContext);
		}
	}
	
	
	
}
