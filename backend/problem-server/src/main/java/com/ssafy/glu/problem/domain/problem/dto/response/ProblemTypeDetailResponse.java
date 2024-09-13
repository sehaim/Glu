package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetail;

import lombok.Builder;

@Builder
public record ProblemTypeDetailResponse(
	String problemTypeDetailCode,
	String name
){
	public static ProblemTypeDetailResponse of(ProblemTypeDetail problemTypeDetail) {
		if(problemTypeDetail == null) return null;
		return ProblemTypeDetailResponse.builder()
			.problemTypeDetailCode(problemTypeDetail.getProblemTypeDetailCode())
			.name(problemTypeDetail.getName())
			.build();
	}
}