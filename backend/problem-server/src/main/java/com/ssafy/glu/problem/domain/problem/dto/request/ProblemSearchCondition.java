package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;

import lombok.Builder;

@Builder
public record ProblemSearchCondition(
	Problem.Status status,
	ProblemTypeCode problemTypeCode,
	ProblemTypeDetailCode problemTypeDetailCode,
	ProblemLevelCode problemLevelCode,
	Boolean hasMemo,
	Boolean isFavorite
) {
}