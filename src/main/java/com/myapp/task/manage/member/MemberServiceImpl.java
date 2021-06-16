package com.myapp.task.manage.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {
	@Autowired
	private MemberRepository memberRepository;

	@Override
	public Optional<MemberVO> findByIdAndPassword(String id, String password) {
		return this.memberRepository.findByIdAndPassword(id, password);
	}

	@Override
	public void save() {
		MemberVO member = new MemberVO();
		member.setId("kakasleki");
		member.setPassword("1234");
		member.setName("이슬기");
		if(this.memberRepository.findById(member.getId()).isPresent()) return;
		this.memberRepository.save(member);
	}
}
