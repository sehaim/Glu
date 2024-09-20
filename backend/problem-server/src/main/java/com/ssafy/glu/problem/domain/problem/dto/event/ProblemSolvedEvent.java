package com.ssafy.glu.problem.domain.problem.dto.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;

import lombok.Builder;

@Builder
public record ProblemSolvedEvent (
	Long userId,
	String problemId,
	boolean isCorrect,
	int acquiredScore,
	String userAnswer,
	int solvedTime
){
	public UserProblemLog toProblemLog(Problem problem) {
		return UserProblemLog.builder()
			.userId(userId)
			.problem(problem)
			.userAnswer(userAnswer)
			.isCorrect(isCorrect)
			.solvedTime(solvedTime)
			.build();
	}
}
