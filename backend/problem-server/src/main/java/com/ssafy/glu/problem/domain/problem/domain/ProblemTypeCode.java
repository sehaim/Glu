package com.ssafy.glu.problem.domain.problem.domain;

import java.util.List;

import lombok.Getter;

@Getter
public enum ProblemTypeCode {
	PT01("어휘 및 문법", List.of(ProblemTypeDetailCode.PT0111, ProblemTypeDetailCode.PT0112, ProblemTypeDetailCode.PT0121)),
	PT02("독해", List.of(ProblemTypeDetailCode.PT0211, ProblemTypeDetailCode.PT0221, ProblemTypeDetailCode.PT0222)),
	PT03("추론", List.of(ProblemTypeDetailCode.PT0311, ProblemTypeDetailCode.PT0312, ProblemTypeDetailCode.PT0321));

	final String description;
	List<ProblemTypeDetailCode> problemTypeDetailCodeList;

	ProblemTypeCode(String description) {
		this.description = description;
	}

	ProblemTypeCode(String description, List<ProblemTypeDetailCode> problemTypeDetailCodeList) {
		this.description = description;
		this.problemTypeDetailCodeList = problemTypeDetailCodeList;
	}
}