package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;

import lombok.Builder;

@Builder
public record TypeGradingResultResponse(
	Integer correctCount,
	CommonCodeResponse problemTypeCode,
	Integer acquiredScore,
	Integer totalScore
) {
	public static TypeGradingResultResponse of(Integer correctCount, CommonCodeResponse problemTypeCode, Integer acquiredScore, Integer totalScore) {
		return TypeGradingResultResponse.builder()
			.correctCount(correctCount)
			.problemTypeCode(problemTypeCode)
			.acquiredScore(acquiredScore)
			.totalScore(totalScore)
			.build();
	}
}
