package com.ssafy.glu.problem.domain.test.dto.response;

import java.util.List;

import com.ssafy.glu.problem.domain.problem.dto.response.ProblemGradingResultResponse;
import com.ssafy.glu.problem.domain.problem.dto.response.TypeGradingResultResponse;
import com.ssafy.glu.problem.domain.test.domain.Test;
import com.ssafy.glu.problem.global.feign.dto.ExpUpdateResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "테스트 채점 응답 DTO")
public record TestGradingResponse(
	@Schema(description = "테스트 ID", example = "test-123")
	String testId,

	@Schema(description = "전체 정답 수", example = "5")
	Integer totalCorrectCount,

	@Schema(description = "전체 풀이 시간", example = "600")
	Integer totalSolvedTime,

	@Schema(description = "유형별 채점 결과 목록")
	List<TypeGradingResultResponse> gradingResultByTypeList,

	@Schema(description = "문제별 채점 결과 목록")
	List<ProblemGradingResultResponse> gradingResultByProblemList,
	// 계산

	@Schema(description = "전체 획득 점수", example = "300")
	Integer totalAcquiredScore,

	// ExpUpdateResponse
	@Schema(description = "단계 상승 여부", example = "true")
	Boolean isStageUp,

	@Schema(description = "단계 상승 URL")
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
