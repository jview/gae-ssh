package org.esblink.common.service.impl;
 
 import java.util.Random;

import org.esblink.common.service.IRandomizer;
 
 public class NumberRandomizer
   implements IRandomizer
 {
   private Random random;
   private static final int DEFAULT_LENGTH = 6;
   private int length = 6;
 
   public NumberRandomizer()
   {
     this.random = new Random(System.currentTimeMillis());
   }
 
   public void setLength(int length)
   {
     this.length = length;
   }
 
   public String generate() {
     return randomString(this.length);
   }
 
   private String randomString(int length) {
     StringBuffer buffer = new StringBuffer();
     for (int i = 0; i < length; i++) {
       buffer.append(randomByMax(9));
     }
     return buffer.toString();
   }
 
   private int randomByMax(int max) {
     if (max <= 0) {
       return 0;
     }
     int num = this.random.nextInt(max + 4);
     while ((num < 1) || (num > max)) {
       num = this.random.nextInt(max + 4);
     }
     return num - 1;
   }
 }


 
 
