package com.ssafy.glu.common.domain.common.domain;

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
