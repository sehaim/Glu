package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;

public record TypeGradingResultResponse(
	Integer correctCount,
	ProblemTypeCode problemTypeCode
) {
}
