package com.ssafy.glu.problem.domain.problem.dto.request;

import lombok.Builder;

@Builder
public record ProblemSearchCondition(
	String problemTypeCode,
	String ProblemTypeDetailCode,
	String problemLevelCode
) {
}