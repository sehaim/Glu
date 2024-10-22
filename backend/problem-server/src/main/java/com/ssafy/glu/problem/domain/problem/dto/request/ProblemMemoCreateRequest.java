package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "문제 메모 생성 요청 DTO")
public record ProblemMemoCreateRequest(
	@Schema(description = "메모 내용", example = "이 문제에 대한 메모입니다.")
	String content
) {
	public ProblemMemo toDocument(Long memoIndex) {
		return ProblemMemo.builder()
			.memoIndex(memoIndex)
			.content(content)
			.build();
	}
}
