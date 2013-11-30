package org.esblink.common.service.impl;

import java.util.Map;

public abstract interface ExpressionEngine
{
  public abstract Object evaluate(String paramString, Map<String, Object> paramMap);
}


 
 
