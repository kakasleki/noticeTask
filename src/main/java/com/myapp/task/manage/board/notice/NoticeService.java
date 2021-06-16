package com.myapp.task.manage.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {
	Page<NoticeVO> findAll(NoticeVO notice, Pageable pageable);
}
