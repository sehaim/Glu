package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.List;

public record TestGradingResponse(
	String testId,
	Integer totalCorrectCount,
	Integer totalSolvedTime,

	List<TypeGradingResultResponse> typeGradingResultResponsesList,

	List<ProblemGradingResultResponse> problemGradingResultResponsesList,

	Integer acquiredScore,
	Integer totalScore,
	Boolean isStageUp,
	String strageUrl
) {

}
