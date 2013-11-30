package org.esblink.module.framework.util;

import javax.servlet.http.Cookie;

public class CookieUtils {

	public static String getByName(Cookie[] cookies, String name){
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null
						&& name.equalsIgnoreCase(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}
}
