package org.esblink.common.service;

import java.io.File;
import java.util.Date;

public abstract interface IMail
{
  public abstract String getFrom();

  public abstract void setFrom(String paramString);

  public abstract String[] getTo();

  public abstract void setTo(String[] paramArrayOfString);

  public abstract String[] getCc();

  public abstract void setCc(String[] paramArrayOfString);

  public abstract String[] getBcc();

  public abstract void setBcc(String[] paramArrayOfString);

  public abstract String getSubject();

  public abstract void setSubject(String paramString);

  public abstract String getBody();

  public abstract void setBody(String paramString);

  public abstract boolean isHtml();

  public abstract void setHtml(boolean paramBoolean);

  public abstract File[] getAttachments();

  public abstract void setAttachments(File[] paramArrayOfFile);

  public abstract boolean isNew();

  public abstract void setNew(boolean paramBoolean);

  public abstract Date getSentDate();

  public abstract void setSentDate(Date paramDate);

  public abstract Date getReceivedDate();

  public abstract void setReceivedDate(Date paramDate);

  public abstract String getImportance();

  public abstract void setImportance(String paramString);

  public abstract String getLanguage();

  public abstract void setLanguage(String paramString);

  public abstract String getEncoding();

  public abstract void setEncoding(String paramString);

  public abstract String getContentType();

  public abstract void setContentType(String paramString);
}


 
 
