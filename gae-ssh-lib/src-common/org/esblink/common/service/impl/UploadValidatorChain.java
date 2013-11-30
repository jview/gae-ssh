package org.esblink.common.service.impl;
 
 import java.io.File;
import java.io.IOException;
import java.util.List;

import org.esblink.common.service.IUploadValidator;
 
 public class UploadValidatorChain
   implements IUploadValidator
 {
   private List<IUploadValidator> uploadValidators;
 
   public UploadValidatorChain(List<IUploadValidator> uploadValidators)
   {
     this.uploadValidators = uploadValidators;
   }
 
   public boolean isAccept(File uploadFile) throws IOException {
     if ((this.uploadValidators != null) && (this.uploadValidators.size() > 0)) {
       int i = 0; for (int n = this.uploadValidators.size(); i < n; i++) {
         UploadValidator uploadValidator = (UploadValidator)this.uploadValidators.get(i);
         if (!uploadValidator.isAccept(uploadFile)) {
           return false;
         }
       }
     }
     return true;
   }
 }


 
 
