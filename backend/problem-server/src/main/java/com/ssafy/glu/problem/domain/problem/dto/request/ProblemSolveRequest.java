package com.ssafy.glu.problem.domain.problem.dto.request;

public record ProblemSolveRequest(
	String userAnswer,
	Integer solvedTime
) {
}