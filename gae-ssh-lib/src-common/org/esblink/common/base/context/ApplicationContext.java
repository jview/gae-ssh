/*** Eclipse Class Decompiler plugin, copyright (c) 2012 Chao Chen (cnfree2000@hotmail.com) ***/

 package org.esblink.common.base.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.servlet.ServletContext;

import org.esblink.common.base.i18n.IGlobalMessageSource;
import org.esblink.common.base.i18n.IMessageSource;
import org.esblink.common.base.i18n.MessageSource;
import org.esblink.common.base.i18n.MessageSourceCache;
import org.esblink.common.base.i18n.PropertiesResourceBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ApplicationContext extends BaseContext {

	private static boolean isIdpLink = true;
	private static Logger log = LoggerFactory.getLogger(ApplicationContext.class);
	private List sfmList;
	private List esbList;
	private static ApplicationContext context;
	private ServletContext servletContext;
	private Application application;
	private PathMatchingResourcePatternResolver resolver;
	private static final String ESBLINK_PACKAGE = "com/idplink/";
	private static final String MODULE_PACKAGE = "com/idplink/module/";
	private static final String ACTION_CONFIG = "/META-INF/config/action.xml";
	private static final String BEANS_CONFIG = "/META-INF/config/beans.xml";
	private static final String MAPPING_CONFIG = "/META-INF/config/mapping.xml";
	private static final String MENU_MESSAGE_PREFIX = "menu";
	private static final String MENU_MESSAGE_SUFFIX = ".properties";
	private static final String MENU_MESSAGE_PATH = "/META-INF/i18n/menu*.properties";
	private Map menuMessages;
	public static final String GLOBAL_MESSAGE_BASE_NAME = "/WEB-INF/i18n/messages";
	private static final String SYSTEM_CONFIG_NAME = "/WEB-INF/sysconfig.properties";
	private Properties systemConfig;

	public static ApplicationContext getContext(){
		
		if (context == null)
			try {
				throw new Exception(
						(new StringBuilder())
								.append("please config web.xml, add: \n<listener>\n\t<listener-class>")
								.append(FrameworkListener.class.getName())
								.append("</listener-class>\n</listener>\n")
								.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		else
			return context;
	}

	public static void init(ServletContext servletContext) {
		context = new ApplicationContext(servletContext);
	}

	public static void destroy() {
		context = null;
	}

	private ApplicationContext(ServletContext servletContext) {
		sfmList = new ArrayList();
		esbList = new ArrayList();
		
		resolver = new PathMatchingResourcePatternResolver();
		systemConfig = new Properties();
		this.servletContext = servletContext;
		initSystemConfig();
		application = initApplication();
		
	}

	public ServletContext getServletContext() {
		return servletContext;
	}

	public Application getApplication() {
		return application;
	}

	private Application initApplication() {
//		servletContext.log((new StringBuilder())
//				.append("[Framework] init application: ")
//				.append(servletContext.getServletContextName()).toString());
		log.info((new StringBuilder())
				.append("[Framework] application location: ")
				.append(servletContext.getRealPath("/")).toString());
		List moduleNames = new ArrayList();
		List<String> actionConfigs = initConfigs("/META-INF/config/action.xml",
				moduleNames);
		List<String> beansConfigs = initConfigs("/META-INF/config/beans.xml",
				moduleNames);
		List<String> mappingConfigs = initConfigs("/META-INF/config/mapping.xml",
				moduleNames);
		List modules = initModules(moduleNames);
		menuMessages = initMessages();
		boolean isTest = false;
		if (isTest) {
			log.info("======running on test===");
		} else {
			log.info("======running on run2==="+servletContext.getServletContextName());
			MessageSourceCache.getInstance().setGlobalMsPath(
					servletContext.getRealPath("/WEB-INF/i18n/"));
//			exportResources(modules);
		}
		mappingConfigs.clear();
		beansConfigs.clear();
		
		for(String action:actionConfigs){
			log.info((new StringBuilder())
					.append("[Framework] add config: ")
					.append(action).toString());
		}
		return new Application(servletContext.getServletContextName(), "1.0",
				modules, actionConfigs, beansConfigs, mappingConfigs);
//		return null;
	}

	public IMessageSource getMenuMessageSource() {
		return getMenuMessageSource(UserContext.getContext().getLocale());
	}

	public IMessageSource getMenuMessageSource(Locale locale) {
		ResourceBundle resourceBundle = null;
		if (locale != null) {
			resourceBundle = (ResourceBundle) menuMessages
					.get((new StringBuilder()).append("menu_")
							.append(locale.getLanguage()).append("_")
							.append(locale.getCountry()).append(".properties")
							.toString());
			if (resourceBundle == null)
				resourceBundle = (ResourceBundle) menuMessages
						.get((new StringBuilder()).append("menu_")
								.append(locale.getLanguage())
								.append(".properties").toString());
		}
		if (resourceBundle == null)
			resourceBundle = (ResourceBundle) menuMessages
					.get("menu.properties");
		return new MessageSource(resourceBundle, locale);
	}

	private Map initMessages() {
		Map results = new HashMap();
		try {
			Resource resources1[] = resolver
					.getResources("classpath*:org/esblink/**/META-INF/i18n/menu*.properties");
			Resource resources2[] = resolver
					.getResources("classpath*:com/esblink/**/META-INF/i18n/menu*.properties");
			Resource resources3[] = resolver
					.getResources("classpath*:com/idplink/**/META-INF/i18n/menu*.properties");
			int length = resources1.length;
			int length2 = length + resources2.length;
			Resource resources[] = new Resource[length2 + resources3.length];
			for (int i = 0; i < resources.length; i++)
				if (i < length)
					resources[i] = resources1[i];
				else if (i < length2)
					resources[i] = resources2[i - length];
				else
					resources[i] = resources3[i - length2];

			Resource arr$[] = resources;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++) {
				Resource resource = arr$[i$];
				try {
					String name = resource.getFilename();
					Properties properties = (Properties) results.get(name);
					if (properties == null) {
						properties = new Properties();
						results.put(name, properties);
					}
					properties.load(resource.getInputStream());
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				}
			}

			initGlobalMenuResource(results);
		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		Map messages = new HashMap();
		Iterator i$;
		java.util.Map.Entry entry;
		for (i$ = results.entrySet().iterator(); i$.hasNext(); messages.put(
				entry.getKey(),
				new PropertiesResourceBundle((Properties) entry.getValue())))
			entry = (java.util.Map.Entry) i$.next();

		i$ = messages.entrySet().iterator();
		do {
			if (!i$.hasNext())
				break;
			java.util.Map.Entry entry2 = (java.util.Map.Entry) i$.next();
			String key = (String) entry2.getKey();
			PropertiesResourceBundle resourceBundle = (PropertiesResourceBundle) messages
					.get(key);
			int i = key.lastIndexOf('_');
			if (i > -1) {
				String parentKey = key.substring(0, i);
				ResourceBundle parentResourceBundle = (ResourceBundle) messages
						.get((new StringBuilder()).append(parentKey)
								.append(".properties").toString());
				if (parentResourceBundle != null) {
					resourceBundle.setParent(parentResourceBundle);
				} else {
					i = parentKey.lastIndexOf('_');
					if (i > -1) {
						parentKey = parentKey.substring(0, i);
						parentResourceBundle = (ResourceBundle) messages
								.get((new StringBuilder()).append(parentKey)
										.append(".properties").toString());
						if (parentResourceBundle != null)
							resourceBundle.setParent(parentResourceBundle);
					}
				}
			}
		} while (true);
		return messages;
	}

	private void initGlobalMenuResource(Map resources) {
		String path = servletContext.getRealPath("/WEB-INF/i18n/");
		File root = new File(path);
		if(!root.exists()){
			log.warn("------initGlobalMenuResource--path="+path+" not found");
			return;
		}
		File menuFiles[] = root.listFiles(new FilenameFilter() {

//			final ApplicationContext this$0;
//			final ApplicationContext this$0$;

			public boolean accept(File dir, String name) {
				return name.startsWith("menu") && name.endsWith(".properties");
			}

			// JavaClassFileOutputException: Invalid index accessing method local variables table of <init>
		});
		File arr$[] = menuFiles;
		int len$ = arr$.length;
		for (int i$ = 0; i$ < len$; i$++) {
			File menuFile = arr$[i$];
			try {
				String name = menuFile.getName();
				Properties properties = (Properties) resources.get(name);
				if (properties == null) {
					properties = new Properties();
					resources.put(name, properties);
				}
				properties.load(new FileInputStream(menuFile));
			} catch (Exception e) {
				log.warn(e.getMessage(), e);
			}
		}

	}

	private List initConfigs(String configName, List moduleNames) {
		List configs = new ArrayList();
		try {
			Resource resources1[] = resolver.getResources((new StringBuilder())
					.append("classpath*:org/esblink/**").append(configName)
					.toString());
			Resource resources2[] = resolver.getResources((new StringBuilder())
					.append("classpath*:com/esblink/**").append(configName)
					.toString());
			Resource resources3[] = resolver.getResources((new StringBuilder())
					.append("classpath*:com/idplink/**").append(configName)
					.toString());
			int length = resources1.length;
			int length2 = length + resources2.length;
			Resource resources[] = new Resource[length2 + resources3.length];
			for (int i = 0; i < resources.length; i++)
				if (i < length)
					resources[i] = resources1[i];
				else if (i < length2)
					resources[i] = resources2[i - length];
				else
					resources[i] = resources3[i - length2];

//			servletContext.log((new StringBuilder())
//					.append("[Framework] search ").append(configName)
//					.append(" count: ").append(resources.length).toString());
			log.info((new StringBuilder()).append("[Framework] search ")
					.append(configName).append(" count: ")
					.append(resources.length).toString());
			Resource arr$[] = resources;
			int len$ = arr$.length;
			for (int i$ = 0; i$ < len$; i$++) {
				Resource resource = arr$[i$];
				try {
					String file = resource.getURL().getPath();
					String config = null;
					if (file.lastIndexOf("org/esblink/") > 0)
						config = file.substring(file.lastIndexOf("org/esblink/"));
					else if (file.lastIndexOf("com/esblink/") > 0)
						config = file.substring(file
								.lastIndexOf("com/esblink/"));
					else
						config = file.substring(file
								.lastIndexOf("com/idplink/"));
					if (!configs.contains(config)) {
//						servletContext.log((new StringBuilder())
//								.append("[Framework] add config: ")
//								.append(config).toString());
//						log.info((new StringBuilder())
//								.append("[Framework] add config: ")
//								.append(config).toString());
						configs.add(config);
					}
					if (config.startsWith("org/esblink/module/")) {
						String moduleName = config.substring(
								"org/esblink/module/".length(), config.length()
										- configName.length());
//						servletContext.log((new StringBuilder())
//								.append("[Framework] parse module name: ")
//								.append(moduleName).toString());
						log.info((new StringBuilder())
								.append("[Framework] parse module name: ")
								.append(moduleName).toString());
						if (!moduleNames.contains(moduleName)) {
							moduleNames.add(moduleName);
							sfmList.add(moduleName);
						}
					} else if (config.startsWith("com/esblink/")) {
						String moduleName = config.substring(
								"com/esblink/".length(), config.length()
										- configName.length());
//						servletContext.log((new StringBuilder())
//								.append("[Framework] parse module name: ")
//								.append(moduleName).toString());
						log.info((new StringBuilder())
								.append("[Framework] parse module name: ")
								.append(moduleName).toString());
						if (!moduleNames.contains(moduleName)) {
							moduleNames.add(moduleName);
							esbList.add(moduleName);
						}
					} else if (config.startsWith("com/idplink/module/")) {
						String moduleName = config.substring(
								"com/idplink/module/".length(), config.length()
										- configName.length());
//						servletContext.log((new StringBuilder())
//								.append("[Framework] parse module name: ")
//								.append(moduleName).toString());
						log.info((new StringBuilder())
								.append("[Framework] parse module name: ")
								.append(moduleName).toString());
						if (!moduleNames.contains(moduleName))
							moduleNames.add(moduleName);
					}
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				}
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return configs;
	}

	private List initModules(List moduleNames) {
		List modules = new ArrayList();
		try {
//			servletContext.log((new StringBuilder())
//					.append("[Framework] search module count: ")
//					.append(moduleNames.size()).toString());
			log.info((new StringBuilder())
					.append("[Framework] search module count: ")
					.append(moduleNames.size()).toString());
			boolean isExist = false;
			Module module = null;
			for (Iterator iterator = moduleNames.iterator(); iterator.hasNext(); modules
					.add(module)) {
				String moduleName = (String) iterator.next();
				isExist = false;
				for (Iterator iterator1 = sfmList.iterator(); iterator1
						.hasNext();) {
					String sfm = (String) iterator1.next();
					if (moduleName.equals(sfm)) {
						isExist = true;
						break;
					}
				}

				module = new Module(moduleName, "1.0");
				if (!isExist) {
					for (Iterator iterator2 = esbList.iterator(); iterator2
							.hasNext();) {
						String esb = (String) iterator2.next();
						if (moduleName.equals(esb)) {
							isExist = true;
							break;
						}
					}

					if (isExist) {
						module.setDirectory("com/esblink/");
						module.setPackageName("com.esblink.");
					} else {
						module.setDirectory("com/idplink/module/");
						module.setPackageName("com.idplink.module.");
					}
				}
			}

		} catch (Exception e) {
			log.warn(e.getMessage(), e);
		}
		return modules;
	}

	private void exportResources(List modules) {
		Module module;
		for (Iterator i$ = modules.iterator(); i$.hasNext(); export(
				module.getStylesDirectory(), module.getStylesExportDirectory())) {
			module = (Module) i$.next();
			export(module.getPagesDirectory(), module.getPagesExportDirectory());
			export(module.getImagesDirectory(),
					module.getImagesExportDirectory());
			export(module.getScriptsDirectory(),
					module.getScriptsExportDirectory());
		}

	}

	private void export(String src, String dist) {
		boolean isWebService =false;
		if (isWebService) {
			log.info((new StringBuilder(" ignore copy file:")).append(dist)
					.append(" by isWebService=").append(isWebService)
					.toString());
			return;
		}
		try {
			File pagesTargetDir = new File(servletContext.getRealPath(dist));
			if (!pagesTargetDir.exists())
				pagesTargetDir.mkdirs();
			Resource resources[] = resolver.getResources((new StringBuilder())
					.append("classpath*:").append(src).append("/**/*.*")
					.toString());
			if (resources != null && resources.length > 0) {
				Resource arr$[] = resources;
				int len$ = arr$.length;
				for (int i$ = 0; i$ < len$; i$++) {
					Resource resource = arr$[i$];
					try {
						String resouceName = resource.getURL().getPath();
						int index = resouceName.indexOf(src);
						String fileName = resouceName.substring(index
								+ src.length());
						File target = new File(pagesTargetDir, fileName);
						File dir = target.getParentFile();
						if (!dir.exists())
							dir.mkdirs();
						if (!target.exists()) {
//							servletContext.log((new StringBuilder())
//									.append("[Framework] release resource: ")
//									.append(dist).append(fileName).toString());
							log.info((new StringBuilder())
									.append("[Framework] release resource: ")
									.append(dist).append(fileName).toString());
//							java.io.OutputStream out = new FileOutputStream(
//									target);
//							java.io.InputStream in = resource.getInputStream();
//							IOUtils.copy(in, out, true);
						}
					} catch (Exception e) {
//						servletContext.log(e.getMessage());
						log.error(e.getMessage());
					}
				}

			}
		} catch (Exception e) {
//			servletContext.log(e.getMessage());
			log.error(e.getMessage());
		}
	}

	public IGlobalMessageSource getMessageSource() {
		return MessageSourceCache.getInstance().getGlobalMs(
				UserContext.getContext().getLocale());
	}

	private void initSystemConfig() {
		try {
			systemConfig.load(servletContext
					.getResourceAsStream("/WEB-INF/sysconfig.properties"));
		} catch (Exception e) {
			try {
				systemConfig.load(servletContext
						.getResourceAsStream("/WEB-INF/url.properties"));
				systemConfig.load(servletContext
						.getResourceAsStream("/WEB-INF/version.properties"));
			} catch (Exception e2) {
				log.debug(
						"load config: /WEB-INF/sysconfig.properties failure!!!!,"+e2.getMessage());
			}
		}
	}

	public Properties getSystemConfig() {
		return systemConfig;
	}

}
