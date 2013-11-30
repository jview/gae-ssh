package org.esblink.common.service.impl;
 
 import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.esblink.common.service.ILogger;
 
 public class Log4jLogger
   implements ILogger
 {
   private static Logger logger = Logger.getLogger(Log4jLogger.class);
 
   public void debug(String msg) {
     logger.debug(msg);
   }
 
   public void debug(String msg, Throwable e) {
     logger.debug(msg, e);
   }
 
   public void error(String msg) {
     logger.error(msg);
   }
 
   public void error(String msg, Throwable e) {
     logger.error(msg, e);
   }
 
   public void info(String msg) {
     logger.info(msg);
   }
 
   public boolean isDebugEnabled() {
     return logger.isDebugEnabled();
   }
 
   public boolean isErrorEnabled() {
     return logger.isEnabledFor(Level.ERROR);
   }
 
   public boolean isFatalEnabled() {
     return logger.isEnabledFor(Level.FATAL);
   }
 
   public boolean isInfoEnabled() {
     return logger.isInfoEnabled();
   }
 
   public boolean isWarnEnabled() {
     return logger.isEnabledFor(Level.WARN);
   }
 
   public void warn(String msg) {
     logger.warn(msg);
   }
 
   public void warn(String msg, Throwable e) {
     logger.warn(msg, e);
   }
 }


 
 
