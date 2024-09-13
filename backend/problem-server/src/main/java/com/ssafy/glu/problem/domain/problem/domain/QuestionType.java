package com.ssafy.glu.problem.domain.problem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class QuestionType {
	private final String questionTypeCode;
	private final String name;
}