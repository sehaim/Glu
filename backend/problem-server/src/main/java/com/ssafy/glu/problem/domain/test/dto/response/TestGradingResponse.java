package com.ssafy.glu.problem.domain.test.dto.response;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResultResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.TypeGradingResultResponse;
import com.ssafy.glu.problem.domain.test.domain.Test;
import com.ssafy.glu.problem.global.feign.dto.ExpUpdateResponse;

import lombok.Builder;

@Builder
public record TestGradingResponse(
	// Test
	String testId,
	Integer totalCorrectCount,
	Integer totalSolvedTime,

	List<TypeGradingResultResponse> gradingResultByTypeList,
	List<ProblemGradingResultResponse> gradingResultByProblemList,
	// 계산
	Integer totalAcquiredScore,

	// ExpUpdateResponse
	Boolean isStageUp,
	String stageUpUrl
) {

	public static TestGradingResponse of(Test test, List<TypeGradingResultResponse> gradingResultByTypeList,
		List<ProblemGradingResultResponse> gradingResultByProblemList, Integer totalAcquiredScore,
		ExpUpdateResponse expUpdateResponse) {
		return TestGradingResponse.builder()
			.testId(test.getTestId())
			.totalCorrectCount(test.getCorrectCount())
			.totalSolvedTime(test.getTotalSolveTime())
			.gradingResultByTypeList(gradingResultByTypeList)
			.gradingResultByProblemList(gradingResultByProblemList)
			.totalAcquiredScore(totalAcquiredScore)
			.isStageUp(expUpdateResponse.isStageUp())
			.stageUpUrl(expUpdateResponse.stageUpUrl())
			.build();
	}

}
