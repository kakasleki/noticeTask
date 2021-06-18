package com.myapp.task.manage.board.notice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface NoticeService {
	Page<NoticeVO> findAll(Map<String, Object> params, Pageable pageable);

	NoticeVO findById(Long noticeNo);

	void insertInitNoticeData();

	Map<String, Object> insertNoticeInfo(NoticeVO noticeInfo, MultipartFile[] files, HttpServletRequest request);

	Map<String, Object> updateNoticeInfo(NoticeVO noticeInfo, MultipartFile[] files, HttpServletRequest request);

	Map<String, Object> deleteNoticeInfo(Long noticeNo);
}
