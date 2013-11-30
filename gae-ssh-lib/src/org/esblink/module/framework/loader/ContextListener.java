package org.esblink.module.framework.loader;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;





/**
 * 启动显示数据库信息，并做连接测试
 * @author chenjh
 *
 */  

public class ContextListener implements ServletContextListener {
	private static Logger log4 = Logger.getLogger(ContextListener.class);
	public void contextInitialized(ServletContextEvent event) {
//		String protocol=ServletActionContext.getRequest().getProtocol();
		log4.info("-------serverInfo="+event.getServletContext().getServerInfo());
		String contextPath=event.getServletContext().getContextPath();
		log4.info("-------contextPath="+contextPath);
		Thread t1 = null;
		t1=new Thread(new ContextTaskThread("initSys", event));t1.start();
		t1=new Thread(new ContextTaskThread("resetCodeTableJs", event));t1.start();
		t1=new Thread(new ContextTaskThread("wsRegist", event));t1.start();
		
	}	
	
	
	

	public void contextDestroyed(ServletContextEvent event) {
		log4.info("----application stop--");
	}
	
	
	

	
}