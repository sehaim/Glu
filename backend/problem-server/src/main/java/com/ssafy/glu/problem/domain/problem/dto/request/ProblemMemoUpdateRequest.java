package com.ssafy.glu.problem.domain.problem.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문제 메모 수정 요청 DTO")
public record ProblemMemoUpdateRequest(
	@Schema(description = "메모 인덱스", example = "1")
	Long memoIndex,

	@Schema(description = "수정할 내용", example = "수정된 메모 내용")
	String content
) {
}