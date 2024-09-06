package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemType;
import com.ssafy.glu.problem.domain.problem.domain.ProblemTypeDetail;

import lombok.Builder;

@Builder
public record ProblemTypeResponse(
	String problemTypeCode,
	String name
){
	public static ProblemTypeResponse of(ProblemType problemType) {
		if(problemType == null) return null;
		return ProblemTypeResponse.builder()
			.problemTypeCode(problemType.getProblemTypeCode())
			.name(problemType.getName())
			.build();
	}
}
