package com.ssafy.glu.problem.domain.problem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문제 풀이 요청 DTO")
public record ProblemSolveRequest(
	@Schema(description = "문제 ID", example = "problem-123")
	String problemId,

	@Schema(description = "사용자 답변", example = "2")
	String userAnswer,

	@Schema(description = "풀이 시간", example = "120")
	Integer solvedTime
) {
}