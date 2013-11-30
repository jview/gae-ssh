 package org.esblink.common.util;
 
 import java.io.PrintStream;
 import org.apache.log4j.Logger;
 
 public class Monitor
 {
   private static Logger logger = Logger.getLogger(Monitor.class);
 
   private static final ThreadLocal local = new ThreadLocal();
 
   private String uri = "";
 
   private long requestBegin = 0L;
 
   private long actionBegin = 0L;
 
   private long bizBegin = 0L;
 
   private long daoBegin = 0L;
 
   private long sqlBegin = 0L;
 
   private long requestTime = 0L;
 
   private long actionTime = 0L;
 
   private long bizTime = 0L;
 
   private long daoTime = 0L;
 
   private long sqlTime = 0L;
 
   private static Monitor getMonitor()
   {
     Monitor monitor = (Monitor)local.get();
     if (monitor == null) {
       monitor = new Monitor();
       local.set(monitor);
     }
     return monitor;
   }
 
   public static void beginRequest(String uri)
   {
     Monitor monitor = new Monitor();
     local.set(monitor);
     monitor.requestBegin = System.currentTimeMillis();
     monitor.uri = uri;
   }
 
   public static void endRequest() {
     Monitor monitor = getMonitor();
     monitor.requestTime = (System.currentTimeMillis() - monitor.requestBegin);
     monitor.requestBegin = 0L;
     if (logger.isDebugEnabled()) {
       logger.debug("\"" + monitor.uri + "\",\"" + monitor.requestTime + "\",\"" + monitor.actionTime + "\",\"" + monitor.bizTime + "\",\"" + monitor.daoTime + "\",\"" + monitor.sqlTime + "\"");
     }
 
     System.out.println("\"" + monitor.uri + "\",\"" + monitor.requestTime + "\",\"" + monitor.actionTime + "\",\"" + monitor.bizTime + "\",\"" + monitor.daoTime + "\",\"" + monitor.sqlTime + "\"");
 
     local.set(null);
   }
 
   public static void beginAction() {
     Monitor monitor = getMonitor();
     monitor.actionBegin = System.currentTimeMillis();
   }
 
   public static void endAction() {
     Monitor monitor = getMonitor();
     monitor.actionTime = (System.currentTimeMillis() - monitor.actionBegin);
     monitor.actionBegin = 0L;
   }
 
   public static void beginBiz() {
     Monitor monitor = getMonitor();
     monitor.bizBegin = System.currentTimeMillis();
   }
 
   public static void endBiz() {
     Monitor monitor = getMonitor();
     monitor.bizTime += System.currentTimeMillis() - monitor.bizBegin;
     monitor.bizBegin = 0L;
   }
 
   public static void beginDao() {
     Monitor monitor = getMonitor();
     monitor.daoBegin = System.currentTimeMillis();
   }
 
   public static void endDao() {
     Monitor monitor = getMonitor();
     monitor.daoTime += System.currentTimeMillis() - monitor.daoBegin;
     monitor.daoBegin = 0L;
   }
 
   public static void beginSql() {
     Monitor monitor = getMonitor();
     monitor.sqlBegin = System.currentTimeMillis();
   }
 
   public static void endSql() {
     Monitor monitor = getMonitor();
     monitor.sqlTime += System.currentTimeMillis() - monitor.sqlBegin;
     monitor.sqlBegin = 0L;
   }
 }


 
 
