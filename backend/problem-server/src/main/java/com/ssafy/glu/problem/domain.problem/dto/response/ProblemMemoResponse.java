package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

import lombok.Builder;

@Builder
public record ProblemMemoResponse(
	Long memoIndex,
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
