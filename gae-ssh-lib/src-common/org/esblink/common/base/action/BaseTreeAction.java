package org.esblink.common.base.action;
 
 public abstract class BaseTreeAction extends BaseAction
 {
   private static final long serialVersionUID = 7458491287067973376L;
   private String node;
 
   public String getNode()
   {
     return this.node;
   }
 
   public void setNode(String node) {
     this.node = node;
     if (null != node) {
       this.node = node.trim();
       if (this.node.matches("^[0-9]+$"))
         setId(Long.valueOf(this.node));
     }
   }
 }


 
 
