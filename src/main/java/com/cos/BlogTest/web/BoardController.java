package com.cos.BlogTest.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

	@GetMapping("/board")
	public String list() {
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String datail(@PathVariable int id) {
		return "board/detail";
	}
	
	@GetMapping("/board/saveFrom")
	public String saveForm() {
		return "board/saveFrom";
	}
	
	
}
