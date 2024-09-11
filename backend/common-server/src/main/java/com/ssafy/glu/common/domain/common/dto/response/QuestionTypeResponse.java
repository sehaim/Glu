package com.ssafy.glu.common.domain.common.dto.response;

import com.ssafy.glu.common.domain.common.domain.QuestionType;

import lombok.Builder;

@Builder
public record QuestionTypeResponse(
	String questionTypeCode,
	String name
) {
	public static QuestionTypeResponse of(QuestionType questionType) {
		if (questionType == null)
			return null;
		return QuestionTypeResponse.builder()
			.questionTypeCode(builder().questionTypeCode)
			.name(questionType.getName())
			.build();
	}
}
