package com.ssafy.glu.problem.domain.problem.domain;

import lombok.Getter;

@Getter
public enum ProblemLevelCode {
	PL01(0, "유아"),
	PL02(1, "초등 1학년"),
	PL03(2, "초등 2학년"),
	PL04(3, "초등 3학년"),
	PL05(4, "초등 4학년"),
	PL06(5, "초등 5학년"),
	PL07(6, "초등 6학년");

	final int level;
	final String description;

	// 열거형 생성자에서 각기 다른 람다식을 받아 초기화
	ProblemLevelCode(int level, String description) {
		this.level = level;
		this.description = description;
	}

	public int score() {
		return level * 100;
	}
}
