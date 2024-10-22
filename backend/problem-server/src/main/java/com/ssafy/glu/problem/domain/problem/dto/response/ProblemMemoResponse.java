package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 메모 응답 DTO")
public record ProblemMemoResponse(
	@Schema(description = "메모 인덱스", example = "1")
	Long memoIndex,

	@Schema(description = "메모 내용", example = "이 문제에 대한 메모입니다.")
	String content
) {
	public static ProblemMemoResponse of(Long memoIndex, String content) {
		return ProblemMemoResponse.builder()
			.memoIndex(memoIndex)
			.content(content)
			.build();
	}

	public static ProblemMemoResponse of(ProblemMemo memo) {
		return of(memo.getMemoIndex(), memo.getContent());
	}
}
