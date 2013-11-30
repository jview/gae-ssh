package org.esblink.common.service.impl;
 
 import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.esblink.common.service.ILogger;
 
 public class JdkLogger
   implements ILogger
 {
   private static Logger logger = Logger.getLogger(JdkLogger.class.getName());
 
   public JdkLogger(Handler handler, Formatter formatter) {
     handler.setFormatter(formatter);
     logger.addHandler(handler);
   }
 
   public void debug(String msg) {
     logger.log(Level.ALL, msg);
   }
 
   public void debug(String msg, Throwable e) {
     logger.log(Level.ALL, msg, e);
   }
 
   public void error(String msg) {
     logger.log(Level.SEVERE, msg);
   }
 
   public void error(String msg, Throwable e) {
     logger.log(Level.SEVERE, msg, e);
   }
 
   public void info(String msg) {
     logger.log(Level.INFO, msg);
   }
 
   public boolean isDebugEnabled() {
     return logger.isLoggable(Level.ALL);
   }
 
   public boolean isErrorEnabled() {
     return logger.isLoggable(Level.SEVERE);
   }
 
   public boolean isFatalEnabled() {
     return logger.isLoggable(Level.OFF);
   }
 
   public boolean isInfoEnabled() {
     return logger.isLoggable(Level.INFO);
   }
 
   public boolean isWarnEnabled() {
     return logger.isLoggable(Level.WARNING);
   }
 
   public void warn(String msg) {
     logger.log(Level.WARNING, msg);
   }
 
   public void warn(String msg, Throwable e) {
     logger.log(Level.WARNING, msg, e);
   }
 }


 
 
