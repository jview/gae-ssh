package org.esblink.common.service.impl;
 
 import java.util.ArrayList;
import java.util.List;

import org.esblink.common.service.IMail;
import org.esblink.common.service.IMailReceiver;
 
 public abstract class AbstractMailReceiver
   implements IMailReceiver
 {
   private final List<ReceiveListener> listeners = new ArrayList();
 
   public void addReceiveListener(ReceiveListener receiveListener) {
     this.listeners.add(receiveListener);
   }
 
   public void removeReceiveListener(ReceiveListener receiveListener) {
     this.listeners.remove(receiveListener);
   }
 
   protected void doReceive(List<IMail> mails)
   {
     int i = 0; for (int n = this.listeners.size(); i < n; i++) {
       ReceiveListener listener = (ReceiveListener)this.listeners.get(i);
       listener.onReceive(mails);
     }
   }
 }


 
 
