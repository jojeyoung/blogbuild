package com.cos.BlogTest.web.dto;

import javax.validation.constraints.NotBlank;

import javax.validation.constraints.Size;

import com.cos.BlogTest.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//dto는 3개 고정
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class JoinReqDto {
	@Size(min = 2, max = 20)
	@NotBlank
	private String username;
	
	@Size(min = 4, max = 20)
	@NotBlank
	private String password;
	
	@Size(min = 4, max = 50)
	@NotBlank
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
