package org.esblink.common.service;

import java.io.File;

public abstract interface IMailSender
{
  public abstract void sendText(String paramString1, String paramString2, String paramString3);

  public abstract void sendText(String paramString1, String paramString2, String paramString3, File[] paramArrayOfFile);

  public abstract void sendText(String[] paramArrayOfString, String paramString1, String paramString2);

  public abstract void sendText(String[] paramArrayOfString, String paramString1, String paramString2, File[] paramArrayOfFile);

  public abstract void sendHtml(String paramString1, String paramString2, String paramString3);

  public abstract void sendHtml(String paramString1, String paramString2, String paramString3, File[] paramArrayOfFile);

  public abstract void sendHtml(String[] paramArrayOfString, String paramString1, String paramString2);

  public abstract void sendHtml(String[] paramArrayOfString, String paramString1, String paramString2, File[] paramArrayOfFile);

  public abstract void sendMail(IMail paramIMail);
}


 
 
