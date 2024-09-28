package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.List;
import java.util.Map;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
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

	public static ProblemGradingResultResponse of(UserProblemLog userProblemLog) {
		if (userProblemLog == null)
			return null;
		Problem problem = userProblemLog.getProblem();
		return ProblemGradingResultResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.content(problem.getContent())
			.metadata(problem.getMetadata())
			.solution(problem.getSolution())
			.isCorrect(userProblemLog.isCorrect())
			.userAnswer(userProblemLog.getUserAnswer())
			.solveTime(userProblemLog.getSolvedTime())
			.build();
	}

	public static List<ProblemGradingResultResponse> createGradingResultByProblemList(
		List<UserProblemLog> userProblemLogList) {
		return userProblemLogList.stream().map(ProblemGradingResultResponse::of).toList();
	}
}
