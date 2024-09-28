package com.ssafy.glu.problem.global.feign.dto;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;

import lombok.Builder;

@Builder
public record ProblemInfo(
	int level,
	ProblemTypeCode code
) {
	public static ProblemInfo from(Problem problem) {
		return ProblemInfo.builder()
			.level(problem.level())
			.code(problem.getProblemTypeCode())
			.build();
	}

	public static ProblemInfo from(GradeResult gradeResult) {
		return ProblemInfo.builder()
			.level(gradeResult.problemLevel())
			.code(gradeResult.problemTypeCode())
			.build();
	}
}
