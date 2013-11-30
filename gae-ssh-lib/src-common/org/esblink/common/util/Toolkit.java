 package org.esblink.common.util;
 
 import java.util.ArrayList;
import java.util.List;

import org.esblink.common.service.ICoder;
import org.esblink.common.service.ICompresser;
import org.esblink.common.service.IConfigurator;
import org.esblink.common.service.IDocumentor;
import org.esblink.common.service.IEncoder;
import org.esblink.common.service.IExpressionEngine;
import org.esblink.common.service.IFileManager;
import org.esblink.common.service.IKeyPairGenerator;
import org.esblink.common.service.ILogger;
import org.esblink.common.service.IMailReceiver;
import org.esblink.common.service.IMailSender;
import org.esblink.common.service.IRandomizer;
import org.esblink.common.service.ISerializer;
import org.esblink.common.service.IUploadValidator;
import org.esblink.common.service.impl.AesCoder;
import org.esblink.common.service.impl.Base64Coder;
import org.esblink.common.service.impl.HtmlCoder;
import org.esblink.common.service.impl.JavaSerializer;
import org.esblink.common.service.impl.LocalFileManager;
import org.esblink.common.service.impl.Log4jLogger;
import org.esblink.common.service.impl.Md5Encoder;
import org.esblink.common.service.impl.MonogramRandomizer;
import org.esblink.common.service.impl.PdfDocumentor;
import org.esblink.common.service.impl.RasCoder;
import org.esblink.common.service.impl.SizeUploadValidator;
import org.esblink.common.service.impl.SmtpMailSender;
import org.esblink.common.service.impl.StringCoder;
import org.esblink.common.service.impl.TxtDocumentor;
import org.esblink.common.service.impl.TypeUploadValidator;
import org.esblink.common.service.impl.UploadValidatorChain;
import org.esblink.common.service.impl.UrlCoder;
import org.esblink.common.service.impl.UuidRandomizer;
import org.esblink.common.service.impl.XMLDocumentor;
 
 public class Toolkit
 {
   private static final Toolkit INSTANCE = new Toolkit();
   private ICoder base64Coder;
   private ICoder htmlCoder;
   private ICoder stringCoder;
   private ICoder urlCoder;
   private ICoder aesCoder;
   private ICoder rasCoder;
   private ICompresser compresser;
   private IConfigurator configurator;
   private IDocumentor txtDocumentor;
   private IDocumentor xmlDocumentor;
   private IDocumentor pdfDocumentor;
   private IDocumentor excelDocumentor;
   private IExpressionEngine expressionEngine;
   private IFileManager fileManager;
   private IKeyPairGenerator keyPairGenerator;
   private ILogger logger;
   private IMailReceiver mailReceiver;
   private IMailSender mailSender;
//   private IMessageReceiver messageReceiver;
//   private IMessageSender messageSender;
   private IRandomizer uuidRandomizer;
   private IRandomizer monogramRandomizer;
   private ISerializer serializer;
   private IUploadValidator uploadValidator;
   private static final IEncoder md5Encoder = new Md5Encoder();
 
   private Toolkit()
   {
     init();
   }
 
   private void init() {
     this.base64Coder = new Base64Coder();
     this.htmlCoder = new HtmlCoder();
     this.stringCoder = new StringCoder();
     this.urlCoder = new UrlCoder();
     this.aesCoder = new AesCoder();
     this.rasCoder = new RasCoder();
//     this.compresser = new ZipCompresser();
     this.txtDocumentor = new TxtDocumentor();
     this.xmlDocumentor = new XMLDocumentor();
     this.pdfDocumentor = new PdfDocumentor();
//     this.excelDocumentor = new ExcelDocumentor();
     this.fileManager = new LocalFileManager();
     this.logger = new Log4jLogger();
//     this.mailReceiver = new Pop3MailReceiver("localhost", "", "");
     this.mailSender = new SmtpMailSender("localhost", "", "", "");
//     this.messageReceiver = null;
//     this.messageSender = null;
     this.uuidRandomizer = new UuidRandomizer();
     this.monogramRandomizer = new MonogramRandomizer();
     this.serializer = new JavaSerializer();
 
     IUploadValidator size = new SizeUploadValidator();
     IUploadValidator type = new TypeUploadValidator();
     List validaties = new ArrayList();
     validaties.add(size);
     validaties.add(type);
     this.uploadValidator = new UploadValidatorChain(validaties);
   }
 
   public static ICoder getBase64Coder()
   {
     return INSTANCE.base64Coder;
   }
 
   public static ICoder getHtmlCoder()
   {
     return INSTANCE.htmlCoder;
   }
 
   public static ICoder getStringCoder()
   {
     return INSTANCE.stringCoder;
   }
 
   public static ICoder getUrlCoder()
   {
     return INSTANCE.urlCoder;
   }
 
   public ICoder getAesCoder()
   {
     return this.aesCoder;
   }
 
   public static ICoder getRasCoder()
   {
     return INSTANCE.rasCoder;
   }
 
   public static ICompresser getCompresser()
   {
     return INSTANCE.compresser;
   }
 
   public static IConfigurator getConfigurator() {
     return INSTANCE.configurator;
   }
 
   public static IDocumentor getTxtDocumentor()
   {
     return INSTANCE.txtDocumentor;
   }
 
   public static IDocumentor getXmlDocumentor()
   {
     return INSTANCE.xmlDocumentor;
   }
 
   public static IDocumentor getPdfDocumentor()
   {
     return INSTANCE.pdfDocumentor;
   }
 
   public static IDocumentor getExcelDocumentor()
   {
     return INSTANCE.excelDocumentor;
   }
 
   public static IExpressionEngine getExpressionEngine() {
     return INSTANCE.expressionEngine;
   }
 
   public static IFileManager getFileManager()
   {
     return INSTANCE.fileManager;
   }
 
   public static IKeyPairGenerator getKeyPairGenerator() {
     return INSTANCE.keyPairGenerator;
   }
 
   public static ILogger getLogger()
   {
     return INSTANCE.logger;
   }
 
   public static IMailReceiver getMailReceiver()
   {
     return INSTANCE.mailReceiver;
   }
 
   public static IMailSender getMailSender()
   {
     return INSTANCE.mailSender;
   }
 
//   public static IMessageReceiver getMessageReceiver()
//   {
//     return INSTANCE.messageReceiver;
//   }
 
//   public static IMessageSender getMessageSender()
//   {
//     return INSTANCE.messageSender;
//   }
 
   public static IRandomizer getUuidRandomizer()
   {
     return INSTANCE.uuidRandomizer;
   }
 
   public static IRandomizer getMonogramRandomizer()
   {
     return INSTANCE.monogramRandomizer;
   }
 
   public static ISerializer getSerializer()
   {
     return INSTANCE.serializer;
   }
 
   public static IUploadValidator getUploadValidator()
   {
     return INSTANCE.uploadValidator;
   }
 
   public void setBase64Coder(ICoder base64Coder) {
     this.base64Coder = base64Coder;
   }
 
   public void setHtmlCoder(ICoder htmlCoder) {
     this.htmlCoder = htmlCoder;
   }
 
   public void setStringCoder(ICoder stringCoder) {
     this.stringCoder = stringCoder;
   }
 
   public void setUrlCoder(ICoder urlCoder) {
     this.urlCoder = urlCoder;
   }
 
   public void setAesCoder(ICoder aesCoder) {
     this.aesCoder = aesCoder;
   }
 
   public void setRasCoder(ICoder rasCoder) {
     this.rasCoder = rasCoder;
   }
 
   public void setCompresser(ICompresser compresser) {
     this.compresser = compresser;
   }
 
   public void setConfigurator(IConfigurator configurator) {
     this.configurator = configurator;
   }
 
   public void setTxtDocumentor(IDocumentor txtDocumentor) {
     this.txtDocumentor = txtDocumentor;
   }
 
   public void setXmlDocumentor(IDocumentor xmlDocumentor) {
     this.xmlDocumentor = xmlDocumentor;
   }
 
   public void setPdfDocumentor(IDocumentor pdfDocumentor) {
     this.pdfDocumentor = pdfDocumentor;
   }
 
   public void setExcelDocumentor(IDocumentor excelDocumentor) {
     this.excelDocumentor = excelDocumentor;
   }
 
   public void setExpressionEngine(IExpressionEngine expressionEngine) {
     this.expressionEngine = expressionEngine;
   }
 
   public void setFileManager(IFileManager fileManager) {
     this.fileManager = fileManager;
   }
 
   public void setKeyPairGenerator(IKeyPairGenerator keyPairGenerator) {
     this.keyPairGenerator = keyPairGenerator;
   }
 
   public void setLogger(ILogger logger) {
     this.logger = logger;
   }
 
   public void setMailReceiver(IMailReceiver mailReceiver) {
     this.mailReceiver = mailReceiver;
   }
 
   public void setMailSender(IMailSender mailSender) {
     this.mailSender = mailSender;
   }
 
//   public void setMessageReceiver(IMessageReceiver messageReceiver) {
//     this.messageReceiver = messageReceiver;
//   }
 
//   public void setMessageSender(IMessageSender messageSender) {
//     this.messageSender = messageSender;
//   }
 
   public void setUuidRandomizer(IRandomizer uuidRandomizer) {
     this.uuidRandomizer = uuidRandomizer;
   }
 
   public void setMonogramRandomizer(IRandomizer monogramRandomizer) {
     this.monogramRandomizer = monogramRandomizer;
   }
 
   public void setSerializer(ISerializer serializer) {
     this.serializer = serializer;
   }
 
   public void setUploadValidator(IUploadValidator uploadValidator) {
     this.uploadValidator = uploadValidator;
   }
 
   public static IEncoder getMd5Encoder()
   {
     return md5Encoder;
   }
 }


 
 
