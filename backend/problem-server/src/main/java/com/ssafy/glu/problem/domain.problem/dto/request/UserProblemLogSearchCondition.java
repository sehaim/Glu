package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

public record UserProblemLogSearchCondition(
	Problem.Status status,
	String problemTypeCode
) {
}