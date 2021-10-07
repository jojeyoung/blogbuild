package com.cos.BlogTest.handler.ex;

	// @author : 조재영 2021.10.08
	// 보드의 id를 못찾을 때 사용

public class MyNotFoundException extends RuntimeException{
	
	public MyNotFoundException(String mag) {
		super(mag);
	}
}
