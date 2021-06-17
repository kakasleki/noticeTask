package com.myapp.task.manage.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface NoticeService {
	Page<NoticeVO> findAll(Map<String, Object> params, Pageable pageable);

	NoticeVO findById(Long noticeNo);

	void insertInitNoticeData();
}
