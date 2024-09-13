package com.ssafy.glu.problem.domain.problem.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProblemLevel {
	private final String problemLevelCode;
	private final String name;
}
