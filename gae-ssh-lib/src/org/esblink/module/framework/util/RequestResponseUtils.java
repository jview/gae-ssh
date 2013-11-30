package org.esblink.module.framework.util;

import javax.servlet.http.HttpServletRequest;

public final class RequestResponseUtils {

	public static boolean isAjaxRequest(HttpServletRequest request) {
		String header = request.getHeader("X-Requested-With");
		if (header != null && "XMLHttpRequest".equals(header))
			return true;
		else
			return false;
	}
}
