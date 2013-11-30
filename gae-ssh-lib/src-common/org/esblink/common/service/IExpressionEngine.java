package org.esblink.common.service;

import java.util.Map;

public abstract interface IExpressionEngine
{
  public abstract Object evaluate(String paramString, Map<String, Object> paramMap);
}


 
 
