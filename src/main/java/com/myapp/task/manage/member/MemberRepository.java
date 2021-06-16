package com.myapp.task.manage.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberVO, Long> {
	Optional<MemberVO> findByIdAndPassword(String id, String password);

	Optional<MemberVO> findById(String id);
}
