package com.myapp.task.common.method;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class TaskMethodServiceImpl implements TaskMethodService {
	@Override
	public String getWriter(HttpServletRequest request) {
		if(request == null || request.getSession().getAttribute("_MANAGER_ID_") == null) return "시스템";
		return request.getSession().getAttribute("_MANAGER_ID_").toString();
	}
}
