package com.cos.BlogTest.handler.ex;

public class MyAsyncNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	public MyAsyncNotFoundException(String msg) {
		super(msg);
	}
}
