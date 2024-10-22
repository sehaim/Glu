package com.ssafy.glu.problem.domain.problem.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.ssafy.glu.problem.domain.common.dto.CommonCodeResponse;
import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 기본 응답 DTO")
public record ProblemBaseResponse(
	@Schema(description = "문제 ID", example = "problem-123")
	String problemId,

	@Schema(description = "문제 제목", example = "문제 제목")
	String title,

	@Schema(description = "문제 내용", example = "문제 내용이 무엇인가?")
	String content,

	@Schema(description = "정답", example = "2")
	String answer,

	@Schema(description = "문제 해설")
	String solution,

	@Schema(description = "문제 상태")
	Problem.Status status,

	@Schema(description = "질문 유형")
	CommonCodeResponse questionType,

	@Schema(description = "문제 난이도")
	CommonCodeResponse problemLevel,

	@Schema(description = "문제 유형")
	CommonCodeResponse problemType,

	@Schema(description = "문제 유형 세부")
	CommonCodeResponse problemTypeDetail,

	@Schema(description = "메타데이터")
	Map<String, Object> metadata,

	@Schema(description = "즐겨찾기 여부", example = "true")
	Boolean isFavorite,

	@Schema(description = "풀이 시간", example = "120")
	Integer solveTime,

	@Schema(description = "풀이 날짜")
	LocalDateTime solveDate
) {

	public static ProblemBaseResponse of(Problem problem, Problem.Status status, Boolean isFavorite, Integer solveTime,
		LocalDateTime solveDate) {
		if (problem == null)
			return null;
		return ProblemBaseResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.answer(problem.getAnswer())
			.content(problem.getContent())
			.solution(problem.getSolution())
			.status(status)
			.questionType(CommonCodeResponse.of(problem.getQuestionTypeCode()))
			.problemLevel(CommonCodeResponse.of(problem.getProblemLevelCode()))
			.problemType(CommonCodeResponse.of(problem.getProblemTypeCode()))
			.problemTypeDetail(CommonCodeResponse.of(problem.getProblemTypeDetailCode()))
			.metadata(problem.getMetadata())
			.isFavorite(isFavorite)
			.solveTime(solveTime)
			.solveDate(solveDate)
			.build();
	}

	public static ProblemBaseResponse of(Problem problem, Problem.Status status, Boolean isFavorite,
		Integer solveTime) {
		return of(problem, status, isFavorite, solveTime, null);
	}

	public static ProblemBaseResponse of(Problem problem, Problem.Status status, Boolean isFavorite) {
		return of(problem, status, isFavorite, null);
	}

	public static ProblemBaseResponse of(Problem problem, Problem.Status status) {
		return of(problem, status, null);
	}

	public static ProblemBaseResponse of(Problem problem) {
		return of(problem, null);
	}

	public static ProblemBaseResponse of(UserProblemStatus userProblemStatus) {
		return of(userProblemStatus.getProblem(), userProblemStatus.getStatus(), userProblemStatus.getIsFavorite(),
			userProblemStatus.getSolveTime(), userProblemStatus.getModifiedDate());
	}
}
