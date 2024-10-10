package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.List;
import java.util.Map;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

import lombok.Builder;

@Builder
public record ProblemGradingResultResponse(
	String problemId,
	String title,
	String content,
	String answer,

	// 문제 Option 등이 담긴 정보
	Map<String, Object> metadata,
	String solution,

	Boolean isCorrect,

	String userAnswer,
	Integer solveTime,

	// 타입 관련 정보
	CommonCodeResponse questionType,
	CommonCodeResponse problemLevel,
	CommonCodeResponse problemTypeDetail,
	CommonCodeResponse problemType
) {
	public static ProblemGradingResultResponse of(Problem problem, Boolean isCorrect,
		ProblemSolveRequest problemSolveRequest) {
		return ProblemGradingResultResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.content(problem.getContent())
			.answer(problem.getAnswer())

			.metadata(problem.getMetadata())
			.solution(problem.getSolution())

			.isCorrect(isCorrect)

			.userAnswer(problemSolveRequest.userAnswer())
			.solveTime(problemSolveRequest.solvedTime())

			.questionType(CommonCodeResponse.of(problem.getQuestionTypeCode()))
			.problemLevel(CommonCodeResponse.of(problem.getProblemLevelCode()))
			.problemType(CommonCodeResponse.of(problem.getProblemTypeCode()))
			.problemTypeDetail(CommonCodeResponse.of(problem.getProblemTypeDetailCode()))

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
			.answer(problem.getAnswer())

			.metadata(problem.getMetadata())
			.solution(problem.getSolution())
			.isCorrect(userProblemLog.isCorrect())
			.userAnswer(userProblemLog.getUserAnswer())
			.solveTime(userProblemLog.getSolvedTime())

			.questionType(CommonCodeResponse.of(problem.getQuestionTypeCode()))
			.problemLevel(CommonCodeResponse.of(problem.getProblemLevelCode()))
			.problemType(CommonCodeResponse.of(problem.getProblemTypeCode()))
			.problemTypeDetail(CommonCodeResponse.of(problem.getProblemTypeDetailCode()))

			.build();
	}

	public static List<ProblemGradingResultResponse> createGradingResultByProblemList(
		List<UserProblemLog> userProblemLogList) {
		return userProblemLogList.stream().map(ProblemGradingResultResponse::of).toList();
	}
}
