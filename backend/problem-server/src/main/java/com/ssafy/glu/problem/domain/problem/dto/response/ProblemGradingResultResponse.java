package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.Map;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

import lombok.Builder;

@Builder
public record ProblemGradingResultResponse(
	String problemId,
	String title,
	String content,

	// 문제 Option 등이 담긴 정보
	Map<String, Object> metadata,
	String solution,

	Boolean isCorrect,

	String userAnswer,
	Integer solveTime
) {
	public static ProblemGradingResultResponse of(String problemId, String title, String content,
		Map<String, Object> metadata,
		Boolean isCorrect, String userAnswer, String solution, Integer solveTime) {
		return ProblemGradingResultResponse.builder()
			.problemId(problemId)
			.title(title)
			.content(content)
			.metadata(metadata)
			.isCorrect(isCorrect)
			.userAnswer(userAnswer)
			.solution(solution)
			.solveTime(solveTime)
			.build();
	}

	public static ProblemGradingResultResponse of(Problem problem, Boolean isCorrect,
		ProblemSolveRequest problemSolveRequest) {
		return ProblemGradingResultResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.content(problem.getContent())
			.metadata(problem.getMetadata())
			.solution(problem.getSolution())

			.isCorrect(isCorrect)

			.userAnswer(problemSolveRequest.userAnswer())
			.solveTime(problemSolveRequest.solvedTime())
			.build();
	}
}
