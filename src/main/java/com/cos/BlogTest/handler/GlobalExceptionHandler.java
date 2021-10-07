package com.cos.BlogTest.handler;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.handler.ex.MyNotFoundException;
import com.cos.BlogTest.utill.Script;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류 터졌어요: " + e.getMessage());
		return Script.href("/", e.getMessage());
		
		
	}
}
