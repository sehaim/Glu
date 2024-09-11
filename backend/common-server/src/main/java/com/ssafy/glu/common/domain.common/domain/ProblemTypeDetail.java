package com.ssafy.glu.common.domain.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ProblemTypeDetail {
	private final String problemTypeDetailCode;
	private final String name;
}
