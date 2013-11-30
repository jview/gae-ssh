package org.esblink.common.service.impl;
 
 import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import org.esblink.common.service.IDocumentor;
import org.esblink.common.util.TreeNode;
 
 public class XMLDocumentor
   implements IDocumentor
 {
   public void exportElement(Object element, File target)
   {
   }
 
   public void exportElement(Object element, OutputStream out)
   {
   }
 
   public void exportTable(List<?> entities, File target)
   {
   }
 
   public void exportTable(List<?> entities, Map<String, String> columns, File target)
   {
   }
 
   public void exportTable(List<?> entities, OutputStream out)
   {
   }
 
   public void exportTable(List<?> entities, Map<String, String> columns, OutputStream out)
   {
   }
 
   public void exportTree(TreeNode<?> element, File target)
   {
   }
 
   public void exportTree(TreeNode<?> element, OutputStream out)
   {
   }
 
   public Object importElement(File target)
   {
     return null;
   }
 
   public Object importElement(InputStream in)
   {
     return null;
   }
 
   public List<?> importTable(File source, Class<?> cls)
   {
     return null;
   }
 
   public List<?> importTable(File source, Map<String, String> columns, Class<?> cls)
   {
     return null;
   }
 
   public List<Map<String, ?>> importTable(File source)
   {
     return null;
   }
 
   public List<Map<String, ?>> importTable(File source, Map<String, String> columns)
   {
     return null;
   }
 
   public List<?> importTable(InputStream in, Class<?> cls)
   {
     return null;
   }
 
   public List<?> importTable(InputStream in, Map<String, String> columns, Class<?> cls)
   {
     return null;
   }
 
   public List<Map<String, ?>> importTable(InputStream in)
   {
     return null;
   }
 
   public List<Map<String, ?>> importTable(InputStream in, Map<String, String> columns)
   {
     return null;
   }
 
   public TreeNode<?> importTree(File target)
   {
     return null;
   }
 
   public TreeNode<?> importTree(InputStream in)
   {
     return null;
   }
 
   public List<?> importTable(InputStream in, Map<String, String> columns, Class<?> cls, int startIndex, int limit)
   {
     return null;
   }
 }


 
 
