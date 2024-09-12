package com.ssafy.glu.problem.domain.problem.dto.response;

import lombok.Builder;

@Builder
public record ProblemGradingResponse(
	boolean isCorrect,
	Integer acquiredScore,
	Integer totalScore,
	boolean isLevelUp,
	String levelUpUrl
) {
}
