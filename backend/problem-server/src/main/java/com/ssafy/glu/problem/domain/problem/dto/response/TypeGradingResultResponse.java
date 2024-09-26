package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;

import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import lombok.Builder;

@Builder
public record TypeGradingResultResponse(
	Integer correctCount,
	CommonCodeResponse problemType,
	Integer acquiredScore,
	Integer totalScore
) {
	public static TypeGradingResultResponse of(Integer correctCount, CommonCodeResponse problemType, Integer acquiredScore, Integer totalScore) {
		return TypeGradingResultResponse.builder()
			.correctCount(correctCount)
			.problemType(problemType)
			.acquiredScore(acquiredScore)
			.totalScore(totalScore)
			.build();
	}

//	public static TypeGradingResultResponse of(UserProblemLog userProblemLog) {
//		return TypeGradingResultResponse.builder()
//				.correctCount(correctCount)
//				.problemTypeCode(problemTypeCode)
//				.acquiredScore(acquiredScore)
//				.totalScore(totalScore)
//				.build();
//	}
}
