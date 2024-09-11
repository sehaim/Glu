package com.ssafy.glu.common.domain.common.dto.response;

import com.ssafy.glu.common.domain.common.domain.ProblemTypeDetail;

import lombok.Builder;

@Builder
public record ProblemTypeDetailResponse(
	String problemTypeDetailCode,
	String name
) {
	public static ProblemTypeDetailResponse of(ProblemTypeDetail problemTypeDetail) {
		if (problemTypeDetail == null)
			return null;
		return ProblemTypeDetailResponse.builder()
			.problemTypeDetailCode(builder().problemTypeDetailCode)
			.name(problemTypeDetail.getName())
			.build();
	}
}
