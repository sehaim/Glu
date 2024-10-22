package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.global.feign.dto.ExpUpdateResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 채점 응답 DTO")
public record ProblemGradingResponse(
	@Schema(description = "정답 여부", example = "true")
	boolean isCorrect,

	@Schema(description = "문제 유형")
	CommonCodeResponse problemType,

	@Schema(description = "획득 점수", example = "100")
	Integer acquiredScore,

	@Schema(description = "총 점수", example = "250")
	Integer totalScore,

	@Schema(description = "단계 상승 여부", example = "true")
	boolean isStageUp,

	@Schema(description = "단계 상승 URL")
	String stageUpUrl
) {
	public static ProblemGradingResponse of(GradeResult gradeResult) {
		return ProblemGradingResponse.builder()
			.isCorrect(gradeResult.isCorrect())
			.problemType(CommonCodeResponse.of(gradeResult.problemTypeCode()))
			.acquiredScore(gradeResult.acquiredScore())
			.totalScore(gradeResult.totalUserScore())
			.build();
	}

	public static ProblemGradingResponse of(GradeResult gradeResult, ExpUpdateResponse expUpdateResponse) {
		return ProblemGradingResponse.builder()
			.isCorrect(gradeResult.isCorrect())
			.problemType(CommonCodeResponse.of(gradeResult.problemTypeCode()))
			.acquiredScore(gradeResult.acquiredScore())
			.totalScore(gradeResult.totalUserScore())
			.isStageUp(expUpdateResponse.isStageUp())
			.stageUpUrl(expUpdateResponse.stageUpUrl())
			.build();
	}
}