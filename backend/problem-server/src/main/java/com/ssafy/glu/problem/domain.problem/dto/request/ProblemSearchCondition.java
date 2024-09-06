package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

import lombok.Builder;

@Builder
public record ProblemSearchCondition(
	Problem.Status status,
	String problemTypeCode,
	String ProblemTypeDetailCode,
	String problemLevelCode
) {
}