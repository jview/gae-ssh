package org.esblink.common.service.impl;
 
 import java.io.File;
import java.io.IOException;

import org.esblink.common.service.IUploadValidator;
 
 public class SizeUploadValidator
   implements IUploadValidator
 {
   public boolean isAccept(File uploadFile)
     throws IOException
   {
     return false;
   }
 }


 
 
