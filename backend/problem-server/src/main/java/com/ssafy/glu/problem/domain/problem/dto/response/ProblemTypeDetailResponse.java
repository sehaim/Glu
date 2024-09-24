package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetailCode;

import lombok.Builder;

@Builder
public record ProblemTypeDetailResponse(
	String problemTypeDetailCode,
	String name
){
	public static ProblemTypeDetailResponse of(ProblemTypeDetailCode problemTypeDetailCode) {
		if(problemTypeDetailCode == null) return null;
		return ProblemTypeDetailResponse.builder()
			.problemTypeDetailCode(problemTypeDetailCode.toString())
			.name(problemTypeDetailCode.getDescription())
			.build();
	}
}