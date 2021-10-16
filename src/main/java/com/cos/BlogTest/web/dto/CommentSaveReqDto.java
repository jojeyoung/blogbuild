package com.cos.BlogTest.web.dto;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentSaveReqDto {
	@NotBlank
	@Size(min = 1, max = 255)
	private String content;
}
