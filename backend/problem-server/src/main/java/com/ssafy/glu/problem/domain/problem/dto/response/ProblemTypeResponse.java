package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeCode;

import lombok.Builder;

@Builder
public record ProblemTypeResponse(
	String problemTypeCode,
	String name
){
	public static ProblemTypeResponse of(ProblemTypeCode problemTypeCode) {
		if(problemTypeCode == null) return null;
		return ProblemTypeResponse.builder()
			.problemTypeCode(problemTypeCode.toString())
			.name(problemTypeCode.getDescription())
			.build();
	}
}
