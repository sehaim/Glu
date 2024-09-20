package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;

import lombok.Builder;

@Builder
public record ProblemGradingResponse(
	boolean isCorrect,
	Integer acquiredScore,
	Integer totalScore,
	boolean isLevelUp,
	String levelUpUrl
) {
	public static ProblemGradingResponse of(GradeResult gradeResult) {
		return ProblemGradingResponse.builder()
			.isCorrect(gradeResult.isCorrect())
			.acquiredScore(gradeResult.acquiredScore())
			.totalScore(gradeResult.updatedUserScore())
			.build();
	}
}
