package com.cos.BlogTest.web.dto;

import com.cos.BlogTest.domain.board.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class BoardSaveReqDto {
	
	private String title;
	private String content;
	
	public  Board toEntity() {
		//장점: 순서안지켜도 됨, 안넣고 싶은건 안넣어도 된다.
		Board board = Board.builder()
				.title(title)
				.content(content)
				.build();
		return board;
	}
}
