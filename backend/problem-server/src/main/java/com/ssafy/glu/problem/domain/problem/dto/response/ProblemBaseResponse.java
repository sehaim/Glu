package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.UserProblemStatus;

import lombok.Builder;

@Builder
public record ProblemBaseResponse(
	String problemId,
	String title,
	String content,
	String answer,
	String solution,
	Problem.Status status,
	QuestionTypeCodeResponse questionTypeCode,
	ProblemLevelResponse problemLevel,
	ProblemTypeResponse problemType,
	ProblemTypeDetailResponse problemTypeDetail
) {
	public static ProblemBaseResponse of(Problem problem, Problem.Status status) {
		if(problem == null) return null;
		return ProblemBaseResponse.builder()
			.problemId(problem.getProblemId())
			.title(problem.getTitle())
			.answer(problem.getAnswer())
			.content(problem.getContent())
			.solution(problem.getSolution())
			.status(status)
			.questionTypeCode(QuestionTypeCodeResponse.of(problem.getQuestionTypeCode()))
			.problemLevel(ProblemLevelResponse.of(problem.getProblemLevelCode()))
			.problemType(ProblemTypeResponse.of(problem.getProblemTypeCode()))
			.problemTypeDetail(ProblemTypeDetailResponse.of(problem.getProblemTypeDetailCode()))
			.build();
	}
	public static ProblemBaseResponse of(Problem problem) {
		return of(problem,null);
	}

	public static ProblemBaseResponse of(UserProblemStatus userProblemStatus) {
		return of(userProblemStatus.getProblem(), userProblemStatus.getStatus());
	}
}
