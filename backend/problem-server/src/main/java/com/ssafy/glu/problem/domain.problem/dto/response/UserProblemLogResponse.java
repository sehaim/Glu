package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

import lombok.Builder;

@Builder
public record UserProblemLogResponse(
	String problemId,
	String title,
	String content,
	String solution,
	Problem.Status status,
	ProblemLevelResponse problemLevel,
	ProblemTypeResponse problemType,
	ProblemTypeDetailResponse problemTypeDetail
) {
	public static UserProblemLogResponse of(Problem problem, Problem.Status status) {
		return UserProblemLogResponse.builder()
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
