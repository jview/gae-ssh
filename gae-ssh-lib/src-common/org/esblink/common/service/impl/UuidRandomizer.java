package org.esblink.common.service.impl;
 
 import java.util.UUID;

import org.esblink.common.service.IRandomizer;
 
 public class UuidRandomizer
   implements IRandomizer
 {
   public String generate()
   {
     return UUID.randomUUID().toString();
   }
 }


 
 
