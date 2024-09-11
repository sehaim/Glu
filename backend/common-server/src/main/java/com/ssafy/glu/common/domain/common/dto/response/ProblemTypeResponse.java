package com.ssafy.glu.common.domain.common.dto.response;

import com.ssafy.glu.common.domain.common.domain.ProblemType;

import lombok.Builder;

@Builder
public record ProblemTypeResponse(
	String problemTypeCode,
	String name
) {
	public static ProblemTypeResponse of(ProblemType problemType) {
		if (problemType == null)
			return null;
		return ProblemTypeResponse.builder()
			.problemTypeCode(problemType.getProblemTypeCode())
			.name(problemType.getName())
			.build();
	}
}
