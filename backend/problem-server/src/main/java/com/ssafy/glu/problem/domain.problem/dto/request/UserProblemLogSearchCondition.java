package com.ssafy.glu.problem.domain.problem.dto.request;

public record UserProblemLogSearchCondition(
	String status,
	String problemTypeCode
) {
}