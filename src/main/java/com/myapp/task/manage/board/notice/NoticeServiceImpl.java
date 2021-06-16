package com.myapp.task.manage.board.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeRepository noticeRepository;

	@Override
	public Page<NoticeVO> findAll(Pageable pageable) {
		return this.noticeRepository.findAll(pageable);
	}
}
