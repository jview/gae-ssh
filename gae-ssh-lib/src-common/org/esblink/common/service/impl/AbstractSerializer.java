package org.esblink.common.service.impl;

 import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.esblink.common.service.ISerializer;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
 
 public abstract class AbstractSerializer
   implements ISerializer
 {
   public byte[] serialize(Serializable obj)
     throws IOException
   {
     ByteArrayOutputStream bo = new ByteArrayOutputStream();
     serialize(obj, bo);
     return bo.toByteArray();
   }
   BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
   public Serializable deserialize(byte[] data) throws IOException {
     return deserialize(new ByteArrayInputStream(data));
   }
 
   public void serialize(Serializable obj, File file) throws IOException {
//	   BlobKey blobKey = new BlobKey(file);
//     blobstoreService.serve(blobKey, res);
	   
//     serialize(obj, new FileOutputStream(file));
   }
 
   public Serializable deserialize(File file) throws IOException {
     return deserialize(new FileInputStream(file));
   }
 }


 
 
