package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemLevelCode;

import lombok.Builder;

@Builder
public record ProblemLevelResponse (
	String problemLevelCode,
	String name
){
	public static ProblemLevelResponse of(ProblemLevelCode problemLevelCode) {
		if (problemLevelCode == null) return null;
		return ProblemLevelResponse.builder()
			.problemLevelCode(problemLevelCode.toString())
			.name(problemLevelCode.getDescription())
			.build();
	}
}