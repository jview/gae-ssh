package org.esblink.module.framework.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;

public class ExtFilter implements Filter {

	private String extHome;

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		arg0.setAttribute("extHome", extHome);

		javax.servlet.http.HttpServletRequest request = (javax.servlet.http.HttpServletRequest) arg0;
		javax.servlet.http.HttpServletResponse response = (javax.servlet.http.HttpServletResponse) arg1;

		if (!RequestResponseUtils.isAjaxRequest(request)) {
			Cookie[] cookies = request.getCookies();
			String extDress = CookieUtils.getByName(cookies,
					Constants.EXT_DRESS);
			if (extDress == null) {
				extDress = Constants.EXT_BLUE_DRESS;
				Cookie cookie = new Cookie(Constants.EXT_DRESS, extDress);
				cookie.setMaxAge(3600 * 24 * 30);
				cookie.setPath("/");
				response.addCookie(cookie);
			}
			arg0.setAttribute(Constants.EXT_DRESS, extHome + extDress);

			String localeName = CookieUtils.getByName(cookies, "locale");
			arg0.setAttribute("extSource", extHome
					+ Constants.EXT_LANG
					+ (localeName == null ? arg0.getLocale().toString()
							: localeName) + ".js");
		}

		arg2.doFilter(arg0, arg1);
	}

	public void init(FilterConfig arg0) throws ServletException {
		extHome = arg0.getInitParameter("home");
	}

}
