package org.esblink.common.service;

import java.util.List;

public abstract interface IMailReceiver
{
  public abstract List<IMail> receive();

  public abstract void addReceiveListener(IReceiveListener paramIReceiveListener);

  public abstract void removeReceiveListener(IReceiveListener paramIReceiveListener);
}


 
 
