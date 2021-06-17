package com.myapp.task.manage.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface NoticeRepository extends PagingAndSortingRepository<NoticeVO, Long> {
	Page<NoticeVO> findAll(Pageable pageable);

	NoticeVO findByNoticeNo(Long noticeNo);

	Page<NoticeVO> findAllBySubjectContaining(String keyword, Pageable pageable);

	Page<NoticeVO> findAllByContentContaining(String keyword, Pageable pageable);

	Page<NoticeVO> findAllBySubjectContainingOrContentContaining(String subject, String content, Pageable pageable);

	Page<NoticeVO> findAllByWriterContaining(String keyword, Pageable pageable);

	Page<NoticeVO> findAllBySubjectContainingAndUpdateDateBetween(String keyword, Date start, Date end, Pageable pageable);

	Page<NoticeVO> findAllByContentContainingAndUpdateDateBetween(String keyword, Date start, Date end, Pageable pageable);

	Page<NoticeVO> findAllBySubjectContainingOrContentContainingAndUpdateDateBetween(String subject, String content, Date start, Date end, Pageable pageable);

	Page<NoticeVO> findAllByWriterContainingAndUpdateDateBetween(String keyword, Date start, Date end, Pageable pageable);

	Page<NoticeVO> findAllByUpdateDateBetween(Date start, Date end, Pageable pageable);
}
