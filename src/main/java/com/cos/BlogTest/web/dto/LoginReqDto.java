package com.cos.BlogTest.web.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //데이터 전송되는게 초기화된다.
public class LoginReqDto {
	@Size(min = 2, max = 20)
	@NotBlank
	private String username;
	@Size(min = 4, max = 20)
	@NotBlank
	private String password;
	//받아서 조회하기 때문에 ToEntity가 필요없다
}
