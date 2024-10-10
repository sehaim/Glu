package com.ssafy.glu.problem.domain.problem.dto.response;

import java.util.List;
import java.util.Map;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemLog;
import com.ssafy.glu.problem.domain.problem.dto.request.ProblemSolveRequest;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 채점 결과 응답 DTO")
public record ProblemGradingResultResponse(
	@Schema(description = "문제 ID", example = "problem-123")
	String problemId,

	@Schema(description = "문제 제목", example = "수학 문제")
	String title,

	@Schema(description = "문제 내용", example = "1 + 1은 얼마인가?")
	String content,

	@Schema(description = "정답", example = "2")
	String answer,

	// 문제 Option 등이 담긴 정보
	@Schema(description = "문제 옵션 등을 담은 정보")
	Map<String, Object> metadata,

	@Schema(description = "문제 해설")
	String solution,

	@Schema(description = "정답 여부", example = "true")
	Boolean isCorrect,

	@Schema(description = "사용자 답변", example = "2")
	String userAnswer,

	@Schema(description = "풀이 시간", example = "120")
	Integer solveTime,

	// 타입 관련 정보
	@Schema(description = "질문 유형", example = "객관식")
	CommonCodeResponse questionType,

	@Schema(description = "문제 난이도", example = "PL01")
	CommonCodeResponse problemLevel,

	@Schema(description = "문제 유형", example = "PT01")
	CommonCodeResponse problemType,

	@Schema(description = "문제 유형 세부", example = "PT0111")
	CommonCodeResponse problemTypeDetail
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
