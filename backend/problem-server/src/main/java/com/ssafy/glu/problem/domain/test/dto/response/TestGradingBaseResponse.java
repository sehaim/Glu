package com.ssafy.glu.problem.domain.test.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.response.TypeGradingResultResponse;
import com.ssafy.glu.problem.domain.test.domain.Test;

import lombok.Builder;

@Builder
public record TestGradingBaseResponse(
	// Test
	String testId,
	Integer totalCorrectCount,
	Integer totalSolvedTime,
	LocalDateTime createdDate,

	List<TypeGradingResultResponse> gradingResultByTypeList
) {
	public static TestGradingBaseResponse of(Test test, List<UserProblemLog> userProblemLogList) {
		if (test == null)
			return null;
		return TestGradingBaseResponse.builder()
			.testId(test.getTestId())
			.totalCorrectCount(test.getCorrectCount())
			.totalSolvedTime(test.getTotalSolveTime())
			.createdDate(test.getCreatedDate())
			.gradingResultByTypeList(TypeGradingResultResponse.createGradingResultByTypeList(userProblemLogList))
			.build();
	}
}
