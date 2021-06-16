package com.myapp.task.manage.login;

import com.myapp.task.common.cmmcode.ResultCode;
import com.myapp.task.common.validation.TaskValidationService;
import com.myapp.task.manage.member.MemberService;
import com.myapp.task.manage.member.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {
	private static final int DEFAULT_SESSION_TIME;

	static {
		DEFAULT_SESSION_TIME = 30;
	}

	@Autowired
	private MemberService memberService;

	@Autowired
	private TaskValidationService taskValidationService;

	@Override
	public Map<String, Object> login(MemberVO member, HttpServletRequest request) {
		Map<String, Object> resultMap = this.loginValidationCheck(member);
		if(ResultCode.ERROR.equals(resultMap.get(ResultCode.RESULT))) return resultMap;

		Optional<MemberVO> loginMember = this.memberService.findByIdAndPassword(member.getId(),member.getPassword());
		if(!loginMember.isPresent()) return this.taskValidationService.errorResult("가입하지 않은 아이디이거나, 잘못된 비밀번호 입니다.");

		this.setLoginSession(loginMember.get(), request);

		resultMap.put(ResultCode.RESULT, ResultCode.SUCCESS);
		return resultMap;
	}

	private Map<String, Object> loginValidationCheck(MemberVO member) {
		Map<String, Object> resultMap = new HashMap<>();

		if(this.taskValidationService.isNull(member.getId())) {
			resultMap.put(ResultCode.MSG, "로그인 아이디를 입력해 주십시오.");
		} else if(this.taskValidationService.isNull(member.getPassword())) {
			resultMap.put(ResultCode.MSG, "로그인 비밀번호를 입력해 주십시오.");
		}

		return this.taskValidationService.validationCheckResult(resultMap);
	}

	private void setLoginSession(MemberVO member, HttpServletRequest request) {
		HttpSession session = request.getSession();

		session.setAttribute("_MANAGER_NO_", member.getMemberNo());
		session.setAttribute("_MANAGER_ID_", member.getId());
		session.setAttribute("_MANAGER_NAME_", member.getName());

		session.setMaxInactiveInterval(DEFAULT_SESSION_TIME * 60);
	}
}
