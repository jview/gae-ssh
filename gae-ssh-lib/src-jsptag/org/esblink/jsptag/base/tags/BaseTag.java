 package org.esblink.jsptag.base.tags;
 
 import javax.servlet.jsp.tagext.TagSupport;
 import org.slf4j.Logger;
 import org.slf4j.LoggerFactory;
 
 public abstract class BaseTag extends TagSupport
 {
   protected Logger log = LoggerFactory.getLogger(getClass());
 }


 
 
