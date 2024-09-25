package com.ssafy.glu.user.domain.user.domain;

import lombok.Getter;

@Getter
public enum ProblemTypeCode {
	PT01("어휘 및 문법"),
	PT02("독해"),
	PT03("추론");

	private final String description;

	ProblemTypeCode(String description) {
		this.description = description;
	}

	public String getCode() {
		return this.name();
	}
}
