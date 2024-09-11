package com.ssafy.glu.common.domain.common.dto.response;

import com.ssafy.glu.common.domain.common.domain.ProblemLevel;

import lombok.Builder;

@Builder
public record ProblemLevelResponse(
	String problemLevelCode,
	String name
) {
	public static ProblemLevelResponse of(ProblemLevel problemLevel) {
		if (problemLevel == null)
			return null;
		return ProblemLevelResponse.builder()
			.problemLevelCode(problemLevel.getProblemLevelCode())
			.name(problemLevel.getName())
			.build();
	}
}
