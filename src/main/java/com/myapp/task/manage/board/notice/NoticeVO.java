package com.myapp.task.manage.board.notice;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "notice")
@Table
public class NoticeVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long noticeNo;

	private String subject;

	private String writer;

	private Date createDate;

	private Date updateDate;

	private String content;

	@Builder
	public NoticeVO(String subject, String writer, Date createDate, Date updateDate, String content) {
		this.subject = subject;
		this.writer = writer;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
}
