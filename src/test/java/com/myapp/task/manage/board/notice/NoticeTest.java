package com.myapp.task.manage.board.notice;

import com.myapp.task.common.cmmcode.ResultCode;
import com.myapp.task.manage.board.attach.AttachService;
import com.myapp.task.manage.board.attach.AttachVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
public class NoticeTest {
	private static final Logger logger;

	static {
		logger = LoggerFactory.getLogger(NoticeTest.class);
	}

	@Autowired
	private NoticeService noticeService;

	@Autowired
	private AttachService attachService;

	@Before
	public void init() {
		this.noticeService.insertInitNoticeData();
	}

	@Test
	public void test01_insertNotice() throws Exception {
		Assert.assertEquals(this.noticeService.insertNoticeInfo(null, null, null).get(ResultCode.RESULT), ResultCode.ERROR);

		MultipartFile file1 = new MockMultipartFile("image1.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile file2 = new MockMultipartFile("image2.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile[] files = {
				file1,
				file2
		};

		List<AttachVO> attachList = this.attachService.uploadAttachFile(files);
		Assert.assertNotNull(attachList);

		StringJoiner attachNos = new StringJoiner(",");
		for(AttachVO attachVO : attachList) {
			attachNos.add(String.valueOf(attachVO.getAttachNo()));
		}

		NoticeVO notice = new NoticeVO();
		notice.setSubject("공지사항 입력 테스트 제목");
		Assert.assertEquals(this.noticeService.insertNoticeInfo(notice, attachNos.toString(), null).get(ResultCode.RESULT), ResultCode.ERROR);

		notice.setContent("공지사항 입력 테스트 내용");
		Assert.assertEquals(this.noticeService.insertNoticeInfo(notice, attachNos.toString(), null).get(ResultCode.RESULT), ResultCode.SUCCESS);

		logger.info("INSERT NOTICE KEY : {}", notice.getNoticeNo());

		Assert.assertTrue(this.attachService.deleteAttachFiles(notice.getNoticeNo()));
	}

	@Test
	public void test02_searchNotice() {
		Map<String, Object> conditionParam = new HashMap<>();
		conditionParam.put("search_option", "0");
		conditionParam.put("search_txt", "초기");

		Pageable pageable = PageRequest.of(0, 10);
		logger.info("NOTICE INFO LIST : {}" , this.noticeService.findAll(conditionParam, pageable));
	}

	@Test
	public void test03_updateNotice() throws Exception {
		Map<String, Object> conditionParam = new HashMap<>();
		conditionParam.put("search_option", "0");
		conditionParam.put("search_txt", "초기");
		Pageable pageable = PageRequest.of(0, 10);

		Page<NoticeVO> noticeList = this.noticeService.findAll(conditionParam, pageable);
		Assert.assertNotNull(noticeList);
		int updateCnt = 0;

		for(NoticeVO notice : noticeList) {
			logger.info("BEFORE UPDATE NOTICE INFO : {}", notice);

			notice.setSubject("공지사항 수정 테스트 제목" + (++updateCnt));
			notice.setContent("공지사항 수정 테스트 내용" + (++updateCnt));

			Assert.assertEquals(this.noticeService.updateNoticeInfo(notice, null, null).get(ResultCode.RESULT), ResultCode.SUCCESS);

			logger.info("AFTER UPDATE NOTICE INFO : {}", notice);
		}
	}

	@Test
	public void test04_deleteNotice() throws Exception {
		Map<String, Object> conditionParam = new HashMap<>();
		conditionParam.put("search_option", "0");
		conditionParam.put("search_txt", "초기");
		Pageable pageable = PageRequest.of(0, 10);

		Page<NoticeVO> noticeList = this.noticeService.findAll(conditionParam, pageable);
		Assert.assertNotNull(noticeList);
		int deleteCnt = 0;

		for(NoticeVO notice : noticeList) {
			Assert.assertEquals(this.noticeService.deleteNoticeInfo(notice.getNoticeNo()).get(ResultCode.RESULT), ResultCode.SUCCESS);
			logger.info("DELETE NOTICE INFO {} : {}", deleteCnt, notice);
		}
	}

	@Test
	public void test5_integrateNoticeCRUD() throws Exception {
		MultipartFile file1 = new MockMultipartFile("image1.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile file2 = new MockMultipartFile("image2.jpg", new FileInputStream(new File("/Users/kakasleki/Downloads/OSH_3893.jpg")));
		MultipartFile[] files = {
				file1,
				file2
		};

		List<AttachVO> attachList = this.attachService.uploadAttachFile(files);
		Assert.assertNotNull(attachList);

		StringJoiner attachNos = new StringJoiner(",");
		for(AttachVO attachVO : attachList) {
			attachNos.add(String.valueOf(attachVO.getAttachNo()));
		}

		NoticeVO notice = new NoticeVO();
		notice.setSubject("공지사항 입력 테스트 제목");
		notice.setContent("공지사항 입력 테스트 내용");
		Assert.assertEquals(this.noticeService.insertNoticeInfo(notice, attachNos.toString(), null).get(ResultCode.RESULT), ResultCode.SUCCESS);

		logger.info("INSERT NOTICE KEY : {}", notice.getNoticeNo());

		notice.setSubject("공지사항 입력 테스트 수정");
		notice.setContent("공지사항 입력 테스트 수정");
		Assert.assertEquals(this.noticeService.updateNoticeInfo(notice, null, null).get(ResultCode.RESULT), ResultCode.SUCCESS);

		notice = this.noticeService.findById(notice.getNoticeNo());
		Assert.assertNotNull(notice);

		Assert.assertEquals(this.noticeService.deleteNoticeInfo(notice.getNoticeNo()).get(ResultCode.RESULT), ResultCode.SUCCESS);
		notice = this.noticeService.findById(notice.getNoticeNo());
		Assert.assertNull(notice);
	}
}
