package org.esblink.common.util;
 
 public final class StringUtils
 {
   public static String convert(String s)
   {
     String str = s.replace("\r\n", "\n").replace("\r", "\n");
     String[] split = str.split("\n");
     str = "";
     for (int i = 0; i < split.length; i++) {
       str = str + split[i] + (i == split.length - 1 ? "" : "\\\\n");
     }
     return str;
   }
 
   public static boolean isNotEmpty(String s)
   {
     return (s != null) && (!"".equals(s));
   }
 
   public static String initialString(String name)
   {
     if (name == null) return "";
     String s = name.trim();
     if (!"".equals(s)) {
       String i = s.substring(0, 1);
       s = i.toUpperCase() + s.substring(1);
     }
     return s;
   }
 }


 
 
