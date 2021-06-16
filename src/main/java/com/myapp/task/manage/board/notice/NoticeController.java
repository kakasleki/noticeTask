package com.myapp.task.manage.board.notice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/board/notice")
public class NoticeController {
	@GetMapping(value = "/main")
	public ModelAndView noticeBoardPage() {
		return new ModelAndView("board/notice/notice");
	}
}
