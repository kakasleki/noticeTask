package com.myapp.task.manage.login;

import com.myapp.task.manage.member.MemberVO;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface LoginService {
	Map<String, Object> login(MemberVO member, HttpServletRequest request);
}
