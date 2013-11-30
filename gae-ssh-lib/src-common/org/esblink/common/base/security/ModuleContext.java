 package org.esblink.common.base.security;
 
 import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.esblink.common.base.cache.CacheManager;
import org.esblink.common.base.cache.ICache;
import org.esblink.common.base.context.ApplicationContext;
import org.esblink.common.base.domain.IModule;
import org.esblink.common.base.domain.ModuleApplyType;
import org.esblink.common.base.domain.ModuleType;
import org.esblink.common.util.TreeNode;
 
 public final class ModuleContext
 {
   private static ModuleContext context = new ModuleContext();
   private static final AssembleTreeUtil treeUtil = new AssembleTreeUtil();
   private static final Log logger = LogFactory.getLog(ModuleContext.class);
   private ICache cache;
 
   private ModuleContext()
   {
     this.cache = CacheManager.getInstance().getCache("_module_cache_region");
   }
 
   public static Map<String, Map> getModuleCacheMap()
   {
     String appCode = ApplicationContext.getContext().getApplication().getName();
     Map modulesMap = (Map)context.cache.getData(appCode);
 
     return modulesMap;
   }
 
   public static Map<String, IModule> getApplicationURLModules()
   {
     Map modulesMap = getModuleCacheMap();
     if (null == modulesMap) {
       logger.error("can not load module cache from ehcache storage.", new RuntimeException("can not load module cache from ehcache storage."));
     }
 
     Map modules = (Map)modulesMap.get("URL");
 
     return modules;
   }
 
   public static Map<String, IModule> getApplicationCodeModules()
   {
     Map modulesMap = getModuleCacheMap();
     if (null == modulesMap) {
       logger.error("can not load module cache from ehcache storage.", new RuntimeException("can not load module cache from ehcache storage."));
     }
 
     Map modules = (Map)modulesMap.get("CODE");
 
     return modules;
   }
 
   public static Map<Long, IModule> getApplicationModule()
   {
     Map modulesMap = getModuleCacheMap();
     if (null == modulesMap) {
       logger.error("can not load module cache from ehcache storage.", new RuntimeException("can not load module cache from ehcache storage."));
     }
 
     Map modules = (Map)modulesMap.get("ID");
     return modules;
   }
 
   public static Map<String, IModule> getApplications()
   {
     Map modulesMap = getModuleCacheMap();
     if (null == modulesMap) {
       logger.error("can not load module cache from ehcache storage.", new RuntimeException("can not load module cache from ehcache storage."));
     }
 
     Map modules = (Map)modulesMap.get("APPLICATION");
 
     return modules;
   }
 
   public static IModule getModule(Long id)
   {
     Map modules = getApplicationModule();
     if (modules == null)
       throw new RuntimeException("not found modules from cache ");
     return (IModule)modules.get(id);
   }
   
   public static List<IModule> getModuleByParent(Long parentId)
   {
     Map modules = getApplicationModule();
     if (modules == null)
       throw new RuntimeException("not found modules from cache ");
     Collection<IModule> list=modules.values();
     List<IModule> res=new ArrayList();
     for(IModule m:list){
    	 if(m.getParent()!=null && m.getParent().getId().longValue()==parentId.longValue()){
    		 res.add(m);
    	 }
    	 
     }
     return res;
//     return (IModule)modules.get(id);
   }
 
   public static IModule getModule(String url)
   {
     if ((null == url) || ("".equals(url.trim())))
       return null;
     Map modules = getApplicationURLModules();
     if (null != modules)
       return (IModule)modules.get(url);
     return null;
   }
 
   public static IModule getModuleByCode(String code) {
     if ((null == code) || ("".equals(code.trim())))
       return null;
     Map modules = getApplicationCodeModules();
     if (null != modules)
       return (IModule)modules.get(code);
     return null;
   }
 
   public static Collection<IModule> filter(Collection<IModule> modules)
   {
     TreeabledUtil root = treeUtil.computerTree(modules);
     if (root == null) {
       logger.error("root is null!!!");
       return new ArrayList(0);
     }
     return root.export();
   }
 
   static class TreeabledUtil //extends DefaultMutableTreeNode
   {
	   public static final Enumeration<TreeNode> EMPTY_ENUMERATION = new Enumeration() {
			public boolean hasMoreElements() {
				return false;
			}

			public TreeNode nextElement() {
				throw new NoSuchElementException("No more elements");
			}
		};
	 transient protected Object	userObject;
	 protected Vector children;
     private TreeabledUtil(IModule module)
     {
       setUserObject(module);
     }
     public TreeabledUtil(){
    	 
     }
 
     public Collection<IModule> export()
     {
       List modules = new ArrayList();
       Enumeration enumer = children();
       int count=0;
       while (enumer.hasMoreElements()) {
    	   count++;
         TreeabledUtil tu = (TreeabledUtil)enumer.nextElement();
         IModule module = (IModule)tu.getUserObject();
         Traversal(module, tu);
         modules.add(module);
       }
//       System.out.println("----export-modules="+modules+" count="+count);
       Collections.sort(modules, new ModuleComparator());
       return modules;
     }
     public Enumeration children() {
 		if (children == null)
 			return EMPTY_ENUMERATION;
 		else
 			return children.elements();
 	}
 
     private void Traversal(IModule m, TreeabledUtil treeNode)
     {
       List modules = new ArrayList();
       int size = 0;
       Enumeration enumer = treeNode.children();
       
      List<IModule> mList= ModuleContext.getModuleByParent(m.getId());
      for (IModule mo:mList) {
//   	   
        TreeabledUtil tu = new TreeabledUtil(mo);
        IModule module = (IModule)tu.getUserObject();
//        System.out.println("--------child="+module.getCode());
        Traversal(module, tu);
        modules.add(module);
        size++;
      }
//       while (enumer.hasMoreElements()) {
//    	   
//         TreeabledUtil tu = (TreeabledUtil)enumer.nextElement();
//         IModule module = (IModule)tu.getUserObject();
//         System.out.println("--------child="+module.getCode());
//         Traversal(module, tu);
//         modules.add(module);
//         size++;
//       }
       if (size == 0) {
         m.setChildren(null);
       } else {
         Collections.sort(modules, new ModuleComparator());
         m.setChildren(modules);
       }
     }

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}

	public Object getUserObject() {
		return userObject;
	}

	public Vector getChildren() {
		return children;
	}

	public void setChildren(Vector children) {
		this.children = children;
	}
	
	public void add(TreeabledUtil selfNode){
		if (children == null)
			children = new Vector();
//		System.out.println("-------add treeNode="+selfNode);
		this.children.add(selfNode);
	}
     
   }
 
   static class AssembleTreeUtil
   {
     public ModuleContext.TreeabledUtil computerTree(Collection<IModule> untreeabled)
     {
//    	 System.out.println("---------untreeabled="+untreeabled.size()+" application="+ModuleType.APPLICATION.ordinal());
       Map tmpTreeLink = new HashMap();
       TreeabledUtil treeNode=new TreeabledUtil();
       ModuleContext.TreeabledUtil rootNode = null;
       boolean isChild=false;
       for (IModule module : untreeabled) {
         if ((ModuleType.APPLICATION == module.getType()) || ((ModuleApplyType.WEB == module.getApplyType()) && (ModuleType.MENU == module.getType())))
         {
           ModuleContext.TreeabledUtil selfNode = (ModuleContext.TreeabledUtil)tmpTreeLink.get(module.getId());
           if (null == selfNode) {
        	   isChild=false;
        	   for (IModule m : untreeabled) {
        		   if(module.getParent()!=null && m.getId().longValue()==module.getParent().getId().longValue()){
        			   isChild=true;
        			   break;
        		   }
        	   }
        	   if(!isChild){
             selfNode = new ModuleContext.TreeabledUtil(module);
             treeNode.add(selfNode);
        	   }
//             tmpTreeLink.put(module.getId(), selfNode);
           }
           IModule parent = module.getParent();
           if (null == parent) {
             rootNode = selfNode;
           }
           else if (module.equals(parent)) {
             rootNode = selfNode;
           }
           else if (ModuleType.APPLICATION == module.getType()) {
             rootNode = selfNode;
           }
           else {
//        	   System.out.println("------else="+tmpTreeLink.size());
//             ModuleContext.TreeabledUtil parentNode = (ModuleContext.TreeabledUtil)tmpTreeLink.get(parent.getId());
//             if (null == parentNode) {
//               parentNode = new ModuleContext.TreeabledUtil(parent);
//               tmpTreeLink.put(parent.getId(), parentNode);
//               
//             }
//             System.out.println("------else="+tmpTreeLink.size()+" node="+((IModule)parentNode.getUserObject()).getCode());
//             
//             parentNode.add(selfNode);
           }
         }
       }
       return treeNode;
     }
   }
 }


 
 
