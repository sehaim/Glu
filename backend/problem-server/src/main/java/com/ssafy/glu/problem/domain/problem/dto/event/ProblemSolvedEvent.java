package com.ssafy.glu.problem.domain.problem.dto.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.grading.GradeResult;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;
import com.ssafy.glu.problem.domain.problem.exception.event.EventDataCreationException;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 풀이 이벤트 DTO")
public record ProblemSolvedEvent(
	@Schema(description = "사용자 ID", example = "1")
	Long userId,

	@Schema(description = "테스트 ID", example = "test-123")
	String testId,

	@Schema(description = "문제 코드 응답")
	ProblemCodeResponse problem,

	@Schema(description = "채점 결과")
	GradeResult result,

	@Schema(description = "사용자 답변", example = "2")
	String userAnswer,

	@Schema(description = "풀이 시간", example = "120")
	int solvedTime
) {
	public static ProblemSolvedEvent of(Long userId, Problem problem, GradeResult gradeResult,
		ProblemSolveRequest request) {
		if (problem == null || gradeResult == null)
			throw new EventDataCreationException();
		return ProblemSolvedEvent.builder()
			.userId(userId)
			.problem(ProblemCodeResponse.from(problem))
			.result(gradeResult)
			.userAnswer(request.userAnswer())
			.solvedTime(request.solvedTime())
			.build();
	}

	public static ProblemSolvedEvent of(Long userId, String testId, Problem problem, GradeResult gradeResult,
		ProblemSolveRequest request) {
		if (problem == null || gradeResult == null)
			throw new EventDataCreationException();
		return ProblemSolvedEvent.builder()
			.userId(userId)
			.testId(testId)
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
