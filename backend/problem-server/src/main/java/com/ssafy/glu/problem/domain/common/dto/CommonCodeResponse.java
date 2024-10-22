package com.ssafy.glu.problem.domain.common.dto;

import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;
import com.ssafy.glu.problem.domain.problem.domain.QuestionTypeCode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "공통 코드 응답 DTO")
public record CommonCodeResponse(
	@Schema(description = "코드 값", example = "PL01")
	String code,

	@Schema(description = "코드 이름", example = "유아")
	String name
) {
	public static CommonCodeResponse of(ProblemLevelCode problemLevelCode) {
		if (problemLevelCode == null)
			return null;
		return CommonCodeResponse.builder()
			.code(problemLevelCode.toString())
			.name(problemLevelCode.getDescription())
			.build();
	}

	public static CommonCodeResponse of(ProblemTypeDetailCode problemTypeDetailCode) {
		if (problemTypeDetailCode == null)
			return null;
		return CommonCodeResponse.builder()
			.code(problemTypeDetailCode.toString())
			.name(problemTypeDetailCode.getDescription())
			.build();
	}

	public static CommonCodeResponse of(ProblemTypeCode problemTypeCode) {
		if (problemTypeCode == null)
			return null;
		return CommonCodeResponse.builder()
			.code(problemTypeCode.toString())
			.name(problemTypeCode.getDescription())
			.build();
	}

	public static CommonCodeResponse of(QuestionTypeCode questionTypeCode) {
		if (questionTypeCode == null)
			return null;
		return CommonCodeResponse.builder()
			.code(questionTypeCode.toString())
			.name(questionTypeCode.getDescription())
			.build();
	}
}

