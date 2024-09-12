package com.ssafy.glu.problem.domain.problem.dto.request;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;

public record ProblemSolveRequest(
	String problemAnswer,

	String userAnswer,

	Integer solvedTime
) {
	public UserProblemLog toDocument(Long userId, Problem problem, boolean isCorrect) {
		return UserProblemLog.builder()
			.userId(userId)
			.problem(problem)
			.solvedTime(solvedTime)
			.userAnswer(userAnswer)
			.isCorrect(isCorrect)
			.build();
	}
}
