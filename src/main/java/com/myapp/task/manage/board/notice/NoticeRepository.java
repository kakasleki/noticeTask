package com.myapp.task.manage.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<NoticeVO, Long> {
	Page<NoticeVO> findAll(Pageable pageable);

	Page<NoticeVO> findBySubjectContaining(NoticeVO notice, Pageable pageable);
}
