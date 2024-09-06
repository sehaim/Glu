package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemMemo;

public record ProblemMemoResponse(
	String problemMemoId,
	String contents
) {
	public static ProblemMemoResponse of(ProblemMemo problemMemo) {
		return new ProblemMemoResponse(problemMemo.getProblemMemoId(), problemMemo.getContents());
	}
}
