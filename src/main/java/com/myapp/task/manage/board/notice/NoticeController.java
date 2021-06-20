package com.myapp.task.manage.board.notice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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

	@PutMapping(value = "/notice")
	public ResponseEntity<Map<String, Object>> insertNoticeInfo(NoticeVO notice, @RequestParam("attachNo") String attachNos, HttpServletRequest request) {
		return new ResponseEntity<>(this.noticeService.insertNoticeInfo(notice, attachNos, request), HttpStatus.OK);
	}

	@PatchMapping(value = "/notice")
	public ResponseEntity<Map<String, Object>> updateNoticeInfo(NoticeVO notice, @RequestParam("attachNo") String attachNos, HttpServletRequest request) {
		return new ResponseEntity<>(this.noticeService.updateNoticeInfo(notice, attachNos, request), HttpStatus.OK);
	}

	@DeleteMapping(value = "/notice/{noticeNo}")
	public ResponseEntity<Map<String, Object>> deleteNoticeInfo(@PathVariable("noticeNo") Long noticeNo) {
		return new ResponseEntity<>(this.noticeService.deleteNoticeInfo(noticeNo), HttpStatus.OK);
	}
}
