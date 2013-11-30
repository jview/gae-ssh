package org.esblink.common.service.impl;
 
 import java.io.File;
import java.io.IOException;

import org.esblink.common.service.IFileManager;
 
 public class LocalFileManager
   implements IFileManager
 {
   private String rootPath;
 
   public LocalFileManager()
   {
   }
 
   public LocalFileManager(String rootPath)
     throws IOException
   {
     this.rootPath = rootPath;
     createRootDir();
   }
 
   public String getRootPath() {
     return this.rootPath;
   }
 
   public void setRootPath(String rootPath) throws IOException {
     this.rootPath = rootPath;
     createRootDir();
   }
 
   public File createDirectory(String directoryName) throws IOException {
     if (isEmpty(directoryName)) {
       throw new IOException("目录名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + directoryName;
     File dir = new File(dirPath);
     if (!dir.exists()) {
       dir.mkdirs();
     }
     else if (!dir.isDirectory()) {
       throw new IOException("已有与此目录同名的文件存在，不能创建此目录！");
     }
 
     return dir;
   }
 
   public File createFile(String fileName) throws IOException {
     if (isEmpty(fileName)) {
       throw new IOException("文件名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + fileName;
     File file = new File(dirPath);
     if (!file.exists()) {
       file.createNewFile();
     }
     else if (!file.isFile()) {
       throw new IOException("已有与此文件同名的目录存在，不能创建此文件！");
     }
 
     return file;
   }
 
   public void deleteDirectory(String directoryName) throws IOException {
     if (isEmpty(directoryName)) {
       throw new IOException("目录名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + directoryName;
     File dir = new File(dirPath);
     if (!dir.exists()) {
       throw new IOException("此目录不存在!");
     }
     if (!dir.isDirectory()) {
       throw new IOException("此路径不是目录!");
     }
     dir.delete();
   }
 
   public void deleteFile(String fileName)
     throws IOException
   {
     if (isEmpty(fileName)) {
       throw new IOException("文件名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + fileName;
     File file = new File(dirPath);
     if (!file.exists()) {
       throw new IOException("此文件不存在!");
     }
     if (!file.isFile()) {
       throw new IOException("此路径不是文件!");
     }
     file.delete();
   }
 
   public File getDirectory(String dirName)
     throws IOException
   {
     if (isEmpty(dirName)) {
       throw new IOException("目录名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + dirName;
     File dir = new File(dirPath);
     if (!dir.exists()) {
       throw new IOException("此目录不存在!");
     }
     if (!dir.isDirectory()) {
       throw new IOException("此路径不是目录!");
     }
 
     return dir;
   }
 
   public File getFile(String fileName) throws IOException {
     if (isEmpty(fileName)) {
       throw new IOException("文件名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + fileName;
     File file = new File(dirPath);
     if (!file.exists()) {
       throw new IOException("此文件不存在!");
     }
     if (!file.isFile()) {
       throw new IOException("此路径不是文件!");
     }
 
     return file;
   }
 
   public boolean isDirectoryExist(String directoryName) throws IOException {
     if (isEmpty(directoryName)) {
       throw new IOException("目录名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + directoryName;
     File dir = new File(dirPath);
     if (!dir.exists()) {
       return false;
     }
     if (!dir.isDirectory()) {
       return false;
     }
 
     return true;
   }
 
   public boolean isFileExist(String fileName) throws IOException {
     if (isEmpty(fileName)) {
       throw new IOException("文件名称不能为空!");
     }
     String dirPath = this.rootPath + File.separator + fileName;
     File file = new File(dirPath);
     if (!file.exists()) {
       return false;
     }
     if (!file.isFile()) {
       return false;
     }
 
     return true;
   }
 
   private boolean isEmpty(String str) {
     if ((str == null) || (str.length() < 1)) {
       return true;
     }
     return false;
   }
 
   private void createRootDir() throws IOException
   {
     if (isEmpty(this.rootPath)) {
       throw new IOException("根路径不能为空！");
     }
     File rootDir = new File(this.rootPath);
     if (rootDir.exists()) {
       if (!rootDir.isDirectory())
         throw new IOException("已有与此根目录同名的文件存在，不能创建根目录！");
     }
     else
       rootDir.mkdirs();
   }
 }


 
 
