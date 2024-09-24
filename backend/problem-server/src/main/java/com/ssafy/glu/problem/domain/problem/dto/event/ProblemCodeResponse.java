package com.ssafy.glu.problem.domain.problem.dto.event;

import com.ssafy.glu.problem.domain.problem.domain.Problem;
import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;

import lombok.Builder;

@Builder
public record ProblemCodeResponse(
	String problemId,
	QuestionTypeCode questionTypeCode,
	ProblemLevelCode problemLevelCode,
	ProblemTypeDetailCode problemTypeDetailCode,
	ProblemTypeCode problemTypeCode
) {
	public static ProblemCodeResponse from(Problem problem) {
		if(problem == null)
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