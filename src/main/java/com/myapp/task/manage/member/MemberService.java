package com.myapp.task.manage.member;

import java.util.Optional;

public interface MemberService {
	Optional<MemberVO> findByIdAndPassword(String id, String password);

	void save();
}
