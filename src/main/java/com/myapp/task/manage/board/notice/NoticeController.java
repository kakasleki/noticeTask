package com.myapp.task.manage.board.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/board/notice")
public class NoticeController {
	@Autowired
	private NoticeService noticeService;

	@GetMapping(value = "/main")
	public ModelAndView noticeBoardPage() {
		return new ModelAndView("board/notice/notice");
	}

	@GetMapping(value = "/notice")
	public Page<NoticeVO> noticeInfoList(NoticeVO notice, Pageable pageable) {
		return this.noticeService.findAll(notice, pageable);
	}
}
