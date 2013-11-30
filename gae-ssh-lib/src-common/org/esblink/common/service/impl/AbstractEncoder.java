package org.esblink.common.service.impl;
 
 import org.esblink.common.service.IEncoder;
 
 public abstract class AbstractEncoder
   implements IEncoder
 {
   public String encode(String src)
   {
     return new String(encode(src.getBytes()));
   }
 }


 
 
