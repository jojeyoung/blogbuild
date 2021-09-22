package com.cos.BlogTest.web.dto;

import com.cos.BlogTest.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

//dto는 3개 고정
@Getter
@AllArgsConstructor
@Setter
public class JoinReqDto {
	private String username;
	private String password;
	private String email;
	
	public User toEntity() {
		//장점: 순서안지켜도 됨, 안넣고 싶은건 안넣어도 된다.
		User user = User.builder()
				.username(username)
				.password(password)
				.email(email)
				.build();
		return user;
	}
}
