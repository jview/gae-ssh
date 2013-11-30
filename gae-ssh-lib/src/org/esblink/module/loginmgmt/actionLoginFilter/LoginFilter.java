package org.esblink.module.loginmgmt.actionLoginFilter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esblink.common.base.context.UserContext;
import org.esblink.module.framework.util.RequestResponseUtils;

public class LoginFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		HttpServletRequest request = (HttpServletRequest) req;
		Long userId = (Long) request.getSession().getAttribute(
				UserContext.USER_ID_KEY);
		String sessionId = request.getSession().getId();

		boolean checkRes = LoginSessionContainer.checkUserSession(userId,
				sessionId);
		if (checkRes)
			chain.doFilter(req, resp);
		else {
			if (RequestResponseUtils.isAjaxRequest(request)) {
				response.getWriter().write("{\"success\":false}");
			} else {
				response.getWriter()
						.write("This account has been landed in other places, so you are forced to exit the system.");
			}
			response.getWriter().flush();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
