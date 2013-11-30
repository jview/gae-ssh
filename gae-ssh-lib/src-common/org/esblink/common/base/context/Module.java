package org.esblink.common.base.context;
 
 public final class Module extends Assembly
 {
   private String packageName = "org.esblink.module.";
   private String directory = "org/esblink/module/";
 
   public Module(String paramString1, String paramString2) {
     super(paramString1, paramString2);
   }
 
   public String getPackage()
   {
     return this.packageName + getName();
   }
 
   public String getDirectory()
   {
     return this.directory + getName();
   }
 
   public String getMetadataDirectory()
   {
     return getDirectory() + "/META-INF";
   }
 
   public String getConfigDirectory()
   {
     return getMetadataDirectory() + "/config";
   }
 
   public String getI18nDirectory()
   {
     return getMetadataDirectory() + "/i18n";
   }
 
   public String getPagesDirectory()
   {
     return getMetadataDirectory() + "/pages";
   }
 
   public String getImagesDirectory()
   {
     return getMetadataDirectory() + "/images";
   }
 
   public String getScriptsDirectory()
   {
     return getMetadataDirectory() + "/scripts";
   }
 
   public String getStylesDirectory()
   {
     return getMetadataDirectory() + "/styles";
   }
 
   public String getPagesExportDirectory()
   {
     return "/pages/" + getName();
   }
 
   public String getImagesExportDirectory()
   {
     return "/images/" + getName();
   }
 
   public String getScriptsExportDirectory()
   {
     return "/scripts/" + getName();
   }
 
   public String getStylesExportDirectory()
   {
     return "/styles/" + getName();
   }
 
   public String getMappingConfig()
   {
     return getConfigDirectory() + "/mapping.xml";
   }
 
   public String getBeansConfig()
   {
     return getConfigDirectory() + "/beans.xml";
   }
 
   public String getActionConfig()
   {
     return getConfigDirectory() + "/action.xml";
   }
 
   public String getI18nBaseName()
   {
     return getI18nDirectory() + "/messages";
   }
 
   public String getReadme()
   {
     return getMetadataDirectory() + "/readme.txt";
   }
 
   public String getPackageName() {
     return this.packageName;
   }
 
   public void setPackageName(String paramString) {
     this.packageName = paramString;
   }
 
   public void setDirectory(String paramString) {
     this.directory = paramString;
   }
 }


 
 
