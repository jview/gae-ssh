package org.esblink.common.base.context;
 
 public abstract class Assembly
 {
   private final String name;
   private final String version;
 
   public Assembly(String name, String version)
   {
     this.name = name;
     this.version = version;
   }
 
   public String getName()
   {
     return this.name;
   }
 
   public String getVersion()
   {
     return this.version;
   }
 }


 
 
