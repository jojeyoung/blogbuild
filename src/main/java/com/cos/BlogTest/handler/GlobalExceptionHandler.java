package com.cos.BlogTest.handler;

import java.util.NoSuchElementException;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.utill.Script;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = NoSuchElementException.class)
	public @ResponseBody String error1(NoSuchElementException e) {
		System.out.println("오류 터졌어요: " + e.getMessage());
		return Script.href("/", "게시글 id를 찾을 수 없습니다");
		
		
	}
}
