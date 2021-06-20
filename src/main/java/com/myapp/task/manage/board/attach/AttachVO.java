package com.myapp.task.manage.board.attach;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myapp.task.manage.board.notice.NoticeVO;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "attach")
@Table
public class AttachVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long attachNo;

	private Long noticeNo;

	private String oriFileName;

	@JsonIgnore
	private String realFilePath;

	private Date uploadDate;

	@Builder
	public AttachVO(Long noticeNo, String oriFileName, String realFilePath, Date uploadDate) {
		this.noticeNo = noticeNo;
		this.oriFileName = oriFileName;
		this.realFilePath = realFilePath;
		this.uploadDate = uploadDate;
	}
}
