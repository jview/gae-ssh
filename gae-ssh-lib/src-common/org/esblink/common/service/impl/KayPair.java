package org.esblink.common.service.impl;
 
 public class KayPair
 {
   private final char[] publicKey;
   private final char[] privateKey;
 
   public KayPair(char[] publicKey, char[] privateKey)
   {
     this.publicKey = publicKey;
     this.privateKey = privateKey;
   }
 
   public char[] getPublicKey() {
     return this.publicKey;
   }
 
   public char[] getPrivateKey() {
     return this.privateKey;
   }
 }


 
 
