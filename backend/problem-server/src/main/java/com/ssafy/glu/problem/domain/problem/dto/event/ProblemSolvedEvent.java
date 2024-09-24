package com.ssafy.glu.problem.domain.problem.dto.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.exception.event.EventDataCreationException;

import lombok.Builder;

@Builder
public record ProblemSolvedEvent(
	Long userId,
	ProblemCodeResponse problem,
	GradeResult result,
	String userAnswer,
	int solvedTime
) {
	public static ProblemSolvedEvent of(Long userId, Problem problem, GradeResult gradeResult,
		ProblemSolveRequest request) {
		if (problem == null || gradeResult == null) throw new EventDataCreationException();
		return ProblemSolvedEvent.builder()
			.userId(userId)
			.problem(ProblemCodeResponse.from(problem))
			.result(gradeResult)
			.userAnswer(request.userAnswer())
			.solvedTime(request.solvedTime())
			.build();
	}

	public UserProblemLog toProblemLog(Problem problem) {
		return UserProblemLog.builder()
			.userId(userId)
			.problem(problem)
			.userAnswer(userAnswer)
			.isCorrect(isCorrect())
			.solvedTime(solvedTime)
			.build();
	}

	public String problemId() {
		return problem.problemId();
	}

	public boolean isCorrect() {
		return result.isCorrect();
	}
}
