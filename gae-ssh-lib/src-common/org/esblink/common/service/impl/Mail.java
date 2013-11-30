package org.esblink.common.service.impl;
 
 import java.io.File;
import java.util.Date;

import org.esblink.common.service.IMail;
 
 public class Mail
   implements IMail
 {
   private String from;
   private String[] to;
   private String[] cc;
   private String[] bcc;
   private String subject;
   private String body;
   private boolean html;
   private File[] attachments;
   private boolean isNew;
   private Date sentDate;
   private Date receivedDate;
   private String importance;
   private String language;
   private String encoding;
   private String contentType;
 
   public Mail()
   {
   }
 
   public Mail(String[] to, String subject, String body, boolean html, File[] attachments)
   {
     this.to = to;
     this.subject = subject;
     this.body = body;
     this.html = html;
     this.attachments = attachments;
   }
 
   public Mail(String[] to, String[] cc, String[] bcc, String subject, String body, boolean html, File[] attachments)
   {
     this.to = to;
     this.cc = cc;
     this.bcc = bcc;
     this.subject = subject;
     this.body = body;
     this.html = html;
     this.attachments = attachments;
   }
 
   public String getFrom()
   {
     return this.from;
   }
 
   public void setFrom(String from) {
     this.from = from;
   }
 
   public String[] getTo()
   {
     return this.to;
   }
 
   public void setTo(String[] to) {
     this.to = to;
   }
 
   public String[] getCc()
   {
     return this.cc;
   }
 
   public void setCc(String[] cc) {
     this.cc = cc;
   }
 
   public String[] getBcc()
   {
     return this.bcc;
   }
 
   public void setBcc(String[] bcc) {
     this.bcc = bcc;
   }
 
   public String getSubject()
   {
     return this.subject;
   }
 
   public void setSubject(String subject) {
     this.subject = subject;
   }
 
   public String getBody()
   {
     return this.body;
   }
 
   public void setBody(String body) {
     this.body = body;
   }
 
   public boolean isHtml()
   {
     return this.html;
   }
 
   public void setHtml(boolean html) {
     this.html = html;
   }
 
   public File[] getAttachments()
   {
     return this.attachments;
   }
 
   public void setAttachments(File[] attachments) {
     this.attachments = attachments;
   }
 
   public boolean isNew()
   {
     return this.isNew;
   }
 
   public void setNew(boolean isNew) {
     this.isNew = isNew;
   }
 
   public Date getSentDate()
   {
     return this.sentDate;
   }
 
   public void setSentDate(Date sentDate) {
     this.sentDate = sentDate;
   }
 
   public Date getReceivedDate()
   {
     return this.receivedDate;
   }
 
   public void setReceivedDate(Date receivedDate) {
     this.receivedDate = receivedDate;
   }
 
   public String getImportance()
   {
     return this.importance;
   }
 
   public void setImportance(String importance) {
     this.importance = importance;
   }
 
   public String getLanguage()
   {
     return this.language;
   }
 
   public void setLanguage(String language) {
     this.language = language;
   }
 
   public String getEncoding()
   {
     return this.encoding;
   }
 
   public void setEncoding(String encoding) {
     this.encoding = encoding;
   }
 
   public String getContentType()
   {
     return this.contentType;
   }
 
   public void setContentType(String contentType) {
     this.contentType = contentType;
   }
 }


 
 
