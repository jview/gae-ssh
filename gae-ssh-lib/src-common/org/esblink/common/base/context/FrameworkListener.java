package org.esblink.common.base.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
 
 public class FrameworkListener
   implements ServletContextListener
 {
	 private Logger log4 = Logger.getLogger(FrameworkListener.class);
	 public static boolean isLoad=false;
   public void contextInitialized(ServletContextEvent paramServletContextEvent)
   {
//	   Thread t1 = null;
//		t1=new Thread(new FrameworkTaskThread("initSys", paramServletContextEvent.getServletContext()));t1.start();
	   if(!isLoad){
		   ApplicationContext.init(paramServletContextEvent.getServletContext());
		   isLoad=true;
	   }
	   log4.debug("-----contextInitialized-isLoad="+isLoad);
   }
 
   public void contextDestroyed(ServletContextEvent paramServletContextEvent)
   {
     ApplicationContext.destroy();
   }
 }