package org.esblink.common.service.impl;
 
 import java.beans.XMLDecoder;
 import java.beans.XMLEncoder;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.OutputStream;
 import java.io.Serializable;
 
 public class XmlSerializer extends AbstractSerializer
 {
   public Serializable deserialize(InputStream in)
     throws IOException
   {
     XMLDecoder xd = new XMLDecoder(in);
     try {
       return (Serializable)xd.readObject();
     } finally {
       xd.close();
     }
   }
 
   public void serialize(Serializable obj, OutputStream out) throws IOException
   {
     XMLEncoder xe = new XMLEncoder(out);
     try {
       xe.writeObject(obj);
       xe.flush();
     } finally {
       xe.close();
     }
   }
 }


 
 
