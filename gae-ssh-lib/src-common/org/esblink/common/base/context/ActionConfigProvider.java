package org.esblink.common.base.context;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.config.StrutsXmlConfigurationProvider;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;

public class ActionConfigProvider
  implements ConfigurationProvider
{
  Log logger = LogFactory.getLog(getClass());

  private List<ConfigurationProvider> providers = new ArrayList();

  public ActionConfigProvider() {
    List<String> moduleConfigs = ApplicationContext.getContext().getApplication().getActionConfigs();
    for (String mc : moduleConfigs) {
      this.providers.add(new StrutsXmlConfigurationProvider(mc, true, ApplicationContext.getContext().getServletContext()));
    }
    this.logger.info("excuted ActionConfigProvider()");
  }

  public void destroy() {
    for (ConfigurationProvider provider : this.providers)
      provider.destroy();
    this.logger.info("excuted destroy()");
  }

  public void init(Configuration arg0) throws ConfigurationException {
    for (ConfigurationProvider provider : this.providers)
      provider.init(arg0);
    this.logger.info("excuted init()");
  }

  public void loadPackages() throws ConfigurationException {
    for (ConfigurationProvider provider : this.providers)
      provider.loadPackages();
    this.logger.info("excuted loadPackages()");
  }

  public boolean needsReload() {
    for (ConfigurationProvider provider : this.providers)
      if (provider.needsReload()) {
        this.logger.info("excuted reload(true)");
        return true;
      }
    this.logger.info("excuted reload(false)");
    return false;
  }

  public void register(ContainerBuilder arg0, LocatableProperties arg1) throws ConfigurationException
  {
    for (ConfigurationProvider provider : this.providers)
      provider.register(arg0, arg1);
    this.logger.info("excuted register()");
  }
}




