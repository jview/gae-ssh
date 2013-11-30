package org.esblink.common.service.impl;
 
 import org.esblink.common.service.ICoder;
 
 public abstract class AbstractCoder extends AbstractEncoder
   implements ICoder
 {
   public String decode(String src)
   {
     return new String(decode(src.toCharArray()));
   }
 }


 
 
