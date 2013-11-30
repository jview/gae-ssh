package org.esblink.common.service.impl;
 
 import java.io.BufferedInputStream;
 import java.io.BufferedOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.ObjectInputStream;
 import java.io.ObjectOutputStream;
 import java.io.OutputStream;
 import java.io.Serializable;
 
 public class JavaSerializer extends AbstractSerializer
 {
   public Serializable deserialize(InputStream in)
     throws IOException
   {
     ObjectInputStream oo = new ObjectInputStream(new BufferedInputStream(in));
     try {
       return (Serializable)oo.readObject();
     } catch (ClassNotFoundException e) {
       throw new IOException("序列化类文件找不到：" + e.getMessage());
     } finally {
       oo.close();
     }
   }
 
   public void serialize(Serializable obj, OutputStream out)
     throws IOException
   {
     ObjectOutputStream oo = new ObjectOutputStream(new BufferedOutputStream(out));
     try {
       oo.writeObject(obj);
       oo.flush();
     } finally {
       oo.close();
     }
   }
 }


 
 
