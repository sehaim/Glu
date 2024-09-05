package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

import lombok.Builder;

@Builder
public record UserProblemLogSearchCondition(
	Problem.Status status,
	String problemTypeCode
) {
}