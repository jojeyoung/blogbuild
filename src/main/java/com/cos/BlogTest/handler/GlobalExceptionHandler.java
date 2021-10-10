package com.cos.BlogTest.handler;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.handler.ex.MyAsyncNotFoundException;
import com.cos.BlogTest.handler.ex.MyNotFoundException;
import com.cos.BlogTest.utill.Script;
import com.cos.BlogTest.web.dto.CMRespDto;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = MyNotFoundException.class)
	public @ResponseBody String error1(MyNotFoundException e) {
		System.out.println("오류 터졌어요: " + e.getMessage());
		return Script.href("/", e.getMessage());
	}
		@ExceptionHandler(value = MyAsyncNotFoundException.class)
		public @ResponseBody CMRespDto<String> error2(MyAsyncNotFoundException e) {
			System.out.println("오류 터졌어 : "+e.getMessage());
			return new CMRespDto<String>(-1, e.getMessage(), null);
		}
}
