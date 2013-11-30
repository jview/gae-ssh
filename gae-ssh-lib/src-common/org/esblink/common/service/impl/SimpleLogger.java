package org.esblink.common.service.impl;
 
 import org.esblink.common.service.ILogger;
 
 public class SimpleLogger
   implements ILogger
 {
   public void debug(String msg)
   {
     System.out.println(msg);
   }
 
   public void debug(String msg, Throwable e) {
     System.out.println(msg);
     if (e != null)
       e.printStackTrace();
   }
 
   public void info(String msg) {
     System.out.println(msg);
   }
 
   public void warn(String msg) {
     System.err.println(msg);
   }
 
   public void warn(String msg, Throwable e) {
     System.err.println(msg);
     if (e != null)
       e.printStackTrace();
   }
 
   public void error(String msg) {
     System.err.println(msg);
   }
 
   public void error(String msg, Throwable e) {
     System.err.println(msg);
     if (e != null)
       e.printStackTrace();
   }
 
   public boolean isDebugEnabled() {
     return true;
   }
 
   public boolean isInfoEnabled() {
     return true;
   }
 
   public boolean isWarnEnabled() {
     return true;
   }
 
   public boolean isErrorEnabled() {
     return true;
   }
 
   public boolean isFatalEnabled() {
     return true;
   }
 }


 
 
