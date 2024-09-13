package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

public record ProblemMemoCreateRequest(
	String content
){
	public ProblemMemo toDocument(Long memoIndex) {
		return ProblemMemo.builder()
			.memoIndex(memoIndex)
			.content(content)
			.build();
	}
}
