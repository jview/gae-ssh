 package org.esblink.common.service.impl;
 
 import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;


 
 public class MessageDigestAdapter extends AbstractEncoder
 {
   private final String arithmetic;
 
   public MessageDigestAdapter(String arithmetic)
   {
     this.arithmetic = arithmetic;
   }
 
   public char[] encode(byte[] src) {
     try {
       MessageDigest md5 = MessageDigest.getInstance(this.arithmetic);
       byte[] d = md5.digest(src);
       Base64 base64en = new Base64();
//       BASE64Encoder base64en = new BASE64Encoder();
       String result = new String(base64en.encode(d));
       return result.toCharArray();
     } catch (NoSuchAlgorithmException e) {
       throw new RuntimeException(e);
     }
   }
 }


 
 
