package org.esblink.common.service;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.esblink.common.util.TreeNode;

public abstract interface IDocumentor
{
  public abstract void exportElement(Object paramObject, File paramFile);

  public abstract Object importElement(File paramFile);

  public abstract void exportTree(TreeNode<?> paramTreeNode, File paramFile);

  public abstract TreeNode<?> importTree(File paramFile);

  public abstract void exportTable(List<?> paramList, File paramFile);

  public abstract void exportTable(List<?> paramList, Map<String, String> paramMap, File paramFile);

  public abstract List<?> importTable(File paramFile, Class<?> paramClass);

  public abstract List<?> importTable(File paramFile, Map<String, String> paramMap, Class<?> paramClass);

  public abstract List<Map<String, ?>> importTable(File paramFile);

  public abstract List<Map<String, ?>> importTable(File paramFile, Map<String, String> paramMap);

  public abstract void exportElement(Object paramObject, OutputStream paramOutputStream);

  public abstract Object importElement(InputStream paramInputStream);

  public abstract void exportTree(TreeNode<?> paramTreeNode, OutputStream paramOutputStream);

  public abstract TreeNode<?> importTree(InputStream paramInputStream);

  public abstract void exportTable(List<?> paramList, OutputStream paramOutputStream);

  public abstract void exportTable(List<?> paramList, Map<String, String> paramMap, OutputStream paramOutputStream);

  public abstract List<?> importTable(InputStream paramInputStream, Class<?> paramClass);

  public abstract List<?> importTable(InputStream paramInputStream, Map<String, String> paramMap, Class<?> paramClass);

  public abstract List<Map<String, ?>> importTable(InputStream paramInputStream);

  public abstract List<Map<String, ?>> importTable(InputStream paramInputStream, Map<String, String> paramMap);

  public abstract List<?> importTable(InputStream paramInputStream, Map<String, String> paramMap, Class<?> paramClass, int paramInt1, int paramInt2);
}


 
 
