package org.esblink.module.framework.loader;

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.esblink.common.base.context.ApplicationContext;


public class ContextTaskThread implements Runnable{
	private Logger log4 = Logger.getLogger(ContextTaskThread.class);
	private String moduleCode="contextTaskThread";
	private String modelCode = getClass().getSimpleName();

	private String infoType;
	private String contextPath;
	private ServletContextEvent event;
	
	public ContextTaskThread(String infoType, ServletContextEvent event){
		this.event=event;
		this.infoType=infoType;
		this.contextPath=event.getServletContext().getServerInfo();
	}
	public ContextTaskThread(){
		
	}
	public void run(){
		log4.info("------contextTask--infoType="+infoType);
		if("initSys".equals(infoType)){
			ApplicationContext.init(event.getServletContext());
		}
	}
	
	
	
}
