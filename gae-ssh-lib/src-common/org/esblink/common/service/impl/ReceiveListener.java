package org.esblink.common.service.impl;

import java.util.List;

import org.esblink.common.service.IMail;

public abstract interface ReceiveListener
{
  public abstract void onReceive(List<IMail> paramList);
}


 
 
