package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

import lombok.Builder;

@Builder
public record ProblemBaseResponse(
	String problemId,
	String title,
	String content,
	String solution,
	Problem.Status status,
	ProblemLevelResponse problemLevel,
	ProblemTypeResponse problemType,
	ProblemTypeDetailResponse problemTypeDetail
) {
	public static ProblemBaseResponse of(Problem problem, Problem.Status status) {
		return ProblemBaseResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.content(problem.getContent())
			.solution(problem.getSolution())
			.status(status)
			.problemLevel(ProblemLevelResponse.of(problem.getProblemLevel()))
			.problemType(ProblemTypeResponse.of(problem.getProblemType()))
			.problemTypeDetail(ProblemTypeDetailResponse.of(problem.getProblemTypeDetail()))
			.build();
	}
}
