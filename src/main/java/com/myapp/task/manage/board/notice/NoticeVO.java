package com.myapp.task.manage.board.notice;

import com.myapp.task.manage.board.attach.AttachVO;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createDate;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updateDate;

	private String content;

	@OneToMany
	@JoinColumn(name = "noticeNo", updatable = false)
	private List<AttachVO> attach;

	@Builder
	public NoticeVO(String subject, String writer, Date createDate, Date updateDate, String content) {
		this.subject = subject;
		this.writer = writer;
		this.content = content;
		this.createDate = createDate;
		this.updateDate = updateDate;
	}
}
