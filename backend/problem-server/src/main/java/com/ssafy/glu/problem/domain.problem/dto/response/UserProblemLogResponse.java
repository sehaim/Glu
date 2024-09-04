package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.Problem;

public record UserProblemLogResponse(
	String problemId,
	String title,
	String content,
	String solution,
	Problem.Status status

) {
}
