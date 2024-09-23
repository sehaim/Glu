package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;

import lombok.Builder;

@Builder
public record QuestionTypeCodeResponse(
	String questionTypeCode,
	String name
){
	public static QuestionTypeCodeResponse of(QuestionTypeCode questionTypeCode) {
		if (questionTypeCode == null) return null;
		return QuestionTypeCodeResponse.builder()
			.questionTypeCode(questionTypeCode.toString())
			.name(questionTypeCode.getDescription())
			.build();
	}
}
