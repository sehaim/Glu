package com.ssafy.glu.problem.domain.problem.dto.response;

import com.ssafy.glu.problem.domain.problem.domain.ProblemLevel;

import lombok.Builder;

@Builder
public record ProblemLevelResponse (
	String problemLevelCode,
	String name
){
	public static ProblemLevelResponse of(ProblemLevel problemLevel) {
		if (problemLevel == null) return null;
		return ProblemLevelResponse.builder()
			.problemLevelCode(problemLevel.getProblemLevelCode())
			.name(problemLevel.getName())
			.build();
	}
}