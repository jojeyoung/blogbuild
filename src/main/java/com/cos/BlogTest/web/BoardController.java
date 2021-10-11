package com.cos.BlogTest.web;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.basic.BasicSplitPaneUI.KeyboardResizeToggleHandler;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.domain.board.Board;
import com.cos.BlogTest.domain.board.BoardRepository;
import com.cos.BlogTest.domain.user.User;
import com.cos.BlogTest.handler.ex.MyAsyncNotFoundException;
import com.cos.BlogTest.handler.ex.MyNotFoundException;
import com.cos.BlogTest.web.dto.BoardSaveReqDto;
import com.cos.BlogTest.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class BoardController {

	private final BoardRepository boardRepository;
	private final HttpSession session;
	
	@DeleteMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> deleteById(@PathVariable int id) {
		
		User principal = (User) session.getAttribute("principal");
		//로그인 된 사람
		if(principal == null) {
			throw new MyAsyncNotFoundException("인증이 되지 않았습니다.");
		}
		
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(()-> new MyAsyncNotFoundException("해당 글을 찾을 수 없습니다."));
		if(principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해당 글을 삭제 할 권한이 없습니다.");
		}
		try {
			boardRepository.deleteById(id);
		} catch(Exception e) {
			throw new MyAsyncNotFoundException(id+"를 찾을 수 없어서 삭제할 수 없습니다");
		}
		return new CMRespDto<String>(1, "성공", null);
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String boardUpdateForm(@PathVariable int id, Model model) {
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(()-> new MyNotFoundException(id + "번호의 게시물을 찾을 수 없습니다" ));
		
		
		model.addAttribute("boardEntity", boardEntity);
		return "board/updateForm";
	}
	
	@PutMapping("/board/{id}")
	public @ResponseBody CMRespDto<String> update(@PathVariable int id, @RequestBody @Valid BoardSaveReqDto dto, BindingResult bindingResult){
		
		//유효성 검사(공통로직)
		if (bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<>();
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}
			throw new MyAsyncNotFoundException(errorMap.toString());
		}

		//인증
		User principal = (User) session.getAttribute("principal");
		if(principal == null) {
			throw new MyAsyncNotFoundException("인증이 되지 않았습니다.");
		}
		Board boardEntity = boardRepository.findById(id)
				.orElseThrow(()->new MyAsyncNotFoundException("해당 게실글을 찾을 수 없습니다"));
		
		if(principal.getId() != boardEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해달 게시물의 권한이 없습니다");
		}
		
		Board board = dto.toEntity();
		board.setUser(principal);
		board.setId(id);
		boardRepository.save(board);
		
		return new CMRespDto<>(1, "업데이트 성공", null);
	}
	
	@PostMapping("/board")
	public String save(BoardSaveReqDto dto) {
		
		Board board = dto.toEntity();
		User principal = (User) session.getAttribute("principal");
		board.setUser(principal);
		boardRepository.save(board);
		return "redirect:/";
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
