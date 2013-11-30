package org.esblink.common.service.impl;
 
 import org.esblink.common.service.IRandomizer;
 
 public class MonogramRandomizer
   implements IRandomizer
 {
   private static final int DEFAULT_LENGTH = 6;
   private int length = 6;
   private static final int min = 0;
   private static final int max = 35;
   private final String[] strs = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "A", "S", "D", "F", "G", "H", "J", "K", "L", "Z", "X", "C", "V", "B", "N", "M" };
 
   public void setLength(int length)
   {
     this.length = length;
   }
 
   public String generate() {
     if (this.length < 1) {
       this.length = 6;
     }
     StringBuffer result = new StringBuffer();
     for (int i = 0; i < this.length; i++) {
       String str = this.strs[getRandomIndex()];
       result.append(str);
     }
     return result.toString();
   }
 
   private int getRandomIndex()
   {
     return (int)(36.0D * Math.random()) + 0;
   }
 }


 
 
