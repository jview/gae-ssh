package org.esblink.common.service.impl;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Map;

public abstract interface TemplateEngine
{
  public abstract String evaluate(String paramString, Map<String, Object> paramMap);

  public abstract void render(Reader paramReader, Map<String, Object> paramMap, Writer paramWriter)
    throws IOException;

  public abstract void render(String paramString, Map<String, Object> paramMap, Writer paramWriter)
    throws IOException;
}


 
 
