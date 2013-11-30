 package org.esblink.common.base.context;
 
 import java.util.Collections;
 import java.util.List;
 
 public final class Application extends Assembly
 {
   private final List<Module> modules;
   private final List<String> actionConfigs;
   private final List<String> beansConfigs;
   private final List<String> mappingConfigs;
 
   public Application(String name, String version, List<Module> modules, List<String> actionConfigs, List<String> beansConfigs, List<String> mappingConfigs)
   {
     super(name, version);
     this.modules = (modules == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(modules));
 
     this.actionConfigs = (actionConfigs == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(actionConfigs));
 
     this.beansConfigs = (beansConfigs == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(beansConfigs));
 
     this.mappingConfigs = (mappingConfigs == null ? Collections.EMPTY_LIST : Collections.unmodifiableList(mappingConfigs));
   }
 
   public List<Module> getModules()
   {
     return this.modules;
   }
 
   public Module getModule(String moduleName)
   {
     if (moduleName != null)
       for (Module module : this.modules)
         if (moduleName.equals(module.getName()))
           return module;
     return null;
   }
 
   public List<String> getActionConfigs()
   {
     return this.actionConfigs;
   }
 
   public List<String> getBeansConfigs() {
     return this.beansConfigs;
   }
 
   public List<String> getMappingConfigs() {
     return this.mappingConfigs;
   }
 }


 
 
