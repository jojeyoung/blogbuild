package com.cos.BlogTest.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.BlogTest.domain.user.User;
import com.cos.BlogTest.domain.user.UserRepository;
import com.cos.BlogTest.utill.SHA;
import com.cos.BlogTest.web.dto.JoinReqDto;
import com.cos.BlogTest.web.dto.LoginReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserRepository userRepository;
	private final HttpSession Session;
	
	@PostMapping("/login")
	public String login(LoginReqDto dto) {
		
		String encPassword = SHA.encrypt(dto.getPassword());
		User principal = userRepository.mLogin(dto.getUsername(), encPassword);
		
		if(principal == null) {
			return "redirect:/loginForm";
		}else {
			Session.setAttribute("principal", principal);
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
	
}
