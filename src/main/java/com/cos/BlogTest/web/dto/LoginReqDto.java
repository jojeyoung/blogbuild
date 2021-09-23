package com.cos.BlogTest.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor //데이터 전송되는게 초기화된다.
public class LoginReqDto {
	private String username;
	private String password;
	//받아서 조회하기 때문에 ToEntity가 필요없다
}
