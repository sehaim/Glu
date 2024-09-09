package com.ssafy.glu.problem.domain.problem.dto.response;

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
}
