package com.cos.BlogTest.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.domain.user.User;
import com.cos.BlogTest.domain.user.UserRepository;
import com.cos.BlogTest.handler.ex.MyAsyncNotFoundException;
import com.cos.BlogTest.utill.SHA;
import com.cos.BlogTest.web.dto.CMRespDto;
import com.cos.BlogTest.web.dto.JoinReqDto;
import com.cos.BlogTest.web.dto.LoginReqDto;
import com.cos.BlogTest.web.dto.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession session;
	
	
	@PutMapping("/user/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @Valid @RequestBody UserUpdateDto dto,
		     BindingResult bindingResult){
		//유효성
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}
		
		//인증
			User principal = (User) session.getAttribute("principal");
			if (principal == null) { // 로그인 안됨
				throw new MyAsyncNotFoundException("인증이 되지 않았습니다.");
			}
		//권한
			if (principal.getId() != id) {
				throw new MyAsyncNotFoundException("회원정보를 수정할 권한이 없습니다.");
			}
		// 핵심로직	
			principal.setEmail(dto.getEmail());
			session.setAttribute("principal", principal); // 세션 값 변경

			userRepository.save(principal);
			
			return new CMRespDto<>(1, "성공", null);
		}
	
	@PostMapping("/login")
	public String login(LoginReqDto dto) {
		
		String encPassword = SHA.encrypt(dto.getPassword());
		User principal = userRepository.mLogin(dto.getUsername(), encPassword);
		
		if(principal == null) {
			return "redirect:/loginForm";
		}else {
			session.setAttribute("principal", principal);
			return "redirect:/";
		}
		
	}
	
	@PostMapping("/join")
	public String join(JoinReqDto dto) { //usernmae= ssar&password =1234&email=ssar@nate.com
		
		//오른쪽은 해쉬 - 왼쪽은 1234
		String encPassword = SHA.encrypt(dto.getPassword());
		dto.setPassword(encPassword);
		User user = dto.toEntity();
		userRepository.save(user);
		
		return "redirect:/loginForm";
	}
	
	@GetMapping("/loginForm")
	public String loginForm(){
		return "user/loginForm";
	}
	
	@GetMapping("/joinForm")
	public String joinForm() {
		return "user/joinForm";
	}
	
	@GetMapping("/user/{id}")
	public String userInfo(@PathVariable int id) {
		return "user/updateForm";
	}
	
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "redirect:/";
		
	}
}


