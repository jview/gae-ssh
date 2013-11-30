package org.esblink.module.loginmgmt.action;

import java.util.Locale;

import org.esblink.common.base.action.BaseAction;
import org.esblink.common.base.context.UserContext;


public class SwitchLocaleAction extends BaseAction {

	private static final long serialVersionUID = -1919165507129046595L;

	private String language;

	public void setLanguage(String language) {
		this.language = language;
	}

	private String country;

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String execute() throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("switch language: " + language);
			log.debug("switch country: " + country);
		}
		log.debug("switch language: " + language);
		log.debug("switch country: " + country);
		if (language != null && language.length() > 0) {
			if (country != null && country.length() > 0) {
				UserContext.getContext().setUserLocale(new Locale(language, country));
			} else {
				UserContext.getContext().setUserLocale(new Locale(language));
			}
		}
		return SUCCESS;
	}
	
	public String swichLocale() throws Exception {
		execute();
		return SUCCESS;
	}

}
