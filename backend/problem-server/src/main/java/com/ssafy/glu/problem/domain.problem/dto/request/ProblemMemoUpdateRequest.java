package com.ssafy.glu.problem.domain.problem.dto.request;

public record ProblemMemoUpdateRequest(
	Long memoIndex,
	String content
) {
}