package com.myapp.task.common.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
	private static final String SESSION_MANAGER_ID;

	static {
		SESSION_MANAGER_ID = "_MANAGER_ID_";
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();

		if(request.getHeader("ajax-request") != null && "true".equals(request.getHeader("ajax-request")) &&
				session.getAttribute(SESSION_MANAGER_ID) == null) {
			response.sendError(6653);
			return false;
		} else if(session.getAttribute(SESSION_MANAGER_ID) == null || "".equals(session.getAttribute(SESSION_MANAGER_ID))) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		} else {
			return true;
		}
	}
}
