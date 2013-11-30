package org.esblink.common.service.impl;
 
 import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

import org.esblink.common.service.ITemplateEngine;
 
 public class VelocityTemplateEngine
   implements ITemplateEngine
 {
   public String evaluate(String template, Map<String, Object> variables)
   {
     return null;
   }
 
   public void render(String templatePath, Map<String, Object> variables, Writer out)
     throws IOException
   {
   }
 
   public void render(Reader templateReader, Map<String, Object> variables, Writer out)
     throws IOException
   {
   }
 }


 
 
