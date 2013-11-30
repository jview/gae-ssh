 package org.esblink.common.base.security;
 
 import java.util.Comparator;

import org.esblink.common.base.domain.IModule;
 
 public class ModuleComparator
   implements Comparator<IModule>
 {
   public int compare(IModule source, IModule target)
   {
     if (source == null)
       return 1;
     if (target == null) {
       return -1;
     }
     Integer selfIndex = source.getSort();
     Integer targIndex = target.getSort();
     Long selfId = source.getId();
     Long targId = target.getId();
 
     int self = 0;
     int targ = 0;
     if (selfId.equals(targId)) {
       return 0;
     }
     if (null != selfIndex) {
       self = selfIndex.intValue();
     }
     if (null != targIndex) {
       targ = targIndex.intValue();
     }
     if (self == targ) {
       return 1;
     }
     return self - targ;
   }
 }


 
 
