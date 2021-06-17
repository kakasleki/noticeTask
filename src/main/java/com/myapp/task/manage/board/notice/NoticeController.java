package com.myapp.task.manage.board.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

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
	public ResponseEntity<Page<NoticeVO>> noticeInfoList(@RequestParam Map<String, Object> params, Pageable pageable) {
		return new ResponseEntity<>(this.noticeService.findAll(params, pageable), HttpStatus.OK);
	}

	@GetMapping(value = "/notice/{noticeNo}")
	public ResponseEntity<NoticeVO> noticeInfo(@PathVariable("noticeNo") Long noticeNo) {
		return new ResponseEntity<>(this.noticeService.findById(noticeNo), HttpStatus.OK);
	}
}
