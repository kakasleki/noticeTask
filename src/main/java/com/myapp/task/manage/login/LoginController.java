package com.myapp.task.manage.login;

import com.myapp.task.manage.board.notice.NoticeService;
import com.myapp.task.manage.member.MemberService;
import com.myapp.task.manage.member.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class LoginController {
	private static final Logger logger;

	static {
		logger = LoggerFactory.getLogger(LoginController.class);
	}

	@Autowired
	private LoginService loginService;

	@Autowired
	private MemberService memberService;

	@Autowired
	private NoticeService noticeService;

	@GetMapping(value = {"/", "/login"})
	public ModelAndView loginPage(HttpServletRequest request) {
		this.memberService.save();
		this.noticeService.insertInitNoticeData();
		if(request.getSession().getAttribute("_MANAGER_ID_") != null) return new ModelAndView(new RedirectView("/board/notice/main"));
		return new ModelAndView("login/login");
	}

	@PostMapping(value = "/login")
	public ResponseEntity<Map<String, Object>> login(MemberVO member, HttpServletRequest request) {
		return new ResponseEntity<>(this.loginService.login(member, request), HttpStatus.OK);
	}

	@GetMapping(value = "/logout")
	public ModelAndView logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return this.loginPage(request);
	}

	@PostMapping(value = "/set/session/time")
	public void setSessionTimeout(HttpServletRequest request) {
		try {
			request.getSession().setMaxInactiveInterval(request.getSession().getMaxInactiveInterval());
		} catch(Exception e) {
			logger.error("SET SESSION TIMEOUT EXTENSION ERROR : ", e);
		}
	}
}
