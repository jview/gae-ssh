package org.esblink.common.service.impl;
 
 import java.io.File;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.service.IMail;
 
 public class SmtpMailSender extends AbstractMailSender
 {
   private static Log log = LogFactory.getLog(SmtpMailSender.class);
   private static final String ISAUTH = "true";
   private static final String BASE64 = "B";
   private static final String ENCODE = "utf-8";
   private static final String MAIL_SMTP_HOST = "mail.smtp.host";
   private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
   private static final String MAIL_SMTP_PROTOCOL = "mail.smtp.protocol";
   private static final String SMTP = "smtp";
   private static final String CONTENT_TYPE_HTML = "text/html;charset=utf-8";
   private static final String CONTENT_TYPE_TEXT = "text/plain;charset=utf-8";
   private final Properties props;
   private final String from;
   private final String nickname;
   private final String username;
   private final String password;
 
   public SmtpMailSender(String host, String from, String username, String password)
   {
     this(host, from, username, username, password);
   }
 
   public SmtpMailSender(String host, String from, String nickname, String username, String password)
   {
     this.from = from;
     this.nickname = nickname;
     this.username = username;
     this.password = password;
     this.props = new Properties();
     this.props.put("mail.smtp.host", host);
     this.props.put("mail.smtp.auth", "true");
     this.props.put("mail.smtp.protocol", "smtp");
   }
 
   protected void send(String[] to, boolean isHtml, String subject, String body, File[] files)
   {
     if ((to == null) || (to.length < 1)) {
       log.error("收件人邮箱不能为空!");
       return;
     }
     if (body == null) {
       body = "";
     }
     if (subject == null) {
       subject = "";
     }
 
     Session session = Session.getDefaultInstance(this.props, getAuth());
     Message msg = new MimeMessage(session);
     try
     {
       msg.setContent(getContent(body, files, isHtml));
       msg.setSubject(convertToBase64(subject));
       msg.setSentDate(new Date());
       InternetAddress fromAddress = null;
 
       if (this.nickname != null)
         fromAddress = new InternetAddress(this.from, convertToBase64(this.nickname));
       else {
         fromAddress = new InternetAddress(this.from);
       }
       msg.setFrom(fromAddress);
       msg.setRecipients(Message.RecipientType.BCC, getReceivers(to));
       Transport.send(msg);
     } catch (Exception e) {
       log.error(e.getMessage(), e);
     }
   }
 
   private InternetAddress[] getReceivers(String[] to)
     throws AddressException
   {
     InternetAddress[] receivers = new InternetAddress[to.length];
     for (int i = 0; i < receivers.length; i++) {
       receivers[i] = new InternetAddress(to[i]);
     }
     return receivers;
   }
 
   private Multipart getContent(String body, File[] files, boolean isHtml)
     throws MessagingException
   {
     Multipart mutilpart = new MimeMultipart();
     mutilpart.addBodyPart(getMessagePart(body, isHtml));
     if ((files != null) && (files.length > 0)) {
       for (int i = 0; i < files.length; i++) {
         BodyPart mbp = new MimeBodyPart();
         FileDataSource fds = new FileDataSource(files[i]);
         mbp.setDataHandler(new DataHandler(fds));
         mbp.setFileName(fds.getName());
         mutilpart.addBodyPart(mbp);
       }
     }
     return mutilpart;
   }
 
   private BodyPart getMessagePart(String body, boolean isHtml)
     throws MessagingException
   {
     BodyPart bp = new MimeBodyPart();
     if (isHtml)
       bp.setContent(body, "text/html;charset=utf-8");
     else {
       bp.setContent(body, "text/plain;charset=utf-8");
     }
     return bp;
   }
 
   private String convertToBase64(String text)
     throws Exception
   {
     text = MimeUtility.encodeText(text, "utf-8", "B");
     return text;
   }
 
   private Authenticator getAuth()
   {
     Authenticator auth = new Authenticator() {
       protected PasswordAuthentication getPasswordAuthentication() {
         return new PasswordAuthentication(SmtpMailSender.this.username, SmtpMailSender.this.password);
       }
     };
     return auth;
   }
 
   public void sendMail(IMail mail)
   {
   }
 }


 
 
