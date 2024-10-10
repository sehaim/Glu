package com.ssafy.glu.problem.domain.problem.dto.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "문제 코드 응답 DTO")
public record ProblemCodeResponse(
	@Schema(description = "문제 ID", example = "problem-123")
	String problemId,

	@Schema(description = "질문 유형 코드")
	QuestionTypeCode questionTypeCode,

	@Schema(description = "문제 난이도 코드")
	ProblemLevelCode problemLevelCode,

	@Schema(description = "문제 유형 세부 코드")
	ProblemTypeDetailCode problemTypeDetailCode,

	@Schema(description = "문제 유형 코드")
	ProblemTypeCode problemTypeCode
) {
	public static ProblemCodeResponse from(Problem problem) {
		if (problem == null)
			return null;
		return ProblemCodeResponse.builder()
			.problemId(problem.getProblemId())
			.questionTypeCode(problem.getQuestionTypeCode())
			.problemLevelCode(problem.getProblemLevelCode())
			.problemTypeDetailCode(problem.getProblemTypeDetailCode())
			.problemTypeCode(problem.getProblemTypeCode())
			.build();
	}
}