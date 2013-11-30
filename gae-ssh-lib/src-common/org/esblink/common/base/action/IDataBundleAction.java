package org.esblink.common.base.action;

import org.esblink.common.base.DataBundle;


public interface IDataBundleAction
{
  public void setRequestDataBundle(DataBundle paramDataBundle);

  public DataBundle getResponseDataBundle();
}

