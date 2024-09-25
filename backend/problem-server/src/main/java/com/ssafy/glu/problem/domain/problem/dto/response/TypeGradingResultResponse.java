package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;

public record TypeGradingResultResponse(
	Integer correctCount,
	CommonCodeResponse problemTypeCode,
	Integer acquiredScore,
	Integer totalScore
) {
}
