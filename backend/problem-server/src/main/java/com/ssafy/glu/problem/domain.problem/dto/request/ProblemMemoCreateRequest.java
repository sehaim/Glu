package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

public record ProblemMemoCreateRequest (
	String content
){
	public ProblemMemo toDocument(Long userId, Problem problem) {
		return ProblemMemo.builder()
			.content(content)
			.userId(userId)
			.problem(problem)
			.build();
	}
}
