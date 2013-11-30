package org.esblink.common.base.context;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;

import org.apache.struts2.config.StrutsXmlConfigurationProvider;
import org.apache.struts2.dispatcher.Dispatcher;
import org.apache.struts2.dispatcher.FilterDispatcher;

import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.struts2.gae.dispatcher.GaeDispatcher;

public class GaeFilterDispatcher extends FilterDispatcher {
	 private List<ConfigurationProvider> providers = new ArrayList();
	protected Dispatcher createDispatcher(FilterConfig filterConfig) {
		Map<String, String> params = new HashMap();
		for (Enumeration e = filterConfig.getInitParameterNames(); e
				.hasMoreElements();) {
			String name = (String) e.nextElement();
			String value = filterConfig.getInitParameter(name);
			params.put(name, value);
		}
		 List<String> moduleConfigs = ApplicationContext.getContext().getApplication().getActionConfigs();
		 String configs = params.get("config");
		    for (String mc : moduleConfigs) {
		    	configs+=","+mc;
		      this.providers.add(new StrutsXmlConfigurationProvider(mc, true, ApplicationContext.getContext().getServletContext()));
		    }
		    if(configs.endsWith(",")){
		    	configs=configs.substring(0, configs.length()-1);
		    }
		    params.put("config", configs);
		return new GaeDispatcher(filterConfig.getServletContext(), params);
	}
}