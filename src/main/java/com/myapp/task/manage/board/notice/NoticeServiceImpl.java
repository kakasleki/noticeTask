package com.myapp.task.manage.board.notice;

import com.myapp.task.common.validation.TaskValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
}
