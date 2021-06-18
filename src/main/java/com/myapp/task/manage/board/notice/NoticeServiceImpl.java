package com.myapp.task.manage.board.notice;

import com.myapp.task.common.cmmcode.ResultCode;
import com.myapp.task.common.validation.TaskValidationService;
import com.myapp.task.manage.board.attach.AttachService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Service
public class NoticeServiceImpl implements NoticeService {
	private static final Logger logger;

	static {
		logger = LoggerFactory.getLogger(NoticeServiceImpl.class);
	}

	@Autowired
	private NoticeRepository noticeRepository;

	@Autowired
	private AttachService attachService;

	@Autowired
	private TaskValidationService taskValidationService;

	@Override
	public Page<NoticeVO> findAll(Map<String, Object> params, Pageable pageable) {
		if(this.taskValidationService.isNull(params.get("search_option")) && this.taskValidationService.isNull(params.get("start_date"))) {
			return this.noticeRepository.findAll(pageable);
		}

		String searchOption = params.get("search_option").toString();
		String keyword = !this.taskValidationService.isNull(params.get("search_txt")) ? params.get("search_txt").toString() : "";

		if(this.taskValidationService.isNull(params.get("start_date"))) {
			if("0".equals(searchOption)) {
				return this.noticeRepository.findAllBySubjectContaining(keyword, pageable);
			} else if("1".equals(searchOption)) {
				return this.noticeRepository.findAllByContentContaining(keyword, pageable);
			} else if("2".equals(searchOption)) {
				return this.noticeRepository.findAllBySubjectContainingOrContentContaining(keyword, keyword, pageable);
			} else if("3".equals(searchOption)) {
				return this.noticeRepository.findAllByWriterContaining(keyword, pageable);
			}
		} else {
			Date start = this.stringToDateTransFormat(params.get("start_date").toString());
			Date end;

			if(this.taskValidationService.isNull(params.get("end_date"))) end = this.stringToDateTransFormat(params.get("start_date").toString());
			else end = this.stringToDateTransFormat(params.get("end_date").toString());

			if(this.taskValidationService.isNull(params.get("search_option")) && !this.taskValidationService.isNull(params.get("start_date"))) {
				return this.noticeRepository.findAllByUpdateDateBetween(start, end, pageable);
			}

			if("0".equals(searchOption)) {
				return this.noticeRepository.findAllBySubjectContainingAndUpdateDateBetween(keyword, start, end, pageable);
			} else if("1".equals(searchOption)) {
				return this.noticeRepository.findAllByContentContainingAndUpdateDateBetween(keyword, start, end, pageable);
			} else if("2".equals(searchOption)) {
				return this.noticeRepository.findAllBySubjectContainingOrContentContainingAndUpdateDateBetween(keyword, keyword, start, end, pageable);
			} else if("3".equals(searchOption)) {
				return this.noticeRepository.findAllByWriterContainingAndUpdateDateBetween(keyword, start, end, pageable);
			}
		}
		return this.noticeRepository.findAll(pageable);
	}

	private Date stringToDateTransFormat(String date) {
		Date returnDate;

		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
			returnDate = dateFormat.parse(date.replaceAll("[^0-9]", ""));
		} catch (ParseException e) {
			logger.error("STRING DATE TO DATE TRANS ERROR : ", e);
			return new Date();
		}

