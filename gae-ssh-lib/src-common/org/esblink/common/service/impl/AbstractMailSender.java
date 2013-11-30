package org.esblink.common.service.impl;
 
 import java.io.File;

import org.esblink.common.service.IMailSender;
 
 public abstract class AbstractMailSender
   implements IMailSender
 {
   public void sendMail(Mail mail)
   {
     send(mail.getTo(), mail.isHtml(), mail.getSubject(), mail.getBody(), mail.getAttachments());
   }
 
   public void sendText(String to, String subject, String body) {
     send(new String[] { to }, false, subject, body, new File[0]);
   }
 
   public void sendText(String to, String subject, String body, File[] attachments) {
     send(new String[] { to }, false, subject, body, attachments);
   }
 
   public void sendText(String[] to, String subject, String body) {
     send(to, false, subject, body, new File[0]);
   }
 
   public void sendText(String[] to, String subject, String body, File[] attachments) {
     send(to, false, subject, body, attachments);
   }
 
   public void sendHtml(String to, String subject, String body) {
     send(new String[] { to }, true, subject, body, new File[0]);
   }
 
   public void sendHtml(String to, String subject, String body, File[] attachments) {
     send(new String[] { to }, true, subject, body, attachments);
   }
 
   public void sendHtml(String[] to, String subject, String body) {
     send(to, true, subject, body, new File[0]);
   }
 
   public void sendHtml(String[] to, String subject, String body, File[] attachments) {
     send(to, true, subject, body, attachments);
   }
 
   protected abstract void send(String[] paramArrayOfString, boolean paramBoolean, String paramString1, String paramString2, File[] paramArrayOfFile);
 }


 
 
