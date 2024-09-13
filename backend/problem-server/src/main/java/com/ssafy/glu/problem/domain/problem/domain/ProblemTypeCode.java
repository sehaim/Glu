package com.ssafy.glu.problem.domain.problem.domain;

import lombok.Getter;

@Getter
public enum ProblemTypeCode {
	PT01("어휘 및 문법"),
	PT02("독해"),
	PT03("추론");

	final String description;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	ProblemTypeCode(String description) {
		this.description = description;
	}
}