package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

import lombok.Builder;

@Builder
public record ProblemMemoResponse(
	String problemMemoId,
	String content
) {
	public static ProblemMemoResponse of(ProblemMemo problemMemo) {
		if (problemMemo == null)
			return null;
		return ProblemMemoResponse.builder()
			.problemMemoId(problemMemo.getProblemMemoId())
			.content(problemMemo.getContent())
			.build();
	}
}