		return returnDate;
	}

	@Override
	public void insertInitNoticeData() {
		for(int i = 0; i < 20; i++) {
			NoticeVO notice = new NoticeVO();
			notice.setSubject("초기 입력데이터 제목 " + i);
			notice.setContent("초기 입력데이터 내용\n테스트 TEST 1234 " + i);
			notice.setWriter("testMember" + i);
			notice.setCreateDate(this.stringToDateTransFormat("20210617"));
			notice.setUpdateDate(this.stringToDateTransFormat("20210618"));

			this.noticeRepository.save(notice);

			System.out.println(notice);
		}
	}

	@Override
	public NoticeVO findById(Long noticeNo) {
		return this.noticeRepository.findByNoticeNo(noticeNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> insertNoticeInfo(NoticeVO noticeInfo, MultipartFile[] files, HttpServletRequest request) {
		Map<String, Object> resultMap = this.noticeInfoValidationCheck(noticeInfo, "INSERT");
		if(ResultCode.ERROR.equals(resultMap.get(ResultCode.RESULT))) return resultMap;
		return this.noticeInfoSave(noticeInfo, files, "INSERT", request);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updateNoticeInfo(NoticeVO noticeInfo, MultipartFile[] files, HttpServletRequest request) {
		Map<String, Object> resultMap = this.noticeInfoValidationCheck(noticeInfo, "UPDATE");
		if(ResultCode.ERROR.equals(resultMap.get(ResultCode.RESULT))) return resultMap;
		return this.noticeInfoSave(noticeInfo, files, "UPDATE", request);
	}

	private Map<String, Object> noticeInfoValidationCheck(NoticeVO noticeInfo, String transType) {
		Map<String, Object> resultMap = new HashMap<>();

		if(this.taskValidationService.isNull(noticeInfo.getSubject())) {
			resultMap.put(ResultCode.MSG, "제목을 입력해 주십시오.");
		} else if(this.taskValidationService.isNull(noticeInfo.getContent())) {
			resultMap.put(ResultCode.MSG, "제목을 입력해 주십시오.");
		} else if("UPDATE".equals(transType) && this.taskValidationService.isNull(noticeInfo.getNoticeNo())) {
			resultMap.put(ResultCode.MSG, "제목을 입력해 주십시오.");
		}

		return this.taskValidationService.validationCheckResult(resultMap);
	}

	private Map<String, Object> noticeInfoSave(NoticeVO noticeInfo, MultipartFile[] files, String transType, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();

		if(transType.equals("INSERT")) noticeInfo.setCreateDate(new Date());
		noticeInfo.setUpdateDate(new Date());
		noticeInfo.setWriter(request.getSession().getAttribute("_MANAGER_ID_").toString());

		// 공지사항 입력 후 첨부파일 추가
		this.noticeRepository.save(noticeInfo);

		if(!this.taskValidationService.isNull(files)) {
			try {
				logger.info("JOB TYPE : {}, NOTICE NO : {}", transType, noticeInfo.getNoticeNo());
				if(!this.attachService.uploadAttachFile(noticeInfo.getNoticeNo(), files)) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
					resultMap.put(ResultCode.RESULT, ResultCode.ERROR);
					resultMap.put(ResultCode.MSG, "NOTICE SAVE FILE UPLOAD FAIL");
					return resultMap;
				}
			} catch (Exception e) {
				logger.error("NOTICE SAVE FILE UPLOAD ERROR : ", e);
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				resultMap.put(ResultCode.RESULT, ResultCode.ERROR);
				resultMap.put(ResultCode.MSG, "NOTICE SAVE FILE UPLOAD ERROR");
				return resultMap;
			}
		}

		resultMap.put(ResultCode.RESULT, ResultCode.SUCCESS);
		return resultMap;
	}

	@Override
	public Map<String, Object> deleteNoticeInfo(Long noticeNo) {
		Map<String, Object> resultMap = new HashMap<>();

		// 첨부파일 삭제 후 공지사항 삭제처리
		if(!this.attachService.deleteAttachFiles(noticeNo)) {
			resultMap.put(ResultCode.RESULT, ResultCode.ERROR);
			resultMap.put(ResultCode.MSG, "ATTACH FILE REMOVE FAIL");
			return resultMap;
		}

		NoticeVO noticeVO = this.noticeRepository.findByNoticeNo(noticeNo);
		this.noticeRepository.delete(noticeVO);

		resultMap.put(ResultCode.RESULT, ResultCode.SUCCESS);
		return resultMap;
	}
}
