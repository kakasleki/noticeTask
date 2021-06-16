package com.myapp.task.manage.member;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "member")
@Table
public class MemberVO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long memberNo;

	private String id;

	private String password;

	private String name;

	@Builder
	public MemberVO(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}
}
