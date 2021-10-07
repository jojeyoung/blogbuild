package com.cos.BlogTest.web;

import java.util.List;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.BlogTest.domain.board.Board;
import com.cos.BlogTest.domain.board.BoardRepository;
import com.cos.BlogTest.domain.user.User;
import com.cos.BlogTest.handler.ex.MyNotFoundException;
import com.cos.BlogTest.web.dto.BoardSaveReqDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final HttpSession session;
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {
		
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("principal");
		board.setUser(principal);
		boardRepository.save(board);
		return"redircet:/";
	}
	
	@GetMapping("/board")
	public String list(Model model) {
		
		List<Board> boardsEntity= boardRepository.findAll();
		model.addAttribute("boardsEntity" , boardsEntity);
		
		return "board/list";
	}
	
	@GetMapping("/board/{id}")
	public String datail(@PathVariable int id, Model model) {
		
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(()-> new MyNotFoundException("게시판번호 "+ id+"를 못찾았어요"));
		
		model.addAttribute("boardEntity", boardEntity);
		return "board/detail";
	}
	
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	
}
