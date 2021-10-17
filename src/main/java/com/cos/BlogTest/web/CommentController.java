package com.cos.BlogTest.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.BlogTest.domain.board.Board;
import com.cos.BlogTest.domain.board.BoardRepository;
import com.cos.BlogTest.domain.comment.Comment;
import com.cos.BlogTest.domain.comment.CommentRepository;
import com.cos.BlogTest.domain.user.User;
import com.cos.BlogTest.handler.ex.MyAsyncNotFoundException;
import com.cos.BlogTest.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class CommentController {
	private final HttpSession session;
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;
	
	@DeleteMapping("/comment/{id}")
		public @ResponseBody CMRespDto<?> deleteById(@PathVariable int id){
		
		User principal = (User) session.getAttribute("principal");
		if(principal == null) {
			throw new MyAsyncNotFoundException("인증되지 않은 사용자입니다");		
		}
		Comment commentEntity = commentRepository.findById(id)
		.orElseThrow(()-> new MyAsyncNotFoundException("존재하지 않는 댓글입니다"));
		if(principal.getId() != commentEntity.getUser().getId()) {
			throw new MyAsyncNotFoundException("해당 글을 삭제 할 권한이 없습니다.");
		}
		
		commentRepository.deleteById(id);
		return new CMRespDto<>(1,"성공",null);
				
	}
}

