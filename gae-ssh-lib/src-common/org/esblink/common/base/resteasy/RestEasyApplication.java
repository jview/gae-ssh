package org.esblink.common.base.resteasy;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

public class RestEasyApplication extends Application {
	private static Logger log4 = Logger.getLogger(RestEasyApplication.class);
    HashSet<Object> singletons = new HashSet<Object>();
    HashSet<Class<?>> set = new HashSet<Class<?>>();

   public RestEasyApplication() {
//       singletons.add(new Library());
	  
	   Map map = BeansCacheRest.getServiceMap();
	   log4.debug("------restEasy---init--"+map.size());;
	   Iterator itor = map.entrySet().iterator();
		Entry ent = null;
		Class cls = null;
		while(itor.hasNext()){
			ent = (Entry)itor.next();
//			log4.info("-----key="+ent.getKey()+" value="+ent.getValue());
			if(ent.getValue()!=null){
				cls = (Class)ent.getValue();				
				set.add(cls);
				Object obj =null;
				try {
					obj= cls.newInstance();
					singletons.add(obj);
					log4.info("registService service "+ent.getKey()+" cls="+cls);;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					log4.warn("registService service "+ent.getKey(), e);
				
				}
//				log4.info("------rest registService "+ent.getKey());;
			}
		}
		log4.debug("------restEasy---end--");;
   }

   @Override
   public Set<Class<?>> getClasses() {
       HashSet<Class<?>> set = new HashSet<Class<?>>();
       // set.add(Library.class);
       return set;
   }

   @Override
   public Set<Object> getSingletons() {
       return singletons;
   }
}