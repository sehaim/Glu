package com.ssafy.glu.problem.domain.test.dto.response;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResultResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.TypeGradingResultResponse;

public record TestGradingResponse(
	String testId,
	Integer totalCorrectCount,
	Integer totalSolvedTime,

	List<TypeGradingResultResponse> gradingResultByTypeList,

	List<ProblemGradingResultResponse> gradingResultByProblemList,

	Integer totalAcquiredScore,
	Boolean isStageUp,
	String stageUpUrl
) {

}
