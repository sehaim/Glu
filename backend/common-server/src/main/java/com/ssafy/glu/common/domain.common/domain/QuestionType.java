package com.ssafy.glu.common.domain.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class QuestionType {
	private final String questionTypeCode;
	private final String name;
}
