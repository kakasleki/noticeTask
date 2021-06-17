package com.myapp.task.manage.board.attach;

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

	private String oriFileName;

	private String realFilePath;

	private Date uploadDate;

	@ManyToOne
	@JoinColumn(name = "noticeNo")
	private NoticeVO notice;

	@Builder
	public AttachVO(String oriFileName, String realFilePath, Date uploadDate) {
		this.oriFileName = oriFileName;
		this.realFilePath = realFilePath;
		this.uploadDate = uploadDate;
	}
}
